package jp.learningpark.framework.xss;

import jp.learningpark.framework.exception.RRException;
import org.apache.commons.lang3.StringUtils;

/**
 * SQLフィルタ
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-01 16:16
 */
public class SQLFilter {

    public static String sqlInject(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        str = str.toLowerCase();

        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alert", "drop"};

        for(String keyword : keywords){
            if(str.indexOf(keyword) != -1){
                throw new RRException("SQL error");
            }
        }

        return str;
    }
}
