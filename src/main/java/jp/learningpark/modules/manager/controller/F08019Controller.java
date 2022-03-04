package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstAskTalkEventEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MailSendHstService;
import jp.learningpark.modules.common.service.MstAskTalkEventService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.manager.dto.F08019Dto;
import jp.learningpark.modules.manager.dto.F08019TalkRecordDetails;
import jp.learningpark.modules.manager.service.F08019Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>未読・未回答者送信一覧画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/18 : NWT文: 新規<br />
 * 2020/11/12 : NWT文: 変更<br />
 * @version 9.0
 */
@RestController
@RequestMapping(value = "/manager/F08019")
public class F08019Controller {

    /**
     * mailSendHstService
     */
    @Autowired
    MailSendHstService mailSendHstService;

    /**
     * mstCodDService
     */
    @Autowired
    private MstCodDService mstCodDetailService;

    /**
     * f08019Service
     */
    @Autowired
    private F08019Service f08019Service;

    /**
     * mstAskTalkEventService
     */
    @Autowired
    private MstAskTalkEventService mstAskTalkEventService;

    /**
     * logger
     */
    Logger logger = LoggerFactory.getLogger("F08019Controller");

    /**
     * @param eventId イベントID
     * @param div 区分
     * @return R
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer eventId, Integer div, String flg,Integer limit, Integer page) {

        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 保護者または生徒のイベント申込状況から対象者一覧を取得
        List<F08019Dto> f08019DtoList = new ArrayList<>();
        String flag = "t";
        Integer offset = (page-1)*limit;
        Integer count = 0;
        if (StringUtils.equals(flag, flg)) {
            //保護者イベント申込状況
            f08019DtoList = f08019Service.getGeasList(div, orgId, eventId,limit,offset);
            count = f08019Service.getGeasList(div, orgId, eventId,null,null).size();
        } else {
            //生徒のイベント申込状況
            f08019DtoList = f08019Service.selectStuApplyList(div, orgId, eventId,limit,offset);
            count = f08019Service.selectStuApplyList(div, orgId, eventId,null,null).size();
        }
        // 区分値を表示するため、引渡データ．区分より、区分値を取得し、画面で表示する。
        MstCodDEntity mstCodDetailEntity = mstCodDetailService.getOne(
                new QueryWrapper<MstCodDEntity>().eq("cod_key", "DELIVER_STS").eq("cod_cd", String.valueOf(div)).eq("del_flg", 0));

        if (f08019DtoList == null || f08019DtoList.size() <= 0) {
            // 上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0017", "イベント")).put("title",mstCodDetailEntity.getCodValue());
        }

        return R.ok().put("page", new PageUtils(f08019DtoList, count, limit, page)).put("title", mstCodDetailEntity.getCodValue());
    }

    /**
     * @param stuIdListStr 生徒ID
     * @param eventId イベントID
     * @param response 响应
     * @param div 区分
     * @return R
     */
    @RequestMapping(value = "/getDownloadInfo", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getDownloadInfo(String stuIdListStr, Integer eventId, Integer div, String flg, HttpServletResponse response) {
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 学生一覧
        List<String> stuIdList = JSON.parseArray(stuIdListStr, String.class);
        // チェックされたユーザに対して、下記条件で、イベント、保護者イベント申込状況を元に、対象者CSVファイルを出力する
        List<F08019Dto> f08019DtoList = new ArrayList<>();
        String flag = "t";
        if (StringUtils.equals(flag, flg)) {
            //保護者イベント申込状況
            f08019DtoList = f08019Service.getDownloadInfo(stuIdList, orgId, eventId, div);
        } else {
            //生徒イベント申込状況
            f08019DtoList = f08019Service.selectStuDownloadInfo(stuIdList, orgId, eventId, div);
        }
        //質問面談イベントを取得する。
        List<MstAskTalkEventEntity> mstAskTalkEventEntityList = mstAskTalkEventService.list(
                new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", eventId).eq("use_div", "1").eq("del_flg", 0).orderByAsc("item_type_div, ask_num"));
        //面談記録詳細を取得する。
        List<F08019TalkRecordDetails> talkRecordDetails = f08019Service.getTalkRecordDetails(eventId);
        Map<String, String> eventIdAndAskNumAnswerMap = new HashMap<>(16);
        talkRecordDetails.forEach(v->{
            eventIdAndAskNumAnswerMap.put(v.getDetailsKey(), v.getDetailsValue());
        });
        // ファイル名
        String fileName = "対象者予約情報リスト.csv";
        try {
            // ファイル
            File tempFile = File.createTempFile("R10002_" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS), ".csv");
            FileWriter fw = new FileWriter(tempFile);
            // csv形式
            fw.write(new String(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF}));
            // 書類の内容
            String header = "\"F08019\"" + "," + "\"対象者予約情報リスト\"" + ",\"" + DateUtils.format(
                    new Date(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH) + "\"" + "\n";
            String header2 = "\"カテゴリ\"" + "," + "\"タイトル\"" + "," + "\"予約日程\"" + "," + "\"保護者ID\"" + "," + "\"保護者名\"" + "," + "\"生徒ID\"" + "," + "\"生徒名\"" + "," + "\"担当講師・教室\"";
            StringBuilder stringBuilder = new StringBuilder().append(header2);
            //質問
            for (MstAskTalkEventEntity mstAskTalkEventEntity : mstAskTalkEventEntityList) {
                if (!StringUtils.isEmpty(mstAskTalkEventEntity.getQuestionName())) {
                    stringBuilder.append(",").append("\"").append(mstAskTalkEventEntity.getQuestionName()).append("\"");
                }
            }
            stringBuilder.append(",").append("\"備考\"").append("\n");
            fw.write(header);
            fw.write(stringBuilder.toString());
            StringBuffer str = new StringBuffer();
            // ファイルの書き込み
            for (F08019Dto f08019Dto : f08019DtoList) {
                dataHandle(f08019Dto, str, mstAskTalkEventEntityList, eventIdAndAskNumAnswerMap);
            }
            fw.write(str.toString());
            fw.flush();

            fileName = "対象者予約情報リスト_" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".csv";
            returnFile(fileName, tempFile, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * <p>データ処理</p>
     *
     * @param f08019Dto
     * @param str
     * @param mstAskTalkEventEntityList
     * @param eventIdAndAskNumAnswerMap
     */
    private void dataHandle(F08019Dto f08019Dto, StringBuffer str, List<MstAskTalkEventEntity> mstAskTalkEventEntityList, Map<String, String> eventIdAndAskNumAnswerMap) {
        // 予約日程
        String planDate;
        if (f08019Dto.getSgdPlanDate() != null) {
            planDate = DateUtils.format(f08019Dto.getSgdPlanDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_SLASH) + " " + DateUtils.format(
                    f08019Dto.getSgdStartDatime(), GakkenConstant.DATE_FORMAT_HH_MM) + "～";
        }else {
            planDate = "";
        }
        // 担当講師・教室
        String name = "";
        String refType = "0";
        if (refType.equals(f08019Dto.getRefTypeDiv())) {
            name = f08019Dto.getOrgNm();
        } else {
            name = f08019Dto.getMentorNm();
        }
        str.append("\"").append(f08019Dto.getCtgyNm()).append("\"").append(",").append("\"").append(f08019Dto.getEventTitle()).append("\"").append(",").append("\"").append(planDate).append("\"").append(",").append("\"").append(f08019Dto.getGuardId()).append("\"").append(",").append("\"").append(f08019Dto.getGuardNm()).append("\"").append(",").append("\"").append(f08019Dto.getStuId()).append("\"").append(",").append("\"").append(f08019Dto.getStuNm()).append("\"").append(",").append("\"").append(Optional.ofNullable(name).orElse("")).append("\"");

        for (int i = 0; i < mstAskTalkEventEntityList.size(); i++) {
            if (!StringUtils.isEmpty(mstAskTalkEventEntityList.get(i).getQuestionName())) {
                String key = f08019Dto.getTrhId() + ";" + f08019Dto.getStuId() + ";" + mstAskTalkEventEntityList.get(i).getItemTypeDiv() + ";" + mstAskTalkEventEntityList.get(
                        i).getAskNum();
                String value;
                if (eventIdAndAskNumAnswerMap.containsKey(key)) {
                    value = eventIdAndAskNumAnswerMap.get(key);
                    if (StringUtils.isEmpty(value)) {
                        value = "";
                    }
                } else {
                    value = "";
                }
                value = value.replaceAll("\"", "\"\"");
                str.append(",").append("\"").append(value).append("\"");
            }
        }
        String biko = StringUtils.isEmpty(f08019Dto.getReplyCnt()) ? "" : f08019Dto.getReplyCnt();
        biko = biko.replaceAll("\"", "\"\"");
        str.append(",").append("\"").append(biko).append("\"").append("\n");
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
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
