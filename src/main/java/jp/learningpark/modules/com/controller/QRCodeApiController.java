package jp.learningpark.modules.com.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.service.QRCodeApiService;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * マナミルQR認識API
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/12/18 ： NWT)hxl ： 新規作成
 * @date 2019/12/18 10:27
 */
@RestController
public class QRCodeApiController {
    @Autowired
    MstCodDService mstCodDService;
    @Autowired
    QRCodeApiService qrCodeApiService;

    /**
     * <P>統合API</P>
     *
     * @param params jsonパラメーター
     * @return
     */
    @RequestMapping(value = "/QRATAPI", method = RequestMethod.POST)
    public R api(@RequestBody String params) {
        MstCodDEntity one = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "QR_DORUN_KBN").eq("del_flg", 0).last("limit 1"));
        if (one == null){
            return R.error().put("message", "「QR_DORUN_KBN」の詳細データがありません").put("token", "");
        }
        qrCodeApiService.setTestFlag(one.getCodValue());
        if (StringUtils.equals("0", one.getCodValue())) {
            //NWT　楊　2021/07/13　MANAMIRU1-703　Edit　Start
            //新しいバージョン
            Map<String, Object> paramsMap = (Map) JSON.parse(params);
            switch (paramsMap.get("type").toString()) {
                case Constant.QR_LOGIN:
                    return qrCodeApiService.login(paramsMap.get("orgId").toString(), paramsMap.get("loginId").toString(), paramsMap.get("password").toString());
                case Constant.TOKEN_REFRESH:
                    return qrCodeApiService.tokenRefresh(paramsMap.get("orgId").toString(), paramsMap.get("loginId").toString(), paramsMap.get("token").toString());
                case Constant.STUDENT_LOGIN:
                    return qrCodeApiService.studentLogin(paramsMap.get("orgId").toString(), paramsMap.get("loginId").toString(), paramsMap.get("studentId").toString(), paramsMap.get("token").toString());
                default:
                    return R.error().put("message", "「タイプ」エラー").put("token", "");
            }
        } else {
            //古いバージョン
            Map<String, Object> paramsMap = (Map) JSON.parse(params);
            switch (paramsMap.get("type").toString()) {
                case Constant.QR_LOGIN:
                    return qrCodeApiService.login((String) paramsMap.get("brandCode"), paramsMap.get("orgId").toString(), paramsMap.get("password").toString());
                case Constant.TOKEN_REFRESH:
                    return qrCodeApiService.tokenRefresh((String) paramsMap.get("brandCode"), paramsMap.get("orgId").toString(), paramsMap.get("token").toString());
                case Constant.STUDENT_LOGIN:
                    return qrCodeApiService.studentLogin((String) paramsMap.get("brandCode"), paramsMap.get("orgId").toString(), paramsMap.get("studentId").toString(), paramsMap.get("token").toString());
                //NWT　楊　2021/07/13　MANAMIRU1-703　Edit　End
                default:
                    return R.error().put("message", "「タイプ」エラー").put("token", "");
            }
        }
    }
}
