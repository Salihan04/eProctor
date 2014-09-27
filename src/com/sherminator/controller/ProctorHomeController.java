/*
 * @author	Soe Lynn
 * @version	1.0
 * 
 */
package com.sherminator.controller;

import com.sherminator.businesslogic.ExamManager;
import com.sherminator.businesslogic.InvigilationSessionManager;
import com.sherminator.businesslogic.ProctorManager;
import com.sherminator.businesslogic.StudentManager;
import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Student;
import com.sherminator.presentation.ProctorHome_UI;

public class ProctorHomeController extends AbstractController {
	
	private Proctor proctor;
	private StudentManager studentMgr;
	private ExamManager examMgr;
	private ProctorManager proctorMgr;
	private InvigilationSessionManager invigilationSessionMgr;
	
	private ProctorHome_UI proctorHome_UI;
	
	public ProctorHomeController(Proctor proctor) {
		
		this.proctor = proctor;
		
		this.examMgr = new ExamManager();
		this.studentMgr = new StudentManager();
		this.proctorMgr = new ProctorManager();
		this.invigilationSessionMgr = new InvigilationSessionManager();		
	}

	@Override
	public void showPresentation() {
		
		proctorHome_UI = new ProctorHome_UI(proctor, this);
		proctorHome_UI.show();
	}

	@Override
	public void gotoNextController() {
		
		ProctorScheduleController proctorScheduleController = new ProctorScheduleController(proctor);
		proctorScheduleController.showPresentation();
	}
	
	public void goToPreviousController() {
		LoginController loginController = new LoginController();
		loginController.showPresentation();
		proctorHome_UI.close();
	}

	public Exam getExam(int examID) {
		return examMgr.getExamByExamID(examID);
	}
	
	public Student getStudent(String matricNo) {
		return studentMgr.getStudentByMatricNo(matricNo);
	}
	
	public void disablePort(Proctor proctor, int port) {
		invigilationSessionMgr.disablePort(proctor, port);
	}
	
	public void makeUnavailable(Proctor proctor) {
		invigilationSessionMgr.makeUnavailable(proctor);
	}
	
	public void makeAvailable(Proctor proctor, String ip_address) {
		invigilationSessionMgr.makeAvailable(proctor, ip_address);
	}
	
	public void verifiedStudentExam(Proctor proctor, Student student, Exam exam) {
		proctorMgr.verifiedStudentExam(proctor, student, exam);
	}

}
