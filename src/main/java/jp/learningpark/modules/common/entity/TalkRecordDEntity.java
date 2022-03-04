/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 面談記録
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("talk_record_d")
public class TalkRecordDEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 面談記録ID
     */
    @NotNull
    private Integer talkId;

    /**
     * 事項類型区分
     */
    @NotNull
    private String itemTypeDiv;

    /**
     * 質問番号
     */
    private Integer askNum;

    /**
     * 設問名
     */
    private String questionName;
    /**
     * 回答形式区分
     */
    private String answerTypeDiv;

    /**
     * 回答結果内容
     */
    private String answerReltCont;

    /**
     * 作成日時
     */
    private Timestamp cretDatime;

    /**
     * 作成ユーザＩＤ
     */
    private String cretUsrId;

    /**
     * 更新日時
     */
    private Timestamp updDatime;

    /**
     * 更新ユーザＩＤ
     */
    private String updUsrId;

    /**
     * 削除フラグ
     */
    private Integer delFlg;


    /**
     * IDを設定する
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * IDを取得する
     */
    public Integer getId() {
        return id;
    }
    /**
     * 面談記録IDを設定する
     */
    public void setTalkId(Integer talkId) {
        this.talkId = talkId;
    }
    
    /**
     * 面談記録IDを取得する
     */
    public Integer getTalkId() {
        return talkId;
    }
    /**
     * 事項類型区分を設定する
     */
    public void setItemTypeDiv(String itemTypeDiv) {
        this.itemTypeDiv = itemTypeDiv;
    }
    
    /**
     * 事項類型区分を取得する
     */
    public String getItemTypeDiv() {
        return itemTypeDiv;
    }
    /**
     * 質問番号を設定する
     */
    public void setAskNum(Integer askNum) {
        this.askNum = askNum;
    }
    
    /**
     * 質問番号を取得する
     */
    public Integer getAskNum() {
        return askNum;
    }
    /**
     * 回答形式区分を設定する
     */
    public void setAnswerTypeDiv(String answerTypeDiv) {
        this.answerTypeDiv = answerTypeDiv;
    }
    
    /**
     * 回答形式区分を取得する
     */
    public String getAnswerTypeDiv() {
        return answerTypeDiv;
    }
    /**
     * 回答結果内容を設定する
     */
    public void setAnswerReltCont(String answerReltCont) {
        this.answerReltCont = answerReltCont;
    }
    
    /**
     * 回答結果内容を取得する
     */
    public String getAnswerReltCont() {
        return answerReltCont;
    }
    /**
     * 作成日時を設定する
     */
    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }
    
    /**
     * 作成日時を取得する
     */
    public Timestamp getCretDatime() {
        return cretDatime;
    }
    /**
     * 作成ユーザＩＤを設定する
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }
    
    /**
     * 作成ユーザＩＤを取得する
     */
    public String getCretUsrId() {
        return cretUsrId;
    }
    /**
     * 更新日時を設定する
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }
    
    /**
     * 更新日時を取得する
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }
    /**
     * 更新ユーザＩＤを設定する
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }
    
    /**
     * 更新ユーザＩＤを取得する
     */
    public String getUpdUsrId() {
        return updUsrId;
    }
    /**
     * 削除フラグを設定する
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
    
    /**
     * 削除フラグを取得する
     */
    public Integer getDelFlg() {
        return delFlg;
    }

    /**
     * 設問名を取得する
     *
     * @return questionName 設問名
     */
    public String getQuestionName() {
        return this.questionName;
    }

    /**
     * 設問名を設定する
     *
     * @param questionName 設問名
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}
