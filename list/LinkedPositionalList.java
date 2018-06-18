package list;
import java.util.NoSuchElementException;

public class LinkedPositionalList<E> implements PositionalList<E> {
	//Nested Node Class
     private static class Node<E> implements Position<E>{
    	 private Node<E> next;
    	 private Node<E> prev;
    	 private E element;
    	 
    	 public Node(E e, Node<E> p, Node<E> n) {
    		 next =n;
    		 prev = p;
    		 element = e;
    	 }
    	 public Node<E> getNext(){
    		 return next;
    	 }
    	 public Node<E> getPrev(){
    		 return prev;
    	 }
    	 public E getElement() {
    		 if(next==null)
    			 throw new IllegalStateException("Position no longer valid");
    		 return element;
    	 }
    	 public void setNext(Node<E> n) {
    		 next = n;
    	 }
    	 public void setPrev(Node<E> p) {
    		 prev = p;
    	 }
    	 public void setElement(E e) {
    		 element = e;
    	 }
     }
     //End of nested node class.
     
     //Nested PositionIterator Class
     private class PositionIterator implements Iterator<Position<E>>{
        private Position<E> cursor =first();  //Next position to be returned.
        private Position<E> recent = null;    //Previously  visited position.
        
        
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return cursor!=null;
		}

