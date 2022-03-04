package jp.learningpark.framework.gakkenID;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.gakkenID.dto.BookEndReceiveDto;
import jp.learningpark.framework.gakkenID.dto.BookEndSendDto;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/3/12 ： NWT)hxl ： 新規作成
 * @date 2021/3/12 11:36
 */
public class BookEndApi {
    private static final Logger logger = LoggerFactory.getLogger(BookEndApi.class);
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient CLIENT = new OkHttpClient().newBuilder()
            .readTimeout(60000, TimeUnit.MILLISECONDS)
            .connectTimeout(60000,TimeUnit.MILLISECONDS)
            .hostnameVerifier((hostname, session) -> true).build();

    public static String active(List<BookEndSendDto> bookEndSendDtoList, String apiKey, String url, String path){
        long start = System.currentTimeMillis();
        logger.info("START {}:{}", path, start);
        String json = JSON.toJSONString(bookEndSendDtoList);
        logger.info("bookend api:" + url + path + " send date:" + json);
        RequestBody body = RequestBody.create(MEDIA_TYPE, json);
        Request request = new Request.Builder().url(url + path)
                .addHeader("Content-Type","application/json")
                .addHeader("charset", "utf-8")
                .addHeader("X-API-KEY", apiKey)
                .addHeader("Content-Length", json.length() + "")
                .post(body)
                .build();
        String responseString;
        try {
            Response response = CLIENT.newCall(request).execute();
            if (response.code() != 200) {
                logger.info("response code: " + response.code());
                switch (response.code()) {
                    case 400:
                        logger.error("リクエストヘッダに誤りがあります。");
                        return null;
                    case 401:
                        logger.error("認証に失敗しました。");
                        return null;
                    case 404:
                        logger.error("指定されたリソースが存在しません。");
                        return null;
                    case 500:
                        logger.error("サーバで内部エラーが発生しました。");
                        return null;
                    default:
                        logger.error("その他エラーです。");
                        return null;
                }
            }
            responseString = response.body().string();
            logger.info("bookend api:" + url + path + " received date:" + responseString);
        }catch (IOException e){
            logger.error("本棚APIリクエスト中に例外が発生しました。");
            logger.error(e.getMessage());
            return null;
        }finally {
            long end = System.currentTimeMillis();
            logger.info("END   {}:{}", path, end);
            logger.info("{}処理時間: {}ms", path, end - start);
        }
        return responseString;
    }

    public static String pretendActive(List<BookEndSendDto> bookEndSendDtoList, String apiKey, String url, String path){
        long start = System.currentTimeMillis();
        logger.info("START {}:{}", path, start);
        String json = JSON.toJSONString(bookEndSendDtoList);
        logger.info("bookend api:" + url + path + " send date:" + json);
        List<BookEndReceiveDto> bookEndReceiveDtoList = new ArrayList<>();
        for (int i = 0; i < bookEndSendDtoList.size(); i++) {
            BookEndSendDto bookEndSendDto = bookEndSendDtoList.get(i);
            BookEndReceiveDto bookEndReceiveDto = new BookEndReceiveDto();
            bookEndReceiveDto.setGidpk(bookEndSendDto.getGidpk());
            bookEndReceiveDto.setGroupid(bookEndSendDto.getGroupid());
            bookEndReceiveDto.setResult(i % 2 == 0 ? "true" : "false");
            bookEndReceiveDtoList.add(bookEndReceiveDto);
        }
        String result = JSON.toJSONString(bookEndReceiveDtoList);
        logger.info("bookend api:" + url + path + " received date:" + result);
        long end = System.currentTimeMillis();
        logger.info("END   {}:{}", path, end);
        logger.info("{}処理時間: {}ms", path, end - start);
        return result;
    }
}
