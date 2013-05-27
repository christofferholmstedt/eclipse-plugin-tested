package se.chho.tested.core;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
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

/**
 * AnalyserManager takes care of launching and managing the parsing process.
 * First classes are divided between test-classes and implementation class in processProject().
 * Next step is to populate the array FoundMethods with information about all method invocations,
 * it's this array that is later on iterated over from each analyser.
 * 
 * @author Christoffer Holmstedt
 */
public class AnalyserManager implements AnalyserManagerObservableInterface {

	// Observers (Different analysers)
	private ArrayList<AnalyserObserverInterface> observers = new ArrayList<AnalyserObserverInterface>();
	
	// Search objects
	private TestedSearchRequestor requestor = new TestedSearchRequestor();
	
	private ArrayList<ICompilationUnit> searchInCompUnits = new ArrayList<ICompilationUnit>();
	private ArrayList<IMethod> nonTestMethods = new ArrayList<IMethod>();
	private ArrayList<IMethod> testMethods = new ArrayList<IMethod>();
	private ArrayList<FoundMethod> foundMethods = new ArrayList<FoundMethod>();
	
	public AnalyserManager (IJavaProject activeJavaProject) {
		try {
			// Process project and populates local Arraylists.
			processProject(activeJavaProject);
			populateFoundMethods();
		} catch (Exception e)
		{
			// Pass
		}
	}
	
	@Override
	public ArrayList<FoundMethod> getFoundMethods() {
		return this.foundMethods;
	}

	@Override
	public void attach(AnalyserObserverInterface Observer) {
		this.observers.add(Observer);
	}

	@Override
	public void detach(AnalyserObserverInterface Observer) {
		// TODO: Make sure this works if needed.
		this.observers.remove(Observer);
	}

	@Override
	public void runAllAnalysers() {
		for (AnalyserObserverInterface observer : this.observers)
		{
			observer.runAnalysis(this);
		}
	}
	
	/**
	 * Inpspiration from: http://stackoverflow.com/questions/11321363/alternative-or-improvement-to-eclipse-jdt-searchengine
	 * @param javaProject
	 * @throws JavaModelException
	 */
	private void processProject(IJavaProject javaProject) throws JavaModelException{
	    for(IPackageFragment pkg : javaProject.getPackageFragments()){
	        if(pkg.getKind() == IPackageFragmentRoot.K_SOURCE){
	            for(ICompilationUnit unit : pkg.getCompilationUnits()){
	            	
	            	// Assumption Github issue gh-1
	            	if (unit.getElementName().matches("(.)*Test.java"))
        			{
	            		this.searchInCompUnits.add(unit);
	            	
	            		// If Compilation Unit is test file/class/unit then add all methods to testMethod Arraylist.
	            		for(IType type : unit.getTypes()){
    	                    for(IMethod method : type.getMethods()){
    	                    	this.testMethods.add(method);
    	                    }
    	                }
        			} else 
        			{
        				// If Compilation Unit is not test file/class/unit then add all methods to nonTestMethod Arraylist.
        				for(IType type : unit.getTypes()){
    	                    for(IMethod method : type.getMethods()){
    	                    	this.nonTestMethods.add(method);
    	                    }
    	                }
        			}
	            }
	        }
	    }
	}
	
	/***
	 * For each nonTestMethod search in each testMethod.
	 * If nonTestMethod is found in testMethod add to ArrayList<foundMethods>.
	 */
	private void populateFoundMethods()
	{
		// For each non test method, find if it's invoked in each test method one by one.
		  for (IMethod nonTestMethod : this.nonTestMethods)
		  {
			  
			  FoundMethod tempFoundMethod = new FoundMethod(nonTestMethod);  

			  // Looping through test methods.
			  for (IMethod testMethod : this.testMethods)
			  {
				  // Set scope for each search to be the test method currently iterating over.
				  IJavaSearchScope scope = SearchEngine.createJavaSearchScope(new IJavaElement[] {testMethod});
				  searchFor(nonTestMethod, scope);

				  // Search result are automatically added to the this.requestor which
				  // can be iterated over.
				  for(SearchMatch match : this.requestor.getMatches())
				  {					  
					  // Each match is then added as a method invocation to the current "FoundMethod" object.
					  tempFoundMethod.addMethodInvocation((IFile)testMethod.getResource(), testMethod, match.getOffset(), match.getLength());
				  }
				  
				  tempFoundMethod.addMatch(testMethod, this.requestor.getCounter());
				  
				  // Clear the requestor when going for the next test method (changing scope in the search).
				  this.requestor.deleteMatches();
				  this.requestor.resetCounter();
			  }
			  foundMethods.add(tempFoundMethod);
		  }
	}
	
	/***
	 * Search for method references
	 * Source: http://stackoverflow.com/questions/11321363/alternative-or-improvement-to-eclipse-jdt-searchengine
	 * @param elem
	 */
	public void searchFor(IJavaElement elem, IJavaSearchScope scope){
	    SearchPattern pattern = SearchPattern.createPattern(elem, IJavaSearchConstants.REFERENCES);
	    SearchEngine searchEngine = new SearchEngine();
	    try{
	    	// This is where the actual search is initialised
	        searchEngine.search(pattern, new SearchParticipant[] {SearchEngine.getDefaultSearchParticipant()}, scope, requestor, null);
	    }catch(CoreException e){
	        e.printStackTrace();
	    }
	}
	
}
