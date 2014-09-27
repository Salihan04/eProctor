/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.businesslogic;

import java.util.List;

import com.sherminator.dataaccess.ProfessorDataAccess;
import com.sherminator.dataaccessfactory.ProfessorDataAccessFactory;
import com.sherminator.model.Course;
import com.sherminator.model.Professor;
import com.sherminator.model.User;

public class ProfessorManager implements UserManager {

	private ProfessorDataAccessFactory factory;
	private ProfessorDataAccess professorDataAccess;
	
	public ProfessorManager() {
		factory = new ProfessorDataAccessFactory();
		professorDataAccess = factory.getDataAccess();
	}
	
	public boolean createProfessor(Professor professor){
		return professorDataAccess.createProfessor(professor);
	}
	
	public boolean updateProfessor(Professor professor){
		return professorDataAccess.updateProfessor(professor);
	}
	
	public boolean deleteProfessor(Professor professor){
		return professorDataAccess.deleteProfessor(professor);
	}
	
	public boolean authenticate(User user) {	
		return professorDataAccess.authenticate(user);
	}
	
	public boolean profTeachCourse(Professor professor, Course course){
		return professorDataAccess.profTeachCourse(professor, course);
	}
	
	public boolean updateProfTeachCourse(Professor professor, Course oldCourse, Course newCourse){
		return professorDataAccess.updateProfTeachCourse(professor, oldCourse, newCourse);
	}
	
	public boolean deleteProfTeachCourse(Professor professor, Course course){
		return professorDataAccess.deleteProfTeachCourse(professor, course);
	}
	
	public Professor getProfessorByUserName(String userName){
		return professorDataAccess.getProfessorByUserName(userName);
	}
	
	public Professor getProfessorByStaffID(String staffID){
		return professorDataAccess.getProfessorByStaffID(staffID);
	}
	
	public List<Professor> getAllProfessors(){
		return professorDataAccess.getAllProfessors();
	}
	
	public List<Course> getCoursesByProfessorStaffID(String staffID){
		return professorDataAccess.getCoursesByProfessorStaffID(staffID);
	}
	
	public List<Course> getCoursesByProfessorUserName(String userName){
		return professorDataAccess.getCoursesByProfessorUserName(userName);
	}
	
}
