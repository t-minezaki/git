package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.CM0005;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.AttendBookDEntity;
import jp.learningpark.modules.common.entity.AttendBookGetPointHstEntity;
import jp.learningpark.modules.common.entity.AttendBookHEntity;
import jp.learningpark.modules.common.entity.GuidReprDEntity;
import jp.learningpark.modules.common.entity.GuidReprHEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstMaxIdEntity;
import jp.learningpark.modules.common.entity.MstVariousSetEntity;
import jp.learningpark.modules.common.entity.UserDisplayItemSetEntity;
import jp.learningpark.modules.common.service.AttendBookDService;
import jp.learningpark.modules.common.service.AttendBookGetPointHstService;
import jp.learningpark.modules.common.service.AttendBookHService;
import jp.learningpark.modules.common.service.GuidReprDService;
import jp.learningpark.modules.common.service.GuidReprHService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstMaxIdService;
import jp.learningpark.modules.common.service.MstVariousSetService;
import jp.learningpark.modules.common.service.UserDisplayItemSetService;
import jp.learningpark.modules.manager.dto.F21008Dto;
import jp.learningpark.modules.manager.service.F21008Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/manager/F21008")
@RestController
public class F21008Controller {

    /**
     * add at 2021/08/17 for V9.02 by NWT wen
     */
    private static final Logger logger = LoggerFactory.getLogger(F21008Controller.class);
    /**
     * コードマスタ_明細 Service
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * 出席簿出欠情報入力画面 Service
     */
    @Autowired
    F21008Service f21008service;
    /**
     * MAX採番 Service
     */
    @Autowired
    MstMaxIdService maxIdService;
    /**
     * 出席簿ヘーダ Service
     */
    @Autowired
    AttendBookHService attendBookHService;
    /**
     * 出席簿明細 Service
     */
    @Autowired
    AttendBookDService attendBookDService;
    /**
     * ユーザ表示項目設定 Service
     */
    @Autowired
    UserDisplayItemSetService userDisplayItemSetService;
    /**
     * 指導報告書明細 Service
     */
    @Autowired
    GuidReprDService guidReprDService;
    /**
     * 指導報告書ヘーダ Service
     */
    @Autowired
    GuidReprHService guidReprHService;

    /**
     * 入退室各種設定マスタ Service
     */
    @Autowired
    MstVariousSetService variousSetService;

    /**
     * add at 2021/08/25 for V9.02 by NWT wen
     * 出席簿付与ポイント履歴 Service
     */
    @Autowired
    AttendBookGetPointHstService pointHstService;

