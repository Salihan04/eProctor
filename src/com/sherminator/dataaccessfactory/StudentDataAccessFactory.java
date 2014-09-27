/*
 * @author	Salihan
 * @version	1.0
 * 
 */
package com.sherminator.dataaccessfactory;

import com.sherminator.dataaccess.StudentDAO;
import com.sherminator.dataaccess.StudentDataAccess;

public class StudentDataAccessFactory implements DataAccessFactory {

	public StudentDataAccess getDataAccess() {
		return new StudentDAO();		
	}
	
}
