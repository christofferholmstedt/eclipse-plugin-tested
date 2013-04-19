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
	
	private boolean atLeastOneTestFailed = false;
	
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
		if (testCaseElement.getTestResult(false) != Result.OK)
		{
			this.atLeastOneTestFailed = true;
		}
    }
	
	@Override
    public void sessionFinished(ITestRunSession session)
	{
		if (!this.atLeastOneTestFailed)
		{
			FindInvokedMethods findMethods = new FindInvokedMethods(session.getLaunchedProject());
			findMethods.run();
		}
		System.out.println("----- Session Finished -----");
		
    }
}
