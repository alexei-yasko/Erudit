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
    public void executeStep(GameFieldModel gameFieldModel, Dictionary dictionary) {
        Map<Word, Integer> moreSuitableWordForColumns = getMoreSuitableWordForColumns(gameFieldModel, dictionary);
        Map<Word, Integer> moreSuitableWordForRows = getMoreSuitableWordForRows(gameFieldModel, dictionary);

        Word moreSuitableWordForAllColumns = getMoreSuitableWord(new ArrayList<Word>(moreSuitableWordForColumns.keySet()), 0);
        Word moreSuitableWordForAllRows = getMoreSuitableWord(new ArrayList<Word>(moreSuitableWordForRows.keySet()), 0);

        Word resultWord = getMoreSuitableWord(Arrays.asList(moreSuitableWordForAllColumns, moreSuitableWordForAllRows), 0);

        if (moreSuitableWordForColumns.get(resultWord) != null) {
            placeWordInColumn(resultWord, moreSuitableWordForColumns.get(resultWord));
        }
        else if (moreSuitableWordForRows.get(resultWord) != null) {
            placeWordInRow(resultWord, moreSuitableWordForRows.get(resultWord));
        }
    }

    private void placeWordInColumn(Word word, int columnNumber) {

    }

    private void placeWordInRow(Word word, int rowNumber) {

    }

    private Map<Word, Integer> getMoreSuitableWordForRows(GameFieldModel gameFieldModel, Dictionary dictionary) {
        Map<Word, Integer> moreSuitableWordForRow = new HashMap<Word, Integer>();

        for (int i = 0; i < gameFieldModel.getFieldHeight(); i++) {
            List<Letter> row = gameFieldModel.getRow(i);
            Word moreSuitableWord = getMoreSuitableWordForRowOrColumn(row, gameFieldModel, dictionary);

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
            Word moreSuitableWord = getMoreSuitableWordForRowOrColumn(column, gameFieldModel, dictionary);

            if (moreSuitableWord != null) {
                moreSuitableWordForColumns.put(moreSuitableWord, i);
            }
        }

        return moreSuitableWordForColumns;
    }

    private Word getMoreSuitableWordForRowOrColumn(
        List<Letter> rowOrColumn, GameFieldModel gameFieldModel, Dictionary dictionary) {

        List<Letter> placedLetters = getPlacedLetters(rowOrColumn);

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

    private List<Letter> getPlacedLetters(List<Letter> columnOrRow) {
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
