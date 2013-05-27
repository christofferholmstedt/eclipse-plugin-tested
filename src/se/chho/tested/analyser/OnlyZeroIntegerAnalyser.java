package se.chho.tested.analyser;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.JavaModelException;

import se.chho.tested.core.AnalyserManagerObservableInterface;
import se.chho.tested.core.AnalyserObserverInterface;
import se.chho.tested.core.FoundMethod;
import se.chho.tested.core.MethodInvocation;
import se.chho.tested.helpers.LinenumberHelper;
import se.chho.tested.helpers.MarkerHelper;
/**
 * Analyser that checks whether a function with integer input has a test case 
 * where only zeros as the only input value.
 * 
 * @author Christoffer Holmstedt
 */
public class OnlyZeroIntegerAnalyser implements AnalyserObserverInterface {

	private ArrayList<FoundMethod> methods = new ArrayList<FoundMethod>();
	private boolean onlyZeroInputTestedAtLeastOnes = false; 
	
	@Override
	public void runAnalysis(AnalyserManagerObservableInterface Observable) {
		methods = Observable.getFoundMethods();
		
		/**
		 * Step 1: Loop through all non test methods
		 */
		for (FoundMethod method : methods) {
			if (method.hasOnlyIntInput())
			{
				/**
				 * Step 2: Loop through all method invocations for the current non test method.
				 */
				for (MethodInvocation methodInv : method.getMethodInvocations())
				{
					/**
					 * Step 3: Start with the notion that all input values is expected to be zero.
					 */
					boolean allZeroInput = true;
							
					/**
					 * Step 4: Loop through all input parameters for the current method invocation
					 */
					for (Integer number : methodInv.getIntParameters())
					{
						/**
						 * Step 5: If any value is other than zero set allZeroInput to false.
						 */
						if (number.intValue() != 0)
						{
							allZeroInput = false;
						}
					}
					/**
					 * Step 6: When all input parameters have been searched for and 
					 * the variable "allZeroInput" still is true then set class-wider variable
					 * can be set to true. 
					 */
					if (allZeroInput)
						this.onlyZeroInputTestedAtLeastOnes = true;
				}
				
				/**
				 * Step 7: When all method invocations has been examined add marker if
				 * this non test method hasn't been tested with all zero value.
				 */
				if (!this.onlyZeroInputTestedAtLeastOnes)
				{
					if (method.isInvokedByMoreThanTwoTests())
					  {
						  try {
							IFile file = (IFile)method.getMethod().getCompilationUnit().getCorrespondingResource();
							int linenumber = LinenumberHelper.getMethodLineNumber(method.getMethod().getCompilationUnit(), method.getMethod());
							String message = "What happens if you test the method \"" + method.getMethod().getElementName() + "\" with only zeros as input?";
							
							// Add new marker
							MarkerHelper.addMarker(file, linenumber, message);
								
						} catch (JavaModelException e) {
							// Auto-generated catch block
							e.printStackTrace();
						}
					  }
				}
					
			}
		}
				
	}
}
