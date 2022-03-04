package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.StuPointDao;
import jp.learningpark.modules.common.entity.StuPointEntity;
import jp.learningpark.modules.manager.dao.F09011Dao;
import jp.learningpark.modules.manager.dto.F09011SaveDto;
import jp.learningpark.modules.manager.dto.F09011StuDto;
import jp.learningpark.modules.manager.dto.F09011StuPointDto;
import jp.learningpark.modules.manager.service.F09011Service;
import jp.learningpark.modules.sys.entity.SysUserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>ポイント設定確認画面Service</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/11/10 : zmh: 新規<br />
 * @version 1.0
 */
@Service
public class F09011ServiceImpl implements F09011Service {

    private final F09011Dao f09011Dao;

    private final StuPointDao stuPointDao;

    public F09011ServiceImpl(F09011Dao f09011Dao, StuPointDao stuPointDao) {
        this.f09011Dao = f09011Dao;
        this.stuPointDao = stuPointDao;
    }

    @Override
    public R init(List<String> stuIds, Integer limit, Integer page) {
        SysUserEntity currentUser = ShiroUtils.getUserEntity();

        List<F09011StuDto> stuInfo = f09011Dao.selectStuInfoListByStuId(currentUser, stuIds, limit, (page - 1) * limit);
        // 上記取得できない場合、画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0017）を表示する。
        if (stuInfo.isEmpty()){
            throw new RRException(MessageUtils.getMessage("MSGCOMN0017", "ユーザー"));
        }
        Integer totalCount = f09011Dao.selectStuInfoListByStuIdCount(currentUser, stuIds);

        // 学生ポイント情報の更新時刻を取得する
        // その後の提出時に排他ロックの判断に使用されます
        List<F09011StuPointDto> existStuPoint =  f09011Dao.selectStuUpdateTime(stuIds);

        existStuPoint.forEach(p -> p.setUpdDatimeFormat(
                DateUtils.format(p.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS)));

        return R.ok().put("page", new PageUtils(stuInfo, totalCount, limit, page))
                     .put("stuList", existStuPoint);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(F09011SaveDto saveDto) {
        List<String> stuIds = saveDto.getStuIdList();
        List<F09011StuPointDto> latestStuPoint = f09011Dao.selectStuUpdateTime(stuIds);

        // 排他チェックエラーの場合、処理を中断し、エラー内容を画面の上部に表示する
        tryExclusiveLockCheck(saveDto.getOldStuPoint(), latestStuPoint);

        Timestamp latestTime = DateUtils.getSysTimestamp();

        // 既存の生徒ポイントに更新
        stuPointUpdate(latestStuPoint, saveDto, latestTime);

        // 生徒ポイント登録
        stuPointInsert(saveDto, latestTime);
    }

    /**
     * 既存の生徒ポイントに更新
     */
    private void stuPointUpdate(List<F09011StuPointDto> latestStuPoint,
                                F09011SaveDto saveDto, Timestamp latestTime){
        Integer point = saveDto.getPoint();
        List<String> stuIds = saveDto.getStuIdList();
        String usrId = ShiroUtils.getUserId();

        for (F09011StuPointDto stuPoint : latestStuPoint) {
            // ポイント　＋　引渡データ．付与ポイント
            // 2020/12/4 huangxinliang modify start
            stuPoint.setMovePoint(stuPoint.getMovePoint() + point);
            /* 2021/01/22 cuikailin add start */
            if (point > 0){
                stuPoint.setMovePointAdd(stuPoint.getMovePointAdd()== null?0:stuPoint.getMovePointAdd() + point);
            }
            /* 2021/01/22 cuikailin add end */
            // 2020/12/4 huangxinliang modify end
            stuPoint.setUpdDatime(latestTime);
            stuPoint.setUpdUsrId(usrId);
            stuPointDao.updateById(stuPoint);
            stuIds.remove(stuPoint.getStuId());
        }
    }

    /**
     * 生徒ポイント登録
     */
    private void stuPointInsert(F09011SaveDto saveDto, Timestamp latestTime){
        Integer point = saveDto.getPoint();
        List<String> stuIds = saveDto.getStuIdList();
        String usrId = ShiroUtils.getUserId();
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        // 生徒ポイントへ登録する
        for (String stuId : stuIds) {
            StuPointEntity newStuPoint = new StuPointEntity();
            // 生徒ID
            newStuPoint.setStuId(stuId);
            // 組織ID
            newStuPoint.setOrgId(orgId);
            // ポイント
            newStuPoint.setMovePoint(point);
            // 調整ポイントの累計(整数の場合)
            newStuPoint.setMovePointAdd(point>0?point:0);
            // 作成日時
            newStuPoint.setCretDatime(latestTime);
            // 作成ユーザＩＤ
            newStuPoint.setCretUsrId(usrId);
            // 更新日時
            newStuPoint.setUpdDatime(latestTime);
            // 更新ユーザＩＤ
            newStuPoint.setUpdUsrId(usrId);
            // 削除フラグ
            newStuPoint.setDelFlg(0);
            stuPointDao.insert(newStuPoint);
        }
    }

    /**
     * 排他的ロックチェックを試してください
     * 排他チェックエラーの場合、処理を中断し、エラー内容を画面の上部に表示する
     */
    private void tryExclusiveLockCheck(List<F09011StuPointDto> old,
                                       List<F09011StuPointDto> latest){
        if (old.size() != latest.size()){
            throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
        }

        // 排他チェックエラーの場合
        for (int i = 0; i < old.size(); i++) {
            F09011StuPointDto oldPoint = old.get(i);
            F09011StuPointDto latestPoint = latest.get(i);

            String latestTime = DateUtils.format(latestPoint.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            if (!oldPoint.getUpdDatimeFormat().equals(latestTime)){
                throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
            }
        }
    }
}
