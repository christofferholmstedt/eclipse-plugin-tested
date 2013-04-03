package se.chho.tested;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.junit.model.ITestCaseElement;

/**
 * This class goes through all test methods in given scope (usually a project not the 
 * entire eclipse workspace) and finds all methods that has been invoked by the tests.
 * 
 * @author Christoffer Holmstedt
 *
 */
public class FindInvokedMethods {

	private ArrayList<ITestCaseElement> passedTests = new ArrayList<ITestCaseElement>();
	private IJavaProject activeJavaProject;
	
	// Collect all (.*)Test.java files in this ArrayList
	private ArrayList<ICompilationUnit> searchInCompUnits = new ArrayList<ICompilationUnit>();
	private ArrayList<IMethod> searchForMethods = new ArrayList<IMethod>();
	
	public FindInvokedMethods (ArrayList<ITestCaseElement> passedTests, IJavaProject activeJavaProject)
	{
		this.passedTests = passedTests;
		this.activeJavaProject = activeJavaProject;
	}

	public void run() {
		try {
			// Process project and populates local arraylists.
			processProject(activeJavaProject);
		} catch (Exception e)
		{
			// Pass
		}
		
		  // Step 1: Prepare search scope
		  IJavaElement[] elems = new IJavaElement[this.searchInCompUnits.size()];
		  elems =  this.searchInCompUnits.toArray(elems);
		  IJavaSearchScope scope = SearchEngine.createJavaSearchScope(elems);
		  
		  for (IMethod method : this.searchForMethods)
		  {
			  searchFor(method, scope);
		  }
        /**
		 * Some example code to fetch testMethodNames.
		 *
			int n=1;
			for (ITestCaseElement element : this.passedTests)
			{
				System.out.println("Test " + n + ": " + element.getTestMethodName());
				n++;
			}
		*/
     }
	
	/**
	 * Source: http://stackoverflow.com/questions/11321363/alternative-or-improvement-to-eclipse-jdt-searchengine
	 * @param javaProject
	 * @throws JavaModelException
	 */
	public void processProject(IJavaProject javaProject) throws JavaModelException{
	    for(IPackageFragment pkg : javaProject.getPackageFragments()){
	        if(pkg.getKind() == IPackageFragmentRoot.K_SOURCE){
	            for(ICompilationUnit unit : pkg.getCompilationUnits()){
	            	
	            	// TODO: Assumption Github issue gh-1
	            	if (unit.getElementName().matches("(.)*Test.java"))
        			{
	            		this.searchInCompUnits.add(unit);
        			} else 
        			{
        				// If Compilation Unit is not test file/class/unit then add all methods to elements to search for.
        				for(IType type : unit.getTypes()){
    	                    for(IMethod method : type.getMethods()){
    	                    	this.searchForMethods.add(method);
    	                    }
    	                }
        			}
	            }
	        }
	    }
	}
	
	/***
	 * Search for method references
	 * Source: http://stackoverflow.com/questions/11321363/alternative-or-improvement-to-eclipse-jdt-searchengine
	 * @param elem
	 */
	public void searchFor(IJavaElement elem, IJavaSearchScope scope){
	    TestedSearchRequestor requestor = new TestedSearchRequestor();
	    SearchPattern pattern = SearchPattern.createPattern(elem, IJavaSearchConstants.REFERENCES);
	    SearchEngine searchEngine = new SearchEngine();
	    try{
	        searchEngine.search(pattern, new SearchParticipant[] {SearchEngine.getDefaultSearchParticipant()}, scope, requestor, null);
	        
	        System.out.println(requestor.getMatches().toString());
	    }catch(CoreException e){
	        e.printStackTrace();
	    }
	}
}
