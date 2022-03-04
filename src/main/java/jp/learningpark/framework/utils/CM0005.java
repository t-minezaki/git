package jp.learningpark.framework.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jp.learningpark.modules.common.dao.*;
import jp.learningpark.modules.common.entity.AttendBookGetPointHstEntity;
import jp.learningpark.modules.common.entity.MstVariousSetEntity;
import jp.learningpark.modules.common.entity.StuGetPointHstEntity;
import jp.learningpark.modules.common.entity.StuPointEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/12/2 ： NWT)hxl ： 新規作成
 * @date 2020/12/2 15:44
 */
public class CM0005 {

    /**
     * 入退室各種設定マスタDao
     */
    private static AtomicReference<MstVariousSetDao> mstVariousSetDaoAtomicReference = new AtomicReference<>();

    /**
     * 生徒ポイントDao
     */
    private static AtomicReference<StuPointDao> stuPointDaoAtomicReference = new AtomicReference<>();

    /**
     * 生徒付与ポイント履歴Dao
     */
    private static AtomicReference<StuGetPointHstDao> stuGetPointHstDaoAtomicReference = new AtomicReference<>();

    /**
     * 生徒ウィークリー計画実績設定Dao
     */
    private static AtomicReference<StuWeeklyPlanPerfDao> stuWeeklyPlanPerfDaoAtomicReference = new AtomicReference<>();

    // add at 2021/8/13 for V9.02 by NWT HuangXL START
    /**
     * 出席簿付与ポイント履歴
     */
    private static AtomicReference<AttendBookGetPointHstDao> attendBookGetPointHstDaoAtomicReference = new AtomicReference<>();
    // add at 2021/8/13 for V9.02 by NWT HuangXL END

    /**
     * 各自動付与ポイントを取得する
     *
     * @param orgId 組織ID
     * @return 登校自動ポイント（デフォルト値は0です）
     */
    public static PointVo getPointVo(String orgId) {

        // 入退室各種設定マスタdaoエンティティクラスを初期化します
        setUpMstVariousSetDao();
        // 各自動付与ポイントを取得する
        List<MstVariousSetEntity> variousSetEntities = mstVariousSetDaoAtomicReference.get().selectList(new QueryWrapper<MstVariousSetEntity>().eq("org_id", orgId)
                .eq("del_flg", 0));
        PointVo pointVo = new PointVo();
        //データが存在しない場合
        if (variousSetEntities.size() == 0) {
            return pointVo;
        }
        pointVo.setMstVariousSetEntity(variousSetEntities.get(0));
        //再認識不可時間
        if (pointVo.getMstVariousSetEntity().getReRecoTime() != null) {
            pointVo.setReRecoTime(pointVo.getMstVariousSetEntity().getReRecoTime());
        }
        //登校自動ポイント
        if (pointVo.getMstVariousSetEntity().getGoSchPoint() != null) {
            pointVo.setGoSchPoint(pointVo.getMstVariousSetEntity().getGoSchPoint());
        }
        //実行登録時自動ポイント
        if (pointVo.getMstVariousSetEntity().getDoLoginPoint() != null) {
            pointVo.setDoLoginPoint(pointVo.getMstVariousSetEntity().getDoLoginPoint());
        }
        //実行時間累計自動ポイント
        if (pointVo.getMstVariousSetEntity().getDoTotalPoint() != null) {
            pointVo.setDoTotalPoint(pointVo.getMstVariousSetEntity().getDoTotalPoint());
        }
        //入室時自動ポイント
        if (pointVo.getMstVariousSetEntity().getInRoomPoint() != null) {
            pointVo.setInRoomPoint(pointVo.getMstVariousSetEntity().getInRoomPoint());
        }
        // add at 2021/8/13 for V9.02 by NWT HuangXL START
        //合格時自動ポイント
        if (pointVo.getMstVariousSetEntity().getPaddPoint() != null) {
            pointVo.setPaddPoint(pointVo.getMstVariousSetEntity().getPaddPoint());
        }
        //満点時自動ポイント
        if (pointVo.getMstVariousSetEntity().getFullMarksPoint() != null) {
            pointVo.setFullMarksPoint(pointVo.getMstVariousSetEntity().getFullMarksPoint());
        }
        //宿題提出時自動ポイント
        if (pointVo.getMstVariousSetEntity().getWorkOutPoint() != null) {
            pointVo.setWorkOutPoint(pointVo.getMstVariousSetEntity().getWorkOutPoint());
        }
        //出席登録時自動ポイント
        if (pointVo.getMstVariousSetEntity().getAttentOutPoint() != null) {
            pointVo.setAttentOutPoint(pointVo.getMstVariousSetEntity().getAttentOutPoint());
        }
        //誕生日時自動ポイント
        if (pointVo.getMstVariousSetEntity().getBirthdayTimePoint() != null) {
            pointVo.setBirthdayTimePoint(pointVo.getMstVariousSetEntity().getBirthdayTimePoint());
        }
        // add at 2021/8/13 for V9.02 by NWT HuangXL END
        return pointVo;
    }

