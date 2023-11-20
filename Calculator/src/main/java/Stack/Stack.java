package Stack;

public interface Stack<E> {
    int size();

    void push(E e);

    boolean isEmpty();

    E pop();

    E peek();
}
