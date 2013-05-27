package se.chho.tested;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.junit.TestRunListener;
import org.eclipse.jdt.junit.model.ITestCaseElement;
import org.eclipse.jdt.junit.model.ITestRunSession;
import org.eclipse.jdt.junit.model.ITestElement.Result;

import se.chho.tested.core.TestedMain;
import se.chho.tested.helpers.MarkerHelper;

/**
 * TestRunListener, this is where TestED plugin listens to all test cases
 * and fetches the information if a test case has passed or failed.
 * 
 * It's also here that TestED plugin is started.
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
	 * When JUnit finishes all unit tests TestED Plugin is run.
	 * First, all previous problem markers are deleted and
	 * then if all test have passed Tested Plugin is run by
	 * instantiating TestedMain.
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
		   
		if (this.allTestPassed)
		{
			// By instantiating TestedMain the analyser is set up and all "analysers" are invoked.
			new TestedMain(session.getLaunchedProject());
		}
    }
}
