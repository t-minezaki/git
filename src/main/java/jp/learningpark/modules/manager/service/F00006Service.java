/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F00006Dto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>F00006 メンター生徒管理画面 Service</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/12/26 : wen: 新規<br />
 * @version 1.0
 */
public interface F00006Service {
    /**
     * インポート
     *
     * @param file ファイル
     * @param type 新規・修正（エラーとする）、新規・修正（上書き）、削除選択フラッグ
     * @return
     */
    R importFile(MultipartFile file, Integer type);

    /**
     * <p>ユーザーIDと生徒IDを取得</p>
     *
     * @param orgId 前画面から受け取ったログイン管理者の組織ID
     * @return　ユーザーIDと生徒ID
     */
    List<F00006Dto> getAfterUsrIdAndStuId(String orgId);
}
