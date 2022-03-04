package jp.learningpark.modules.guard.dto;

import jp.learningpark.modules.common.entity.TalkRecordDEntity;
import jp.learningpark.modules.common.entity.TalkRecordHEntity;

import java.util.List;

public class F30410Dto extends TalkRecordHEntity {
    /**
     * 生徒氏名
     */
    public String stuName;
    /**
     * 日時
     */
    public String sgdStartTime;
    /**
     * 備考
     */
    public String replyCnt;

    /**
     * 保護者の質問事項情報
     */
    private List<TalkRecordDEntity> talkRecordDEntityList;

    /**
     * 生徒氏名を取得する
     *
     * @return stuName 生徒氏名
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * 生徒氏名を設定する
     *
     * @param stuName 生徒氏名
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * 日時を取得する
     *
     * @return sgdStartTime 日時
     */
    public String getSgdStartTime() {
        return this.sgdStartTime;
    }

    /**
     * 日時を設定する
     *
     * @param sgdStartTime 日時
     */
    public void setSgdStartTime(String sgdStartTime) {
        this.sgdStartTime = sgdStartTime;
    }

    /**
     * 備考を取得する
     *
     * @return replyCnt 備考
     */
    public String getReplyCnt() {
        return this.replyCnt;
    }

    /**
     * 備考を設定する
     *
     * @param replyCnt 備考
     */
    public void setReplyCnt(String replyCnt) {
        this.replyCnt = replyCnt;
    }

    /**
     * 保護者の質問事項情報を取得する
     *
     * @return talkRecordDEntityList 保護者の質問事項情報
     */
    public List<TalkRecordDEntity> getTalkRecordDEntityList() {
        return this.talkRecordDEntityList;
    }

    /**
     * 保護者の質問事項情報を設定する
     *
     * @param talkRecordDEntityList 保護者の質問事項情報
     */
    public void setTalkRecordDEntityList(List<TalkRecordDEntity> talkRecordDEntityList) {
        this.talkRecordDEntityList = talkRecordDEntityList;
    }
}
