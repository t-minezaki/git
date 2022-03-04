/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.pop.dto.F03005Dto;


/**
 * <p>F03005_単元追加・編集画面 Service</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/01/13 : wen: 新規<br />
 * @version 1.0
 */
public interface F03005Service {

    /**
     * <p>単元追加・編集画面</p>
     *
     * F03005 Dto
     * @return
     */
    R addMstUnitInfo(F03005Dto f03005Dto);
}
