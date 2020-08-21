package com.jee.boot.controller.user;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jee.boot.controller.AbstractBaseController;
import com.jee.boot.datasource.second.vo.MobileVo;
import com.jee.rest.base.annotation.logger.ResponseLogger;
import com.jee.rest.base.response.biz.BizResponse;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@RestController
@Slf4j
public class TestController extends AbstractBaseController{
	
	   //自定义序列化测试
	   @RequestMapping("/data/format")
	   public BizResponse testFastJSONFormat() {

	        MobileVo dto = new MobileVo() ;
	        dto.setMobile("13555555");
	        dto.setLeftMoney(125.85855);
	        dto.setWinRate(0.12225);
	        dto.setBirthDay(new Date());
	        dto.setShortCode("1701");

	    	return BizResponse.ok().listObjects(dto) ;
	    }
        //参数校验测试
	    @RequestMapping("/data/valid")
	    @ResponseLogger(false)
	    public BizResponse testFormDataValid(
	            @RequestParam(defaultValue = "createDateDesc")	String order ,
	            @RequestParam(defaultValue = "15") int pageSize,
	            @Min(1)  @RequestParam(defaultValue = "1") int pageNo ,
	            @NotNull @Max(200) @Min(0) int age ,
	            @NotBlank(message = "用户名不为空") String username
	    ) {
	        //代理调用方式
	       

	        return  BizResponse.ok() ;
	    }
	    
	    //分布式任务调度
	     @Scheduled(cron =  "0 0 0/4 * * MON-FRI")
	     @SchedulerLock(name = "testTaskJob" , lockAtLeastFor = "10S")
	     public void testTaskJob() {
	    	 
	    	 log.info(">>>>>>>>>>>任务开始>>>>>>>>>>>>>>>>");
	     }

}
