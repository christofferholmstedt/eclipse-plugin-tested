package se.chho.tested.core;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IBuffer;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaCore;

import se.chho.tested.helpers.ParameterParserHelper;

/**
 * A method invocation is an invocation in a test case.
 * 
 * A method invocation is "fetched" as a string from a string buffer using 
 * offset and length. This string is then parsed into an integer or string.
 * 
 * @author Christoffer Holmstedt
 *
 */
public class MethodInvocation {

	private IFile invokedInFile;
	private IMethod invokedInMethod;
	private int invokedAtLine;
	private boolean onlyStringInput = false; 
	private boolean onlyIntInput = false;
	
	// Assumption first iteration of this plugin only works
	// for method calls that has only string or only int parameter values.
	private ArrayList<Integer> intParameters = new ArrayList<Integer>();
	private ArrayList<String> stringParameters = new ArrayList<String>();
	
	public MethodInvocation (IFile file, IMethod method, int offset, int length) {
		this.invokedInFile = file;
		this.invokedInMethod = method;
		
		try {
			ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);
			IBuffer buf = cu.getBuffer();
			String methodInvString = buf.getText(offset, length);
			
			ArrayList<Object> test = ParameterParserHelper.parseParameters(methodInvString);
			if (!test.isEmpty())
			{
				Object tempObj = test.get(0);
						
				if (tempObj instanceof Integer)
				{
					this.setOnlyIntInput(true);
					for (Object tempInt : test)
						intParameters.add(((Integer)tempInt).intValue());
				} else if (tempObj instanceof String)
				{
					this.setOnlyStringInput(true);
					for (Object tempString : test)
						stringParameters.add(((String)tempString));
				} else {
					System.out.println("Type is unknown");
				}
			}

			// TODO: get linenumber from offset and length
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getStringParameters() {
		return this.stringParameters;
	}
	
	public ArrayList<Integer> getIntParameters() {
		return this.intParameters;
	}
	
	public void setInvokedInFile(IFile file) {
		this.invokedInFile = file;
	}
	
	public void setInvokedInMethod(IMethod method) {
		this.invokedInMethod = method;
	}
	
	public void setInvokedAtLine(int linenumber) {
		this.invokedAtLine = linenumber;
	}
	
	public IFile getInvokedInFile() {
		return this.invokedInFile;
	}
	
	public IMethod getInvokedInMethod() {
		return this.invokedInMethod;
	}
	
	public int getInvokedAtLine() {
		return this.invokedAtLine;
	}
	
	public void setOnlyIntInput(boolean value)
	{
		this.onlyIntInput = value;
	}
	
	public boolean getOnlyIntInput() {
		return this.onlyIntInput;
	}
	
	public void setOnlyStringInput(boolean value)
	{
		this.onlyStringInput = value;
	}
	
	public boolean getOnlyStringInput() {
		return this.onlyStringInput;
	}
}