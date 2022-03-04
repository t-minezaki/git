/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.dao.MstStuDao;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.service.F08010Service;
import jp.learningpark.modules.pop.dto.F04004Dto;
import jp.learningpark.modules.pop.service.F04004Service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>配信先設定画面 Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/06/18: yang: 新規<br />
 * @version 1.0
 */
@RequestMapping("/pop/f04004")
@RestController
public class F04004Controller {

    /**
     * 組織マスタService
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * 共通 Service
     */
    @Autowired
    private CommonService commonService;

    /**
     * MstCodDService
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * MstCodDService
     */
    @Autowired
    private MstGrpService mstGrpService;

    /**
     * F04004Service
     */
    @Autowired
    private F04004Service f04004Service;
    /**
     * GuardEventApplyStsService
     */
    @Autowired
    private GuardEventApplyStsService guardEventApplyStsService;
    /**
     * ユーザ基本マスタ
     */
    @Autowired
    private MstUsrService mstUsrService;

    /**
     * <p>初期表示</p>
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f04004Init(Integer eventId, String stuIdList) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List<F04004Dto> stuEntity = new ArrayList<>();
        if (stuIdList != null) {
            List<String> list = Arrays.asList(stuIdList.split(","));
            if (list.size() != 0) {
                stuEntity = f04004Service.getEntity(null, null, null, null, null, null, null, list);
            }
        }
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        //本組織及び下層組織リストの取得
        List<OrgAndLowerOrgIdDto> orgIdList = commonService.getThisAndLowerOrgId(brandCd, orgId);
        //学年の取得
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().and(w -> w.eq("cod_key", "SCHY_DIV").eq("del_flg", 0)).orderByAsc("sort"));
        //取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する
        if (mstCodDEntityList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "学年"));
        }

        return R.ok().put("org", org).put("orgIdList", orgIdList).put("schyList", mstCodDEntityList).put("stuEntity", stuEntity);
    }

    /**
     * <p>検索</p>
     *
     * @param group
     * @param orgIdList
     * @param schy
     * @return
     */
    // 2020/11/04 modify LiYuHuan start
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public R f04004search(@RequestParam("schy") String schy, @RequestParam("group") List<Integer> group, @RequestParam("rowIds") List<String> rowIds, @RequestParam("orgIdList") List<String> orgIdList, @RequestParam("stuId") String stuId, @RequestParam("stuNm") String stuNm, MultipartFile file, String orgFlag, String eventId) {
        //画面．検索条件．組織IDが一件でも選択していない場合、エラーとなり、処理を中断し、エラー内容を画面の上部に表示する
        if (orgFlag.equals("1")) {
            if (orgIdList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0028", "組織"));
            }
        } else {
            orgIdList.clear();
            // 2020/12/2 huangxinliang modify start
            orgIdList.add(ShiroUtils.getUserEntity().getOrgId());
            // 2020/12/2 huangxinliang modify end
        }
        //2020/11/04 modify LiPeipei
        List<F04004Dto> stuEntity = null;
        if (file != null) {
            String fileName = file.getOriginalFilename();
            boolean isExcel2003 = true;
            if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                isExcel2003 = false;
            }
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Workbook wb = null;
            if (isExcel2003) {
                try {
                    wb = new HSSFWorkbook(inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
                }

            } else {
                try {
                    wb = new XSSFWorkbook(inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
                }
            }
            Sheet sheet = wb.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            List<String> stuIds = new ArrayList<>();
            for (int i = 2; i < rows + 1; i++) {
                Row row = sheet.getRow(i);
                Cell cells = row.getCell(0);
                try {
                    stuIds.add(cells.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
                }
            }
            Integer count = 0;
            List<String> unexistList = new ArrayList<>();
            for (String singleStuId : stuIds) {
                MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("after_usr_id", singleStuId));
//                MstStuEntity mstStuEntity = mstStuDao.selectOne(new QueryWrapper<MstStuEntity>().eq("stu_id",singleStuId));
                if (mstUsrEntity == null) {
                    count++;
                    unexistList.add(singleStuId);
                }
            }
            if (count != 0) {
                List<String> showList = unexistList.subList(0, count > 5 ? 5 : count);
                return R.error(MessageUtils.getMessage("MSGCOMN0172", count.toString(), org.apache.commons.lang3.StringUtils.join(showList, "、")));
            }
            stuEntity = f04004Service.getEntity(schy, group, orgIdList, stuId, stuNm, stuIds, rowIds, null);
        } else {
            stuEntity = f04004Service.getEntity(schy, group, orgIdList, stuId, stuNm, null, rowIds, null);
        }
        //取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する
        if (stuEntity.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "対象ユーザー"));
        }
        if (eventId != null && !eventId.equals("undefined")) {
            for (F04004Dto dto : stuEntity) {
                GuardEventApplyStsEntity guardEventApplyStsEntity = guardEventApplyStsService.getOne(new QueryWrapper<GuardEventApplyStsEntity>().eq("event_id",
                        Integer.parseInt(eventId)).eq("stu_id", dto.getStuId()).eq("guard_id", dto.getGuardId()).eq("del_flg", 0));
                if (guardEventApplyStsEntity != null && StringUtils.equals("1", guardEventApplyStsEntity.getReadingStsDiv())) {
                    dto.setReadFlg(false);
                } else {
                    dto.setReadFlg(true);
                }
            }
        }
        return R.ok().put("stuEntity", stuEntity);
    }

    /**
     * グループの取得
     *
     * @param selectedNode
     * @return
     */
    @RequestMapping(value = "/getOrgList", method = RequestMethod.POST)
    public R f04004search(String selectedNode, String orgFlag) {
        //グループの取得
        if (orgFlag.equals("0")) {
            selectedNode = ShiroUtils.getUserEntity().getOrgId();
        }

        List<MstGrpEntity> mstGrpEntityList = mstGrpService.list(new QueryWrapper<MstGrpEntity>().eq("org_id", selectedNode).eq("del_flg", 0));
        return R.ok().put("mstGrpEntityList", mstGrpEntityList);
    }

    /**
     * テンプレートファイルを出力ファイルに出力して保存する
     *
     * @param
     */
    @RequestMapping(value = "/getTemplate", method = RequestMethod.POST)
    public void getTemplate(HttpServletResponse response) {
        InputStream inputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        String fileNm = "";
        fileNm = "お知らせ対象(インポート).xlsx";
        inputStream = this.getClass().getResourceAsStream("/templates/excel/お知らせ対象(インポート).xlsx");
        try {
            wb = new XSSFWorkbook(inputStream);
            ExcelUtils.getTemplate(response, wb, fileNm);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
// 2020/11/04 modify LiYuHuan end