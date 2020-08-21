package com.jee.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ScheduleConfig implements SchedulingConfigurer , AsyncConfigurer {


	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler());
	
	}

	@Bean(destroyMethod = "shutdown")
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(5);// 我这里设置的线程数是2,可以根据需求调整 
		taskScheduler.setThreadNamePrefix("task-Scheduling-");
		taskScheduler.setAwaitTerminationSeconds(600);
		//taskScheduler.setErrorHandler(throwable -> log.error("调度任务发生异常", throwable));
		taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
		return taskScheduler;
	}

}
