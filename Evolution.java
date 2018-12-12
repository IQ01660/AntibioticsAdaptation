import java.awt.Graphics;
import java.util.Random;

/**
 * This class is where the main methods of the evolution are located
 * It has 3 main functions create variation in the population,
 * foster natural selection
 * and use the "heredity principle" to produce the new generation of bacteria
 * @author ikramgabiyev
 *
 */
public class Evolution 
{
	public static final int BAC_POPULATION = 200;
	public static final int ANTI_POPULATION = 2;
	public static BiologicalDS<Bacterium> bacteriaPopulation = new BiologicalDS<Bacterium>();
	public static BiologicalDS<Antibiotic> antibioPopulation = new BiologicalDS<Antibiotic>();
	public static Virus hiv = new Virus(); // the only virus controlled by the user
	/**
	 * Creates the first random probes of bacteria.
	 * Should run just once at the beginning of the program.
	 * See Main.java
	 */
	
	public static void randomlyPopulate() 
	{
		for(int j = 0; j < ANTI_POPULATION; j++)
		{
			Antibiotic anti = new Antibiotic();
		}
		for(int i = 0; i < BAC_POPULATION; i++) 
		{
			Bacterium randomBacterium = new Bacterium();
		}
	}
	
	/**
	 * Return a bacterium, which probably has a longer
	 * lifeTime, than other bacteria
	 * @return fittest Bacterium
	 */
	
	public static Bacterium fittestParent()
	{
		while(true) 
		{
			Random rand = new Random();
			int randomIndex = rand.nextInt(BAC_POPULATION);
			BiologicalNode<Bacterium> tempTail = bacteriaPopulation.tail;
			
			for (int i = bacteriaPopulation.length - 1; i >= randomIndex; i--)
			{			
				if(rand.nextDouble() < tempTail.bioElem.piePiece)
				{
					return tempTail.bioElem;
				}
				tempTail = tempTail.nextBioNode;
			}
		}
		
	}
	
	/**
	 * This method employs the fittestParent() 
	 * to find to parents, cross them, thereby
	 * introducing the child bacterium to the Population
	 */
	
	public static void naturalSelection()
	{
		Bacterium father = fittestParent();
		Bacterium mother = fittestParent();
		Bacterium.crossBacteria(father, mother);
		
	}
	
	/**
	 * Runs through every bacterium alive,
	 * and calls disappear for everyone
	 * disappear() checks if a bacterium was eaten by antibiotic
	 * and removes that bacterium from the data structure if that is the case.
	 * Note: removed elements cannot be drawn in the next generation.
	 */
	
	public static void disappearAllConsumed()
	{
		BiologicalNode<Bacterium> tempTail = bacteriaPopulation.tail;
		for(int i = 0; i < bacteriaPopulation.length; i++) 
		{
			tempTail.bioElem.disappear();
			tempTail = tempTail.nextBioNode;
		}
	}
	
	/**
	 * Draws all bacteria in the data structure
	 * at the same time it uses move(time) to move all bacteria
	 * and bounce() to make them bounce off the walls if they hit them
	 * Note: See bounce() and move(time) in KinematticObject.java 
	 * @param Graphics g
	 */
	
	public static void drawAllBacteria(Graphics g)
	{
		BiologicalNode<Bacterium> tempTail = bacteriaPopulation.tail;
		for(int i = 0; i < bacteriaPopulation.length; i++) 
		{
			tempTail.bioElem.move(0.1);
			tempTail.bioElem.bounce();
			tempTail.bioElem.drawBacteria(g);
			// do not go further if you reach the last element
			if (i != bacteriaPopulation.length - 1)
			{
				tempTail = tempTail.nextBioNode;
			}
		}
	}
	
	/**
	 * Draws all antibiotics in the data structure
	 * at the same time it uses move(time) to move all antibiotics
	 * and bounce() to make them bounce off the walls if they hit them
	 * Note: See bounce() and move(time) in KinematticObject.java 
	 * @param Graphics g
	 */
	
	public static void drawAllAntibiotics(Graphics g)
	{
		BiologicalNode<Antibiotic> tempTail = antibioPopulation.tail;
		for(int i = 0; i < antibioPopulation.length; i++) 
		{
			tempTail.bioElem.move(0.1);
			tempTail.bioElem.bounce();
			tempTail.bioElem.drawAntibiotic(g);
			tempTail = tempTail.nextBioNode;
		}
	} 
	
	/**
	 * Returns the average fittnessValue of all bacteria
	 * This method is used mainly for debugging and for observation
	 * @return average fittness of the population
	 */
	
	public static double findAverageFitness()
	{
		double fitnessSum = 0;
		BiologicalNode<Bacterium> tempTail = bacteriaPopulation.tail;
		for(int i = 0; i < bacteriaPopulation.length; i++) 
		{
			fitnessSum += tempTail.bioElem.genome.fitnessValue;
			tempTail = tempTail.nextBioNode;
		}
	return fitnessSum/BAC_POPULATION;
	}
}