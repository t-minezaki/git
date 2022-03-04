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
 * 入退室各種設定マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_various_set")
public class MstVariousSetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 組織ID
     */
    @NotNull
    private String orgId;

    /**
     * 再認識不可時間
     */
    @NotNull
    private Integer reRecoTime;

    /**
     * 登校時メッセージ
     */
    @NotNull
    private String goSchMsg;

    /**
     * 認証エラーメッセージ
     */
    @NotNull
    private String goSchErrMsg;

    /**
     * 時間内エラーメッセージ
     */
    @NotNull
    private String douGoSchMsg;

    /**
     * 登校自動ポイント
     */
    private Integer goSchPoint;

    /**
     * 実行登録時自動ポイント
     */
    private Integer doLoginPoint;

    /**
     * 実行時間累計自動ポイント
     */
    private Integer doTotalPoint;

    /**
     * 入室時自動ポイント
     */
    private Integer inRoomPoint;

    /**
     * 合格時自動ポイント
     */
    private Integer paddPoint;

    /**
     * 満点時自動ポイント
     */
    private Integer fullMarksPoint;

    /**
     * 宿題提出時自動ポイント
     */
    private Integer workOutPoint;
    /**
     * 出席登録時自動ポイント
     */
    private Integer attentOutPoint;

    /**
     * 誕生日時自動ポイント
     */
    private Integer birthdayTimePoint;

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
     * 組織IDを設定する
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    /**
     * 組織IDを取得する
     */
    public String getOrgId() {
        return orgId;
    }
    /**
     * 再認識不可時間を設定する
     */
    public void setReRecoTime(Integer reRecoTime) {
        this.reRecoTime = reRecoTime;
    }
    
    /**
     * 再認識不可時間を取得する
     */
    public Integer getReRecoTime() {
        return reRecoTime;
    }
    /**
     * 登校時メッセージを設定する
     */
    public void setGoSchMsg(String goSchMsg) {
        this.goSchMsg = goSchMsg;
    }
    
    /**
     * 登校時メッセージを取得する
     */
    public String getGoSchMsg() {
        return goSchMsg;
    }
    /**
     * 認証エラーメッセージを設定する
     */
    public void setGoSchErrMsg(String goSchErrMsg) {
        this.goSchErrMsg = goSchErrMsg;
    }
    
    /**
     * 認証エラーメッセージを取得する
     */
    public String getGoSchErrMsg() {
        return goSchErrMsg;
    }
    /**
     * 時間内エラーメッセージを設定する
     */
    public void setDouGoSchMsg(String douGoSchMsg) {
        this.douGoSchMsg = douGoSchMsg;
    }
    
    /**
     * 時間内エラーメッセージを取得する
     */
    public String getDouGoSchMsg() {
        return douGoSchMsg;
    }
    /**
     * 登校自動ポイントを設定する
     */
    public void setGoSchPoint(Integer goSchPoint) {
        this.goSchPoint = goSchPoint;
    }
    
    /**
     * 登校自動ポイントを取得する
     */
    public Integer getGoSchPoint() {
        return goSchPoint;
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
     * 実行登録時自動ポイントを取得する
     *
     * @return doLoginPoint 実行登録時自動ポイント
     */
    public Integer getDoLoginPoint() {
        return this.doLoginPoint;
    }

    /**
     * 実行登録時自動ポイントを設定する
     *
     * @param doLoginPoint 実行登録時自動ポイント
     */
    public void setDoLoginPoint(Integer doLoginPoint) {
        this.doLoginPoint = doLoginPoint;
    }

    /**
     * 実行時間累計自動ポイントを取得する
     *
     * @return doTotalPoint 実行時間累計自動ポイント
     */
    public Integer getDoTotalPoint() {
        return this.doTotalPoint;
    }

    /**
     * 実行時間累計自動ポイントを設定する
     *
     * @param doTotalPoint 実行時間累計自動ポイント
     */
    public void setDoTotalPoint(Integer doTotalPoint) {
        this.doTotalPoint = doTotalPoint;
    }

    /**
     * 入室時自動ポイントを取得する
     *
     * @return inRoomPoint 入室時自動ポイント
     */
    public Integer getInRoomPoint() {
        return this.inRoomPoint;
    }

    /**
     * 入室時自動ポイントを設定する
     *
     * @param inRoomPoint 入室時自動ポイント
     */
    public void setInRoomPoint(Integer inRoomPoint) {
        this.inRoomPoint = inRoomPoint;
    }

    /**
     * 合格時自動ポイントを取得する
     *
     * @return paddPoint 合格時自動ポイント
     */
    public Integer getPaddPoint() {
        return this.paddPoint;
    }

    /**
     * 合格時自動ポイントを設定する
     *
     * @param paddPoint 合格時自動ポイント
     */
    public void setPaddPoint(Integer paddPoint) {
        this.paddPoint = paddPoint;
    }

    /**
     * 満点時自動ポイントを取得する
     *
     * @return fullMarksPoint 満点時自動ポイント
     */
    public Integer getFullMarksPoint() {
        return this.fullMarksPoint;
    }

    /**
     * 満点時自動ポイントを設定する
     *
     * @param fullMarksPoint 満点時自動ポイント
     */
    public void setFullMarksPoint(Integer fullMarksPoint) {
        this.fullMarksPoint = fullMarksPoint;
    }

    /**
     * 宿題提出時自動ポイントを取得する
     *
     * @return workOutPoint 宿題提出時自動ポイント
     */
    public Integer getWorkOutPoint() {
        return this.workOutPoint;
    }

    /**
     * 宿題提出時自動ポイントを設定する
     *
     * @param workOutPoint 宿題提出時自動ポイント
     */
    public void setWorkOutPoint(Integer workOutPoint) {
        this.workOutPoint = workOutPoint;
    }

    /**
     * 出席登録時自動ポイントを取得する
     *
     * @return attentOutPoint 出席登録時自動ポイント
     */
    public Integer getAttentOutPoint() {
        return this.attentOutPoint;
    }

    /**
     * 出席登録時自動ポイントを設定する
     *
     * @param attentOutPoint 出席登録時自動ポイント
     */
    public void setAttentOutPoint(Integer attentOutPoint) {
        this.attentOutPoint = attentOutPoint;
    }

    /**
     * 誕生日時自動ポイントを取得する
     *
     * @return birthdayTimePoint 誕生日時自動ポイント
     */
    public Integer getBirthdayTimePoint() {
        return this.birthdayTimePoint;
    }

    /**
     * 誕生日時自動ポイントを設定する
     *
     * @param birthdayTimePoint 誕生日時自動ポイント
     */
    public void setBirthdayTimePoint(Integer birthdayTimePoint) {
        this.birthdayTimePoint = birthdayTimePoint;
    }
}
