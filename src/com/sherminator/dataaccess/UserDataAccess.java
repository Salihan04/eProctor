/*
 * 
 * @author	Soe Lynn
 * @author	Sherman
 * @version	1.1
 * 
 */

package com.sherminator.dataaccess;

import com.sherminator.model.User;

public interface UserDataAccess extends DataAccess {

	// will return true if username and password matches
	public boolean authenticate(User user);
	
}
