import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Progress
{
	/**
	 * loads the current best score
	 * from the provided file
	 * @param fileName
	 * @return current best score
	 */
	public static int loadScoreFromFile(String fileName){
		int bestScore = 0;
	    try 
	    {
	    	File loadedFile = new File(fileName);
	    	//crating a an individual scanner for our file
			Scanner inputHolder = new Scanner(loadedFile);
	    	
	    	if(inputHolder.hasNextInt()) 
	    	{
	    		bestScore = inputHolder.nextInt();	
	    	}	
	    	inputHolder.close();
	    }
	    catch(FileNotFoundException ex) 
	    {
	    	System.err.println("Incorrect file");
	    	System.err.println(ex.getMessage());
	    }
	    return bestScore;
	}
	
	/**
	 * stores your score if you
	 * scored higher than previous best score
	 * @param bestScore
	 * @param fileName
	 */
	public static void saveScoreToFile(int bestScore, String fileName)
	{
		try 
		{
			File scoreFile = new File(fileName);
			PrintWriter printer = new PrintWriter(scoreFile);
			if(Virus.consumedPopulation > bestScore)
			{
				printer.print(Virus.consumedPopulation);
			}
			else {
				printer.print(bestScore);
			}
			printer.close();
		}
		catch(FileNotFoundException ex) 
	    {
	    	System.err.println("Incorrect file");
	    	System.err.println(ex.getMessage());
	    }
	}
}