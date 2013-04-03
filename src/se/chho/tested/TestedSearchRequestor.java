package se.chho.tested;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchRequestor;

public class TestedSearchRequestor extends SearchRequestor {
	private ArrayList<SearchMatch> matches;

	public TestedSearchRequestor(){
	    super();
	    matches = new ArrayList<SearchMatch>();
	}
	
	@Override
	public void acceptSearchMatch(SearchMatch match) throws CoreException {
	    if(match.getAccuracy() == SearchMatch.A_ACCURATE);
	        matches.add(match);
	}

	public ArrayList<SearchMatch> getMatches(){
	    return matches;
	}
}