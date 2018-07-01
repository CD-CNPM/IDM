package com.idm;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;

public class DownloadTableModel extends AbstractTableModel implements Observer {
	// Table column name
	private static final String[] columnNames = { "File Name", "Size", "Progress", "Transfer rate", "Status" };

	// Table column class
	private static final Class[] columnClasses = { String.class, String.class, JProgressBar.class, String.class,
			String.class };

	// Tên mỗi col trong table
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int col) {
		return columnClasses[col];
	}

	// số lượng col của table
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	// số lượng row của JTable = size(số lượng record) của DownloadList
	@Override
	public int getRowCount() {
		return DownloadManager.getInstance().getDownloadList().size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Download download = DownloadManager.getInstance().getDownloadList().get(row);

		switch (col) {
		case 0: // File name
			return download.getdFileName();
		case 1: // Size
			return (download.getdFileSize() == -1) ? "" : (FileUtil.readableFileSize(download.getdFileSize()));
		case 2: // Progress
			return download.getProgress();
		case 3: // Transfer rate
			return (download.getdState() != DownloadState.DOWNLOADING) ? ""
					: (FileUtil.readableFileSize((long) download.getSpeed()) + "/sec");
		case 4: // Status
			return download.getdState();
		}
		return "";
	}

	public void addNewDownload(Download download) {

		// Register this model to be a downloader's observer
		download.addObserver(this);

		fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
	}

	@Override
	public void update(Observable o, Object arg1) {
		int index = DownloadManager.getInstance().getDownloadList().indexOf(o);
		fireTableRowsUpdated(index, index);
	}

}