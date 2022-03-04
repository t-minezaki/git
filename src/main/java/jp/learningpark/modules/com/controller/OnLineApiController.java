package jp.learningpark.modules.com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.com.service.OnLineApiService;
import jp.learningpark.modules.common.dao.SysAccessPermissionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * マナミルSAMLMAPPING API
 * </p>
 *
 * @author NWT)lyx
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/03/16 ： NWT)lyx ： 新規作成
 * @date 2021/03/16 10:27
 */
@RestController
public class OnLineApiController {

    @Autowired
    OnLineApiService onLineApiService;

    @Autowired
    SysAccessPermissionDao  sysAccessPermissionDao;
    /**
     * <P>統合API</P>
     *
     * @return
     */
    @RequestMapping(value = "/SamlDataSync", method = RequestMethod.POST)
    public ResponseEntity<Object> api(@RequestBody String data) {
        try{
            // 1.0　マナミルシステム訪問のサーバーIPを取得する、訪問できるかどうか判断する
//            String ip = getServerIp();
//            List<SysAccessPermissionEntity> ipList = sysAccessPermissionDao.selectList(new QueryWrapper<SysAccessPermissionEntity>().eq("server_ip", ip).eq("del_flg", 0));
//            if(ipList.size() == 0){
//                return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0191", ip)).put("code", "202");
//            }
            // jsonのキーワード「records」のデータを取得します。
            JSONObject params = JSON.parseObject(data);
            String serialNumber = params.get("SerialNumber").toString();
            String timestamp = params.get("timestamp").toString();
            JSONArray paramsMap = params.getJSONArray("records");
            return onLineApiService.handleData(paramsMap, serialNumber, timestamp, data);
        }catch(Exception e){
            e.printStackTrace();
            //データ解析失敗
            //201 --> HTTP/1.1 400 Bad Request
            return ResponseEntity.badRequest().body(R.error().put("msg", "データ解析失敗").put("code", "201").put("HTTP/1.1", "400 Bad Request"));
        }
    }


    /**
     * リクエストの送信者のIPを取得
     *
     * @return
     */
    public String getServerIp() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes == null ? null : servletRequestAttributes.getRequest();
        return request == null ? null : request.getRemoteAddr();
    }
}
