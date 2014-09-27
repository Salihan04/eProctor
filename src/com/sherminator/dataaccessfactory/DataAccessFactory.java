/*
 * @author	Soe Lynn
 * @version	1.0
 * 
 */
package com.sherminator.dataaccessfactory;

import com.sherminator.dataaccess.DataAccess;

public interface DataAccessFactory {
	
	// Return the data access object
	public DataAccess getDataAccess();
	
}
