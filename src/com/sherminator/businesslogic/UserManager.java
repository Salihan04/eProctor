/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.businesslogic;

import com.sherminator.model.User;

public interface UserManager {

	public boolean authenticate(User user);
	
}
