package se.chho.tested.core;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;

public class MethodInvocation {

	private IFile invokedInFile;
	private int invokedAtLine;
	private boolean onlyStringInput = false; 
	private boolean onlyIntInput = false;
	
	private ArrayList<Integer> intParameters = new ArrayList<Integer>();
	private ArrayList<String> stringParameters = new ArrayList<String>();
	
	public ArrayList<String> getStringParameters() {
		return this.stringParameters;
	}
	
	public ArrayList<Integer> getIntParameters() {
		return this.intParameters;
	}
	
	public boolean hasOnlyIntInput() {
		return this.onlyIntInput;
	}
	
	public boolean hasOnlyStringInput() {
		return this.onlyStringInput;
	}
	
	public IFile invokedInFile() {
		return this.invokedInFile;
	}
	
	public int getInvokedAtLine() {
		return this.invokedAtLine;
	}
}