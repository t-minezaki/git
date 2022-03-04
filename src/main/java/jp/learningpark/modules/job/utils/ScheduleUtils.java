/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */

package jp.learningpark.modules.job.utils;

import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.modules.job.entity.SysScheduleJobEntity;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

/**
 * 定時任務ツール類
 *
 * @author gakken@sinways.com.cn
 * @since 1.2.0 2016-11-28
 */
public class ScheduleUtils {
    private final static String JOB_NAME = "TASK_";

    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";
    
    /**
     * トリガーキーを取得する
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }
    
    /**
     * jobKeyを取得する
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 式トリガーを取得する
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new RRException("取得定時任務CronTriggerに異常", e);
        }
    }

    /**
     * 定時任務の作成
     */
    public static void createScheduleJob(Scheduler scheduler, SysScheduleJobEntity scheduleJob) {
        try {
        	//jobメッセージを構築する
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();

            //次式スケジューリングビルダー
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
            		.withMisfireHandlingInstructionDoNothing();

            //新しいcronExpression式で新しいtriggerを構築する
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId())).withSchedule(scheduleBuilder).build();

            //パラメータを入れて実行時間を取り
            jobDetail.getJobDataMap().put(JOB_PARAM_KEY, scheduleJob);

            scheduler.scheduleJob(jobDetail, trigger);
            
            //任務を一時停止する
            if(scheduleJob.getStatus() == Constant.SCHEDULE_STATUS_PAUSE){
            	pauseJob(scheduler, scheduleJob.getJobId());
            }
        } catch (SchedulerException e) {
            throw new RRException("定時任務の作成に失敗", e);
        }
    }
    
    /**
     * 定時任務を更新する
     */
    public static void updateScheduleJob(Scheduler scheduler, SysScheduleJobEntity scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

            //次式スケジューリングビルダー
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
            		.withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getJobId());
            
            //triggerを新しいcronExpression式で再構築する
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            
            //パラメータ
            trigger.getJobDataMap().put(JOB_PARAM_KEY, scheduleJob);
            
            scheduler.rescheduleJob(triggerKey, trigger);
            
            //任務を一時停止する
            if(scheduleJob.getStatus() == Constant.SCHEDULE_STATUS_PAUSE){
            	pauseJob(scheduler, scheduleJob.getJobId());
            }
            
        } catch (SchedulerException e) {
            throw new RRException("定時任務の更新失敗", e);
        }
    }

    /**
     * 定時任務を直ちに実行する
     */
    public static void run(Scheduler scheduler, SysScheduleJobEntity scheduleJob) {
        try {
        	//パラメータ
        	JobDataMap dataMap = new JobDataMap();
        	dataMap.put(JOB_PARAM_KEY, scheduleJob);
        	
            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), dataMap);
        } catch (SchedulerException e) {
            throw new RRException("定時任務の即時実行失敗", e);
        }
    }

    /**
     * 定時任務を一時停止
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RRException("定時任務の一時停止に失敗しました。", e);
        }
    }

    /**
     * 定時任務に復帰する
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RRException("定時任務復帰失敗", e);
        }
    }

    /**
     * 定時任務を削除する
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RRException("定時任務の削除失敗", e);
        }
    }
}
