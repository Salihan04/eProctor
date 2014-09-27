/*
 * @author	Salihan
 * @version	1.0
 * 
 */
package com.sherminator.presentation.tablemodel;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.presentation.component.TableButtonCellEditor;

public class ExamTableModel extends AbstractTableModel {

	public static final int EXAM_TITLE_COLUMN = 0;
	public static final int MAX_MARKS_COLUMN = 1;
	public static final int DURATION_COLUMN = 2;
	public static final int START_DATE_TIME_COLUMN = 3;
	public static final int END_DATE_TIME_COLUMN = 4;
	
	private static String[] column_names = { "Exam Title", "Max Marks", "Duration", "Start Date Time", "End Date Time"};
	private List<Exam> exams;
	private JTable tblExam;
	
	public ExamTableModel(List<Exam> exams, JTable tblExam) {
		this.exams = exams;
		this.tblExam = tblExam;
	}
	
	@Override
	public int getColumnCount() {
		
		int column_count = 0;
		
		if(column_names != null) {
			column_count = column_names.length;
		}
		
		return column_count;
	}

	@Override
	public int getRowCount() {
		
		int row_count = 0;
		
		if(exams != null) {
			row_count = exams.size();
		}
		
		return row_count;
	}

	@Override
	public Object getValueAt(int row, int col) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		switch(col) {
		case EXAM_TITLE_COLUMN:
			return exams.get(row).getExamTitle();
		case MAX_MARKS_COLUMN:
			return exams.get(row).getMaxMarks();
		case DURATION_COLUMN:
			return exams.get(row).getDuration();
		case START_DATE_TIME_COLUMN:
			return dateFormat.format(exams.get(row).getStartDateTime());
		case END_DATE_TIME_COLUMN:
			return dateFormat.format(exams.get(row).getEndDateTime());
		}
		
		return "";
	}
	
	public Exam getValueAt(int row) {
		return exams.get(row);
	}
	
	@Override
	public String getColumnName(int col) {
		return column_names[col];
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		if(tblExam.getCellEditor(row, col) instanceof TableButtonCellEditor) {
			return true;
		}
		
		return false;
	}

}