package jp.learningpark.modules.com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstUsrFirtPwEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstUsrFirtPwService;
import jp.learningpark.modules.common.service.MstUsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManamiruToLeadSSOController {
    @Autowired
    MstCodDService mstCodDService;
    @Autowired
    MstUsrService mstUsrService;

    /**
     *
     * @return
     */
    @Autowired
    MstUsrFirtPwService mstUsrFirtPwService;

    @RequestMapping(value = "/toUrl",method = RequestMethod.POST)
    public R toUrl(){
        //SSO用URLの各項目内容を取得するため、下記の条件でコードマスタ_明細を取得する。
        MstCodDEntity mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key","MANAMIRU_TO_LEAD_URL").eq("del_flg",0));
        //取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
        if (mstCodDEntity == null){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","SSO用URL"));
        }
        //ログインユーザの初期パスワードを取得するため、下記の条件でユーザ初期パスワード管理データを取得する。
        MstUsrFirtPwEntity mstUsrFirtPwEntity = mstUsrFirtPwService.getOne(new QueryWrapper<MstUsrFirtPwEntity>().eq("usr_id", ShiroUtils.getUserId()).eq("del_flg",0));
        if (mstUsrFirtPwEntity == null){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","ユーザのパスワード"));
        }
        return R.ok().put("mstCodDEntity",mstCodDEntity).put("mstUsrFirtPwEntity",mstUsrFirtPwEntity);
    }
}
