/*
 * @author	Salihan
 * @version	1.0
 * 
 */

package com.sherminator.dataaccessfactory;

import com.sherminator.dataaccess.CourseDAO;
import com.sherminator.dataaccess.CourseDataAccess;
import com.sherminator.dataaccess.DataAccess;

public class CourseDataAccessFactory implements DataAccessFactory{

	@Override
	public CourseDataAccess getDataAccess() {
		
		return new CourseDAO();
	}
}
