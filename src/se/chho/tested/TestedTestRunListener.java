package se.chho.tested;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestCaseElement;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.eclipse.jdt.junit.model.ITestElement.Result;

import se.chho.tested.helpers.MarkerHelper;

/**
 * @author Christoffer Holmstedt
 */

public class TestedTestRunListener extends TestRunListener {
	
	private boolean allTestPassed = true;
	
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
	
	/***
	 * After each test case see if it failed, if it fails set 
	 * this.allTestPassed to false to indicate that Tested Plugin should
	 * not make any analysis yet.
	 * 
	 * See Github issue number 4.
	 * https://github.com/christofferholmstedt/eclipse-plugin-tested/issues/4
	 */
	@Override
    public void testCaseFinished(ITestCaseElement testCaseElement)
	{
		if (testCaseElement.getTestResult(false) != Result.OK)
		{
			this.allTestPassed = false;
		}
    }
	
	/***
	 * When JUnit finishes all unit tests TestED Plugin is ran.
	 * First all previous set problem markers are deleted and
	 * then if all test have passed the plugin is started by instantiating
	 * TestedMain calling its run() method.
	 * 
	 * See Github issue number 4.
	 * https://github.com/christofferholmstedt/eclipse-plugin-tested/issues/4
	 */
	@Override
    public void sessionFinished(ITestRunSession session)
	{
		 
		   try {
			  IResource resource = session.getLaunchedProject().getCorrespondingResource();
		      resource.deleteMarkers(MarkerHelper.PASSED_TEST_MARKER_ID, true, IResource.DEPTH_INFINITE);
		   } catch (CoreException e) {
		      // something went wrong
		   }
		// Run TestED plugin if all tests pass.
		if (this.allTestPassed)
		{
			TestedMain findMethods = new TestedMain(session.getLaunchedProject());
			findMethods.run();
		}
    }
}
