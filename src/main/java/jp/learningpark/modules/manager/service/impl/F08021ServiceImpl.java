package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csvreader.CsvWriter;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.manager.dao.F08021Dao;
import jp.learningpark.modules.manager.dto.F08021Dto;
import jp.learningpark.modules.manager.service.F08021Service;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)wyh
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/9 ： NWT)wyh ： 新規作成
 * @date 2020/11/9 18:55
 */
@Service
public class F08021ServiceImpl implements F08021Service {
    /**
     * f08021Dao
     */
    private final F08021Dao f08021Dao;
    /**
     * mstCodDDao
     */
    private final MstCodDDao mstCodDDao;

    public F08021ServiceImpl(F08021Dao f08021Dao, MstCodDDao mstCodDDao) {
        this.f08021Dao = f08021Dao;
        this.mstCodDDao = mstCodDDao;
    }

    @Override
    public R init(String openDiv,  String readDiv, String orgId, Integer messageId) {
        List<F08021Dto> grsList = f08021Dao.getGrsList(openDiv, readDiv, messageId, orgId);
        String title = null;
        if (StringUtils.isEmpty(openDiv) && StringUtils.isEmpty(readDiv)){
            title = "配信総件数";
        }else if (!StringUtils.isEmpty(openDiv)){
            title = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "OPEN_STS_DIV").eq("cod_cd", openDiv)).getCodValue();
        }else if (!StringUtils.isEmpty(readDiv)){
            title = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "READING_STS_DIV").eq("cod_cd", readDiv)).getCodValue();
        }
        if (grsList == null || grsList.size() <= 0) {
            // 上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
            R r = R.error();
            r.put("msg", MessageUtils.getMessage("MSGCOMN0017", "メッセージ"));
            r.put("title", title);
            return r;
        }
        R r = R.ok();
        r.put("objList", grsList);
        r.put("title", title);
        return r;
    }


    @Override
    public File getDownloadFile(String orgId,  String openDiv, String readDiv, Integer messageId, List<String> stuIdList) {
        //2020/11/19 9.0 wyh add start
        CsvWriter csvWriter = null;
        File outputFile;
        //2020/11/19 9.0 wyh add end
        List<F08021Dto> downloadInfo = f08021Dao.getDownloadInfo(openDiv, readDiv, messageId, orgId, stuIdList);
        //2020/11/19 9.0 wyh modify start
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
            // ヘッダーを書き込む
            csvWriter.writeRecord(new String[]{"F08021", "メッセージ対象リスト", DateUtils.format(new Date(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH)});
            csvWriter.flush();
            csvWriter.writeRecord(new String[]{"メッセージＩＤ", "メッセージ名", "掲載開始日時", "生徒ID", "生徒名", "ステータス"});
            csvWriter.flush();

            // ファイルの書き込み
            for (F08021Dto f08021Dto : downloadInfo) {
                //ステータス
                String status;
                if (StringUtils.equals(f08021Dto.getReadingStsDiv(), "0")) {
                    status = "未読";
                } else {
                    if (StringUtils.equals(f08021Dto.getOpenedFlg(), "0")) {
                        status = "既読未開封";
                    } else {
                        status = "既読開封済";
                    }
                }
                String[] rowDate = new String[6];
                //メッセージID
                rowDate[0] = Integer.toString(messageId);
                //メッセージTitle
                rowDate[1] = f08021Dto.getMessageTitle();
                //掲載開始日時
                rowDate[2] = f08021Dto.getPubPlanStartDt() + "～";
                //生徒ID
                rowDate[3] = f08021Dto.getStuLoginId();
                //生徒名
                rowDate[4] = f08021Dto.getStuNm();
                //ステータス
                rowDate[5] = status;
                csvWriter.writeRecord(rowDate);
                csvWriter.flush();
            }
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (csvWriter != null){
                csvWriter.close();
            }
        }
        //2020/11/19 9.0 wyh modify end
    }
}
