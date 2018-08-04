package com.beyond.entity;

public class Table {
    private Element[][] elements;
    private int rowCount;
    private int colCount;

    public Table(int i){
        elements = new Element[i][i];
        initElements(elements);
    }

    private void initElements(Element[][] elements){
        for (int i=0;i<elements.length;i++){
            for (int j = 0; j < elements[i].length; j++) {
                elements[i][j] = new Element(i,j);
            }
        }
    }

    public Element[][] getElements() {
        return elements;
    }

    public void setElements(Element[][] elements) {
        this.elements = elements;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }
}
