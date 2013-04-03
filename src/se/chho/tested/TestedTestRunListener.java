package se.chho.tested;

import java.util.ArrayList;

import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestCaseElement;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.eclipse.jdt.junit.model.ITestElement.Result;

/**
 * Inspiration form Brett Daniel 
 * http://www.brettdaniel.com/archives/2009/05/23/232945/
 *
 * @author Christoffer Holmstedt
 */

public class TestedTestRunListener extends TestRunListener {

	private ArrayList<ITestCaseElement> passedTests = new ArrayList<ITestCaseElement>();
	private ArrayList<ITestCaseElement> failedTests = new ArrayList<ITestCaseElement>(); // If not passed they fail.
	
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
		if (testCaseElement.getTestResult(false) == Result.OK)
		{
			passedTests.add(testCaseElement);
		}
		else
		{
			failedTests.add(testCaseElement);
		}
    }
	
	@Override
    public void sessionFinished(ITestRunSession session)
	{
		FindInvokedMethods analyser = new FindInvokedMethods(passedTests, session.getLaunchedProject());
		analyser.run();
		System.out.println("----- Session Finished -----");
		// Wipe storage between JUnit runs.
		passedTests.clear();
		failedTests.clear();
    }
}
