/*
 * @author	Salihan
 * @version	1.0
 * 
 */
package com.sherminator.dataaccessfactory;

import com.sherminator.dataaccess.DataAccess;
import com.sherminator.dataaccess.QuestionDAO;
import com.sherminator.dataaccess.QuestionDataAccess;

public class QuestionDataAccessFactory implements DataAccessFactory{

	@Override
	public QuestionDataAccess getDataAccess() {
		
		return new QuestionDAO();
	}

}
