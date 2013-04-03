package se.chho.tested;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestCaseElement;
import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.eclipse.jdt.junit.model.ITestElement.Result;

/**
 * Inspiration form Brett Daniel 
 * http://www.brettdaniel.com/archives/2009/05/23/232945/
 *
 * @author Christoffer Holmstedt
 */

public class TestedTestRunListener extends TestRunListener {

	ArrayList<String> passedTests = new ArrayList<String>();
	ArrayList<String> failedTests = new ArrayList<String>(); // If not passed they fail.
	
	public TestedTestRunListener ()
	{
		// Do Nothing in here.
	}
	
	@Override
    public void sessionStarted(ITestRunSession session) {
		System.out.println("----- Session Started -----");
    }
	
	@Override
	public void testCaseStarted(ITestCaseElement testCaseElement)
	{
		// Do Nothing in here.
	}
	
	@Override
    public void testCaseFinished(ITestCaseElement testCaseElement)
	{
		String methodName = testCaseElement.getTestMethodName();
		if (testCaseElement.getTestResult(false) == Result.OK)
		{
			passedTests.add(methodName);
		}
		else
		{
			failedTests.add(methodName);
		}
    }
	
	@Override
    public void sessionFinished(ITestRunSession session)
	{
		for (String test : passedTests)
		{
			System.out.println(test);
		}
		
		// Wipe storage between JUnit runs.
		passedTests.clear();
		failedTests.clear();
    }
}
