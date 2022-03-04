/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.csvreader.CsvWriter;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.TalkRecordDEntity;
import jp.learningpark.modules.manager.dao.F08011Dao;
import jp.learningpark.modules.manager.dto.F08011CsvVo;
import jp.learningpark.modules.manager.dto.F08011DspDto;
import jp.learningpark.modules.manager.dto.F08011Dto;
import jp.learningpark.modules.manager.service.F08011Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F08011_配信設定詳細一覧画面 ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/08 : yang: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F08011ServiceImpl implements F08011Service {
    /**
     * f08011Dao
     */
    @Autowired
    F08011Dao f08011Dao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * イベント配信者一覧情報を表示するため
     *
     * @param eventId
     * @param paramsMap
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<F08011Dto> getDeliverList(Integer eventId, String orgId, Map<String, String> paramsMap, Integer page, Integer limit) {
        return f08011Dao.getDeliverList(eventId, orgId, paramsMap, page, limit);
    }

    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    @Override
    public F08011Dto selectDspItem(String userId, String menuId) {
        return f08011Dao.selectDspItems(userId, menuId);
    }

    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    @Override
    public Integer getDspCount(String userId, String menuId) {
        return f08011Dao.getDspCount(userId, menuId);
    }

    /**
     * @param f08011DspDto
     * @return
     */
    @Override
    public Integer insertDspItem(F08011DspDto f08011DspDto) {
        return f08011Dao.insertDspItem(f08011DspDto);
    }

    /**
     * @param f08011DspDto
     * @return
     */
    @Override
    public Integer updateDspItem(F08011DspDto f08011DspDto) {
        return f08011Dao.updateDspItem(f08011DspDto);
    }

    @Override
    public R getCsvFileName(List<String> stuIdList) {
        List<F08011CsvVo> f08011CsvVoList = f08011Dao.selectCsvData(stuIdList);
        String outputFilename = "対象者情報リスト_" + DateUtils.format(
                DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + GakkenConstant.CSV_FILE_SUFFIX;
        String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), outputFilename);
        try {
            File outputFile = new File(tempPath);
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                outputFile.createNewFile();
            }
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
            bufferedOutputStream.write(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF});
            CsvWriter csvWriter = new CsvWriter(bufferedOutputStream, ',', StandardCharsets.UTF_8);

            csvWriter.setTextQualifier('"');
            csvWriter.setForceQualifier(true);
            //header
            csvWriter.writeRecord(
                    new String[] {"F08011", "対象者情報リスト", DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH)});
            csvWriter.writeRecord(new String[] {"生徒ID", "学年", "性別", "生徒名", "保護者名", "学校名", "電話番号"});
            csvWriter.flush();

            String[] strings = new String[7];
            for (F08011CsvVo f08011CsvVo : f08011CsvVoList) {
                strings[0] = f08011CsvVo.getStuId();
                strings[1] = f08011CsvVo.getSchy();
                strings[2] = f08011CsvVo.getSex();
                strings[3] = f08011CsvVo.getStuNm();
                strings[4] = f08011CsvVo.getGuardNm();
                strings[5] = f08011CsvVo.getSch();
                strings[6] = f08011CsvVo.getTel();
                csvWriter.writeRecord(strings);
                csvWriter.flush();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            return R.error("ファイルの書き込み中に例外が発生しました");
        }
        return R.ok().put("fileName", outputFilename);
    }

    /**
     * <p>ファイルを作成する。</p>
     *
     * add at 2021/08/12 for V9.02 by NWT wen
     * @param stuList
     * @return
     */
    @Override
    public String getFile(List<LinkedHashMap<String, Object>> stuList) {
        String outputFilename = "対象者(質問面談)情報リスト_" + DateUtils.format(
                DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + GakkenConstant.CSV_FILE_SUFFIX;
        String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), outputFilename);
        File outputFile = null;
        CsvWriter csvWriter = null;
        try {
            outputFile = new File(tempPath);
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                outputFile.createNewFile();
            }

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
            bufferedOutputStream.write(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF});
            csvWriter = new CsvWriter(bufferedOutputStream, ',', StandardCharsets.UTF_8);

            if (csvWriter == null) {
                return null;
            }

            csvWriter.setTextQualifier('"');
            csvWriter.setForceQualifier(true);
            //header
            String header[] = new String[] {"保護者名", "生徒ID", "生徒名", "ステータス", "既読日", "回答日", "申込日程", "教室", "質問１", "質問２", "質問３", "質問４", "質問５", "質問６", "質問７", "質問８", "質問９", "質問１０", "面談質問１", "面談質問２", "面談質問３", "面談質問４", "面談質問５"};
            F08011Dto f08011DtoHeader = JSON.parseObject(JSON.toJSONString(stuList.get(0)), F08011Dto.class);
            if (StringUtils.isNotBlank(f08011DtoHeader.getRefType()) && StringUtils.equals("1", f08011DtoHeader.getRefType())) {
                header[7] = "担当講師";
            }
            // 設問名ありの場合、取得したデータを設定する　
            if (!f08011DtoHeader.getTalkRecordDEntityList().isEmpty()) {
                for (int i = 0; i < f08011DtoHeader.getTalkRecordDEntityList().size(); i++) {
                    TalkRecordDEntity question = f08011DtoHeader.getTalkRecordDEntityList().get(i);
                    if (StringUtils.isNotBlank(question.getQuestionName())) {
                        header[i + 8] = question.getQuestionName();
                    }
                }
            }
            // 設問名ありの場合、取得したデータを設定する　
            if (!f08011DtoHeader.getInterviewList().isEmpty()) {
                for (int i = 0; i < f08011DtoHeader.getInterviewList().size(); i++) {
                    TalkRecordDEntity talk = f08011DtoHeader.getInterviewList().get(i);
                    if (StringUtils.isNotBlank(talk.getQuestionName())) {
                        header[i + 18] = talk.getQuestionName();
                    }
                }
            }
            csvWriter.writeRecord(header);
            csvWriter.flush();

            String[] strings = new String[header.length];
            // 内容を書き込む
            for (int i = 0; i < stuList.size(); i++) {
                F08011Dto f08011Dto = JSON.parseObject(JSON.toJSONString(stuList.get(i)), F08011Dto.class);
                // 保護者名
                strings[0] = f08011Dto.getGuardName();
                // 生徒ID
                strings[1] = f08011Dto.getStuLoginId();
                // 生徒名
                strings[2] = f08011Dto.getStuName();
                // ステータス
                strings[3] = f08011Dto.getStatus();
                // 既読日
                strings[4] = f08011Dto.getReadTime() == null ? "" : DateUtils.format(f08011Dto.getReadTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
                // 回答日
                strings[5] = f08011Dto.getReplyTime() == null ? "" : DateUtils.format(f08011Dto.getReplyTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH);
                // 申込日程
                strings[6] = f08011Dto.getStartTime() == null ? "" : DateUtils.format(f08011Dto.getStartTime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM);
                // 担当講師/教室
                strings[7] = f08011Dto.getTeacherName();
                // 質問事項
                List<TalkRecordDEntity> questionList = f08011Dto.getTalkRecordDEntityList();
                for (int j = 0; j < 10; j++) {
                    if (questionList.isEmpty()) {
                        strings[j + 8] = "";
                    } else {
                        TalkRecordDEntity talk = questionList.get(j);
                        strings[j + 8] = StringUtils.equals("3", talk.getAnswerTypeDiv()) ? "" : talk.getAnswerReltCont();
                    }
                }
                // 面談事項
                List<TalkRecordDEntity> talkList = f08011Dto.getInterviewList();
                for (int j = 0; j < 5; j++) {
                    if (talkList.isEmpty()) {
                        strings[j + 18] = "";
                    } else {
                        TalkRecordDEntity talk = talkList.get(j);
                        strings[j + 18] = talk.getAnswerReltCont();
                    }
                }
                csvWriter.writeRecord(strings);
                csvWriter.flush();
            }
            return outputFilename;
        } catch (IOException e) {
            logger.error("ファイル生成の失敗！", e);
            throw new RRException("ファイルの書き込み中に例外が発生しました");
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
        }
    }
}
