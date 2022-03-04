/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.learningpark.modules.manager.dto.F21071Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p>F21071_配信先既読未読状態確認一覧画面（インフォメーション）Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/06/01 : yang: 新規<br />
 * @version 7.0
 */
@Mapper
public interface F21071Dao extends BaseMapper<F21071Dto> {
    /**
     * 既読未読集計一覧を取得する。
     * @param msgId
     * @param orgIdList
     * @param limit
     * @param page
     * @param flg
     * @return
     */
    List<F21071Dto> getGridList(@Param("msgId") Integer msgId,@Param("orgIdList")List<String> orgIdList,@Param("limit") Integer limit,
                                @Param("page") Integer page,
                                @Param("flg")boolean flg);

}

