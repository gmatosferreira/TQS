package pt.tqsua.homework.cache;

import java.util.*;

public class Cache<T> implements IGenericCache<String, T>{

    private Map<String, CacheEntry<T>> entries;
    private int hits;
    private int misses;

    public Cache() {
        this.entries = new HashMap<>();
    }

    /**
     * Removes all entries from the cache
     * @return int number of entries removed
     */
    public int clear() {
        int size = entries.size();
        entries.clear();
        return size;
    };

    /**
     * Removes all expired cache entries
     * @return int number of entries removed
     */
    public int clean() {
        List<String> expired = this.getExpiredKeys();
        for(String key:expired) {
            this.entries.remove(key);
        }
        return expired.size();
    };

    /**
     * Check if a certain key is already stored in the cache
     * @param key String with cache key
     * @return boolean true if key exists in cache
     */
    public boolean containsKey(String key) {
        this.clean();
        return entries.containsKey(key);
    };

    /**
     * Given a key, retrieves a value stored in the cache
     * @param key String with cache key
     * @return Optional<CacheEntry> entry of empty is does not exist
     */
    public Optional<T> get(String key) {
        this.clean();
        Optional<T> entry = entries.containsKey(key) ? Optional.of(entries.get(key).getValue()) : Optional.empty();
        this.hits += entry.isPresent() ? 1 : 0;
        this.misses += entry.isEmpty() ? 1 : 0;
        return entry;
    };

    /**
     * Stores a new entry to the cache, associated with a search key, with a ttl in seconds
     * @param key
     * @param value
     * @param ttl
     */
    public void put(String key, T value, int ttl) {
        entries.put(key, new CacheEntry(value, ttl));
    };

    /**
     * Stores a new entry to the cache, associated with a search key
     * @param key
     * @param value
     */
    public void put(String key, T value) {
        entries.put(key, new CacheEntry(value));
    };

    /**
     * Given a key, removes an entry from the cache
     * @param key
     */
    public void remove(String key) {
        entries.remove(key);
    };

    /**
     * Internal method to get expired keys
     * @return
     */
    private List<String> getExpiredKeys() {
        List<String> expired = new ArrayList<>();
        for(String key:entries.keySet()) {
            if(entries.get(key).expired()) {
                expired.add(key);
            }
        }
        return expired;
    }

    // Stats
    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public int getSize() {
        return this.entries.size();
    }
}
