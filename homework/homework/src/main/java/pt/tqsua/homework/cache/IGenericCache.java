package pt.tqsua.homework.cache;

// Inspired on https://medium.com/@marcellogpassos/creating-a-simple-and-generic-cache-manager-in-java-e62e4204a10e

import java.util.Optional;

public interface IGenericCache<K, V> {

    int clean();

    int clear();

    boolean containsKey(K key);

    Optional<V> get(K key);

    void put(K key, V value);

    void remove(K key);

}