package com.bnk.plus.config.schedule;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.bnk.plus.config.schedule.tasks.SchedulerWorkerTask;

/**
 * <pre>
 * AppTaskScheduler.java
 * 
 * #스케쥴러 등록 방법
 * 1. "./tasks" 폴더에 작업 구분을 할 수 있는 클래스를 추가 한다.
 * 2. 추가한 클래스에 실제 구동될 한 작업 작업을 메소드 형태로 만든다.
 *  예) @Scheduled(fixedDelay=5000)
 *     public void tokenStoreTable() {
 *       log.debug("tokenstore table truncated - run");
 *     }
 * 3. 이 클래스(AppTaskScheduler.java)에 빈으로 등록해준다.
 *  예) @Bean
 *     public SchedulerWorkerTask schedulerWorkerTask(){
 *       return new SchedulerWorkerTask();
 *     }
 * 
 * #기타 - 반복 주기 설정
 * 1. 매 일정 초마다 실행
 *  @Scheduled(fixedDelay=5000)
 * 2. 일월년 등의 주기적인 실행
 *  @Scheduled(cron="0 0 0 0 0 0 0")
 *  초 분 시간 일 월 주 년(옵션)
 *  예) 0 0 * * * * : 매일 매시 시작 시점
 *  예) 0 0 8-10 * * * : 매일 8,9,10시
 *  예) 0 0/30 8-10 * * * : 매일 8:00, 8:30, 9:00, 9:30, 10:00
 *  예) 0 0 9-17 * * MON-FRI : 주중 9시부터 17시까지
 *  예) 0 0 0 25 12 ? : 매년 크리스마스 자정
 * 3. 앱이 구동되자마자 한번 실행 후 반복을 해야 할 경우 "@Scheduled(initialDelay=1000)"로 설정한다.
 * </pre>
 *
 * @author ks-choi
 * @date 2016. 3. 29.
 */
@Configuration
@EnableScheduling
public class AppTaskScheduler implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler());
		/*
		// 스케쥴 등록 - 어노테이션 기반으로 사용하지 않을 경우 아래처럼 여러 스케쥴을 등록할 수 있다.
		taskRegistrar.addFixedRateTask(new Runnable() {
			public void run() {
				new SchedulerWorkerTask().tokenStoreTable();
			}
		}, 1000);
		*/
	}
	
	@Bean(destroyMethod="shutdown")
    public Executor taskScheduler() {
//        return Executors.newScheduledThreadPool(42);
		return new ThreadPoolTaskScheduler();
    }
	
	@Bean
	public SchedulerWorkerTask schedulerWorkerTask(){
		return new SchedulerWorkerTask();
	}
	
}