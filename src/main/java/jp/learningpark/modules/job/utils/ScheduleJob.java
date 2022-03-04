/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */

package jp.learningpark.modules.job.utils;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.SpringContextUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.job.entity.SysScheduleJobEntity;
import jp.learningpark.modules.job.entity.SysScheduleJobLogEntity;
import jp.learningpark.modules.job.service.SysScheduleJobLogService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 定时任务
 *
 * @author gakken@sinways.com.cn
 * @since 1.2.0 2016-11-28
 */
public class ScheduleJob extends QuartzJobBean {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ExecutorService service = Executors.newSingleThreadExecutor(); 
	
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();  

        SysScheduleJobEntity scheduleJob = (SysScheduleJobEntity) jobDataMap.get(ScheduleUtils.JOB_PARAM_KEY);

        //spring beanを取得する
        SysScheduleJobLogService scheduleJobLogService = (SysScheduleJobLogService) SpringContextUtils.getBean("sysScheduleJobLogService");
        
        //実行記録をデータベースに保存する
        SysScheduleJobLogEntity log = new SysScheduleJobLogEntity();
        log.setJobId(scheduleJob.getJobId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());
        log.setCreateTime(DateUtils.getSysTimestamp());
        
        //任務開始時刻
        long startTime = System.currentTimeMillis();
        
        try {
            //任務開始
        	logger.info("任務開始，任務ID：" + scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),
            		scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);
            
			future.get();
			
			//総実行時間
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			//任務状態    0：成功    1：失敗
			log.setStatus(0);
			
			logger.info("任務完了，任務ID：" + scheduleJob.getJobId() + "  全部でかかる時間：" + times + "ミリ秒");
		} catch (Exception e) {
			logger.error("任務の実行に失敗しました，任務ID：" + scheduleJob.getJobId(), e);
			
			//タスク実行にかかる総時間
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			
			//任務状態    0：成功    1：失敗
			log.setStatus(1);
			log.setError(StringUtils.substring(e.toString(), 0, 2000));
		}finally {
			scheduleJobLogService.save(log);
		}
    }
}
