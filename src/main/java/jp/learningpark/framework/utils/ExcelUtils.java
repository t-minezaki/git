/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.framework.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Iterator;


/**
 * * poiベースのjavaeeはexcelツールを導出する
 * *
 * * @author gong
 * * @see POI
 */
public class ExcelUtils {

    /**
     * @param response      請求
     * @param fileName      ファイル名は「生徒表」
     * @param commontHeader commont
     * @param excelHeader   エクセルの表ヘッダ数群には、「名前#name」フォーマット文字列、「名前」がexcelタイトル行、「name」が対象フィールド名が格納されている
     * @param dataList      データセットは、表头数组のフィールド名と一致し、javabeanの仕様を満たしていなければならない
     * @return hssfworkbookを返す
     * @throws Exception
     */
    public static <T> void export(HttpServletResponse response, String fileName, String[] commontHeader, String[] excelHeader, Collection<T> dataList) throws Exception {
        // 設定要求
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; fileName=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8"));
        response.addHeader("Cache-Control", "no-cache");
        // workbookを作成し、excelファイルに対応します
        HSSFWorkbook wb = null;
        BufferedOutputStream toClient = null;
        try {
            wb = new HSSFWorkbook();
            // タイトル様式を設ける
            HSSFCellStyle titleStyle = wb.createCellStyle();
            // 備考を設ける
            HSSFCellStyle commontStyle = wb.createCellStyle();
            // 设置タイトル字体样式
            Font titleFont = wb.createFont();
            titleFont.setFontHeightInPoints((short) 10);
            titleFont.setFontName("Meiryo UI");
            titleStyle.setFont(titleFont);
            titleStyle.setWrapText(true);
            //设置備考字体样式
            Font commontFont = wb.createFont();
            commontFont.setFontHeightInPoints((short) 10);
            commontFont.setFontName("Meiryo UI");
            commontFont.setColor(IndexedColors.RED.getIndex());
            commontStyle.setFont(commontFont);
            commontStyle.setWrapText(true);
            // workbookにsheetを追加し、excelファイル中のsheetに対応します
            HSSFSheet sheet = wb.createSheet("sheet1");
            // 表題数列
            String[] titleArray = new String[excelHeader.length];
            // フィールド名の数
            String[] fieldArray = new String[excelHeader.length];
            for (int i = 0; i < excelHeader.length; i++) {
                String[] tempArray = excelHeader[i].split("#");
                titleArray[i] = tempArray[0];
                fieldArray[i] = tempArray[1];
            }
            // sheetで備考行を追加します
            HSSFRow rowCommont = sheet.createRow(0);
            rowCommont.setHeight((short) (25 * 50));
            for (int i = 0; i < commontHeader.length; i++) {
                HSSFCell titleCell = rowCommont.createCell(i);
                titleCell.setCellValue(commontHeader[i]);
                titleCell.setCellStyle(commontStyle);
                sheet.autoSizeColumn(i + 1);
            }
            //sheetでタイトル行の样式を追加します
            titleStyle.setBorderLeft(BorderStyle.THIN);
            titleStyle.setBorderBottom(BorderStyle.THIN);
            titleStyle.setBorderRight(BorderStyle.THIN);
            titleStyle.setBorderTop(BorderStyle.THIN);
            titleStyle.setFillForegroundColor(IndexedColors.CORAL.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            // sheetでタイトル行を追加します
            HSSFRow row = sheet.createRow(1);
            // タイトルに値を付ける
            for (int i = 0; i < titleArray.length; i++) {
                HSSFCell titleCell = row.createCell(i);
                titleCell.setCellValue(titleArray[i]);
                titleCell.setCellStyle(titleStyle);
                sheet.autoSizeColumn(i + 1);
            }
            // データパターンはタイトルとデータパターンが異なるので別々に設定する必要がありますがカバーします
            HSSFCellStyle dataStyle = wb.createCellStyle();
            Font dataFont = wb.createFont();
            dataFont.setFontHeightInPoints((short) 12);
            dataStyle.setFont(dataFont);
            // 集合データを横断して、データ列を作成する
            Iterator<T> it = dataList.iterator();
            int index = 1;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                sheet.autoSizeColumn(0);
                T t = (T) it.next();
                // 利用反射を利用して、伝えてきたフィールド名数組によって、動的に対応するgetXXXを呼び出す方法が属性値を得ている
                for (int i = 0; i < fieldArray.length; i++) {
                    HSSFCell dataCell = row.createCell(i);
                    dataCell.setCellStyle(dataStyle);
                    sheet.autoSizeColumn(i);
                    String fieldName = fieldArray[i];
                    dataCell.setCellValue(BeanUtils.getProperty(t, fieldName));
                }
            }
            toClient = new BufferedOutputStream(response.getOutputStream());
            wb.write(toClient);
            toClient.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (wb != null) {
                wb.close();
            }
            if (toClient != null) {
                toClient.close();
            }
        }
    }


