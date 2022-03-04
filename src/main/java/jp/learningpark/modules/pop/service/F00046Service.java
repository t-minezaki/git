/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.modules.pop.dto.F00046Dto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>組織検索追加画面 Service</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/10/14: zmh: 新規<br />
 * @version 1.0
 */
@Service
public interface F00046Service {

    /**
     * 組織IDと組織名に応じたファジークエリ組織情報
     *
     * @param orgId 照会する組織ID
     * @param orgName 照会する組織名
     * @return ファジークエリで見つかった組織情報を返す
     */
    List<F00046Dto> search(String orgId, String orgName);
}
