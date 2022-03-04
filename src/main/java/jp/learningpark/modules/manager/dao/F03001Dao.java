 /*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

 import jp.learningpark.modules.manager.dto.F03001Dto;
 import org.apache.ibatis.annotations.Mapper;
 import org.apache.ibatis.annotations.Param;

 import java.util.List;

/**
 * <p>教科書一覧</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/12/26 : hujunjie: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F03001Dao {
    /**
     * 学年リスト取得
     *
     * @return
     */
    List<F03001Dto> getSchyList();

    /**
     * 教科リスト取得
     *
     * @return
     */
    List<F03001Dto> getSubjtList();

    /**
     * 出版社リスト取得
     *
     * @return
     */
    List<F03001Dto> getPublisherList();

    /**
     * 教科書一覧リスト
     *
     * @param orgId        組織ID
     * @param orgIdList    組織IDList
     * @param schyDiv      学年区分
     * @param subjtDiv     教科区分
     * @param publisherDiv 出版社区分
     * @param brandCd      ブランドコード
     * @param limit        limit
     * @param page         ページ
     * @param textbNm      教科書名
     * @param textbId      教科書ID
     * @return
     */
    List<F03001Dto> getTextbList(@Param("orgId") String orgId, @Param("schyDiv") String schyDiv, @Param("subjtDiv") String subjtDiv
            , @Param("publisherDiv") String publisherDiv, @Param("brandCd") String brandCd, @Param("limit") Integer limit
            , @Param("page") Integer page, @Param("textbNm") String textbNm, @Param("textbId") Integer textbId);

    /**
     * 教科書一覧カウント
     *
     * @param orgId        組織ID
     * @param orgIdList    組織IDList
     * @param schyDiv      学年区分
     * @param subjtDiv     教科区分
     * @param publisherDiv 出版社区分
     * @param brandCd      ブランドコード
     * @param textbNm      教科書名
     * @param textbId      教科書ID
     * @return
     */
    Integer getTextbCount(@Param("orgId") String orgId, @Param("schyDiv") String schyDiv, @Param("subjtDiv") String subjtDiv
            , @Param("publisherDiv") String publisherDiv, @Param("brandCd") String brandCd, @Param("textbNm") String textbNm, @Param("textbId") Integer textbId);

    /**
     * 教科書一覧外層リスト
     *
     * @param orgId        組織ID
     * @param orgIdList    組織IDList
     * @param schyDiv      学年区分
     * @param subjtDiv     教科区分
     * @param publisherDiv 出版社区分
     * @param brandCd      ブランドコード
     * @param limit        limit
     * @param page         ページ
     * @param textbNm      教科書名
     * @return
     */
    List<F03001Dto> getTextbListUpLevel(@Param("orgId") String orgId, @Param("schyDiv") String schyDiv, @Param("subjtDiv") String subjtDiv
            , @Param("publisherDiv") String publisherDiv, @Param("brandCd") String brandCd, @Param("limit") Integer limit
            , @Param("page") Integer page, @Param("textbNm") String textbNm);

    /**
     * 削除データ取得
     *
     * @param orgId        組織ID
     * @param schyDiv      学年区分
     * @param subjtDiv     教科区分
     * @param publisherDiv 出版社区分
     * @param brandCd      ブランドコード
     * @param textbId      教科書ID
     * @return
     */
    F03001Dto getDelData(@Param("orgId") String orgId, @Param("schyDiv") String schyDiv, @Param("subjtDiv") String subjtDiv
            , @Param("publisherDiv") String publisherDiv, @Param("brandCd") String brandCd, @Param("textbId") Integer textbId);

    /**
     * 教科書デフォルト単元IDが生徒タームプラン設定に存在する判断
     *
     * @param textbId 教科書ID
     * @return
     */
    Integer getTermPlanBook(@Param("textbId") Integer textbId);

    /**
     * 塾学習期間IDの取得
     *
     * @param textbId 教科書ID
     * @param orgId   組織ID
     * @param brandCd ブランドコード
     * @return
     */
    Integer selectMstCrmschLearnPrdId(@Param("textbId") Integer textbId, @Param("orgId") String orgId, @Param("brandCd") String brandCd);

    /**
     * 塾学習期間IDの取得（インポート）
     *
     * @param schyDiv 学年区分
     * @param orgId   組織ID
     * @param brandCd ブランドコード
     * @return
     */
    Integer selectMstCrmschLearnPrdIdToImport(@Param("schyDiv") String schyDiv, @Param("orgId") String orgId, @Param("brandCd") String brandCd);

    /**
     * 本組織と全部の上層組織Idを取得
     *
     * @param orgId   組織ID
     * @param brandCd ブランドコード
     * @return
     */
    List<F03001Dto> selectAllUpLevOrgByOrgId(@Param("orgId") String orgId, @Param("brandCd") String brandCd);
}
