package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.TalkRecordDEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>F08006_イベント日程時間設定画面(POP) Dto</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/01 : yang: 新規<br />
 * @version 1.0
 */

public class F08011Dto {
    /**
     * add at 2021/08/30 for V9.02 by NWT wen
     * ダウンロード用ネーム
     */
    private String downloadName;

    /**
     * 保護者ID
     */
    private String guardId;
    /**
     * 変更後ユーザーID
     */
    private String stuLoginId;
    /**
     * 学年
     */
    private String schy;
    /**
     * 保護者名
     */
    public String guardName;
    /**
     *
     */
    public String stuName;
    /**
     * ステータス
     */
    public String status;
    /**
     * 既読日
     */
    public Timestamp readTime;
    /**
     * 既読日Start
     */
    public String readTimeStart;
    /**
     * 既読日End
     */
    public String readTimeEnd;

    /**
     * 回答日
     */
    public Timestamp replyTime;
    /**
     * 回答日Start
     */
    public String replyTimeStart;
    /**
     * 回答日End
     */
    public String replyTimeEnd;
    /**
     * 申込日程
     */
    public Timestamp startTime;
    /**
     * 申込日程Start
     */
    public String applyDatimeStart;
    /**
     * 申込日程End
     */
    public String applyDatimeEnd;
    /**
     * 担当講師/教室
     */
    public String roleName;
    /**
     * 回答１
     */
    public String reply1;
    /**
     * 配信数
     */
    private Integer deleverSum;

    /**
     * 未読数
     */
    private Integer notReadingSum;

    /**
     * 既読数
     */
    private Integer readingSum;

    /**
     * 予約数
     */
    private Integer replySum;
    /**
     * 担当講師
     */
    public String teacherName;
    /**
     * refType
     */
    public String refType;
    /**
     * 閲覧回答区分
     */
    public String replyStatusDiv;
    /**
     * 閲覧状況区分
     */
    public String readingStatusDiv;
    /**
     * 回答ステータス
     */
    public String readingStatus;
    /**
     * 回答ステータス
     */
    public String replyStatus;
    /**
     * 組織名
     */
    public String orgNm;
    /**
     * イベント日程(明細)Id
     */
    public Integer eventScheDelId;
    /**
     *
     */
    private String eventFlag;
    /**
     * 保護者loginId
     */
    private String guardLoginId;
    /**
     * 表示項目
     */
    private String dspItems;
    /**
     * 表示しなければならない項目
     */
    private String mustItems;
    /**
     * 全項目
     */
    private String allItems;
    /**
     * 面談記録詳細．回答結果内容
     */
    private String answerReltCont;

    /**
     * 質問
     */
    private String askNum;
    /**
     * イベントid
     */
    private Integer id;
    /**
     * 質問id
     */
    private Integer talkId;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 面談記録詳細
     */
    private List<TalkRecordDEntity> talkRecordDEntityList;

    /**
     * add at 2021/08/25 for V9.02 by NWT wen
     * 面談事項
     */
    private List<TalkRecordDEntity> interviewList;

    /**
     * 面談記録詳細．回答結果内容1
     */
    private String answerReltCont1;
    /**
     * 面談記録詳細．回答結果内容2
     */
    private String answerReltCont2;
    /**
     * 面談記録詳細．回答結果内容3
     */
    private String answerReltCont3;
    /**
     * 面談記録詳細．回答結果内容4
     */
    private String answerReltCont4;
    /**
     * 面談記録詳細．回答結果内容5
     */
    private String answerReltCont5;
    /**
     * 面談記録詳細．回答結果内容6
     */
    private String answerReltCont6;
    /**
     * 面談記録詳細．回答結果内容7
     */
    private String answerReltCont7;
    /**
     * 面談記録詳細．回答結果内容8
     */
    private String answerReltCont8;
    /**
     * 面談記録詳細．回答結果内容9
     */
    private String answerReltCont9;
    /**
     * 面談記録詳細．回答結果内容10
     */
    private String answerReltCont10;

