/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F09020Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F09020_一斉お知らせ配信(新規作成)（スマホ） </p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/03/03 : zpa: 新規<br />
 * @version 1.0
 */
public interface F09020Dao {
    List<F09020Dto> init1();
    F09020Dto init2(@Param("noticeId") Integer noticeId);
    List<F09020Dto> getList(@Param("stuIdlist") List<String> stuIdList,@Param("orgIdlist") List<String> orgIdList);

    /**
     *
     */
    List<F09020Dto> getDeliverStuList(@Param("noticeId") Integer noticeId);
}
