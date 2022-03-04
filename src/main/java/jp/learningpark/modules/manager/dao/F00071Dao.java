/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.manager.dto.F00071Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F00071_生徒ステータス状況詳細画面 Dao</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/20 : tan: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00071Dao extends BaseMapper<F00071Dto> {

    /**
     * 初期化の画面．検索条件．組織名
     *
     * @param brandCd
     * @param sOrgId
     * @return
     */
    List<F00071Dto> getOrganization(@Param("brandCd") String brandCd,
                                    @Param("sOrgId") String sOrgId);

    /**
     * 初期化の画面．検索条件．ステータス
     *
     * @return
     */
    List<F00071Dto> getUsrStatus();

    /**
     * 初期化の画面．検索条件．異動ステータス
     *
     * @return
     */
    List<F00071Dto> getDisabilityStatus();

    /**
     * ユーザ基本マスタ、生徒基本マスタ、転出転入履歴を元に一覧情報を取得
     *
     * @param orgName   画面．検索条件．組織名
     * @param stuId     画面．検索条件．生徒ＩＤ
     * @param stuName   画面．検索条件．生徒姓名
     * @param usrStatus 画面．検索条件．ステータス
     * @param crmSts    画面．検索条件．異動ステータス
     * @param moveYmd   画面．検索条件．異動年月日
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @return
     */
    List<F00071Dto> getValue(@Param("orgIdList") List<OrgAndLowerOrgIdDto> orgAndLowerOrgIdDto,
                             @Param("stuId") String stuId,
                             @Param("stuName") String stuName,
                             @Param("usrStatus") String usrStatus,
                             @Param("crmSts") String crmSts,
                             @Param("moveYmd") String moveYmd,
                             @Param("limit") Integer limit,
                             @Param("page") Integer page);

    /**
     * 総件数をとる
     *
     * @param orgName   画面．検索条件．組織名
     * @param stuId     画面．検索条件．生徒ＩＤ
     * @param stuName   画面．検索条件．生徒姓名
     * @param usrStatus 画面．検索条件．ステータス
     * @param crmSts    画面．検索条件．異動ステータス
     * @param moveYmd   画面．検索条件．異動年月日
     * @return
     */
    Integer totalCount(@Param("orgIdList") List<OrgAndLowerOrgIdDto> orgAndLowerOrgIdDto,
                       @Param("stuId") String stuId,
                       @Param("stuName") String stuName,
                       @Param("usrStatus") String usrStatus,
                       @Param("crmSts") String crmSts,
                       @Param("moveYmd") String moveYmd);

}