    /**
     * 面談記録詳細．回答結果内容1
     */
    private String questionName1;
    /**
     * 面談記録詳細．回答結果内容2
     */
    private String questionName2;
    /**
     * 面談記録詳細．回答結果内容3
     */
    private String questionName3;
    /**
     * 面談記録詳細．回答結果内容4
     */
    private String questionName4;
    /**
     * 面談記録詳細．回答結果内容5
     */
    private String questionName5;
    /**
     * 面談記録詳細．回答結果内容6
     */
    private String questionName6;
    /**
     * 面談記録詳細．回答結果内容7
     */
    private String questionName7;
    /**
     * 面談記録詳細．回答結果内容8
     */
    private String questionName8;
    /**
     * 面談記録詳細．回答結果内容9
     */
    private String questionName9;
    /**
     * 面談記録詳細．回答結果内容10
     */
    private String questionName10;

    /**
     * 保護者名を取得する
     *
     * @return guardName 保護者名
     */
    public String getGuardName() {
        return this.guardName;
    }

    /**
     * 保護者名を設定する
     *
     * @param guardName 保護者名
     */
    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    /**
     * ステータスを取得する
     *
     * @return status ステータス
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * ステータスを設定する
     *
     * @param status ステータス
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 既読日を取得する
     *
     * @return readTime 既読日
     */
    public Timestamp getReadTime() {
        return this.readTime;
    }

