package haiku_generator;

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
			root = new ASTNode("Start");
			totalSyllables = expandNode5(root);
		}

		

	}

	// helper function
	// returns the total number of syllables in this node
	private int expandNode5(ASTNode node) {
		int syllables = 0;
		if (node.grammarSymbol.equals("List")) {
			
			Random rand = new Random();
			int r = rand.nextInt(1);
			
			if (r == 0) {
				// List -> NP
				ASTNode child = new ASTNode("NP");
				node.children.add(child);
				syllables = expandNode5(child);
			} else {
				// List -> NP List
				ASTNode child1 = new ASTNode("List");
				ASTNode child2 = new ASTNode("NP");
				node.children.add(child1);
				node.children.add(child2);
				syllables += expandNode5(child1);
				syllables += expandNode5(child2);
			}


		} else if (node.grammarSymbol.equals("Noun")) {
//			ASTNode child = new ASTNode("*terminal*");
//			Word theNoun = getRandomNoun();
//			child.terminalString = theNoun;
//			syllables += theNoun.syllables;
//			children.add(child);
		}
		return syllables;
	}

}


