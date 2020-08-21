package com.jee.rest.base.serializer.extend;

import java.lang.reflect.Field;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.jee.rest.base.serializer.annotation.NumberFieldFormat;

public class ExtendSerializeConfigForDataFormat2 extends SerializeConfig {

	@Override
	public ObjectSerializer getObjectWriter(Class<?> clazz) {
		ObjectSerializer write = get(clazz);

		if (write == null) {
			Class<?> tempClass = clazz;
			out: while (tempClass != null) {
				Field fields[] = tempClass.getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent(NumberFieldFormat.class)) {
						write = new ExtendJavaBeanSerializer2(clazz);
						break out;
					}
				}
				tempClass = tempClass.getSuperclass();
			}
			if (write != null) {
				put(clazz, write);
			}
		}
		return super.getObjectWriter(clazz);

	}

}