    /**
     * 既読日を設定する
     *
     * @param readTime 既読日
     */
    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }

    /**
     * 回答日を取得する
     *
     * @return replyTime 回答日
     */
    public Timestamp getReplyTime() {
        return this.replyTime;
    }

    /**
     * 回答日を設定する
     *
     * @param replyTime 回答日
     */
    public void setReplyTime(Timestamp replyTime) {
        this.replyTime = replyTime;
    }

    /**
     * 申込日程を取得する
     *
     * @return startTime 申込日程
     */
    public Timestamp getStartTime() {
        return this.startTime;
    }

    /**
     * 申込日程を設定する
     *
     * @param startTime 申込日程
     */
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    /**
     * 担当講師教室を取得する
     *
     * @return teacherName 担当講師教室
     */
    public String getRoleName() {
        return this.roleName;
    }

    /**
     * 担当講師教室を設定する
     *
     * @param teacherName 担当講師教室
     */
    public void setRoleName(String teacherName) {
        this.roleName = teacherName;
    }

    /**
     * 回答１を取得する
     *
     * @return reply1 回答１
     */
    public String getReply1() {
        return this.reply1;
    }

    /**
     * 回答１を設定する
     *
     * @param reply1 回答１
     */
    public void setReply1(String reply1) {
        this.reply1 = reply1;
    }



    /**
     * を取得する
     *
     * @return stuName
     */
    public String getStuName() {
        return this.stuName;
    }

    /**
     * を設定する
     *
     * @param stuName
     */
    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    /**
     * refTypeを取得する
     *
     * @return refType refType
     */
    public String getRefType() {
        return this.refType;
    }

    /**
     * refTypeを設定する
     *
     * @param refType refType
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }

    /**
     * 担当講師を取得する
     *
     * @return teacherName 担当講師
     */
    public String getTeacherName() {
        return this.teacherName;
    }

    /**
     * 担当講師を設定する
     *
     * @param teacherName 担当講師
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * 既読日Startを取得する
     *
     * @return readTimeStart 既読日Start
     */
    public String getReadTimeStart() {
        return this.readTimeStart;
    }

    /**
     * 既読日Startを設定する
     *
     * @param readTimeStart 既読日Start
     */
    public void setReadTimeStart(String readTimeStart) {
        this.readTimeStart = readTimeStart;
    }

    /**
     * 既読日Endを取得する
     *
     * @return readTimeEnd 既読日End
     */
    public String getReadTimeEnd() {
        return this.readTimeEnd;
    }

    /**
     * 既読日Endを設定する
     *
     * @param readTimeEnd 既読日End
     */
    public void setReadTimeEnd(String readTimeEnd) {
        this.readTimeEnd = readTimeEnd;
    }

    /**
     * 回答日Startを取得する
     *
     * @return replyTimeStart 回答日Start
     */
    public String getReplyTimeStart() {
        return this.replyTimeStart;
    }

    /**
     * 回答日Startを設定する
     *
     * @param replyTimeStart 回答日Start
     */
    public void setReplyTimeStart(String replyTimeStart) {
        this.replyTimeStart = replyTimeStart;
    }

    /**
     * 回答日Endを取得する
     *
     * @return replyTimeEnd 回答日End
     */
    public String getReplyTimeEnd() {
        return this.replyTimeEnd;
    }

    /**
     * 回答日Endを設定する
     *
     * @param replyTimeEnd 回答日End
     */
    public void setReplyTimeEnd(String replyTimeEnd) {
        this.replyTimeEnd = replyTimeEnd;
    }

    /**
     * 申込日程Startを取得する
     *
     * @return applyDatimeStart 申込日程Start
     */
    public String getApplyDatimeStart() {
        return this.applyDatimeStart;
    }

    /**
     * 申込日程Startを設定する
     *
     * @param applyDatimeStart 申込日程Start
     */
    public void setApplyDatimeStart(String applyDatimeStart) {
        this.applyDatimeStart = applyDatimeStart;
    }

    /**
     * 申込日程Endを取得する
     *
     * @return applyDatimeEnd 申込日程End
     */
    public String getApplyDatimeEnd() {
        return this.applyDatimeEnd;
    }

    /**
     * 申込日程Endを設定する
     *
     * @param applyDatimeEnd 申込日程End
     */
    public void setApplyDatimeEnd(String applyDatimeEnd) {
        this.applyDatimeEnd = applyDatimeEnd;
    }

    /**
     * 閲覧回答区分を取得する
     *
     * @return replyStatusDiv 閲覧回答区分
     */
    public String getReplyStatusDiv() {
        return this.replyStatusDiv;
    }

    /**
     * 閲覧回答区分を設定する
     *
     * @param replyStatusDiv 閲覧回答区分
     */
    public void setReplyStatusDiv(String replyStatusDiv) {
        this.replyStatusDiv = replyStatusDiv;
    }

    /**
     * 回答ステータスを取得する
     *
     * @return readingStatus 回答ステータス
     */
    public String getReadingStatus() {
        return this.readingStatus;
    }

    /**
     * 回答ステータスを設定する
     *
     * @param readingStatus 回答ステータス
     */
    public void setReadingStatus(String readingStatus) {
        this.readingStatus = readingStatus;
    }

    /**
     * 回答ステータスを取得する
     *
     * @return replyStatus 回答ステータス
     */
    public String getReplyStatus() {
        return this.replyStatus;
    }

    /**
     * 回答ステータスを設定する
     *
     * @param replyStatus 回答ステータス
     */
    public void setReplyStatus(String replyStatus) {
        this.replyStatus = replyStatus;
    }

    /**
     * 組織名を取得する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を設定する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * 配信数を取得する
     *
     * @return deleverSum 配信数
     */
    public Integer getDeleverSum() {
        return this.deleverSum;
    }

    /**
     * 配信数を設定する
     *
     * @param deleverSum 配信数
     */
    public void setDeleverSum(Integer deleverSum) {
        this.deleverSum = deleverSum;
    }

    /**
     * 未読数を取得する
     *
     * @return notReadingSum 未読数
     */
    public Integer getNotReadingSum() {
        return this.notReadingSum;
    }

    /**
     * 未読数を設定する
     *
     * @param notReadingSum 未読数
     */
    public void setNotReadingSum(Integer notReadingSum) {
        this.notReadingSum = notReadingSum;
    }

    /**
     * 既読数を取得する
     *
     * @return readingSum 既読数
     */
    public Integer getReadingSum() {
        return this.readingSum;
    }

    /**
     * 既読数を設定する
     *
     * @param readingSum 既読数
     */
    public void setReadingSum(Integer readingSum) {
        this.readingSum = readingSum;
    }

    /**
     * 予約数を取得する
     *
     * @return replySum 予約数
     */
    public Integer getReplySum() {
        return this.replySum;
    }

    /**
     * 予約数を設定する
     *
     * @param replySum 予約数
     */
    public void setReplySum(Integer replySum) {
        this.replySum = replySum;
    }

    /**
     * 閲覧状況区分を取得する
     *
     * @return readingStatusDiv 閲覧状況区分
     */
    public String getReadingStatusDiv() {
        return this.readingStatusDiv;
    }

    /**
     * 閲覧状況区分を設定する
     *
     * @param readingStatusDiv 閲覧状況区分
     */
    public void setReadingStatusDiv(String readingStatusDiv) {
        this.readingStatusDiv = readingStatusDiv;
    }

    /**
     * イベント日程(明細)Idを取得する
     *
     * @return eventScheDelId イベント日程(明細)Id
     */
    public Integer getEventScheDelId() {
        return this.eventScheDelId;
    }

    /**
     * イベント日程(明細)Idを設定する
     *
     * @param eventScheDelId イベント日程(明細)Id
     */
    public void setEventScheDelId(Integer eventScheDelId) {
        this.eventScheDelId = eventScheDelId;
    }

    /**
     * 表示項目を取得する
     *
     * @return dspItems 表示項目
     */
    public String getDspItems() {
        return this.dspItems;
    }

    /**
     * 表示項目を設定する
     *
     * @param dspItems 表示項目
     */
    public void setDspItems(String dspItems) {
        this.dspItems = dspItems;
    }

    /**
     * 表示しなければならない項目を取得する
     *
     * @return mustItems 表示しなければならない項目
     */
    public String getMustItems() {
        return this.mustItems;
    }

    /**
     * 表示しなければならない項目を設定する
     *
     * @param mustItems 表示しなければならない項目
     */
    public void setMustItems(String mustItems) {
        this.mustItems = mustItems;
    }

    /**
     * 全項目を取得する
     *
     * @return allItems 全項目
     */
    public String getAllItems() {
        return this.allItems;
    }

    /**
     * 全項目を設定する
     *
     * @param allItems 全項目
     */
    public void setAllItems(String allItems) {
        this.allItems = allItems;
    }

    /**
     * 面談記録詳細．回答結果内容を取得する
     *
     * @return answerReltCont 面談記録詳細．回答結果内容
     */
    public String getAnswerReltCont() {
        return this.answerReltCont;
    }

    /**
     * 面談記録詳細．回答結果内容を設定する
     *
     * @param answerReltCont 面談記録詳細．回答結果内容
     */
    public void setAnswerReltCont(String answerReltCont) {
        this.answerReltCont = answerReltCont;
    }

    /**
     * 質問を取得する
     *
     * @return askNum 質問
     */
    public String getAskNum() {
        return this.askNum;
    }

    /**
     * 質問を設定する
     *
     * @param askNum 質問
     */
    public void setAskNum(String askNum) {
        this.askNum = askNum;
    }

    /**
     * 面談記録詳細を取得する
     *
     * @return talkRecordDEntityList 面談記録詳細
     */
    public List<TalkRecordDEntity> getTalkRecordDEntityList() {
        return this.talkRecordDEntityList;
    }

    /**
     * 面談記録詳細を設定する
     *
     * @param talkRecordDEntityList 面談記録詳細
     */
    public void setTalkRecordDEntityList(List<TalkRecordDEntity> talkRecordDEntityList) {
        this.talkRecordDEntityList = talkRecordDEntityList;
    }

    /**
     * 質問idを取得する
     *
     * @return talkId 質問id
     */
    public Integer getTalkId() {
        return this.talkId;
    }

    /**
     * 質問idを設定する
     *
     * @param talkId 質問id
     */
    public void setTalkId(Integer talkId) {
        this.talkId = talkId;
    }

    /**
     * イベントidを取得する
     *
     * @return id イベントid
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * イベントidを設定する
     *
     * @param id イベントid
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 面談記録詳細．回答結果内容1を取得する
     *
     * @return answerReltCont1 面談記録詳細．回答結果内容1
     */
    public String getAnswerReltCont1() {
        return this.answerReltCont1;
    }

    /**
     * 面談記録詳細．回答結果内容1を設定する
     *
     * @param answerReltCont1 面談記録詳細．回答結果内容1
     */
    public void setAnswerReltCont1(String answerReltCont1) {
        this.answerReltCont1 = answerReltCont1;
    }

    /**
     * 面談記録詳細．回答結果内容2を取得する
     *
     * @return answerReltCont2 面談記録詳細．回答結果内容2
     */
    public String getAnswerReltCont2() {
        return this.answerReltCont2;
    }

    /**
     * 面談記録詳細．回答結果内容2を設定する
     *
     * @param answerReltCont2 面談記録詳細．回答結果内容2
     */
    public void setAnswerReltCont2(String answerReltCont2) {
        this.answerReltCont2 = answerReltCont2;
    }

    /**
     * 面談記録詳細．回答結果内容3を取得する
     *
     * @return answerReltCont3 面談記録詳細．回答結果内容3
     */
    public String getAnswerReltCont3() {
        return this.answerReltCont3;
    }

    /**
     * 面談記録詳細．回答結果内容3を設定する
     *
     * @param answerReltCont3 面談記録詳細．回答結果内容3
     */
    public void setAnswerReltCont3(String answerReltCont3) {
        this.answerReltCont3 = answerReltCont3;
    }

    /**
     * 面談記録詳細．回答結果内容4を取得する
     *
     * @return answerReltCont4 面談記録詳細．回答結果内容4
     */
    public String getAnswerReltCont4() {
        return this.answerReltCont4;
    }

    /**
     * 面談記録詳細．回答結果内容4を設定する
     *
     * @param answerReltCont4 面談記録詳細．回答結果内容4
     */
    public void setAnswerReltCont4(String answerReltCont4) {
        this.answerReltCont4 = answerReltCont4;
    }

    /**
     * 面談記録詳細．回答結果内容5を取得する
     *
     * @return answerReltCont5 面談記録詳細．回答結果内容5
     */
    public String getAnswerReltCont5() {
        return this.answerReltCont5;
    }

    /**
     * 面談記録詳細．回答結果内容5を設定する
     *
     * @param answerReltCont5 面談記録詳細．回答結果内容5
     */
    public void setAnswerReltCont5(String answerReltCont5) {
        this.answerReltCont5 = answerReltCont5;
    }

    /**
     * 面談記録詳細．回答結果内容6を取得する
     *
     * @return answerReltCont6 面談記録詳細．回答結果内容6
     */
    public String getAnswerReltCont6() {
        return this.answerReltCont6;
    }

    /**
     * 面談記録詳細．回答結果内容6を設定する
     *
     * @param answerReltCont6 面談記録詳細．回答結果内容6
     */
    public void setAnswerReltCont6(String answerReltCont6) {
        this.answerReltCont6 = answerReltCont6;
    }

    /**
     * 面談記録詳細．回答結果内容7を取得する
     *
     * @return answerReltCont7 面談記録詳細．回答結果内容7
     */
    public String getAnswerReltCont7() {
        return this.answerReltCont7;
    }

    /**
     * 面談記録詳細．回答結果内容7を設定する
     *
     * @param answerReltCont7 面談記録詳細．回答結果内容7
     */
    public void setAnswerReltCont7(String answerReltCont7) {
        this.answerReltCont7 = answerReltCont7;
    }

    /**
     * 面談記録詳細．回答結果内容8を取得する
     *
     * @return answerReltCont8 面談記録詳細．回答結果内容8
     */
    public String getAnswerReltCont8() {
        return this.answerReltCont8;
    }

    /**
     * 面談記録詳細．回答結果内容8を設定する
     *
     * @param answerReltCont8 面談記録詳細．回答結果内容8
     */
    public void setAnswerReltCont8(String answerReltCont8) {
        this.answerReltCont8 = answerReltCont8;
    }

    /**
     * 面談記録詳細．回答結果内容9を取得する
     *
     * @return answerReltCont9 面談記録詳細．回答結果内容9
     */
    public String getAnswerReltCont9() {
        return this.answerReltCont9;
    }

    /**
     * 面談記録詳細．回答結果内容9を設定する
     *
     * @param answerReltCont9 面談記録詳細．回答結果内容9
     */
    public void setAnswerReltCont9(String answerReltCont9) {
        this.answerReltCont9 = answerReltCont9;
    }

    /**
     * 面談記録詳細．回答結果内容10を取得する
     *
     * @return answerReltCont10 面談記録詳細．回答結果内容10
     */
    public String getAnswerReltCont10() {
        return this.answerReltCont10;
    }

    /**
     * 面談記録詳細．回答結果内容10を設定する
     *
     * @param answerReltCont10 面談記録詳細．回答結果内容10
     */
    public void setAnswerReltCont10(String answerReltCont10) {
        this.answerReltCont10 = answerReltCont10;
    }

    /**
     * 面談記録詳細．回答結果内容1を取得する
     *
     * @return questionName1 面談記録詳細．回答結果内容1
     */
    public String getQuestionName1() {
        return this.questionName1;
    }

    /**
     * 面談記録詳細．回答結果内容1を設定する
     *
     * @param questionName1 面談記録詳細．回答結果内容1
     */
    public void setQuestionName1(String questionName1) {
        this.questionName1 = questionName1;
    }

    /**
     * 面談記録詳細．回答結果内容2を取得する
     *
     * @return questionName2 面談記録詳細．回答結果内容2
     */
    public String getQuestionName2() {
        return this.questionName2;
    }

    /**
     * 面談記録詳細．回答結果内容2を設定する
     *
     * @param questionName2 面談記録詳細．回答結果内容2
     */
    public void setQuestionName2(String questionName2) {
        this.questionName2 = questionName2;
    }

    /**
     * 面談記録詳細．回答結果内容3を取得する
     *
     * @return questionName3 面談記録詳細．回答結果内容3
     */
    public String getQuestionName3() {
        return this.questionName3;
    }

    /**
     * 面談記録詳細．回答結果内容3を設定する
     *
     * @param questionName3 面談記録詳細．回答結果内容3
     */
    public void setQuestionName3(String questionName3) {
        this.questionName3 = questionName3;
    }

    /**
     * 面談記録詳細．回答結果内容4を取得する
     *
     * @return questionName4 面談記録詳細．回答結果内容4
     */
    public String getQuestionName4() {
        return this.questionName4;
    }

    /**
     * 面談記録詳細．回答結果内容4を設定する
     *
     * @param questionName4 面談記録詳細．回答結果内容4
     */
    public void setQuestionName4(String questionName4) {
        this.questionName4 = questionName4;
    }

    /**
     * 面談記録詳細．回答結果内容5を取得する
     *
     * @return questionName5 面談記録詳細．回答結果内容5
     */
    public String getQuestionName5() {
        return this.questionName5;
    }

    /**
     * 面談記録詳細．回答結果内容5を設定する
     *
     * @param questionName5 面談記録詳細．回答結果内容5
     */
    public void setQuestionName5(String questionName5) {
        this.questionName5 = questionName5;
    }

    /**
     * 面談記録詳細．回答結果内容6を取得する
     *
     * @return questionName6 面談記録詳細．回答結果内容6
     */
    public String getQuestionName6() {
        return this.questionName6;
    }

    /**
     * 面談記録詳細．回答結果内容6を設定する
     *
     * @param questionName6 面談記録詳細．回答結果内容6
     */
    public void setQuestionName6(String questionName6) {
        this.questionName6 = questionName6;
    }

    /**
     * 面談記録詳細．回答結果内容7を取得する
     *
     * @return questionName7 面談記録詳細．回答結果内容7
     */
    public String getQuestionName7() {
        return this.questionName7;
    }

    /**
     * 面談記録詳細．回答結果内容7を設定する
     *
     * @param questionName7 面談記録詳細．回答結果内容7
     */
    public void setQuestionName7(String questionName7) {
        this.questionName7 = questionName7;
    }

    /**
     * 面談記録詳細．回答結果内容8を取得する
     *
     * @return questionName8 面談記録詳細．回答結果内容8
     */
    public String getQuestionName8() {
        return this.questionName8;
    }

    /**
     * 面談記録詳細．回答結果内容8を設定する
     *
     * @param questionName8 面談記録詳細．回答結果内容8
     */
    public void setQuestionName8(String questionName8) {
        this.questionName8 = questionName8;
    }

    /**
     * 面談記録詳細．回答結果内容9を取得する
     *
     * @return questionName9 面談記録詳細．回答結果内容9
     */
    public String getQuestionName9() {
        return this.questionName9;
    }

    /**
     * 面談記録詳細．回答結果内容9を設定する
     *
     * @param questionName9 面談記録詳細．回答結果内容9
     */
    public void setQuestionName9(String questionName9) {
        this.questionName9 = questionName9;
    }

    /**
     * 面談記録詳細．回答結果内容10を取得する
     *
     * @return questionName10 面談記録詳細．回答結果内容10
     */
    public String getQuestionName10() {
        return this.questionName10;
    }

    /**
     * 面談記録詳細．回答結果内容10を設定する
     *
     * @param questionName10 面談記録詳細．回答結果内容10
     */
    public void setQuestionName10(String questionName10) {
        this.questionName10 = questionName10;
    }

    /**
     * 生徒IDを取得する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 保護者IDを取得する
     *
     * @return guardId 保護者ID
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param guardId 保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @return stuLoginId 変更後ユーザーID
     */
    public String getStuLoginId() {
        return this.stuLoginId;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @param stuLoginId 変更後ユーザーID
     */
    public void setStuLoginId(String stuLoginId) {
        this.stuLoginId = stuLoginId;
    }

    /**
     * 学年を取得する
     *
     * @return schy 学年
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * 学年を設定する
     *
     * @param schy 学年
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * を取得する
     *
     * @return eventFlag
     */
    public String getEventFlag() {
        return this.eventFlag;
    }

    /**
     * を設定する
     *
     * @param eventFlag
     */
    public void setEventFlag(String eventFlag) {
        this.eventFlag = eventFlag;
    }

    /**
     * 保護者loginIdを取得する
     *
     * @return guardLoginId 保護者loginId
     */
    public String getGuardLoginId() {
        return this.guardLoginId;
    }

    /**
     * 保護者loginIdを設定する
     *
     * @param guardLoginId 保護者loginId
     */
    public void setGuardLoginId(String guardLoginId) {
        this.guardLoginId = guardLoginId;
    }

    /**
     * add at 20210825 for V9.02 by NWT wen      面談事項を取得する
     *
     * @return interviewList add at 20210825 for V9.02 by NWT wen      面談事項
     */
    public List<TalkRecordDEntity> getInterviewList() {
        return this.interviewList;
    }

    /**
     * add at 20210825 for V9.02 by NWT wen      面談事項を設定する
     *
     * @param interviewList add at 20210825 for V9.02 by NWT wen      面談事項
     */
    public void setInterviewList(List<TalkRecordDEntity> interviewList) {
        this.interviewList = interviewList;
    }

    /**
     * add at 20210830 for V9.02 by NWT wen
     * ダウンロード用ネームを取得する
     *
     * @return ダウンロード用ネーム
     */
    public String getDownloadName() {
        return this.downloadName;
    }

    /**
     * add at 20210830 for V9.02 by NWT wen
     * ダウンロード用ネームを設定する
     *
     * @param downloadName ダウンロード用ネーム
     */
    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }
}
