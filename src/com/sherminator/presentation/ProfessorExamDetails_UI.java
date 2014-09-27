/*
 * 
 * @author	Gou Tong
 * @author	Huiting
 * @version	1.0
 * 
 */
package com.sherminator.presentation;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.sherminator.businesslogic.AnswerManager;
import com.sherminator.businesslogic.CourseManager;
import com.sherminator.businesslogic.ExamManager;
import com.sherminator.businesslogic.QuestionManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.controller.ProfExamDetailController;
import com.sherminator.model.AnomalyReport;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Exam;
import com.sherminator.model.Professor;
import com.sherminator.model.Question;
import com.sherminator.model.Student;
import com.sherminator.presentation.component.SherminatorDialog;
//import com.sherminator.presentation.ProfessorHome_UI.CourseTableCourseNameButton_Listener;
import com.sherminator.presentation.component.TableButtonCellEditor;
import com.sherminator.presentation.component.TableButtonCellRenderer;
import com.sherminator.presentation.tablemodel.CourseTableModel;
import com.sherminator.presentation.tablemodel.QuestionTableModel;
import com.sherminator.presentation.tablemodel.StudentTableModel;

public class ProfessorExamDetails_UI implements AbstractUI {

	private Professor professor;
	private Course course;
	private Exam exam;
	private List<Question> questions;
	private List<Student> students;
	private ProfExamDetailController profExamDetailController;

	private JFrame frame;
	private JLabel lblWelcome;
	private JButton btnAddQuestion;
	private JButton btnLogout;
	private JButton btnBack;
	private JButton btnTakeExam;
	private JButton btnAddStudent;
	private JLabel lblCourseCode;
	private JLabel lblCourseName;
	private JLabel lblExamTitle;
	private JLabel lblMaxMarks;
	private JLabel lblDuration;
	private JLabel lblStartTime;
	private JLabel lblEndTime;
	private JTextField txtCourseCode;
	private JTextField txtCourseName;
	private JTextField txtExamTitle;
	private JTextField txtMaxMarks;
	private JTextField txtDuration;
	private JTextField txtStartTime;
	private JTextField txtEndTime;
	private JTable tblQuestion;
	private JTable tblStudent;
	private JPanel panel1;
	private JPanel panel2;
	private JScrollPane scroller;
	private JScrollPane scroller2;

	private class StudentTableViewReportButton_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnViewReport = (JButton)e.getSource();
			JTable table = (JTable)btnViewReport.getParent();
			StudentTableModel studentTableModel = (StudentTableModel)table.getModel();
			Student student = studentTableModel.getValueAt(table.getSelectedRow());
			
			AnomalyReport report = profExamDetailController.getAnomalyReportByStudentAndExam(student, exam);
			
