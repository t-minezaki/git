package jp.learningpark.modules.com.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.http.ResponseEntity;

/**
 *  マナミルSAMLMAPPING API
 *
 * @author NWT)lyx
 */
public interface OnLineApiService {

    /**
     * jsonDataHanle
     * @param paramsMap
     * @param serialNumber
     * @param timestamp
     * @param data
     * @return
     */
    ResponseEntity<Object> handleData(JSONArray paramsMap, String serialNumber, String timestamp, String data);

    /**
     * getGidKp
     * @param gid
     * @return
     * @throws Exception
     */
    String  getGidKp(String gid) throws Exception;

    /**
     * 【エラー】メール通知送信を行う。
     * @param errFilePath
     * @return
     */
    void postEmail(String errFilePath, String serialNumber, String timestamp, String outPutCsvFilePath);
}
