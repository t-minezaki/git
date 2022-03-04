package jp.learningpark.modules.com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.dto.QRResponseApiDto;
import jp.learningpark.modules.com.service.QRResponseApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * QR解答相关 Controller
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/24 ： NWT)hxl ： 新規作成
 * @date 2020/02/24 11:39
 */
@RestController
public class QRResponseApiController {

    /**
     * qrResponseApiService
     */
    @Autowired
    QRResponseApiService qrResponseApiService;

    /**
     * <P>統合API</P>
     *
     * @param params    jsonパラメーター
     * @return
     */
    @RequestMapping(value = "/QRAPI", method = RequestMethod.POST)
    public R api(@RequestBody String params) {
        QRResponseApiDto qrResponseApiDto = JSON.parseObject(StringUtils.defaultString(params), new TypeReference<QRResponseApiDto>() {
        });
        switch (qrResponseApiDto.getType()){
            case Constant.QR_DL_LOGIN:
                return qrResponseApiService.qrDownloadLogin(qrResponseApiDto);
            case Constant.CHECK_VER:
                return qrResponseApiService.getResponse(qrResponseApiDto);
            default:
                return R.error().put("code", 500).put("message", "タイプ値が正しくありません。");
        }
    }

    /**
     * ダウンロードファイル
     *
     * @param fileName  ファイル名
     * @param token     トークン
     * @return
     */
    @RequestMapping(value = "/download/{fileName}", method = RequestMethod.GET)
    public Object download(HttpServletResponse response, @PathVariable("fileName")String fileName, String token){
        //ダウンロード
        return qrResponseApiService.download(response, fileName, token);
    }
}
