package com.ht.note.common;

import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class CacheMap implements Serializable {


    public static ConcurrentHashMap<String,String> cacheMap = new ConcurrentHashMap<>();
}
