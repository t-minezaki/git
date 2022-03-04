/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F04005Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F04005 塾ニュース照会画面 Dao</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/14 : wen: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F04005Dao {

    /**
     * <p>「組織ID」＝セッションデータ．組織IDの場合</p>
     *
     * @param id セッションデータ.ID
     * @return
     */
    List<F04005Dto> getStuList(Integer id);
    List<F04005Dto> getotherStu(@Param("id") Integer id,@Param("orgId")String orgId);
}

