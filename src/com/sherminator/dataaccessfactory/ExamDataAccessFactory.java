/*
 * @author	Salihan
 * @version	1.0
 * 
 */

package com.sherminator.dataaccessfactory;

import com.sherminator.dataaccess.DataAccess;
import com.sherminator.dataaccess.ExamDAO;
import com.sherminator.dataaccess.ExamDataAccess;

public class ExamDataAccessFactory implements DataAccessFactory{

	@Override
	public ExamDataAccess getDataAccess() {
		
		return new ExamDAO();
	}

}
