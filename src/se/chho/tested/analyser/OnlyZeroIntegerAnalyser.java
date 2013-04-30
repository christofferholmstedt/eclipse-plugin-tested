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

public class OnlyZeroIntegerAnalyser implements AnalyserObserverInterface {

	private ArrayList<FoundMethod> methods = new ArrayList<FoundMethod>();
	private boolean onlyZeroInputTestedAtLeastOnes = false; 
	
	@Override
	public void runAnalysis(AnalyserManagerObservableInterface Observable) {
		methods = Observable.getFoundMethods();
		
		// 
		for (FoundMethod method : methods) {
			if (method.hasOnlyIntInput())
			{
				for (MethodInvocation methodInv : method.getMethodInvocations())
				{
					// Step 1: Start with the notion that all input values is expected to be zero.
					boolean allZeroInput = true;
							
					// Step 2: Loop through all input parameters
					for (Integer number : methodInv.getIntParameters())
					{
						// Step 3: If any value is other than zero set allZeroInput to false.
						if (number.intValue() != 0)
						{
							allZeroInput = false;
						}
					}
					// Step 4: When all input parameters have been searched for and 
					// "allZeroInput" still is true then set class-wider variable
					if (allZeroInput)
						this.onlyZeroInputTestedAtLeastOnes = true;
				}
					
			}
			
			// Step 5: Add marker if this hasn't been tested
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
