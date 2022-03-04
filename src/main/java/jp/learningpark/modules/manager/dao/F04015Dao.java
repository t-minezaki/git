/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dto.F04015Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F04015_配信先既読未読状態確認一覧画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/17 : zpa: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F04015Dao extends BaseMapper<F04015Dto> {
    List<F04015Dto> init01(@Param("noticeId") Integer noticeId,@Param("limit")Integer limit,@Param("page")Integer page);
    Integer init01Count(@Param("noticeId") Integer noticeId);
    List<F04015Dto> init02(@Param("orgIds") String orgIds, @Param("noticeId") Integer noticeId,@Param("limit")Integer limit,@Param("page")Integer page);
    Integer init02Count(@Param("orgIds") String orgIds, @Param("noticeId") Integer noticeId);
    List<MstOrgEntity> getOrg(@Param("brandCd") String brandCd,@Param("orgId") String orgId);
}

