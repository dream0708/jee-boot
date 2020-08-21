package com.jee.boot.datasource.second.vo;

import com.jee.boot.config.DictFieldValueHandler;
import com.jee.rest.base.serializer.annotation.CustomizedFieldFormat;
import com.jee.rest.base.serializer.annotation.DateTimeFieldFormat;
import com.jee.rest.base.serializer.annotation.NumberFieldFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author yaomengke
 * @create 2020- 08 - 19 - 16:14
 */

@Data
public class MobileVo {

    private String mobile ;

    @CustomizedFieldFormat(value = "mobile.virtual" , handlerClass = DictFieldValueHandler.class)
    private String shortCode ;

    @DateTimeFieldFormat(format = "yyyy-MM-dd")
    private Date birthDay ;

    @NumberFieldFormat(format = "0.000" )
    private Double leftMoney ;

    @NumberFieldFormat(format = "0.00%" )
    private Double winRate ;


}
