/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F03001Dto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>教科書一覧</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/12/26 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F03001Service {
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
     * 教科書一覧カウント
     *
     * @param orgId        組織ID
     * @param orgIdList    組織リスト
     * @param schyDiv      学年区分
     * @param subjtDiv     教科書区分
     * @param publisherDiv 出版社区分
     * @param brandCd      ブランドコード
     * @param textbNm      教科書名
     * @param textbId      教科書ID
     * @return
     */
    Integer getTextbCount(String orgId, String schyDiv, String subjtDiv, String publisherDiv, String brandCd, String textbNm, Integer textbId);


    /**
     * 教科書一覧外層リスト
     *
     * @param orgId        組織ID
     * @param schyDiv      学年区分
     * @param subjtDiv     教科書区分
     * @param publisherDiv 出版社区分
     * @param brandCd      ブランドコード
     * @param orgFlg       本組織フラグ
     * @param pageSize     ページサイズ
     * @param textbNm      教科書名
     * @return
     */
    List<F03001Dto> getTextbListUpLevel(String orgId, String schyDiv, String subjtDiv, String publisherDiv, String brandCd, Integer limit, Integer page, String textbNm);

    /**
     * 教科書削除
     *
     * @param f03001Dto 教科書一覧Dto
     * @return
     */
    R delBook(F03001Dto f03001Dto);

    /**
     * インポート
     *
     * @param file         ファイル
     * @param schyDiv      学年区分
     * @param subjtDiv     教科書区分
     * @param publisherDiv 出版社区分
     * @param textbNm      教科書名
     * @return
     */
    R importFile(MultipartFile file, String schyDiv, String subjtDiv, String publisherDiv, String textbNm);

    /**
     * 塾学習期間IDの取得
     *
     * @param textbId 教科書ID
     * @param orgId   組織ID
     * @param brandCd ブランドコード
     * @return
     */
    Integer selectMstCrmschLearnPrdId(Integer textbId, String orgId, String brandCd);

    /**
     * 本組織と全部の上層組織Idを取得
     *
     * @param orgId   組織ID
     * @param brandCd ブランドコード
     * @return
     */
    List<F03001Dto> selectAllUpLevOrgByOrgId(String orgId, String brandCd);
}