		@Override
		public Position<E> next() throws NoSuchElementException{
			// TODO Auto-generated method stub
			if(cursor==null) throw new NoSuchElementException("Nothing left to iterate");
			recent = cursor;
			cursor = after(cursor);
			return recent;
		}
		@Override
		public void remove() throws IllegalStateException{
			if(recent == null) throw new IllegalStateException("Nothing to remove");
			LinkedPositionalList.this.remove(recent);
			recent = null; //Not allow removing until next() is called.
		}
    	 
     }
     //End of Nested PositionIterator Class
     
     private class PositionListIterator implements ListIterator<Position<E>>{
        private Position<E> next = first();
        private Position<E> recent = null;
        int index =-1;
		@Override
		public void add(Position<E> e) {
			// TODO Auto-generated method stub
			
			Node<E> n = validate(next);
			addBetween(e.getElement(), n.getPrev(), n);
			index++;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return next!=null;
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return validate(recent).getPrev()!=null;
		}

		@Override
		public Position<E> previous() {
			// TODO Auto-generated method stub
			if(!hasPrevious())
				throw new IllegalStateException("Nothing to iterate");
			Position<E> temp = recent;
			recent = position(validate(recent).getPrev());
			next = temp;
			index--;
			return recent;
		}

		@Override
		public Position<E> next() {
			// TODO Auto-generated method stub
			if(!hasNext())
				throw new IllegalStateException("Nothing to iterate");
			Position<E> temp = recent;
			recent = position(validate(recent).getNext());
			next = temp;
			index++;
			return recent;
		}

		@Override
		public int nextIndex() {
			// TODO Auto-generated method stub
			return index+1;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return index-1;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			if(recent == null) throw new IllegalStateException("Nothing to remove");
			LinkedPositionalList.this.remove(recent);
			recent = null;
		}

		@Override
		public void set(Position<E> e) {
			// TODO Auto-generated method stub
			validate(recent).setElement(e.getElement());
		}
    	 
     }
     private class ElementListIterator implements ListIterator<E>{
        PositionListIterator itr = new PositionListIterator();
		@Override
		public void add(E e) {
			// TODO Auto-generated method stub
			Position<E> p = new Node(e,null,null);
			itr.add(p);
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return itr.hasNext();
		}

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return itr.hasPrevious();
		}

		@Override
		public E previous() {
			// TODO Auto-generated method stub
			return itr.previous().getElement();
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			return itr.next().getElement();
		}

		@Override
		public int nextIndex() {
			// TODO Auto-generated method stub
			return itr.nextIndex();
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return itr.previousIndex();
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			itr.remove();
		}

		@Override
		public void set(E e) {
			// TODO Auto-generated method stub
			Position<E> p = new Node(e,null,null);
			itr.set(p);
		}
    	 
     }
     //Nested PositionIterable class
     private class PositionIterable implements Iterable<Position<E>>{

		@Override
		public Iterator<Position<E>> iterator() {
			// TODO Auto-generated method stub
			return new PositionIterator();
		}
    	 
     }
     //End of NestedIterable class
     private class ListIterable implements Iterable<E>{

		@Override
		public Iterator<E> iterator() {
			// TODO Auto-generated method stub
			return new ElementListIterator();
		}
    	 
     }
     //Nested ElementIterator class.
     private class ElementIterator implements Iterator<E>{
    	PositionIterator it = new PositionIterator();
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
            return it.hasNext();
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			return it.next().getElement();
		}
		@Override
		public void remove() {
			it.remove();
		}
    	 
     }
     //End of Nested ElementIterator class.
     
     private Node<E> header = null;
     private Node<E> trailer = null;
     int size =0;
     public ListIterator listIterator() {
    	 ListIterable it = new ListIterable();
    	 return (ListIterator)it.iterator();
     }
     public Iterable<Position<E>> positions(){
    	 return new PositionIterable();
     }
     public LinkedPositionalList() {
    	 header = new Node<E>(null,null,null);
    	 trailer = new Node<E>(null,header,null);
    	 header.setNext(trailer);
     }
     
     private Node<E> validate(Position<E> p) throws IllegalArgumentException{
    	 if(!(p instanceof Node))
    		 throw new IllegalArgumentException("Invalid postition");
    	 Node<E> node = (Node<E>) p;
    	 if(node.getNext()==null)
    		 throw new IllegalArgumentException("p is not in the list now.");
    	 return node;
     }
     private Position<E> position(Node<E> node){
    	 if(node==header||node==trailer)
    		 return null;
    	 return node;
     }

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size==0;
	}

	@Override
	public Position<E> first() {
		// TODO Auto-generated method stub
		return position(header.getNext());
	}

	@Override
	public Position<E> last() {
		// TODO Auto-generated method stub
		return position(trailer.getPrev());
	}

	@Override
	public Position<E> before(Position<E> p) throws IllegalStateException {
		// TODO Auto-generated method stub
		Node<E> node = validate(p);
		return position(node.getNext());
	}

	@Override
	public Position<E> after(Position<E> p) throws IllegalStateException {
		// TODO Auto-generated method stub
		Node<E> node = validate(p);
		return position(node.getNext());
	}

	@Override
	public Position<E> addFirst(E e) {
		// TODO Auto-generated method stub
		return addBetween(e, header, header.getNext());
	}

	@Override
	public Position<E> addLast(E e) {
		// TODO Auto-generated method stub
		return addBetween(e, trailer.getPrev(), trailer);
	}

	@Override
	public Position<E> addBefore(Position<E> p, E e) throws IllegalStateException {
		// TODO Auto-generated method stub
		Node<E> node = validate(p);
		return addBetween(e, node.getPrev(), node);
	}

	@Override
	public Position<E> addAfter(Position<E> p, E e) throws IllegalStateException {
		// TODO Auto-generated method stub
		Node<E> node = validate(p);
		return addBetween(e, node, node.getNext());
	}

	@Override
	public E set(Position<E> p, E e) throws IllegalStateException {
		// TODO Auto-generated method stub
		Node<E> node = validate(p);
		E ans = node.getElement();
		node.setElement(e);
		return ans;
		
	}

	@Override
	public E remove(Position<E> p) throws IllegalStateException {
		// TODO Auto-generated method stub
		Node<E> node = validate(p);
		node.getPrev().setNext(node.getNext());
		node.getNext().setPrev(node.getPrev());
		E ans = node.getElement();
		node.setElement(null);
		node.setNext(null);
		node.setPrev(null);
		size--;
		return ans;
		
	}
     
     private Position<E> addBetween(E e, Node<E> prev,Node<E> next){
    	 Node<E> newest = new Node<E>(e,prev,next);
    	 prev.setNext(newest);
    	 next.setPrev(newest);
    	 size++;
    	 return position(newest);
     }
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new ElementIterator();
	}
    public static void insertionSort(PositionalList<Integer> list) {
    	Position<Integer> marker = list.first();
    	while(marker!=list.last()) {
    		Position<Integer> pivot = list.after(marker);
    		int value = pivot.getElement();
    		if(value>marker.getElement()) {
    			marker = pivot;
    		}
    		else {
    			Position<Integer> walker = marker;
    			while(walker!=list.first()&&list.before(walker).getElement()>value)
    				walker = list.before(walker);
    			list.remove(pivot);
    			list.addBefore(walker	, value);
    		}
    	}
    }
	
	
     
     
     
     
     
  
     
     
}
