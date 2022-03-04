/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import com.baidu.ueditor.ActionEnter;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : huangyong <br />
 * 変更履歴 <br />
 * 2019/03/01 : huangyong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/plugins/ueditor-1.4.3.3")
@RestController
public class UEditorController extends AbstractController {

    @RequestMapping(value = "/ueditorUpload")
    public void upload(HttpServletRequest request, HttpServletResponse response) {

        try {
            response.setContentType("application/json");
            request.setCharacterEncoding("utf-8");

//            logger.error("1:"+this.getClass().getResource("").getPath().replace("/jp/learningpark/modules/com/controller/","/statics/"));
//            logger.error("2:"+this.getClass().getResource("").getPath());
//            logger.error("3:"+this.getClass().getResource("").getPath());
//            logger.error("4:"+this.getClass().getResource("").getPath());
//            logger.error("5:"+this.getClass().getResource("").getPath());
//            logger.error("6:"+this.getClass().getResource("").getPath());
////           String rootPath =  UEditorController.class.getResource("/").getPath();

            //Server
//            String rootPath=  this.getClass().getResource("").getPath().replace("/jp/learningpark/modules/com/controller/","/statics/");


            //local
//            String rootPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//            rootPath = rootPath.replace("classes", "resources");
//            rootPath = URLDecoder.decode(rootPath, "UTF-8");
//            rootPath += "statics";

            String rootPath=new ClassPathResource("/statics").getFile().getPath();

//            logger.error("7:"+this.getClass().getClassLoader().getResource("/").getPath());
//            logger.error("8："+this.getClass().getClassLoader().getResource("").getPath());
//
//            logger.error("9:"+this.getClass().getResource("").getPath());
//            logger.error("10:"+this.getClass().getResource("/").getPath());
//
//            logger.error("11:"+this.getClass().getClassLoader().getResource(".").getPath());
//
//            logger.error("12:"+Thread.currentThread().getContextClassLoader().getResource("/").getPath());
//            logger.error("13:"+Thread.currentThread().getContextClassLoader().getResource("").getPath());
//
//            logger.error("14:"+Thread.currentThread().getContextClassLoader().getResource(".").getPath());





            String exec = new ActionEnter(request, rootPath, "/plugins/ueditor-1.4.3.3/jsp/config.json").exec();
            /* 2021/09/16 manamiru1-772 cuikl del start */
            /* 2021/09/16 manamiru1-772 cuikl del end */
            exec = exec.replaceAll("\"uriPrefixPlacement\"", "\"" + GakkenConstant.urlPrefix + "\"");
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
