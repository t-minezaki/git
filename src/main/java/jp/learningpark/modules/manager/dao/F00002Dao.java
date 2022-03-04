/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstManagerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * F00002 管理者基本情報登録画面 Dao
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/11 : xiong: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00002Dao {

    /**
     * 管理者基本マスタから取得し
     * @param managerId
     * @return
     */
    MstManagerEntity getManager(String managerId);

    /**
     * 更新内容
     * @param entity
     * @return
     */
    Integer updateManagerInfo(@Param("entity")Map<String, Object> entity);

    /**
     * 更新内容
     * @param entity
     * @return
     */
    Integer updateMentorInfo(@Param("entity")Map<String, Object> entity);
}
