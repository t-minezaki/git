/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.learningpark.modules.manager.dto.F04016Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>保護者既読未読詳細一覧画面（マナミルチャンネル）Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/02/10 : yang: 新規<br />
 * @version 6.0
 */
@Mapper
public interface F04016Dao extends BaseMapper<F04016Dto> {
    //保護者お知らせ閲覧状況より、取得する
    List<F04016Dto> init(@Param("noticeId") Integer noticeId, @Param("flg") String flg,@Param("orgId")String orgId, @Param("limit") Integer limit, @Param(
            "page") Integer page);
    //件数の取得
    Integer getTotalCount(@Param("noticeId") Integer noticeId, @Param("flg") String flg,@Param("orgId")String orgId);

}

