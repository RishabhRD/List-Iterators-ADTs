package apps;

import list.Position;
import list.Iterable;
import list.Iterator;

import list.LinkedPositionalList;
import list.PositionalList;

public class FavoritesList <E>{
	protected static class Item<E>{
		private E value;
		private int count = 0;
		public Item(E value) {
			this.value = value;
		}
		public int getCount() { return count; }
		public E getValue() {return value;}
		public void increment() {count++;}
	}
	PositionalList<Item<E>> list  = new LinkedPositionalList<>();
	protected E value(Position<Item<E>> p) {
		return p.getElement().getValue();
	}
	protected int count(Position<Item<E>> p) {
		return p.getElement().getCount();
	}
	protected Position<Item<E>> findPosition(E e){
		Position<Item<E>> walk = list.first();
		while(walk!=null && !e.equals(value(walk))) {
			walk= list.after(walk);
		}
		return walk;
	}
	protected void moveUp(Position<Item<E>> p) {
		int cnt = count(p);
		Position<Item<E>> walk = p;
		while(walk!=list.first()&&count(list.before(walk))<cnt) {
			walk = list.before(walk);
		}
		if(walk!=p) {
			list.addBefore(walk,list.remove(p));
		}
	}
	public int size() {
		return list.size();
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
	public void access(E e) {
		Position<Item<E>>  p = findPosition(e);
		if(p==null)
			list.after(p);
		p.getElement().increment();
		moveUp(p);
	}
	public void remove(E e) {
		Position<Item<E>> p = findPosition(e);
		if(p!=null)
			list.remove(p);
	}
	public Iterable<E> getFavorities(int k) throws IllegalArgumentException{
		if(k<0||k>size()) {
			throw new IllegalArgumentException("Invalid index");
		}
		PositionalList<E> res = new LinkedPositionalList<>();
		Iterator<Item<E>> itr = list.iterator();
		for(int j=0;j<k;j++) {
			res.addLast(itr.next().getValue());
		}
		return res;
	}
	

}
