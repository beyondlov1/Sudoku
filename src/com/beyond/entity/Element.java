package com.beyond.entity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Element {
    private int i, j;
    private Integer num;
    private Set<Integer> possibleResults;

    public Element() {

    }

    public Element(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public Element(int i, int j, Integer num) {
        this.i = i;
        this.j = j;
        this.num = num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void fill(Table table,int failCount) {
        //选取一个
        if (possibleResults!=null&&possibleResults.size() > 0) {
            Iterator<Integer> iterator = this.possibleResults.iterator();
            for(int i=0;i<failCount+1;i++){
                if(iterator.hasNext()){
                    this.num = iterator.next();
                }else{
                    System.out.println("It is not possible");
                    throw new RuntimeException("not possible");
                }
            }
            table.getElements()[i][j].setNum(num);

            //To do : remove possible
            this.possibleResults = null;
        } else {
            throw new RuntimeException("该处没有可添加的数字");
        }
    }

    public void setPossibleResults(Table table) {
        if(num!=null){
            return;
        }
        Set<Integer> possibleResults = new HashSet<>();
        for (int k = 1; k <= table.getElements().length; k++) {
            possibleResults.add(k);
        }
        for (Element aRow : getRow(table)) {
            possibleResults.remove(aRow.getNum());
        }
        for (Element aCol : getCol(table)) {
            possibleResults.remove(aCol.getNum());
        }
        if(possibleResults.isEmpty()){
            possibleResults = null;
        }
        this.possibleResults = possibleResults;
    }

    public Set<Integer> getPossibleResults() {
        return possibleResults;
    }

    private Element[] getRow(Table table) {
        Element[][] elements = table.getElements();
        return elements[i];
    }

    private Element[] getCol(Table table) {
        Element[][] elements = table.getElements();
        Element[] col = new Element[elements[i].length];
        for (int k = 0; k < elements.length; k++) {
            col[k] = elements[k][j];
        }
        return col;
    }

    public Integer getNum() {
        return num;
    }
}
