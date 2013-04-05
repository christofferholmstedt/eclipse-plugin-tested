package se.chho.tested;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchRequestor;

public class TestedSearchRequestor extends SearchRequestor {
	private ArrayList<SearchMatch> matches;
	private Map<String,Integer> invokedMethodsCounter = new HashMap<String,Integer>();
	private String prevMethod;
	private int counter = 0;
	
	public TestedSearchRequestor(){
	    super();
	    matches = new ArrayList<SearchMatch>();
	}
	
	@Override
	public void acceptSearchMatch(SearchMatch match) throws CoreException {
	    if(match.getAccuracy() == SearchMatch.A_ACCURATE);
	    	this.counter++;
	    	// TODO: prevMethod is broken, though the logic is also broken
	    	// can't find what I match against in this object, only the containig element.
	    
//	    	String[] invokedMethods = match.getElement().toString().split("\\s+");
//	    	System.out.println(prevMethod + " " + match.getElement());
//	    	if (prevMethod != invokedMethods[1])
//	    	{
//	    		if(invokedMethodsCounter.containsKey(invokedMethods[1]))
//	    			invokedMethodsCounter.put(invokedMethods[1], invokedMethodsCounter.get(invokedMethods[1])+1);
//	            else
//	                invokedMethodsCounter.put(invokedMethods[1], 1);
//	    		
//	    		
//	    	}
//	    	prevMethod = invokedMethods[1];
//	    	matches.add(match);
	}

	public ArrayList<SearchMatch> getMatches(){
	    return matches;
	}
	
	public Map<String,Integer> getInvokedMethodsCounter(){
	    return invokedMethodsCounter;
	}
	
	public int getCounter(){
	    return counter;
	}
	
	public void resetCounter(){
	    this.counter = 0;
	}
}