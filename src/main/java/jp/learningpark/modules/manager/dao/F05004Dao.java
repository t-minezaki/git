/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.manager.dto.F05004Dto;
import jp.learningpark.modules.manager.dto.F05004DtoStu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F05004_知らせ照会画面 Dao</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/14 : gong: 新規<br />
 * 2019/06/18 : hujiaxing: mod<br />
 * @version 1.0
 */
@Mapper
public interface F05004Dao {
    /**
     * 「組織ID」＝セッションデータ．組織IDの場合、下記条件より、画面．配信先組織を取得し表示する
     *
     * @param noticeId お知らせＩＤ
     * @return
     */
    List<F05004Dto> selectOrgsByNoticeId(Integer noticeId);

    /**
     * 「組織ID」≠セッションデータ．組織IDの場合、上層組織から指定された配信先組織のうち、本組織と本組織の下層組織を取得し
     *
     * @param noticeId お知らせＩＤ
     * @param orgs 本組織、下位組織IDリスト
     * @return
     */
    List<F05004Dto> selectOrgsByNoticeIdWithOrgIds(@Param("noticeId") Integer noticeId, @Param("orgs") List<OrgAndLowerOrgIdDto> orgs);

    /**
     * <p>select student list by notice id (org id)</p>
     * @param noticeId お知らせＩＤ
     * @param orgId 組織ID
     * @return
     */
    List<F05004DtoStu> selectStuListByNoticeId(@Param("noticeId") Integer noticeId ,@Param("orgId")String orgId);
}
