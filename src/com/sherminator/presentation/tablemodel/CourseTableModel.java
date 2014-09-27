/*
 * @author	Soe Lynn
 * @version	1.0
 * 
 */

package com.sherminator.presentation.tablemodel;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import com.sherminator.model.Course;
import com.sherminator.presentation.component.TableButtonCellEditor;

public class CourseTableModel extends AbstractTableModel {

	public static final int COURSE_CODE_COLUMN = 0;
	public static final int COURSE_NAME_COLUMN = 1;
	
	private static String[] column_names = { "Course Code", "Course Name"};
	private List<Course> courses;
	private JTable tblCourses;
	
	public CourseTableModel(List<Course> courses, JTable tblCourses) {
		this.courses = courses;
		this.tblCourses = tblCourses;
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
		
		if(courses != null) {
			row_count = courses.size();
		}
		
		return row_count;
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		
		switch(col) {
		case COURSE_CODE_COLUMN:
			return courses.get(row).getCourseCode();
		case COURSE_NAME_COLUMN:
			return courses.get(row).getCourseName();
		}
		
		return "";
	}
	
	public Course getValueAt(int row) {
		return courses.get(row);
	}
	
	@Override
	public String getColumnName(int col) {
		return column_names[col];
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		String columnName = tblCourses.getColumnName(col);
		TableColumn column = tblCourses.getColumn(columnName);
		
		if(column.getCellEditor() instanceof TableButtonCellEditor) {
			return true;
		}
		
		return false;
	}


}