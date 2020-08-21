package com.jee.rest.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThreadLocalUtil {
	@SuppressWarnings("unchecked")
	private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal() {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<>(4);
		}
	};

	public static Map<String, Object> getThreadLocal() {
		return threadLocal.get();
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String key) {
		Map<String , Object> map = (Map) threadLocal.get();
		return (T) map.get(key);
	}
	@SuppressWarnings("unchecked")
	public static <T> T get(String key, T defaultValue) {
		Map<String , Object> map = (Map) threadLocal.get();
		return (T) map.get(key) == null ? defaultValue : (T) map.get(key);
	}
	@SuppressWarnings("unchecked")
	public static void set(String key, Object value) {
		Map map = (Map) threadLocal.get();
		map.put(key, value);
	}
	@SuppressWarnings("unchecked")
	public static void set(Map<String, Object> keyValueMap) {
		Map map = (Map) threadLocal.get();
		map.putAll(keyValueMap);
	}
	@SuppressWarnings("unchecked")
	public static void remove() {
		threadLocal.remove();
	}
	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> fetchVarsByPrefix(String prefix) {
		Map<String, T> vars = new HashMap<>();
		if (prefix == null) {
			return vars;
		}
		Map map = (Map) threadLocal.get();
		Set<Map.Entry> set = map.entrySet();

		for (Map.Entry entry : set) {
			Object key = entry.getKey();
			if (key instanceof String) {
				if (((String) key).startsWith(prefix)) {
					vars.put((String) key, (T) entry.getValue());
				}
			}
		}
		return vars;
	}
	@SuppressWarnings("unchecked")
	public static <T> T remove(String key) {
		Map map = (Map) threadLocal.get();
		return (T) map.remove(key);
	}
	@SuppressWarnings("unchecked")
	public static void clear(String prefix) {
		if (prefix == null) {
			return;
		}
		Map map = (Map) threadLocal.get();
		Set<Map.Entry> set = map.entrySet();
		List<String> removeKeys = new ArrayList<>();

		for (Map.Entry entry : set) {
			Object key = entry.getKey();
			if (key instanceof String) {
				if (((String) key).startsWith(prefix)) {
					removeKeys.add((String) key);
				}
			}
		}
		for (String key : removeKeys) {
			map.remove(key);
		}
	}
}
