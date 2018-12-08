import java.util.Random;

/**
 * DNA class stores information that will be sent to the next generation
 * It also has methods that can initiate mutation and cross the DNA of parents
 * to produce a new DNA. Note: Bacterium class has a field called "genome" of type DNA
 * @author ikramgabiyev
 */

public class DNA
{
	//static and final fields
	public static final double maxComponentSpeed = 10; //max speed of each component
	public static final double maxMass = 10; // max possible mass for a bacteria
	public static final double mutationProbability = 0.05; // the chance of mutating a DNA
	private static Random randGenerator = new Random();
	
	public Vector initVelocity; 
	public double dna_mass; // the same as bacteria's mass
	public double fitnessValue; // right now used just to paint the bacteria according to its fitness
	
	// determines the strength of the force between the bacteria and antibiotic
	private double repulsionCofficient; 
	
	/**
	 * Returns the repulsion Coefficient (getter)
	 * @return repulsionCoefficient
	 */
	
	public double getRepulsionCoef()
	{
		return this.repulsionCofficient;
	}
	
	/**
	 * Sets the repulsionCoefficient to 
	 * a new value
	 * @param the new value of repulsionCoefficient
	 */
	
	public void setRepulsionCoef(double _repCoef) 
	{
		if(_repCoef >= 0 && _repCoef <= 1)
		{
			this.repulsionCofficient = _repCoef;
		}
		else 
		{
			System.out.println("wrong repulsion coefficient");
		}
	}
	
	/**
	 * creates a DNA using random parameters
	 */
	
	public DNA() 
	{
		this.initVelocity = new Vector(randomSample(-maxComponentSpeed, maxComponentSpeed), randomSample(-maxComponentSpeed, maxComponentSpeed));
		this.dna_mass = randomSample(0, maxMass);
		//this.setRepulsionCoef(randGenerator.nextGaussian()*0.1 + 0.5);//normal distribution
		this.repulsionCofficient = randomSample(0, 1);
		this.fitnessValue = this.repulsionCofficient / (this.dna_mass);
	}
	
	/**
	 * Creates a DNA using pre-defined parameters
	 * Is used when producing a new bacteria from parents
	 * @param _initVel
	 * @param _mass
	 * @param _repCoef
	 */
	
	public DNA(Vector _initVel, double _mass, double _repCoef)
	{
		this.initVelocity = _initVel;
		this.dna_mass = _mass;
		setRepulsionCoef(_repCoef);
		this.fitnessValue = this.repulsionCofficient / (this.dna_mass);
	}
	
	/**
	 * Returns a random double between the min and max
	 * @param _min 
	 * @param _max
	 * @return a random double from min to max
	 */
	
	public static double randomSample(double _min, double _max) 
	{
		return _min + (_max - _min)*randGenerator.nextDouble();
	}	
	
	/**
	 * In a genetic algorithm mutations have to occur
	 * to add some variation; when new bacteria are crossed
	 * this method is called. It may change the DNA of a new bacteria
	 * with some probability
	 * @param prob - should be between 0 and 1
	 * * see mutationProbability field in DNA.java
	 */
	
	private void mutate(double prob)
	{
		if(randomSample(0, 1) < prob) {
			this.initVelocity = new Vector(randomSample(-maxComponentSpeed, maxComponentSpeed), randomSample(-maxComponentSpeed, maxComponentSpeed));
			this.dna_mass = randomSample(0, maxMass);
			this.setRepulsionCoef(randomSample(0, 1));
		}
	}
	
	/**
	 * Returns a new DNA by taking some properties from
	 * father DNA and mother DNA. Note: mutate(prob) is called here as well.
	 * @param _father - gives the initVelocity and mass
	 * @param _mother - gives the repulsion Coefficient
	 * @return new child DNA
	 */
	
	public static DNA cross(DNA _father, DNA _mother)
	{
		Vector childInitVel = _father.initVelocity;
		double childMass = _father.dna_mass;
		double childRepCoef = _mother.getRepulsionCoef();
		DNA childDNA = new DNA(childInitVel, childMass, childRepCoef);
		childDNA.mutate(DNA.mutationProbability);
		return childDNA;
	}
}