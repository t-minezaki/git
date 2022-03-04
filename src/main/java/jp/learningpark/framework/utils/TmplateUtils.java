package jp.learningpark.framework.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Map;

public class TmplateUtils {
    private static String defaultCharacter = "UTF-8";
    private static Configuration cfg;
    private static Logger logger = LoggerFactory.getLogger(TmplateUtils.class);
    private  TmplateUtils() {
    }
    static {
        cfg = new Configuration(Configuration.getVersion());
        cfg.setDefaultEncoding(defaultCharacter);
        cfg.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
    }
    /**
     * テンプレートをレンダリングする
     * @param data データMap
     * @param tplStr テンプレート
     * @return
     */
    public static  String generateString(Map<String, Object> data,  String tplStr) {
        String result = null;
        String name="myStrTpl";
        try {
            StringTemplateLoader stringTemplateLoader= new StringTemplateLoader();
            stringTemplateLoader.putTemplate(name, tplStr);
            cfg.setTemplateLoader(stringTemplateLoader);
            Template template = cfg.getTemplate(name,defaultCharacter);
            StringWriter out = new StringWriter();
            template.process(data, out);
            out.flush();
            result= out.toString();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * テンプレートを描画してファイルに保存する
     * @param templateFileDir テンプレートディレクトリ
     * @param fileName ファイル名テンプレート
     * @param targetFilePath ファイル名を描画する
     * @param dataMap データ
     * @return
     */
    public static boolean renderingTemplateAndGenerateFile(String templateFileDir, String fileName,String targetFilePath,Map<String, Object> dataMap){
        boolean flag=true;
        try {
            // 设置文件所在目录的路径
            cfg.setDirectoryForTemplateLoading(new File(templateFileDir));//模板路径
            // 获取模版
            Template template = cfg.getTemplate(fileName);
            // 设置输出文件名,和保存路径
            File outFile = new File(targetFilePath);
            // 将模板和数据模型合并生成文件 重点设置编码集
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
            // 生成文件
            template.process(dataMap, out);
            // 关闭流
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
            flag=false;
        }
        return flag;
    }

}