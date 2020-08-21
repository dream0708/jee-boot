package com.jee.rest.base.serializer.extend;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.jee.rest.base.serializer.annotation.NumberFieldFormat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExtendJavaBeanSerializer2 extends JavaBeanSerializer {

	private final static IdentityHashMap<String, DecimalFormat> formatTypes = new IdentityHashMap<String, DecimalFormat>();

	private final static IdentityHashMap<String, Object> numberlFormatMaps = new IdentityHashMap<String, Object>();

	public static final String CONTACT_STRING = "&&&";

	public ExtendJavaBeanSerializer2(Class<?> beanType) {
		super(beanType);
	}

	protected Object processValue(JSONSerializer jsonBeanDeser, //
			BeanContext beanContext, Object object, //
			String key, //
			Object propertyValue) {

		Object ret = super.processValue(jsonBeanDeser, beanContext, object, key, propertyValue);
		//log.info("ExtendJavaBeanSerializer:{} , BeanContext:{} ,Object:{} , key:{} , Object:{} , Ret:{} ", this,
				//beanContext, object, key, propertyValue, ret);
		//log.info("ExtendJavaBeanSerializer:{}  formatTypes:{} , numberlFormatMaps:{} " , this , formatTypes.hashCode() , numberlFormatMaps.hashCode() );
		if(beanContext == null || StringUtils.isEmpty(key)) {
			return ret ;
		}
		Object formatType = numberlFormatMaps.get(beanContext + CONTACT_STRING + key);
		if (formatType == null) {
			Field field = beanContext.getField();
			if (field != null) {
				NumberFieldFormat anno = field.getAnnotation(NumberFieldFormat.class);
				if (anno != null) {
					String format = anno.format();
					if (StringUtils.isEmpty(format)) {
						numberlFormatMaps.put(beanContext + CONTACT_STRING + key, 0);
						return ret;
					}
					numberlFormatMaps.put(beanContext + CONTACT_STRING + key, anno);
					return parseNumberFieldFormat(ret, anno);
				} else {
					numberlFormatMaps.put(beanContext + CONTACT_STRING + key, 0);
				}
			} else {
				numberlFormatMaps.put(beanContext + CONTACT_STRING + key, 0);
			}
		}else if (formatType instanceof NumberFieldFormat) {
			NumberFieldFormat anno = (NumberFieldFormat) formatType;
			return parseNumberFieldFormat(ret, anno);
		}
		
		return ret;
	}

	public Object parseNumberFieldFormat(Object ret, NumberFieldFormat anno) {
		try {
			if (ret == null) {
				ret = anno.nullable();
			}
			String format = anno.format();
			String doubleString = String.valueOf(ret);
			DecimalFormat decimalFormat = formatTypes.get(format + CONTACT_STRING + anno.mode().ordinal());
			if (decimalFormat == null) {
				decimalFormat = new DecimalFormat(format);
				decimalFormat.setRoundingMode(anno.mode());
				formatTypes.put(format + CONTACT_STRING + anno.mode().ordinal(), decimalFormat);
			}
			return decimalFormat.format(toDouble(doubleString, Double.valueOf( anno.error())));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return anno.error();
		}
	}

	protected Object processValueBak(JSONSerializer jsonBeanDeser, //
			BeanContext beanContext, Object object, //
			String key, //
			Object propertyValue) {

		Object ret = super.processValue(jsonBeanDeser, beanContext, object, key, propertyValue);
		log.debug("ExtendJavaBeanSerializer:{} , BeanContext:{} ,Object:{} , key:{} , Object:{} , ret:{} ", this,
				beanContext, object, key, propertyValue, ret);

		Field field = beanContext.getField();
		if (field != null) {
			NumberFieldFormat anno = field.getAnnotation(NumberFieldFormat.class);
			if (anno != null) {
				String format = anno.format();
				if (StringUtils.isEmpty(format)) {
					return ret;
				}
				try {
					if (ret == null) {
						ret = anno.nullable();
					}
					String doubleString = String.valueOf(ret);
					DecimalFormat decimalFormat = formatTypes.get(format + CONTACT_STRING + anno.mode().ordinal());
					if (decimalFormat == null) {
						decimalFormat = new DecimalFormat(format);
						decimalFormat.setRoundingMode(anno.mode());
						formatTypes.put(format + CONTACT_STRING + anno.mode().ordinal(), decimalFormat);
					}
					return decimalFormat.format(toDouble(doubleString, Double.valueOf( anno.error())));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					return anno.error();
				}
			}
		}
		return ret;
	}

	public static double toDouble(final String str) {
		return toDouble(str, 0.0d);
	}

	public static double toDouble(final String str, int digits, RoundingMode roundingMode) {
		double d = toDouble(str);
		BigDecimal b = new BigDecimal(d);
		return b.setScale(digits, roundingMode).doubleValue();
	}

	public static String formatNumber(final String str, String pattern) {
		return new DecimalFormat(pattern).format(toDouble(str));
	}

	public static String formatNumber(final Number number, String pattern) {
		return new DecimalFormat(pattern).format(toDouble(String.valueOf(number)));
	}

	public static double toDouble(final String str, final double defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		try {
			return Double.parseDouble(str);
		} catch (final NumberFormatException nfe) {
			return defaultValue;
		}
	}

}
