package com.beyond.utils;

import com.beyond.test.TestArrayUtils;

import java.util.*;

public class ArrayUtils {
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

    public static Integer[][] getSudokuArray(int k) {
        Integer[][] integers = new Integer[k][k];
        ArrayList<Integer>[] list = new ArrayList[k];
        for (int i = 0; i < k; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                list[i].add(j + 1);
            }
        }
        for (int i = 0; i < k; i++) {
            fillRow(integers, list[i], i);
        }
        return integers;
    }

    private static void fillRow(Integer[][] integers, List<Integer> list, int i) {
        TestArrayUtils.printIntegerArray(integers);
        for (int j = 0; j < integers.length; j++) {
            int randomListIndex = (int) (Math.random() * (list.size()));
            Integer integer = list.get(randomListIndex);
            integers[i][j] = integer;
            list.remove(integer);

            boolean isDuplicate = false;
            for (int l = 0; l < i; l++) {
                if (integer.equals(integers[l][j])) {
                    isDuplicate =true;
                    break;
                }
            }

            if(isDuplicate){
                resetList(list, integers.length);
                fillRow(integers, list, i);
                break;
            }
        }
    }

    private static void resetList(List<Integer> list, int k) {
        while (list.size() > 0) {
            list.remove(0);
        }
        for (int j = 0; j < k; j++) {
            list.add(j + 1);
        }
    }

    public static void main(String[] args) {

    }

}
