package com.beyondtwo.entity;

import com.beyondtwo.utils.ArrayUtils;

public class Table {
    private Element[][] elements;
    private int i;
    public Table(){

    }
    public Table(int i){
        elements = new Element[i][i];
        initElements(elements);
        this.i = i;
    }


    private void initElements(Element[][] elements){
        for (int i=0;i<elements.length;i++){
            for (int j = 0; j < elements[i].length; j++) {
                elements[i][j] = new Element(i,j);
            }
        }
    }

    public void initElements() {
        Element[][] elements = this.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                elements[i][j].setTable(this);
                elements[i][j].setPossibleResults();
            }
        }
    }

    public Element[][] getElements() {
        return elements;
    }

    public void setElements(Element[][] elements) {
        this.elements = elements;
    }

    public boolean isOver() {
        Element[][] elements = this.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j].getPossibleResults() != null)
                    return false;
            }
        }
        return true;
    }

    public boolean isSuccess() {
        Element[][] elements = this.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j].getNum() == null)
                    return false;
            }
        }
        return true;
    }

    public void clear() {
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                elements[i][j].setNum(null);
            }
        }
    }

    public void printTable() {
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                System.out.print(elements[i][j].getNum() + "  ");
            }
            System.out.println();
        }
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

}
