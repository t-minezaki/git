package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csvreader.CsvWriter;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstMessageDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.manager.dao.F08022Dao;
import jp.learningpark.modules.manager.dto.F08022Dto;
import jp.learningpark.modules.manager.service.F08022Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * <p>F08022_未読・未回答者送信一覧画面（インフォメーション） ServiceImpl</p>
 *
 * @author NWT文
 * @version 9.0
 * 変更履歴:
 * 2020/11/3 ： NWT文 ： 新規作成
 */
@Service
public class F08022ServiceImpl implements F08022Service {
    /**
     * メッセージマスタ　Dao
     */
    @Autowired
    MstMessageDao mstMessageDao;

    /**
     * f08020Dao
     */
    @Autowired
    F08022Dao f08022Dao;

    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDetailDao;

    /**
     * @param readDiv 閲覧状況区分
     * @param orgId セッションデータ．組織ＩＤ
     * @param messageId ＩＤ
     * *  @return
     */
    @Override
    public R init(String readDiv, String orgId, Integer messageId) {
        //管理者OR先生情報を取得する
        List<F08022Dto> grsList = f08022Dao.getGrsList(readDiv, messageId, orgId);
        String title = null;
        if (StringUtils.isEmpty(readDiv)) {
            title = "配信総件数";
        } else if (!StringUtils.isEmpty(readDiv)) {
            title = mstCodDetailDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "READING_STS_DIV").eq("cod_cd", readDiv)).getCodValue();
        }
        if (grsList == null || grsList.size() <= 0) {
            // 上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
            R r = R.error();
            r.put("msg", MessageUtils.getMessage("MSGCOMN0017", "インフォメーション"));
            r.put("title", title);
            return r;
        }
        R r = R.ok();
        r.put("objList", grsList);
        r.put("title", title);
        return r;

    }

    /**
     * @param orgId 組織ID
     * @param readDiv 閲覧状況区分
     * @param messageId ＩＤ
     * @param usrIdList 管理者OR先生IDリスト
     * * @return
     */
    @Override
    public File getDownloadFile(String orgId, String readDiv, Integer messageId, List<String> usrIdList) throws IOException {
        //2020/11/19 9.0 wyh add start
        CsvWriter csvWriter = null;
        File outputFile;
        //2020/11/19 9.0 wyh add end
        List<F08022Dto> downloadInfo = f08022Dao.getDownloadInfo(readDiv, messageId, orgId, usrIdList);
        // ファイル

        try {
            // ファイル
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), "R10002_" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".csv");
            outputFile = new File(tempPath);
            if (!outputFile.exists()) {
                boolean mkdirs = outputFile.getParentFile().mkdirs();
                boolean newFile = outputFile.createNewFile();
            }
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                bufferedOutputStream.write(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF});
                csvWriter = new CsvWriter(bufferedOutputStream, ',', StandardCharsets.UTF_8);
            }catch (Exception e){
                e.printStackTrace();
            }
            if (csvWriter == null){
                return null;
            }
            // csv形式
            csvWriter.setTextQualifier('"');
            csvWriter.setForceQualifier(true);
            // 書類の内容
            csvWriter.writeRecord(new String[]{"F08022", "インフォメーション対象リスト", DateUtils.format(new Date(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH)});
            csvWriter.flush();
            csvWriter.writeRecord(new String[]{"インフォメーションＩＤ", "インフォメーション名", "掲載開始日時", "管理者ＩＤ OR 先生ID", "管理者氏名　OR　先生氏名", "ステータス"});
            csvWriter.flush();
            // ファイルの書き込み
            for (F08022Dto f08022Dto : downloadInfo) {
                String status;
                if (StringUtils.equals(f08022Dto.getReadingStsDiv(), "0")) {
                    status = "未読";
                } else {
                    status="既読";
                }
                String[] rowDate = new String[6];
                //お知らせＩＤ
                rowDate[0] = Integer.toString(messageId);
                //お知らせ名
                rowDate[1] = f08022Dto.getMessageTitle();
                //掲載開始日時
                rowDate[2] = f08022Dto.getPubPlanStartDt() + "～";
                //管理者OR先生ID
                rowDate[3] = f08022Dto.getDeliverId();
                //管理者OR先生名
                rowDate[4] = f08022Dto.getUserName();
                //ステータス
                rowDate[5] = status;
                csvWriter.writeRecord(rowDate);
                csvWriter.flush();
            }
            return outputFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (csvWriter != null){
                csvWriter.close();
            }
        }
        //2020/11/18 9.0 wyh modify end
    }
}
