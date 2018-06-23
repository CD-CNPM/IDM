package com.idm;

import java.awt.Component;
import java.text.MessageFormat;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RendererProgressBar  extends JProgressBar implements TableCellRenderer{
	private static final long serialVersionUID = 1L;

//	được sử dụng để tạo một thanh tiến trình với các giá trị min và max đã cho
	public RendererProgressBar(int min, int max) {
		super(min, max);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		// được sử dụng để thiết lập giá trị hiện tại trên thanh tiến trình.
			setValue( (int) (((Float) value) * 100) );

		// được sử dụng để thiết lập giá trị tới chuỗi tiến trình
			setString(MessageFormat.format("{0,number,#.##%}", value));
		
			return this;
	}

}
