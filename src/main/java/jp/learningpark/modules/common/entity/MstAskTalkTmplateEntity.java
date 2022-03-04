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
 * 質問面談テンプレート
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_ask_talk_tmplate")
public class MstAskTalkTmplateEntity implements Serializable {
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
     * テンプレートID
     */
    private Integer tempId;

    /**
     * 事項類型区分
     */
    @NotNull
    private String itemTypeDiv;

    /**
     * 利用可区分
     */
    @NotNull
    private String useDiv;

    /**
     * 質問番号
     */
    private Integer askNum;

    /**
     * 設問名
     */
    @NotNull
    private String questionName;

    /**
     * 設問形式区分
     */
    @NotNull
    private String answerTypeDiv;

    /**
     * 選択肢１
     */
    private String optCont1;

    /**
     * 選択肢2
     */
    private String optCont2;

    /**
     * 選択肢3
     */
    private String optCont3;

    /**
     * 選択肢4
     */
    private String optCont4;

    /**
     * 選択肢5
     */
    private String optCont5;

    /**
     * 選択肢6
     */
    private String optCont6;

    /**
     * 選択肢7
     */
    private String optCont7;

    /**
     * 選択肢8
     */
    private String optCont8;

    /**
     * 選択肢9
     */
    private String optCont9;

    /**
     * 選択肢10
     */
    private String optCont10;

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
     * テンプレートIDを設定する
     */
    public void setTempId(Integer tempId) {
        this.tempId = tempId;
    }
    
    /**
     * テンプレートIDを取得する
     */
    public Integer getTempId() {
        return tempId;
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
     * 利用可区分を設定する
     */
    public void setUseDiv(String useDiv) {
        this.useDiv = useDiv;
    }
    
    /**
     * 利用可区分を取得する
     */
    public String getUseDiv() {
        return useDiv;
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
     * 設問名を設定する
     */
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
    
    /**
     * 設問名を取得する
     */
    public String getQuestionName() {
        return questionName;
    }
    /**
     * 設問形式区分を設定する
     */
    public void setAnswerTypeDiv(String answerTypeDiv) {
        this.answerTypeDiv = answerTypeDiv;
    }
    
    /**
     * 設問形式区分を取得する
     */
    public String getAnswerTypeDiv() {
        return answerTypeDiv;
    }
    /**
     * 選択肢１を設定する
     */
    public void setOptCont1(String optCont1) {
        this.optCont1 = optCont1;
    }
    
    /**
     * 選択肢１を取得する
     */
    public String getOptCont1() {
        return optCont1;
    }
    /**
     * 選択肢2を設定する
     */
    public void setOptCont2(String optCont2) {
        this.optCont2 = optCont2;
    }
    
    /**
     * 選択肢2を取得する
     */
    public String getOptCont2() {
        return optCont2;
    }
    /**
     * 選択肢3を設定する
     */
    public void setOptCont3(String optCont3) {
        this.optCont3 = optCont3;
    }
    
    /**
     * 選択肢3を取得する
     */
    public String getOptCont3() {
        return optCont3;
    }
    /**
     * 選択肢4を設定する
     */
    public void setOptCont4(String optCont4) {
        this.optCont4 = optCont4;
    }
    
    /**
     * 選択肢4を取得する
     */
    public String getOptCont4() {
        return optCont4;
    }
    /**
     * 選択肢5を設定する
     */
    public void setOptCont5(String optCont5) {
        this.optCont5 = optCont5;
    }
    
    /**
     * 選択肢5を取得する
     */
    public String getOptCont5() {
        return optCont5;
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
     * 選択肢6を取得する
     *
     * @return optCont6 選択肢6
     */
    public String getOptCont6() {
        return this.optCont6;
    }

    /**
     * 選択肢6を設定する
     *
     * @param optCont6 選択肢6
     */
    public void setOptCont6(String optCont6) {
        this.optCont6 = optCont6;
    }

    /**
     * 選択肢7を取得する
     *
     * @return optCont7 選択肢7
     */
    public String getOptCont7() {
        return this.optCont7;
    }

    /**
     * 選択肢7を設定する
     *
     * @param optCont7 選択肢7
     */
    public void setOptCont7(String optCont7) {
        this.optCont7 = optCont7;
    }

    /**
     * 選択肢8を取得する
     *
     * @return optCont8 選択肢8
     */
    public String getOptCont8() {
        return this.optCont8;
    }

    /**
     * 選択肢8を設定する
     *
     * @param optCont8 選択肢8
     */
    public void setOptCont8(String optCont8) {
        this.optCont8 = optCont8;
    }

    /**
     * 選択肢9を取得する
     *
     * @return optCont9 選択肢9
     */
    public String getOptCont9() {
        return this.optCont9;
    }

    /**
     * 選択肢9を設定する
     *
     * @param optCont9 選択肢9
     */
    public void setOptCont9(String optCont9) {
        this.optCont9 = optCont9;
    }

    /**
     * 選択肢10を取得する
     *
     * @return optCont10 選択肢10
     */
    public String getOptCont10() {
        return this.optCont10;
    }

    /**
     * 選択肢10を設定する
     *
     * @param optCont10 選択肢10
     */
    public void setOptCont10(String optCont10) {
        this.optCont10 = optCont10;
    }
}
