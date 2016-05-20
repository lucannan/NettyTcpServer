package cn.lcn.guava.cache;

import com.google.common.cache.Cache;

public class StudentCache {
    private Cache<Integer, String> sCache;

    public Cache<Integer, String> getsCache() {
        return sCache;
    }

    public void setsCache(Cache<Integer, String> sCache) {
        this.sCache = sCache;
    }
}
