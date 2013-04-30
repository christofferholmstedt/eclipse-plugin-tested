package se.chho.tested.core;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IMethod;

public class MethodInvocation {

	private IFile invokedInFile;
	private IMethod invokedInMethod;
	private int invokedAtLine;
	
	// Assumption first iteration of this plugin only works
	// for method calls that has only string or only int parameter values.
	private ArrayList<Integer> intParameters = new ArrayList<Integer>();
	private ArrayList<String> stringParameters = new ArrayList<String>();
	
	public MethodInvocation (IFile file, IMethod method, int offset, int length) {
		this.invokedInFile = file;
		this.invokedInMethod = method;
		
		// TODO: get linenumber from offset and length
		// TODO: get string value from offset and length
		// TODO: split string value to parameter values.
		// TODO: Add parameter values to corresponding ArrayList (string or int)
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
}