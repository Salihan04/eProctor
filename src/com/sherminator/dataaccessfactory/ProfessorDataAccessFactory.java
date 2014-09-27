/*
 * @author	Salihan
 * @version	1.0
 * 
 */
package com.sherminator.dataaccessfactory;

import com.sherminator.dataaccess.ProfessorDAO;
import com.sherminator.dataaccess.ProfessorDataAccess;

public class ProfessorDataAccessFactory implements DataAccessFactory {

	public ProfessorDataAccess getDataAccess() {
		return new ProfessorDAO();
	}
	
}
