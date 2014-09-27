/*
 * @author	Soe Lynn
 * @version	1.0
 * 
 */
package com.sherminator.dataaccessfactory;

import com.sherminator.dataaccess.AnomalyReportDAO;
import com.sherminator.dataaccess.AnomalyReportDataAccess;

public class AnomalyReportDataAccessFactory implements DataAccessFactory {

	@Override
	public AnomalyReportDataAccess getDataAccess() {
		
		return new AnomalyReportDAO();
	}
}