    /**
     * 初期表示
     *
     * @param tgtYmd
     * @param grpId
     * @param absDiv
     * @param attendBookCd
     * @param limit
     * @param page
     * @return
     */

    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init(String tgtYmd, Integer grpId, String absDiv, String attendBookCd, Integer attendBookId, Integer limit, Integer page, Integer pageDiv) {
        //日付の取得
        Date dateStartTime = DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        Date dateEndTime = DateUtils.addDays(dateStartTime, 1);
        //グループマスタ、生徒基本マスタと遅刻欠席連絡履歴から生徒基本情報を取得し
        List<F21008Dto> f21008DtoList = new ArrayList<>();
        //教科
        Map<String, String> subject = new HashMap<>();
        //遅欠ステータス
        Map<String, String> absSts = new HashMap<>();
        //宿題
        Map<String, String> homeWk = new HashMap<>();
        //ケア
        Map<String, String> care = new HashMap<>();
        //遅欠連絡ステータス
        Map<String, String> attendSts = new HashMap<>();
        //理由（連絡情報）
        Map<String, String> reason = new HashMap<>();
        // add at 2021/08/11 for V9.02 by NWT wen START
        //小テスト合否
        Map<String, String> test = new HashMap<>();
        // add at 2021/08/11 for V9.02 by NWT wen END
        List<MstCodDEntity> mstCodDEntityList = new ArrayList<>();
        //教科
        mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "SUBJT_DIV").eq("del_flg", 0));
        getMap(mstCodDEntityList, subject);
        //遅欠ステータス
        mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "ABS_STS_DIV").eq("del_flg", 0));
        getMap(mstCodDEntityList, absSts);
        //宿題
        mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "HOME_WK_DIV").eq("del_flg", 0));
        getMap(mstCodDEntityList, homeWk);
        //ケア
        mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "CARE_DIV").eq("del_flg", 0));
        getMap(mstCodDEntityList, care);
        // add at 2021/08/11 for V9.02 by NWT wen START
        //小テスト合否
        mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "TEST_PASS_KBN").eq("del_flg", 0));
        getMap(mstCodDEntityList, test);
        // add at 2021/08/11 for V9.02 by NWT wen END
        //ケア
        mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "ABS_REASON").eq("del_flg", 0));
        getMap(mstCodDEntityList, reason);
        //遅欠連絡ステータス
        mstCodDEntityList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().eq("cod_key", "ABS_DIV").in("cod_cd", "1", "2").eq("del_flg", 0).orderByDesc("cod_cd"));
        getMap(mstCodDEntityList, attendSts);
        //画面の検索条件出欠フラグリストデータを設定するため、コードマスタ_明細データを取得する。
        mstCodDEntityList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_cd").eq("cod_key", "ABS_DIV").eq("del_flg", 0).orderByAsc("sort"));
        //一覧画面(F21007)で「新規作成」ボタン押下時
        if (pageDiv == 1) {
            //グループマスタ、生徒基本マスタと遅刻欠席連絡履歴から生徒基本情報を取得
            f21008DtoList = f21008service.getNewStuList(dateStartTime, dateEndTime, grpId, absDiv);
            for (F21008Dto f21008Dto : f21008DtoList) {
                if (f21008Dto.getConnectFlg() != null && (f21008Dto.getAbsStsDiv() == null || f21008Dto.getAbsStsDiv().isEmpty())) {
                    if (StringUtils.equals(f21008Dto.getConnectFlg(), "0")) {
                        f21008Dto.setAbsStsDiv(absSts.get(StringUtils.equals(f21008Dto.getAbsSts(), "2") ? "3" : "1"));
                        f21008Dto.setAbsStsCod(StringUtils.equals(f21008Dto.getAbsSts(), "2") ? "3" : "1");
                    } else {
                        f21008Dto.setAbsStsDiv(absSts.get(StringUtils.equals(f21008Dto.getAbsSts(), "1") ? "2" : "4"));
                        f21008Dto.setAbsStsCod(StringUtils.equals(f21008Dto.getAbsSts(), "1") ? "2" : "4");
                    }
                } else {
                    f21008Dto.setAbsStsCod(f21008Dto.getAbsStsDiv());
                    f21008Dto.setAbsStsDiv(absSts.get(f21008Dto.getAbsStsDiv()));
                }
            }
        }
        //一覧画面(F21007)で「編集」ボタン押下時、
        else {
            //グループマスタ、生徒基本マスタと遅刻欠席連絡履歴から生徒基本情報を取得
            f21008DtoList = f21008service.getEditStuList(dateStartTime, dateEndTime, attendBookId, absDiv);
            for (F21008Dto f21008Dto : f21008DtoList) {
                //生徒教科の取得
                f21008Dto.setSubjectCod(f21008Dto.getSubjtDiv());
                if (!StringUtils.equals("99", f21008Dto.getSubjtDiv())) {
                    f21008Dto.setSubjtDiv(subject.get(f21008Dto.getSubjtDiv()));
                } else {
                    f21008Dto.setSubjtDiv(f21008Dto.getYobi1());
                }
                // 生徒遅欠ステータスの取得
                f21008Dto.setAbsStsCod(f21008Dto.getAbsStsDiv());
                f21008Dto.setAbsStsDiv(absSts.get(f21008Dto.getAbsStsDiv()));
                // 生徒宿題の取得
                f21008Dto.setHomeWkCod(f21008Dto.getHomeWkDiv());
                f21008Dto.setHomeWkDiv(homeWk.get(f21008Dto.getHomeWkDiv()));
                //生徒ケアの取得
                f21008Dto.setCareCod(f21008Dto.getCareDiv());
                f21008Dto.setCareDiv(care.get(f21008Dto.getCareDiv()));
                // add at 2021/08/11 for V9.02 by NWT wen START
                //生徒小テスト合否の取得
                f21008Dto.setTestCod(f21008Dto.getTestPassKbn());
                f21008Dto.setTestPassKbn(test.get(f21008Dto.getTestPassKbn()));
                // add at 2021/08/11 for V9.02 by NWT wen END
            }
        }
        //上記取得できない場合、
        if (f21008DtoList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒グループ")).put("mstCodDEntityList", mstCodDEntityList);
        }
        //遅欠連絡ステータス
        for (F21008Dto f21008Dto : f21008DtoList) {
            f21008Dto.setAbsSts(attendSts.get(f21008Dto.getAbsSts()));
            f21008Dto.setAbsReason(reason.get(f21008Dto.getAbsReason()));
        }
        return R.ok().put("mstCodDEntityList", mstCodDEntityList).put("page", new PageUtils(f21008DtoList, f21008DtoList.size(), limit, page));
    }

    @RequestMapping(value = "stuSelect", method = RequestMethod.GET)
    public R stuSelect(String tgtYmd, String stuIdListStr) {
        //遅欠ステータス
        Map<String, String> absSts = new HashMap<>();
        //理由（連絡情報）
        Map<String, String> reason = new HashMap<>();
        Map<String, String> attendSts = new HashMap<>();
        List<MstCodDEntity> mstCodDEntityList = new ArrayList<>();
        //遅欠ステータス
        mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "ABS_STS_DIV").eq("del_flg", 0));
        getMap(mstCodDEntityList, absSts);
        //ケア
        mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "ABS_REASON").eq("del_flg", 0));
        getMap(mstCodDEntityList, reason);
        //遅欠連絡ステータス
        mstCodDEntityList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().eq("cod_key", "ABS_DIV").in("cod_cd", "1", "2").eq("del_flg", 0).orderByDesc("cod_cd"));
        getMap(mstCodDEntityList, attendSts);
        List<String> stuIdList = JSON.parseArray(stuIdListStr, String.class);
        Date dateStartTime = DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        Date dateEndTime = DateUtils.addDays(dateStartTime, 1);
        List<F21008Dto> addStu = f21008service.addStu(dateStartTime, dateEndTime, stuIdList);
        //遅欠連絡ステータス
        for (F21008Dto f21008Dto : addStu) {
            if (f21008Dto.getConnectFlg() != null) {
                if (StringUtils.equals(f21008Dto.getConnectFlg(), "0")) {
                    f21008Dto.setAbsStsDiv(absSts.get(StringUtils.equals(f21008Dto.getAbsSts(), "2") ? "3" : "1"));
                    f21008Dto.setAbsStsCod(StringUtils.equals(f21008Dto.getAbsSts(), "2") ? "3" : "1");
                } else {
                    f21008Dto.setAbsStsDiv(absSts.get(StringUtils.equals(f21008Dto.getAbsSts(), "1") ? "2" : "4"));
                    f21008Dto.setAbsStsCod(StringUtils.equals(f21008Dto.getAbsSts(), "1") ? "2" : "4");
                }
            } else {
                f21008Dto.setAbsStsCod(f21008Dto.getAbsStsDiv());
                f21008Dto.setAbsStsDiv(absSts.get(f21008Dto.getAbsStsDiv()));
            }
            //生徒遅欠ステータスの取得
            f21008Dto.setAbsSts(attendSts.get(f21008Dto.getAbsStsCod()));
            f21008Dto.setAbsReason(reason.get(f21008Dto.getAbsReason()));
        }
        return R.ok().put("addStu", addStu);
    }

    @RequestMapping(value = "getshowItems", method = RequestMethod.GET)
    public R getshowItems() {
        //画面表示項目
        F21008Dto displayItems = f21008service.selectDspItems(ShiroUtils.getUserId(), "F21008");
        return R.ok().put("displayItems", displayItems);
    }

    /**
     * 前画面入力内容の取得
     *
     * @param selectData
     * @param codKey
     * @return
     */
    @RequestMapping(value = "getSelectedItems", method = RequestMethod.GET)
    public R getSelectedItems(String selectData, String codKey, String pageDiv) {
        MstCodDEntity mstCodDEntity = new MstCodDEntity();
        if (StringUtils.equals("8", pageDiv)) {
            List<String> codCdList = JSON.parseArray(selectData, String.class);
            String codCdStr = codCdList.get(0);
            for (int i = 1; i < codCdList.size(); i++) {
                codCdStr += "," + codCdList.get(i);
            }
            List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(
                    new QueryWrapper<MstCodDEntity>().eq("cod_key", codKey).in("cod_cd", codCdList).eq("del_flg", 0));
            String codValueStr = mstCodDEntityList.get(0).getCodValue2();
            for (int i = 1; i < codCdList.size(); i++) {
                codValueStr += "," + mstCodDEntityList.get(i).getCodValue2();
            }
            mstCodDEntity.setCodValue2(codValueStr);
            mstCodDEntity.setCodCd(codCdStr);
        } else {
            mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", codKey).eq("cod_cd", selectData).eq("del_flg", 0));
        }
        return R.ok().put("mstCodDEntity", mstCodDEntity);
    }

    /**
     * 対象者削除
     *
     * @param stuIdListStr
     * @param attendBookId
     * @param flg
     * @return
     */
    @RequestMapping(value = "stuDel", method = RequestMethod.POST)
    public R stuDel(String stuIdListStr, Integer attendBookId, boolean flg) {
        List<String> stuIdList = JSON.parseArray(stuIdListStr, String.class);
        AttendBookHEntity attendBookHEntity = attendBookHService.getById(attendBookId);
        //指導報告書ヘーダ
        GuidReprHEntity guidReprHEntity = guidReprHService.getOne(
                new QueryWrapper<GuidReprHEntity>().eq("ATTEND_BOOK_CD", attendBookHEntity.getAttendBookCd()).eq("org_id",
                        ShiroUtils.getUserEntity().getOrgId()).eq("del_flg", 0));
        //一覧部でオール生徒が選択された場合
        if (flg) {
            if (guidReprHEntity != null) {
                //指導報告書明細データを削除する
                guidReprDService.remove(new QueryWrapper<GuidReprDEntity>().eq("guid_repr_id", guidReprHEntity.getId()).eq("del_flg", 0));
                //指導報告書ヘーダデータを削除する
                //                guidReprHService.removeById(guidReprHEntity);
            }
            //出席簿明細データを削除する
            attendBookDService.remove(new QueryWrapper<AttendBookDEntity>().eq("attend_book_id", attendBookId).eq("del_flg", 0));
            //出席簿ヘーダデータを削除する
            //            attendBookHService.removeById(attendBookId);
        } else {
            for (String stuId : stuIdList) {
                AttendBookDEntity attendBookDEntity = attendBookDService.getOne(
                        new QueryWrapper<AttendBookDEntity>().eq("stu_id", stuId).eq("attend_book_id", attendBookId).eq("del_flg", 0));
                if (guidReprHEntity != null) {
                    GuidReprDEntity guidReprDEntity = guidReprDService.getOne(
                            new QueryWrapper<GuidReprDEntity>().eq("stu_id", stuId).eq("guid_repr_id", guidReprHEntity.getId()).eq("del_flg", 0));
                    //指導報告書明細データを削除する
                    guidReprDService.removeById(guidReprDEntity);
                }
                //出席簿明細データを削除する
                attendBookDService.removeById(attendBookDEntity);
            }
        }
        // add at 2021/08/11 for V9.02 by NWT wen START
        // 出席簿付与ポイント履歴を削除する
        pointHstService.remove(
                new QueryWrapper<AttendBookGetPointHstEntity>().eq("attend_book_id", attendBookId).eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq(
                        "del_flag", 0));
        for (String stuId : stuIdList) {
            CM0005.addPoint(stuId, ShiroUtils.getUserEntity().getOrgId(), null, 6, ShiroUtils.getUserId(), DateUtils.getSysTimestamp());
        }
        // add at 2021/08/11 for V9.02 by NWT wen END
        return R.ok();
    }

    /**
     * 「登録」ボタン押下
     * 2021/08/11 for V9.02 by NWT wen トランザクション制御の追加
     *
     * @param tgtYmd
     * @param grpId
     * @param timesNum
     * @param jqGridList
     * @param pageDiv
     * @return
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @Transactional
    public R submit(String tgtYmd, Integer grpId, Integer timesNum, Integer attendBookId, String jqGridList, Integer pageDiv, String addStuList) {
        String attendBookCd = null;
        List<F21008Dto> newStuList = new ArrayList<>();
        //生徒情報を取得し
        // modify at 2021/08/17 for V9.02 by NWT wen START
        List<F21008Dto> stuList = new ArrayList<F21008Dto>();
        List<String> addStu = new ArrayList<String>();
        try {
            stuList = JSON.parseArray(jqGridList, F21008Dto.class);
            addStu = JSON.parseArray(addStuList, String.class);
        } catch (JSONException e) {
            logger.error("すべての対象者：{}", jqGridList);
            logger.error("追加した対象者：{}", addStuList);
            throw new RRException("データ保存に失敗");
        }
        if (stuList.isEmpty()) {
            return R.error(500, "データがなく、保存に失敗する！");
        }
        // modify at 2021/08/17 for V9.02 by NWT wen END
        for (F21008Dto stu : stuList) {
            for (String addDto : addStu) {
                if (StringUtils.equals(stu.getStuId(), addDto)) {
                    newStuList.add(stu);
                }
            }
        }
        //日付の取得
        Date date = DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        //組織IDの取得
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        //MAXIDの取得
        MstMaxIdEntity mstMaxIdEntity = new MstMaxIdEntity();
        AttendBookHEntity attendBookHEntity = new AttendBookHEntity();
        mstMaxIdEntity = maxIdService.getOne(new QueryWrapper<MstMaxIdEntity>().eq("org_id", orgId).eq("type_div", "a").eq("del_flg", 0));
        Integer maxId = 0;
        if (mstMaxIdEntity == null) {
            //MAX採番登録
            mstMaxIdEntity = new MstMaxIdEntity();
            //組織ID
            mstMaxIdEntity.setOrgId(orgId);
            //MAXID
            mstMaxIdEntity.setMaxId(1);
            //種類区分
            mstMaxIdEntity.setTypeDiv("a");
            //作成日時
            mstMaxIdEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            mstMaxIdEntity.setUpdUsrId(userId);
            //更新日時
            mstMaxIdEntity.setCretDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstMaxIdEntity.setCretUsrId(userId);
            //削除フラグ
            mstMaxIdEntity.setDelFlg(0);
            maxIdService.save(mstMaxIdEntity);
            maxId = mstMaxIdEntity.getMaxId();
        } else {
            maxId = mstMaxIdEntity.getMaxId();
            //MAXID
            mstMaxIdEntity.setMaxId(maxId + 1);
            //更新日時
            mstMaxIdEntity.setCretDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstMaxIdEntity.setCretUsrId(userId);
            //削除フラグ
            mstMaxIdEntity.setDelFlg(0);
            maxIdService.updateById(mstMaxIdEntity);
        }
        //一覧画面(F21007)で「新規作成」ボタン押下時
        if (pageDiv == 1) {
            AttendBookDEntity attendBookDEntity = new AttendBookDEntity();
            //出席簿ヘーダを登録する
            //組織ID
            attendBookHEntity.setOrgId(orgId);
            //対象年月日
            attendBookHEntity.setTgtYmd(date);
            //出席簿コード
            attendBookHEntity.setAttendBookCd("a" + (maxId));
            //グループID
            attendBookHEntity.setGrpId(grpId);
            //回数
            attendBookHEntity.setTimesNum(1);
            //作成日時
            attendBookHEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            attendBookHEntity.setUpdUsrId(userId);
            //更新日時
            attendBookHEntity.setCretDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            attendBookHEntity.setCretUsrId(userId);
            //削除フラグ
            attendBookHEntity.setDelFlg(0);
            attendBookHService.save(attendBookHEntity);
            //登録後出席簿IDの取得
            attendBookId = attendBookHEntity.getId();
            //登録後出席簿コードの取得
            attendBookCd = attendBookHEntity.getAttendBookCd();
            //出席簿明細を登録する
            for (F21008Dto dto : stuList) {
                saveAttendBookDEntity(attendBookDEntity, attendBookHEntity.getId(), dto);
            }
            // add at 2021/08/11 for V9.02 by NWT wen START
            savePointHst(attendBookId, date, stuList, true);
            // add at 2021/08/11 for V9.02 by NWT wen END
        }
        //一覧画面(F21007)で「編集」ボタン押下時、
        else {
            //            出席簿明細データを更新する。
            //修正した出席簿情報を反映するため、出席簿明細を登録する。
            for (F21008Dto dto : stuList) {
                AttendBookDEntity attendBookDEntity = attendBookDService.getOne(
                        new QueryWrapper<AttendBookDEntity>().eq("attend_book_id", attendBookId).eq("stu_id", dto.getStuId()).eq("del_flg", 0));
                if (attendBookDEntity != null) {
                    //教科区分
                    attendBookDEntity.setSubjtDiv(dto.getSubjtDiv());
                    //出欠ステータス区分
                    attendBookDEntity.setAbsStsDiv(dto.getAbsStsDiv());
                    //宿題区分
                    attendBookDEntity.setHomeWkDiv(dto.getHomeWkDiv());
                    //小テスト点数
                    attendBookDEntity.setTestPoints(dto.getTestPoints());
                    //ケア区分
                    attendBookDEntity.setCareDiv(dto.getCareDiv());
                    // add at 2021/08/11 for V9.02 by NWT wen START
                    //小テスト合否区分
                    attendBookDEntity.setTestPassKbn(dto.getTestPassKbn());
                    // add at 2021/08/11 for V9.02 by NWT wen END
                    //予備１
                    attendBookDEntity.setYobi1(StringUtils.equals(dto.getSubjtDiv(), "99") ? dto.getSubjtName() : null);
                    //更新日時
                    attendBookDEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    attendBookDEntity.setUpdUsrId(userId);
                    //作成ユーザＩＤ
                    attendBookDEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //作成ユーザＩＤ
                    attendBookDEntity.setCretUsrId(userId);
                    //削除フラグ
                    attendBookDEntity.setDelFlg(0);
                    attendBookDService.update(attendBookDEntity, new QueryWrapper<AttendBookDEntity>().eq("id", attendBookDEntity.getId()).eq("del_flg", 0));
                } else {
                    attendBookDEntity = new AttendBookDEntity();
                    saveAttendBookDEntity(attendBookDEntity, attendBookId, dto);
                }
            }
            // add at 2021/08/11 for V9.02 by NWT wen START
            savePointHst(attendBookId, date, stuList, false);
            // add at 2021/08/11 for V9.02 by NWT wen END
            attendBookHEntity = attendBookHService.getById(attendBookId);
            GuidReprHEntity guidReprHEntity = guidReprHService.getOne(
                    new QueryWrapper<GuidReprHEntity>().eq("attend_book_cd", attendBookHEntity.getAttendBookCd()).eq("org_id", attendBookHEntity.getOrgId()).eq(
                            "del_flg", 0));
            if (guidReprHEntity != null) {
                for (F21008Dto stu : newStuList) {
                    GuidReprDEntity guidReprDEntity = new GuidReprDEntity();
                    //指導報告書ID
                    guidReprDEntity.setGuidReprId(guidReprHEntity.getId());
                    //生徒ID
                    guidReprDEntity.setStuId(stu.getStuId());
                    //出欠ステータス区分
                    guidReprDEntity.setAbsStsDiv(stu.getAbsStsDiv());
                    //使用テキスト
                    guidReprDEntity.setUseCont(null);
                    //指導内容
                    guidReprDEntity.setGuidCont(null);
                    //宿題内容
                    guidReprDEntity.setHwkCont(null);
                    //小テスト単元名
                    guidReprDEntity.setTestUnitNm(null);
                    //前回宿題完成区分
                    guidReprDEntity.setLastTimeHwkDiv(null);
                    //進捗ステータス区分
                    guidReprDEntity.setSchStsDiv(null);
                    //授業様子区分
                    guidReprDEntity.setLectShapeDiv(null);
                    //連絡事項内容
                    guidReprDEntity.setConcItemCont(null);
                    //作成ユーザＩＤ
                    guidReprDEntity.setCretUsrId(userId);
                    //作成日時
                    guidReprDEntity.setCretDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    guidReprDEntity.setUpdUsrId(userId);
                    //更新日時
                    guidReprDEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //削除フラグ
                    guidReprDEntity.setDelFlg(0);
                    guidReprDService.save(guidReprDEntity);
                }
            }
        }
        return R.ok().put("attendBookId", attendBookId).put("attendBookCd", attendBookCd);
    }

    /**
     * Map作成
     *
     * @param mstCodDEntityList
     * @param map
     * @return
     */
    public Map<String, String> getMap(List<MstCodDEntity> mstCodDEntityList, Map<String, String> map) {
        for (MstCodDEntity mstCodDEntity : mstCodDEntityList) {
            map.put(mstCodDEntity.getCodCd(), mstCodDEntity.getCodValue());
        }
        return map;
    }

    /**
     * POP画面の「確認」押下
     */
    @RequestMapping(value = "saveDspItmes", method = RequestMethod.GET)
    public R saveDspItmes(String dspitems, String menuId) {
        // 項目文字列を表示する設定
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        UserDisplayItemSetEntity userDisplayItemSetEntity = new UserDisplayItemSetEntity();
        userDisplayItemSetEntity = userDisplayItemSetService.getOne(
                new QueryWrapper<UserDisplayItemSetEntity>().eq("user_id", ShiroUtils.getUserId()).eq("menu_id", menuId).eq("del_flg", 0));
        //ユーザ表示項目設定を削除する
        if (userDisplayItemSetEntity != null) {
            userDisplayItemSetService.removeById(userDisplayItemSetEntity);
        }
        //ID
        userDisplayItemSetEntity = new UserDisplayItemSetEntity();
        //ユーザID
        userDisplayItemSetEntity.setUserId(ShiroUtils.getUserId());
        //画面ID
        userDisplayItemSetEntity.setMenuId(menuId);
        //表示項目
        userDisplayItemSetEntity.setDisplayItems(StringUtils.join(dspitemsList.toArray(), ','));
        //作成日時
        userDisplayItemSetEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        userDisplayItemSetEntity.setCretUsrId(ShiroUtils.getUserId());
        //更新日時
        userDisplayItemSetEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        userDisplayItemSetEntity.setUpdUsrId(ShiroUtils.getUserId());
        //削除フラグ
        userDisplayItemSetEntity.setDelFlg(0);
        userDisplayItemSetService.save(userDisplayItemSetEntity);
        return R.ok();
    }

    public boolean saveAttendBookDEntity(AttendBookDEntity attendBookDEntity, Integer attendBookId, F21008Dto dto) {
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        //出席簿コード
        attendBookDEntity.setAttendBookId(attendBookId);
        //生徒ID
        attendBookDEntity.setStuId(dto.getStuId());
        //教科区分
        attendBookDEntity.setSubjtDiv(dto.getSubjtDiv());
        //出欠ステータス区分
        attendBookDEntity.setAbsStsDiv(dto.getAbsStsDiv());
        //宿題区分
        attendBookDEntity.setHomeWkDiv(dto.getHomeWkDiv());
        //小テスト点数
        attendBookDEntity.setTestPoints(dto.getTestPoints());
        //ケア区分
        attendBookDEntity.setCareDiv(dto.getCareDiv());
        // add at 2021/08/11 for V9.02 by NWT wen START
        //小テスト合否区分
        attendBookDEntity.setTestPassKbn(dto.getTestPassKbn());
        // add at 2021/08/11 for V9.02 by NWT wen END
        //予備１
        attendBookDEntity.setYobi1(StringUtils.equals(dto.getSubjtDiv(), "99") ? dto.getSubjtName() : null);
        //更新日時
        attendBookDEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        attendBookDEntity.setUpdUsrId(userId);
        //作成ユーザＩＤ
        attendBookDEntity.setCretDatime(DateUtils.getSysTimestamp());
        //作成ユーザＩＤ
        attendBookDEntity.setCretUsrId(userId);
        //削除フラグ
        attendBookDEntity.setDelFlg(0);
        return attendBookDService.save(attendBookDEntity);
    }

    /**
     * <p>出席簿付与ポイント履歴</p>
     * データ作成と修正
     * add at 2021/08/11 for V9.02 by NWT wen
     *
     * @param attendBookId 出席簿ID
     * @param date 画面指定した対象年月日
     * @param stuList 生徒リスト
     * @param flag 新規修正フラグ
     * @return
     */
    public R savePointHst(Integer attendBookId, Date date, List<F21008Dto> stuList, boolean flag) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String userId = ShiroUtils.getUserId();
        Map<String, Object> map = new HashMap<>();
        MstVariousSetEntity variousSetEntity = variousSetService.getOne(
                new QueryWrapper<MstVariousSetEntity>().select("padd_point", "full_marks_point", "work_out_point", "attent_out_point").eq("org_id", orgId).eq(
                        "del_flg", 0));
        if (variousSetEntity == null) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0192", "入退室各種設定マスタの自動付与ポイント", "出席簿付与ポイント履歴作成"));
        }
        map.put("attendBookId", attendBookId);
        map.put("orgId", orgId);
        map.put("userId", userId);
        List<AttendBookGetPointHstEntity> entities = new ArrayList<AttendBookGetPointHstEntity>();
        List<String> stuId = new ArrayList<String>();
        for (F21008Dto dto : stuList) {
            stuId.add(dto.getStuId());
            AttendBookGetPointHstEntity entity = new AttendBookGetPointHstEntity();
            // 生徒ID
            entity.setStuId(dto.getStuId());
            // 画面．小テスト合否が「0：合格」の場合、
            if (StringUtils.equals("0", dto.getTestPassKbn())) {
                entity.setPassScorePoint(variousSetEntity.getPaddPoint());
            } else { // 上記以外の場合、
                entity.setPassScorePoint(0);
            }

            // 画面．小テスト合否が「1：満点」の場合、
            if (StringUtils.equals("1", dto.getTestPassKbn())) {
                entity.setFullScorePoint(variousSetEntity.getFullMarksPoint());
            } else { // 上記以外の場合、
                entity.setFullScorePoint(0);
            }

            // 画面．宿題が提出「0：〇」の場合、
            if (StringUtils.equals("0", dto.getHomeWkDiv())) {
                entity.setHworkOutPoint(variousSetEntity.getWorkOutPoint());
            } else { // 上記以外の場合、
                entity.setHworkOutPoint(0);
            }

            // 画面．出欠が「0：出席　又は　3：遅刻（連絡有）　又は　4：遅刻（連絡無）」の場合、
            if (StringUtils.equals("0", dto.getAbsStsDiv()) || StringUtils.equals("3", dto.getAbsStsDiv()) || StringUtils.equals("4", dto.getAbsStsDiv())) {
                entity.setAbsLoginPoint(variousSetEntity.getAttentOutPoint());
            } else { // 上記以外の場合、
                entity.setAbsLoginPoint(0);
            }
            entities.add(entity);
        }
        map.put("stuList", entities);
        try {
            if (date == null) {
                throw new RRException("データ保存に失敗!");
            }
            map.put("tgtYmd", date);
            if (flag) {
                f21008service.insertPointHst(map);
            } else {
                pointHstService.remove(
                        new QueryWrapper<AttendBookGetPointHstEntity>().eq("org_id", orgId).eq("attend_book_id", attendBookId).in("stu_id", stuId).eq(
                                "del_flag", 0));
                f21008service.insertPointHst(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("データ保存に失敗!");
        }
        for (F21008Dto dto : stuList) {
            CM0005.addPoint(dto.getStuId(), orgId, null, 6, userId, DateUtils.getSysTimestamp());
        }
        return R.ok();
    }
}
