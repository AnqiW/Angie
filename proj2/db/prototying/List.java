package db.prototying;

import java.util.Iterator;

/**
 * Created by zitongyang on 2/24/17.
 */
public interface List<Item> extends Iterable<Item> {
    void lengthMinusOne();

    void addFirst(Item elem);

    void addLast(Item elem);

    boolean isEmpty();

    int length();

    void printDeque();

    Item removeFirst();

    void remove(int index);

    Item removeLast();

    Item get(int index);

    void replace(int index, Item newItem);

    Iterator<Item> iterator();

    List<Item> concatenate(List<Item> l);

    boolean contains(Item elem);

}
