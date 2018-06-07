package com.idm;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;

public class DownloadTableModel  extends AbstractTableModel implements Observer {
	// Table column name
    private static final String[] columnNames = {"File Name", "Size", "Progress", "Transfer rate", "Status"};
    
    // Table column class
	private static final Class[] columnClasses = {String.class, String.class, JProgressBar.class, String.class, String.class};

    //Tên mỗi col trong table
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int col) {
        return columnClasses[col];
    }

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}