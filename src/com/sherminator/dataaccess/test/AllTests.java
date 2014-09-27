/*
 * @author	Nasruddin
 * @version	1.0
 * 
 */

package com.sherminator.dataaccess.test;

import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AnomalyReportTest.class, AnswerTest.class, CourseTest.class,
		ExamTest.class, ProctorTest.class, ProfessorTest.class,
		QuestionTest.class, StudentTest.class })
public class AllTests extends TestSuite {

}
