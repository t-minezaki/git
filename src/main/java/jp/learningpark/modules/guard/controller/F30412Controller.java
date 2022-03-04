package jp.learningpark.modules.guard.controller;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.LateAbsHstEntity;
import jp.learningpark.modules.common.service.LateAbsHstService;
import jp.learningpark.modules.guard.service.F30411Service;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * <p>
 * F30412 遅刻・欠席連絡登録
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/15 ： NWT)hxl ： 新規作成
 * @date 2019/11/15 9:51
 */
@RestController
@RequestMapping("/guard/F30412")
public class F30412Controller {
    /**
     * lateAbsHstService
     */
    @Autowired
    LateAbsHstService lateAbsHstService;
    /**
     * lateAbsHstService
     */
    @Autowired
    F30411Service f30411Service;

    /**
     *
     * @param stuId     生徒ID
     * @param type      遅欠連絡ステータス
     * @param lateTime  遅刻時間(分)
     * @param lateDay   対象年月日
     * @param reason    遅欠理由
     * @param remark    備考
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public R submit(String stuId, String type, Integer lateTime
            , String lateDay, String reason, String remark){
        SysUserEntity userEntity = ShiroUtils.getUserEntity();
        String orgId = userEntity.getOrgId();
        String usrId = userEntity.getUsrId();
        Timestamp date = DateUtils.toTimestamp(DateUtils.parse(lateDay, Constant.DATE_FORMAT_YYYY_MM_DD_BARS));
        Timestamp cretDate = DateUtils.getSysTimestamp();
        LateAbsHstEntity lateAbsHstEntity = new LateAbsHstEntity();
        lateAbsHstEntity.setOrgId(orgId);
        lateAbsHstEntity.setGuardId(usrId);
        lateAbsHstEntity.setStuId(stuId);
        lateAbsHstEntity.setAbsSts(type);
        lateAbsHstEntity.setLateTm(lateTime);
        lateAbsHstEntity.setTgtYmd(date);
        lateAbsHstEntity.setCorrspdSts("0");
        lateAbsHstEntity.setAbsReason(reason);
        lateAbsHstEntity.setBikou(remark);
        lateAbsHstEntity.setCretDatime(cretDate);
        lateAbsHstEntity.setCretUsrId(usrId);
        lateAbsHstEntity.setUpdDatime(cretDate);
        lateAbsHstEntity.setUpdUsrId(usrId);
        lateAbsHstEntity.setDelFlg(0);
        if (lateAbsHstService.save(lateAbsHstEntity)){
            return R.ok();
        }else{
            return R.error();
        }
    }
}
