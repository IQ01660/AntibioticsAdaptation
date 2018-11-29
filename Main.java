import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
/*
 * This method will control the animation
 * as well as call the add mouse event listener
 */
public class Main extends JPanel implements WindowInfo
{
	//static and final fields
	private static final int FPS = 60;
	
	public static void main(String[] args) 
	{
		JFrame simulationFrame = new JFrame("Antibiotics Simulation");
		simulationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		simulationFrame.setContentPane(new Main());
		simulationFrame.pack();
		simulationFrame.setVisible(true);
	}
	public Main() 
	{
		this.setPreferredSize(new Dimension(WindowInfo.WINDOW_WIDTH, WindowInfo.WINDOW_HEIGHT));
		Thread genSimulationTimeline = new Thread(new SimulationRunner());
		genSimulationTimeline.start();
	}
	@Override
	public void paintComponent(Graphics g)
	{
		//animation drawing
	}
	class SimulationRunner implements Runnable
	{

		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			while(true) 
			{
				//repeting animation
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