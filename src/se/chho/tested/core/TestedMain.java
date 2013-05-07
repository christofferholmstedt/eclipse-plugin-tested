package se.chho.tested.core;

import org.eclipse.jdt.core.IJavaProject;

import se.chho.tested.analyser.AtLeastOneNegativeIntegerAnalyser;
import se.chho.tested.analyser.EmptyStringAnalyser;
import se.chho.tested.analyser.OnlyMaximumIntegerAnalyser;
import se.chho.tested.analyser.OnlyMinimumIntegerAnalyser;
// import se.chho.tested.analyser.NullStringAnalyser;
import se.chho.tested.analyser.OnlyZeroIntegerAnalyser;


/**
 * 
 * @author Christoffer Holmstedt
 *
 */
public class TestedMain {
	
	public TestedMain (IJavaProject activeJavaProject)
	{
		/**
		 *  Step 1: Instantiate the manager
		 */
		AnalyserManagerObservableInterface analyserManager = new AnalyserManager(activeJavaProject);
		
		/**
		 * Step 2: Instantiate all the analysers
		 */
		AnalyserObserverInterface onlyZeroIntegerInput = new OnlyZeroIntegerAnalyser();
		AnalyserObserverInterface onlyMaximumIntegerInput = new OnlyMaximumIntegerAnalyser();
		AnalyserObserverInterface onlyMinimumIntegerInput = new OnlyMinimumIntegerAnalyser();
		AnalyserObserverInterface AtLeastOneNegativeIntegerInput = new AtLeastOneNegativeIntegerAnalyser();
		// AnalyserObserverInterface nullStringInput = new NullStringAnalyser();
		AnalyserObserverInterface emptyStringInput = new EmptyStringAnalyser();
		
		/**
		 * Step 3: Attach all instantiated analysers to the manager
		 */
		analyserManager.attach(onlyZeroIntegerInput);
		analyserManager.attach(onlyMaximumIntegerInput);
		analyserManager.attach(onlyMinimumIntegerInput);
		analyserManager.attach(AtLeastOneNegativeIntegerInput);
		// analyserManager.attach(nullStringInput);
		analyserManager.attach(emptyStringInput);

		/**
		 * Step 4: Run everything
		 */
		analyserManager.runAllAnalysers();
	}
}
