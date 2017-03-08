package db.prototying;

/**
 * Created by zitongyang on 2/24/17.
 */

import java.util.Iterator;

import static org.junit.Assert.*;

    /**
     * An array based implementation of the Map61B class.
     */
    public class DequeMap<K, V> implements Map<K, V> {
        private List<K> keys;
        private List<V> values;
        private int mapSize;

        /** The following two method creates a iterator
         * for keys and values of a map. Using the
         * iterator method in LinkedListDeque.
         */
        public Iterator<K> iterator() {
            return keys.iterator();
        }

        public Iterator<V> valueIterator(){
            return values.iterator();
        }


        public DequeMap() {
            keys = new LinkedListDeque<K>();
            values = new LinkedListDeque<V>();
            mapSize = 0;
        }

        /** Returns the index of the given key if it exists,
         *  -1 otherwise. */
        private int keyIndex(K key) {
            int i = 0;
            while(i < mapSize){
                try{
                    keys.get(i).equals(key);
                } catch (Exception ne){
                    i++;
                }
                if (keys.get(i).equals(key)) {
                    return i;
                }
                i++;
            }
            return -1;
        }

        public boolean containsKey(K key) {
            int index = keyIndex(key);
            return index > -1;
        }

        public void put(K key, V value) {

                int index = keyIndex(key);
                if (index == -1) {
                    keys.addLast(key);
                    values.addLast(value);
                    mapSize += 1;
                    return;
                }
                values.replace(index, value);
        }

        public V get(K key)  {
            if(containsKey(key)){
                int index = keyIndex(key);
                return values.get(index);
            }
          else{
                throw new NullPointerException("ERROR: Key doesn't exsit!");
            }
        }

        public void remove(K key){
            int temp = keyIndex(key);
            values.remove(temp);
            keys.remove(temp);
            mapSize --;
    }

        public int size() {
            return mapSize;
        }

        public List<K> keys() {
            List<K> keylist = new LinkedListDeque<K>();
            for (int i = 0; i < keys.length(); i += 1) {
                keylist.addFirst(keys.get(i));
            }
            return keylist;
        }

        public void keyMinusOne(int index){
            keys.elemMinusOne(index);
        }
    }

