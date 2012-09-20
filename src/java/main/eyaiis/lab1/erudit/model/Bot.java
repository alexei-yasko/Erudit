package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eyaiis.lab1.erudit.dictionary.Dictionary;

/**
 * Class for the computer bot.
 *
 * @author Q-YAA
 */
public class Bot extends User {

    public Bot(String name) {
        super(name);
    }

    @Override
    public void executeStep(Game game, Dictionary dictionary) {
        GameFieldModel gameFieldModel = game.getGameFieldModel();

        Map<Word, Integer> moreSuitableWordForColumns = getMoreSuitableWordForColumns(gameFieldModel, dictionary);
        Map<Word, Integer> moreSuitableWordForRows = getMoreSuitableWordForRows(gameFieldModel, dictionary);

        Word moreSuitableWordForAllColumns = getMoreSuitableWord(new ArrayList<Word>(moreSuitableWordForColumns.keySet()), 0);
        Word moreSuitableWordForAllRows = getMoreSuitableWord(new ArrayList<Word>(moreSuitableWordForRows.keySet()), 0);

        Word resultWord = getMoreSuitableWord(Arrays.asList(moreSuitableWordForAllColumns, moreSuitableWordForAllRows), 0);

        if (moreSuitableWordForColumns.get(resultWord) != null) {
            int columnIndex = moreSuitableWordForColumns.get(resultWord);
            placeWordInColumn(resultWord, gameFieldModel, columnIndex);
        }
        else if (moreSuitableWordForRows.get(resultWord) != null) {
            int rowIndex = moreSuitableWordForRows.get(resultWord);
            placeWordInRow(resultWord, gameFieldModel, rowIndex);
        }

        if (resultWord != null) {
            dictionary.deleteWord(resultWord.convertToString());
            removeWordLettersFromUserHand(resultWord);
        }

        game.nextStep(resultWord);
    }

    private void removeWordLettersFromUserHand(Word word) {

        for (Letter letter : word.getLettersOfWord()) {
            getLetterList().remove(letter);
        }
    }

    private void placeWordInColumn(Word word, GameFieldModel gameFieldModel, int columnIndex) {
        List<Letter> column = gameFieldModel.getColumn(columnIndex);
        List<Letter> rightPlacementWordInColumn = getRightPlacementOfWord(word, column);
        gameFieldModel.setColumn(columnIndex, 0, rightPlacementWordInColumn);
    }

    private void placeWordInRow(Word word, GameFieldModel gameFieldModel, int rowIndex) {
        List<Letter> row = gameFieldModel.getRow(rowIndex);
        List<Letter> rightPlacementWordInColumn = getRightPlacementOfWord(word, row);
        gameFieldModel.setRow(rowIndex, 0, rightPlacementWordInColumn);
    }


    private List<Letter> getRightPlacementOfWord(Word word, List<Letter> columnOrRow) {
        int possibleWordPositions = columnOrRow.size() - word.getLength();

        for (int i = 0; i < possibleWordPositions; i++) {
            List<Letter> experimentalColumnOrRow = new ArrayList<Letter>(columnOrRow);

            for (int j = i; j < i + word.getLength(); j++) {
                experimentalColumnOrRow.set(j, word.getLetter(j - i));
            }

            if (isRightWordPosition(i, i + word.getLength(), experimentalColumnOrRow, columnOrRow)) {
                return experimentalColumnOrRow;
            }
        }

        return null;
    }

    private boolean isRightWordPosition(
        int beginIndex, int endIndex, List<Letter> experimentalColumnOrRow, List<Letter> columnOrRow) {

        boolean isRightPosition = false;

        for (int i = beginIndex; i < endIndex; i++) {

            if (columnOrRow.get(i) != null && columnOrRow.get(i).equals(experimentalColumnOrRow.get(i))) {
                isRightPosition = true;
            }
        }

        return isRightPosition;
    }

