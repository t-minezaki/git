package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.manager.service.F08023Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)ckl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/5 ： NWT)ckl ： 新規作成
 * @date 2020/11/5 13:41
 */
@RestController
@RequestMapping(value = "/manager/F08023")
public class F08023Controller {
    /**
     * f08020Service
     */
    @Autowired
    F08023Service f08023Service;

    /**
     * @param div               閲覧状況区分
     * @param guidReprDeliverCd 指導報告書配信コード
     * @return R
     */
    @RequestMapping("/init")
    public R init(String div, String guidReprDeliverCd) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        return f08023Service.init(orgId, div, guidReprDeliverCd);
    }

    /**
     * @param stuIdListStr      生徒IDリスト
     * @param guidReprDeliverCd 指導報告書配信コード
     * @param div               閲覧状況区分
     * @param response          組織ID
     * @return null
     */
    @RequestMapping(value = "/getDownloadInfo", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getDownloadInfo(String stuIdListStr, String guidReprDeliverCd, String div, HttpServletResponse response) {

        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        // 学生一覧
        List<String> stuIdList = JSON.parseArray(stuIdListStr, String.class);
        // ファイル名
        String fileName = "対象者（学習連絡）情報リスト.csv";
        File downloadFile = f08023Service.getDownloadFile(orgId, guidReprDeliverCd, div, stuIdList);
        returnFile(fileName, downloadFile, response);
        return null;
    }

    /**
     * @param filename ファイル名
     * @param file     ファイル
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
            e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            //2020/11/18 9.0 cuikailin add start
            if (file.exists()) {
                file.deleteOnExit();
            }
            //2020/11/18 9.0 cuikailin add end
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
