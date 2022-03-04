/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.modules.sys.servlet;

import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.HttpContextUtils;
import jp.learningpark.framework.utils.MessageUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * サーバから画像リソースを取得します。
 *
 * @author gong
 * @email neways@gmail.com
 * @date 2019年04月18日 下午14:00
 */
@WebServlet(name = "imgServlet", urlPatterns = {"/server-image/*","/plugins/ueditor-1.4.3.3/dialogs/server-image/*"})
public class SysImageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI().replace(HttpContextUtils.getHttpServletRequest().getContextPath(),"");
        String rs = uri.split("/server-image/")[1];
        String result = rs.replace("/", File.separator);

        OutputStream os = null;

        // 服务器上的文件
        File file = FileUtils.getStorageFile(MessageUtils.getMessage("path.img"), result);

        FileInputStream fips = null;
        try {
            os = response.getOutputStream();
            fips = new FileInputStream(file);
            byte[] btImg = readStream(fips);
            os.write(btImg);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (fips != null) {
                    fips.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * パイプ内のストリームデータを読み出します。
     */
    public byte[] readStream(InputStream inStream) {
        ByteArrayOutputStream bops = new ByteArrayOutputStream();
        int data = -1;
        try {
            while ((data = inStream.read()) != -1) {
                bops.write(data);
            }
            return bops.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }
}
