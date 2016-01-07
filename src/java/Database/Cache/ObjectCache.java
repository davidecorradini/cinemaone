package Database.Cache;

import java.util.HashMap;

/**
 * 
 * @param <Key>
 * @param <Value>
 */
public class ObjectCache<Key, Value> {
    private final HashMap<Key, Value> cache;
    
    public ObjectCache(){
        cache = new HashMap<>();
    }
    /**
     * 
     * @param key
     * @return cached Object corresponding to the specified key, null if not present.
     */
    public Value get(Key key){
        return cache.get(key);
    }
    
    /**
     * checks if in the cache there is an object at the specified key.
     * @param key
     * @return true if present, false otherwise
     */
    public boolean containsKey(Key key){
        return cache.containsKey(key);
    }
    /**
     * checks if in the cache there is an object with the specified value.
     * @param value
     * @return true if present, false otherwise 
     */
    public boolean containsValue(Value value){
        return cache.containsValue(value);
    }
    /**
     * adds a new element to the cache.
     * @param key key of the element
     * @param value value to store.
     */
    public synchronized void add(Key key, Value value){
        cache.put(key, value);
    }
    
    /**
     * removes an element from the cache, matches the key and the value.
     * @param key
     * @param value 
     */
    public synchronized void remove(Key key, Value value){
        cache.remove(key, value);
    }
    
    /**
     * removes from the cache the object that corresponds to the specified key.
     * @param key
     * @return the value of the object removed.
     */
    public synchronized Value remove(Key key){
        return cache.remove(key);
    }
    
    /**
     * deletes all the element from the cache.
     */
    public synchronized void clean(){
        cache.clear();
    }
}
