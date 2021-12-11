package haiku_generator;

import java.util.Random;



public class Word {
	
	private String text;
	private int syllables;
	private String pos;


	public Word()
	{
		
	}
	
	public Word(String txt, int syls, String p)
	{	
	text=txt;
	syllables=syls;
	pos=p;

	}
	
	public String getText()
	{
		return text;
	}
	
	public int getSyl()
	{
		return syllables;
	}
	
	public String getPos()
	{
		return pos;
	}
	

	
}