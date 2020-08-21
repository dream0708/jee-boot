package com.jee.rest.base.serializer.extend;

import java.lang.reflect.Field;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.jee.rest.base.serializer.annotation.DateTimeFieldFormat;
import com.jee.rest.base.serializer.annotation.CustomizedFieldFormat;
import com.jee.rest.base.serializer.annotation.NumberFieldFormat;

public class ExtendSerializeConfigForDataFormat extends SerializeConfig {

	@Override
	public ObjectSerializer getObjectWriter(Class<?> clazz) {
		ObjectSerializer write = get(clazz);

		if (write == null) {
			Class<?> tempClass = clazz;
			out: while (tempClass != null) {
				Field fields[] = tempClass.getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent(NumberFieldFormat.class)) {
						write = new ExtendJavaBeanSerializer(clazz);
						break out;
					}
					if (field.isAnnotationPresent(DateTimeFieldFormat.class)) {
						write = new ExtendJavaBeanSerializer(clazz);
						break out;
					}
					if (field.isAnnotationPresent(CustomizedFieldFormat.class)) {
						write = new ExtendJavaBeanSerializer(clazz);
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
