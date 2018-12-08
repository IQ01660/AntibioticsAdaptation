import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// I will make changes to this class

public class Antibiotic extends KinematicObject implements Hungry, KeyListener, MouseListener
{
	public static final double maxComponentSpeed = 20;
	public static boolean stat = false;
	public Antibiotic()
	{
		/*
		 * this will change later when
		 * mouse interactions are 
		 * implemented
		 */
		this.lifeTime = 100;
		this.radius = 100;
		this.velocity = new Vector(20, 20);
		this.position = new Vector(500, 500);
		this.mass = 500;
		this.acceleration = new Vector(0, 0);
		Evolution.antibioPopulation.append(this);
		
	}
	public void drawAntibiotic(Graphics g)
	{
		g.setColor(new Color(181, 9, 9));
		g.fillOval((int)(this.position.x - this.radius), (int)(this.position.y - this.radius), (int)(this.radius*2), (int)(this.radius*2));
		g.setColor(new Color(58, 255, 137));
		g.fillOval((int)(this.position.x - this.radius/2), (int)(this.position.y - this.radius/2), (int)(this.radius), (int)(this.radius));
	}
	@Override
	public void eat() 
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyChar() == 'w') {
			System.out.println("YES");
			this.velocity = new Vector(0, -maxComponentSpeed);
		}
		if (e.getKeyChar() == 'a') {
			this.velocity = new Vector(-maxComponentSpeed, 0);
		}
		if (e.getKeyChar() == 'd') {
			this.velocity = new Vector(maxComponentSpeed, 0);
		}
		if (e.getKeyChar() == 's') {
			this.velocity = new Vector(0, maxComponentSpeed);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Antibiotic newOne = new Antibiotic();
		newOne.position = new Vector(e.getX(), e.getY());
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		stat = true;
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}