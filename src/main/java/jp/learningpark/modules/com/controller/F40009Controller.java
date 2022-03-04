/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.com.service.F40009Service;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.common.entity.MstManagerEntity;
import jp.learningpark.modules.common.entity.MstMentorEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstGuardService;
import jp.learningpark.modules.common.service.MstManagerService;
import jp.learningpark.modules.common.service.MstMentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * <p>F40009_ログインID解除画面 Controller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/27 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/com/F40009")
@RestController
public class F40009Controller {

    /**
     * F40009_ログインID解除画面 Service
     */
    @Autowired
    private F40009Service f40009Service;

//    /**
//     * システムユーザーオンライン Dao
//     */
//    @Autowired
//    private SysUserOnlineService sysUserOnlineService;

    /**
     * 管理者基本マスタ Service
     */
    @Autowired
    private MstManagerService mstManagerService;

    /**
     * メンター基本マスタ Service
     */
    @Autowired
    private MstMentorService mstMentorService;

    /**
     * 保護者基本マスタ Service
     */
    @Autowired
    private MstGuardService mstGuardService;

    /**
     * 生徒の場合のみ、保護者のメールアドレスより判定する。
     *
     * @param id    変更後ユーザＩＤ
     * @param email メールアドレス
     * @return
     */
    @RequestMapping(value = "/doPostEmail", method = RequestMethod.POST)
    public R doPostEmail(String id, String email, String brandCd, HttpServletRequest request) {
        //姓名
        String userName = "";

        //2.1.2 ＩＤ存在チェック
        MstUsrEntity mstUsrEntity = f40009Service.getOne(id, brandCd);
        if (mstUsrEntity == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0080"));
        }

        //上記取得した「ユーザーＩＤ」
        String usrId = mstUsrEntity.getUsrId();

        //2.1.3 メールアドレス存在チェック
        if (mstUsrEntity.getRoleDiv() != null) {
            switch (mstUsrEntity.getRoleDiv().trim()) {
                case "1":
                    //「管理者」の場合、
                    MstManagerEntity mstManagerEntity = mstManagerService.getOne(new QueryWrapper<MstManagerEntity>().select("flnm_nm", "flnm_lnm").eq("mgr_id", usrId).eq("mailad", email).eq("del_flg", 0));
                    if (mstManagerEntity == null) {
                        return R.error(MessageUtils.getMessage("MSGCOMN0080"));
                    }
                    userName = mstManagerEntity.getFlnmNm() + mstManagerEntity.getFlnmLnm();
                    break;
                case "2":
                    //「メンター」の場合、
                    MstMentorEntity mstMentorEntity = mstMentorService.getOne(new QueryWrapper<MstMentorEntity>().select("flnm_nm", "flnm_lnm").eq("mentor_id", usrId).eq("mailad", email).eq("del_flg", 0));
                    if (mstMentorEntity == null) {
                        return R.error(MessageUtils.getMessage("MSGCOMN0080"));
                    }
                    userName = mstMentorEntity.getFlnmNm() + mstMentorEntity.getFlnmLnm();
                    break;
                case "3":
                    //「保護者」の場合、
                    MstGuardEntity mstGuardEntity = mstGuardService.getOne(new QueryWrapper<MstGuardEntity>().select("flnm_nm", "flnm_lnm").eq("guard_id", usrId).eq("mailad", email).eq("del_flg", 0));
                    if (mstGuardEntity == null) {
                        return R.error(MessageUtils.getMessage("MSGCOMN0080"));
                    }
                    userName = mstGuardEntity.getFlnmNm() + mstGuardEntity.getFlnmLnm();
                    break;
                case "4":
                    //「生徒」の場合、
                    MstStuEntity mstStuEntity = f40009Service.getWithGuard(usrId, email);
                    if (mstStuEntity == null) {
                        return R.error(MessageUtils.getMessage("MSGCOMN0080"));
                    }
                    userName = mstStuEntity.getFlnmNm() + mstStuEntity.getFlnmLnm();
                    break;
                default:
                    break;
            }
        }
        String uuid = UUID.randomUUID().toString();



//        //2.2.3  メール内容の専用のトークンURLの訪問時間を制限するため、下記条件でシステムユーザーオンラインより更新する。
//        SysUserOnlineEntity sysUserOnlineEntity = new SysUserOnlineEntity();
//        //セッションID uuid
//        sysUserOnlineEntity.setSessionId(uuid);
//        //ユーザーID
//        sysUserOnlineEntity.setUserId(usrId);
//        //ログインIPアドレス
//        sysUserOnlineEntity.setIpAddr(request.getRemoteAddr());
//        //ブラウザタイプ
//        sysUserOnlineEntity.setBrowserType("1");
//        //システムデバイス
//        sysUserOnlineEntity.setSysOs("windows");
//        //オンラインステータス
//        sysUserOnlineEntity.setOnlineStatus("1");
//        //セッション開始日時
//        sysUserOnlineEntity.setSesStartDt(DateUtils.getSysTimestamp());
//        //期限切れ期間
//        sysUserOnlineEntity.setExpireTime(60);
//        //作成日時
//        sysUserOnlineEntity.setCretDatime(DateUtils.getSysTimestamp());
//        //更新日時
//        sysUserOnlineEntity.setUpdateTime(DateUtils.getSysTimestamp());
//        try{
//            sysUserOnlineService.save(sysUserOnlineEntity);
//        }catch (Exception e){
//            return R.error(MessageUtils.getMessage("MSGCOMN0080"));
//        }

        R r = f40009Service.postEmail(id, usrId, userName, email, brandCd, uuid, request);
        return r;
    }
}
