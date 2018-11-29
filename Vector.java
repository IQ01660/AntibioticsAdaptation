public class Vector 
{
	public double x;
	public double y;
	public double magnitude;
	public Vector(double _x, double _y)
	{
		this.x = _x;
		this.y = _y;
		this.magnitude = Math.sqrt((_x*_x) + (_y*_y));
	}
	
	public Vector add(Vector _v2) 
	{
		return new Vector(this.x + _v2.x, this.y + _v2.y);
	}
	public Vector subtract(Vector _v2) 
	{
		return new Vector(this.x - _v2.x, this.y - _v2.y);
	}
	public Vector multiply(double coefficient)
	{
		return new Vector(this.x * coefficient, this.y * coefficient);
	}
	public Vector divide(double coefficient)
	{
		return new Vector(this.x / coefficient, this.y / coefficient);
	}
	
	/**
	 * get the magnitude of the difference
	 * between two vectors (such as position)
	 */
	public static double getDistance(Vector _v1, Vector _v2)
	{
		return Math.sqrt( Math.pow(_v1.x - _v2.x, 2) + Math.pow(_v1.y - _v2.y, 2) );	
	}
}