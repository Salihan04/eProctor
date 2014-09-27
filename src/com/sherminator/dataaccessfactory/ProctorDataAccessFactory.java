/*
 * @author	Salihan
 * @version	1.0
 * 
 */
package com.sherminator.dataaccessfactory;

import com.sherminator.dataaccess.ProctorDAO;
import com.sherminator.dataaccess.ProctorDataAccess;

public class ProctorDataAccessFactory implements DataAccessFactory {

	public ProctorDataAccess getDataAccess() {
		return new ProctorDAO();
	}
	
}
