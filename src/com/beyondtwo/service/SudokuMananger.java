package com.beyondtwo.service;

import com.beyondtwo.entity.Element;
import com.beyondtwo.entity.Table;
import com.beyondtwo.test.Test;
import com.beyondtwo.utils.ArrayUtils;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SudokuMananger {

    public static int successCount = 0;
    public static Table successTable = null;

    public Table getQuestionTable(int k) {

        //get completed sudokuArray
        Integer[][] intTable = ArrayUtils.getSudokuArray(k);

        System.out.println("sudokuArray:");
        ArrayUtils.printIntegerArray(intTable);

        Table table = new Table(k);
        getSingleAnswerTable(intTable, table);

        return table;
    }

    private Table getSingleAnswerTable(Integer[][] integers, Table table) {
        Integer[][] intTable = new Integer[integers.length][integers[0].length];

        for (int i = 0; i < intTable.length; i++) {
            for (int j = 0; j < intTable[i].length; j++) {
                intTable[i][j] = new Integer(integers[i][j]);
            }
        }
        Element[][] elements = table.getElements();
        //set some elements null
        Random random = new Random();
        for (int i = 0; i < intTable.length; i++) {
            for (int j = 0; j < intTable[i].length; j++) {
                intTable[i][j] = (random.nextInt(20)<10) ?  intTable[i][j]:null ;
            }
        }

        //init table
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                elements[i][j].setNum(intTable[i][j]);
            }
        }


        //makeSureSingleAnswer
        table.initElements();
        Element firstElement = getFirstElement(table);
        firstElement.fill();

        if (successCount == 1) {
            return table;
        }
        successCount = 0;
        table = getSingleAnswerTable(integers, table);

        return table;
    }

    public Table solveQuestionTable(Table table) {
        table.initElements();
        Element firstElement = getFirstElement(table);
        firstElement.solveFill();
        return table;
    }

    private Element getFirstElement(Table table) {
        //get min possible element
        Element minPossibleElement = null;
        Element[][] elements = table.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j].getPossibleResults() == null) {
                    continue;
                }
                if (minPossibleElement == null) {
                    minPossibleElement = elements[i][j];
                } else {
                    minPossibleElement = minPossibleElement.getPossibleResults().size() < elements[i][j].getPossibleResults().size() ? minPossibleElement : elements[i][j];
                }
            }
        }
        return minPossibleElement;
    }

    public static void main(String[] args) {
        SudokuMananger sudokuMananger = new SudokuMananger();
        Table table = sudokuMananger.getQuestionTable(5);
        Test.printTable(table);
        sudokuMananger.solveQuestionTable(table);
        Test.printTable(successTable);
    }
}
