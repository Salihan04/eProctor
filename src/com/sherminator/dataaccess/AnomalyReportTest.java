package com.sherminator.dataaccess;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sherminator.model.AnomalyReport;
import com.sherminator.model.Proctor;

import junit.framework.TestCase;

public class AnomalyReportTest extends TestCase {
	public void testCreateAnomalyReport(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, 3, 24, 7, 29, 0);
		java.util.Date tempDate = calendar.getTime();
		Date startDateTime = new java.sql.Date(tempDate.getTime());
		AnomalyReportDAO test = new AnomalyReportDAO();
		ProctorDAO proct = new ProctorDAO();
		Boolean result1 = false;
		Boolean result2 = false;
		Boolean result3 = false;
		Boolean result4 = false;
		Boolean result5 = false;
		Boolean result6 = false;
		Boolean result7 = false;
		
		//dummy test variables
		Proctor proctor = new Proctor("Limbo", "root", "Limbo Jack");
		proct.createProctor(proctor);
		AnomalyReport anom1 = new AnomalyReport(startDateTime, "please ignore this report", "Nasruddin", "U1221221L", 2);
		result1 = test.createAnomalyReport(anom1);
		
		//setAnomalyReportID
		test.setAnomalyReportID(anom1);
		System.out.println(anom1.getReportID());
		result2 = (anom1.getReportID()!=-1);
		
		//updateAnomalyReport
		anom1.setUsername("Limbo");
		result3 = test.updateAnomalyReport(anom1);
		
		
		//getAnomalyReportByDate
		List<AnomalyReport> anomList = test.getAnomalyReportByDate(startDateTime);
		for(AnomalyReport anom: anomList){
			if(anom.getReportID()==anom1.getReportID()){
				result4=true;
			}
		}
		
		
		//getAnomalyReportByProctor
		result5 = true;
		anomList = test.getAnomalyReportByProctor(proctor);
		for(AnomalyReport anom: anomList){
			if(!anom.getUsername().equals(proctor.getUserName())){
				result5=false;
				break;
			}
		}
		
		//getanomalyReports

		anomList = test.getAnomalyReports();
		for(AnomalyReport anom: anomList){
			if(anom.getReportID()==anom1.getReportID()){
				result6=true;
			}
		}
		
		result7 = test.deleteAnomalyReport(anom1);
		proct.deleteProctor(proctor);
		
		
		assertEquals("true", result1.toString());
		assertEquals("true", result2.toString());
		assertEquals("true", result3.toString());
		assertEquals("true", result4.toString());
		assertEquals("true", result5.toString());
		assertEquals("true", result6.toString());
		assertEquals("true", result7.toString());
		System.out.println("all working");
		
	}
}
