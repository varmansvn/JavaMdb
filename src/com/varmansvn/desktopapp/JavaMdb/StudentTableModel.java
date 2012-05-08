/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.varmansvn.desktopapp.JavaMdb;

import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author varman
 */
public class StudentTableModel extends AbstractTableModel{
    
    DatabaseManager dbManager;
    List<Map<String, Object>> studentList= null;
    int numCols = 0;
    //private Connectio
    
    public StudentTableModel() {
        dbManager = new DatabaseManager("student.accdb");
        numCols = getColumnCount();
        studentList = dbManager.getStudents();
    }
    
    @Override
    public int getRowCount() {
        if(null == dbManager) return -1;
        return dbManager.getStudentRowCount();
    }

    @Override
    public int getColumnCount() {
        if(null == dbManager) return -1;
        return dbManager.getStudentColumnCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       Map<String, Object> aStudent = studentList.get(numCols * rowIndex + columnIndex);
       return aStudent.get(rowIndex+"."+columnIndex);
    }
    
    @Override
    public String getColumnName(int col) {
        return dbManager.getStudentColNameByColId(col);
    }
}
