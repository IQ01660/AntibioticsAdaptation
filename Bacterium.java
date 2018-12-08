import java.awt.Color;
import java.awt.Graphics;

/**
 * This class is a "phenotype", namely it describes the object that will store the
 * "genotype", which is desribed in DNA
 * @author ikramgabiyev
 *
 */

public class Bacterium extends KinematicObject implements Consumable, WindowInfo
{
	public static final double BAC_RADIUS = 5; // the radius of all bacteria
	public static final double UNIVERSAL_COEF = -5000; // used to calculate repulsion force ( see changeAcceleration() )
	public static final int OFFSET = 20; // an offset used to pose some limit to where bacteria can emerge
	
	public Color bacColor; // the color of the Bacterium
	public DNA genome; // stores properties which will be sent to the next generation
	public double piePiece; // value between 0 and 1 - shows how long a bacterium lived compared to others
	
	/**
	 * sets the parameters of the bacterium
	 * according to their genome (there are some other properties as well)
	 * The purpose of this method is not to repeat the same code
	 * in both constructors
	 */
	
	private void setBacteriumParameters() {
		this.lifeTime = 0;
		this.radius = BAC_RADIUS;
		this.mass = this.genome.dna_mass;
		this.velocity = this.genome.initVelocity;
		this.acceleration = new Vector(0, 0); //no acceleration initially
		this.position = new Vector(DNA.randomSample(OFFSET, WindowInfo.WINDOW_WIDTH - OFFSET), DNA.randomSample(OFFSET, WindowInfo.WINDOW_HEIGHT - OFFSET));
		this.bacColor = new Color(50,Math.min((int)(255*this.genome.fitnessValue), 255),100);
		Evolution.bacteriaPopulation.append(this);
	}
	
	/**
	 * produces a DNA with random parameters using 
	 * the first constructor of DNA. setBacteriumParameters()
	 * is then called to set all properties of genome to the bacteria
	 */
	
	public Bacterium()
	{
		this.genome = new DNA();
		this.setBacteriumParameters();
	}
	
	/**
	 * produces a Bacterium with a pre-defined DNA
	 * Is used when creating a child from parents
	 * @param _dna - pre-defined DNA
	 */
	
	public Bacterium(DNA _dna)
	{
		this.genome = _dna;
		this.setBacteriumParameters();
	}
	
	/**
	 * checks whether the bacterium is eaten by any antibiotic
	 * @return true - if is eaten
	 */
	
	public boolean isIn() 
	{
		BiologicalNode<Antibiotic> tempTail = Evolution.antibioPopulation.tail;
		for (int i = Evolution.antibioPopulation.length - 1; i >= 0; i--)
		{
			if( Vector.getDistance(this.position, tempTail.bioElem.position) <= (tempTail.bioElem.radius) )
			{
				return true;
			}
			tempTail = tempTail.nextBioNode;
		}
		return false;
	}
	
	/**
	 * Removes the bacteria from DS if isIn returns true
	 * If a bacterium disappears naturalSelection() is called,
	 * which produces a new bacterium using natural selection
	 * (see Evolution.java for more info)
	 */
	
	@Override
	public void disappear() 
	{
		// TODO Auto-generated method stub
		if (this.isIn()) 
		{
			Evolution.bacteriaPopulation.remove(this);
			Evolution.naturalSelection();
		}
	}
	 
	/**
	 * This method temporarily changes the acceleration of a bacterium
	 * while it is near the antibiotic. A repulsive force is created
	 * based on bacteria's repulsionCoefficient and its mass
	 * The formula is: a = G * (repCoef / m * x)
	 * It also stabilizes the velocity of the bacterium
	 * when it leaves the "danger zone"
	 */
	
	public void changeAcceleration()
	{
		Antibiotic closestAnti = Evolution.antibioPopulation.peek();
		
		if(Math.abs((closestAnti.position.x-this.position.x)) < closestAnti.radius + 20) {
			
			double accelerationX = UNIVERSAL_COEF * this.genome.getRepulsionCoef()/((closestAnti.position.x-this.position.x)*this.mass);
			double accelerationY = UNIVERSAL_COEF * this.genome.getRepulsionCoef()/((closestAnti.position.y-this.position.y)*this.mass);
			this.acceleration = new Vector(accelerationX, accelerationY);
		}
		if(this.velocity.magnitude > DNA.maxComponentSpeed*1.41) {
			this.velocity.x = (10/this.velocity.magnitude)*this.velocity.x;
			this.velocity.y = (10/this.velocity.magnitude)*this.velocity.y;
		}
		if(Math.abs((closestAnti.position.x-this.position.x)) > closestAnti.radius + 50) {
			this.acceleration = new Vector(0, 0);
		}
		
	}
	
	/**
	 * Draws Bacteria while calling changeAcceleration()
	 * this makes the program constantly control whether a repulsive force has to be created
	 * Furthermore, lifeTime of the Bacterium is increased, as well as the lifeTimeSum: see BiologicalDS.java
	 * the piePiece field is also changed depending on the lifeTime and lifeTimeSum
	 * Note: method is called only for those bacteria who are in DS (see Evolution.java)
	 * @param g
	 */
	
	public void drawBacteria(Graphics g)
	{
		this.changeAcceleration();
		this.lifeTime++;
		Evolution.bacteriaPopulation.lifeTimeSum++;
		System.out.println(Evolution.findAverageFitness());
		this.piePiece = this.lifeTime / Evolution.bacteriaPopulation.lifeTimeSum;
		g.setColor(bacColor);
		g.fillOval((int)(this.position.x - this.radius), (int)(this.position.y - this.radius), (int)(2*this.radius), (int)(2*this.radius));
	}
	
	/**
	 * produces a new bacterium using two parents.
	 * Note DNA.cross method was used to cross DNA of parents
	 * @param _father
	 * @param _mother
	 */
	
	public static void crossBacteria(Bacterium _father, Bacterium _mother)
	{
		Bacterium child = new Bacterium(DNA.cross(_father.genome, _mother.genome));
	}
}