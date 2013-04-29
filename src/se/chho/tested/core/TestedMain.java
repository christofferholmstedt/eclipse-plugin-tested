package se.chho.tested.core;

import org.eclipse.jdt.core.IJavaProject;

import se.chho.tested.analyser.AnalyserManager;
import se.chho.tested.analyser.AnalyserManagerObservableInterface;

/**
 * This class goes through all test methods in given scope (usually a project not the 
 * entire eclipse workspace) and finds all methods that has been invoked by the tests.
 * 
 * @author Christoffer Holmstedt
 *
 */
public class TestedMain {
	

	
	public TestedMain (IJavaProject activeJavaProject)
	{

		AnalyserManagerObservableInterface analyserManager = new AnalyserManager(activeJavaProject);
	}
}
