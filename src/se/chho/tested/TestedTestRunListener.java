package se.chho.tested;

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

	public String[] passedTests;
	public String[] failedTests; // If not passed they fail.
	
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
		String methodName = testCaseElement.getTestMethodName();
		String progress = testCaseElement.getProgressState().toString();
		System.out.println("Test Case Started: " + methodName + " : " + progress);
	}
	
	@Override
    public void testCaseFinished(ITestCaseElement testCaseElement)
	{
		String methodName = testCaseElement.getTestMethodName();
		String progress = testCaseElement.getProgressState().toString();
		if (testCaseElement.getTestResult(false) == Result.OK)
		{
			
		}
		else
		{
			
		}
		System.out.println("Test Case Finished: " + methodName + " : " + progress);
    }
	
	@Override
    public void sessionFinished(ITestRunSession session)
	{
		String testName = session.getTestRunName();
		
		try 
		{
			System.out.println(session.getLaunchedProject().getPackageFragmentRoots());
		}
		catch (JavaModelException e)
		{
			// Do Nothing
		}
		ITestElement[] testElements = session.getChildren();
		for (ITestElement element : testElements)
		{
			System.out.println(testName + ": " + element.getTestResult(false) + " : " + element.getElapsedTimeInSeconds());
		}
		System.out.println("Session Finished.");
    }
}
