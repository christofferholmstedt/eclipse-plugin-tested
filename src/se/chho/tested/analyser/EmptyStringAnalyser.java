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

public class EmptyStringAnalyser implements AnalyserObserverInterface {

	private ArrayList<FoundMethod> methods = new ArrayList<FoundMethod>();
	private boolean anyEmptyString = false; 
	
	@Override
	public void runAnalysis(AnalyserManagerObservableInterface Observable) {
		methods = Observable.getFoundMethods();

		for (FoundMethod method : methods) {
			if (method.hasOnlyStringInput())
			{
				for (MethodInvocation methodInv : method.getMethodInvocations())
				{
					// Step 1: Start with the notion that all input values is expected other than empty strings.
					boolean anyEmptyStringTest = false;
							
					// Step 2: Loop through all input parameters
					for (String string : methodInv.getStringParameters())
					{
						// Step 3: If any value is empty and not null set anyEmptyStringTest to true.
						if (string.isEmpty() && string != null)
						{
							anyEmptyStringTest = true;
						}
					}
					// Step 4: When all input parameters have been searched for and 
					// "anyEmptyStringTest" is true then set class-wider variable
					// This is to be able to reset "anyEmptyStringTest" while looping through "MethodInv"
					if (anyEmptyStringTest)
						this.anyEmptyString = true;
				}
				
				// Step 5: Add marker if this hasn't been tested
				if (!this.anyEmptyString)
				{
					if (method.isInvokedByMoreThanTwoTests())
					  {
						  try {
							IFile file = (IFile)method.getMethod().getCompilationUnit().getCorrespondingResource();
							int linenumber = LinenumberHelper.getMethodLineNumber(method.getMethod().getCompilationUnit(), method.getMethod());
							String message = "What happens if you test the method \"" + method.getMethod().getElementName() + "\" with an empty string as input?";
							
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
