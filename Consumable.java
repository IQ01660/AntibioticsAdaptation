public interface Consumable
{
	/**
	 * makes the object disappear if it is eaten
	 */
	
	public void disappear();
	
	/**
	 * return true if the object is consumed
	 * @return true - if inside another blob
	 */
	
	public boolean isIn();
}