package com.jee.rest.base.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.extern.slf4j.Slf4j;
import sun.util.logging.resources.logging;

@Slf4j
public class DoubleSerializer implements ObjectSerializer {

    public final static DoubleSerializer instance      = new DoubleSerializer();

    private DecimalFormat                decimalFormat = null;

    public DoubleSerializer(){

    }

    public DoubleSerializer(DecimalFormat decimalFormat){
        this.decimalFormat = decimalFormat;
    }

    public DoubleSerializer(String decimalFormat){
        this(new DecimalFormat(decimalFormat));
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;

        if (object == null) {
            out.writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }

        double doubleValue = ((Double) object).doubleValue();

        if (Double.isNaN(doubleValue) //
                || Double.isInfinite(doubleValue)) {
            out.writeNull();
        } else {
            if (decimalFormat == null) {
                out.writeDouble(doubleValue, true);
            } else {
                String doubleText = decimalFormat.format(toDouble(String.valueOf(object)));
                log.info("doubleText:{}" , doubleText) ;
                out.writeString(doubleText);
            }
        }
    }
    
    
	/**
	 * 转换str为double，如果转换失败则返回0.0d
	 */
	public static double toDouble(final String str) {
		return toDouble(str, 0.0d);
	}

	/*
	 * BigDecimal.ROUND_DOWN 表示不进位 BigDecimal.ROUND_UP表示进位
	 */
	public static double toDouble(final String str, int digits, RoundingMode roundingMode) {
		double d = toDouble(str);
		BigDecimal b = new BigDecimal(d);
		return b.setScale(digits, roundingMode).doubleValue();
	}

	/**
	 * 转换str为double，如果转换失败则返回defaultValue
	 */
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
