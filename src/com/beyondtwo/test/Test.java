package com.beyondtwo.test;

import com.beyond.utils.ArrayUtils;
import com.beyondtwo.entity.Element;
import com.beyondtwo.entity.Table;
import com.beyondtwo.service.SudokuMananger;

public class Test {

    public static int successCount = 0;

    public static void main(String[] args) {
        Table targetTable = null;
        int k = 6;
        Table theTable = new Table(k);
        Integer[][] theIntTable = new Integer[k][k];
        for (int i = 0; i < 2000; i++) {
            Table table = initTable(theTable, theIntTable, k);
            initElements(table);
            Element firstElement = getFirstElement(table);
            firstElement.fill();

            if (successCount == 1) {
                targetTable = table;
                break;
            }
            successCount = 0;
        }
        System.out.println(successCount);
        printTable(targetTable);

        /*Table table = getQuestionTable();
        initElements(table);
        Element firstElement = getFirstElement(table);
        firstElement.fill();
        System.out.println(successCount);*/
    }

    /**
     * getQuestionTable has 2 way
     * 1. 随机生成一张表，纵横无重复，如果有重复就重新生成 只能到5
     * 2. 生成一个填好的表，将某些位置设置成null 只能到7
     *
     * @param table
     * @param intTable
     * @param k
     * @return
     */
    private static Table initTable(Table table, Integer[][] intTable, int k) {
        /*if(table==null){
            table = new Table(k);
        }else{
            clearTable(table);
        }
        Element[][] elements = table.getElements();

        if(intTable==null){
            intTable = new Integer[k][k];
        }else {
            clearIntTable(intTable);
        }
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                int randomNum = (int) (Math.random() * k+1);
                intTable[i][j] = randomNum;
                if(randomNum>k){
                    intTable[i][j] = null;
                }
            }
        }

        boolean hasDuplicate = isDuplicate(intTable);

        if(hasDuplicate){
            getQuestionTable(table,intTable,k);
        }else{
            for (int i = 0; i < elements.length; i++) {
                for (int j = 0; j < elements[i].length; j++) {
                    elements[i][j].setNum(intTable[i][j]);
                }
            }
            return table;
        }*/

        return new SudokuMananger().getQuestionTable(k);
    }

    private static void clearIntTable(Integer[][] intTable) {
        for (int i = 0; i < intTable.length; i++) {
            for (int j = 0; j < intTable[i].length; j++) {
                intTable[i][j] = null;
            }
        }
    }

    private static void clearTable(Table table) {
        table.clear();
    }

    public static Integer[] getColomn(Integer[][] integers, int k) {
        Integer[] result = new Integer[integers.length];
        for (int i = 0; i < integers.length; i++) {
            result[i] = integers[i][k];
        }
        return result;
    }

    public static boolean isDuplicate(Integer[][] integers) {
        for (int i = 0; i < integers.length; i++) {
            if (ArrayUtils.findDuplicate(integers[i]) != null) {
                return true;
            }
        }
        for (int i = 0; i < integers[0].length; i++) {
            if (ArrayUtils.findDuplicate(getColomn(integers, i)) != null) {
                return true;
            }
        }
        return false;
    }

    private static Element getFirstElement(Table table) {
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

    private static void initElements(Table table) {
        Element[][] elements = table.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                elements[i][j].setTable(table);
                elements[i][j].setPossibleResults();
            }
        }
    }

    public static void printTable(Table table) {
        Element[][] elements = table.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                System.out.print(elements[i][j].getNum() + "  ");
            }
            System.out.println();
        }
    }
}
