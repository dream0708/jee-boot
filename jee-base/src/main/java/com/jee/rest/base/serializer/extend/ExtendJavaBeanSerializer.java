package com.jee.rest.base.serializer.extend;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.jee.rest.base.anno.utils.DateTimeFormatUtils;
import com.jee.rest.base.anno.utils.NumberFormatUtils;
import com.jee.rest.base.serializer.annotation.CustomizedFieldFormat;
import com.jee.rest.base.serializer.annotation.DateTimeFieldFormat;
import com.jee.rest.base.serializer.annotation.NumberFieldFormat;
import com.jee.rest.base.serializer.handler.CustomizedFieldValueHandler;
import com.jee.rest.base.thread.spring.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ExtendJavaBeanSerializer extends JavaBeanSerializer {

	
	private final static Map<Class<?>, CustomizedFieldValueHandler> valueHandlerMaps = new ConcurrentHashMap<Class<?>, CustomizedFieldValueHandler>();

	private final static Map<String, Object> formatMaps = new ConcurrentHashMap<String, Object>();
	
	
	public static final String CONTACT_STRING = "&&&";
	
	

	public ExtendJavaBeanSerializer(Class<?> beanType) {
		super(beanType);
	}

	protected Object processValue(JSONSerializer jsonBeanDeser, //
			BeanContext beanContext, Object object, //
			String key, //
			Object propertyValue) {

		Object ret = super.processValue(jsonBeanDeser, beanContext, object, key, propertyValue);
		
		if(beanContext == null || StringUtils.isEmpty(key)) {
			return ret ;
		}
		Object formatType = formatMaps.get(beanContext + CONTACT_STRING + key);
		if (formatType == null) {
			Field field = beanContext.getField();
			if (field != null) {
				NumberFieldFormat anno = field.getAnnotation(NumberFieldFormat.class);
				if (anno != null) {
					String format = anno.format();
					if (StringUtils.isEmpty(format)) {
						formatMaps.put(beanContext + CONTACT_STRING + key, 1);
						return ret;
					}
					formatMaps.put(beanContext + CONTACT_STRING + key, anno);
					return parseNumberFieldFormat(ret, propertyValue , anno);
				}
				DateTimeFieldFormat anno2 = field.getAnnotation(DateTimeFieldFormat.class);
				if(anno2 != null) {
					String format = anno2.format();
					if (StringUtils.isEmpty(format)) {
						formatMaps.put(beanContext + CONTACT_STRING + key, 2);
						return ret;
					}
					formatMaps.put(beanContext + CONTACT_STRING + key, anno2);
					return parseDateFieldFormat(ret, propertyValue , anno2);
				}
				
				CustomizedFieldFormat anno3 = field.getAnnotation(CustomizedFieldFormat.class);
				if(anno3 != null) {
					String format = anno3.value();
					if (StringUtils.isEmpty(format)) {
						formatMaps.put(beanContext + CONTACT_STRING + key, 3);
						return ret;
					}
					formatMaps.put(beanContext + CONTACT_STRING + key, anno3);
					return parseCustomizedFieldFormat(ret, propertyValue , anno3);
				}
				
				formatMaps.put(beanContext + CONTACT_STRING + key, 0);
				
			} else {
				formatMaps.put(beanContext + CONTACT_STRING + key, 0);
			}
		}else if (formatType instanceof NumberFieldFormat) {
			NumberFieldFormat anno = (NumberFieldFormat) formatType;
			return parseNumberFieldFormat(ret,propertyValue , anno);
		}else if (formatType instanceof DateTimeFieldFormat) {
			DateTimeFieldFormat anno = (DateTimeFieldFormat) formatType;
			return parseDateFieldFormat(ret, propertyValue , anno);
		}else if (formatType instanceof CustomizedFieldFormat) {
			CustomizedFieldFormat anno = (CustomizedFieldFormat) formatType;
			return parseCustomizedFieldFormat(ret, propertyValue , anno);
		}
		return ret;
	}
	
	
	public Object parseDateFieldFormat(Object ret, Object propertyValue , DateTimeFieldFormat anno) {
		try {
			log.debug("parseDateFieldFormat({} , {} , {} ) ", ret , propertyValue , anno.format());
			if (ret == null) {
				ret = anno.nullable() ;
				return ret ;
			}
			if(ret instanceof Date) {
				return DateFormatUtils.format((Date)ret, anno.format()) ;
			}
			if(ret instanceof Instant) {
				return DateFormatUtils.format(Date.from((Instant)ret) , anno.format()) ;
			}
			if(ret instanceof LocalDateTime) {
				return DateTimeFormatUtils.format((LocalDateTime) ret , anno.format()) ;
			}
			if(ret instanceof LocalDate) {
				return DateTimeFormatUtils.format((LocalDate) ret , anno.format()) ;
			}
			if(ret instanceof LocalTime) {
				return DateTimeFormatUtils.format((LocalTime) ret , anno.format()) ;
			}
			return ret ;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return anno.error();
		}
	}

	public Object parseNumberFieldFormat(Object ret, Object  propertyValue ,NumberFieldFormat anno) {
		try {
			log.debug("parseNumberFieldFormat({} , {} , {} ) ", ret , propertyValue , anno.format());
			if (ret == null) {
				return anno.nullable() ;
			}
			String pattern = anno.format();
			if(ret instanceof BigDecimal) {
				return NumberFormatUtils.formatNumber(
						(BigDecimal)ret, 
						pattern, 
						anno.mode(),
						anno.groupingSize(), 
						anno.scale()) ;
			}
			String doubleString = String.valueOf(ret);
			return NumberFormatUtils.formatNumber(
					NumberFormatUtils.toBigDecimal(doubleString, anno.error()), 
					pattern, 
					anno.mode(),
					anno.groupingSize(), 
					anno.scale()) ;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return anno.error();
		}
	}


	
	
	public Object parseCustomizedFieldFormat(Object ret, Object  propertyValue ,CustomizedFieldFormat anno) {
		try {
			log.debug("parseExtendField({} , {} , {} ) ", ret , propertyValue , anno.value());
			Class<? extends CustomizedFieldValueHandler> handlerClass = anno.handlerClass() ;
			CustomizedFieldValueHandler handler  = SpringContextHolder.getApplicationContext().getBean(handlerClass) ;
			if(handler == null) {
				return ret ;
			}
			return handler.handlerExtendField(ret, propertyValue, anno) ;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ret ;
		}
	}


	

}
