/*
 * @author	Salihan
 * @version	1.0
 * 
 */

package com.sherminator.presentation.tablemodel;

import java.util.Hashtable;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import com.sherminator.businesslogic.AnswerManager;
import com.sherminator.model.Answer;
import com.sherminator.model.Answer;
import com.sherminator.model.Course;
import com.sherminator.model.Question;
import com.sherminator.presentation.component.TableButtonCellEditor;

public class QuestionTableModel extends AbstractTableModel {

	public static final int QUESTION_COLUMN = 0;
	public static final int CORRECT_ANSWER_COLUMN = 1;
	public static final int CHOICES_COLUMN = 2;
	public static final int REMOVE_COLUMN = 3;
	
	private static String[] column_names = { "Question", "Correct Answer", "Choices", " "};
	private List<Question> questions;
	private Hashtable<Question, List<Answer>> questionAnswers;
	private JTable tblQuestions;
	
	public QuestionTableModel(List<Question> questions, JTable tblQuestions) {
		this.questions = questions;
		this.tblQuestions = tblQuestions;
		
		questionAnswers = new Hashtable<Question, List<Answer>>();
		AnswerManager answerManager = new AnswerManager();
		
		for(Question q: questions) {
			List<Answer> answers = answerManager.getAnswersByQuestion(q);
			questionAnswers.put(q, answers);
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
		
		if(questions != null) {
			row_count = questions.size();
		}
		
		return row_count;
	}

	@Override
	public Object getValueAt(int row, int col) {
		
	
		switch(col) {
		case QUESTION_COLUMN:
			return questions.get(row).getQuestionSentence();
		case CORRECT_ANSWER_COLUMN:
			return questions.get(row).getCorrectAnswer().getAnswerSentence();
		case CHOICES_COLUMN:
			String choices = "";
			
			Question question = questions.get(row);
			int index = 0;
			for(Answer answer : questionAnswers.get(question)) {
				choices = choices + ++index + ") " + answer.getAnswerSentence() + "\n";
			}
			choices = choices.trim();
			return choices;
		case REMOVE_COLUMN:
			return "Delete";
		default:
			return " ";
		}
	}
	
	public Question getValueAt(int row) {
		return questions.get(row);
	}
	
	@Override
	public String getColumnName(int col) {
		return column_names[col];
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		String columnName = tblQuestions.getColumnName(col);
		TableColumn column = tblQuestions.getColumn(columnName);
		
		if(column.getCellEditor() instanceof TableButtonCellEditor) {
			return true;
		}
		
		return false;
	}


}