import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
		this.addMouseMotionListener(Evolution.hiv);
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//background
		g.setColor(new Color(253, 255, 163));
		g.fillRect(0, 0, WindowInfo.WINDOW_WIDTH, WindowInfo.WINDOW_HEIGHT);
		
		//animation drawing

		Evolution.drawAllBacteria(g);
		Evolution.hiv.drawVirus(g);
		Evolution.drawAllAntibiotics(g);
		
		if (Virus.isExtinct == true || Antibiotic.isExtinct == true) {
			String finalMsg = "";
			if (Virus.isExtinct) {finalMsg = "You Lost";}
			else {finalMsg = "You Won";}
			g.setColor(new Color(44, 234, 164, 127));
			g.fillRect((int)WindowInfo.WINDOW_WIDTH/2 - 100, (int)WindowInfo.WINDOW_HEIGHT - 100, 350, 100);
			g.setColor(Color.MAGENTA);
			g.setFont(new Font("Monospaced", Font.PLAIN, 40)); 
			g.drawString(finalMsg, (int)WindowInfo.WINDOW_WIDTH/2 - 35, (int)WindowInfo.WINDOW_HEIGHT - 50);
			
			// Save if the score is better than the
			// previous best score
			int bestScore = Progress.loadScoreFromFile("src/BestScore.txt");
			Progress.saveScoreToFile(bestScore, "src/BestScore.txt");
		}
		
		//score of antibiotics
		g.setColor(new Color(44, 234, 164, 127));
		// antibiottic score rectangle
		g.fillRect((int)WindowInfo.WINDOW_WIDTH - 200, 100, 200, 100);
		// virus score rectangle
		g.fillRect(0, 100, 200, 100);
		// best score rectangle
		g.fillRect((int)WindowInfo.WINDOW_WIDTH/2 - 100, 0, 350, 100);
		g.setColor(Color.MAGENTA);
		g.setFont(new Font("Monospaced", Font.PLAIN, 40)); 
		//antibiotic score 
		g.drawString("A:"+Antibiotic.consumedPopulation, (int)WindowInfo.WINDOW_WIDTH - 180, 160);
		//virus score
		g.drawString("V:"+Virus.consumedPopulation, 20, 160);
		//best score
		g.drawString("Best:"+Progress.loadScoreFromFile("src/BestScore.txt"), (int)WindowInfo.WINDOW_WIDTH/2 - 35, 50);
	}
	class SimulationRunner implements Runnable
	{

		@Override
		public void run() 
		{
			// TODO Auto-generated method stub
			while(Virus.isExtinct == false && Antibiotic.isExtinct == false) 
			{
				Evolution.disappearAllConsumed();
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
			repaint();
		}
		
	}
}