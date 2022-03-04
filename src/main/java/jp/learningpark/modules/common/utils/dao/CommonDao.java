/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.utils.dao;

import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstHolidayEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.dto.SchdlDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>共通処理 DAOです。</p>
 *
 * @author NWT : gaoli <br />
 * 変更履歴 <br />
 * 2018/10/08 : gaoli: 新規<br />
 * @version 1.0
 */
@Mapper
public interface CommonDao {

    /**
     * 自然カレンダーの対象日当週の生徒固定スケジュールと計画ブロックの取得
     *
     * @param stuId 生徒ID
     * @param startYmd 開始日
     * @param endYmd 終了日
     * @param flg all 全て、以外の場合R1：復習 , S1：学習 , P1：予習, W1：学校の宿題
     * @return 生徒固定スケジュールと計画ブロック情報
     */
    List<SchdlDto> selectSchdlList(@Param("stuId") String stuId, @Param("startYmd") Date startYmd, @Param("endYmd") Date endYmd, @Param("flg") String flg);

    /**
     * <p>計画ブロックの取得（NWT譚）</p>
     *
     * @param stuId 生徒ID
     * @param startYmd 開始日
     * @param endYmd 終了日
     * @param flg all 全て、以外の場合R1：復習 , S1：学習 , P1：予習, W1：学校の宿題
     * @return 計画ブロック
     */
    List<SchdlDto> selectPlanBlockList(@Param("stuId") String stuId, @Param("startYmd") Date startYmd, @Param("endYmd") Date endYmd, @Param("flg") String flg);

    /**
     * <p>計画ブロックの取得（NWT譚）</p>
     *
     * @param stuId 生徒ID
     * @param startYmd 開始日
     * @param endYmd 終了日
     * @param flg all 全て、以外の場合R1：復習 , S1：学習 , P1：予習, W1：学校の宿題
     * @return 計画ブロック
     */
    List<SchdlDto> selectPrintPlanBlockList(
            @Param("stuId") String stuId, @Param("startYmd") Date startYmd, @Param("endYmd") Date endYmd, @Param("flg") String flg);

    /**
     * <p>生徒固定スケジュールと個別スケジュール調整情報の取得（NWT譚）</p>
     *
     * @param id 生徒固定スケジュールID
     * @param stuId 生徒ID
     * @param startYmd 開始日
     * @param checkStart 開始時間チェック
     * @param checkEnd 終了時間チェック
     * @return 生徒固定スケジュールと個別スケジュール調整情報
     */
    List<SchdlDto> selectFixedBlockList(
            @Param("id") Integer id,
            @Param("stuId") String stuId, @Param("startYmd") Date startYmd, @Param("checkStart") Boolean checkStart, @Param("checkEnd") Boolean checkEnd);

    /**
     * @param id 生徒固定スケジュールID
     * @param stuId 生徒ID
     * @param startYmd 開始日
     * @param checkStart 開始時間チェック
     * @param checkEnd 終了時間チェック
     * @return 印刷用生徒固定スケジュールと個別スケジュール調整情報
     */
    List<SchdlDto> selectPrintFixedBlockList(
            @Param("id") Integer id,
            @Param("stuId") String stuId, @Param("startYmd") Date startYmd, @Param("checkStart") Boolean checkStart, @Param("checkEnd") Boolean checkEnd);

    /**
     * 変更後学年の塾学習期間IDの取得
     *
     * @param orgId 組織ID
     * @param brandCd ブランドコード
     * @param schyDiv 学年区分
     * @return
     */
    Integer seletctCrmLearnPrdIdAfterUpdateSchy(@Param("orgId") String orgId, @Param("brandCd") String brandCd, @Param("schyDiv") String schyDiv);

    /**
     * <p>本組織及び下層組織リストの取得</p>
     *
     * @param brandCd ブランドコード
     * @param orgId 組織ID
     * @return
     */
    List<OrgAndLowerOrgIdDto> selectThisAndLowerOrgId(@Param("brandCd") String brandCd, @Param("orgId") String orgId);

    /**
     * <p>本組織及び上下層組織リストの取得</p>
     *
     * @param orgId 本組織ID
     * @return
     */
    List<OrgAndLowerOrgIdDto> selectAllOrgList(@Param("orgId") String orgId);

    /**
     * <p>本組織及び上層組織リストの取得</p>
     *
     * @param orgId 本組織ID
     * @return
     */
    List<OrgAndLowerOrgIdDto> getUpLvOrgList(@Param("brandCd") String brandCd, @Param("orgId") String orgId);

    /**
     * @param afterUsrId ログインID
     * @param brandCd 　ブランドコード
     * @return
     */
    List<String> selectOrgsForChoose(@Param("afterUsrId") String afterUsrId, @Param("manaFlg") String manaFlg, @Param("brandCd") String brandCd);

    /**
     * 組織情報に従って、上位組織情報を照会します
     *
     * @param lowerOrg 最下位の組織
     * @param brandCd ブランドコード
     * @return {lowerOrg}の親組織に戻る
     */
    MstOrgEntity findUpperOrg(@Param("orgInfo") MstOrgEntity lowerOrg, @Param("brandCd") String brandCd);

    /**
     * ファイルサーバーを取得する。
     *
     * @return
     */
    MstCodDEntity selectServer();

    /**
     * @param year
     * @return
     */
    List<MstHolidayEntity> getHolidayByTgtYmd(String year);
}
