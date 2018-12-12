/**
 * Both classes are used to create a data structure
 * TThe classes are generic, and can only be implemented
 * for object whose classes extend KinematicObject class
 * See KinematicObject.java for more info about that class
 * @author ikramgabiyev
 *
 * @param <E>
 */

public class BiologicalDS<E extends KinematicObject>
{
	public BiologicalNode<E> tail; // the last node of the DS
	public int length;
	public double lifeTimeSum = 0; // the sum of all lifeTimes: used to calculate piePiece (see Bacterium.java)
	
	public BiologicalDS() {
		this.tail = null;
		this.length = 0;
	}

	/**
	 * Takes the biological Element [bacterium, antibiotic]
	 * and appends it to the end of the DS
	 * @param the biological element
	 */
	
	public void append(E toAppend) {
		// TODO Auto-generated method stub
		BiologicalNode<E> newTail = new BiologicalNode<E>(toAppend);
		newTail.nextBioNode = this.tail;
		this.tail = newTail;
		this.length++;
	}

	/**
	 * Returns the "value" last element of the DS
	 * @return the bioElem of the tail
	 */
	
	public E peek() {
		// TODO Auto-generated method stub
		if (this.length == 0) {
			return null;
		}
		else {
			return this.tail.bioElem;
		}
	}

	/**
	 * Removes the last element of the DS
	 * Is used in "remove(E _bioElem)"
	 * Note: removing the last element is a bit different
	 * from removing others
	 * @return the removed "value" of the node
	 */
	
	private E pop() {
		// TODO Auto-generated method stub
		if (this.length == 0) {
			return null;
		}
		else {
			E oldTailValue = this.tail.bioElem;
			this.lifeTimeSum -= oldTailValue.lifeTime;
			this.tail = this.tail.nextBioNode;
			this.length--;
			return oldTailValue;
		}
	}

	/**
	 * Removes the node from DS;
	 * uses pop() for the end Node
	 * @param _bioElem (will remove the bioElem passed in)
	 */
	
	public void remove(E _bioElem) {
		// TODO Auto-generated method stub
		
		// first check if the element to remove
		// is the tail itself, and use pop() for it
		BiologicalNode<E> tempTail = this.tail;
		if (tempTail.bioElem == _bioElem) {
			this.pop();
			return;
		}
		
		// else go through every element
		// and remove the necessary one
		else {
			for(int i = length - 1; i >= 0; i--) {
				if (tempTail.nextBioNode.bioElem != _bioElem) {
					tempTail = tempTail.nextBioNode;
				}
				else {
					if (this.lifeTimeSum > tempTail.nextBioNode.bioElem.lifeTime) {
						this.lifeTimeSum -= tempTail.nextBioNode.bioElem.lifeTime;
					}
					
					tempTail.nextBioNode = tempTail.nextBioNode.nextBioNode;
					break;
				}
			}
		}
		this.length--;
	}

}

class BiologicalNode<E extends KinematicObject>
{
	public E bioElem; // the value of the node (e.g. Bacterium or Antibiotic)
	public BiologicalNode<E> nextBioNode; // the next Node of  this node
	
	public BiologicalNode(E _bioElem)
	{
		this.bioElem = _bioElem;
	}
}