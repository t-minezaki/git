/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * スケジュールジョブ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("sys_schedule_job")
public class SysScheduleJobEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ジョブID
     */
    @TableId(type = IdType.AUTO)
    private Long jobId;

    /**
     * Bean名
     */
    @NotBlank(message="Bean名は入力必須項目です。")
    private String beanName;

    /**
     * 方法名
     */
    @NotBlank(message="方法名は入力必須項目です。")
    private String methodName;

    /**
     * パラメータ
     */
    private String params;

    /**
     * Cron書式
     */
    private String cronExpression;

    /**
     * 状態
     */
    private Integer status;

    /**
     * 注釈
     */
    private String remark;

    /**
     * 作成日時
     */
    private Timestamp createTime;

    /**
     * 作成ユーザＩＤ
     */
    private Integer createUserId;


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
     * Cron書式を設定する
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    
    /**
     * Cron書式を取得する
     */
    public String getCronExpression() {
        return cronExpression;
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
     * 注釈を設定する
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    /**
     * 注釈を取得する
     */
    public String getRemark() {
        return remark;
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
     * 作成ユーザＩＤを設定する
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
    
    /**
     * 作成ユーザＩＤを取得する
     */
    public Integer getCreateUserId() {
        return createUserId;
    }
}
