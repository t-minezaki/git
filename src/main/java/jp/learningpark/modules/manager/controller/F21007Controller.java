package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.AttendBookDEntity;
import jp.learningpark.modules.common.entity.AttendBookHEntity;
import jp.learningpark.modules.common.entity.MstMaxIdEntity;
import jp.learningpark.modules.common.service.AttendBookDService;
import jp.learningpark.modules.common.service.AttendBookHService;
import jp.learningpark.modules.common.service.MstGrpService;
import jp.learningpark.modules.common.service.MstMaxIdService;
import jp.learningpark.modules.manager.dto.F21007Dto;
import jp.learningpark.modules.manager.service.F21007Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>F21007_出席簿一覧画面 Controller</p >
 *
 * @author NWT : LiYuHuan <br />
 * @author NWT文
 * @version 9.0要件変更 2020/11/11
 */
@RequestMapping("/manager/F21007")
@RestController
public class F21007Controller {
    /**
     * 出席簿ヘーダ Service
     */
    @Autowired
    AttendBookHService attendBookHeadService;
    /**
     * グループマスタ Service
     */
    @Autowired
    MstGrpService mstGrpService;
    /**
     * F21007Service Service
     */
    @Autowired
    F21007Service f21007Service;
    /**
     * MAX採番 Service
     */
    @Autowired
    MstMaxIdService mstMaxIdService;
    /**
     * 出席簿明細 Service
     */
    @Autowired
    AttendBookDService attendBookDetailService;

    /**
     * @param limit
     * @param page
     * @param tgtYmd 対象日
     * @param grpNm 画面入力したグループ名
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String tgtYmd, String grpNm) {
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        Date date = DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        Integer[] weekDays = {0, 1, 2, 3, 4, 5, 6, 7};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0) {
            w = 7;
        }
        String dayweekDiv = StringUtils.defaultString(weekDays[w]);
        List<Integer> grpIdList = new ArrayList<>();
        // クエリーデータセットを取得する
        List<F21007Dto> f21007DtoList = f21007Service.selectAll(orgId, date, grpNm);
        for (F21007Dto dto : f21007DtoList) {
            grpIdList.add(dto.getGrpId());
        }
        List<F21007Dto> f21007DtoList1 = f21007Service.selectMstGrpNm(orgId, grpIdList, dayweekDiv, grpNm);
        f21007DtoList.addAll(f21007DtoList1);
        if (f21007DtoList.size() == 0) {
            // アイテムが取得できない場合はメッセージ(MSGCOMN0017)に戻る。
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "出席簿"));
        }
        // プロジェクトを取得するとページに戻る
        return R.ok().put("page", new PageUtils(f21007DtoList, f21007DtoList.size(), limit, page));
    }

    /**
     * 「追加作成」ボタン押下
     *
     * @param orgId 組織ID
     * @param id 　出席簿明細id
     * @param grpId グループID
     * @param tgtYmd 　対象日
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public R add(String orgId, Integer id, Integer grpId, String tgtYmd) {
        Date date = DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        AttendBookHEntity attendBookHeadEntity = attendBookHeadService.getOne(
                new QueryWrapper<AttendBookHEntity>().eq("org_id", orgId).eq("grp_id", grpId).eq("tgt_ymd", date));
        MstMaxIdEntity maxIdEntity = mstMaxIdService.getOne(new QueryWrapper<MstMaxIdEntity>().eq("org_id", orgId).eq("type_div", "a").eq("del_flg", 0));
        Integer maxId = 0;
        if (maxIdEntity != null) {
            maxId = maxIdEntity.getMaxId();
            //MAXID
            maxIdEntity.setMaxId(maxIdEntity.getMaxId() + 1);
            //更新日時
            maxIdEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            maxIdEntity.setUpdUsrId(ShiroUtils.getUserId());
            //ID
            attendBookHeadEntity.setId(null);
            //組織ID
            attendBookHeadEntity.setOrgId(orgId);
            //対象年月日
            attendBookHeadEntity.setTgtYmd(date);
            //出席簿コード
            attendBookHeadEntity.setAttendBookCd(maxIdEntity.getTypeDiv() + maxId);
            //グループID
            attendBookHeadEntity.setGrpId(grpId);
            //回数
            attendBookHeadEntity.setTimesNum(f21007Service.selectNumMax(orgId, grpId, date) + 1);
            //作成日時
            attendBookHeadEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            attendBookHeadEntity.setCretUsrId(orgId);
            //更新日時
            attendBookHeadEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            attendBookHeadEntity.setUpdUsrId(orgId);
            //削除フラグ
            attendBookHeadEntity.setDelFlg(0);
        } else {
            maxIdEntity = new MstMaxIdEntity();

            //組織ID
            maxIdEntity.setOrgId(orgId);
            //MAXID
            maxIdEntity.setMaxId(1);
            //種類区分
            maxIdEntity.setTypeDiv("a");
            //作成日時
            maxIdEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            maxIdEntity.setCretUsrId(ShiroUtils.getUserId());
            //更新日時
            maxIdEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            maxIdEntity.setUpdUsrId(ShiroUtils.getUserId());
            //削除フラグ
            maxIdEntity.setDelFlg(0);

            mstMaxIdService.save(maxIdEntity);
            //ID
            attendBookHeadEntity.setId(null);
            //組織ID
            attendBookHeadEntity.setOrgId(orgId);
            //対象年月日
            attendBookHeadEntity.setTgtYmd(date);
            //出席簿コード
            attendBookHeadEntity.setAttendBookCd(maxIdEntity.getTypeDiv() + maxIdEntity.getMaxId());
            //グループID
            attendBookHeadEntity.setGrpId(grpId);
            //回数
            attendBookHeadEntity.setTimesNum(f21007Service.selectNumMax(orgId, grpId, date) + 1);
            //作成日時
            attendBookHeadEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            attendBookHeadEntity.setCretUsrId(orgId);
            //更新日時
            attendBookHeadEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            attendBookHeadEntity.setUpdUsrId(orgId);
            //削除フラグ
            attendBookHeadEntity.setDelFlg(0);
        }
        attendBookHeadService.save(attendBookHeadEntity);
        Integer newId = attendBookHeadEntity.getId();
        List<AttendBookDEntity> attendBookDetailEntity = attendBookDetailService.list(
                new QueryWrapper<AttendBookDEntity>().select("stu_id", "subjt_div", "abs_sts_div", "home_wk_div", "test_points", "care_div").eq(
                        "attend_book_id", id).eq("del_flg", 0));
        for (AttendBookDEntity attendD : attendBookDetailEntity) {
            //出席簿コード
            attendD.setAttendBookId(newId);
            //生徒ID
            attendD.setStuId(attendD.getStuId());
            //教科区分
            attendD.setSubjtDiv("");
            //出欠ステータス区分
            attendD.setAbsStsDiv("");
            //宿題区分
            attendD.setHomeWkDiv("");
            //小テスト点数
            attendD.setTestPoints(null);
            //ケア
            attendD.setCareDiv("");
            //作成日時
            attendD.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            attendD.setCretUsrId(orgId);
            //更新日時
            attendD.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            attendD.setUpdUsrId(orgId);
            //更新ユーザＩＤ
            attendD.setDelFlg(0);
            //出席簿明細の登録処理
            attendBookDetailService.save(attendD);
        }

        mstMaxIdService.update(maxIdEntity, new QueryWrapper<MstMaxIdEntity>().eq("org_id", orgId).eq("type_div", "a").eq("del_flg", 0));
        return R.ok();
    }
}
