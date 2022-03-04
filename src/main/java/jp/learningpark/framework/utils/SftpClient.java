package jp.learningpark.framework.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import jp.learningpark.framework.gakkenID.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class SftpClient {

    private static Logger logger = LoggerFactory.getLogger(SftpClient.class);

    public static final String localFolder = "/inputCsv/";

    private Session session = null;

    private ChannelSftp sftp = null;

    private static final String PATHSEPARATOR = "/";

    public SftpClient() {

    }

    /**
     * パスワードで登録
     *
     * @param ip
     * @param user
     * @param psw
     * @param port
     * @throws JSchException
     */
    public SftpClient(String ip, String user, String psw, int port) throws JSchException {
        this.connect(ip, user, psw, port);
    }

    /**
     * 鍵で登録
     *
     * @param ip
     * @param user
     * @param psw
     * @param keyFileNm  秘密鍵のフェイル名
     * @param passPhrase 秘密鍵のパスワード
     * @param port
     * @throws JSchException
     */
    public SftpClient(String ip, String user, String psw, String keyFileNm, String passPhrase, int port) throws JSchException, IOException {
        this.connectByKey(ip, user, keyFileNm,passPhrase, port);
    }

    /**
     * パスワードで登録
     *
     * @param ip
     * @param user
     * @param psw
     * @param port
     * @throws JSchException
     */
    public void connect(String ip, String user, String psw, int port) throws JSchException {
        JSch jsch = new JSch();
        if (port <= 0) {
            // サーバに接続し、デフォルトのポートを使う
            session = jsch.getSession(user, ip);
        } else {
            // 指定されたポート接続サーバを使う
            session = jsch.getSession(user, ip, port);
        }

        // サーバに接続失敗であれば、エラーメッセージを表示する
        if (session == null) {
            throw new JSchException("SFTPサーバが接続できません。");
        }

        // ホストに登録するパスワードを設定する
        session.setPassword(psw);
        // 最初のログイン時のメッセージを設定する(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        // ログインタイムアウト時間を設定する
        session.connect(300000);

        Channel channel = session.openChannel("sftp");
        channel.connect(10000000);
        sftp = (ChannelSftp) channel;
    }

    /**
     * 鍵で登録
     *
     * @param ip
     * @param user
     * @param keyFileNm 　秘密鍵のフェイル名
     * @param passPhrase  秘密鍵のパスワード
     * @param port
     * @throws JSchException
     */
    public void connectByKey(String ip, String user, String keyFileNm, String passPhrase, int port) throws JSchException, IOException {
        JSch jsch = new JSch();
        ClassPathResource classPathResource = new ClassPathResource(keyFileNm);

        //秘密鍵
        if(StringUtils.isNotEmpty(passPhrase)){
            jsch.addIdentity(classPathResource.getFile().getPath(), passPhrase);
        }else{
            jsch.addIdentity(classPathResource.getFile().getPath());
        }

        if (port <= 0) {
            // サーバに接続し、デフォルトのポートを使う
            session = jsch.getSession(user, ip);
        } else {
            // 指定されたポート接続サーバを使う
            session = jsch.getSession(user, ip, port);
        }

        // サーバに接続失敗であれば、エラーメッセージを表示する
        if (session == null) {
            throw new JSchException("SFTPサーバが接続できません。");
        }

        // 最初のログイン時のメッセージを設定する(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        // ログインタイムアウト時間を設定する
        session.connect(300000);

        Channel channel = session.openChannel("sftp");
        channel.connect(10000000);
        sftp = (ChannelSftp) channel;
    }


    /**
     * SFTPサーバへの接続を切る
     */
    public void disConnect() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
        if (sftp != null && sftp.isConnected()) {
            sftp.disconnect();
        }
    }

    /**
     * 指定されたディレクトリのすべてのファイルを読み込みし、保存する
     * ファイルのみ
     *
     * @param sPath フォルダパス
     */
    @SuppressWarnings("unchecked")
    public Map<String, File> readFromFolder(String sPath) {

        Map<String, File> result = new HashMap<>();
        try {
            try {
                sftp.cd(sPath);
            } catch (SftpException e) {

                sftp.mkdir(sPath);
                sftp.cd(sPath);
            }
            Vector<ChannelSftp.LsEntry> listFiles = sftp.ls(sftp.pwd());

            File localfile = new File(localFolder + sPath);
            if (!localfile.exists()) {
                localfile.mkdirs();
            }
            for (ChannelSftp.LsEntry file : listFiles) {
                String fileName = file.getFilename();
                if (file.getAttrs().isDir() || StringUtils.equals(".", fileName) || StringUtils.equals("..", fileName)) {
                    continue;
                }
                File localFile = new File(localfile, fileName);
                if (!localFile.exists()) {
                    localFile.createNewFile();
                }
                try {
                    InputStream inputStream = sftp.get(sftp.pwd() + "/" + fileName);
                    FileUtils.copyInputStreamToFile(inputStream, localFile);
                    /* 2021/09/15 manamiru1-772 cuikl edit start */
                    logger.debug("ホストフォルダバス「" + session.getHost() + sPath + "」のフェイル「" + file.getFilename() + "」はローカルフェイル「" + localFile.getName() +
                            "」に移動成功しました。");
                    /* 2021/09/15 manamiru1-772 cuikl edit end */
                    result.put(fileName, localFile);
                    /* 2021/09/16 manamiru1-772 cuikl del start */
                    /* 2021/09/16 manamiru1-772 cuikl del end */
                } catch (SftpException e) {
                    logger.error(e.getMessage());
                    logger.error("ホストフォルダバス「" + session.getHost() + sPath + "」のフェイル「" + file.getFilename() + "」はローカルフェイル「" + localFile.getName() +
                            "」に移動失敗しました。");

                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 指定されたディレクトリのすべてのファイルを読み込みし、保存する
     * フォルダとサブファイルを含む
     *
     * @param sPath フォルダパス
     */
    @SuppressWarnings("unchecked")
    public Map<String, File> readAllFromFolder(String sPath) {
        Map<String, File> result = new HashMap<>();
        try {
            try {
                sftp.cd(sPath);
            } catch (SftpException e) {

                sftp.mkdir(sPath);
                sftp.cd(sPath);
            }
            Vector<ChannelSftp.LsEntry> listFiles = sftp.ls(sftp.pwd());

            File localfile = new File(localFolder + sPath);
            if (!localfile.exists()) {
                localfile.mkdirs();
            }
            for (ChannelSftp.LsEntry file : listFiles) {
                String fileName = file.getFilename();
                if (StringUtils.equals(".", fileName) || StringUtils.equals("..", fileName)) {
                    continue;
                }
                File localFile = new File(localfile, fileName);

                if (file.getAttrs().isDir()) {
                    if (!localFile.exists()) {
                        localFile.mkdirs();
                    }
                } else {
                    if (!localFile.exists()) {
                        localFile.createNewFile();
                    }
                    try {
                        InputStream inputStream = sftp.get(sftp.pwd() + "/" + fileName);
                        FileUtils.copyInputStreamToFile(inputStream, localFile);
                        /* 2021/09/15 manamiru1-772 cuikl edit start */
                        logger.debug("ホストフォルダバス「" + session.getHost() + sPath + "」のフェイル「" + file.getFilename() + "」はローカルフェイル「" + localFile.getName() +
                                "」に移動成功しました。");
                        /* 2021/09/15 manamiru1-772 cuikl edit end */
                    } catch (SftpException e) {
                        logger.error(e.getMessage());
                        logger.error("ホストフォルダバス「" + session.getHost() + sPath + "」のフェイル「" + file.getFilename() + "」はローカルフェイル「" + localFile.getName() +
                                "」に移動失敗しました。");
                    }
                }

                result.put(fileName, localFile);
                /* 2021/09/16 manamiru1-772 cuikl del start */
                /* 2021/09/16 manamiru1-772 cuikl del end */

            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }


    /**
     * 指定されたディレクトリのすべてのファイルを読み込みして、ローカルフォルダバスに保存する
     * フォルダとサブファイルを含む
     *
     * @param sPath     フォルダパス
     * @param localPath ローカルフォルダパス
     * @return true:成功/fasle:失敗
     */
    @SuppressWarnings("unchecked")
    public boolean readFromFolderToLocalPath(String sPath, String localPath) {
        try {
            try {
                sftp.cd(sPath);
            } catch (SftpException e) {
                //フォルダパスは存在しない
                return true;
            }
            Vector<ChannelSftp.LsEntry> listFiles = sftp.ls(sftp.pwd());

            File localfile = new File(localFolder + localPath);
            if (!localfile.exists()) {
                localfile.mkdirs();
            }
            for (ChannelSftp.LsEntry file : listFiles) {
                // Check if it is a file (not a directory).
                if (!file.getAttrs().isDir()) {
                    // Download only if changed later.
                    File newFile = new File(localPath + PATHSEPARATOR + file.getFilename());
                    if (!newFile.exists()) {
                        newFile.createNewFile();
                    }
                    try {
                        InputStream inputStream = sftp.get(sPath + PATHSEPARATOR + file.getFilename());
                        FileUtils.copyInputStreamToFile(inputStream, newFile);
                        /* 2021/09/16 manamiru1-772 cuikl del start */
                        /* 2021/09/16 manamiru1-772 cuikl del end */
                        /* 2021/09/15 manamiru1-772 cuikl edit start */
                        logger.debug("ホストフォルダバス「" + session.getHost() + File.separator + sPath + "」のフェイル「" + file.getFilename() + "」はローカルフェイル「" + localPath +
                                "」に移動成功しました。");
                        /* 2021/09/15 manamiru1-772 cuikl edit end */
                    } catch (SftpException e) {
                        logger.error(e.getMessage());
                        logger.error("ホストフォルダバス「" + session.getHost() + File.separator + sPath + "」のフェイル「" + file.getFilename() + "」はローカルフェイル「" + localPath +
                                "」に移動失敗しました。");
                        e.printStackTrace();
                    }
                } else if (!(".".equals(file.getFilename()) || "..".equals(file.getFilename()))) {
                    // Empty folder copy.
                    new File(localPath + PATHSEPARATOR + file.getFilename()).mkdirs();
                    // Enter found folder on server to read its contents and create locally.
                    readFromFolderToLocalPath(
                            sPath + PATHSEPARATOR + file.getFilename(),
                            localPath + PATHSEPARATOR + file.getFilename());
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    /**
     * ローカルフォルダバスの全てフェイルは指定されたホストフォルダバスに保存する
     * フェイルのみ
     *
     * @param localPath ローカルフォルダパス
     * @param sPath     フォルダパス
     * @return true:成功/fasle:失敗
     */
    @SuppressWarnings("unchecked")
    public boolean localFolderTransToHostFolder(String localPath, String sPath) {
        try {
            try {
                sftp.cd(sPath);
            } catch (SftpException e) {

                sftp.mkdir(sPath);
                sftp.cd(sPath);
            }

            File localfile = new File(localPath);
            if (!localfile.exists()) {
                return true;
            }
            for (File file : localfile.listFiles()) {
                String fileName = file.getName();
                if (StringUtils.equals(".", fileName) || StringUtils.equals("..", fileName) || file.isDirectory()) {
                    continue;
                }
                boolean transFlg = upLoadFile(file, sPath);
                if (!transFlg) {
                    logger.error("ローカルフェイル「" + localPath + File.separator + fileName + "」はホストフォルダバス「" + session.getHost() + File.separator + sPath +
                            "」に移動失敗しました。");
                    return false;
                } else {
                    logger.error("ローカルフェイル「" + localPath + File.separator + fileName + "」はホストフォルダバス「" + session.getHost() + File.separator + sPath +
                            "」に移動成功しました。");
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return true;
    }

    /**
     * 指定されたディレクトリファイルを読む
     *
     * @param sPath フォルダパス
     * @param fPath ファイル名
     */
    public File readFile(String sPath, String fPath) {

        File localFile = null;
        try {
            try {
                sftp.cd(sPath);
            } catch (SftpException e) {
                sftp.mkdir(sPath);
                sftp.cd(sPath);
            }
            File localfolder = new File(localFolder + sPath);
            if (!localfolder.exists()) {
                localfolder.mkdirs();
            }
            localFile = new File(localfolder, fPath);
            if (!localFile.exists()) {
                localFile.createNewFile();
            }
            try {
                FileUtils.copyInputStreamToFile(sftp.get(fPath), localFile);

            } catch (SftpException e) {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return localFile;
    }

    /**
     * ローカルフェイルは指定されたホストフォルダバスに保存する
     *
     * @param localPath ローカルフェイパス
     * @param sPath     フォルダパス
     * @return true:成功/fasle:失敗
     */
    @SuppressWarnings("unchecked")
    public boolean localFileToHost(String localPath, String sPath) {
        File localFile = new File(localPath);
        if (localFile.exists()) {
            return upLoadFile(localFile, sPath);
        }
        return true;
    }

    /**
     * 指定されたファイルはsftpホストフォルダのパスに移動すること
     *
     * @param file  移動されたファイル
     * @param sPath sftpホストフォルダのパス
     * @return
     */
    public boolean upLoadFile(File file, String sPath) {
        try {
            try {
                sftp.cd(sPath);
            } catch (SftpException e) {
                //マルチレベルディレクトリ
                String[] dirs = sPath.split("/");
                String tempPath = "";
                for (String dir : dirs) {
                    if (StringUtils.isNotEmpty(dir)) {
                        tempPath += "/" + dir;
                        try {
                            sftp.cd(tempPath);
                        } catch (Exception e1) {
                            sftp.mkdir(tempPath);
                            sftp.cd(tempPath);
                        }
                    }
                }
            }
            copyFile(file, sftp.pwd());
            /* 2021/09/15 manamiru1-772 cuikl edit start */
            logger.debug("ローカルフェイル「" + file.getAbsoluteFile() + "」はホスト「" + session.getHost() + "」のフォルダ「" + sPath + "」に移動成功しました。");
            /* 2021/09/15 manamiru1-772 cuikl edit end */
        } catch (Exception e) {
            logger.error("ローカルフェイル「" + file.getAbsoluteFile() + "」はホスト「" + session.getHost() + "」のフォルダ「" + sPath + "」に移動失敗しました。");
            e.printStackTrace();
            logger.error(e.getMessage());


            return false;
        }
        return true;
    }

    /**
     * 指定されたフォルダの全てフェイル/ファイルはsftpホストフォルダのパスに移動すること
     *
     * @param file  フォルダ/ファイル
     * @param sPath 　ホストフォルダバス
     */
    private void copyFile(File file, String sPath) {

        if (file.isDirectory()) {
            File[] list = file.listFiles();
            try {
                try {
                    String fileName = file.getName();
                    sftp.cd(sPath);
                    sftp.mkdir(fileName);
                } catch (Exception e) {
                }
                sPath = sPath + "/" + file.getName();
                try {

                    sftp.cd(file.getName());
                } catch (SftpException e) {
                    logger.error(e.getMessage());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            for (int i = 0; i < list.length; i++) {
                copyFile(list[i], sPath);
            }
        } else {

            try {
                sftp.cd(sPath);

            } catch (SftpException e1) {
                e1.printStackTrace();
            }
            InputStream instream = null;
            OutputStream outstream = null;
            try {
                outstream = sftp.put(file.getName());
                instream = new FileInputStream(file);

                byte b[] = new byte[1024];
                int n;
                try {
                    while ((n = instream.read(b)) != -1) {
                        outstream.write(b, 0, n);
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }

            } catch (SftpException e) {
                logger.error(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    outstream.flush();
                    outstream.close();
                    instream.close();

                } catch (Exception e2) {
                    logger.error(e2.getMessage());
                }
            }
        }
    }

//    /**
//     * SFTPホスト1のフォルダバスの全てフェイルはSFTPホスト2のフォルダバスに転送すること
//     *
//     * @param sftpClientFrom SFTPホスト1
//     * @param sftpClientTo   SFTPホスト2
//     * @param fromPath       SFTPホスト1のフォルダバス(移動元)
//     * @param toPath         　SFTPホスト2のフォルダバス(移動先)
//     * @return
//     */
//    public static OutPutDto transOnlyFiles(SftpClient sftpClientFrom, SftpClient sftpClientTo, String fromPath, String toPath, String errMsg,String ip) {
//        OutPutDto outPutDto = new OutPutDto();
//        int normalCount = 0;
//        int errorCount = 0;
//        int allCount = 0;
//        boolean sucessFlg = true;
//        Map<String, File> fromFiles = null;
//        try {
//            fromFiles = sftpClientFrom.readFromFolder(fromPath);
//            boolean transFlg = false;
//            for (Map.Entry<String, File> entry : fromFiles.entrySet()) {
//                transFlg = sftpClientTo.upLoadFile(entry.getValue(), toPath);
//                if (!transFlg) {
//                    errorCount++;
//                    sucessFlg = false;
//                    errMsg += "ローカルフェイル「" + entry.getValue().getAbsolutePath() + "」はホスト「" + ip + "」のフォルダ「" + toPath + "」に移動失敗しました。";
//                } else {
//                    entry.getValue().delete();
//                    normalCount++;
//                }
//                allCount++;
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            sucessFlg = false;
//        }
//        outPutDto.setSuccess(sucessFlg);
//        outPutDto.setAllCount(allCount);
//        outPutDto.setErrorCount(errorCount);
//        outPutDto.setNormalCount(normalCount);
//        return outPutDto;
//    }

    /**
     * 一時ディレクトリの削除
     *
     * @param path
     */
    public static void deleteTempFolder(String path) {
        File tempFolder = null;
        if (path.indexOf("/", 2) != -1) {
            tempFolder = new File(localFolder + path.substring(0, path.indexOf("/", 2)));
        } else {
            tempFolder = new File(localFolder+path);
        }

        if (tempFolder.exists()) {
            deleteFile(tempFolder);
        }
    }

    /**
     * ファイルとサブファイルを削除
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        File[] files = file.listFiles();
        for (File f : files) {
            String name = file.getName();
            /* 2021/09/16 manamiru1-772 cuikl edit start */
            logger.info("ローカルフォルダ「" + name + "」を削除しました。");
            /* 2021/09/16 manamiru1-772 cuikl edit end */
            if (f.isDirectory()) {
                deleteFile(f);
            } else {
                f.delete();
            }
        }
        file.delete();
    }

    /**
     * SFTPホスト1のフォルダバスの全てフェイル又はフォルダはSFTPホスト1のフォルダバスに転送すること
     *
     * @param sftpClientFrom SFTPホスト1
     * @param sftpClientTo   SFTPホスト2
     * @param fromPath       SFTPホスト1のフォルダバス(移動元)
     * @param toPath         　SFTPホスト2のフォルダバス(移動先)
     * @return
     */
    public static boolean transFilesContainsFolder(SftpClient sftpClientFrom, SftpClient sftpClientTo, String fromPath, String toPath) {
        Map<String, File> fromFiles = null;
        try {
            fromFiles = sftpClientFrom.readAllFromFolder(fromPath);
            boolean transFlg = false;
            for (Map.Entry<String, File> entry : fromFiles.entrySet()) {
                //フォルダ
                if (entry.getValue().isDirectory()) {
                    transFilesContainsFolder(sftpClientFrom, sftpClientTo, fromPath + "/" + entry.getValue().getName(),
                            toPath + "/" + entry.getValue().getName());
                }
                //フェイル
                else {
                    transFlg = sftpClientTo.upLoadFile(entry.getValue(), toPath);
                    if (!transFlg) {
                        return false;
                    } else {
                        entry.getValue().delete();
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * SFTPホスト1の一つのフェイルはSFTPホスト1のフォルダバスに転送して、
     * SFTPホスト1のフェイルを削除
     *
     * @param sftpClientFrom SFTPホスト1
     * @param sftpClientTo   SFTPホスト2
     * @param filePath       SFTPホスト1のフェイル(移動元)
     * @param toPath         　SFTPホスト2のフォルダバス(移動先)
     * @return
     */
    public static boolean transOneFileToOtherHost(SftpClient sftpClientFrom, SftpClient sftpClientTo, String filePath, String toPath) {
        //フォルダバス
        String path = filePath.substring(0, filePath.lastIndexOf("/"));
        //フェイル名
        String fileNm = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());

        File fromFile = null;
        try {
            fromFile = sftpClientFrom.readFile(path, fileNm);
            boolean transFlg = false;
            if (fromFile != null && fromFile.exists()) {
                transFlg = sftpClientTo.upLoadFile(fromFile, toPath);
                if (!transFlg) {
                    return false;
                }
                //移動元のフェイルを削除
                else {
                    sftpClientFrom.deleteHostFile(filePath);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * SFTPホストの一つのフェイルは削除する
     *
     * @param filePath SFTPホストのフェイル(移動元)
     * @return
     */
    public boolean deleteHostFile(String filePath) {
        //フォルダバス
        String path = filePath.substring(0, filePath.lastIndexOf("/"));
        //フェイル名
        String fileNm = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());

        try {
            sftp.cd(path);
        } catch (SftpException e) {
            //バスは存在しない
            logger.error(e.getMessage());
            return true;
        }

        try {
            sftp.rm(fileNm);
        } catch (Exception e1) {
            logger.error(e1.getMessage());
            return false;
        }
        return true;
    }

}
