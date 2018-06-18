package list;

public interface ListIterator<E> extends Iterator<E>{
       void add(E e);
       boolean hasNext();
       boolean hasPrevious();
       E previous();
       E next();
       int nextIndex();
       int previousIndex();
       void remove();
       void set(E e);
}
