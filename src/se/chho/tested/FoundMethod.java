package se.chho.tested;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.IMethod;

public class FoundMethod {
	
	private IMethod method;
	// testMethods store counter how many times this.method has been found in IMethod testMethod.
	private Map<IMethod,Integer> testMethods = new HashMap<IMethod,Integer>();
	private int diffTestMethods = 0; // Number of different test methods this.method is called from.
	private boolean invokedByMoreThanTwoTests = false;
	
	public FoundMethod(IMethod method)	
	{
		this.method = method;
	}
	
	public String getName()
	{
		return this.method.getElementName();
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
			System.out.println(testMethod.getElementName() + " " + numberOfHits);
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
}
