package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.manager.service.F08020Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 9.0
 * <p>
 * 変更履歴:
 * 2020/11/3 ： NWT)hxl ： 新規作成
 * 2020/11/18 ： NWT)hxl ： 変更
 * @date 2020/11/3 19:41
 */
@RestController
@RequestMapping(value = "/manager/F08020")
public class F08020Controller {
    /**
     * f08020Service
     */
    @Autowired
    F08020Service f08020Service;

    /**
     * 初期オブジェクトリスト
     * @param openDiv   開封状況区分
     * @param readDiv   閲覧状況区分
     * @param noticeId  お知らせID
     * @param orgId     組織ID
     * @return
     */
    @RequestMapping("/init")
    public R init(String openDiv, String readDiv, Integer noticeId, String orgId){
        return f08020Service.init(openDiv, orgId, readDiv, noticeId);
    }

    /**
     * @param stuIdListStr  生徒ID
     * @param response      响应
     * @param openDiv       開封状況区分
     * @param readDiv       閲覧状況区分
     * @param orgId         組織ID
     * @return R
     */
    @RequestMapping(value = "/getDownloadInfo", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getDownloadInfo(String stuIdListStr, Integer noticeId, String openDiv, String readDiv, String orgId, HttpServletResponse response) {

        // 学生一覧
        List<String> stuIdList = JSON.parseArray(stuIdListStr, String.class);
        // ファイル名
        String fileName = "対象者（お知らせ）情報リスト.csv";
        File downloadFile = f08020Service.getDownloadFile(orgId, openDiv, readDiv, noticeId, stuIdList);
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
            //2020/11/18 9.0 huangxinliang add start
            if (file.exists()){
                file.deleteOnExit();
            }
            //2020/11/18 9.0 huangxinliang add end
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
