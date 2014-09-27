/*
 * @author	Salihan
 * @version	1.0
 * 
 */

package com.sherminator.dataaccessfactory;

import com.sherminator.dataaccess.AnswerDAO;
import com.sherminator.dataaccess.AnswerDataAccess;
import com.sherminator.dataaccess.DataAccess;

public class AnswerDataAccessFactory implements DataAccessFactory{

	@Override
	public AnswerDataAccess getDataAccess() {
		
		return new AnswerDAO();
	}
}
