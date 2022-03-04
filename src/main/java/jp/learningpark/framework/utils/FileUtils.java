/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.framework.utils;

import jp.learningpark.framework.exception.RRException;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.utils.service.CommonService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ファイル操作用ユーティリティ
 *
 * @author sk
 */
@Component
@ConfigurationProperties(prefix = "server")
public class FileUtils {

    /**
     *
     */
    @Autowired
    CommonService commonService;

    private static FileUtils fileUtils;

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    @PostConstruct
    public void init() {
        fileUtils = this;
        fileUtils.commonService = this.commonService;
    }
    //    @Value("${sftp.root}")
    //    static String rootPath;
    //
    //    public FileUtils(@Value("${sftp.root}") String rootPath) {
    //        FileUtils.rootPath = rootPath;
    //    }

    /**
     * ファイル親フォルダ作成.
     *
     * @param _filePath ファイルパス.
     * @return パラメータで渡されたファイル.
     */
    public static final File mkdirsParent(String _filePath) {
        return mkdirsParent(new File(getAbsolutePath(_filePath)));
    }

    /**
     * ファイル親フォルダ作成.
     *
     * @param _file ファイル.
     * @return パラメータで渡されたファイルをそのまま返す.
     */
    public static final File mkdirsParent(File _file) {

        // 親フォルダ取得
        File parent_ = _file.getParentFile();

        // 親フォルダ存在チェック
        if (parent_ != null && !parent_.exists()) {
            _file.getParentFile().mkdirs();
        }

        // ファイル返却
        return _file;

    }

    /**
     * ストレージファイルを取得する
     *
     * @param _paths ファイルパス.
     * @return パラメータで渡されたファイル.
     */
    public static final File getStorageFile(String... _paths) {
        // ファイルパス生成
        File file = new File(StringUtils.defaultString(_paths[0]));

        // パス要素を連結
        for (int i = 1; i < _paths.length; ++i) {
            file = new File(file.getPath(), StringUtils.defaultString(_paths[i]));
        }

        // 相対パスの場合は「Context配下」と判断
        //        if (!file.isAbsolute()) {
        //            file = new File(servlet.get("context-path"), file.getPath());
        //            file = new File(MessageUtils.getMessage("path.root"), file.getPath());
        //        }
        file = new File(servlet.get("context-path"), file.getPath());
        MstCodDEntity webInEntity = fileUtils.commonService.getServer();
        file = new File(webInEntity.getCodValue(), file.getPath());
        return file;
    }

    /**
     * ストレージファイルを取得する
     */
    private static Map<String, String> servlet = new HashMap<>();

