/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.businesslogic;

import java.util.List;

import com.sherminator.dataaccess.ProctorDataAccess;
import com.sherminator.dataaccessfactory.ProctorDataAccessFactory;
import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Student;
import com.sherminator.model.User;

public class ProctorManager implements UserManager {

	private ProctorDataAccessFactory factory;
	private ProctorDataAccess proctorDataAccess;
	
	public ProctorManager() {
		factory = new ProctorDataAccessFactory();
		proctorDataAccess = factory.getDataAccess();
	}
	
	public boolean createProctor(Proctor proctor){
		return proctorDataAccess.createProctor(proctor);
	}
	public boolean updateProctor(Proctor proctor){
		return proctorDataAccess.updateProctor(proctor);
	}
	
	public boolean deleteProctor(Proctor proctor){
		return proctorDataAccess.deleteProctor(proctor);
	}
	
	public boolean verifiedStudentExam(Proctor proctor, Student student, Exam exam) {
		return proctorDataAccess.verifiedStudentExam(proctor, student, exam);
	}
	
	public boolean authenticate(User user) {
		return proctorDataAccess.authenticate(user);
	}
	
	public Proctor getProctorByUserName(String userName){
		return proctorDataAccess.getProctorByUserName(userName);
	}
	
	public List<Proctor> getProctorByName(String name){
		return proctorDataAccess.getProctorByName(name);
	}
	
	public List<Proctor> getProctors(){
		return proctorDataAccess.getProctors();
	}
	
}
