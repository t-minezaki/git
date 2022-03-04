/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.GuidReprDService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.manager.dto.F21013Dto;
import jp.learningpark.modules.manager.service.F21013Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2019/12/4 : lyh: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F21013")
@RestController
public class F21013Controller {

    /**
     * F21013Service
     */

    @Autowired
    F21013Service f21013Service;
    /**
     * コードマスタ_明細 Service
     */
    @Autowired
    MstCodDService mstCodDService;
    /**
     * 指導報告書明細 Service
     */
    @Autowired
    GuidReprDService guidReprDService;

    /**
     * logger
     */
    Logger logger = LoggerFactory.getLogger(F21013Controller.class);

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String month, String cd, String stuIdListStr, String stuIdStr, String year) {
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // ユーザーID
        String userId = ShiroUtils.getUserEntity().getUsrId();
        //ログインユーザＩＤ
        String roleDiv = StringUtils.trim(ShiroUtils.getUserEntity().getRoleDiv());
        //        String choose = "";
        String years = year;

        if (years == null) {
            Date now = new Date();
            years = DateUtils.format(now, GakkenConstant.DATE_FORMAT_YYYY);
            //            choose = years + "-" + month;
        }
        MstCodDEntity mstCodDList = mstCodDService.getOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value_2").eq("cod_key", "GUID_SIT_LIST").eq("cod_cd", cd).eq("del_flg", 0));
        String value = "";

        if (mstCodDList != null) {
            value = mstCodDList.getCodValue2();
        }

        List<String> stuIdList1 = new ArrayList<>();
        List<String> stuIdListLast1 = new ArrayList<>();
        if (!StringUtils.isEmpty(stuIdListStr)) {
            stuIdList1 = JSON.parseArray(stuIdListStr, String.class);
        }
        if (!StringUtils.isEmpty(stuIdStr)) {
            stuIdListLast1 = JSON.parseArray(stuIdStr, String.class);
        }

        Set<String> stuIdList = new HashSet<>(stuIdList1);
        Set<String> stuIdListLast = new HashSet<>(stuIdListLast1);
        if (cd != null && !StringUtils.equals(cd, "0")) {
            if (StringUtils.equals(cd, "10") || StringUtils.equals(cd, "1") || StringUtils.equals(cd, "3")) {
                Set<String> stu = new HashSet<>();
                if (stuIdListLast.size() != 0) {
                    stu = stuIdListLast;
                } else {
                    stu = stuIdList;
                }
                List<F21013Dto> f21013DtoList = f21013Service.selectTen(orgId, month, stu, cd, years);
                if (f21013DtoList.size() != 0) {
                    for (int i = 0; i < f21013DtoList.size(); i++) {
                        for (F21013Dto dto : f21013DtoList) {
                            if (dto.getCodValue() != null) {
                                List<String> codCdList = Arrays.asList(dto.getCodValue().split(","));
                                List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(
                                        new QueryWrapper<MstCodDEntity>().in("cod_cd", codCdList).eq("cod_key", mstCodDList.getCodValue2()).eq("del_flg", 0));
                                String codValue = "";
                                if (StringUtils.equals(cd, "10")) {
                                    for (int j = 0; j < mstCodDEntityList.size(); j++) {
                                        if (j == 0) {
                                            codValue += mstCodDEntityList.get(j).getCodValue2();
                                        } else {
                                            codValue += "," + mstCodDEntityList.get(j).getCodValue2();
                                        }
                                    }
                                } else {
                                    for (int j = 0; j < mstCodDEntityList.size(); j++) {
                                        if (j == 0) {
                                            codValue += mstCodDEntityList.get(j).getCodValue();
                                        } else {
                                            codValue += "," + mstCodDEntityList.get(j).getCodValue();
                                        }
                                    }
                                }

                                dto.setCodValue(codValue);
                            } else {
                                return R.error(MessageUtils.getMessage("MSGCOMN0017", "先生の指導状況"));
                            }
                        }
                        return R.ok().put("page", new PageUtils(f21013DtoList, f21013DtoList.size(), limit, page));
                    }
                } else {
                    return R.error(MessageUtils.getMessage("MSGCOMN0017", "先生の指導状況"));
                }

            } else {
                List<F21013Dto> f21013DtoList = f21013Service.reSelect(orgId, stuIdList, value, cd, month, stuIdListLast, roleDiv, years);
                if (f21013DtoList.size() == 0) {
                    return R.error(MessageUtils.getMessage("MSGCOMN0017", "先生の指導状況"));
                }
                return R.ok().put("page", new PageUtils(f21013DtoList, f21013DtoList.size(), limit, page));
            }

        } else {
            List<F21013Dto> f21013DtoList = f21013Service.select(orgId, userId, month, roleDiv, stuIdListLast, years);
            if (f21013DtoList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "先生の指導状況"));
            }
            return R.ok().put("page", new PageUtils(f21013DtoList, f21013DtoList.size(), limit, page));
        }
        return R.ok();
    }

    @RequestMapping(value = "/getMstCod", method = RequestMethod.GET)
    public R getMstCod() {
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_cd", "cod_value_2").eq("cod_key", "GUID_SIT_LIST").eq("del_flg", 0).orderByAsc(
                        "sort"));

        return R.ok().put("mstCodDEntityList", mstCodDEntityList);
    }

    /**
     * CSVファイルのダウンロード
     *
     * @param response 响应
     * @return R
     */
    @RequestMapping(value = "/getDownloadInfo", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getDownloadInfo(HttpServletResponse response, String rowDataStr, String d, String month) {

        try {
            List<JSONObject> rowDataList = JSON.parseArray(rowDataStr, JSONObject.class);

            boolean head = true;
            //ファイル名
            String fileName = "指導状況一覧.csv";
            // ファイル
            File tempFile = File.createTempFile("指導状況一覧" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS), ".csv");
            FileWriter fw = new FileWriter(tempFile);
            // csv形式
            fw.write(new String(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF}));
            // 書類の内容
            for (JSONObject rowData : rowDataList) {

                if (head) {

                    List<String> headerList = new ArrayList<>(rowData.keySet());
                    headerList.sort((a, b)->b.compareTo(a));
                    String header = StringUtils.join(headerList.toArray(), ',') + "\n";
                    head = false;

                    fw.write(header);
                }

                List<String> headerList = new ArrayList<>(rowData.keySet());
                headerList.sort((a, b)->b.compareTo(a));

                List<String> valueList = new ArrayList<>();

                for (String key : headerList) {

                    valueList.add(rowData.getString(key));
                }

                String content = StringUtils.join(valueList.toArray(), ',') + "\n";

                fw.write(content);
            }

            fw.flush();
            fw.close();

            fileName = "指導状況一覧" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".csv";
            returnFile(fileName, tempFile, response);
        } catch (IOException e) {

            logger.error(e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * @param filename ファイル名
     * @param file ファイル
     * @param response 响应
     */
    private void returnFile(String filename, File file, HttpServletResponse response) {

        // responseパラメータ
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            // headerを設定する
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }

        // ブラウザにファイルを返す
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }
}