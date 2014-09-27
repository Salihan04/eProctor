/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.controller;

import com.sherminator.businesslogic.ExamManager;
import com.sherminator.model.Exam;

public class ProfEditExamController extends AbstractController{
	//declare UI and other relevant info here
	private Exam examRecord;
	
	public ProfEditExamController(Exam exam){
		examRecord=exam;
	}
	
	@Override
	public void showPresentation() {
		//shows the form for editing exam, pass in examRecord
	}
	
	public void updateExamDetails(Exam exam){
		//view can update exam details here after prof saves edits
		//updates the database first then go back to ProfCourseDetailController
		examRecord=exam;
		ExamManager examMgr = new ExamManager();
		examMgr.updateExam(examRecord);
		gotoNextController();
	}
	
	public void cancelEdit(){
		//invoked by listener detecting window close event
		gotoNextController();
	}
	
	@Override
	public void gotoNextController() {
		//Cancellation of this form/submission of edit will invoke this to go back to ProfCourseDetailController
		//close ui
		//pass in prof,course details from stack to ProfCourseDetailController(prof,course);
		//ProfCourseDetailController profCDCtrl= new ProfCourseDetailController(prof,course);
	}
}