import java.awt.Graphics;
import java.util.Random;

public class Evolution 
{
	public static final int POPULATION = 200;
	public static BiologicalDS<Bacterium> bacteriaPopulation = new BiologicalDS<Bacterium>();
	public static BiologicalDS<Antibiotic> antibioPopulation = new BiologicalDS<Antibiotic>();
	
	public static void randomlyPopulate() 
	{
		Antibiotic anti = new Antibiotic(); // change it later
		for(int i = 0; i < POPULATION; i++) 
		{
			Bacterium randomBacterium = new Bacterium();
		}
	}
	public static Bacterium fittestParent()
	{
		while(true) 
		{
			Random rand = new Random();
			int randomIndex = rand.nextInt(POPULATION);
			BiologicalNode<Bacterium> tempTail = bacteriaPopulation.tail;
			
			for (int i = bacteriaPopulation.length - 1; i >= randomIndex; i--)
			{			
				if(tempTail.bioElem.genome.getRepulsionCoef() > rand.nextDouble() && i == randomIndex
						&& tempTail.bioElem.genome.dna_mass < rand.nextDouble()*DNA.maxMass)
				{
					return tempTail.bioElem;
				}
				tempTail = tempTail.nextBioNode;
			}
		}
		
	}
	public static void naturalSelection()
	{
		Bacterium father = fittestParent();
		Bacterium mother = fittestParent();
		Bacterium.crossBacteria(father, mother);
		
	}
	public static void disappearAllConsumed()
	{
		BiologicalNode<Bacterium> tempTail = bacteriaPopulation.tail;
		for(int i = 0; i < bacteriaPopulation.length; i++) 
		{
			tempTail.bioElem.disappear();
			tempTail = tempTail.nextBioNode;
		}
	}
	public static void drawAllBacteria(Graphics g)
	{
		BiologicalNode<Bacterium> tempTail = bacteriaPopulation.tail;
		for(int i = 0; i < bacteriaPopulation.length; i++) 
		{
			tempTail.bioElem.move(0.1);
			tempTail.bioElem.bounce();
			tempTail.bioElem.drawBacteria(g);
			tempTail = tempTail.nextBioNode;
		}
		
	}
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
	public static double findAverageFitness()
	{
		double fitnessSum = 0;
		BiologicalNode<Bacterium> tempTail = bacteriaPopulation.tail;
		for(int i = 0; i < bacteriaPopulation.length; i++) 
		{
			fitnessSum += tempTail.bioElem.genome.fitnessValue;
			tempTail = tempTail.nextBioNode;
		}
	return fitnessSum/POPULATION;
	}
}