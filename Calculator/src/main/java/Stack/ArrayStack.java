package Stack;


public class ArrayStack<E> implements Stack<E> {
    private static final int capacity = 668;
    private E[] data;
    private int now = -1;

    public ArrayStack() {
        this(capacity);
    }

    public ArrayStack(int capacity) {
        data = (E[]) new Object[capacity];
    }



    @Override
    public int size() {
        return now + 1;
    }

    @Override
    public void push(E e) {
        if (size() == data.length)
            System.out.println("stack is full");
        else data[++now] = e;
    }

    @Override
    public boolean isEmpty() {
        return now == -1;
    }

    @Override
    public E pop() {
        if (isEmpty())
            return null;
        else {
            E answer = data[now];
            data[now] = null;
            now--;
            return answer;
        }
    }

    @Override
    public E peek() {
        if (isEmpty())
            return null;
        else return data[now];
    }
}
