package haiku_generator;

import java.util.ArrayList;
import java.util.Random;



public class GrammarTree {


	private ASTNode root;
	int totalSyllables; // initialize to 0
	

	// Use this method to give the tree a root node.
	// It will return a reference to the root node created.
	public GrammarTree()
	{
		root=new ASTNode("start");

	}

	public void generateTree(int syllableCount) 
	{
		
		//if statement for first/third line
		if (syllableCount==5)
		{
		
			expandNode5(root);
		}
		else expandNode7(root);

		
 
	}
	
	public ArrayList<String> terminalList()
	{
		//traverse tree and put terminals in order in an array 
		ArrayList<String> terminals = new ArrayList<String>();
		return terminalListHelpler(root, terminals);
		
		
	}
	
	private ArrayList<String> terminalListHelpler(ASTNode node, ArrayList<String> terminals)
	{
		
		if(node.terminalString.contains("*"))
		{
			if(node.terminalString.contains("Noun")){
				terminals.add("n");
			}
			else if(node.terminalString.contains("Verb")) {
				terminals.add("v");
			}else if (node.terminalString.contains("Adjective"))
			{
			terminals.add("adj");
			}
		}
		for (ASTNode n : node.children)
		{
		terminalListHelpler(n,terminals);
		
		}
		return terminals;
		
		
	}
	

	// helper function
	// returns the total number of syllables in this node
	private void expandNode5(ASTNode node) {
		int syllables = 0;
		if (node.grammarSymbol.equals("List")) {
			
			Random rand = new Random();
			int r = rand.nextInt(1); 
			
			if (r == 0) {
				// List -> NP
				ASTNode child = new ASTNode("NP");
				node.children.add(child);
				expandNode5(child);
			} else {
				// List -> NP List
				ASTNode child1 = new ASTNode("List");
				ASTNode child2 = new ASTNode("NP");
				node.children.add(child1);
				node.children.add(child2);
				expandNode5(child1);
				expandNode5(child2);
			}


		} else if (node.grammarSymbol.equals("Noun")) {
//			ASTNode child = new ASTNode("*terminal*");
//			Word theNoun = getRandomNoun();
//			child.terminalString = theNoun;
//			syllables += theNoun.syllables;
//			children.add(child);
		}
		
	}
	
	
	private void expandNode7(ASTNode node) {
		
	
		Random rand = new Random();
		
		if(node.grammarSymbol.equals("Start"))
		{
			ASTNode child = new ASTNode("VerbPhrase");
			node.children.add(child);
			 expandNode7(child);
			
		}else if (node.grammarSymbol.equals("VerbPhrase"))
		{
			ASTNode child1 = new ASTNode("NounPhrase");
			ASTNode child2 = new ASTNode("Verb");
			node.children.add(child1);
			node.children.add(child2);
			expandNode7(child1);
			expandNode7(child2);
			
		}else if (node.grammarSymbol.equals("NounPhrase"))
		{
			ASTNode child1 = new ASTNode("Adj");
			ASTNode child2 = new ASTNode("NounPhrase");
			node.children.add(child1);
			node.children.add(child2);
			expandNode7(child1);
			expandNode7(child2);
			
		}else if(node.grammarSymbol.equals("NounPhrase"))
		{
			ASTNode child1 = new ASTNode("Noun");
			node.children.add(child1);
			
			expandNode7(child1);
		
		}else if(node.grammarSymbol.equals("Noun"))
		{
			ASTNode child = new ASTNode("Noun");
			Word randomNoun = new Word();
			
		}
		
		
		
		
	
	}

}


