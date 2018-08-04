package com.beyondtwo.swing;

import com.beyondtwo.entity.Table;
import com.beyondtwo.service.SudokuMananger;
import com.beyondtwo.utils.ArrayUtils;
import com.beyondtwo.utils.LoggerUtils;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class Face extends JPanel {

    class MyTableModel extends AbstractTableModel {
        private String[] columnNames;
        private Object[][] data;
        private List<SuccessListener> listeners = new ArrayList<>();

        private MyTableModel() {

        }

        public void addListeners(SuccessListener listener) {
            this.listeners.add(listener);
        }
        public void removeListener(SuccessListener listener){
            this.listeners.remove(listener);
        }

        public MyTableModel(String[] columnNames, Object[][] data) {
            this.columnNames = columnNames;
            this.data = data;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return data[0].length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return getValueAt(0, columnIndex).getClass();
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public void fireTableCellUpdated(int row, int column) {
            LoggerUtils.log(listeners.toString());
            super.fireTableCellUpdated(row, column);
            fireTableDataChanged();
            if(ArrayUtils.isSudokuSuccess(ArrayUtils.getIntegersFromJTableModel(this))){
                LoggerUtils.log("SUCCESS");
                for (SuccessListener listener:listeners){
                    listener.doSuccess();
                }
            }
        }
    }

    interface SuccessListener{
        void doSuccess();
    }

    class MySuccessListener implements SuccessListener{

        private Component component;
        public MySuccessListener(Component component){
            this.component = component;
        }
        @Override
        public void doSuccess() {

        }
    }

    class MyComboBoxRender extends JLabel implements TableCellRenderer {

        private List<Map<Integer, Integer>> unEditablePosition = new ArrayList<>();
        private Integer[][] integers;

        public Integer[][] getIntegers() {
            return integers;
        }

        public void setIntegers(Integer[][] integers) {
            this.integers = integers;
        }


        public MyComboBoxRender(List<Map<Integer, Integer>> unEditablePosition) {
            this.setOpaque(true);
            this.unEditablePosition = unEditablePosition;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.setText(value.toString());
            Map<Integer, Integer> map = new HashMap<>();
            map.put(row, column);
            if (unEditablePosition.contains(map)) {
                this.setForeground(Color.black);
            } else {
                this.setForeground(Color.decode("#00A1D6"));
            }
            Integer[][] integers = ArrayUtils.getIntegersFromJTable(table);
            boolean isCrossSudoku = ArrayUtils.isCrossSudoku(integers, row, column);
            if (!isCrossSudoku) {
                this.setForeground(Color.red);
            }
            return this;
        }

    }

    class MyComboBoxEditor extends JComboBox implements TableCellEditor {

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return new DefaultCellEditor(this).getTableCellEditorComponent(table, value, isSelected, row, column);
        }

        @Override
        public Object getCellEditorValue() {
            return this.getPrototypeDisplayValue();
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            return true;
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }

        @Override
        public boolean stopCellEditing() {
            return true;
        }

        @Override
        public void cancelCellEditing() {

        }

        @Override
        public void addCellEditorListener(CellEditorListener l) {

        }

        @Override
        public void removeCellEditorListener(CellEditorListener l) {

        }
    }

    class MyComboBoxEditor2 extends DefaultCellEditor {

        private JTable jTable;

        public JTable getjTable() {
            return jTable;
        }

        public void setjTable(JTable jTable) {
            this.jTable = jTable;
        }

        public MyComboBoxEditor2(JComboBox comboBox) {
            super(comboBox);
        }

        @Override
        public boolean isCellEditable(EventObject anEvent) {
            return super.isCellEditable(anEvent);
        }

        @Override
        public boolean shouldSelectCell(EventObject anEvent) {
            return super.shouldSelectCell(anEvent);
        }

        @Override
        public boolean stopCellEditing() {
            LoggerUtils.log("stop");
            return super.stopCellEditing();
        }
    }

    class MyTable extends JTable {

        private List<Map<Integer, Integer>> unEditablePosition = new ArrayList<>();

        public MyTable(TableModel tableModel) {
            super(tableModel);
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    Object value = tableModel.getValueAt(i, j);
                    if (!value.equals("")) {
                        Map<Integer, Integer> map = new HashMap<>();
                        map.put(i, j);
                        unEditablePosition.add(map);
                    }
                }
            }
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            Map<Integer, Integer> map = new HashMap<>();
            map.put(row, column);
            if (unEditablePosition.contains(map)) {
                return false;
            } else {
                return true;
            }

        }
    }

    public void setUpRenderCell(JTable table, TableColumn sportColumn) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Snowboarding");
        comboBox.addItem("Rowing");
        comboBox.addItem("Knitting");
        comboBox.addItem("Speed reading");
        comboBox.addItem("Pool");
        comboBox.addItem("None of the above");
        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        sportColumn.setCellRenderer(renderer);
    }

    public void createUI() {
        int k = 9;
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String[] columnNames = null;
        String[][] data = null;

        //initQuestionTable
        SudokuMananger sudokuMananger = new SudokuMananger();
        Table questionTable = sudokuMananger.getQuestionTable(k);
        //init model
        List<Map<Integer, Integer>> unEditablePosition = new ArrayList<>();
        columnNames = new String[questionTable.getElements().length];
        data = new String[questionTable.getElements().length][questionTable.getElements()[0].length];
        for (int i = 0; i < questionTable.getElements().length; i++) {
            for (int j = 0; j < questionTable.getElements()[i].length; j++) {
                Integer num = questionTable.getElements()[i][j].getNum();
                data[i][j] = num == null ? "" : num.toString();
                if (num != null) {
                    Map<Integer, Integer> map = new HashMap<>();
                    map.put(i, j);
                    unEditablePosition.add(map);
                }
            }
        }

        MyTableModel myTableModel = new MyTableModel(columnNames, data);

        //init comboBox
        JComboBox<String> jComboBox = new JComboBox<>();
        for (int i = 0; i < k; i++) {
            jComboBox.addItem(i + 1 + "");
        }

        //init table
        JTable jTable = null;
        jTable = new MyTable(myTableModel);
        MyComboBoxEditor2 myComboBoxEditor = new MyComboBoxEditor2(jComboBox);
        MyComboBoxRender myComboBoxRender = new MyComboBoxRender(unEditablePosition);
        LoggerUtils.log(unEditablePosition.toString());
        jTable.setDefaultEditor(Object.class, myComboBoxEditor);
        jTable.setDefaultRenderer(Object.class, myComboBoxRender);
        JTable finalJTable = jTable;
        myTableModel.addListeners(new SuccessListener() {
            @Override
            public void doSuccess() {
                jFrame.remove(finalJTable);
                jFrame.add(new Label("SUCCESS"));
            }
        });

        //set table graph
        Enumeration<TableColumn> columns = jTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            columns.nextElement().setPreferredWidth(30);
        }
        jTable.setRowHeight(30);

        //setUpRenderCell(jTable, jTable.getColumnModel().getColumn(2));

        jFrame.getContentPane().add(jTable);

        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Face face = new Face();
        face.createUI();
    }
}
