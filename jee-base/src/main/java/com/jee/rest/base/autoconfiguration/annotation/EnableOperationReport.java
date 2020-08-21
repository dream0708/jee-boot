package com.jee.rest.base.autoconfiguration.annotation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jee.rest.base.anno.utils.LocalIpUtils;
import com.jee.rest.base.annotation.monitor.FinishedReport;
import com.jee.rest.base.annotation.monitor.SchedulerReport;
import com.jee.rest.base.annotation.monitor.WarningReport;
import com.jee.rest.base.common.enums.ReportMode;
import com.jee.rest.base.http.util.HttpTemplateUtil;

import lombok.extern.slf4j.Slf4j;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ WarningReportAspect.class })
public @interface EnableOperationReport {

}

@Aspect
@Slf4j
class WarningReportAspect implements InitializingBean {

	private final String DEV_AGENT_ID = "1000009";

	private final String DEV_HTTP_URL = "http://127.0.0.1/dev";

	private final String PROD_AGENT_ID = "1000059";

	private final String PROD_HTTP_URL = "http://127.0.0.1/prod";

	private final String PROD_NAME = "prod";

	@Resource
	private Environment environment;

	private static String reportAgentId;

	private static String tcProxyUrl;

	// https://www.jianshu.com/p/d8a654993719
	@Pointcut("@annotation(report)")
	public void waringPoincut(WarningReport report) {

	}

	@Pointcut("@annotation(report)")
	public void schedulerReport(SchedulerReport report) {

	}
	
	@Pointcut("@annotation(report)")
	public void finishedReport(FinishedReport report) {

	}

	@AfterThrowing(value = "waringPoincut(report)", throwing = "ex")
	public void doAfterThrowingAndWarningReport(WarningReport report, Throwable ex) {
		try {
			String title = report.title();
			String[] users = report.users();
			String group = report.group();
			JSONObject object = new JSONObject();
			object.put("title", title);

			object.put("agentid", reportAgentId);
			if (!ArrayUtils.isEmpty(users)) {
				object.put("touser", StringUtils.join(users, "|"));
			}
			String methodName = ex.getStackTrace()[0].getMethodName();
			String content = "标        题：" + title + "\r\n" + "时        间："
					+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "\r\n" + "地        址："
					+ LocalIpUtils.getLocalIp4Address("unkown host") + "\r\n" + "方        法：" + methodName + "\r\n"
					+ "异常 信息：" + ex.getMessage() + "\r\n";
			if (report.exceptionStack()) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				content += ("异常类型：" + ex.getClass().getName() + "\r\n" + "异常堆栈信息：" + sw.toString() + "\r\n");
			}
			object.put("content", content);
		
			//

		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>>异常上报发生异常>>>>>>>>>>>>>>>>>>");
			log.error(e.getMessage(), e);
		}

	}

	@AfterReturning(value = "schedulerReport(report)", returning = "res")
	public void doAfterReturnAndWarningReport(SchedulerReport report, Object res) {
		try {
			if (!report.finished()) {
				return;
			}

			String title = report.title();
			String[] users = report.users();
			String group = report.group();
			JSONObject object = new JSONObject();
			object.put("title", title);
			object.put("agentid", reportAgentId);
			if (!ArrayUtils.isEmpty(users)) {
				object.put("touser", StringUtils.join(users, "|"));
			}

			String content = "完成 提醒：" + title + "\r\n" + "时       间："
					+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "\r\n" + "地       址："
					+ LocalIpUtils.getLocalIp4Address("unkown host") + "\r\n" + "返回 信息：" + JSON.toJSONString(res)
					+ "\r\n";

			object.put("content", content);
			
			
			
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>>返回上报发生异常>>>>>>>>>>>>>>>>>>");
			log.error(e.getMessage(), e);
		}

	}
	
	
	
	@AfterReturning(value = "finishedReport(report)", returning = "res")
	public void doAfterReturnAndFinishedReport(FinishedReport report, Object res) {
		try {
			

			String title = report.title();
			String[] users = report.users();
			String group = report.group();
			JSONObject object = new JSONObject();
			object.put("title", title);
			object.put("agentid", reportAgentId);
			if (!ArrayUtils.isEmpty(users)) {
				object.put("touser", StringUtils.join(users, "|"));
			}

			String content = "完成 提醒：" + title + "\r\n" + "时       间："
					+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "\r\n" + "地       址："
					+ LocalIpUtils.getLocalIp4Address("unkown host") + "\r\n" + "返回 信息：" + JSON.toJSONString(res)
					+ "\r\n";

			object.put("content", content);
			
			
		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>>返回上报发生异常>>>>>>>>>>>>>>>>>>");
			log.error(e.getMessage(), e);
		}

	}

	@AfterThrowing(value = "schedulerReport(report)", throwing = "ex")
	public void doAfterThrowingAndWarningReport(SchedulerReport report, Throwable ex) {
		try {
			String title = report.title();
			String[] users = report.users();
			String group = report.group();
			JSONObject object = new JSONObject();
			object.put("title", title);
			object.put("agentid", reportAgentId);
			if (!ArrayUtils.isEmpty(users)) {
				object.put("touser", StringUtils.join(users, "|"));
			}
			String methodName = ex.getStackTrace()[0].getMethodName();
			String content = "异常 提醒：" + title + "\r\n" + "时       间："
					+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "\r\n" + "地        址："
					+ LocalIpUtils.getLocalIp4Address("unkown host") + "\r\n" + "方        法：" + methodName + "\r\n"
					+ "异常 信息：" + ex.getMessage() + "\r\n";
			if (report.exceptionStack()) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				content += ("异常类型：" + ex.getClass().getName() + "\r\n" + "异常堆栈信息：" + sw.toString() + "\r\n");
			}
			object.put("content", content);
			
			

		} catch (Exception e) {
			log.error(">>>>>>>>>>>>>>>>>>异常上报发生异常>>>>>>>>>>>>>>>>>>");
			log.error(e.getMessage(), e);
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info(">>>>>>>afterPropertiesSet<<<<<<<<");
		reportAgentId = environment.getProperty("qywx.warning.agentid");
		if (StringUtils.isBlank(reportAgentId)) {
			final String envString = environment.getProperty("spring.profiles.active", PROD_NAME);
			reportAgentId = envString.contains(PROD_NAME) ? PROD_AGENT_ID : DEV_AGENT_ID;
		}
		log.info("reportAgentId:{}", reportAgentId);
		tcProxyUrl = environment.getProperty("qywx.warning.proxyurl");
		if (StringUtils.isBlank(tcProxyUrl)) {
			final String envString = environment.getProperty("spring.profiles.active", PROD_NAME);
			tcProxyUrl = envString.contains(PROD_NAME) ? PROD_HTTP_URL : DEV_HTTP_URL;
		}
		log.info("tcProxyUrl:{}", tcProxyUrl);

	}

}
