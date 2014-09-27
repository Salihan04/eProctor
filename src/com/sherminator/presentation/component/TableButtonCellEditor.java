/*
 * @author	Soe Lynn
 * @version	1.0
 * 
 */

package com.sherminator.presentation.component;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class TableButtonCellEditor extends AbstractCellEditor implements TableCellEditor {

	private String value;
	private JButton button;
	
	public TableButtonCellEditor(ActionListener listener) {
		this("", listener);
	}
	
	public TableButtonCellEditor(String text, ActionListener listener) {
		value = text;
		button = new JButton(text);
		button.addActionListener(listener);
	}
	
	@Override
	public Object getCellEditorValue() {
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {		
		
		if(this.value.equals("")) {
			button.setText((String)value);
		}
		
		return button;
	}

}
