public class BiologicalDS<E extends KinematicObject>
{
	public BiologicalNode<E> tail;
	public int length;
	
	public BiologicalDS() {
		this.tail = null;
		this.length = 0;
	}

	public void append(E toAppend) {
		// TODO Auto-generated method stub
		BiologicalNode<E> newTail = new BiologicalNode<E>(toAppend);
		newTail.nextBioNode = this.tail;
		this.tail = newTail;
		this.length++;
	}

	public E peek() {
		// TODO Auto-generated method stub
		if (this.length == 0) {
			return null;
		}
		else {
			return this.tail.bioElem;
		}
	}

	public E pop() {
		// TODO Auto-generated method stub
		if (this.length == 0) {
			return null;
		}
		else {
			E oldTailValue = this.tail.bioElem;
			this.tail = this.tail.nextBioNode;
			this.length--;
			return oldTailValue;
		}
	}

	public void remove(E _bioElem) {
		// TODO Auto-generated method stub
		BiologicalNode<E> tempTail = this.tail;
		if (tempTail.bioElem == _bioElem) {
			this.pop();
			return;
		}
		else {
			for(int i = length - 1; i >= 0; i--) {
				if (tempTail.nextBioNode.bioElem != _bioElem) {
					tempTail = tempTail.nextBioNode;
				}
				else {
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
	public E bioElem;
	public BiologicalNode<E> nextBioNode;
	public BiologicalNode(E _bioElem)
	{
		this.bioElem = _bioElem;
	}
}