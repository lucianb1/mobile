package com.example.botos.appointment.utils;

import android.util.LruCache;

/**
 * Created by Botos on 12/7/2016.
 */
public class Cache {

    private static Cache instance;
    private LruCache<Object, Object> lru;

    private Cache() {
        lru = new LruCache<Object, Object>(1024);
    }

    public static Cache getInstance() {

        if (instance == null) {

            instance = new Cache();
        }

        return instance;

    }

    public LruCache<Object, Object> getLru() {
        return lru;
    }

}
