package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csvreader.CsvWriter;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.manager.dao.F08020Dao;
import jp.learningpark.modules.manager.dto.F08020Dto;
import jp.learningpark.modules.manager.service.F08020Service;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/3 ： NWT)hxl ： 新規作成
 * 2020/11/18 ： NWT)hxl ： 変更
 * @date 2020/11/3 11:55
 */
@Service
public class F08020ServiceImpl implements F08020Service {
    /**
     * f08020Dao
     */
    @Autowired
    F08020Dao f08020Dao;
    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDDao;
    @Override
    public R init(String openDiv, String orgId, String readDiv, Integer noticeId) {
        List<F08020Dto> grsList = f08020Dao.getGrsList(openDiv, readDiv, noticeId, orgId);
        String title = null;
        //区分値を表示するため、引渡データ．区分より、区分値を取得し
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
            r.put("msg", MessageUtils.getMessage("MSGCOMN0017", "お知らせ"));
            r.put("title", title);
            return r;
        }
        R r = R.ok();
        r.put("objList", grsList);
        r.put("title", title);
        return r;
    }

    @Override
    public File getDownloadFile(String orgId, String openDiv, String readDiv, Integer noticeId, List<String> stuIdList) {
        //2020/11/18 9.0 huangxinliang add start
        CsvWriter csvWriter = null;
        File outputFile;
        //2020/11/18 9.0 huangxinliang add end
        List<F08020Dto> downloadInfo = f08020Dao.getDownloadInfo(openDiv, readDiv, noticeId, orgId, stuIdList);
        //2020/11/18 9.0 huangxinliang modify start
        try {
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), "R10002_" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".csv");
            // ファイル
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
            csvWriter.writeRecord(new String[]{"F08020", "お知らせ対象リスト", DateUtils.format(new Date(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH)});
            csvWriter.flush();
            csvWriter.writeRecord(new String[]{"お知らせＩＤ", "お知らせ名", "掲載開始日時", "保護者ID", "保護者名", "生徒ID", "生徒名", "ステータス", "電話番号"});
            csvWriter.flush();
            // ファイルの書き込み
            for (F08020Dto f08020Dto : downloadInfo) {
                //ステータス
                String status;
                if (StringUtils.equals(f08020Dto.getReadingStsDiv(), "0")){
                    status = "未読";
                }else{
                    if (StringUtils.equals(f08020Dto.getOpenedFlg(), "0")){
                        status = "既読未開封";
                    }else {
                        status = "既読開封済";
                    }
                }
                String[] rowDate = new String[9];
                //お知らせＩＤ
                rowDate[0] = Integer.toString(noticeId);
                //お知らせ名
                rowDate[1] = f08020Dto.getNoticeTitle();
                //掲載開始日時
                rowDate[2] = f08020Dto.getPubPlanStartDt() + "～";
                //保護者ID
                rowDate[3] = f08020Dto.getGuardLoginId();
                //保護者名
                rowDate[4] = f08020Dto.getGuardNm();
                //生徒ID
                rowDate[5] = f08020Dto.getStuLoginId();
                //生徒名
                rowDate[6] = f08020Dto.getStuNm();
                //ステータス
                rowDate[7] = status;
                //電話番号
                rowDate[8] = "'" + f08020Dto.getTelnum();
                csvWriter.writeRecord(rowDate);
                csvWriter.flush();
            }
            return outputFile;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if (csvWriter != null){
                csvWriter.close();
            }
        }
        //2020/11/18 9.0 huangxinliang modify end
    }
}
