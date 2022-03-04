/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.manager.dto.F09022Dto;
import jp.learningpark.modules.manager.service.F09022Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>スマホ_一斉お知らせ配信状況一覧画面</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/02/27 : lyh: 新規<br />
 * @version 6.0
 */
@RequestMapping(value = "/manager/F09022")
@RestController
public class F09022Controller {
    @Autowired
    F09022Service f09022Service;
    @RequestMapping(value = "/init",method = RequestMethod.GET)
    //初期表示
    public R init(boolean flag,Integer noticeId,Integer limit){
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        boolean lastPage = true;
        String flg = "1";
        if (!flag){
            flg = "0";
        }
        //下記条件とソート順でお知らせ配信状況を取得する。
        List<F09022Dto> stuList = f09022Service.init(orgId,noticeId,flg);
        //上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
        if (stuList.size()==0){
            if (flg =="0"){
                return R.error(MessageUtils.getMessage("MSGCOMN0017","お知らせ配信状況"));
            }else {
                return R.error(MessageUtils.getMessage("MSGCOMN0017","未読お知らせ"));
            }
        }
        //保護者お知らせ閲覧状況・閲覧状況区分
        for (F09022Dto dto:stuList) {
            if (StringUtils.equals(dto.getReadingStsDiv(),"0")){
                dto.setReadingStsDiv("未読");
            }else {
                dto.setReadingStsDiv("");
            }
        }
        if (limit < stuList.size()){
            lastPage = false;
            stuList = stuList.subList(0,limit);
        }else {
            stuList = stuList.subList(0,stuList.size());
        }
        return R.ok().put("stuList",stuList).put("lastPage",lastPage);
    }
}