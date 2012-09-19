package eyaiis.lab1.erudit.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public void executeStep(GameFieldModel gameFieldModel) {

    }

//    public void getAllLettersCombination() {
//        List<List<Letter>> lettersCombination = new ArrayList<List<Letter>>();
//
//        permutation(lettersCombination, new ArrayList<Letter>(letterList), 0);
//
//        System.out.println(lettersCombination.size());
//    }
//
//    public void permutation(List<List<Letter>> lettersCombination, List<Letter> letters, int level) {
//
//        if (level == letters.size() - 1) {
//            lettersCombination.add(new ArrayList<Letter>(letters));
//        }
//        else {
//            for (int j = level; j < letters.size(); j++) {
//                swap(letters, level, j);
//                permutation(lettersCombination, letters, level + 1);
//                swap(letters, level, j);
//            }
//        }
//
//    }
//
//    private void swap(List<Letter> letters, int first, int second) {
//        Letter letter = letters.get(first);
//        letters.set(first, letters.get(second));
//        letters.set(second, letter);
//    }

//    private void doCombination(List<Letter> letterList, int depth, int length) {
//        if (depth == length) {
//            qwerty.add(new ArrayList<Letter>(letterList));
//        }
//        for (int i = 0; i < getLetterList().size(); i++) {
//            letterList.set(depth, getLetterList().get(i));
//            doCombination(letterList, depth + 1, length);
//        }
//
//    }
//
//    public static void main(String[] args) {
//        int[] pa = new int[]{1, 2, 3, 4, 5, 6, 7};
//        prmt(pa, 0);
//    }
//
//    private static void prmt(int[] pa, int i) {
//        if (i == pa.length - 1) {
//            arraout(pa);
//        }
//        else {
//            for (int j = i; j < pa.length; j++) {
//                aswap(pa, i, j);
//                prmt(pa, i + 1);
//                aswap(pa, i, j);
//            }
//        }
//    }
//
//    private static void aswap(int[] pa, int i, int j) {
//        int k = pa[i];
//        pa[i] = pa[j];
//        pa[j] = k;
//    }
//
//    private static void arraout(int[] pa) {
//        String s = "[";
//
//        for (int a : pa) {
//            s += a + ", ";
//        }
//
//        s = s.substring(0, s.length() - 2);
//
//        s += "]";
//
//        System.out.println(s);
//    }

}
