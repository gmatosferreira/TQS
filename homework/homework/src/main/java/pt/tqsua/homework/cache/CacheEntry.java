package pt.tqsua.homework.cache;

import java.time.Instant;

public class CacheEntry<T> {

    private Instant created;
    private int ttl;
    private T value;

    public CacheEntry(T value, int ttl) {
        this.value = value;
        this.ttl = ttl;
        this.created = Instant.now();
    };

    public CacheEntry(T value) {
        // Default ttl of 30 seconds
        this(value, 30);
    }

    public boolean expired() {
        return (this.created.getEpochSecond()+this.ttl)<Instant.now().getEpochSecond();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}