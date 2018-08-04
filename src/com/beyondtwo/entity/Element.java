package com.beyondtwo.entity;

import com.beyondtwo.service.SudokuMananger;
import com.beyondtwo.utils.LoggerUtils;

import java.util.*;

public class Element {
    private int i, j;
    private Integer num;
    private List<Integer> possibleResults;
    private int possibleIndex;

    private Element lastElement;
    private Element nextElement;

    private Table table;

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

    public void fill() {
        /*this.setPossibleResults();
        if (possibleResults == null) {
            //end
            if (table.isSuccess()) {
                handleSuccess(table);
            }
            return;
        }
        for (Integer possibleResult : possibleResults) {
            this.setNum(possibleResult);
            this.setNextElement();
            if (this.nextElement != null) {
                this.nextElement.setPossibleResults();
                if (this.nextElement.possibleResults == null || this.nextElement.possibleResults.size() < 1) {
                    //end
                    if (table.isSuccess()) {
                        handleSuccess(table);
                    }
                } else {
                    this.nextElement.fill();
                }
            } else {
                if (table.isSuccess()) {
                    handleSuccess(table);
                }
            }

            if (SudokuMananger.successCount == 2) { //保证只有一个答案
                break;
            }
        }
        this.setNum(null);*/


        this.setPossibleResults();
        //
        if (possibleResults == null || possibleResults.size() == 0) {
            // this way is deparated
            this.setNum(null);
            return;
        }
        for (Integer possibleResult : possibleResults) {
            this.setNum(possibleResult);
            this.setNextElement();
            if (nextElement == null) {
                //success
                SudokuMananger.successCount++;
                return;
            } else {
                nextElement.fill();
                //return back TO DO code
                this.setNum(null);
            }
        }
    }

    private void handleSuccess(Table table) {
        SudokuMananger.successCount++;
    }

    public void solveFill() {
        /*this.setPossibleResults();
        //
        if (possibleResults == null) {
            // this way is deparated
            return;
        }
        for (Integer possibleResult : possibleResults) {
            this.setNum(possibleResult);
            this.setNextElement();
            if (nextElement == null) {
                //success
                SudokuMananger.successTable = table;
                return;
            } else {
                nextElement.solveFill();
                //return back TO DO code
                if (SudokuMananger.successTable != null) {
                    return;
                }
            }
        }*/

        this.setPossibleResults();
        //
        if (possibleResults == null || possibleResults.size() == 0) {
            // this way is deparated
            if (SudokuMananger.successTable == null) {
                this.setNum(null);
            }
            return;
        }
        for (Integer possibleResult : possibleResults) {
            this.setNum(possibleResult);
            this.setNextElement();
            if (nextElement == null) {
                //success
                SudokuMananger.successTable = table;
                return;
            } else {
                nextElement.solveFill();
                //return back TO DO code
                if (SudokuMananger.successTable == null) {
                    this.setNum(null);
                }else{
                    break;
                }
            }
            if (SudokuMananger.successTable != null) {
                return;
            }
        }
    }

    public void setPossibleResults() {
        if (num != null) {
            return;
        }
        List<Integer> possibleResults = new ArrayList<>();
        for (int k = 1; k <= table.getElements().length; k++) {
            possibleResults.add(k);
        }
        for (Element aRow : getRow(table)) {
            possibleResults.remove(aRow.getNum());
        }
        for (Element aCol : getCol(table)) {
            possibleResults.remove(aCol.getNum());
        }
        if (possibleResults.isEmpty()) {
            possibleResults = null;
        }
        this.possibleResults = possibleResults;
    }

    public List<Integer> getPossibleResults() {
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

    public void setNum(Integer num) {
        this.num = num;
    }

    public Element getLastElement() {
        return lastElement;
    }

    public void setLastElement(Element lastElement) {
        this.lastElement = lastElement;
    }

    public Element getNextElement() {
        return nextElement;
    }

    private void setNextElement() {
        Set<Element> elementsContainer = new HashSet<>();
        Element[][] elements = table.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                if (elements[i][j].getNum() == null) {
                    elements[i][j].setPossibleResults();
                    elementsContainer.add(elements[i][j]);
                }
            }
        }
        Element minPossibleElement = null;
        for (Element element : elementsContainer) {
            if (minPossibleElement == null) {
                minPossibleElement = element;
            } else {
                if(element.getPossibleResults()==null){
                    //System.out.println("element Possible result = null");
                    LoggerUtils.log("element Possible result = null");
                    this.nextElement = element;
                    return;
                }
                if (minPossibleElement.getPossibleResults()==null){
                    //System.out.println("minElement Possible result = null");
                    LoggerUtils.log("minElement Possible result = null");
                    this.nextElement = minPossibleElement;
                    return;
                }
                minPossibleElement = minPossibleElement.getPossibleResults().size() < element.getPossibleResults().size() ? minPossibleElement : element;
            }
        }
        this.nextElement = minPossibleElement;




      /*  //get min possible element
        Element minPossibleElement = null;
        Element[][] elements = table.getElements();
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements[i].length; j++) {
                elements[i][j].setPossibleResults();
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
        this.nextElement =  minPossibleElement;*/
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public int getPossibleIndex() {
        return possibleIndex;
    }

    public void setPossibleIndex(int possibleIndex) {
        this.possibleIndex = possibleIndex;
    }
}
