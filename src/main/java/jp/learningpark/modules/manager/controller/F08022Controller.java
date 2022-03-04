package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.service.F08022Service;
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
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>F08022_未読・未回答者送信一覧画面（インフォメーション） Controller</p>
 *
 * @author NWT文
 * @version 9.0
 * 変更履歴:
 * 2020/11/3 ： NWT文 ： 新規作成
 */
@RestController
@RequestMapping(value = "/manager/F08022")
public class F08022Controller {
    /**
     * f08020Service
     */
    @Autowired
    F08022Service f08022Service;

    /**
     * @param readDiv 閲覧状況区分
     * @param orgId 組織ID
     * @param messageId メッセージＩＤ
     * * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(String readDiv, String orgId, Integer messageId) {

        return f08022Service.init(readDiv, orgId, messageId);
    }

    /**
     * @param usrIdListStr 管理者OR 先生 ID
     * @param response 响应
     * @param readDiv 閲覧状況区分
     * @return R
     */
    @RequestMapping(value = "/getDownloadInfo", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getDownloadInfo(String orgId, String usrIdListStr, Integer messageId, String readDiv, HttpServletResponse response) throws IOException {
        // 学生一覧
        List<String> usrIdList = JSON.parseArray(usrIdListStr, String.class);
        // ファイル名
        String fileName = "対象者（インフォメーション）情報リスト.csv";
        File downloadFile = f08022Service.getDownloadFile(orgId, readDiv, messageId, usrIdList);
        returnFile(fileName, downloadFile, response);
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
            //2020/11/19 9.0 wyh add start
            if (file.exists()){
                file.deleteOnExit();
            }
            //2020/11/19 9.0 wyh add end
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
