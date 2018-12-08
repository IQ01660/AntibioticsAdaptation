import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * This method will control the animation
 * as well as call the add mouse event listener
 */
public class Main extends JPanel implements WindowInfo
{
	
	//static and final fields
	private static final int FPS = 60;
	
	public static void main(String[] args) 
	{
		Vector a = new Vector(5,3);
		JFrame simulationFrame = new JFrame("Antibiotics Simulation");
		simulationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		simulationFrame.setContentPane(new Main());
		simulationFrame.pack();
		simulationFrame.setVisible(true);
	}
	
	public void addNotify() {
        super.addNotify();
        requestFocus();
    }
	
	public Main() 
	{
		this.setPreferredSize(new Dimension(WindowInfo.WINDOW_WIDTH, WindowInfo.WINDOW_HEIGHT));
		Thread genSimulationTimeline = new Thread(new SimulationRunner());
		genSimulationTimeline.start();
		Evolution.randomlyPopulate();
		this.addKeyListener(Evolution.antibioPopulation.peek());
		this.addMouseListener(Evolution.antibioPopulation.peek());
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//back
		g.setColor(new Color(253, 255, 163));
		g.fillRect(0, 0, WindowInfo.WINDOW_WIDTH, WindowInfo.WINDOW_HEIGHT);
		//animation drawing

		Evolution.drawAllBacteria(g);
		Evolution.drawAllAntibiotics(g);
		
	}
	class SimulationRunner implements Runnable
	{

		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			while(true) 
			{
				Evolution.disappearAllConsumed();
				System.out.println(Evolution.findAverageFitness());
				//repeating animation
				repaint();
				try 
				{
					Thread.sleep(1000/FPS);
				}
				catch(Exception e)
				{
					System.out.println("Frames are not working properly");
				}
			}
		}
		
	}
}