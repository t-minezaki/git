/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;
import jp.learningpark.modules.manager.dto.F07011Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F07011_成績教科メンテナンス一覧画面 Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/04/23 : yang: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F07011Dao {
    /**
     *<p>コードマスタ情報取得</p>
     *
     * @param schy 学年
     * @param codCd 教科CD
     * @param subName 教科名
     * @param limit 件数
     * @param page ページ数
     * @return
     */
    List<F07011Dto> mstcodList(@Param("schy") String schy,
                               @Param("codCd") String codCd,
                               @Param("subName") String subName,
                               @Param("limit") Integer limit,
                               @Param("page") Integer page);

    /**
     *<p>コードマスタ情報記録数取得</p>
     *
     * @param schy 学年
     * @param codCd 教科CD
     * @param subName 教科名
     * @return
     */
    Integer mstcodCount(@Param("schy") String schy,
                        @Param("codCd") String codCd,
                        @Param("subName") String subName);

}
