/*
 * 
 * @author	Soe Lynn
 * @author	Sherman
 * @version	1.1
 * 
 */

package com.sherminator.dataaccess;

import com.sherminator.model.InvigilationSession;
import com.sherminator.model.Proctor;

public interface InvigilationSessionDataAccess extends DataAccess {

	public boolean makeAvailable(Proctor proctor, String ip_address);
	public boolean makeUnavailable(Proctor proctor);
	
	public boolean disablePort(Proctor proctor, int port);
	
	public InvigilationSession getAvailableInvigilator();
	
}
