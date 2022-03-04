package jp.learningpark.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.job.dao.BTGKA1003Dao;
import jp.learningpark.modules.job.entity.BTGKA1003Dto;
import jp.learningpark.modules.job.service.BTGKA1003Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("BTGKA1003Service")
@Component
//@ConfigurationProperties(prefix = "server")
public class BTGKA1003ServiceImpl implements BTGKA1003Service {
    @Autowired
    BTGKA1003Dao btgka1003Dao;
    @Autowired
    MstCodDService mstCodDService;

//    private String server;
    /**
     * logger
     */
    Logger logger = LoggerFactory.getLogger("BTGKA1003Controller");

    private Map<String, String> servlet = new HashMap<>();

    public Map<String, String> getServlet() {
        return servlet;
    }

    public void setServlet(Map<String, String> servlet) {
        this.servlet = servlet;
    }

    @Override
    public List<BTGKA1003Dto> getUserList(String sysTime, String roleType, Integer delType) {
        return btgka1003Dao.getUserList(sysTime, roleType, delType);
    }

    @Override
    public List<BTGKA1003Dto> getOrgList(String sysTime, Integer delType) {
        return btgka1003Dao.getOrgList(sysTime, delType);
    }

    @Override
    public List<BTGKA1003Dto> getOrgAllList(String sysTime, Integer delType) {
        return btgka1003Dao.getOrgAllList(sysTime, delType);
    }

    @Override
    public List<BTGKA1003Dto> getPwList(String sysTime, Integer delType) {
        return btgka1003Dao.getPwList(sysTime, delType);
    }

