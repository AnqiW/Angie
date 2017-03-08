package db.prototying;

import java.util.Iterator;

/**
 * Created by zitongyang on 2/23/17.
 */
public class LinkedListDeque<Item> implements List<Item> {

    /**
     * A raw data structure ItemNode
     */
    private class ItemNode {
        private ItemNode prev;
        private ItemNode next;
        private Item elem;

        /**
         * This is a constructor for raw data type ItemNode
         */
        ItemNode(ItemNode previousNode, Item element, ItemNode nextNode) {
            prev = previousNode;
            elem = element;
            next = nextNode;
        }

        /**
         * This is a constructor for an empty ItemNode
         */
        ItemNode() {
            prev = null;
            next = null;
            elem = null;
        }

        public Item get(int index) {
            if (index == 0) {
                return this.next.elem;
            }
            return this.next.get(index - 1);
        }

    }

    /**
     * The following are three components for LinkedListDeque.
     */
    private ItemNode sentinel;
    private int length;

    public void lengthMinusOne() {
        length--;
    }

    /**
     * This is a private iterator class that helps iterating
     * through the whole deque
     */
    private class DequeIterator<Item> implements Iterator<Item> {
        private LinkedListDeque originalDeque;

        public DequeIterator(LinkedListDeque deque) {
            originalDeque = deque;
        }

        @Override
        public boolean hasNext() {
            return originalDeque.length() > 0;
        }

        @Override
        public Item next() {
            Item temp = (Item) originalDeque.removeFirst();
            return temp;
        }
    }

    /**
     * This is the constructor for an empty LinkedListDeque,
     * a sentinel node.
     */
    public LinkedListDeque() {

        // First we build an empty circular ItemNode */
        ItemNode emptynode = new ItemNode();
        emptynode.elem = null;
        emptynode.prev = emptynode;
        emptynode.next = emptynode;

        // Here we add structure into LinkedListDeque */
        this.length = 0;
        this.sentinel = emptynode;
    }

    public LinkedListDeque(Item[] elems) {
        this();
        for (Item elem : elems) {
            this.addLast(elem);
        }
    }

    /**
     * Adds an element to the front of the list
     */
    public void addFirst(Item element) {
        length = length + 1;
        ItemNode newNode = new ItemNode(sentinel, element, sentinel.next);
        sentinel.next = newNode;
        sentinel.next.next.prev = newNode;
    }

    /**
     * Adds an element to the end of the list.
     */
    public void addLast(Item element) {
        length += 1;
        ItemNode newNode = new ItemNode(sentinel.prev, element, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        if (sentinel.next == sentinel) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of Items in the Deque.
     */
    public int length() {
        return length;
    }

    /**
     * Prints the Items in the Deque from first to last,
     * separated by a space.
     */
    public void printDeque() {
        ItemNode ptr = sentinel.next;
        while (!(ptr == sentinel)) {
            System.out.print(ptr.elem + " ");
            ptr = ptr.next;
        }
    }

    /** Removes and returns the Item at the front of the Deque. */
    /**
     * If no such Item exists, returns null.
     */
    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }
        ItemNode removed = sentinel.next;
        sentinel.next = removed.next;
        removed.next.prev = sentinel;
        length = length - 1;
        return removed.elem;
    }


    public void remove(int index) {
        ItemNode ptr = sentinel.next;
        if (index == 0) {
            removeFirst();
        } else {
            while (index != 1) {
                ptr = ptr.next;
                index = index - 1;
            }
            ptr.next = ptr.next.next;
            ptr = ptr.next;
            ptr.prev = ptr.prev.prev;
            length--;
        }

    }

    /** Removes and returns the item at the back of the Deque. */
    /**
     * If no such item exists, returns null.
     */
    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }
        ItemNode removed = sentinel.prev;
        removed.prev.next = sentinel;
        sentinel.prev = removed.prev;
        length = length - 1;
        return removed.elem;
    }


    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    public Item get(int index) {
        if (this.isEmpty()) {
            return null;
        }

        ItemNode ptr = sentinel.next;
        while (index != 0) {
            ptr = ptr.next;
            index = index - 1;
        }
        return ptr.elem;
    }

    public Item getRecursive(int index) {
        return sentinel.get(index);
    }

    /**
     * Replace the i-th item in the deque with a new element.
     */
    public void replace(int index, Item newElem) {
        if (this.isEmpty()) {
        }

        ItemNode ptr = sentinel.next;
        while (index != 0) {
            ptr = ptr.next;
            index = index - 1;
        }
        ptr.elem = newElem;
    }

    /**
     * Return an iterator object. Iterating through the entire list
     * should be non-destructive, thus make a copy first
     */
    public Iterator<Item> iterator() {
        LinkedListDeque<Item> temp = new LinkedListDeque<>();
        for (int i = 0; i < length(); i++) {
            temp.addLast(get(i));
        }
        Iterator<Item> resultIterator = new DequeIterator<>(temp);
        return resultIterator;
    }


    @Override
    /**
     * This method returns the concatenate of two linked list,
     * the way the method doing this should not non-destructive
     * and returns a new list.
     */
    public List<Item> concatenate(List<Item> l) {
        List result = new LinkedListDeque();
        for (Item elem : this) {
            result.addLast(elem);
        }
        for (Item elem : l) {
            result.addLast(elem);
        }
        return result;
    }

    @Override
    public boolean contains(Item elem) {
        for (Item e : this) {
            if (e.equals(elem)) {
                return true;
            }
        }
        return false;
    }
}
