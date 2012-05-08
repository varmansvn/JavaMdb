/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.varmansvn.desktopapp.JavaMdb;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author varman
 */
public class DatabaseManager {
    
    private Database m_Db;
    
    public DatabaseManager(String dbFile) {
        try {
            m_Db = Database.open(new File(dbFile));
        } catch(IOException e) {
            System.out.println("IO Exception: "+e.toString());
        }
        
        // @varmansvn: To follow what is required by Trodeth
        // which mean, all the rows are stored in the list, so
        // let print the list out.
        PrintStudentList();
    }
        
    public Table getStudentTable() {
        Table studentTable = null;
        if(null == m_Db) return null;
        try {
            studentTable = m_Db.getTable("student");
        } catch (IOException ex) {
            System.out.println("IO Exception: " +ex.toString());
        }
        return studentTable;
    }
    
    public int getStudentRowCount() {
        Table studentTable = null;
        int rowCount = 0;
        studentTable = getStudentTable();
        if(null == studentTable) return -1;
        rowCount = studentTable.getRowCount();
        return rowCount;
    }
    
    public int getStudentColumnCount() {
        Table studentTable = null;
        int colCount = 0;
        studentTable = getStudentTable();
        if(null == studentTable) return -1;
        colCount = studentTable.getColumnCount();
        return colCount;
    }
    
    public List<Map<String, Object>> getStudents() {
       Table studentTable = null;
       List<Column> colList;
       List<Map<String, Object>> studentList = new ArrayList<>();
       int numCols = 0;
       int numRows = 0;
       

       studentTable = getStudentTable();
       if(null == studentTable) return null;
       
       
       colList = studentTable.getColumns();
       numCols = getStudentColumnCount();
       numRows = getStudentRowCount();
       
       int i = 0;
       int j = 0; 
       for(Map<String, Object> row: studentTable) {
            for(Column col: colList) {
                String position;
                Map<String, Object> myMap = new HashMap<String, Object>();
                position = i+"."+j;
                //System.out.print("index = "+position + " value = " +row.get(col.getName()) + " ");
                myMap.put(position, row.get(col.getName()));
                //System.out.print(" " +myMap.get(position));
                studentList.add(myMap);
                
                //Map<String, Object> tmp = studentList.get(numCols * i + j);
                //System.out.println("index = " + (numCols * i + j) + " value = " + tmp.get(position));
                j++;
            }
            //System.out.println("");
            i++;
            j = 0;
        }
       
        return studentList;
    }
    
    public void createNewStudent(String name, String sex, int age) {
        Table studentTable = null;
        try {
            studentTable = getStudentTable();
            if(null == studentTable) return;
            studentTable.addRow(name, sex, age);
        } catch (IOException ex) {
             System.out.println("IO Exception: " +ex.toString());          
        }        
    }
    
    public void PrintStudentList() {
        List<Map<String, Object>> studentList = null;
        Table studentTable = null;
        int numCols = 0;
        int numRows = 0;
        
       studentTable = getStudentTable();
       if(null == studentTable) return;
       
        numCols = getStudentColumnCount();
        numRows = getStudentRowCount();
        
        studentList = getStudents();
        if(null == studentList) return;
        
        for(int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                Map<String, Object> mymap = studentList.get(numCols * x + y);
                System.out.print(mymap.get(x+"."+y) + " ");
            }
            System.out.println("");
        }
    }
    
    public String getStudentColNameByColId(int col) {
        Table studentTable = null;
        List<Column> colList = null;
        String colName = null;
        
        studentTable = getStudentTable();
        if(null == studentTable) return null;
        
        colList = studentTable.getColumns();
        if(null == colList) return null;
        
        colName = colList.get(col).getName();
        if(null == colName) return null;
        return colName;
    }
}
