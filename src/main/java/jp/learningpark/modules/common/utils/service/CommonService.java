/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.utils.service;

import com.alibaba.fastjson.JSONObject;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstHolidayEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.dto.SchdlDto;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * <p>共通処理 Serviceです。</p>
 *
 * @author NWT : gaoli <br />
 * 変更履歴 <br />
 * 2018/10/08 : gaoli: 新規<br />
 * @version 1.0
 */
public interface CommonService {

    /**
     * <p>計画図エリア情報取得処理(F10301)</p>
     *
     * @param stuId 生徒ID
     * @param startYmd 週開始日
     * @param endYmd 週終了日
     * @return 計画図エリア情報
     */
    List<SchdlDto> getSchdlList(String stuId, Date startYmd, Date endYmd);

    /**
     * <p>計画ブロックの取得（NWT譚）</p>
     *
     * @param stuId 生徒ID
     * @param startYmd 週開始日
     * @param endYmd 週終了日
     * @return 計画ブロック
     */
    List<SchdlDto> selectPlanBlockList(String stuId, Date startYmd, Date endYmd);

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
    List<SchdlDto> selectFixedBlockList(Integer id, String stuId, Date startYmd, Boolean checkStart, Boolean checkEnd);

    /**
     * @param id 生徒固定スケジュールID
     * @param stuId 生徒ID
     * @param startYmd 開始日
     * @param checkStart 開始時間チェック
     * @param checkEnd 終了時間チェック
     * @return 印刷用生徒固定スケジュールと個別スケジュール調整情報
     */
    List<SchdlDto> getPrintFixedBlockList(Integer id, String stuId, Date startYmd, Boolean checkStart, Boolean checkEnd);

    /**
     * <p>計画図エリア情報取得処理(F10301)</p>
     *
     * @param stuId 生徒ID
     * @param startYmd 週開始日
     * @param endYmd 週終了日
     * @param all 全て
     * @return 計画図エリア情報
     */
    List<SchdlDto> getSchdlList(String stuId, Date startYmd, Date endYmd, String all);

    /**
     * <p>計画ブロックの取得（NWT譚）</p>
     *
     * @param stuId 生徒ID
     * @param startYmd 開始日
     * @param endYmd 終了日
     * @param all 全て
     * @return 計画ブロック
     */
    List<SchdlDto> selectPlanBlockList(String stuId, Date startYmd, Date endYmd, String all);

    /**
     * <p>計画ブロックの取得（NWT譚）</p>
     *
     * @param stuId 生徒ID
     * @param startYmd 開始日
     * @param endYmd 終了日
     * @param all 全て
     * @return 計画ブロック
     */
    List<SchdlDto> getPrintPlanBlockList(String stuId, Date startYmd, Date endYmd, String all);

    /**
     * 変更後学年の塾学習期間IDの取得
     *
     * @param orgId 組織ID
     * @param brandCd ブランドコード
     * @param schyDiv 学年区分
     * @return
     */
    Integer getctCrmLearnPrdIdAfterUpdateSchy(String orgId, String brandCd, String schyDiv);

    /**
     * <p>本組織及び下層組織リストの取得</p>
     *
     * @param brandCd ブランドコード
     * @param orgId 組織ID
     * @return
     */
    List<OrgAndLowerOrgIdDto> getThisAndLowerOrgId(String brandCd, String orgId);

    /**
     * <p>本組織及び上下層組織リストの取得</p>
     *
     * @param orgId 本組織ID
     * @return
     */
    List<OrgAndLowerOrgIdDto> getAllOrgList(String orgId);

    /**
     * <p>本組織及び上層組織リストの取得</p>
     *
     * @param brandCd ブランドコード
     * @param orgId 本組織ID
     * @return
     */
    List<OrgAndLowerOrgIdDto> getUpLvOrgList(String brandCd, String orgId);

    /**
     * @param afterUsrId ログインID
     * @param brandCd 　ブランドコード
     * @return
     */
    List<String> getOrgsForChoose(String afterUsrId, String brandCd);

    Integer unReadCount(String orgId, String guardId, String stuId);

    /**
     * ignore
     */
    List<MstOrgEntity> retainTopOrgList(List<MstOrgEntity> selectedOrg);

    /**
     * <p>取得したの最上位組織保留する</p>
     * <p>組織情報のコレクションを受け取り、同じ組織内で最高レベルの組織を維持する</p>
     *
     * <p>たとえば、次の状況</p>
     * <pre>
     *     org      level
     *     NE         0
     *     GK         0
     *     GC         1
     *     YW         2
     * </pre>
     * <p>NE GC YWが同じ組織であると仮定する</p>
     * <p>その場合、メソッドの最終的な戻り結果はNE GKのみになります。</p>
     *
     * @param selectedOrg トップ組織を取得する必要があります
     * @param brandCd ブランドコードが入力されていない場合は、セッションから取得します
     * @return {param: selectedOrg} 最上位の組織コレクション
     * @since 8.0
     */
    List<MstOrgEntity> retainTopOrgList(List<MstOrgEntity> selectedOrg, String brandCd);

    /**
     * デバイスTOKEN管理(2020/11/10楊)
     *
     * @param deviceToken
     * @param mstUsrEntity
     * @return
     */
    R updateDeviceToken(String deviceToken, MstUsrEntity mstUsrEntity, String phoneType, HttpServletRequest request);

    /**
     * push 未読件数を取得する
     * 2020/12/01
     * modify yang
     *
     * @param guardId
     * @return
     */
    Integer pushUnreadCount(String guardId);

    /**
     * tokenを取得する
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    String getToken();

    /**
     * @return
     */
    String getOrgIdAdd();

    //2021/01/22 liyuhuan modify start
    /**
     * @return
     */
    String getOrgIdLower();
    //2021/01/22 liyuhuan modify end

    /**
     * @return
     */
    MstCodDEntity getServer();

    /**
     * @param year
     * @return
     */
    List<MstHolidayEntity> selectHolidayByTgtYmd(String year);

    R contextLoads(HttpServletRequest request, JSONObject node, Integer flg);

    /**
     * QRコード暫定対応
     *
     * @param guardId
     * @return
     */
    Integer pushUnreadCountForQR(String guardId);
    /* 2021/02/20 4-29 start */
    /**
     *
     * @param deviceToken
     * @param mstUsrEntity
     * @param phoneType
     * @param request
     * @return
     */
    R updateDeviceTokenForPUSH(String deviceToken, MstUsrEntity mstUsrEntity, String phoneType, HttpServletRequest request,String userId);
    /* 2021/02/20 4-29 end */

    // add at 2021/9/29 for V9.02 by NWT HuangXL START
    /**
     * 最大端末数を超過するデバイスを無効化するため
     * @param usrId     セッションデータ．ユーザID
     */
    void deleteRedundantDeviceToken(String usrId);
    // add at 2021/9/29 for V9.02 by NWT HuangXL END
}