/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.ExcelUtils;
import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F00007Dto;
import jp.learningpark.modules.manager.dto.F00007ExportDto;
import jp.learningpark.modules.manager.service.F00007Service;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>生徒集計画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/01/10 : hujunjie: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F00007")
@RestController
public class F00007Controller {
    /**
     * 組織マスタ
     */
    @Autowired
    MstOrgService mstOrgService;

    /**
     * 生徒集計画面
     */
    @Autowired
    F00007Service f00007Service;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 生徒集計画面初期表示
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f00007init(Integer limit, Integer page) {
        String sessionOrgId = (String)ShiroUtils.getSessionAttribute(GakkenConstant.ORG_ID);
        String userOrgId = ShiroUtils.getUserEntity().getOrgId();
        //組織ID
        String orgId = StringUtils.isEmpty(sessionOrgId) ? userOrgId : sessionOrgId;
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //組織マスタ
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id", orgId));
        //組織名
        String orgNm = mstOrgEntity.getOrgNm();
        //全部下層データ
        List<F00007Dto> lowerLevList = f00007Service.selectLowerLevOrg(brandCd, orgId);
        if (lowerLevList == null || lowerLevList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "組織マスタ")).put("orgId", orgId).put("orgNm", orgNm);
        }

        R info = this.selectUserCount(limit, page, orgId);
        info.put("orgId", orgId);
        info.put("orgNm", orgNm);
        info.put("lowerLevList", lowerLevList);
        return info;
    }

    /**
     * 集計人数リスト取得
     *
     * @param currentOrgId 当組織ID
     * @return
     */
    @RequestMapping(value = "/selectUserCount/{currentOrgId}", method = RequestMethod.GET)
    public R selectUserCount(Integer limit, Integer page, @PathVariable String currentOrgId) {
        if ("null".equals(currentOrgId)) {
            currentOrgId = ShiroUtils.getUserEntity().getOrgId();
        }
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //組織マスタ
        MstOrgEntity mstOrgEntity = new MstOrgEntity();

        //当組織対応する全部下層データ
        List<F00007Dto> lowerLevListToUserCount = f00007Service.selectLowerLevOrg(brandCd, currentOrgId);
        //本組織＆下層組織リスト非空チェク
        if (lowerLevListToUserCount == null || lowerLevListToUserCount.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "組織"));
        }

        //集計人数リスト
        List<Map<String, String>> usrCountList = new ArrayList<>();
        List<F00007Dto> currentOrgIdlowerLevList = new ArrayList<>();
        //テンプマップ
        Map<String, String> tempMap = f00007Service.selectUsrCount(lowerLevListToUserCount);
        if (tempMap == null || tempMap.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒"));
        }
        for (F00007Dto dto : lowerLevListToUserCount) {
            mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().eq("org_id", dto.getOrgId()));
            currentOrgIdlowerLevList = f00007Service.selectLowerLevOrg(brandCd, dto.getOrgId());
            tempMap = f00007Service.selectUsrCount(currentOrgIdlowerLevList);
            if (tempMap == null || tempMap.size() == 0) {
                tempMap = new HashMap<String, String>();
                tempMap.put("usrcount", "0");
                tempMap.put("incount", "0");
                tempMap.put("outcount", "0");
                tempMap.put("exitcount", "0");
            }
            tempMap.put("orgId", dto.getOrgId());
            tempMap.put("orgNm", mstOrgEntity.getOrgNm());
            usrCountList.add(tempMap);
        }

        List<Map<String, String>> currentPage;
        currentPage = page(usrCountList, limit, page);
        return R.ok().put("page", new PageUtils(currentPage, usrCountList.size(), limit, page));
    }

    /**
     * <p>エクスポート</p>
     *
     * @param currentOrgId 当組織ID
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public R exportExcel(String currentOrgId) {
        if (StringUtils.isEmpty(currentOrgId)) {
            currentOrgId = ShiroUtils.getUserEntity().getOrgId();
        }
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //出力ファイル名
        String fileNm = ShiroUtils.getUserEntity().getOrgId();
        //テンプレート取り込む
        File file;
        InputStream inputStream = this.getClass().getResourceAsStream("/templates/excel/生徒ステータス情報（エクスポート）.xlsx");
        FileOutputStream fileOutputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb;
        try {
            wb = new XSSFWorkbook(inputStream);
            //当組織対応する全部下層データ
            List<F00007Dto> lowerLevListToUserCount = f00007Service.selectLowerLevOrg(brandCd, currentOrgId);
            //本組織＆下層組織リスト非空チェク
            if (lowerLevListToUserCount == null || lowerLevListToUserCount.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "組織"));
            }

            //エクスポートデータリスト取得
            List<F00007ExportDto> exportUserList = f00007Service.exportFile(lowerLevListToUserCount);
            if (exportUserList == null || exportUserList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒"));
            }

            String field = "orgId,orgNm,afterUsrId,usrNm,usrSts,crmschSts";
            String[] fieldArray = field.split(",");
            fileNm += "_F00007_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
            wb = ExcelUtils.createExcelWb(wb, 1, fieldArray, exportUserList);
            file = FileUtils.getStorageFile(MessageUtils.getMessage("path.excel"), fileNm);
            if (!file.exists()) {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            inputStream.close();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok().put("fileNm", fileNm);
    }

    /**
     * 教科書デフォルトターム情報を元に、教科書デフォルトターム情報を取得し、エクスポートファイルに出力する。
     *
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void f00007download(HttpServletResponse response, @RequestParam String fileNm) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String donloadFileName = orgId + "_生徒在塾ステータス情報_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
        //出力ファイル名
        String outPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.excel") , fileNm);
        ExcelUtils.excelDownload(response, outPath, donloadFileName);
    }

    /**
     * ページカットバイリスト
     *
     * @param dataList    全部データリスト
     * @param pageSize    ページサイズ
     * @param currentPage 当ページ
     */
    public static List<Map<String, String>> page(List<Map<String, String>> dataList, int pageSize, int currentPage) {
        List<Map<String, String>> currentPageList = new ArrayList<>();
        if (dataList != null && dataList.size() > 0) {
            int currIdx = (currentPage > 1 ? (currentPage - 1) * pageSize : 0);
            for (int i = 0; i < pageSize && i < dataList.size() - currIdx; i++) {
                Map<String, String> data = dataList.get(currIdx + i);
                currentPageList.add(data);
            }
        }
        return currentPageList;
    }
}
