import java.util.Random;

public class DNA
{
	//static and final fields
	public static final double maxComponentSpeed = 150;//max speed of each component
	public static final double maxMass = 50;// max possible mass for a bacteria
	public static final double mutationProbability = 0.05;
	
	public Vector initVelocity;
	public double dna_mass; // the same as bacteria's mass
	
	// determines the strength of the force between the bacteria and antibiotic
	private double repulsionCofficient; 
	private Random randGenerator = new Random();
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
		this.initVelocity = new Vector(this.randomSample(0, maxComponentSpeed), this.randomSample(0, maxComponentSpeed));
		this.dna_mass = this.randomSample(0, maxMass);
		this.setRepulsionCoef(this.randomSample(0, 1));
	}
	public DNA(Vector _initVel, double _mass, double _repCoef)
	{
		this.initVelocity = _initVel;
		this.dna_mass = _mass;
		setRepulsionCoef(_repCoef);
	}
	//generates random double number using the maximum and minimum value
	private double randomSample(double _min, double _max) 
	{
		return _min + (_max - _min)*randGenerator.nextDouble();
	}	
	//adds some variation to our system
	//probability should be between 0 and 1
	private void mutate(double prob)
	{
		if(this.randomSample(0, 1) < prob) {
			this.initVelocity = new Vector(this.randomSample(0, maxComponentSpeed), this.randomSample(0, maxComponentSpeed));
			this.dna_mass = this.randomSample(0, maxMass);
			this.setRepulsionCoef(this.randomSample(0, 1));
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