    /**
     * ポイントを追加
     *
     * @param stuId      生徒ID
     * @param orgId      組織ID
     * @param type       1：goSchoPoint;2:doLoginPoint;3:doTotalPoint;4:inRoomPoint;6:attentOutPoint;7:birthdayTimePoint
     * @param operatorId オペレーターID
     */
    public static void addPoint(String stuId, String orgId, PointVo pointVo, int type, String operatorId, Timestamp getPointDatime) {

        // modify at 2021/8/20 for V9.02 by NWT HuangXL START
        // 生徒ポイントdaoエンティティクラスを初期化します
        setUpStuPointDao();
        // 生徒付与ポイント履歴daoエンティティクラスを初期化します
        setUpStuGetPointHstDao();
        // 生徒ウィークリー計画実績設定daoエンティティクラスを初期化します
        setUpStuWeeklyPlanPerfDao();
        // 出席簿付与ポイント履歴daoエンティティクラスを初期化します
        setUpAttendBookGetPointHstDao();
        // modify at 2021/8/20 for V9.02 by NWT HuangXL END

        // システム日付
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        // 生徒のポイント、リセット日時を取得する
        List<StuPointEntity> stuPointEntities = stuPointDaoAtomicReference.get().selectList(
                new QueryWrapper<StuPointEntity>()
                        // 生徒ポイント．生徒ID　＝　入力パラメータ．生徒ID
                        .eq("stu_id", stuId)
                        // 生徒ポイント．組織ID　＝　入力パラメータ．組織ID
                        .eq("org_id", orgId)
                        .eq("del_flg", 0));
        StuPointEntity stuPointEntity;

        if (stuPointEntities.size() == 0) {
            stuPointEntity = new StuPointEntity();
            //組織ID
            stuPointEntity.setOrgId(orgId);
            //生徒ID
            stuPointEntity.setStuId(stuId);
            //ポイント
            stuPointEntity.setPoint(0);
            //学習実績登録ポイント
            stuPointEntity.setDoLoginPoint(0);
            //学習実績累計ポイント
            stuPointEntity.setDoTotalPoint(0);
            //入室ポイント
            stuPointEntity.setInRoomPoint(0);
            //調整ポイント
            stuPointEntity.setMovePoint(0);
            // add at 2021/08/17 for V9.02 by NWT wen START
            //出席簿登録時ポイント
            stuPointEntity.setAttendPoint(0);
            //誕生日時ポイント
            stuPointEntity.setBirthdayPoint(0);
            // add at 2021/08/17 for V9.02 by NWT wen END
            // 取得リセット日時にシステム時間(YYYY)-5を設定する。
            stuPointEntity.setResetDatime(DateUtils.toTimestamp(DateUtils.addYears(sysTimestamp, -5)));
            //作成日時
            stuPointEntity.setCretDatime(sysTimestamp);
            //作成ユーザＩＤ
            stuPointEntity.setCretUsrId(operatorId);
        } else if (stuPointEntities.size() > 1) {
            // 2020/12/4 huangxinliang modify start
            stuPointEntities.sort((o1, o2) -> -Integer.compare((o1.getPoint() + o1.getDoLoginPoint() + o1.getDoTotalPoint() + o1.getInRoomPoint() + o1.getMovePoint()),
                    o2.getPoint() + o2.getDoLoginPoint() + o2.getDoTotalPoint() + o2.getInRoomPoint() + o2.getMovePoint()));
            // 2020/12/4 huangxinliang modify end
            // 2020/11/24 huangxinliang modify start
            stuPointEntity = stuPointEntities.remove(0);
            // ダーティデータ
            if (stuPointEntities.size() > 0) {
                stuPointDaoAtomicReference.get().deleteBatchIds(stuPointEntities.stream().map(StuPointEntity::getId).collect(Collectors.toList()));
            }
            // 2020/11/24 huangxinliang modify end
        } else {
            stuPointEntity = stuPointEntities.get(0);
        }

        // modify at 2021/8/20 for V9.02 by NWT HuangXL START
        // 3入力パラメータ．付与ポイント区分が「3：実行時間累計自動ポイント付与」の場合、下記処理行う
        int step = 0;
        if (type == 3) {
            int allPerfTime = stuWeeklyPlanPerfDaoAtomicReference.get().selectAllPerfTime(stuId, stuPointEntity.getResetDatime());
            step = (int) Math.floor((double) allPerfTime / (60 * 100));
        }

        // 3-1入力パラメータ．付与ポイント区分が「6：出席簿登録時自動ポイント付与」の場合、下記処理行う。
        AttendBookGetPointHstEntity attendBookGetPointHstEntity = new AttendBookGetPointHstEntity();
        if (type == 6) {
            List<AttendBookGetPointHstEntity> attendBookGetPointHstEntities = attendBookGetPointHstDaoAtomicReference.get().selectAllPointByStuIdAndOrgId(stuId, orgId, stuPointEntity.getResetDatime());
            if (attendBookGetPointHstEntities.size() != 0) {
                attendBookGetPointHstEntity = attendBookGetPointHstEntities.get(0);
            }
        }
        // modify at 2021/8/20 for V9.02 by NWT HuangXL END
        // modify at 2021/8/27 for V9.02 by NWT HuangXL START
        // 4.　下記条件で生徒付与ポイント履歴から付与ポイント履歴を取得する。
        QueryWrapper<StuGetPointHstEntity> stuGetPointHstEntityCountQueryWrapper = new QueryWrapper<>();
        // 生徒付与ポイント履歴．生徒ID　＝　入力パラメータ．生徒ID（マナミル一意ID）
        stuGetPointHstEntityCountQueryWrapper.eq("org_id", orgId);
        // 生徒付与ポイント履歴．組織ID　＝　入力パラメータ．組織ID
        stuGetPointHstEntityCountQueryWrapper.eq("stu_id", stuId);
        // 生徒付与ポイント履歴．削除フラグ　＝　「0：有効」
        stuGetPointHstEntityCountQueryWrapper.eq("del_flg", 0);
        // 生徒付与ポイント履歴．付与ポイント区分　＝　入力パラメータ．付与ポイント区分
        stuGetPointHstEntityCountQueryWrapper.eq("point_type_div", Integer.toString(type));
        // modify at 2021/8/31 for V9.02 by NWT HuangXL START
        StringBuilder lastStr = new StringBuilder();
        if (type == 1 || type == 4){
            // ①　付与ポイント区分＝「1：登校」　または　「4：入室」の場合
            // 生徒付与ポイント履歴．付与ポイント日時(YYYYMMDD)　＝　入力パラメータ．付与ポイント日時(システム日時)(YYYYMMDD)
            lastStr.append("and to_char(get_point_datime, 'yyyy-mm-dd') = '").append(DateUtils.format(getPointDatime, Constant.DATE_FORMAT_YYYY_MM_DD_BARS)).append("'");
//            stuGetPointHstEntityCountQueryWrapper.last("and to_char(get_point_datime, 'yyyy-mm-dd') = '" + DateUtils.format(getPointDatime, Constant.DATE_FORMAT_YYYY_MM_DD_BARS) + "'");
        }else if (type == 2) {
            // ②　付与ポイント区分＝「2：実行登録」
            // 生徒付与ポイント履歴．付与ポイント日時(YYYYMMDD)　＝　入力パラメータ．付与ポイント日時(学習実績時間)
            lastStr.append("and to_char(get_point_datime, 'yyyy-mm-dd') = '").append(DateUtils.format(getPointDatime, Constant.DATE_FORMAT_YYYY_MM_DD_BARS)).append("'");
//            stuGetPointHstEntityCountQueryWrapper.last("and to_char(get_point_datime, 'yyyy-mm-dd') = '" + DateUtils.format(getPointDatime, Constant.DATE_FORMAT_YYYY_MM_DD_BARS) + "'");
        }else if (type == 7) {
            // ③　付与ポイント区分＝「7：誕生日時自動ポイント付与」の場合
            // 生徒付与ポイント履歴．付与ポイント日時(YYYYMMDD)　＝　入力パラメータ．付与ポイント日時（誕生日）(YYYYMMDD)
            lastStr.append("and to_char(get_point_datime, 'yyyy-mm-dd') = '").append(DateUtils.format(getPointDatime, Constant.DATE_FORMAT_YYYY_MM_DD_BARS)).append("'");
//            stuGetPointHstEntityCountQueryWrapper.last("and to_char(get_point_datime, 'yyyy-mm-dd') = '" + DateUtils.format(getPointDatime, Constant.DATE_FORMAT_YYYY_MM_DD_BARS) + "'");
        }else if (type == 3 || type == 6){
            // ④　付与ポイント区分＝「3：学習実績累計」　または　「6：出席簿登録時」の場合
            // 生徒付与ポイント履歴．付与ポイント日時　＞　取得したリセット日時
            // modify at 2021/8/30 for V9.02 by NWT HuangXL START
            // stuGetPointHstEntityCountQueryWrapper.last("and to_char(get_point_datime, 'yyyy-mm-dd') > '" + DateUtils.format(stuPointEntity.getResetDatime(), Constant.DATE_FORMAT_YYYY_MM_DD_BARS) + "'");
            lastStr.append("and to_char(get_point_datime, 'yyyy-mm-dd HH24:MI:SS.MS') > '").append(DateUtils.format(stuPointEntity.getResetDatime(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS)).append("'");
//            stuGetPointHstEntityCountQueryWrapper.last("and to_char(get_point_datime, 'yyyy-mm-dd HH24:MI:SS.MS') > '" + DateUtils.format(stuPointEntity.getResetDatime(), Constant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS) + "'");
            // modify at 2021/8/30 for V9.02 by NWT HuangXL END
        }
        //作成日時逆順に並べる
        lastStr.append(" ORDER BY cret_datime DESC limit 1");
        stuGetPointHstEntityCountQueryWrapper.last(lastStr.toString());
        StuGetPointHstEntity stuGetPointHstEntity = stuGetPointHstDaoAtomicReference.get().selectOne(stuGetPointHstEntityCountQueryWrapper);
        // modify at 2021/8/31 for V9.02 by NWT HuangXL END
        // modify at 2021/8/27 for V9.02 by NWT HuangXL END
        // modify at 2021/8/18 for V9.02 by NWT HuangXL END

        // 登録パラメータ設定
        int point = 0;
        switch (type) {
            case 1:
                //ポイント
                point = stuPointEntity.getPoint();
                stuPointEntity.setPoint(stuPointEntity.getPoint() + pointVo.getGoSchPoint());
                point = stuPointEntity.getPoint() - point;
                break;
            case 2:
                //学習実績登録ポイント
                point = stuPointEntity.getDoLoginPoint();
                stuPointEntity.setDoLoginPoint(stuPointEntity.getDoLoginPoint() + pointVo.getDoLoginPoint());
                point = stuPointEntity.getDoLoginPoint() - point;
                break;
            case 3:
                //学習実績累計ポイント
                // modify at 2021/8/20 for V9.02 by NWT HuangXL START
                // modify at 2021/8/27 for V9.02 by NWT HuangXL START
                point = pointVo.getDoTotalPoint() * step;
                stuPointEntity.setDoTotalPoint(point);
                // modify at 2021/8/27 for V9.02 by NWT HuangXL END
                // modify at 2021/8/20 for V9.02 by NWT HuangXL END
                break;
            case 4:
                //入室ポイント
                point = stuPointEntity.getInRoomPoint();
                stuPointEntity.setInRoomPoint(stuPointEntity.getInRoomPoint() + pointVo.getInRoomPoint());
                point = stuPointEntity.getInRoomPoint() - point;
                break;
            // add at 2021/8/13 for V9.02 by NWT HuangXL START
            case 6:
                //出席簿付与ポイント
                // modify at 2021/8/20 for V9.02 by NWT HuangXL START
                point = attendBookGetPointHstEntity.getPassScorePoint() + attendBookGetPointHstEntity.getFullScorePoint() + attendBookGetPointHstEntity.getHworkOutPoint() + attendBookGetPointHstEntity.getAbsLoginPoint();
                stuPointEntity.setAttendPoint(point);
                // modify at 2021/8/20 for V9.02 by NWT HuangXL END
                break;
            case 7:
                //誕生日時ポイント
                point = stuPointEntity.getBirthdayPoint();
                stuPointEntity.setBirthdayPoint(stuPointEntity.getBirthdayPoint() + pointVo.getBirthdayTimePoint());
                point = stuPointEntity.getBirthdayPoint() - point;
                break;
            // add at 2021/8/13 for V9.02 by NWT HuangXL END
            default:
                break;
        }
        // modify at 2021/8/20 for V9.02 by NWT HuangXL START
        //更新日時
        stuPointEntity.setUpdDatime(sysTimestamp);
        //更新ユーザＩＤ
        stuPointEntity.setUpdUsrId(operatorId);
        //削除フラグ
        stuPointEntity.setDelFlg(0);

        // modify at 2021/8/18 for V9.02 by NWT HuangXL START
        // modify at 2021/8/31 for V9.02 by NWT HuangXL START
// modify at 2021/8/27 for V9.02 by NWT HuangXL START
        // 4-1取得できる場合、且つ　付与ポイント区分が「6：出席簿登録時」の場合 または　「3：実行時間累計自動ポ」、生徒付与ポイント履歴を更新する。
        if (stuGetPointHstEntity != null && (type == 6 || type == 3)) {
//            StuGetPointHstEntity stuGetPointHstEntity = new StuGetPointHstEntity();
            //付与ポイント数
            stuGetPointHstEntity.setPointNum(point);
            //付与ポイント日時  システム時間
            stuGetPointHstEntity.setGetPointDatime(sysTimestamp);
            //更新日時
            stuGetPointHstEntity.setUpdDatime(sysTimestamp);
            //更新ユーザＩＤ
            stuGetPointHstEntity.setUpdUsrId(operatorId);
            // 「DBセット」シートのDBセット名「生徒付与ポイント履歴更新」
//            stuGetPointHstDaoAtomicReference.get().update(stuGetPointHstEntity, new UpdateWrapper<StuGetPointHstEntity>().eq("stu_id", stuId).eq("org_id", orgId).eq("point_type_div", Integer.toString(type)));
            stuGetPointHstDaoAtomicReference.get().updateById(stuGetPointHstEntity);
        }else if (stuGetPointHstEntity == null) {
            // 4-2取得できない場合、生徒付与ポイント履歴を登録する。
            stuGetPointHstEntity = new StuGetPointHstEntity();
            //組織ID
            stuGetPointHstEntity.setOrgId(orgId);
            //生徒ID
            stuGetPointHstEntity.setStuId(stuId);
            //付与ポイント区分
            stuGetPointHstEntity.setPointTypeDiv(Integer.toString(type));
            //付与ポイント数
            stuGetPointHstEntity.setPointNum(point);
            //付与ポイント日時
            stuGetPointHstEntity.setGetPointDatime(getPointDatime);
            //作成日時
            stuGetPointHstEntity.setCretDatime(sysTimestamp);
            //作成ユーザＩＤ
            stuGetPointHstEntity.setCretUsrId(operatorId);
            //更新日時
            stuGetPointHstEntity.setUpdDatime(sysTimestamp);
            //更新ユーザＩＤ
            stuGetPointHstEntity.setUpdUsrId(operatorId);
            //削除フラグ
            stuGetPointHstEntity.setDelFlg(0);
            // 「DBセット」シートのDBセット名「生徒付与ポイント履歴登録」
            stuGetPointHstDaoAtomicReference.get().insert(stuGetPointHstEntity);
        }else if (stuGetPointHstEntity != null && (type == 1 || type == 2 || type == 4 || type == 7)) {
            // 4-3取得できる場合、且つ　付与ポイント区分が「1、2、4、7」の場合、生徒付与ポイント処理を行わない。
            return;
        }
// modify at 2021/8/27 for V9.02 by NWT HuangXL END
        // 5生徒ポイントから該当生徒のポイントを抽出して、自動できポイントをプラスして、更新を行う。　
        if (stuPointEntity.getId() == null) {
            // 5-1取得できない場合  生徒ポイント新規
            stuPointDaoAtomicReference.get().insert(stuPointEntity);
        } else {
            // 5-2取得できる場合  生徒ポイント更新
            stuPointDaoAtomicReference.get().updateById(stuPointEntity);
        }
        // modify at 2021/8/31 for V9.02 by NWT HuangXL END
        // modify at 2021/8/18 for V9.02 by NWT HuangXL END
        // modify at 2021/8/20 for V9.02 by NWT HuangXL END
    }

    public static class PointVo {

        /**
         * 再認識不可時間
         */
        private int reRecoTime;

        /**
         * 登校自動ポイント
         */
        private int goSchPoint;

        /**
         * 実行登録時自動ポイント
         */
        private int doLoginPoint;

        /**
         * 実行時間累計自動ポイント
         */
        private int doTotalPoint;

        /**
         * 入室時自動ポイント
         */
        private int inRoomPoint;

        // add at 2021/8/13 for V9.02 by NWT HuangXL START
        /**
         * 合格時自動ポイント
         */
        private int paddPoint;

        /**
         * 満点時自動ポイント
         */
        private int fullMarksPoint;

        /**
         * 宿題提出時自動ポイント
         */
        private int workOutPoint;

        /**
         * 出席登録時自動ポイント
         */
        private int attentOutPoint;

        /**
         * 誕生日時自動ポイント
         */
        private int birthdayTimePoint;
        // add at 2021/8/13 for V9.02 by NWT HuangXL END

        /**
         * 入退室各種設定
         */
        private MstVariousSetEntity mstVariousSetEntity;

        public PointVo() {
            //（デフォルト値は10です）
            this.reRecoTime = 10;
            //（デフォルト値は0です）
            this.goSchPoint = 0;
            //（デフォルト値は0です）
            this.doLoginPoint = 0;
            //（デフォルト値は0です）
            this.doTotalPoint = 0;
            //（デフォルト値は0です）
            this.inRoomPoint = 0;
            // add at 2021/8/13 for V9.02 by NWT HuangXL START
            //（デフォルト値は0です）
            this.paddPoint = 0;
            //（デフォルト値は0です）
            this.fullMarksPoint = 0;
            //（デフォルト値は0です）
            this.workOutPoint = 0;
            //（デフォルト値は0です）
            this.attentOutPoint = 0;
            //（デフォルト値は0です）
            this.birthdayTimePoint = 0;
            // add at 2021/8/13 for V9.02 by NWT HuangXL END
            mstVariousSetEntity = null;
        }

        /**
         * 再認識不可時間を取得する
         *
         * @return reRecoTime 再認識不可時間
         */
        public int getReRecoTime() {
            return this.reRecoTime;
        }

        /**
         * 再認識不可時間を設定する
         *
         * @param reRecoTime 再認識不可時間
         */
        public void setReRecoTime(int reRecoTime) {
            this.reRecoTime = reRecoTime;
        }

        /**
         * 登校自動ポイントを取得する
         *
         * @return goSchPoint 登校自動ポイント
         */
        public int getGoSchPoint() {
            return this.goSchPoint;
        }

        /**
         * 登校自動ポイントを設定する
         *
         * @param goSchPoint 登校自動ポイント
         */
        public void setGoSchPoint(int goSchPoint) {
            this.goSchPoint = goSchPoint;
        }

        /**
         * 実行登録時自動ポイントを取得する
         *
         * @return doLoginPoint 実行登録時自動ポイント
         */
        public int getDoLoginPoint() {
            return this.doLoginPoint;
        }

        /**
         * 実行登録時自動ポイントを設定する
         *
         * @param doLoginPoint 実行登録時自動ポイント
         */
        public void setDoLoginPoint(int doLoginPoint) {
            this.doLoginPoint = doLoginPoint;
        }

        /**
         * 実行時間累計自動ポイントを取得する
         *
         * @return doTotalPoint 実行時間累計自動ポイント
         */
        public int getDoTotalPoint() {
            return this.doTotalPoint;
        }

        /**
         * 実行時間累計自動ポイントを設定する
         *
         * @param doTotalPoint 実行時間累計自動ポイント
         */
        public void setDoTotalPoint(int doTotalPoint) {
            this.doTotalPoint = doTotalPoint;
        }

        /**
         * 入室時自動ポイントを取得する
         *
         * @return inRoomPoint 入室時自動ポイント
         */
        public int getInRoomPoint() {
            return this.inRoomPoint;
        }

        /**
         * 入室時自動ポイントを設定する
         *
         * @param inRoomPoint 入室時自動ポイント
         */
        public void setInRoomPoint(int inRoomPoint) {
            this.inRoomPoint = inRoomPoint;
        }

        /**
         * 入退室各種設定を取得する
         *
         * @return mstVariousSetEntity 入退室各種設定
         */
        public MstVariousSetEntity getMstVariousSetEntity() {
            return this.mstVariousSetEntity;
        }

        /**
         * 入退室各種設定を設定する
         *
         * @param mstVariousSetEntity 入退室各種設定
         */
        public void setMstVariousSetEntity(MstVariousSetEntity mstVariousSetEntity) {
            this.mstVariousSetEntity = mstVariousSetEntity;
        }

        // add at 2021/8/13 for V9.02 by NWT HuangXL START

        /**
         * 合格時自動ポイントを取得する
         *
         * @return paddPoint 合格時自動ポイント
         */
        public int getPaddPoint() {
            return this.paddPoint;
        }

        /**
         * 合格時自動ポイントを設定する
         *
         * @param paddPoint 合格時自動ポイント
         */
        public void setPaddPoint(int paddPoint) {
            this.paddPoint = paddPoint;
        }

        /**
         * 満点時自動ポイントを取得する
         *
         * @return fullMarksPoint 満点時自動ポイント
         */
        public int getFullMarksPoint() {
            return this.fullMarksPoint;
        }

        /**
         * 満点時自動ポイントを設定する
         *
         * @param fullMarksPoint 満点時自動ポイント
         */
        public void setFullMarksPoint(int fullMarksPoint) {
            this.fullMarksPoint = fullMarksPoint;
        }

        /**
         * 宿題提出時自動ポイントを取得する
         *
         * @return workOutPoint 宿題提出時自動ポイント
         */
        public int getWorkOutPoint() {
            return this.workOutPoint;
        }

        /**
         * 宿題提出時自動ポイントを設定する
         *
         * @param workOutPoint 宿題提出時自動ポイント
         */
        public void setWorkOutPoint(int workOutPoint) {
            this.workOutPoint = workOutPoint;
        }

        /**
         * 出席登録時自動ポイントを取得する
         *
         * @return attentOutPoint 出席登録時自動ポイント
         */
        public int getAttentOutPoint() {
            return this.attentOutPoint;
        }

        /**
         * 出席登録時自動ポイントを設定する
         *
         * @param attentOutPoint 出席登録時自動ポイント
         */
        public void setAttentOutPoint(int attentOutPoint) {
            this.attentOutPoint = attentOutPoint;
        }

        /**
         * 誕生日時自動ポイントを取得する
         *
         * @return birthdayTimePoint 誕生日時自動ポイント
         */
        public int getBirthdayTimePoint() {
            return this.birthdayTimePoint;
        }

        /**
         * 誕生日時自動ポイントを設定する
         *
         * @param birthdayTimePoint 誕生日時自動ポイント
         */
        public void setBirthdayTimePoint(int birthdayTimePoint) {
            this.birthdayTimePoint = birthdayTimePoint;
        }
        // add at 2021/8/13 for V9.02 by NWT HuangXL END
    }

    /**
     * daoエンティティクラスを初期化します
     */
    private static void setUpMstVariousSetDao() {
        if (mstVariousSetDaoAtomicReference.get() == null) {
            synchronized (mstVariousSetDaoAtomicReference) {
                if (mstVariousSetDaoAtomicReference.get() == null) {
                    mstVariousSetDaoAtomicReference.set((MstVariousSetDao) SpringContextUtils.getBean("mstVariousSetDao"));
                }
            }
        }
    }

    /**
     * daoエンティティクラスを初期化します
     */
    private static void setUpStuPointDao() {
        if (stuPointDaoAtomicReference.get() == null) {
            synchronized (stuPointDaoAtomicReference) {
                if (stuPointDaoAtomicReference.get() == null) {
                    stuPointDaoAtomicReference.set((StuPointDao) SpringContextUtils.getBean("stuPointDao"));
                }
            }
        }
    }

    /**
     * daoエンティティクラスを初期化します
     */
    private static void setUpStuGetPointHstDao() {
        if (stuGetPointHstDaoAtomicReference.get() == null) {
            synchronized (stuGetPointHstDaoAtomicReference) {
                if (stuGetPointHstDaoAtomicReference.get() == null) {
                    stuGetPointHstDaoAtomicReference.set((StuGetPointHstDao) SpringContextUtils.getBean("stuGetPointHstDao"));
                }
            }
        }
    }

    /**
     * daoエンティティクラスを初期化します
     */
    private static void setUpStuWeeklyPlanPerfDao() {
        if (stuWeeklyPlanPerfDaoAtomicReference.get() == null) {
            synchronized (stuWeeklyPlanPerfDaoAtomicReference) {
                if (stuWeeklyPlanPerfDaoAtomicReference.get() == null) {
                    stuWeeklyPlanPerfDaoAtomicReference.set((StuWeeklyPlanPerfDao) SpringContextUtils.getBean("stuWeeklyPlanPerfDao"));
                }
            }
        }
    }

    // add at 2021/8/13 for V9.02 by NWT HuangXL START

    /**
     * daoエンティティクラスを初期化します
     */
    private static void setUpAttendBookGetPointHstDao() {
        if (attendBookGetPointHstDaoAtomicReference.get() == null) {
            synchronized (attendBookGetPointHstDaoAtomicReference) {
                if (attendBookGetPointHstDaoAtomicReference.get() == null) {
                    attendBookGetPointHstDaoAtomicReference.set((AttendBookGetPointHstDao) SpringContextUtils.getBean("attendBookGetPointHstDao"));
                }
            }
        }
    }
    // add at 2021/8/13 for V9.02 by NWT HuangXL END
}
