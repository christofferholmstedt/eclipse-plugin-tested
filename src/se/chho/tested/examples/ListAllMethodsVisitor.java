package se.chho.tested.examples;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class ListAllMethodsVisitor extends ASTVisitor {
	
	  private static final String VALID_PATTERN_TESTS = "^test(.*)";
	  
	  // Storage for all methods
	  List<MethodDeclaration> testMethods = new ArrayList<MethodDeclaration>();
	  List<MethodDeclaration> otherMethods = new ArrayList<MethodDeclaration>();

	  // http://help.eclipse.org/juno/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Freference%2Fapi%2Forg%2Feclipse%2Fjdt%2Fcore%2Fdom%2Fpackage-summary.html&resultof=%22ASTVisitor%22%20%22astvisitor%22
	  @Override
	  public boolean visit(MethodDeclaration node) {
		  if (node.getName().getIdentifier().matches(ListAllMethodsVisitor.VALID_PATTERN_TESTS))
		  {
			  testMethods.add(node);
		  }
		  else
		  {
			  System.out.println("*Visitor.java: " + node.getName());
			  otherMethods.add(node);
		  }
	    return super.visit(node);
	  }
	  
	  public List<MethodDeclaration> getTestMethods() {
	    return testMethods;
	  }
	  
	  public List<MethodDeclaration> getOtherMethods() {
		  return otherMethods;
	  }
} 




