/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.manager.dto.F04015Dto;

import java.util.List;

/**
 * <p>F04015_配信先既読未読状態確認一覧画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/17 : zpa: 新規<br />
 * @version 1.0
 */
public interface F04015Service {
    List<F04015Dto> init01(Integer noticeId, Integer limit, Integer page);
    Integer init01Count(Integer noticeId);
    List<F04015Dto> init02(String orgIds,Integer noticeId, Integer limit, Integer page);
    Integer init02Count(String orgIds,Integer noticeId);
    List<MstOrgEntity> getOrg(String brandCd,String orgId);
}
