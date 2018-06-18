package list;

public interface Iterator<E> {
       boolean hasNext();
       E next();
       default void remove() throws IllegalStateException  {
    	   throw new UnsupportedOperationException("Remove");
       }
}
