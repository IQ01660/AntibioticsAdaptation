import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

// I will make changes to this class

public class Antibiotic extends KinematicObject implements Hungry
{
	// the speed of y and x components(different from speed)
	public static final double COMPONENT_SPEED = 20; 
	// the starting lifetime of each antibiotic
	private static final double LIFETIME = 1000; 
	// the radius of the antibiotic
	private static final double ANTI_RADIUS = 100;
	// the calories spent on moving and living
	private static final double CALORIES_DECREMENT = 2;
	// the calories gained when eating one bacterium
	private static final double CALORIES_INCREEMENT = 20;
	
	// the number of bacteria eaten
	public static int consumedPopulation = 0;
	// checks if there are still antibiotics alive
	public static boolean isExtinct = false;
	
	
	public Antibiotic()
	{
		this.lifeTime = LIFETIME;
		this.radius = ANTI_RADIUS;
		this.velocity = new Vector(COMPONENT_SPEED, COMPONENT_SPEED);
		this.position = new Vector(DNA.randomSample(WindowInfo.WINDOW_WIDTH/2, WindowInfo.WINDOW_WIDTH - this.radius), DNA.randomSample(WindowInfo.WINDOW_HEIGHT/2, WindowInfo.WINDOW_HEIGHT - this.radius));
		this.acceleration = new Vector(0, 0);
		Evolution.antibioPopulation.append(this);
		
	}
	
	/**
	 * Draws the Antibiotic
	 * @param g
	 */
	
	public void drawAntibiotic(Graphics g)
	{
		//processes that should occur in
		//before drawing every time
		this.lifeTime -= CALORIES_DECREMENT;
		this.disappear();
		
		//drawing the antibiotic
		g.setColor(new Color(181, 9, 9));
		g.fillOval((int)(this.position.x - this.radius), (int)(this.position.y - this.radius), (int)(this.radius*2), (int)(this.radius*2));
		g.setColor(new Color((int)(Math.min(58*1000/this.lifeTime, 255)), 255, 137));
		g.fillOval((int)(this.position.x - this.radius/2), (int)(this.position.y - this.radius/2), (int)(this.radius), (int)(this.radius));
		
		//drawing the lifeTime
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("Monospaced", Font.PLAIN, 20)); 
		g.drawString(this.lifeTime+"", (int)(this.position.x - this.radius/4 - 10), (int)(this.position.y + 10));
	}
	
	/**
	 * called in Bacterium.java
	 * every time a bacterium is consumed by the antibiotic
	 */
	
	@Override
	public void eat() 
	{
		// TODO Auto-generated method stub
		this.lifeTime += CALORIES_INCREEMENT;
		Antibiotic.consumedPopulation++;
	}
	
	/**
	 * remove the antibiotics
	 * unless this is the last one 
	 * P.S. the simulation will stop then.
	 */
	
	public void disappear() 
	{
		if(this.lifeTime <= 0)
		{
			if (Evolution.antibioPopulation.length == 1) {
				isExtinct = true;
				lifeTime = 0;
				return; // do not remove the last antibiotic; error will occur
			}
			Evolution.antibioPopulation.remove(this);
		}
	}
}