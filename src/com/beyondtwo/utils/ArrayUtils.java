package com.beyondtwo.utils;

import com.beyond.test.TestArrayUtils;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.*;

public class ArrayUtils {
    public static Integer[][] getSudokuArray(int k) {
        Integer[][] integers = new Integer[k][k];

        //init row lists
        ArrayList<Integer>[] list = new ArrayList[k];
        for (int i = 0; i < k; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                list[i].add(j + 1);
            }
        }

        //fillRows
        for (int i = 0; i < k; i++) {
            if (i == integers.length - 1) {
                fillLastRow(integers, integers[i]);
                return integers;
            }
            fillRow(integers, list[i], i);
        }
        return integers;
    }
    private static void fillRow(Integer[][] integers, List<Integer> list, int i) {

        for (int j = 0; j < integers.length; j++) {
            //fill random number
            int randomListIndex = (int) (Math.random() * (list.size()));
            Integer integer = list.get(randomListIndex);
            integers[i][j] = integer;
            list.remove(integer);

            //vertify is column duplicate
            boolean isDuplicate = false;
            for (int l = 0; l < i; l++) {
                if (integer.equals(integers[l][j])) {
                    isDuplicate = true;
                    break;
                }
            }
            //handle duplicate
            if (isDuplicate) {
                resetList(list,integers.length);
                fillRow(integers, list, i);
                break;
            }
        }
    }
    private static List<Integer> resetList(List<Integer> list, int k) {
        while (list.size()>0){
            list.remove(0);
        }
        for (int j = 0; j < k; j++) {
            list.add(j+1);
        }
        return list;
    }
    private static void fillLastRow(Integer[][] integers, Integer[] integer) {
        Integer[] lastRow = new Integer[integers.length];
        for (int m = 0; m < integer.length; m++) {
            List<Integer>[] lists = new ArrayList[integers.length];
            for (int j = 0; j < lists.length; j++) {
                lists[j] = new ArrayList<>();
            }
            for (int j = 0; j < integers.length; j++) {
                lists[m].add(j + 1);
            }
            for (int j = 0; j < integers.length - 1; j++) {
                lists[m].remove(integers[j][m]);
            }
            lastRow[m] = lists[m].get(0);
        }

        //判断最后一行是否有重复的结果
        Integer duplicate = ArrayUtils.findDuplicate(lastRow);
        if (duplicate != null) {
            System.out.println("fail");
        } else {
            System.out.println("success");
            integers[integers.length - 1] = lastRow;
        }
    }

    public static <T> boolean hasDuplicate(T[] ts) {
        List<T> list = Arrays.asList(ts);
        Set<T> set = new HashSet<>(list);
        return set.size() != ts.length;
    }
    public static <T> T findDuplicate(T[] ts) {
        for (int i = 0; i < ts.length - 1; i++) {
            for (int j = i + 1; j < ts.length; j++) {
                if (ts[i] != null && (ts[i].equals(ts[j])) && (i != j)) {
                    return ts[i];
                }
            }
        }
        return null;
    }

    public static boolean isCrossSudoku(Integer[][] integers,int row,int column){
        Integer[] rowArray = integers[row];
        Integer[] colomnArray = getColomn(integers, column);
        if (findDuplicate(rowArray)==null){
            if (findDuplicate(colomnArray)==null){
                return true;
            }
        }
        return false;
    }

    public static boolean isSudokuSuccess(Integer[][] integers){
        for (int i = 0; i < integers.length; i++) {
            for (int j = 0; j < integers[i].length; j++) {
                if (integers[i][j] == null)
                    return false;
            }
        }
        for (int i = 0; i < integers.length; i++) {
            for (int j = 0; j < integers[i].length; j++) {
                boolean isCrossSudoku = isCrossSudoku(integers, i, j);
                if(!isCrossSudoku){
                    return false;
                }
            }
        }

        return true;
    }


    public static Integer[][] getIntegersFromJTable(JTable table) {
        return getIntegersFromJTableModel(table.getModel());
    }

    public static Integer[][] getIntegersFromJTableModel(TableModel model) {
        Integer[][] integers = new Integer[model.getRowCount()][model.getColumnCount()];
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                Object value = model.getValueAt(i, j);
                if (!value.equals("")) {
                    integers[i][j] = Integer.valueOf(value.toString());
                }
            }
        }
        return integers;
    }

    public static void printIntegerArray(Integer[][] integers){
        for (int i = 0; i < integers.length; i++) {
            for (int j = 0; j < integers[i].length; j++) {
                System.out.print(integers[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static Integer[] getColomn(Integer[][] integers, int k) {
        Integer[] result = new Integer[integers.length];
        for (int i = 0; i < integers.length; i++) {
            result[i] = integers[i][k];
        }
        return result;
    }

    public static void main(String[] args) {
        Integer[][] integers = getSudokuArray(7);
        TestArrayUtils.printIntegerArray(integers);
    }

}
