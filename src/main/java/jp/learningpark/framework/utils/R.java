/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.framework.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * アヤックス共通クラス
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(500, "システムエラー　管理者に連絡してください");
    }

    public static R error(String msg) {
        return error(-1, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(BindingResult result) {
        R r = new R();
        r.put("code", -1);
        StringBuffer msg = new StringBuffer();

        for (FieldError fieldError : result.getFieldErrors()) {
            Object[] args =  fieldError.getArguments();
//            msg.append(MessageUtils.getMessage("error." + fieldError.getCode(), Stringargs));
            
        }

        r.put("msg", msg.toString());
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
