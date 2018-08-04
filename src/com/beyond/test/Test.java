package com.beyond.test;

import com.beyond.entity.Element;
import com.beyond.entity.Table;

public class Test {

    private static int successCount = 0;

    public static void main(String[] args) {
        Table table = initTable();

        printTable(table);

        try {
            fillTable(table, 0);
        }catch (RuntimeException e){
            if(e.getMessage().equals("not possible")){
                System.out.println(successCount);
            }
        }


    }

    private static void fillTable(Table table, int failCount) {
        calculatePossibleResults(table);
        fillMinPossibleResultCount(table, failCount);

        if (!isOver(table)) {
            fillTable(table, failCount);
        } else {
            if (!isSuccess(table)) {
                fillTable(resetTable(), failCount + 1);
            } else {
                successCount++;
                //fillTable(resetTable(), failCount + 1);
            }
        }
    }

    private static Table resetTable() {
        return initTable();
    }

    private static Table initTable() {
        Table table = new Table(5);
        Element[][] elements = table.getElements();
        elements[0][2].setNum(2);
        elements[0][0].setNum(1);
        elements[2][1].setNum(2);
        return table;
    }

    private static boolean isOver(Table table) {
        Element[][] elements = table.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j].getPossibleResults() != null)
                    return false;
            }
        }
        return true;
    }

    private static boolean isSuccess(Table table) {
        Element[][] elements = table.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j].getNum() == null)
                    return false;
            }
        }
        return true;
    }

    private static void fillMinPossibleResultCount(Table table, int failCount) {
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
        if (minPossibleElement != null) {
            minPossibleElement.fill(table, failCount);
            printTable(table);
        }

    }

    private static void printTable(Table table) {
        Element[][] elements = table.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                System.out.print(elements[i][j].getNum() + "  ");
            }
            System.out.println();
        }
    }

    private static void calculatePossibleResults(Table table) {
        Element[][] elements = table.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                elements[i][j].setPossibleResults(table);
                System.out.print(elements[i][j].getPossibleResults() + "  ");
            }
            System.out.println();
        }
    }
}
