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
 * 生徒ポイント
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_point")
public class StuPointEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 生徒ID
     */
    @NotNull
    private String stuId;

    /**
     * 組織ID
     */
    @NotNull
    private String orgId;

    /**
     * 登校自動ポイント
     */
    @NotNull
    private Integer point;

    /**
     * 実行登録時自動ポイント
     */
    @NotNull
    private Integer doLoginPoint;

    /**
     * 実行時間累計自動ポイント
     */
    @NotNull
    private Integer doTotalPoint;

    /**
     * 入室時自動ポイント
     */
    @NotNull
    private Integer inRoomPoint;

    /**
     * 調整ポイント
     */
    @NotNull
    private Integer movePoint;

    /**
     * 調整ポイントの累計(整数の場合)
     */
    @NotNull
    private Integer movePointAdd;
    /* add at 2021/08/12 for V9.02 by NWT LiGX START */
    /**
     * 出席簿登録時ポイント
     */
    @NotNull
    private Integer attendPoint;
    /**
     * 誕生日時ポイント
     */
    @NotNull
    private Integer birthdayPoint;
    /* add at 2021/08/12 for V9.02 by NWT LiGX END */
    /**
     * リセット日時
     */
    private Timestamp resetDatime;

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
     * 生徒IDを設定する
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
    
    /**
     * 生徒IDを取得する
     */
    public String getStuId() {
        return stuId;
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
     * 登校自動ポイントを取得する
     *
     * @return point 登校自動ポイント
     */
    public Integer getPoint() {
        return this.point;
    }

    /**
     * 登校自動ポイントを設定する
     *
     * @param point 登校自動ポイント
     */
    public void setPoint(Integer point) {
        this.point = point;
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
     * 調整ポイントを取得する
     *
     * @return movePoint 調整ポイント
     */
    public Integer getMovePoint() {
        return this.movePoint;
    }

    /**
     * 調整ポイントを設定する
     *
     * @param movePoint 調整ポイント
     */
    public void setMovePoint(Integer movePoint) {
        this.movePoint = movePoint;
    }

    /**
     * リセット日時を取得する
     *
     * @return resetDatime リセット日時
     */
    public Timestamp getResetDatime() {
        return this.resetDatime;
    }

    /**
     * リセット日時を設定する
     *
     * @param resetDatime リセット日時
     */
    public void setResetDatime(Timestamp resetDatime) {
        this.resetDatime = resetDatime;
    }

    /**
     * 調整ポイントの累計(整数の場合)を取得する
     *
     * @return movePointAdd 調整ポイントの累計(整数の場合)
     */
    public Integer getMovePointAdd() {
        return this.movePointAdd;
    }

    /**
     * 調整ポイントの累計(整数の場合)を設定する
     *
     * @param movePointAdd 調整ポイントの累計(整数の場合)
     */
    public void setMovePointAdd(Integer movePointAdd) {
        this.movePointAdd = movePointAdd;
    }
    /* add at 2021/08/12 for V9.02 by NWT LiGX START */
    /**
     * 出席簿登録時ポイントを取得する
     *
     * @return attendPoint 出席簿登録時ポイント
     */
    public Integer getAttendPoint() {
        return this.attendPoint;
    }

    /**
     * 出席簿登録時ポイントを設定する
     *
     * @param attendPoint 出席簿登録時ポイント
     */
    public void setAttendPoint(Integer attendPoint) {
        this.attendPoint = attendPoint;
    }

    /**
     * 誕生日時ポイントを取得する
     *
     * @return birthdayPoint  誕生日時ポイント
     */
    public Integer getBirthdayPoint() {
        return this.birthdayPoint;
    }

    /**
     * 誕生日時ポイントを設定する
     *
     * @param birthdayPoint 誕生日時ポイント
     */
    public void setBirthdayPoint(Integer birthdayPoint) {
        this.birthdayPoint = birthdayPoint;
    }
    /* add at 2021/08/12 for V9.02 by NWT LiGX END */
}
