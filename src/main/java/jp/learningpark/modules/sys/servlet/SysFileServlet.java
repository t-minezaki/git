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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * サーバからファイルリソースを取得します。
 *
 * @author wen
 */
@WebServlet(name = "fileServlet", urlPatterns = {"/server-file/*"})
public class SysFileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String uri = request.getRequestURI().replace(HttpContextUtils.getHttpServletRequest().getContextPath(), "");
        String rs = uri.split("/server-file/")[1];
        String result = rs.replace("/", File.separator);

        OutputStream os = null;

        // 服务器上的文件
        File file = FileUtils.getStorageFile(MessageUtils.getMessage("path.file"), URLDecoder.decode(result, "UTF-8"));

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
