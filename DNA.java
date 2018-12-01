import java.util.Random;

public class DNA
{
	//static and final fields
	public static final double maxComponentSpeed = 10;//max speed of each component
	public static final double maxMass = 10;// max possible mass for a bacteria
	public static final double mutationProbability = 0.05;
	private static Random randGenerator = new Random();
	
	public Vector initVelocity;
	public double dna_mass; // the same as bacteria's mass
	public double fitnessValue;
	// determines the strength of the force between the bacteria and antibiotic
	private double repulsionCofficient; 
	
		// getters and setters
		public double getRepulsionCoef()
		{
			return this.repulsionCofficient;
		}
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
	
	public DNA() 
	{
		this.initVelocity = new Vector(randomSample(-maxComponentSpeed, maxComponentSpeed), randomSample(-maxComponentSpeed, maxComponentSpeed));
		this.dna_mass = randomSample(0, maxMass);
		//this.setRepulsionCoef(randGenerator.nextGaussian()*0.1 + 0.5);//normal distribution
		this.repulsionCofficient = randomSample(0, 1);
		this.fitnessValue = this.repulsionCofficient / (this.dna_mass);
	}
	public DNA(Vector _initVel, double _mass, double _repCoef)
	{
		this.initVelocity = _initVel;
		this.dna_mass = _mass;
		setRepulsionCoef(_repCoef);
		this.fitnessValue = this.repulsionCofficient / (this.dna_mass);
	}
	//generates random double number using the maximum and minimum value
	public static double randomSample(double _min, double _max) 
	{
		return _min + (_max - _min)*randGenerator.nextDouble();
	}	
	//adds some variation to our system
	//probability should be between 0 and 1
	private void mutate(double prob)
	{
		if(randomSample(0, 1) < prob) {
			this.initVelocity = new Vector(randomSample(0, maxComponentSpeed), randomSample(0, maxComponentSpeed));
			this.dna_mass = randomSample(0, maxMass);
			this.setRepulsionCoef(randomSample(0, 1));
		}
	}
	//crosses the DNAs of father and mother
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