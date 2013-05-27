package se.chho.tested.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IMethod;

import se.chho.tested.helpers.LinenumberHelper;

/**
 * A FoundMethod is a non test method that has matching test cases,
 * or in other words, a mapping between all method invocations in test cases 
 * to the non test method.
 * 
 * Each FoundMethod stores information about all method invocations.
 * @author Christoffer Holmstedt
 *
 */
public class FoundMethod {
	
	private IMethod method;
	private IFile file;
	private int linenumber;
	private boolean onlyStringInput = false; 
	private boolean onlyIntInput = false;
	private ArrayList<MethodInvocation> invokedBy = new ArrayList<MethodInvocation>(); 
	
	// testMethods store counter how many times this.method has been found in IMethod testMethod.
	private Map<IMethod,Integer> testMethods = new HashMap<IMethod,Integer>();
	private int diffTestMethods = 0; // Number of different test methods this.method is called from.
	private boolean invokedByMoreThanTwoTests = false;
	
	// Constructor
	public FoundMethod(IMethod method)
	{
		this.method = method;
		this.file = (IFile)method.getResource();
		
		try {
			this.linenumber = LinenumberHelper.getMethodLineNumber(method.getCompilationUnit(), method);
		} catch (Exception e) {
			// Pass
		}
		
	}
		
	public ArrayList<MethodInvocation> getMethodInvocations() {
		return this.invokedBy;
	}
	
	public void addMethodInvocation(MethodInvocation invoke)
	{
		this.invokedBy.add(invoke);
	}
		
	public String getName()
	{
		return this.method.getElementName();
	}
	
	public void addMethodInvocation(IFile invokedInFile, IMethod invokedInMethod, int offset, int length)
	{
		MethodInvocation methodInv = new MethodInvocation(invokedInFile, invokedInMethod, offset, length);
		
		// Determine if the method invocation is only string or only integers.
		if (methodInv.getOnlyIntInput())
		{
			this.setOnlyIntInput(true);
		} else if (methodInv.getOnlyStringInput())
		{
			this.setOnlyStringInput(true);
		}
		invokedBy.add(methodInv);
	}
	
	public IMethod getMethod()
	{
		return this.method;
	}
	
	/**
	 * @return the diffTestMethods
	 */
	public int getDiffTestMethods() {
		return diffTestMethods;
	}

	/**
	 * @param diffTestMethods the diffTestMethods to set
	 */
	private void setDiffTestMethods(int diffTestMethods) {
		this.diffTestMethods = diffTestMethods;
		if (this.diffTestMethods >= 2)
			this.setInvokedByMoreThanTwoTests(true);
		else
			this.setInvokedByMoreThanTwoTests(false);
	}

	public void addMatch(IMethod testMethod, int numberOfHits)
	{
			if (numberOfHits > 0)
			{
				testMethods.put(testMethod, numberOfHits);
				recountDiffTestMethods();
			}
	}
	
	private void recountDiffTestMethods()
	{
		int c = 0;
		for (@SuppressWarnings("unused") Entry<IMethod, Integer> entry : this.testMethods.entrySet())
			c++;
		this.setDiffTestMethods(c);
	}

	/**
	 * @return the invokedByMoreThanTwoTests
	 */
	public boolean isInvokedByMoreThanTwoTests() {
		return invokedByMoreThanTwoTests;
	}

	/**
	 * @param invokedByMoreThanTwoTests the invokedByMoreThanTwoTests to set
	 */
	private void setInvokedByMoreThanTwoTests(boolean invokedByMoreThanTwoTests) {
		this.invokedByMoreThanTwoTests = invokedByMoreThanTwoTests;
	}
	
	public void setOnlyIntInput(boolean value)
	{
		this.onlyIntInput = value;
	}
	
	public boolean hasOnlyIntInput() {
		return this.onlyIntInput;
	}
	
	public void setOnlyStringInput(boolean value)
	{
		this.onlyStringInput = value;
	}
	
	public boolean hasOnlyStringInput() {
		return this.onlyStringInput;
	}
}
