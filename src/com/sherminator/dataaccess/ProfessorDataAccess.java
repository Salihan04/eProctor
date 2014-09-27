/*
 * 
 * @author	Soe Lynn
 * @author	Sherman
 * @version	1.1
 * 
 */
package com.sherminator.dataaccess;

import java.util.List;

import com.sherminator.model.Professor;
import com.sherminator.model.Course;

public interface ProfessorDataAccess extends UserDataAccess {

	public boolean createProfessor(Professor professor);
	public boolean updateProfessor(Professor professor);
	public boolean deleteProfessor(Professor professor);
	public boolean profTeachCourse(Professor professor, Course course);
	public boolean updateProfTeachCourse(Professor professor, Course oldCourse, Course newCourse);
	public boolean deleteProfTeachCourse(Professor professor, Course course);
	public Professor getProfessorByUserName(String userName);
	public Professor getProfessorByStaffID(String staffID);
	public List<Professor> getAllProfessors();
	public List<Course> getCoursesByProfessorStaffID(String staffID);
	public List<Course> getCoursesByProfessorUserName(String userName);
}
