package se.chho.tested.core;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchRequestor;

public class TestedSearchRequestor extends SearchRequestor {
	private ArrayList<SearchMatch> matches;
	private int counter = 0;
	
	public TestedSearchRequestor(){
	    super();
	    this.matches = new ArrayList<SearchMatch>();
	}
	
	@Override
	public void acceptSearchMatch(SearchMatch match) throws CoreException {
	    if(match.getAccuracy() == SearchMatch.A_ACCURATE);
	    	this.counter++;
	    	this.matches.add(match);
	    
	}

	public void deleteMatches() {
		this.matches.clear();
	}
	public ArrayList<SearchMatch> getMatches(){
	    return matches;
	}

	public int getCounter(){
	    return counter;
	}
	
	public void resetCounter(){
	    this.counter = 0;
	}
}