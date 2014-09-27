/*
 * @author	Huiting
 * @version	1.0
 * 
 */

package com.sherminator.dataaccess.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.sherminator.dataaccess.ProctorDAO;
import com.sherminator.model.Proctor;

public class ProctorTest extends TestCase {

	@Test
	public void test() {
		Boolean createProctor = false;
		Boolean getProctorByName = false;
		Boolean getProctorByUsername = false;
		Boolean getProctors = false;
		Boolean updateProctor = false;
		Boolean deleteProctor = false;
		
		List<Proctor> proctors = new ArrayList<Proctor>();
		Proctor tempProctor = null;
		
		ProctorDAO test = new ProctorDAO();
		Proctor proctor = new Proctor("testProctor","root", "TESTPROCTOR");

		//createProctor
		createProctor = test.createProctor(proctor);
		
		//getProctorByName
		proctors = test.getProctorByName(proctor.getName());
		for(Proctor p: proctors){
			if(p.getName().equals(proctor.getName())
					&& p.getPassword().equals(proctor.getPassword())
					&& p.getProctorID()==proctor.getProctorID()
					&&p.getUserName().equals(proctor.getUserName())){
				getProctorByName=true;
				
			}
		}
		
		//getProctorByUsername
		tempProctor = test.getProctorByUserName(proctor.getUserName());
		for(Proctor p: proctors){
			if(p.getName().equals(proctor.getName())
					&& p.getPassword().equals(proctor.getPassword())
					&& p.getProctorID()==proctor.getProctorID()
					&&p.getUserName().equals(proctor.getUserName())){
				getProctorByUsername=true;
				
			}
		}
		
		//getProctors
		proctors = test.getProctors();
		for(Proctor p: proctors){
			if(p.getName().equals(proctor.getName())
					&& p.getPassword().equals(proctor.getPassword())
					&& p.getProctorID()==proctor.getProctorID()
					&&p.getUserName().equals(proctor.getUserName())){
				getProctors=true;
				
			}
		}
		
		//updateProctor
		proctor.setPassword("root2");
		test.updateProctor(proctor);
		proctors = test.getProctors();
		for(Proctor p: proctors){
			if(p.getName().equals(proctor.getName())
					&& p.getPassword().equals(proctor.getPassword())
					&& p.getProctorID()==proctor.getProctorID()
					&&p.getUserName().equals(proctor.getUserName())){
				updateProctor =true;
			}
		}
		
		//deleteProctor
		deleteProctor = test.deleteProctor(proctor);
		
		//test results
		assertEquals("true", createProctor.toString());
		System.out.println("proctorDAO.createProctor(Proctor proctor) passed");
		assertEquals("true", getProctorByName.toString());
		System.out.println("proctorDAO.getProctorByName(Proctor proctor) passed");
		assertEquals("true", getProctorByUsername.toString());
		System.out.println("proctorDAO.getProctorByUsername(String username) passed");
		assertEquals("true", getProctors.toString());
		System.out.println("proctorDAO.getProctors() passed");
		assertEquals("true", updateProctor.toString());
		System.out.println("proctorDAO.updateProctor(Proctor proctor) passed");
		
		assertEquals("true", deleteProctor.toString());
		System.out.println("proctorDAO.deleteProctor(Proctor proctor) passed");
		System.out.println("All working in proctorDAO");
	}

}
