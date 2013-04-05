package se.chho.tested;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchRequestor;

public class TestedSearchRequestor extends SearchRequestor {
//	private ArrayList<SearchMatch> matches;
	private int counter = 0;
	
	public TestedSearchRequestor(){
	    super();
//	    matches = new ArrayList<SearchMatch>();
	}
	
	@Override
	public void acceptSearchMatch(SearchMatch match) throws CoreException {
	    if(match.getAccuracy() == SearchMatch.A_ACCURATE);
	    	this.counter++;
	    	// TODO: prevMethod is broken, though the logic is also broken
	    	// can't find what I match against in this object, only the containig element.
	    
//	    	matches.add(match);
	}

//	public ArrayList<SearchMatch> getMatches(){
//	    return matches;
//	}

	public int getCounter(){
	    return counter;
	}
	
	public void resetCounter(){
	    this.counter = 0;
	}
}