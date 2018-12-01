import java.awt.Color;
import java.awt.Graphics;

public class Bacterium extends KinematicObject implements Consumable, WindowInfo
{
	public static final double BAC_RADIUS = 5;
	public static final double UNIVERSAL_COEF = -5000;
	public static final int OFFSET = 20;
	public Color bacColor;
	public DNA genome;
	//random bacteria
	public Bacterium()
	{
		this.radius = BAC_RADIUS;
		this.genome = new DNA();
		this.mass = this.genome.dna_mass;
		this.velocity = this.genome.initVelocity;
		this.acceleration = new Vector(0, 0); //no acceleration initially
		this.position = new Vector(DNA.randomSample(OFFSET, WindowInfo.WINDOW_WIDTH - OFFSET), DNA.randomSample(OFFSET, WindowInfo.WINDOW_HEIGHT - OFFSET));
		this.bacColor = new Color(50,Math.min((int)(255*this.genome.fitnessValue), 255),100);
		Evolution.bacteriaPopulation.append(this);
	}
	
	public Bacterium(DNA _dna)
	{
		this.radius = BAC_RADIUS;
		this.genome = _dna;
		this.mass = this.genome.dna_mass;
		this.velocity = this.genome.initVelocity;
		this.acceleration = new Vector(0, 0); //no acceleration initially
		this.position = new Vector(DNA.randomSample(OFFSET, WindowInfo.WINDOW_WIDTH - OFFSET), DNA.randomSample(OFFSET, WindowInfo.WINDOW_HEIGHT - OFFSET));
		this.bacColor = new Color(50,Math.min((int)(255*this.genome.fitnessValue), 255),100);
		Evolution.bacteriaPopulation.append(this);
	}
	
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
	public void drawBacteria(Graphics g)
	{
		this.changeAcceleration();
		g.setColor(bacColor);
		g.fillOval((int)(this.position.x - this.radius), (int)(this.position.y - this.radius), (int)(2*this.radius), (int)(2*this.radius));
	}
	
	public static void crossBacteria(Bacterium _father, Bacterium _mother)
	{
		Bacterium child = new Bacterium(DNA.cross(_father.genome, _mother.genome));
	}
}