    @Override
    public R downloadCSV(String codCd, List<BTGKA1003Dto> userList, String roleType) {
        try {
            //CSV出力パスを取る
            MstCodDEntity mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_value").eq("cod_key", "MNMRCSV_DOWNLOADPATH_LIST").eq("cod_cd", codCd).eq("del_flg", 0));
            String csvPath = mstCodDEntity.getCodValue();
            // CSV出タイプ
            String csvType = DateUtils.getSysTimestamp().toString() + csvPath;
            // ファイル名
            String fileName = csvType + ".csv";
            // 生成パス
            String savePath = File.separator + fileName;
            // ファイル
            File csvFile = FileUtils.getBatchStorageFile(MessageUtils.getMessage("path.excel"), savePath);
            // 相対パスの場合は「Context配下」と判断
            if (!csvFile.exists()) {
                csvFile.getParentFile().mkdirs();
                csvFile.createNewFile();
            }
            FileWriter fw = new FileWriter(csvFile);
            // csv形式
            fw.write(new String(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}));
            // 書類の内容
            String header = "";
            // ファイルの書き込み
            StringBuffer str = new StringBuffer();
            if (StringUtils.equals(roleType, "1")) {
                header = "\t管理者（変更後ユーザーID）\t" + "," + "\tパスワード\t" + "," + "\t名前\t" + "," + "\tかな/カナ\t" + "," + "\tメールアドレス\t" + "," + "\t組織コード\t" + "," + "\t所属長\t"
                        + "," + "\t入社年月日\t" + "," + "\tマスタコード1\t" + "," + "\tマスタコード2\t" + "," + "\tマスタコード3\t" + "," + "\tマスタコード4\t" + "," + "\tマスタコード5\t" + "," + "\tロールコード\t"
                        + "," + "\tユーザーID\t" + "," + "\t電話番号\t" + "," + "\t特殊\t" + "," + "\t追加情報3\t" + "," + "\t追加情報4\t" + "," + "\t追加情報5\t" + "," + "\t追加情報6\t"
                        + "," + "\t追加情報7\t" + "," + "\t追加情報8\t" + "," + "\t追加情報9\t" + "," + "\t追加情報10\t" + "," + "\tログイン期限\t" + "," + "\t有効／無効\t" + "," + "\tアクセス許可IPアドレス\t"
                        + "," + "\t保有ポイント\t" + "," + "\t登録方法\t" + "\n";
                for (BTGKA1003Dto dto : userList) {
                    str.append("\t" + dto.getAfterUsrId() + "\t" + "," + "\t" + dto.getUsrPassword() + "\t" + "," + "\t" + dto.getUserName() + "\t" + "," + "\t" + dto.getUserKName() + "\t" + "," + "\t" + dto.getMailad()
                            + "\t" + "," + "\t" + dto.getOrgId() + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "管理者" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + dto.getTelnum() + "\t" + "\t" + "," + "\t" + dto.getSpecAuth() + "\t"
                            + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + ""
                            + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + "" + "\t" + "\n");
                }
            } else if (StringUtils.equals(roleType, "2")) {
                header = "\t先生（変更後ユーザーID）\t" + "," + "\tパスワード\t" + "," + "\t名前\t" + "," + "\tかな/カナ\t" + "," + "\tメールアドレス\t" + "," + "\t組織コード\t" + "," + "\t所属長\t"
                        + "," + "\t入社年月日\t" + "," + "\tマスタコード1\t" + "," + "\tマスタコード2\t" + "," + "\tマスタコード3\t" + "," + "\tマスタコード4\t" + "," + "\tマスタコード5\t" + "," + "\tロールコード\t"
                        + "," + "\tユーザーID\t" + "," + "\t電話番号\t" + "," + "\t追加情報2\t" + "," + "\t追加情報3\t" + "," + "\t追加情報4\t" + "," + "\t追加情報5\t" + "," + "\t追加情報6\t"
                        + "," + "\t追加情報7\t" + "," + "\t追加情報8\t" + "," + "\t追加情報9\t" + "," + "\t追加情報10\t" + "," + "\tログイン期限\t" + "," + "\t有効／無効\t" + "," + "\tアクセス許可IPアドレス\t"
                        + "," + "\t保有ポイント\t" + "," + "\t登録方法\t" + "\n";
                for (BTGKA1003Dto dto : userList) {
                    str.append("\t" + dto.getAfterUsrId() + "\t" + "," + "\t" + dto.getUsrPassword() + "\t" + "," + "\t" + dto.getUserName() + "\t" + "," + "\t" + dto.getUserKName() + "\t" + "," + "\t" + dto.getMailad()
                            + "\t" + "," + "\t" + dto.getOrgId() + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "先生" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + dto.getTelnum() + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + ""
                            + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + "" + "\t" + "\n");
                }

            } else if (StringUtils.equals(roleType, "3")) {
                header = "\t保護者（変更後ユーザーID）\t" + "," + "\tパスワード\t" + "," + "\t名前\t" + "," + "\tかな/カナ\t" + "," + "\tメールアドレス\t" + "," + "\t組織コード\t" + "," + "\t所属長\t"
                        + "," + "\t入社年月日\t" + "," + "\tマスタコード1\t" + "," + "\tマスタコード2\t" + "," + "\tマスタコード3\t" + "," + "\tマスタコード4\t" + "," + "\tマスタコード5\t" + "," + "\tロールコード\t"
                        + "," + "\tユーザーID\t" + "," + "\t電話番号\t" + "," + "\t続柄区分\t" + "," + "\t郵便番号\t" + "," + "\t住所１\t" + "," + "\t住所２\t" + "," + "\t追加情報6\t"
                        + "," + "\t追加情報7\t" + "," + "\t追加情報8\t" + "," + "\t追加情報9\t" + "," + "\t追加情報10\t" + "," + "\tログイン期限\t" + "," + "\t有効／無効\t" + "," + "\tアクセス許可IPアドレス\t"
                        + "," + "\t保有ポイント\t" + "," + "\t登録方法\t" + "\n";
                for (BTGKA1003Dto dto : userList) {
                    str.append("\t" + dto.getAfterUsrId() + "\t" + "," + "\t" + dto.getUsrPassword() + "\t" + "," + "\t" + dto.getUserName() + "\t" + "," + "\t" + dto.getUserKName() + "\t" + "," + "\t" + dto.getMailad()
                            + "\t" + "," + "\t" + dto.getOrgId() + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "保護者" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + dto.getTelnum() + "\t" + "\t" + "," + "\t" + dto.getReltnspDiv() + "\t"
                            + "\t" + "," + "\t" + dto.getEmail() + "\t" + "\t" + "," + "\t" + dto.getAdr1() + "\t" + "\t" + "," + "\t" + dto.getAdr2() + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + ""
                            + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + "" + "\t" + "\n");
                }

            } else if (StringUtils.equals(roleType, "4")) {
                header = "\t生徒（変更後ユーザーID）\t" + "," + "\tパスワード\t" + "," + "\t名前\t" + "," + "\tかな/カナ\t" + "," + "\tメールアドレス\t" + "," + "\t組織コード\t" + "," + "\t所属長\t"
                        + "," + "\t入社年月日\t" + "," + "\tマスタコード1\t" + "," + "\tマスタコード2\t" + "," + "\tマスタコード3\t" + "," + "\tマスタコード4\t" + "," + "\tマスタコード5\t" + "," + "\tロールコード\t"
                        + "," + "\tユーザーID\t" + "," + "\tユーザーID\t" + "," + "\t保護者ID\t" + "," + "\t複数保護者ID\t" + "," + "\t性別区分\t" + "," + "\t生年月日\t" + "," + "\t学年区分\t"
                        + "," + "\t二次元コード\t" + "," + "\tオリジナルCD\t" + "," + "\t追加情報9\t" + "," + "\t追加情報10\t" + "," + "\tログイン期限\t" + "," + "\t有効／無効\t" + "," + "\tアクセス許可IPアドレス\t"
                        + "," + "\t保有ポイント\t" + "," + "\t登録方法\t" + "\n";
                for (BTGKA1003Dto dto : userList) {
                    str.append("\t" + dto.getAfterUsrId() + "\t" + "," + "\t" + dto.getUsrPassword() + "\t" + "," + "\t" + dto.getUserName() + "\t" + "," + "\t" + dto.getUserKName() + "\t" + "," + "\t" + dto.getMailad()
                            + "\t" + "," + "\t" + dto.getOrgId() + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "生徒" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + dto.getGuardId() + "\t"
                            + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + dto.getGender() + "\t" + "\t" + "," + "\t" + dto.getBirthd() + "\t" + "," + "\t" + dto.getSchy() + "\t" + "\t" + "," + "\t" + "" + "\t"
                            + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + ""
                            + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + "" + "\t" + "\n");
                }

            } else {
                if (StringUtils.equals(codCd, "8") || StringUtils.equals(codCd, "9")) {
                    header = "\t組織コード\t" + "," + "\t組織名\t" + "," + "\t組織略名\t" + "," + "\t説明\t" + "," + "\t親組織コード\t" + "," + "\t所属長\t" + "," + "\t組織進捗管理者1\t"
                            + "," + "\t組織進捗管理者2\t" + "," + "\t組織進捗管理者3\t" + "," + "\t組織進捗管理者4\t" + "," + "\t組織進捗管理者5\t" + "," + "\t組織の権限\t" + "," + "\tユーザの権限\t" + "," + "\t運用権限委譲\t"
                            + "," + "\t承認権限委譲\t" + "," + "\t登録方法\t" + "," + "\t組織進捗管理者\t" + "," + "\tマスタコード1\t" + "," + "\tマスタコード2\t" + "," + "\tマスタコード3\t" + "," + "\tマスタコード4\t"
                            + "," + "\tマスタコード5\t" + "," + "\t変更後ユーザー(組織)ID\t" + "," + "\t所属階層\t" + "," + "\tブランドコード\t" + "," + "\t特権管理者　かな/カナ\t" + "," + "\t特権管理者電話番号\t" + "," + "\t特権管理者メール\t"
                            + "," + "\t追加情報7\t" + "," + "\t追加情報8\t" + "," + "\t追加情報9\t" + "," + "\t追加情報10\t" + "," + "\t追加情報11\t"
                            + "," + "\t追加情報12\t" + "," + "\t追加情報13\t" + "," + "\t追加情報14\t" + "," + "\t追加情報15\t" + "," + "\t追加情報16\t"
                            + "," + "\t追加情報17\t" + "," + "\t追加情報18\t" + "," + "\t追加情報19\t" + "," + "\t追加情報20\t" + "\n";
                    for (BTGKA1003Dto dto : userList) {
                        str.append("\t" + dto.getOrgId() + "\t" + "," + "\t" + dto.getOrgNm() + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + "" + "\t" + "," + "\t" + dto.getUpplevOrgId()
                                + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + dto.getUserName() + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                                + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + dto.getOrgType() + "\t" + "\t" + "," + "\t" + dto.getSpecAuth() + "\t"
                                + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                                + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t"
                                + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + dto.getOrgId() + "\t" + "\t" + "," + "\t" + dto.getLevel() + "\t" + "\t" + "," + "\t" + dto.getBrandCd() + "\t"
                                + "\t" + "," + "\t" + dto.getUserKName() + "\t" + "\t" + "," + "\t" + dto.getTelnum() + "\t" + "\t" + "," + "\t" + dto.getMailad() + "\t" + "\t" + "," + "\t" + "" + "\t"
                                + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\t" + "," + "\t" + "" + "\t" + "\n");
                    }
                }
                if (StringUtils.equals(codCd, "10") || StringUtils.equals(codCd, "11")) {
                    header = "\t組織コード\t" + "," + "\t削除フラグ\t" + "," + "\t変更後ユーザーID\t" + "," + "\tロール区分\t" + "," + "\tユーザステータス\t" + "\n";
                    for (BTGKA1003Dto dto : userList) {
                        str.append("\t" + dto.getOrgId() + "\t" + "," + "\t" + dto.getDel() + "\t" + "," + "\t" + dto.getAfterUsrId() + "\t" + "," + "\t" + dto.getRole() + "\t" + "," + "\t" + "" + "\n");
                    }
                }
                if (StringUtils.equals(codCd, "12") || StringUtils.equals(codCd, "13")) {
                    header = "\tユーザID\t" + "," + "\tロール区分\t" + "," + "\tユーザ初期PW\t" + "," + "\tシステム区分\t" + "," + "\t削除フラグ\t" + "\n";
                    for (BTGKA1003Dto dto : userList) {
                        str.append("\t" + dto.getUsrId() + "\t" + "," + "\t" + dto.getRole() + "\t" + "," + "\t" + dto.getUsrFstPassword() + "\t" + "," + "\t" + dto.getSysDiv() + "\t" + "," + "\t" + dto.getDel() + "\n");
                    }
                }
            }
            fw.write(header);
            fw.write(str.toString());
            fw.flush();
            fw.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
        return R.ok();
    }
}
