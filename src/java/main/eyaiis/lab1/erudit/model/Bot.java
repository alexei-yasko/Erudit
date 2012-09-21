package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Word moreSuitableWordForAllColumns = getMoreSuitableWord(new ArrayList<Word>(moreSuitableWordForColumns.keySet()));
        Word moreSuitableWordForAllRows = getMoreSuitableWord(new ArrayList<Word>(moreSuitableWordForRows.keySet()));

        Word resultWord = getMoreSuitableWord(Arrays.asList(moreSuitableWordForAllColumns, moreSuitableWordForAllRows));

        if (resultWord != null) {

            if (moreSuitableWordForColumns.get(resultWord) != null) {
                int columnIndex = moreSuitableWordForColumns.get(resultWord);
                placeWordInColumn(resultWord, gameFieldModel, columnIndex);
            }
            else if (moreSuitableWordForRows.get(resultWord) != null) {
                int rowIndex = moreSuitableWordForRows.get(resultWord);
                placeWordInRow(resultWord, gameFieldModel, rowIndex);
            }

            dictionary.deleteWord(resultWord.convertToString());
            removeWordLettersFromUserHand(resultWord);
        }

        game.nextStep(resultWord);
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

        List<Letter> placedOnRowOrColumnLetters = getPlacedLettersInRowOrColumn(rowOrColumn);

        if (!placedOnRowOrColumnLetters.isEmpty()) {
            List<Letter> possibleLettersForWords = new ArrayList<Letter>(getLetterList());
            possibleLettersForWords.addAll(placedOnRowOrColumnLetters);

            List<String> composedStringWords =
                dictionary.composeWordsFromLetters(GameUtils.convertFromLetterListToCharacterList(possibleLettersForWords));
            List<Word> composedWords = composeWordsFromStringListAndLetters(composedStringWords, possibleLettersForWords);

            List<Word> filteredWords = selectWordsThatCanBePlaced(composedWords, rowOrColumn);

            return getMoreSuitableWord(filteredWords);
        }
        else {
            return null;
        }
    }

    private List<Letter> getPlacedLettersInRowOrColumn
        (List<Letter> columnOrRow) {
        List<Letter> placedLetters = new ArrayList<Letter>();

        for (Letter letter : columnOrRow) {

            if (letter != null) {
                placedLetters.add(letter);
            }
        }

        return placedLetters;
    }

    private List<Word> composeWordsFromStringListAndLetters(List<String> stringList, List<Letter> possibleLetters) {
        List<Word> wordList = new ArrayList<Word>();

        for (String word : stringList) {
            wordList.add(Word.composeWordFromLetters(word, possibleLetters));
        }

        return wordList;
    }

    private Word getMoreSuitableWord(List<Word> wordList) {
        Word maxPointsWord = null;
        int maxPoints = 0;

        for (Word word : wordList) {

            if (word != null) {

                if (word.isContainsAllLettersFromList(getLetterList())) {
                    return word;
                }

                int wordPoints = word.calculateWordPoints();

                if (wordPoints > maxPoints) {
                    maxPointsWord = word;
                    maxPoints = wordPoints;
                }
            }
        }

        return maxPointsWord;
    }

    private List<Word> selectWordsThatCanBePlaced(List<Word> wordList, List<Letter> columnOrRow) {
        List<Word> filteredList = new ArrayList<Word>();

        for (Word word : wordList) {

            if (isWordCanBePlaced(word, columnOrRow)) {
                filteredList.add(word);
            }
        }

        return filteredList;
    }

    private boolean isWordCanBePlaced(Word word, List<Letter> columnOrRow) {
        return getRightPlacementOfWord(word, columnOrRow) != null;
    }

    private List<Letter> getRightPlacementOfWord(Word word, List<Letter> columnOrRow) {
        int possibleWordPositionNumbers = columnOrRow.size() - word.getLength();

        for (int i = 0; i < possibleWordPositionNumbers; i++) {
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

        boolean isNotNullExist = false;
        boolean isNullExist = false;

        for (int i = beginIndex; i < endIndex; i++) {

            if (columnOrRow.get(i) == null) {
                isNullExist = true;
            }

            if (columnOrRow.get(i) != null) {
                isNotNullExist = true;
            }

            if (columnOrRow.get(i) != null && !columnOrRow.get(i).equals(experimentalColumnOrRow.get(i))) {
                return false;
            }
        }

        return isNullExist && isNotNullExist;
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

    private void removeWordLettersFromUserHand(Word word) {

        for (Letter letter : word.getLettersOfWord()) {
            getLetterList().remove(letter);
        }
    }
}
