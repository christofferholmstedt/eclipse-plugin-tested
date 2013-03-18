package se.chho.tested.examples;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class ListAllMethodsHandler extends AbstractHandler {
	    
	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		System.out.println("---- New command ----");
		// All projects, both closed and opened
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		
		// Get current active/selected project name
		IEditorPart  editorPart =
				PlatformUI.getWorkbench().
				getActiveWorkbenchWindow().
				getActivePage().
				getActiveEditor();
		String activeProjectName = null;
		
		if(editorPart  != null)
		{
			IFileEditorInput input = 
					(FileEditorInput)editorPart.getEditorInput();
		    IFile file = input.getFile();
		    IProject activeProject = 
		    		file.getProject();
		    activeProjectName = 
		    		activeProject.getName();
		}
		
		// Iterate over all projects available and analyse the "active" one.
	    for (IProject project : projects) {
	    	try {
	    		if (project.getName().equalsIgnoreCase(activeProjectName)
	    				&& project.isNatureEnabled(JDT_NATURE))
	    		{
	    			// Go through all methods in classes in the current
	    			// project.
	    			analyseMethods(project);
	    			// TODO: Can I mark all methods in "otherMethods" with a red exclamation mark here?
	    		}
	      } catch (CoreException e) {
	        e.printStackTrace();
	      }
	    }
	    return null;
	  }
	
	/**
	 * Analysemethods parses a project creates an AST for each Package Fragment Root.
	 * @param project
	 * @throws JavaModelException
	 */
	  private void analyseMethods(IProject project) throws JavaModelException { 
		  
		// Returns all package fragments in all package fragment roots contained in this project
		IPackageFragment[] packages = JavaCore.create(project).getPackageFragments();

	    for (IPackageFragment tempPackage : packages) {
	    	// If the package is located within a source root (K_SOURCE) the
	    	// create a new AST for that package.
	      if (tempPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
	        createAST(tempPackage);
	      }
	    }
	  }
	  /***
	   * Takes one package fragment splits it up into its compilationUnits
	   * which are source files.
	   * compilationUnits (source files
	   * @param tempPackage
	   * @throws JavaModelException
	   */
	  private void createAST(IPackageFragment tempPackage)
	      throws JavaModelException {
	    
		  // For each source file 
		  for (ICompilationUnit unit : tempPackage.getCompilationUnits()) {
			  CompilationUnit tempCompilationUnit = parseCompilationUnit(unit);
			  ListAllMethodsVisitor visitor = new ListAllMethodsVisitor();
			  
			  tempCompilationUnit.accept(visitor);
			
			  // Get all tests in the selected package
			  for (MethodDeclaration method : visitor.getTestMethods()) {		 
			    
					  System.out.println("Method name: \"" + method.getName());
					  List<Statement> rows = new ArrayList<Statement>();
					  rows = method.getBody().statements();
					  
					  // Prints all rows in all test methods
					  // TODO: parse out all method calls.
					  // TODO: Must be some better way to identify method calls?
					  /*for (Statement row: rows) 
					  {
					  		// For each statement regexp match against visitor.otherMethods()
					  		// If otherMethods exists within statement remove it from visitor.otherMethods.
						  System.out.println(row);
					  }*/
				  
				  // TODO: What is bindings? Expensive when creating AST below but what does it give me?
				  IMethodBinding bindings = method.resolveBinding();
				  // System.out.println("TEST: " + bindings.getName());
			  }
	    }
	  }
  
	  private static CompilationUnit parseCompilationUnit(ICompilationUnit tempCompilationUnit) {
		  // Updated to AST.JLS4
		  ASTParser parser = ASTParser.newParser(AST.JLS4);
		  
		  parser.setKind(ASTParser.K_COMPILATION_UNIT);
		  parser.setSource(tempCompilationUnit);
		  
		  // TODO: What does this add as functionality goes?
		  // Warning, considerable cost in both time and space added.
		  parser.setResolveBindings(true);
		  
		  return (CompilationUnit) parser.createAST(null); // parse
	  }
} 