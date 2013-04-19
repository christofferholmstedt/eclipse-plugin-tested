package se.chho.tested;

import java.util.ArrayList;
import java.util.regex.Pattern;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
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
import org.eclipse.jdt.junit.model.ITestCaseElement;

/**
 * This class goes through all test methods in given scope (usually a project not the 
 * entire eclipse workspace) and finds all methods that has been invoked by the tests.
 * 
 * @author Christoffer Holmstedt
 *
 */
public class FindInvokedMethods {
	
	// Search objects
	private TestedSearchRequestor requestor = new TestedSearchRequestor();
	
	private IJavaProject activeJavaProject;
	
	// Collect all (.*)Test.java files in this ArrayList
	private ArrayList<ICompilationUnit> searchInCompUnits = new ArrayList<ICompilationUnit>();
	private ArrayList<IMethod> nonTestMethods = new ArrayList<IMethod>();
	private ArrayList<IMethod> testMethods = new ArrayList<IMethod>();
	private ArrayList<FoundMethod> foundMethods = new ArrayList<FoundMethod>();
	
	public FindInvokedMethods (IJavaProject activeJavaProject)
	{
		this.activeJavaProject = activeJavaProject;
	}

	public void run() {
		try {
			// Process project and populates local Arraylists.
			processProject(activeJavaProject);
			populateFoundMethods();
		} catch (Exception e)
		{
			// Pass
		}
  
		  // Test print out of this ArrayList
//		  for (ITestCaseElement passed : this.passedTests)
//		  {
//			  System.out.println(passed.getTestMethodName());
//		  }
		  
		  for (FoundMethod foundMethod : foundMethods)
		  {
			  if (foundMethod.isInvokedByMoreThanTwoTests())
			  {
				  
//				try {		
//					int linenumber = getMethodLineNumber(foundMethod.getMethod().getCompilationUnit(), foundMethod.getMethod());
//					System.out.println("Line Number: " + linenumber);
//					
//					IMarker marker = foundMethod.getMethod().getCorrespondingResource().createMarker(IMarker.MARKER);
//					marker.setAttribute(IMarker.LINE_NUMBER, linenumber);
//					marker.setAttribute(IMarker.SEVERITY, IMarker.PRIORITY_NORMAL);
//					marker.setAttribute("description", "test description");
//					marker.setAttribute(IMarker.MESSAGE, "My marker message");
//					
//				} catch (JavaModelException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (CoreException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			  }
			  String newLine = System.getProperty("line.separator");
			  System.out.println(foundMethod.getName() + " found in " + foundMethod.getDiffTestMethods() + " different test functions.");
			  System.out.println("Is " + foundMethod.getName() + " found in more than 2 test methods? " + foundMethod.isInvokedByMoreThanTwoTests() + newLine);
			  // TODO: Figure out how to show marker for all methods
			  if (foundMethod.isInvokedByMoreThanTwoTests())
			  {
				  try {
					  IResource m = foundMethod.getMethod().getCorrespondingResource();
					  int linenumber = getMethodLineNumber(foundMethod.getMethod().getCompilationUnit(), foundMethod.getMethod());
					  IMarker marker = m.createMarker("org.eclipse.core.resources.problemmarker");
					  marker.setAttribute(IMarker.LINE_NUMBER, linenumber);
				  } catch (Exception e)
				  {
					  System.out.println(" ============= error ============= ");
					  e.printStackTrace();
				  }
			  }
		  }
     }
	
	/**
	 * Source: http://stackoverflow.com/questions/11321363/alternative-or-improvement-to-eclipse-jdt-searchengine
	 * @param javaProject
	 * @throws JavaModelException
	 */
	private void processProject(IJavaProject javaProject) throws JavaModelException{
	    for(IPackageFragment pkg : javaProject.getPackageFragments()){
	        if(pkg.getKind() == IPackageFragmentRoot.K_SOURCE){
	            for(ICompilationUnit unit : pkg.getCompilationUnits()){
	            	
	            	// TODO: Assumption Github issue gh-1
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
	 * Search for method references
	 * Source: http://stackoverflow.com/questions/11321363/alternative-or-improvement-to-eclipse-jdt-searchengine
	 * @param elem
	 */
	public void searchFor(IJavaElement elem, IJavaSearchScope scope){
	    SearchPattern pattern = SearchPattern.createPattern(elem, IJavaSearchConstants.REFERENCES);
	    SearchEngine searchEngine = new SearchEngine();
	    try{
	        searchEngine.search(pattern, new SearchParticipant[] {SearchEngine.getDefaultSearchParticipant()}, scope, requestor, null);
	    }catch(CoreException e){
	        e.printStackTrace();
	    }
	}
	
	/***
	 * Source: http://stackoverflow.com/a/562298
	 * @param type
	 * @param method
	 * @return
	 * @throws JavaModelException
	 */
	private int getMethodLineNumber(final ICompilationUnit compUnit, IMethod method) throws JavaModelException {
	    String source = compUnit.getSource();
	    String sourceUpToMethod= source.substring(0, method.getSourceRange().getOffset());
	    Pattern lineEnd= Pattern.compile("$", Pattern.MULTILINE | Pattern.DOTALL);
	    return lineEnd.split(sourceUpToMethod).length;
	}
	
	private void populateFoundMethods()
	{
		// For each non test method, find if it's invoked in each test method one by one.
		  for (IMethod nonTestMethod : this.nonTestMethods)
		  {
			  FoundMethod tempFoundMethod = new FoundMethod(nonTestMethod);
			  for (IMethod testMethod : this.testMethods)
			  {
				  IJavaSearchScope scope = SearchEngine.createJavaSearchScope(new IJavaElement[] {testMethod});
				  searchFor(nonTestMethod, scope);
				  
				  tempFoundMethod.addMatch(testMethod, this.requestor.getCounter());
				  
				  // System.out.println(nonTestMethod.getElementName() + " found in " + testMethod.getElementName() + ": " + this.requestor.getCounter() + " times.");
				  this.requestor.resetCounter();
			  }
			  foundMethods.add(tempFoundMethod);
		  }
	}
}
