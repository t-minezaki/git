/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * スケジュールジョブログ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("sys_schedule_job_log")
public class SysScheduleJobLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ログID
     */
    @TableId(type = IdType.AUTO)
    private Long logId;

    /**
     * ジョブID
     */
    private Long jobId;

    /**
     * Bean名
     */
    private String beanName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * パラメータ
     */
    private String params;

    /**
     * 状態
     */
    private Integer status;

    /**
     * エラー情報
     */
    private String error;

    /**
     * 実行時間
     */
    private Integer times;

    /**
     * 作成日時
     */
    private Timestamp createTime;

    /**
     * 作成ユーザーID
     */
    private Integer createUserId;


    /**
     * ログIDを設定する
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }
    
    /**
     * ログIDを取得する
     */
    public Long getLogId() {
        return logId;
    }
    /**
     * ジョブIDを設定する
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
    
    /**
     * ジョブIDを取得する
     */
    public Long getJobId() {
        return jobId;
    }
    /**
     * Bean名を設定する
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
    
    /**
     * Bean名を取得する
     */
    public String getBeanName() {
        return beanName;
    }
    /**
     * 方法名を設定する
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    /**
     * 方法名を取得する
     */
    public String getMethodName() {
        return methodName;
    }
    /**
     * パラメータを設定する
     */
    public void setParams(String params) {
        this.params = params;
    }
    
    /**
     * パラメータを取得する
     */
    public String getParams() {
        return params;
    }
    /**
     * 状態を設定する
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 状態を取得する
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * エラー情報を設定する
     */
    public void setError(String error) {
        this.error = error;
    }
    
    /**
     * エラー情報を取得する
     */
    public String getError() {
        return error;
    }
    /**
     * 実行時間を設定する
     */
    public void setTimes(Integer times) {
        this.times = times;
    }
    
    /**
     * 実行時間を取得する
     */
    public Integer getTimes() {
        return times;
    }
    /**
     * 作成日時を設定する
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 作成日時を取得する
     */
    public Timestamp getCreateTime() {
        return createTime;
    }
    /**
     * 作成ユーザーIDを設定する
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
    
    /**
     * 作成ユーザーIDを取得する
     */
    public Integer getCreateUserId() {
        return createUserId;
    }
}
