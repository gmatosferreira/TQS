package pt.tqsua.homework.cache;

import java.time.Instant;

public class CacheEntry<T> {

    public static final int DEFAULTTTL = 120; // in seconds

    private Instant created;
    private int ttl;
    private T value;

    public CacheEntry(T value, int ttl) {
        this.value = value;
        this.ttl = ttl;
        this.created = Instant.now();
    }

    public CacheEntry(T value) {
        this(value, DEFAULTTTL);
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
