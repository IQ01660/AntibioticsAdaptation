import java.awt.Color;
import java.awt.Graphics;

public class Antibiotic extends KinematicObject implements Hungry
{
	public int lifeTime = 100;
	
	public Antibiotic()
	{
		/*
		 * this will change later when
		 * mouse interactions are 
		 * implemented
		 */
		this.radius = 100;
		this.velocity = new Vector(50, 50);
		this.position = new Vector(500, 500);
		this.mass = 500;
		this.acceleration = new Vector(0, 0);
		Evolution.antibioPopulation.append(this);
	}
	public void drawAntibiotic(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.fillOval((int)(this.position.x - this.radius), (int)(this.position.y - this.radius), (int)(this.radius*2), (int)(this.radius*2));
	}
	@Override
	public void eat() 
	{
		// TODO Auto-generated method stub
		this.lifeTime++;
	}
	
}