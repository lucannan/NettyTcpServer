package cn.lcn.guava.factory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class MyCacheFactory<K, V> {
    private Long expireSecondsAfterWrite;
    private Long maximumSize;
    private Cache<K, V> cache;

    public Long getExpireSecondsAfterWrite() {
        return expireSecondsAfterWrite;
    }

    public void setExpireSecondsAfterWrite(Long expireSecondsAfterWrite) {
        this.expireSecondsAfterWrite = expireSecondsAfterWrite;
    }

    public Long getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Long maximumSize) {
        this.maximumSize = maximumSize;
    }


}