    /**
     * <p>テンプレートによってexcelファイルを生成する</p>
     *
     * @param wb
     * @param fieldArray
     * @param response
     * @param fileNm
     * @param dataList
     * @param <T>
     * @throws Exception
     */
    public static <T> void export(XSSFWorkbook wb, String[] fieldArray, HttpServletResponse response, String fileNm, Collection<T> dataList) throws Exception {
        // 設定要求
        response.setContentType("application/octet-stream");
        response.addHeader("Cache-Control", "no-cache");
        response.setHeader("Content-Disposition", "attachment; fileName=" + fileNm + ";filename*=utf-8''" + URLEncoder.encode(fileNm, "UTF-8"));
        BufferedOutputStream toClient = null;
        try {
            //テンプレートのsheet
            Sheet sheet = wb.getSheetAt(0);
            Row row;

            Iterator<T> it = dataList.iterator();
            int index = 0;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                T t = (T) it.next();
                // 利用反射を利用して、伝えてきたフィールド名数組によって、動的に対応するgetXXXを呼び出す方法が属性値を得ている
                for (int i = 0; i < fieldArray.length; i++) {
                    Cell dataCell = row.createCell(i);
                    String fieldName = fieldArray[i];
                    dataCell.setCellValue(BeanUtils.getProperty(t, fieldName));
                }
            }
            toClient = new BufferedOutputStream(response.getOutputStream());
            wb.write(toClient);
            toClient.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (wb != null) {
                wb.close();
            }
            if (toClient != null) {
                toClient.close();
            }
        }
    }

    /**
     * <p>テンプレートによってexcelファイルを生成する</p>
     *
     * @param wb
     * @param index
     * @param fieldArray
     * @param dataList
     * @param <T>
     * @throws Exception
     */
    public static <T> XSSFWorkbook createExcelWb(XSSFWorkbook wb, Integer index, String[] fieldArray, Collection<T> dataList) throws Exception {
        //テンプレートのsheet
        Sheet sheet = wb.getSheetAt(0);
        Row row;
        Iterator<T> it = dataList.iterator();
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射を利用して、伝えてきたフィールド名数組によって、動的に対応するgetXXXを呼び出す方法が属性値を得ている
            for (int i = 0; i < fieldArray.length; i++) {
                Cell dataCell = row.createCell(i);
                String fieldName = fieldArray[i];
                dataCell.setCellValue(BeanUtils.getProperty(t, fieldName));
            }
        }
        return wb;
    }

    /**
     * <p>テンプレートによってexcelファイルを生成する(F02001)</p>
     *
     * @param wb
     * @param index
     * @param fieldArray
     * @param dataList
     * @param <T>
     * @throws Exception
     */
    public static <T> XSSFWorkbook createExcelWbF02001(XSSFWorkbook wb, Integer index, String[] fieldArray, Collection<T> dataList) throws Exception {
        //テンプレートのsheet
        Sheet sheet = wb.getSheetAt(0);
        Row row;
        Iterator<T> it = dataList.iterator();
        //CellStyle style = wb.createCellStyle();
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            CellStyle style = wb.createCellStyle();
            if (StringUtils.equals("isOrg", fieldArray[0]) && StringUtils.equals(BeanUtils.getProperty(t, fieldArray[0]), "2")) {
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            }

            if (StringUtils.equals("isOrg", fieldArray[0]) && StringUtils.equals(BeanUtils.getProperty(t, fieldArray[0]), "3")) {
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            }

            // 利用反射を利用して、伝えてきたフィールド名数組によって、動的に対応するgetXXXを呼び出す方法が属性値を得ている
            for (int i = 1; i < fieldArray.length; i++) {
                Cell dataCell = row.createCell(i - 1);
                String fieldName = fieldArray[i];
                dataCell.setCellValue(BeanUtils.getProperty(t, fieldName));
                dataCell.setCellStyle(style);
            }
        }
        return wb;
    }

    /**
     * <p>テンプレートによってexcelファイルを生成する(F09001)</p>
     *
     * @param wb
     * @param index
     * @param fieldArray
     * @param dataList
     * @param <T>
     * @throws Exception
     */
    public static <T> XSSFWorkbook createExcelWbF09001(XSSFWorkbook wb, Integer index, String[] fieldArray, Collection<T> dataList) throws Exception {
        //テンプレートのsheet
        Sheet sheet = wb.getSheetAt(0);
        Row row;
        Iterator<T> it = dataList.iterator();
        //CellStyle style = wb.createCellStyle();
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            CellStyle style = wb.createCellStyle();
            // 利用反射を利用して、伝えてきたフィールド名数組によって、動的に対応するgetXXXを呼び出す方法が属性値を得ている
            for (int i = 0; i < fieldArray.length; i++) {
                Cell dataCell = row.createCell(i);
                String fieldName = fieldArray[i];
                dataCell.setCellValue(BeanUtils.getProperty(t, fieldName));
                dataCell.setCellStyle(style);
            }
        }
        return wb;
    }

    /**
     * <p>テンプレートによってexcelファイルを生成する(F21014)</p>
     *
     * @param excelHeader
     * @param dataList
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> XSSFWorkbook createExcelWbF21014(String[] excelHeader, Collection<T> dataList) throws Exception {
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("sheet1");
            for(int i = 0; i < excelHeader.length; i++){
                sheet.setColumnWidth(i, 14);
            }
            CellStyle titleStyle = wb.createCellStyle();
            titleStyle.setBorderLeft(BorderStyle.THIN);
            titleStyle.setBorderBottom(BorderStyle.THIN);
            titleStyle.setBorderRight(BorderStyle.THIN);
            titleStyle.setBorderTop(BorderStyle.THIN);
            titleStyle.setFillForegroundColor(IndexedColors.CORAL.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = wb.createFont();
            font.setColor(IndexedColors.GREEN.getIndex());
            font.setFontName("Meiryo UI");
            titleStyle.setFont(font);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            CellStyle contentStyle = wb.createCellStyle();
            contentStyle.setAlignment(HorizontalAlignment.CENTER);
            XSSFRow row = sheet.createRow(1);
            // タイトルに値を付ける
            for (int i = 0; i < excelHeader.length; i++) {
                XSSFCell titleCell = row.createCell(i);
                titleCell.setCellValue(excelHeader[i]);
                titleCell.setCellStyle(titleStyle);
                sheet.autoSizeColumn(i + 1);
            }
            // データパターンはタイトルとデータパターンが異なるので別々に設定する必要がありますがカバーします
            XSSFCellStyle dataStyle = wb.createCellStyle();
            Font dataFont = wb.createFont();
            dataFont.setFontHeightInPoints((short) 12);
            dataStyle.setFont(dataFont);
            // 集合データを横断して、データ列を作成する
            Iterator<T> it = dataList.iterator();
            int index = 1;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                sheet.autoSizeColumn(0);
                T t = (T) it.next();
                // 利用反射を利用して、伝えてきたフィールド名数組によって、動的に対応するgetXXXを呼び出す方法が属性値を得ている
                for (int i = 0; i < excelHeader.length; i++) {
                    XSSFCell dataCell = row.createCell(i);
                    dataCell.setCellStyle(dataStyle);
                    sheet.autoSizeColumn(i);
                    if (i < 2) {
                        String fieldName = excelHeader[i];
                        dataCell.setCellValue(BeanUtils.getProperty(t, fieldName));
                    }else {
                        String[] day = BeanUtils.getArrayProperty(t, "day");
                        dataCell.setCellValue(day[i - 2]);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * <p>サーバパス下のexcelファイルを出力する</p>
     *
     * @param response
     * @param outPath  出力ファイルパスとファイル名
     */
    public static void excelDownload(HttpServletResponse response, String outPath) {
        String fileName = outPath.substring(outPath.lastIndexOf("/") + 1);
        excelDownload(response, outPath, fileName);
    }


    /**
     * <p>ファイルのタイトル行のチェック</p>
     *
     * @param row        タイトル行
     * @param cellValues タイトルのvalue
     */
    public static boolean isApplicable(Row row, String[] cellValues) {
        boolean flg = true;
        Cell cell;
        for (int i = 0; i < cellValues.length; i++) {
            cell = row.getCell(i);
            cell.setCellType(CellType.STRING);
            if (!StringUtils.equals(cell.getStringCellValue(), cellValues[i])) {
                flg = false;
                break;
            }
        }
        return flg;
    }

    /**
     * 行かどうかを判断する
     *
     * @param row
     * @param count
     * @return
     */
    public static boolean isEmptyRow(Row row, int count) {
        boolean flg = true;
        if (row == null) {
            return true;
        }
        Cell cell;
        for (int i = 0; i < count; i++) {
            cell = row.getCell(i);
            if (cell != null && !StringUtils.isEmpty(cell.toString())) {
                flg = false;
                break;
            }
        }
        return flg;
    }


    /**
     * <p>サーバパス下のexcelファイルを出力する</p>
     *
     * @param response
     * @param outPath  出力ファイルパスとファイル名
     */
    public static void excelDownload(HttpServletResponse response, String outPath, String downFileName) {
        // 設定要求
        response.addHeader("Cache-Control", "no-cache");
        File file = null;
        FileInputStream fileInputStream;
        XSSFWorkbook wb = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            response.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode(downFileName, "UTF-8") + ";filename*=utf-8''" + URLEncoder.encode(downFileName, "UTF-8"));
            response.setContentType("application/x-msdownload;");
            file = new File(outPath);
            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());

            fileInputStream = new FileInputStream(file);
            bis = new BufferedInputStream(fileInputStream);
            wb = new XSSFWorkbook(bis);
            wb.write(bufferedOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (file != null && file.exists()) {
                // ダウンロード完了、ファイルを削除する
                file.delete();
            }
        }
    }

    /**
     * <p>テンプレートファイルを出力ファイルに出力して保存する</p>
     *
     * @param response
     * @param wb
     * @param downFileName 出力ファイルパスとファイル名
     */
    public static void getTemplate(HttpServletResponse response, XSSFWorkbook wb, String downFileName) {
        // 設定要求
        response.addHeader("Cache-Control", "no-cache");

        BufferedOutputStream bufferedOutputStream = null;
        try {
            response.setHeader("Content-Disposition", "attachment; fileName=" + URLEncoder.encode(downFileName, "UTF-8") + ";filename*=utf-8''" + URLEncoder.encode(downFileName, "UTF-8"));
            response.setContentType("application/x-msdownload;");
            bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
            wb.write(bufferedOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}