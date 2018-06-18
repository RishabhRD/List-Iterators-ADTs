package list;

import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E>,Iterable<E> {
    private static final int CAPACITY = 16;
    private int size =0;
    private E[] data;
    
    
    //Nested Iterator Class..... Lazy Iterator
    
    private class ArrayIterator implements Iterator<E>{
    	private int j=0;      //next element index to be returned
    	boolean removable = false;
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return j<size;
		}
		@Override
		public E next() {
			// TODO Auto-generated method stub
			if(j>=size) throw new NoSuchElementException("No next element");
			removable = true;
			return data[j++];
		}
		
		@Override
		public void remove() throws IllegalStateException {
			if(!removable) throw new IllegalStateException("Nothing to remove");
			ArrayList.this.remove(j-1);
			removable = false;  //do not allow removing until next() is called.
			j--;
		}
    	
    	
    }
    
    //End of Iterator Class
    
    public ArrayList(){
    	this(CAPACITY);
    }
    public ArrayList(int capacity) {
    	data = (E[]) new Object[capacity];
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
	public E get(int i) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		checkIndex(i,size);
		return  data[i];
	}

	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		checkIndex(i,size);
		E ans = data[i];
		data[i] = e;
		return ans;
	}

	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
        checkIndex(i,size+1);
        if(size==data.length) resize(2*data.length);
        for(int k = size-1; k>=i;k--) {
        	data[k+1] = data[k];
        }
        data[i] = e;
        size++;
	}

	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		checkIndex(i,size);
		if(size == data.length/4)
			resize(data.length/2);
		E temp =data[i];
		for(int k =i; k<size-1;k++) {
			data[k] = data[k+1];
		}
		data[size-1] = null;
		size--;
		return temp;
	}
	protected void checkIndex(int i,int n) {
		if(i<0||i>=n)
			throw new IndexOutOfBoundsException("Illegal index: "+i);
	}
	protected void resize(int capacity) {
		E[] temp = (E[]) new Object[capacity];
		for(int i=0; i<size;i++) {
			temp[i] = data[i];
		}
		data = temp;
		
	}
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new ArrayIterator();
	}

}
