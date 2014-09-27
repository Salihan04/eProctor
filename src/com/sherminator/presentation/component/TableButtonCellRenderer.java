/*
 * @author	Soe Lynn
 * @version	1.0
 * 
 */
package com.sherminator.presentation.component;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableButtonCellRenderer implements TableCellRenderer {

	private String value;
	private JButton button;

	public TableButtonCellRenderer() {
		this("");
	}

	public TableButtonCellRenderer(String text) {
		value = text;
		button = new JButton(text);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		if(this.value.equals("")) {
			button.setText((String)value);
		}

		return button;
	}

}
