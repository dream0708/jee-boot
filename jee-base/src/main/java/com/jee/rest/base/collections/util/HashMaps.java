package com.jee.rest.base.collections.util;

import java.util.HashMap;

public class HashMaps<K , V> extends HashMap<K  , V>{
	
	public HashMaps<K , V> add(K key , V value){
		this.put(key, value) ;
		return this ;
	}
	
	
	public HashMaps<K , V> puts(K key , V value){
		this.put(key, value) ;
		return this ;
	}

}