    public Map<String, String> getServlet() {
        return servlet;
    }

    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }

    public static final File getBatchStorageFile(String... _paths) {

        // ファイルパス生成
        File file = new File(StringUtils.defaultString(_paths[0]));

        // パス要素を連結
        for (int i = 1; i < _paths.length; ++i) {
            file = new File(file.getPath(), StringUtils.defaultString(_paths[i]));
        }

        // 相対パスの場合は「Context配下」と判断
//        if (!file.isAbsolute()) {
//            file = new File(servlet.get("context-path"), file.getPath());
//            file = new File(MessageUtils.getMessage("path.root"), file.getPath());
//        }
        file = new File(servlet.get("context-path"), file.getPath());
        MstCodDEntity webInEntity = fileUtils.commonService.getServer();
        file = new File(webInEntity.getCodValue(), file.getPath());
        return file;
    }

    /**
     * ストレージ絶対パス取得.
     *
     * @param _paths パス構成要素
     * @return ストレージ絶対パス
     */
    public static String getAbsolutePath(String... _paths) {
        // 絶対パスを返す
        return getStorageFile(_paths).getAbsolutePath();
    }

    /**
     * ストレージパス取得.
     *
     * @param _paths パス構成要素
     * @return ストレージパス
     */
    public static String getStoragePath(String... _paths) {
        // コンテキストパスを返す
        return getStorageFile(_paths).getPath();
    }

    /**
     * 相対パス取得.
     *
     * @param _paths パス構成要素
     * @return 相対パス
     */
    public static String getRelativePath(String... _paths) {

        // ファイルパス生成
        File path_ = new File(StringUtils.defaultString(_paths[0]));

        // パス要素を連結
        for (int i = 1; i < _paths.length; ++i) {
            path_ = new File(path_.getPath(), StringUtils.defaultString(_paths[i]));
        }

        // 絶対パスを返す
        return path_.getPath();

    }

    //    /**
    //     * CSVファイルを出力する。
    //     *
    //     * @author PengShiming
    //     *
    //     * @param httpServletResponse
    //     * @param fileName CSVファイル名
    //     * @param contentList csv output DTO collection
    //     * @param header DTO propertyName
    //     * @param contentHeader DTO collection
    //     * @throws SystemException
    //     */
    //    public static void writeCSVFile(HttpServletResponse httpServletResponse, String fileName, List<?> contentList,
    //                                    String[] header, String[] contentHeader) throws SystemException {
    //        ICsvBeanWriter writer = null;
    //        try {
    //            httpServletResponse.setCharacterEncoding("UTF-16LE");
    //            httpServletResponse.setContentType("application/csv;charset=" + FrameworkConstants.ENCODE_TYPE);
    //            fileName = fileName + ".csv";
    //            httpServletResponse.setHeader("Content-Disposition",
    //                            "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-16LE"));
    //
    //            java.io.OutputStreamWriter outWriter = null;
    //            outWriter = new java.io.OutputStreamWriter(httpServletResponse.getOutputStream(), "UTF-16LE");
    //            writer = new CsvBeanWriter(outWriter, CsvPreference.STANDARD_PREFERENCE);
    //            if (null != contentHeader && contentHeader.length > 0) {
    //                writer.writeHeader(contentHeader);
    //            }
    //            if (null != contentList && contentList.size() > 0) {
    //                for (Object o : contentList) {
    //                    writer.write(o, header);
    //                }
    //            }
    //        } catch (Exception e) {
    //            throw new SystemException(e);
    //        } finally {
    //            if (writer != null) {
    //                try {
    //                    writer.close();
    //                } catch (IOException e) {
    //                    throw new SystemException(e);
    //                }
    //            }
    //        }
    //    }

    //    /**
    //     * Excelファイルを出力する。
    //     *
    //     * @author PengShiming
    //     *
    //     * @param httpServletResponse
    //     * @param wb
    //     * @param fileName CSVファイル名
    //     * @throws SystemException
    //     */
    //    public static void writeExelFile(HttpServletResponse httpServletResponse, XSSFWorkbook wb, String fileName)
    //                    throws SystemException {
    //
    //        OutputStream out = null;
    //
    //        try {
    //            fileName = fileName.concat(".xls");
    //            httpServletResponse.setHeader("Content-Disposition",
    //                            "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
    //            httpServletResponse.setContentType("application/vnd.ms-excel; charset=utf-8");
    //            httpServletResponse.setCharacterEncoding("UTF-8");
    //            out = httpServletResponse.getOutputStream();
    //            wb.write(out);
    //            out.close();
    //        } catch (Exception e) {
    //            throw new SystemException(e);
    //        }
    //    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {
            // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                // 删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * zipファイル作成用変数
     */
    protected static byte buf[] = new byte[1024];

    /**
     * zipファイルを作成
     *
     * @param jos
     * @param file 保存先ファイル名
     * @param pathName 作業フォルダパス
     * @param zipFileName ファイル名
     * @throws FileNotFoundException
     * @throws IOException           システム例外
     */
    private static void recurseFiles(ZipOutputStream jos, File file, String pathName, String zipFileName) throws FileNotFoundException, IOException {
        if (file.isDirectory()) {
            pathName = pathName + file.getName() + "/";
            try {
                jos.putNextEntry(new ZipEntry(pathName));
            } catch (IOException e) {
                throw new IOException(e);
            }
            String fileNames[] = file.list();
            if (fileNames != null) {
                // 作業フォルダにファイルをzip圧縮する
                for (int i = 0; i < fileNames.length; i++) {
                    recurseFiles(jos, new File(file, fileNames[i]), pathName, "");
                }
            }
        } else {
            ZipEntry jarEntry = null;
            if (StringUtils.isEmpty(zipFileName)) {
                jarEntry = new ZipEntry(pathName + file.getName());
            } else {
                jarEntry = new ZipEntry(zipFileName);
            }
            if (file.exists() && file.isFile()) {
                FileInputStream fin = null;
                BufferedInputStream in = null;
                try {
                    fin = new FileInputStream(file);
                    in = new BufferedInputStream(fin);
                    jos.putNextEntry(jarEntry);

                    int len = 0;
                    while ((len = in.read(buf)) >= 0) {
                        jos.write(buf, 0, len);
                    }
                } catch (FileNotFoundException e) {
                    throw new FileNotFoundException();
                } catch (IOException e) {
                    throw new IOException(e);
                } finally {

                    try {
                        if (in != null) {
                            in.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fin != null) {
                            fin.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            // ZIP出力ストリームをクローズ
            jos.closeEntry();
        }
    }

    /**
     * zipファイルを作成
     *
     * @param files 保存先ファイルリスト
     * @param zipFile 保存先ファイル名(必ず完全パスを含む)
     * @throws IOException システム例外
     */
    public static void toZip(List<File> files, File zipFile) throws IOException, FileNotFoundException {

        ZipOutputStream jos = null;
        try {
            jos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), 1024));
            for (int i = 0; i < files.size(); i++) {
                recurseFiles(jos, files.get(i), "", "");
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                if (jos != null) {
                    jos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * zipファイルを作成
     *
     * @param files 保存先ファイルリスト 例えば：<String, File>ZIP内ファイル名、ファイル
     * @param zipFile 保存先ファイル名(必ず完全パスを含む)
     * @throws IOException システム例外
     */
    public static void toZip(Map<String, File> files, File zipFile) throws IOException, FileNotFoundException {

        ZipOutputStream jos = null;
        try {
            if (!existsFile(zipFile.getAbsolutePath())) {
                mkdirsParent(zipFile.getAbsolutePath());
            }
            jos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), 1024));
            for (Map.Entry<String, File> entry : files.entrySet()) {
                recurseFiles(jos, entry.getValue(), entry.getValue().getPath(), entry.getKey());
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            try {
                if (jos != null) {
                    jos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ファイル存在チェック
     *
     * @param _path ファイルパス.
     * @return true:存在する
     */
    public static final Boolean existsFile(String _path) {

        // 親フォルダ取得
        File file_ = new File(getAbsolutePath(_path));

        // ファイル存在チェック
        return file_.exists() && file_.isFile();

    }

    /**
     * ファイル名から拡張子を返します。
     *
     * @param _path ファイルパス（例：c\test.csv⇒csv）
     * @return ファイルの拡張子
     */
    public static final String getSuffix(File _path) {

        if (_path.isDirectory()) {
            return null;
        }

        return getSuffix(_path.getName());
    }

    /**
     * ファイル名から拡張子を返します。
     *
     * @param _path ファイルパス（例：/pub/test/test.csv⇒csv）
     * @return ファイルの拡張子
     */
    public static final String getSuffix(String _path) {

        if (StringUtils.isEmpty(_path)) {
            return null;
        }

        int lastDotPosition = _path.lastIndexOf(".");
        if (lastDotPosition != -1) {
            return _path.substring(lastDotPosition + 1);
        }
        return null;
    }

    /**
     * 単一ファイルCopy処理.
     *
     * @param srcFile コピー元ファイル.
     * @param destFile コピー先ファイル.
     * @return コピー先ファイルの絶対パス.
     * @throws IOException
     * @throws IOException システム例外が発生した場合にスローされる
     */
    public static final void copyFile(final File srcFile, final File destFile) throws IOException {
        org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
    }

    //    /**
    //     *
    //     * 機能：ダウンロードファイル。
    //     *
    //     * @author MBPCD : 彭仕明 <br />
    //     * @param　　ファイルの ストレージ<br />
    //     */
    //    public static void downloadFile(String filePath, String fileName) {
    //        try {
    //            final InputStream istr = new BufferedInputStream(new FileInputStream(new File(filePath)));
    //
    //            try {
    //                // ダウンロード
    //                ResponseUtil.download(fileName, istr);
    //            } finally {
    //                if (null != istr) {
    //                    istr.close();
    //                }
    //            }
    //        } catch (final IOException e) {
    //            throw new IORuntimeException(e);
    //        }
    //    }

    /**
     * base64形式の文字列を画像に変換してサーバーに保存し、同時に画像リンクを返します
     * 2020/11/12 huangxinliang modify start
     *
     * @param imgStr base64形式の文字列
     * @param eventId イベントID
     * @param askNumber 質問面談事項数
     * @param stuId 生徒ID
     * @return 画像リンク
     * @throws IOException
     */
    public static String Base64ToImage(String imgStr, int eventId, int askNumber, String stuId) throws IOException {
        // 画像データが空です
        if (StringUtils.isEmpty(imgStr)) {
            return null;
        }
        String realPath = "img" + File.separator + "event" + eventId + File.separator + "ask" + askNumber + File.separator + stuId + ".png";
        // 生成サーバパス（データベースパス）
        String savePath = GakkenConstant.FILE_PATH_PREFIX + realPath;
        File newFile = FileUtils.getStorageFile(MessageUtils.getMessage("path.file"), realPath);
        if (!newFile.exists()) {
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        Base64.Decoder decoder1 = Base64.getDecoder();
        try{
            byte[] bytes = decoder1.decode(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                //異常なデータを調整します
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(newFile);
            out.write(bytes);
            out.flush();
            out.close();
            return savePath;
        }catch (Exception e){
            return savePath;
        }
    }
    //2020/11/12 huangxinliang modify end

    /**
     * <p>ファイルを移動</p>
     *
     * @param pathList ファイルのリスト
     * @param toPath パス
     * @param type 画像またはファイル(画像:imageUrlPrefix ファイル:fileUrlPrefix 動画:videoUrlPrefix)
     * @return 修正前と修正後のパスmap
     */
    public static Map<String, String> getRealPath(List<String> pathList, String toPath, String type) {
        File newFile;
        File oldFile;

        Map<String, String> map = new HashMap<String, String>();
        try {
            String rootPath = new ClassPathResource("/statics").getFile().getPath();
            // コンテキストパス区分　ロール、59　38、39
            String target = GakkenConstant.urlPrefix;
            if (!servlet.isEmpty() && servlet.get("context-path").length() > 1) {
                target += servlet.get("context-path");
            }
            for (String path : pathList) {
                oldFile = new File(rootPath + path.substring(target.length(), path.length()));
                //データベースに保存するパス
                String savePath = GakkenConstant.UEDITOR + File.separator + DateUtils.format(
                        DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_SLASH).replace("/", File.separator) + path.substring(
                        path.lastIndexOf("/"));
                map.put(path, ".." + GakkenConstant.SERVER_FILE_PREFIX + savePath);
                //サーバパスのファイル
                newFile = FileUtils.getStorageFile(MessageUtils.getMessage("path.file"), savePath);
                if (!newFile.exists()) {
                    newFile.getParentFile().mkdirs();
                    newFile.createNewFile();
                }
                FileUtils.copyFile(oldFile, newFile);
                oldFile.delete();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    public static MultipartFile[] generateMultipartFiles(String filePaths){
        String[] filePathArray = filePaths.split(",");
        int fileNumber = filePathArray.length;
        MultipartFile[] multipartFiles = new MultipartFile[fileNumber];
        for (int i = 0; i < fileNumber; i++) {
            try {
                String[] strings = filePathArray[i].split(GakkenConstant.FILE_SEPARATOR_PATTERN);
                /* 2021/09/15 manamiru1-772 cuikl del start */
                String fileName = strings[strings.length - 1];
                /* 2021/09/15 manamiru1-772 cuikl del end */
                String month = strings[strings.length - 2];
                String tempFilePath = null;
                String year = null;
                String noticeType = strings[strings.length - 4];
                year = strings[strings.length - 3];

                //separator + yyyy + separator + mm + separator + filename
                tempFilePath = File.separator + noticeType + File.separator + year + File.separator + month + File.separator + fileName;
                String realPath = "";
                realPath = tempFilePath;
                File destFile = getStorageFile(MessageUtils.getMessage("path.file"), realPath);
                if (destFile.exists()) {
                    multipartFiles[i] = getMulFileByPath(destFile.getPath());
                }
            }catch (ArrayIndexOutOfBoundsException e){
                throw new RRException("ファイルパスエラー");
            }
        }
        return multipartFiles;
    }

    /**
     * MultipartFileに変換
     *
     * @param picPath
     * @return
     */
    public static MultipartFile getMulFileByPath(String picPath) {
        FileItem fileItem = createFileItem(picPath);
        MultipartFile mfile = new CommonsMultipartFile(fileItem);
        return mfile;
    }

    /**
     * FileItemを作成
     *
     * @param filePath
     * @return
     */
    public static FileItem createFileItem(String filePath) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        String[] names = filePath.split(GakkenConstant.FILE_SEPARATOR_PATTERN);
        String name = names[names.length - 1];
        name = name.replaceAll("\\d{17}" + extFile, extFile);
        FileItem item = factory.createItem(textFieldName, "text/plain", true, name);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }
    public static Map<String, File> getFilesDatas(String filePath){
        Map<String, File> files = new HashMap<>();
        File file = new File(filePath);
        File[] filePathLists = file.listFiles();
        if (filePathLists == null){
            return files;
        }
        for (File filePathList : filePathLists) {
            if (filePathList.isFile()) {
                files.put(filePathList.getName(), filePathList);
            }
        }
        return files;
    }
}
