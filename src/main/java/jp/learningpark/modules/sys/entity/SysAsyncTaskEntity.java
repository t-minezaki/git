/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 非同期タスクマスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("sys_async_task")
public class SysAsyncTaskEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * タイプ
     */
    private Integer taskType;

    /**
     * 状態　0:新規,1:送信完了,2:送信失敗
     */
    private Integer status;

    /**
     * コンテキスト
     */
    private String context;

    /**
     * LRS ID
     */
    private String lrsId;


    /**
     * ${column.comments}を設定する
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Integer getId() {
        return id;
    }
    /**
     * タイプを設定する
     */
    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }
    
    /**
     * タイプを取得する
     */
    public Integer getTaskType() {
        return taskType;
    }
    /**
     * 状態　0:新規,1:送信完了,2:送信失敗を設定する
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    /**
     * 状態　0:新規,1:送信完了,2:送信失敗を取得する
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * コンテキストを設定する
     */
    public void setContext(String context) {
        this.context = context;
    }
    
    /**
     * コンテキストを取得する
     */
    public String getContext() {
        return context;
    }
    /**
     * LRS IDを設定する
     */
    public void setLrsId(String lrsId) {
        this.lrsId = lrsId;
    }
    
    /**
     * LRS IDを取得する
     */
    public String getLrsId() {
        return lrsId;
    }
}