    private Map<Word, Integer> getMoreSuitableWordForRows(GameFieldModel gameFieldModel, Dictionary dictionary) {
        Map<Word, Integer> moreSuitableWordForRow = new HashMap<Word, Integer>();

        for (int i = 0; i < gameFieldModel.getFieldHeight(); i++) {
            List<Letter> row = gameFieldModel.getRow(i);
            Word moreSuitableWord = getMoreSuitableWordForRowOrColumn(row, dictionary);

            if (moreSuitableWord != null) {
                moreSuitableWordForRow.put(moreSuitableWord, i);
            }
        }

        return moreSuitableWordForRow;
    }

    private Map<Word, Integer> getMoreSuitableWordForColumns(GameFieldModel gameFieldModel, Dictionary dictionary) {
        Map<Word, Integer> moreSuitableWordForColumns = new HashMap<Word, Integer>();

        for (int i = 0; i < gameFieldModel.getFieldWidth(); i++) {
            List<Letter> column = gameFieldModel.getColumn(i);
            Word moreSuitableWord = getMoreSuitableWordForRowOrColumn(column, dictionary);

            if (moreSuitableWord != null) {
                moreSuitableWordForColumns.put(moreSuitableWord, i);
            }
        }

        return moreSuitableWordForColumns;
    }

    private Word getMoreSuitableWordForRowOrColumn(
        List<Letter> rowOrColumn, Dictionary dictionary) {

        List<Letter> placedLetters = getPlacedLettersInRowOrColumn(rowOrColumn);

        if (!placedLetters.isEmpty()) {
            List<Letter> possibleLettersForWords = new ArrayList<Letter>(getLetterList());
            possibleLettersForWords.addAll(placedLetters);

            List<String> composedWords =
                dictionary.composeWordsFromLetters(GameUtils.convertFromLetterListToCharacterList(possibleLettersForWords));

            List<String> possibleWords = filterWordsByRegexp(composedWords, createRegexpForRowOrColumn(rowOrColumn));

            return getMoreSuitableWord(
                composeWordsFromStringList(possibleWords, possibleLettersForWords), placedLetters.size() + 1);
        }
        else {
            return null;
        }
    }

    private List<Word> composeWordsFromStringList(List<String> stringList, List<Letter> possibleLetters) {
        List<Word> wordList = new ArrayList<Word>();

        for (String word : stringList) {
            wordList.add(Word.composeWord(word, possibleLetters));
        }

        return wordList;
    }

    private List<String> filterWordsByRegexp(List<String> wordList, String regexp) {
        Pattern pattern = Pattern.compile(regexp);

        List<String> filteredWordList = new ArrayList<String>();

        for (String word : wordList) {
            Matcher matcher = pattern.matcher(word);

            if (matcher.matches()) {
                filteredWordList.add(word);
            }
        }

        return filteredWordList;
    }

    private String createRegexpForRowOrColumn(List<Letter> rowOrColumn) {
        int quantityOfEmptyCell = 0;
        String pattern = "[а-я]{0,%s}";

        StringBuilder regexpBuilder = new StringBuilder();

        for (Letter letter : rowOrColumn) {

            if (letter == null) {
                quantityOfEmptyCell++;
            }
            else {
                regexpBuilder.append(String.format(pattern, quantityOfEmptyCell));
                regexpBuilder.append(letter.getName());

                quantityOfEmptyCell = 0;
            }
        }

        regexpBuilder.append(String.format(pattern, quantityOfEmptyCell));

        return regexpBuilder.toString();
    }

    private List<Letter> getPlacedLettersInRowOrColumn(List<Letter> columnOrRow) {
        List<Letter> placedLetters = new ArrayList<Letter>();

        for (Letter letter : columnOrRow) {

            if (letter != null) {
                placedLetters.add(letter);
            }
        }

        return placedLetters;
    }

    private Word getMoreSuitableWord(List<Word> wordList, int wordMinLength) {
        Word maxPointsWord = null;
        int maxPoints = 0;

        for (Word word : wordList) {

            if (word != null) {

                if (word.isContainsLetterList(getLetterList())) {
                    return word;
                }

                int wordPoints = word.calculateWordPoints();

                if (wordPoints > maxPoints && word.getLength() >= wordMinLength) {
                    maxPointsWord = word;
                    maxPoints = wordPoints;
                }
            }
        }

        return maxPointsWord;
    }
}
