package pt.tqsua.homework.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Cache<T> implements IGenericCache<String, T>{

    private static final Logger log = LoggerFactory.getLogger(Cache.class);

    public static final int DEFAULTTTL = CacheEntry.DEFAULTTTL;

    private Map<String, CacheEntry<T>> entries;
    private int hits;
    private int misses;
    private int expired;

    public Cache() {
        log.debug("A new CACHE was created!");
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
        List<String> expiredKeys = this.getExpiredKeys();
        for(String key:expiredKeys) {
            this.entries.remove(key);
            this.expired += 1;
        }
        return expiredKeys.size();
    };

    /**
     * Check if a certain key is already stored in the cache
     * @param key String with cache key
     * @return boolean true if key exists in cache
     */
    public boolean isPresent(String key) {
        this.clean();
        this.misses += !entries.containsKey(key) ? 1 : 0;
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
        log.debug("Get from cache with {} hits and {} misses", this.hits, this.misses);
        return entry;
    };

    /**
     * Stores a new entry to the cache, associated with a search key, with a ttl in seconds
     * @param key
     * @param value
     * @param ttl
     */
    public void put(String key, T value, int ttl) {
        entries.put(key, new CacheEntry<T>(value, ttl));
    };

    /**
     * Stores a new entry to the cache, associated with a search key
     * @param key
     * @param value
     */
    public void put(String key, T value) {
        entries.put(key, new CacheEntry<T>(value));
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
        List<String> expiredKeys = new ArrayList<>();
        for(Map.Entry<String, CacheEntry<T>> entry:entries.entrySet()) {
            if(entry.getValue().expired()) {
                expiredKeys.add(entry.getKey());
            }
        }
        return expiredKeys;
    }

    // Stats
    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public int getSize() {
        this.clean();
        return this.entries.size();
    }

    public int getExpired() {
        this.clean();
        return this.expired;
    }
}
