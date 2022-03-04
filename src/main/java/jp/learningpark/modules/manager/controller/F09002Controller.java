package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.manager.dto.F09002Dto;
import jp.learningpark.modules.manager.service.F09002Service;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/manager/F09002")
public class F09002Controller {

    /**
     * f09002Service
     */
    @Autowired
    F09002Service f09002Service;

    /**
     * logger
     */
    Logger logger = LoggerFactory.getLogger(F09002Controller.class);

    /**
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping(value = "/init")
    public R init(Integer limit, Integer page, String orgId, String stuIdListStr) {

        // 組織ID
        orgId = (orgId == null || StringUtils.isEmpty(orgId)) ? ShiroUtils.getUserEntity().getOrgId() : orgId;
        //メンターID
        String userId = ShiroUtils.getUserEntity().getUsrId();
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();

        // 生徒リステ
        List<String> stuIdList = JSON.parseArray(stuIdListStr, String.class);

        List<F09002Dto> f09002DtoList = f09002Service.init(orgId, stuIdList, userId, roleDiv, limit, (page - 1) * limit);
        Integer count = f09002Service.init(orgId, stuIdList, userId, roleDiv, null, null).size();
        if (f09002DtoList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "二次元コード"));
        }

        return R.ok().put("page", new PageUtils(f09002DtoList, count, limit, page)).put("orgId", orgId);
    }

    /**
     * 二次元コードを生成する
     *
     * @param content
     * @param resp
     * @throws Exception
     */
    @RequestMapping(value = "/qrcode")
    public void getQrcode(String content, HttpServletResponse resp) throws Exception {

        if (content.isEmpty()) {
            return;
        }

        ServletOutputStream stream = null;
        try {
            stream = resp.getOutputStream();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 0);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bm = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 200, 200, hints);
            MatrixToImageWriter.writeToStream(bm, "png", stream);
        } catch (WriterException e) {
            logger.error(e.getMessage());

        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * CSVファイルのダウンロード
     *
     * @param response 响应
     * @return R
     */
    @RequestMapping(value = "/getDownloadInfo", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getDownloadInfo(HttpServletResponse response, String orgId, String stuIdListStr) throws UnsupportedEncodingException {

        // 組織ID
        orgId = (orgId == null || StringUtils.isEmpty(orgId)) ? ShiroUtils.getUserEntity().getOrgId() : orgId;
        //メンターID
        String userId = ShiroUtils.getUserEntity().getUsrId();
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();
        // 生徒リステ
        List<String> stuIdList = JSON.parseArray(URLDecoder.decode(stuIdListStr, "UTF-8"), String.class);

        List<F09002Dto> f09002DtoList = f09002Service.init(orgId, stuIdList, userId, roleDiv, null, null);

        // ファイル名
        String fileName = "二次元コード一覧.csv";
        try {
            // ファイル
            File tempFile = File.createTempFile("二次元コード一覧_" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDD), ".csv");
            FileWriter fw = new FileWriter(tempFile);
            // csv形式
            fw.write(new String(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF}));
            // 書類の内容
            String header = "\n";
            String header2 = "\t組織名\t" + "," + "\tシステムID\t" + "," + "\tオリジナルCD\t" + "," + "\t生徒ID\t" + "," + "\t生徒名\t" + "," + "\t学年\t" + "," + "\t二次元コード（ソース情報）\t" + "\n";
            fw.write(header);
            fw.write(header2);
            StringBuffer str = new StringBuffer();
            // ファイルの書き込み
            for (F09002Dto f09002Dto : f09002DtoList) {

                str.append(
                        "\t" + f09002Dto.getOrgNm() + "\t" + "," + "\t" + f09002Dto.getStuId() + "\t" + "," + "\t" + f09002Dto.getOriaCd() + "\t" + "," + "\t" + f09002Dto.getAfterUsrId() + "\t" + "," + "\t" + f09002Dto.getStuNm() + "\t" + "," + "\t" + f09002Dto.getSchyDiv() + "\t" + "," + "\t" + f09002Dto.getQrCod() + "\t" + "\n");
            }
            fw.write(str.toString());
            fw.flush();
            fw.close();

            fileName = "二次元コード一覧" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDD) + ".csv";
            returnFile(fileName, tempFile, response);
        } catch (IOException e) {

            logger.error(e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * @param filename ファイル名
     * @param file ファイル
     * @param response 响应
     */
    private void returnFile(String filename, File file, HttpServletResponse response) {

        // responseパラメータ
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            // headerを設定する
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }

        // ブラウザにファイルを返す
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 印刷が必要なファイルを取得する
     *
     * @param startRow 行目
     * @param startCol 列目
     * @param response
     * @return
     */
    @RequestMapping(value = "/getPrintFile", method = RequestMethod.GET)
    public void getPrintFile(Integer startRow, Integer startCol, HttpServletResponse response, String orgId, String stuIdListStr) throws UnsupportedEncodingException {

        // セル幅
        final int COL_WIDTH = 9000;

        // セル高さ
        final int ROW_HEIGHT = 3000;

        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFSheet sheet = wb.createSheet("一括印刷データ");

        // セル行
        XSSFRow row = sheet.createRow(0);
        // セル
        XSSFCell cell = row.createCell(0);
        // セル様式
        CellStyle style = wb.createCellStyle();

        // セルフォント
        Font font = wb.createFont();
        // 粗体
        font.setBold(true);
        // フォントサイズ
        font.setFontHeightInPoints((short)13);
        style.setFont(font);
        // 自動改行
        style.setWrapText(true);
        // 垂直整列
        style.setVerticalAlignment(VerticalAlignment.TOP);

        // 組織ID
        orgId = (orgId == null || StringUtils.isEmpty(orgId)) ? ShiroUtils.getUserEntity().getOrgId() : orgId;

        // 生徒リステ
        List<String> stuIdList = JSON.parseArray(URLDecoder.decode(stuIdListStr, "UTF-8"), String.class);

        //メンターID
        String userId = ShiroUtils.getUserEntity().getUsrId();
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();
        List<F09002Dto> f09002DtoList = f09002Service.init(orgId, stuIdList, userId, roleDiv, null, null);

        for (int i = 0; i < startRow - 1; i++) {

            row = sheet.createRow(i);

            row.setHeight((short)ROW_HEIGHT);
        }

        for (int i = 0; i < startCol - 1; i++) {

            sheet.setColumnWidth(i, COL_WIDTH);
        }

        int colMax = 3 + startCol - 1;

        int colIndex = startCol - 1;

        int rowIndex = startRow - 1;

        row = sheet.createRow(rowIndex);

        for (F09002Dto f09002Dto : f09002DtoList) {

            if (colIndex > colMax) {

                colIndex = startCol - 1;
                rowIndex += 2;
                row = sheet.createRow(rowIndex);
            }

            sheet.setColumnWidth(colIndex, COL_WIDTH);

            row.setHeight((short)ROW_HEIGHT);

            cell = row.createCell(colIndex);
            cell.setCellStyle(style);
            Integer orgLoopTimes = txfloat(f09002Dto.getOrgNm().length(),6).intValue();
            Integer stuLoopTimes = txfloat(f09002Dto.getStuNm().length(),6).intValue();
            String orgNm = "";
              for (int i = 0; i < orgLoopTimes; i++) {
                  if (orgLoopTimes == 1){
                      orgNm = orgNm.concat(f09002Dto.getOrgNm().substring(i, f09002Dto.getOrgNm().length()) + "\r\n");
                  }else {
                      if (i != orgLoopTimes -1){
                          orgNm = orgNm.concat(f09002Dto.getOrgNm().substring(i * 6, (i + 1) * 6) + "\r\n");
                      }else {
                          orgNm = orgNm.concat(f09002Dto.getOrgNm().substring(i * 6, f09002Dto.getOrgNm().length()) + "\r\n");
                      }
                  }
              }
            String stuNm = "";
            for (int i = 0; i < stuLoopTimes; i++) {
                if (stuLoopTimes == 1){
                    stuNm = stuNm.concat(f09002Dto.getStuNm().substring(i, f09002Dto.getStuNm().length() ) + "\r\n");
                }else{
                    if (i != stuLoopTimes -1){
                        stuNm = stuNm.concat(f09002Dto.getStuNm().substring(i * 6, (i + 1) * 6) + "\r\n");
                    }else {
                        stuNm = stuNm.concat(f09002Dto.getStuNm().substring(i * 6, f09002Dto.getStuNm().length()) + "\r\n");
                    }
                }
            }
            // セル内容
            Integer allRows = stuNm.split("\r\n").length + orgNm.split("\r\n").length;
            String shift = "";
              for (int i = 0; i < 5 - allRows; i++) {
                  shift += "\r\n";
              }
              //組織IDの位置を調整します -- 2020/11/03 yang
            cell.setCellValue(
                    "\r\n" + orgNm + "\r\n"  + stuNm  + shift + orgId + "_" + f09002Dto.getAfterUsrId());

            BufferedImage img = null;

            // 二次元コード画像
            if (f09002Dto.getQrCod() != null && !f09002Dto.getQrCod().isEmpty()) {
                try {
                    Map<EncodeHintType, Object> hints = new HashMap<>();
                    hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                    hints.put(EncodeHintType.MARGIN, 0);
                    QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    BitMatrix bm = qrCodeWriter.encode(f09002Dto.getQrCod(), BarcodeFormat.QR_CODE, 100, 100, hints);
                    img = MatrixToImageWriter.toBufferedImage(bm);

                } catch (WriterException e) {

                    logger.error(e.getMessage());
                }
            }

            if (img != null) {
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

                try {

                    ImageIO.write(img, "jpg", byteArrayOut);

                    XSSFDrawing patriarch = sheet.createDrawingPatriarch();

                    XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, colIndex, rowIndex, colIndex + 1, rowIndex + 1);

                    // 二次元符号画像位置
                    anchor.setDx1(125 * 10000);
                    anchor.setDy1(20 * 10000);
                    anchor.setDx2(-10 * 10000);
                    anchor.setDy2(-40 * 10000);

                    anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_DO_RESIZE);

                    XSSFPicture pict = patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));
                } catch (IOException e) {
                    logger.error(e.getMessage());
                } finally {

                    try {

                        if (byteArrayOut != null) {
                            byteArrayOut.close();
                        }
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
            }

            colIndex += 3;
        }

        // ファイルが戻る
        BufferedOutputStream bufferedOutputStream = null;

        OutputStream outputStream = null;

        try {
            String fileName = "貼付用二次元コード一覧_" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDD) + ".xlsx";

            response.addHeader("Cache-Control", "no-cache");
            response.setHeader(
                    "Content-Disposition",
                    "attachment; fileName=" + URLEncoder.encode(fileName, "UTF-8") + ";filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/x-msdownload;");

            outputStream = response.getOutputStream();

            bufferedOutputStream = new BufferedOutputStream(outputStream);

            wb.write(bufferedOutputStream);

            outputStream.flush();

            bufferedOutputStream.flush();

        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {

                if (outputStream != null) {

                    outputStream.close();
                }

                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }

                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    public static BigDecimal txfloat(int a,int b) {
        BigDecimal length = new BigDecimal(a);
        BigDecimal time = new BigDecimal(b);
        return length.divide(time,2);
    }
}
