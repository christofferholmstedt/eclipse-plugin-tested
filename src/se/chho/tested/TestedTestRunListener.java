package se.chho.tested;

import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestCaseElement;
import org.eclipse.jdt.junit.model.ITestElement;
import org.eclipse.jdt.junit.model.ITestRunSession;

/**
 * Inspiration form Brett Daniel 
 * http://www.brettdaniel.com/archives/2009/05/23/232945/
 *
 * @author Christoffer Holmstedt
 */

public class TestedTestRunListener extends TestRunListener {

	public TestedTestRunListener ()
	{
		// Do Nothing in here.
	}
	
	@Override
    public void sessionStarted(ITestRunSession session) {
		System.out.println("Session Started.");
    }
	
	@Override
	public void testCaseStarted(ITestCaseElement testCaseElement)
	{
		System.out.println(testCaseElement.getProgressState());
		System.out.println("Test Case Started.");
	}
	
	@Override
    public void testCaseFinished(ITestCaseElement testCaseElement)
	{
		System.out.println("Test Case Finished.");
    }
	
	@Override
    public void sessionFinished(ITestRunSession session)
	{
		ITestElement[] testCases = session.getChildren();
		for (ITestElement testCase : testCases)
		{
			System.out.println(testCase.getTestResult(true) + " : " + testCase.getElapsedTimeInSeconds());
		}
		System.out.println("Session Finished.");
    }
}
