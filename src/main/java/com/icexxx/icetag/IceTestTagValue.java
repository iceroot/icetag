package com.icexxx.icetag;

import java.util.Hashtable;

public class IceTestTagValue {
    private static Hashtable<String, Boolean> full = new Hashtable<String, Boolean>();
    private static Hashtable<String, Boolean> simple = new Hashtable<String, Boolean>();

    public synchronized static void put(String key, Boolean value) {
        if (value == null) {
            full.remove(key);
        } else {
            full.put(key, value);
        }
        if (key != null) {
            String simpleKey = key.substring(0, key.lastIndexOf("@"));
            if (value == null) {
                simple.remove(simpleKey);
            } else {
                simple.put(simpleKey, value);
            }
        }

    }

    public synchronized static Boolean get(String key) {
        Boolean value = full.get(key);
        if (value == null) {
            String simpleKey = key.substring(0, key.lastIndexOf("@"));
            return simple.get(simpleKey);
        } else {
            return value;
        }
    }
}
