/*
 * @author	Salihan
 * @version	1.0
 * 
 */
package com.sherminator.dataaccessfactory;

import com.sherminator.dataaccess.InvigilationSessionDAO;
import com.sherminator.dataaccess.InvigilationSessionDataAccess;

public class InvigilationSessionDataAccessFactory implements DataAccessFactory {

	@Override
	public InvigilationSessionDataAccess getDataAccess() {
		return new InvigilationSessionDAO();
	}

	
	
}
