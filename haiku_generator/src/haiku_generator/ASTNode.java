package haiku_generator;

import java.util.ArrayList;

import haiku_generator.WordBank.Word;

public class ASTNode {
 
	String grammarSymbol;
	String terminalString; 
	ArrayList<ASTNode> children;
	
	
	
	public ASTNode(String grammarSym)
	{
		grammarSymbol=grammarSym;
	}

}