/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F00006Dto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>F00006_メンター生徒管理画面 Dao</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/12/27 : wen: 新規<br />
 * @version 1.0
 */
@Mapper
public interface F00006Dao {

    /**
     * <p>ユーザーIDと生徒IDを取得</p>
     *
     * @param orgId 前画面から受け取ったログイン管理者の組織ID
     * @return　ユーザーIDと生徒ID
     */
    List<F00006Dto> selectAfterUsrIdAndStuId(String orgId);
}
