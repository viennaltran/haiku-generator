package haiku_generator;

import java.util.ArrayList;
import java.util.Random;



public class GrammarTree {


	private ASTNode root;

	// initialize to 0
	
	// Use this method to give the tree a root node.
	// It will return a reference to the root node created.
	public GrammarTree()
	{
		

	}

	public void generateTree(int syllableCount) 
	{
		root=new ASTNode("Start");
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
		if(node.grammarSymbol.contains("*"))
		{
			if(node.grammarSymbol.contains("Noun")){
				terminals.add("n");
				
			}
			else if(node.grammarSymbol.contains("Verb")) {
				terminals.add("v");
				
				
			}else if (node.grammarSymbol.contains("Adjective"))
			{
				terminals.add("adj");
				
			
			}else if(node.grammarSymbol.contains("Conj"))
			{
				terminals.add("conj");
				
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
		Random rand = new Random();
		if(node.grammarSymbol.equals("Start"))
		{
			ASTNode child= new ASTNode("List");
			node.children.add(child);
			expandNode5(child);
			
		}
		else if (node.grammarSymbol.equals("List")) {
			
			
			int r = rand.nextInt(2); 
			
			if (r == 0) {
				// List -> NP
				ASTNode child = new ASTNode("NounPhrase");
				node.children.add(child);
				expandNode5(child);
			} else {
				// List -> NP List
				ASTNode child1 = new ASTNode("List");
				ASTNode child2 = new ASTNode("NounPhrase");
				node.children.add(child1);
				node.children.add(child2);
				expandNode5(child1);
				expandNode5(child2);
			}


		} else if (node.grammarSymbol.equals("NounPhrase")) {
			int r = rand.nextInt(2);
			
			if(r==0)
			{
				ASTNode child= new ASTNode("*Noun");
				node.children.add(child);
				
			}else {
				
				ASTNode child1 = new ASTNode("NounPhrase");
				ASTNode child2= new ASTNode("*Adjective");
				node.children.add(child1);
				node.children.add(child2);
				expandNode5(child1);
						
			}
			
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
			int r = rand.nextInt(2);
			if (r== 0)
			{
			ASTNode child1 = new ASTNode("NounPhrase");
			ASTNode child2 = new ASTNode("*Verb");
			node.children.add(child1);
			node.children.add(child2);
			expandNode7(child1);
		
			}else
			{
				ASTNode child1 = new ASTNode("NounPhrase");
				ASTNode child2 = new ASTNode("*Verb");
				ASTNode child3 = new ASTNode("*Conj");
				ASTNode child4= new ASTNode("*Verb");
				node.children.add(child1);
				node.children.add(child2);
				node.children.add(child3);
				node.children.add(child4);
				expandNode7(child1);
				
				
				
			}
			
		}else if (node.grammarSymbol.equals("NounPhrase"))
		{
			int r =rand.nextInt(2);
				
			if (r==1) {
			ASTNode child1 = new ASTNode("*Adjective");
			ASTNode child2 = new ASTNode("NounPhrase");
			node.children.add(child1);
			node.children.add(child2);
			expandNode7(child2);
			
			}else {
			ASTNode child1 = new ASTNode("*Noun");
			node.children.add(child1);
			}
		
		
		}
		
	
	}

}


