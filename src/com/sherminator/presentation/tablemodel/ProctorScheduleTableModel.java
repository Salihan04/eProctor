/*
 * @author	Salihan
 * @version	1.0
 * 
 */

package com.sherminator.presentation.tablemodel;

import java.sql.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import com.sherminator.businesslogic.ExamManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;
import com.sherminator.model.Student;
import com.sherminator.presentation.component.TableButtonCellEditor;

public class ProctorScheduleTableModel extends AbstractTableModel {

	public static final int COURSE_CODE_COLUMN = 0;
	public static final int EXAM_TITLE_COLUMN = 1;
	public static final int MATRIC_NO_COLUMN = 2;
	public static final int STUDENT_NAME_COLUMN = 3;
	public static final int EXAM_STATUS_COLUMN = 4;

	private static String[] column_names = { "Course Code", "Exam Title", "Matric No", "Name", " "};
	private List<Student> studentsByBookedDate;
	private List<Exam> examsByBookedDate;
	private List<Exam> examsAttendedByStudent;
	//private Date bookedDateTime1;
	//private Date bookedDateTime2;
	
	private ExamManager examManager = new ExamManager();
	private StudentManager studentManager = new StudentManager();
	private JTable tblProctorSchedule;
	
	public ProctorScheduleTableModel(List<Student> studentsByBookedDate, List<Exam> examsByBookedDate, JTable tblProctorSchedule) {
		//this.bookedDateTime1 = bookedDateTime1;
		//this.bookedDateTime2 = bookedDateTime2;
		this.studentsByBookedDate = studentsByBookedDate;
		this.examsByBookedDate = examsByBookedDate;
		this.tblProctorSchedule = tblProctorSchedule;
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

		if(examsByBookedDate != null) {
			row_count = examsByBookedDate.size();
		}

		return row_count;
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		
		switch(col) {
		case COURSE_CODE_COLUMN:
			return examsByBookedDate.get(row).getCourseCode();
		case EXAM_TITLE_COLUMN:
			return examsByBookedDate.get(row).getExamTitle();
		case MATRIC_NO_COLUMN:
			return studentsByBookedDate.get(row).getMatricNo();
		case STUDENT_NAME_COLUMN:
			return studentsByBookedDate.get(row).getName();
		case EXAM_STATUS_COLUMN:
			examsAttendedByStudent = examManager.getExamsAttendedByStudent(studentsByBookedDate.get(row));
			if(examsAttendedByStudent.contains(examsByBookedDate.get(row))) {
				return "Taken";
			}
			else
				return "Not Taken";
		default:
			return " ";
		}
	}
	
	public Exam getValueAt(int row) {
		return examsByBookedDate.get(row);
	}
	
	@Override
	public String getColumnName(int col) {
		return column_names[col];
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		String columnName = tblProctorSchedule.getColumnName(col);
		TableColumn column = tblProctorSchedule.getColumn(columnName);
		
		if(column.getCellEditor() instanceof TableButtonCellEditor) {
			return true;
		}
		
		return false;
	}

}
