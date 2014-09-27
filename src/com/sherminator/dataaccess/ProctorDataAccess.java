/*
 * 
 * @author	Soe Lynn
 * @author	Sherman
 * @version	1.1
 * 
 */
package com.sherminator.dataaccess;

import java.util.List;

import com.sherminator.model.Exam;
import com.sherminator.model.Proctor;
import com.sherminator.model.Student;
import com.sherminator.model.User;

public interface ProctorDataAccess extends UserDataAccess{

	public boolean createProctor(Proctor proctor);
	public boolean updateProctor(Proctor proctor);
	public boolean deleteProctor(Proctor proctor);
	public boolean verifiedStudentExam(Proctor proctor, Student student, Exam exam);
	public Proctor getProctorByUserName(String userName);
	public List<Proctor> getProctorByName(String name);
	public List<Proctor> getProctors();
	public boolean authenticate(User user);
}
