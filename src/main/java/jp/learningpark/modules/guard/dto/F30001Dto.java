/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

import jp.learningpark.modules.common.entity.MstStuEntity;

/**
 * F30001保護者基本情報登録画面 Dto
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/15 : xiong: 新規<br />
 * @version 1.0R
 */
public class F30001Dto extends MstStuEntity {

    /**
     * count
     */
    private Integer count;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * ユーザーID
     */
    private  String usrId;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String getStuId() {
        return stuId;
    }

    @Override
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
}
