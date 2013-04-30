package se.chho.tested.helpers;

import java.util.regex.Pattern;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

	public class LinenumberHelper {
	
		/***
	 * Source: http://stackoverflow.com/a/562298
	 * @param type
	 * @param method
	 * @return
	 * @throws JavaModelException
	 */
	public static int getMethodLineNumber(final ICompilationUnit compUnit, IMethod method) throws JavaModelException {
	    String source = compUnit.getSource();
	    String sourceUpToMethod= source.substring(0, method.getSourceRange().getOffset());
	    Pattern lineEnd= Pattern.compile("$", Pattern.MULTILINE | Pattern.DOTALL);
	    return lineEnd.split(sourceUpToMethod).length;
	}
}