			if(report == null) {
				JOptionPane.showMessageDialog(null, "No Anomaly Report");
			}
			else {
				String reportDetails = "<html>"
						+ "Matric No. : " + student.getMatricNo() + "<br />"
						+ "Name       : " + student.getName() + "<br />"
						+ "Notes      : " + report.getNotes()
						+ "</html>";
				JOptionPane.showMessageDialog(null, reportDetails);
			}
		}
		
	}
	
	private class QuestionTableDeleteButton_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnQuestion = (JButton)e.getSource();
			JTable table = (JTable)btnQuestion.getParent();
			QuestionTableModel questionTableModel = (QuestionTableModel) table.getModel();
			Question question = questionTableModel.getValueAt(table.getSelectedRow());
			
			questions = profExamDetailController.removeQuestion(question);
			
			panel1.removeAll();
			setQuestionTab();
		}

	}
	
	private class StudentTableRemoveButton_Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton btnRemove = (JButton)e.getSource();
			JTable table = (JTable)btnRemove.getParent();
			StudentTableModel studentTableModel = (StudentTableModel) table.getModel();
			Student student = studentTableModel.getValueAt(table.getSelectedRow());
			
			students = profExamDetailController.removeStudent(student);
			
			panel2.removeAll();
			setStudentTab();			
		}
	}

	private class AddStudent_Listener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			Student student = SherminatorDialog.showStudentsDialog("Choose a Student", profExamDetailController.getAllStudents());
			
			if(student != null) {
				students = profExamDetailController.addStudent(student, course);
			}
			
			panel2.removeAll();
			setStudentTab();
			
			//JButton btnAddStudent = (JButton)e.getSource();
			
			//Need to have UI for the below part
			//StudentManager studentManager = new StudentManager();
			//studentManager.studentEnrollCourse(student, course);
			// Close the current UI
			//close();
		}
	}
	
	private class Back_Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			CourseManager courseManager = new CourseManager();
			profExamDetailController.gotoPreviousController(professor, course);
		}

	}

	public ProfessorExamDetails_UI(Professor professor, Course course, Exam exam,List<Question> questions,List<Student> students,ProfExamDetailController profExamDetailController) {
		this.professor = professor;
		this.course = course;
		this.exam = exam;
		this.questions = questions;
		this.students = students;
		this.profExamDetailController = profExamDetailController;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new Logout_Listener(this));
		btnLogout.setBounds(520, 19, 100, 23);
		frame.getContentPane().add(btnLogout);

		lblWelcome = new JLabel("Welcome, " + professor.getUserName());
		lblWelcome.setBounds(400, 23, 200, 14);
		frame.getContentPane().add(lblWelcome);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new Back_Listener());
		btnBack.setBounds(10, 19, 89, 23);
		frame.getContentPane().add(btnBack);

		lblCourseCode = new JLabel("Course Code");
		lblCourseCode.setBounds(20, 60, 90, 14);
		frame.getContentPane().add(lblCourseCode);

		txtCourseCode = new JTextField();
		txtCourseCode.setEnabled(false);
		txtCourseCode.setBounds(120, 57, 140, 20);
		frame.getContentPane().add(txtCourseCode);

		lblExamTitle = new JLabel("Exam Title");
		lblExamTitle.setBounds(20, 90, 90, 14);
		frame.getContentPane().add(lblExamTitle);

		txtExamTitle = new JTextField();
		txtExamTitle.setEnabled(false);
		txtExamTitle.setBounds(120, 87, 140, 20);
		frame.getContentPane().add(txtExamTitle);

		lblStartTime = new JLabel("Start Time");
		lblStartTime.setBounds(20, 120, 90, 14);
		frame.getContentPane().add(lblStartTime);

		txtStartTime = new JTextField();
		txtStartTime.setEnabled(false);
		txtStartTime.setBounds(120, 117, 140, 20);
		frame.getContentPane().add(txtStartTime);

		lblEndTime = new JLabel("End Time");
		lblEndTime.setBounds(20, 150, 90, 14);
		frame.getContentPane().add(lblEndTime);

		txtEndTime = new JTextField();
		txtEndTime.setEnabled(false);
		txtEndTime.setBounds(120, 147, 140, 20);
		frame.getContentPane().add(txtEndTime);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(20,170,590,250);
		panel1 = new JPanel();
		panel1.setLayout(null);
		tabbedPane.addTab("Questions",panel1);
		panel2 = new JPanel();
		panel2.setLayout(null);
		tabbedPane.addTab("Students",panel2);
		frame.getContentPane().add(tabbedPane);
		
		setQuestionTab();
		setStudentTab();
		
		updateData();
	}

	public void updateData() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy hh:mm a");

		txtCourseCode.setText(course.getCourseCode());
		//txtCourseName.setText(course.getCourseName());
		txtExamTitle.setText(exam.getExamTitle());
		//txtMaxMarks.setText(exam.getMaxMarks() + "");
		//txtDuration.setText(exam.getDuration() + "");
		txtStartTime.setText(format.format(exam.getStartDateTime()));
		txtEndTime.setText(format.format(exam.getEndDateTime()));
	}

	public void close(){
		frame.dispose();
	}

	public void show() {
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	private void setQuestionTab() {
		tblQuestion = new JTable();
		QuestionTableModel questionTableModel = new QuestionTableModel(questions, tblQuestion);
		tblQuestion.setModel(questionTableModel);
		tblQuestion.setPreferredSize(new Dimension(590,1000));
		TableColumn answerColumn = tblQuestion.getColumn("Choices");
		answerColumn.setCellRenderer(new MyCellRenderer());
		scroller = new JScrollPane(tblQuestion);
		scroller.setBounds(0,30,585, 195);
		panel1.add(scroller);

		TableColumn deleteColumn = tblQuestion.getColumn(" ");
		deleteColumn.setCellRenderer(new TableButtonCellRenderer());
		deleteColumn.setCellEditor(new TableButtonCellEditor(new QuestionTableDeleteButton_Listener()));
	}
	
	private void setStudentTab() {
		btnAddStudent = new JButton("Add Student");
		btnAddStudent.addActionListener(new AddStudent_Listener());
		btnAddStudent.setBounds(10, 4, 100, 22);
		panel2.add(btnAddStudent);
		
		tblStudent = new JTable();
		StudentTableModel studentTableModel = new StudentTableModel(students, tblStudent, exam);
		tblStudent.setModel(studentTableModel);
		tblStudent.setPreferredSize(new Dimension(590,1000));
		scroller2 = new JScrollPane(tblStudent);
		scroller2.setBounds(0,30,585,195);
		panel2.add(scroller2);
		
		TableColumn removeColumn = new TableColumn();
		removeColumn.setHeaderValue("");
		removeColumn.setModelIndex(tblStudent.getColumnCount());
		removeColumn.setCellRenderer(new TableButtonCellRenderer("Remove"));
		removeColumn.setCellEditor(new TableButtonCellEditor("Remove", new StudentTableRemoveButton_Listener()));
		tblStudent.addColumn(removeColumn);
		
		TableColumn viewReportColumn = new TableColumn();
		viewReportColumn.setHeaderValue("");
		viewReportColumn.setModelIndex(tblStudent.getColumnCount());
		viewReportColumn.setCellRenderer(new TableButtonCellRenderer("View Report"));
		viewReportColumn.setCellEditor(new TableButtonCellEditor("View Report", new StudentTableViewReportButton_Listener()));
		tblStudent.addColumn(viewReportColumn);
		
	}
		
	private class MyCellRenderer extends JTextArea implements TableCellRenderer {
	     public MyCellRenderer() {
	       setLineWrap(true);
	       setWrapStyleWord(true);
	    }

	   public Component getTableCellRendererComponent(JTable table, Object
	           value, boolean isSelected, boolean hasFocus, int row, int column) {
	       setText((String) value);//or something in value, like value.getNote()...
	       setSize(table.getColumnModel().getColumn(column).getWidth(),
	               getPreferredSize().height);
	       if (table.getRowHeight(row) != getPreferredSize().height) {
	               table.setRowHeight(row, getPreferredSize().height);
	       }
	       return this;
	   }
	}

}
