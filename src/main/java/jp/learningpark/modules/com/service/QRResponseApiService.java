package jp.learningpark.modules.com.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.com.dto.QRResponseApiDto;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * QR解答相关 Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/24 ： NWT)hxl ： 新規作成
 * @date 2020/02/24 11:44
 */
public interface QRResponseApiService {

    /**
     * 教科書のダウンロード情報に戻る
     *
     * @param qrResponseApiDto  QRResponseのJSON情報
     * @return
     */
    R getResponse(QRResponseApiDto qrResponseApiDto);

    /**
     * 解答集アプリのログイン
     *
     * @param qrResponseApiDto  QRResponseのJSON情報
     * @return
     */
    R qrDownloadLogin(QRResponseApiDto qrResponseApiDto);
    /**
     * ダウンロードファイル
     *
     * @param fileName  ファイル名
     * @param token     トークン
     * @return
     */
    Object download(HttpServletResponse response, String fileName, String token);
}
