package pt.tqsua.homework.model;

public class Entity<T> {

    private T data;
    private int cacheHits;
    private int cacheMisses;
    private int cacheExpired;
    private int cacheSize;
    private int requests;

    public Entity() {}

    public Entity(T data, int cacheHits, int cacheMisses, int cacheSize, int expired) {
        this.data = data;
        this.cacheHits = cacheHits;
        this.cacheMisses = cacheMisses;
        this.requests = cacheHits + cacheMisses;
        this.cacheSize = cacheSize;
        this.cacheExpired = expired;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCacheHits() {
        return cacheHits;
    }

    public void setCacheHits(int cacheHits) {
        this.cacheHits = cacheHits;
    }

    public int getCacheMisses() {
        return cacheMisses;
    }

    public void setCacheMisses(int cacheMisses) {
        this.cacheMisses = cacheMisses;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public int getCacheExpired() {
        return cacheExpired;
    }

    public void setCacheExpired(int cacheExpired) {
        this.cacheExpired = cacheExpired;
    }
}
