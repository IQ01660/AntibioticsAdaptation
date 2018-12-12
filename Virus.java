import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * This class describes an object controlled
 * by the user that can be eaten by antibiotic but
 * who can eat bacteria like antibiotics do
 * The user has to try to eat as much as he can before
 * antibiotics die
 * @author ikramgabiyev
 */
public class Virus extends KinematicObject implements Consumable, Hungry, MouseMotionListener
{
	// the radius of all bacteria
	public static final double VIRUS_RADIUS = 20; 
	// the speed of the virus used in calculations
	public static final double SPEED = 30; 
	
	// the population of bacteria consumed; displayed on the top left 
	// corner of the screen (see Main.java)
	public static int consumedPopulation = 0;
	// shows whether the virus is eate4n by antibiotics or not
	public static boolean isExtinct = false;
	
	// color of the virus
	public Color virusColor;
	
	public Virus() {
		this.radius = VIRUS_RADIUS;
		this.velocity = new Vector(0, 0); // no velocity initially (user should move mouse)
		this.acceleration = new Vector(0, 0); // no acceleration initially
		this.position = new Vector(100, 100);
		this.virusColor = new Color(12, 255, 238);
	}
	
	/**
	 * called in Bacterium.java if a bacterium is eaten by the virus
	 * it increases consumed population
	 */
	
	@Override
	public void eat() {
		// TODO Auto-generated method stub
		consumedPopulation++;
	}
	
	/**
	 * Changes the isExtinct,
	 * which will stop the while loop
	 * in the run() method in Main.java
	 */
	
	@Override
	public void disappear() 
	{
		// TODO Auto-generated method stub
		if (this.isIn()) 
		{
			isExtinct = true;
		}
	}
	
	/**
	 * checks whether the virus is inside a antibiotic
	 */
	
	@Override
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
	 * The method changes the direction of the velocity
	 * of the virus depending on where the user moves the mouse
	 * Note: it is the direction of the velocity that is changing
	 * the ball will still bounce 
	 * @param e
	 */
	
	private void followMouse(MouseEvent e) 
	{
		double deltaX = e.getX() - this.position.x;
		double deltaY = e.getY() - this.position.y;
		double hypothenuse = Vector.getDistance(this.position, new Vector(e.getX(), e.getY()));
		double sineAlpha = deltaY / hypothenuse;
		double cosineAlpha = deltaX / hypothenuse;
		this.velocity = new Vector(SPEED * cosineAlpha, SPEED * sineAlpha);
	}
	
	/**
	 * draws the virus
	 * It also calls bounce(), move(double time)
	 * and disappear()
	 * @param Graphics g
	 */
	
	public void drawVirus(Graphics g)
	{
		this.bounce();
		this.disappear();
		this.move(0.1);
		g.setColor(this.virusColor);
		g.fillOval((int)(this.position.x - this.radius), (int)(this.position.y - this.radius), (int)(2*this.radius), (int)(2*this.radius));
	}
	
				/**
				 * this method is not used
				 */
	
				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
				}
	
	/**
	 * once user moves mouse
	 * followMouse(MouseEvent e) is called
	 */
	
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		this.followMouse(e);
	}
}