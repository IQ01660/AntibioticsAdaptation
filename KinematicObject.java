public abstract class KinematicObject implements WindowInfo
{
	public Vector position;
	public Vector velocity;
	public Vector acceleration;
	public double mass;
	public double radius;
	
	/**
	 * changes the position of the object based on 
	 * its current position, velocity and acceleration
	 */
	public void move(double time)
	{
		this.velocity = this.velocity.add(this.acceleration.multiply(time));
		this.position = this.position.add(this.velocity.multiply(time));
	}
	/**
	 * does not allow the object to go off the screen
	 */
	public void bounce()
	{
		if(isBeyondX()) 
		{
			this.velocity.x = -1 * this.velocity.x;
		}
		else if(isBeyondY())
		{
			this.velocity.y = -1 * this.velocity.y;
		}
	}
	//checks if the object goes beyond the left or right of the screen
	private boolean isBeyondX()
	{
		if (this.position.x - this.radius < 0){return true;}
		else if (this.position.x + this.radius > WindowInfo.WINDOW_WIDTH) {return true;}
		else {return false;}
	}
	//checks if the object goes beyond the top or bottom of the screen
	private boolean isBeyondY()
	{
		if (this.position.y - this.radius < 0){return true;}
		else if (this.position.y + this.radius > WindowInfo.WINDOW_HEIGHT) {return true;}
		else {return false;}
	}
}