package apps;

import list.Iterator;
import list.LinkedPositionalList;
import list.Position;
import list.PositionalList;

public class FavoriteListMTF<E> extends FavoritesList<E> {
     protected void moveUp(Position<Item<E>> p) {
    	 if(p!=list.first()) {
    		 list.addFirst(list.remove(p));
    	 }
     }
     public list.Iterable<E> getFavorities(int k) throws IllegalArgumentException{
    	 if(k<0||k>size())
    		 throw new IllegalArgumentException();
    	 PositionalList<Item<E>> temp = new LinkedPositionalList<>();
    	 Iterator<Item<E>> it = list.iterator();
    	 while(it.hasNext()) {
    		 temp.addLast(it.next());
    	 }
    	 PositionalList<E> res = new LinkedPositionalList<>();
    	 for(int j=0; j<k;j++) {
    		 Position<Item<E>> highPos = temp.first();
    		 Position<Item<E>> walk = temp.after(highPos);
    		 while(walk!=null) {
    			 if(count(walk)>count(highPos) ){
    				 highPos = walk;
    			 }
    			 walk = temp.after(walk);
    			 
    		 }
    		 res.addLast(value(highPos));
    		 temp.remove(highPos);
    	 }
    	 return res;
    	 
     }
}