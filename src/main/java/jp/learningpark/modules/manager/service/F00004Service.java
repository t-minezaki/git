/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F00004GuardDto;
import jp.learningpark.modules.manager.dto.F00004ManagerDto;
import jp.learningpark.modules.manager.dto.F00004MentorDto;
import jp.learningpark.modules.manager.dto.F00004StuDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>F00004 利用者基本情報設定・修正 Service</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/25 : gong: 新規<br />
 * @version 1.0
 */
public interface F00004Service {
    /**
     * インポート
     *
     * @param file ファイル
     * @param type 選択したロール
     * @param div  エラーとする or 上書きする
     * @return
     */
    R importFile(MultipartFile file, Integer type,Integer div);

    /**
     * エクスポート処理、管理者が選択した場合
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00004ManagerDto> getExcelDataOfManager(String orgId);

    /**
     * エクスポート処理、メンターが選択した場合
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00004MentorDto> getExcelDataOfMentor(String orgId);

    /**
     * エクスポート処理、保護者が選択した場合
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00004GuardDto> getExcelDataOfGuard(String orgId);

    /**
     * エクスポート処理、生徒が選択した場合
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00004StuDto> getExcelDataOfStu(String orgId);

    /**
     * 関係ファイルのインポート
     *
     * @param file ファイル
     * @return
     */
    R guardWithStuImprot(MultipartFile file);

    /**
     * 生徒・保護者の依存関係情報エクスポート処理、生徒が選択した場合
     *
     * @param orgId 組織ID
     * @return
     */
    List<F00004StuDto> getExcelDataOfStuWithGuard(String orgId);
}
