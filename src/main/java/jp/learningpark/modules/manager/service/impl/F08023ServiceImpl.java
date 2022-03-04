package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csvreader.CsvWriter;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.manager.dao.F08023Dao;
import jp.learningpark.modules.manager.dto.F08023Dto;
import jp.learningpark.modules.manager.service.F08023Service;
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
 * @author NWT)ckl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/5 ： NWT)ckl ： 新規作成
 * @date 2020/11/5 10:55
 */
@Service
public class F08023ServiceImpl implements F08023Service {
    /**
     * f08023Dao
     */
    @Autowired
    F08023Dao f08023Dao;
    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    private static final String READ_FLAG = "-1";

    /**
     * @param orgId             組織ID
     * @param div               閲覧状況区分
     * @param guidReprDeliverCd 指導報告書配信コード
     * @return
     */
    @Override
    public R init(String orgId, String div, String guidReprDeliverCd) {
        //保護者配信対象データを取得し
        List<F08023Dto> grsList = f08023Dao.getGrsList(div, orgId, guidReprDeliverCd);
        MstCodDEntity mstCodDEntity = new MstCodDEntity();

        //区分値を表示するため、引渡データ．区分より、区分値を取得し
        if (StringUtils.equals(READ_FLAG, div)) {
            mstCodDEntity.setCodValue("配信");
        } else {
            mstCodDEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "READING_STS_DIV").eq("cod_cd", div));
        }
        if (grsList == null || grsList.size() <= 0) {
            // 上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0017", "学習連絡")).put("title", mstCodDEntity.getCodValue());
        }
        return R.ok().put("objList", grsList).put("title", mstCodDEntity.getCodValue());
    }

    /**
     * @param orgId             組織ID
     * @param guidReprDeliverCd 指導報告書配信コード
     * @param div               閲覧状況区分
     * @param stuIdList         生徒IDリスト
     * @return
     */
    @Override
    public File getDownloadFile(String orgId, String guidReprDeliverCd, String div, List<String> stuIdList) {
        List<F08023Dto> downloadInfo = f08023Dao.getDownloadInfo(guidReprDeliverCd, div, orgId, stuIdList);
        //2020/11/18 9.0 cuikailin add start
        CsvWriter csvWriter = null;
        File outputFile;
        //2020/11/18 9.0 cuikailin add end
        try {
            /* 2020/11/18 9.0 cuikailin modify start */
            String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), "R10002_" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".csv");
            // ファイル
            outputFile = new File(tempPath);
            if (!outputFile.exists()) {
                boolean mkdirs = outputFile.getParentFile().mkdirs();
                boolean newFile = outputFile.createNewFile();
            }
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                bufferedOutputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
                csvWriter = new CsvWriter(bufferedOutputStream, ',', StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (csvWriter == null) {
                return null;
            }

            // csv形式
            csvWriter.setTextQualifier('"');
            csvWriter.setForceQualifier(true);
            // 書類の内容
            // ヘッダーを書き込む
            csvWriter.writeRecord(new String[]{"F08023", "学習連絡対象リスト", DateUtils.format(new Date(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH)});
            csvWriter.flush();
            csvWriter.writeRecord(new String[]{"生徒ID", "学年", "性別", "生徒名", "保護者名", "学校名", "電話番号"});
            csvWriter.flush();
            // ファイルの書き込み
            for (F08023Dto f08023Dto : downloadInfo) {
//                String status = null;
//                if (StringUtils.equals(f08023Dto.getReadingStsDiv(), "0")) {
//                    status = "未読";
//                } else if (StringUtils.equals(f08023Dto.getReadingStsDiv(), "1")) {
//                    status = "既読";
//                } else {
//                    status = "配信";
//                }
//                ;
                String[] rowDate = new String[7];
                rowDate[0] = f08023Dto.getStuLoginId();
                rowDate[1] = f08023Dto.getSchyDiv();
                rowDate[2] = f08023Dto.getGendrDiv();
                rowDate[3] = f08023Dto.getStuNm();
                rowDate[4] = f08023Dto.getGuardNm();
                rowDate[5] = f08023Dto.getSch();
                rowDate[6] = f08023Dto.getTelnum();
                csvWriter.writeRecord(rowDate);
                csvWriter.flush();
            }
            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
        }
        /* 2020/11/18 9.0 cuikailin modify end */
    }
}
