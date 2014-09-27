/*
 * @author	Salihan
 * @version	1.0
 * 
 */

package com.sherminator.presentation.tablemodel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import com.sherminator.businesslogic.AnswerManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.model.Answer;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Question;
import com.sherminator.model.Student;
import com.sherminator.presentation.component.TableButtonCellEditor;

public class StudentTableModel extends AbstractTableModel {

	public static final int MATRIC_NO_COLUMN = 0;
	public static final int NAME_COLUMN = 1;
	public static final int MARKS_COLUMN = 2;
	
	private static String[] column_names = { "Matric No", "Name", "Marks"};
	private List<Student> students;
	private List<Integer> studentsMark;
	private JTable tblStudent;
	private StudentManager studentManager = new StudentManager();
	private Exam exam;
	
	public StudentTableModel(List<Student> students, JTable tblStudent, Exam exam) {
		this.students = students;
		this.tblStudent = tblStudent;
		this.exam = exam;
		
		studentsMark = new ArrayList<Integer>();
		for(Student s: students) {
			int mark = studentManager.calculateStudentResultForExam(s, exam);
			studentsMark.add(mark);
		}
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
		
		if(students != null) {
			row_count = students.size();
		}
		
		return row_count;
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		switch(col) {
		case MATRIC_NO_COLUMN:
			return students.get(row).getMatricNo();
		case NAME_COLUMN:
			return students.get(row).getName();
		case MARKS_COLUMN:
			//calculate marks functions
			float weightage = exam.getMaxMarks() / exam.getNo_of_questions();	
			int marksObtained = Math.round(studentsMark.get(row) * weightage);
			
			return marksObtained + " / " + exam.getMaxMarks();
		}
		
		return "";
	}
	
	public Student getValueAt(int row) {
		return students.get(row);
	}
	
	@Override
	public String getColumnName(int col) {
		return column_names[col];
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
			
		if(tblStudent.getCellEditor(row, col) instanceof TableButtonCellEditor) {
			return true;
		}
		
		return false;
	}


}