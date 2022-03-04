/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.pop.dto.F00046Dto;
import jp.learningpark.modules.pop.service.F00046Service;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>組織検索追加画面 Controller</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/10/14: zmh: 新規<br />
 * @version 1.0
 */
@RequestMapping("/pop/f00046")
@RestController
public class F00046Controller {

    private final F00046Service f00046Service;
    private final CommonService commonService;

    public F00046Controller(F00046Service f00046Service, CommonService commonService) {
        this.f00046Service = f00046Service;
        this.commonService = commonService;
    }

    /**
     * <p>組織IDと組織名に応じたファジークエリ組織情報</p>
     *
     * @param orgId 照会する組織ID
     * @param orgName 照会する組織名
     * @return ファジークエリで見つかった組織情報を返す
     */
    @GetMapping("/search")
    public R search(String orgId, String orgName) {
        List<F00046Dto> orgList = f00046Service.search(orgId, orgName);

        // 上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
        if (orgList.isEmpty()){
            throw new RRException(MessageUtils.getMessage("MSGCOMN0017", "対象組織"));
        }

        return R.ok().put("orgList", orgList);
    }

    /**
     * <p>取得したの最上位組織保留する</p>
     *
     * @param selectedOrg ページで選択した組織
     * @return 選ばれたトップ組織
     */
    @PostMapping("/retainTopOrg")
    public R retainTopOrg(@RequestBody(required = false) LinkedList<MstOrgEntity> selectedOrg){
        if (selectedOrg == null || selectedOrg.isEmpty()){
            throw new RRException(MessageUtils.getMessage("MSGCOMN0096", "組織対象", "「＞　または　＞＞」"));
        }

        List<MstOrgEntity> topOrg = commonService.retainTopOrgList(selectedOrg);

        return R.ok().put("orgList", topOrg);
    }

}
