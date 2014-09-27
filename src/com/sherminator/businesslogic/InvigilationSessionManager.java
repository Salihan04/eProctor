/*
 * @author	Ang Weeliang
 * @version	1.0
 * 
 */
package com.sherminator.businesslogic;

import com.sherminator.dataaccess.InvigilationSessionDataAccess;
import com.sherminator.dataaccessfactory.InvigilationSessionDataAccessFactory;
import com.sherminator.model.InvigilationSession;
import com.sherminator.model.Proctor;

public class InvigilationSessionManager {
	
	private InvigilationSessionDataAccessFactory factory;
	private InvigilationSessionDataAccess dataaccess;
	
	public InvigilationSessionManager() {
		factory = new InvigilationSessionDataAccessFactory();
		dataaccess = factory.getDataAccess();
	}

	public boolean makeAvailable(Proctor proctor, String ip_address) {
		return dataaccess.makeAvailable(proctor, ip_address);
	}
	
	public boolean makeUnavailable(Proctor proctor) {
		return dataaccess.makeUnavailable(proctor);
	}
	
	public boolean disablePort(Proctor proctor, int port) {
		return dataaccess.disablePort(proctor, port);
	}
	
	public InvigilationSession getAvailableInvigilator() {
		return dataaccess.getAvailableInvigilator();
	}
	
}
