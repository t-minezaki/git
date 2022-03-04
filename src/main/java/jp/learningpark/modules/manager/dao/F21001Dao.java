/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstMentorEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * F00002 メンター基本情報登録画面 Dao
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/11 : xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F21001Dao {

    /**
     * 管理者基本マスタから取得し
     * @param mentorId
     * @return
     */
    MstMentorEntity getMentor(String mentorId);
}
