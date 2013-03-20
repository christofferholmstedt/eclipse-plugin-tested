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
	private static final String VALID_PATTERN_TESTS = "^test(.*)";
	
	List<MethodDeclaration> testMethods = new ArrayList<MethodDeclaration>();
	List<MethodDeclaration> nonTestMethods = new ArrayList<MethodDeclaration>();
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
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

		// For each source file  accept visitor
		for (ICompilationUnit unit : tempPackage.getCompilationUnits()) {
			CompilationUnit tempCompilationUnit = parseCompilationUnit(unit);
			ListAllMethodsVisitor visitor = new ListAllMethodsVisitor();

			tempCompilationUnit.accept(visitor);
			
			// Get all tests in the selected package
			for (MethodDeclaration method : visitor.getMethods()) {
				// Parse all methods and group them.
				if (method.getName().getIdentifier().matches(ListAllMethodsHandler.VALID_PATTERN_TESTS))
					testMethods.add(method);
				else
					nonTestMethods.add(method);
					
				
				
				// TODO: What is bindings? Expensive when creating AST below but what does it give me?
				IMethodBinding bindings = method.resolveBinding();
			}
		}
		
		for (MethodDeclaration testMethod : testMethods)
		{
			for (MethodDeclaration nonTestMethod : nonTestMethods)
			{
				List<Statement> statementList = new ArrayList<Statement>();
				statementList = testMethod.getBody().statements();
				
				for (Statement st : statementList )
				{
					// Trim the string for each statement otherwise
					// newline will disturb the .matches() regex function.
					String tempString = new String(st.toString());
					tempString = tempString.trim();
					
					// Pattern is any character in front and after the method name.
					String pattern = ".*" + nonTestMethod.getName().getIdentifier() + ".*"; 

					if (tempString.matches(pattern))
					{
						System.out.println("+++ Match: " + nonTestMethod.getName().getIdentifier() +
								" executed in " + testMethod.getName().getIdentifier());
					} else 
					{
						System.out.println("--- Nope");
					}
					
				}
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