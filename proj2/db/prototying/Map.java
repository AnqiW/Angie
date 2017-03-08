package db.prototying;

import java.util.Iterator;

/**
 * Created by zitongyang on 2/24/17.
 */
public interface Map<K, V> extends Iterable<K>{
    /**
     * Returnss true if this map contains a mapping for the specified key.
     */
    boolean containsKey(K key);

    /**
     * Returns the value to which the specified key is mapped. No defined
     * behavior if the key doesn't exist (ok to crash).
     */
    V get(K key);

    /**
     * Returns the number of key-value mappings in this map.
     */
    int size();

    /**
     * Associates the specified value with the specified key in this map.
     */
    void put(K key, V value);

    /**
     *Removes the specified value and the specified key in this map.
     */
    void remove(K key);

    /**
     * Returns a list of the keys in this map.
     */
    List<K> keys();

    /**
     * The following method returns a iterator for
     * keys and values respectively
     */
    Iterator<K> iterator();

    Iterator<V> valueIterator();

}


