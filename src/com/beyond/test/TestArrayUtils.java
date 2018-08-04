package com.beyond.test;

import com.beyond.utils.ArrayUtils;

public class TestArrayUtils {

    public static void main(String[] args){
        Integer[][] sudokuArray = ArrayUtils.getSudokuArray(5);
        for (int i = 0; i < sudokuArray.length; i++) {
            for (int j = 0; j < sudokuArray[i].length; j++) {
                System.out.print(sudokuArray[i][j] + "  ");
            }
            System.out.println();
        }

    }

    public static void printIntegerArray(Integer[][] integers){
        for (int i = 0; i < integers.length; i++) {
            for (int j = 0; j < integers[i].length; j++) {
                System.out.print(integers[i][j] + "  ");
            }
            System.out.println();
        }
    }

}
