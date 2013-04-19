package se.chho.tested;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestCaseElement;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.eclipse.jdt.junit.model.ITestElement.Result;

import se.chho.tested.helpers.MarkerHelper;

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
		// Do Nothing in here.
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
		 
		   try {
			  IResource resource = session.getLaunchedProject().getCorrespondingResource();
		      resource.deleteMarkers(MarkerHelper.PASSED_TEST_MARKER_ID, true, IResource.DEPTH_INFINITE);
		      System.out.println("It Works");
		   } catch (CoreException e) {
		      // something went wrong
		   }
		// Run TestED plugin if all tests pass.
		if (!this.atLeastOneTestFailed)
		{
			FindInvokedMethods findMethods = new FindInvokedMethods(session.getLaunchedProject());
			findMethods.run();
		}
    }
}
