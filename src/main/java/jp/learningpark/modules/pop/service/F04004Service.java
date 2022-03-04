/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.modules.pop.dto.F04004Dto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>配信先設定画面 Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/06/18: yang: 新規<br />
 * @version 1.0
 */
@Service
public interface F04004Service {
    List<F04004Dto> getEntity(String schy, List<Integer> group, List<String> orgIdList, String stuId, String stuNm, List<String> stuIds, List<String> rowIds, List<String> stuIdList);
}
