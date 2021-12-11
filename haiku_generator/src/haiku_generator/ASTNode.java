package haiku_generator;

import java.util.ArrayList;



public class ASTNode {
 
	String grammarSymbol;
	String terminalString; 
	ArrayList<ASTNode> children;
	
	
	
	public ASTNode(String grammarSym)
	{
		grammarSymbol=grammarSym;
		terminalString ="";
		children = new ArrayList<ASTNode>();
	}

}