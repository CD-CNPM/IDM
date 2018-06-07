package idm;

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
	
	// số lượng col của table
	@Override
	public int getColumnCount() {
		 return columnNames.length;
	}
	// số lượng row của table
	@Override
	public int getRowCount() {
        return DownloadManager.getInstance().getDownloadList().size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		 Downloader download = DownloadManager.getInstance().getDownloadList().get(row);
	        
	        switch (col) {
	            case 0: // File name
	                return download.getFileName();
	            case 1: // Size
	                return (download.getFileSize() == -1) ? "" : (FileUltil.readableFileSize(download.getFileSize()));
	            case 2: // Progress
	                return download.getProgress();
	            case 3: // Transfer rate
	                return  (download.getState() != 0) ? "" : (FileUltil.readableFileSize((long) download.getSpeed()) + "/sec");
	            case 4: // Status
	                return Downloader.STATUS[download.getState()];
	        }
	        
	        return "";
	}

	@Override
	public void update(Observable ob, Object arg) {
		int index = DownloadManager.getInstance().getDownloadList().indexOf(ob);
        fireTableRowsUpdated(index, index);
		
	}
}
