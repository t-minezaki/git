/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.ExcelUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.MstUnitDao;
import jp.learningpark.modules.common.dao.TextbDefTimeInfoDao;
import jp.learningpark.modules.common.entity.MstUnitEntity;
import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;
import jp.learningpark.modules.manager.dao.F02001Dao;
import jp.learningpark.modules.manager.dto.F02001Dto;
import jp.learningpark.modules.manager.service.F02001Service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>F02001 単元情報インポート・エクスポート画面 ServiceImpl</p>
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/01/03 : wen: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F02001ServiceImpl implements F02001Service {

    /**
     * F02001 Dao
     */
    @Autowired
    F02001Dao f02001Dao;

    /**
     * MstUnitDao Dao
     */
    @Autowired
    MstUnitDao mstUnitDao;

    /**
     * MstUnitDao Dao
     */
    @Autowired
    TextbDefTimeInfoDao textbDefTimeInfoDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>単元情報を取得する</p>
     *
     * @param orgId      組織ID
     * @param schyDiv    学年コード
     * @param subjtDiv   教科コード
     * @param upOrgId    上層組織リスト
     * @param lowerOrgId 下層組織リスト
     * @return 単元情報
     */
    @Override
    public List<F02001Dto> getMstUnitInfo(String orgId, String schyDiv, String subjtDiv, List<String> upOrgId, List<String> lowerOrgId) {
        return f02001Dao.selectMstUnitInfo(orgId, schyDiv, subjtDiv, upOrgId, lowerOrgId);
    }

    /**
     * <p>インポート</p>
     *
     * @param file ファイル
     * @param type 新規・修正（エラーとする）または新規・修正（上書き）選択フラッグ
     * @return
     */
    @Override
    public R importFile(MultipartFile file, Integer type) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ユーザId
        String usrId = ShiroUtils.getUserId();
        //ファイル名を取得
        String fileName = file.getOriginalFilename();
        Cell cell = null;
        //判断ファイルのタイプ
        if (!fileName.matches("^.+\\.(?i)(xlsx)$")) {
            return R.error(MessageUtils.getMessage("MSGCOMN0076", "xlsx"));
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
        }
        Workbook wb = null;
        if (isExcel2003) {
            try {
                wb = new HSSFWorkbook(inputStream);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
            }

        } else {
            try {
                wb = new XSSFWorkbook(inputStream);
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new RRException(MessageUtils.getMessage("MSGCOMN0091"));
            }
        }
        //アクセスアップロードファイルの最初のシート
        Sheet sheet = wb.getSheetAt(0);

        String title[] = new String[]{"自動採番ID\n※修正、削除時、必須", "組織ID\n※新規修正時、必須", "学年コード\n※新規修正時、必須\n(コードマスタ＿参照)", "教科コード\n※新規修正時、必須\n(コードマスタ＿参照)", "単元管理CD", "章名\n※新規修正時、必須", "節名", "項目名\n※新規修正時、必須"};
        if (!ExcelUtils.isApplicable(sheet.getRow(0), title)) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0078", "単元情報"));
        }

        List<TextbDefTimeInfoEntity> f02001GetUnitInfoDtoList = null;
        MstUnitEntity mstUnitEntityAdd = null;
        if (sheet.getLastRowNum() != 0) {
            //1行をから遍歴し、論理を処理する
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (ExcelUtils.isEmptyRow(row, title.length)) {
                    return R.ok();
                }
                //組織ID空白チェック
                if (row.getCell(1) == null || StringUtils.isEmpty(row.getCell(1).toString())) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0063", i + 1 + "", sheet.getRow(0).getCell(1).getStringCellValue()));
                }
                //本組織チェック
                if (!(StringUtils.equals(row.getCell(1).getStringCellValue(), orgId))) {
                    throw new RRException(MessageUtils.getMessage("MSGCOMN0071", i + 1 + "", sheet.getRow(0).getCell(1).getStringCellValue(), "本組織"));
                }
                if (type != 2) {//削除しない
                    if (row.getCell(2) == null || StringUtils.isEmpty(row.getCell(2).toString())) {
                        //学年区分空白の場合
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0063", i + 1 + "", sheet.getRow(0).getCell(2).getStringCellValue()));
                    }
                    if (row.getCell(3) == null || StringUtils.isEmpty(row.getCell(3).toString())) {
                        //教科区分空白の場合
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0063", i + 1 + "", sheet.getRow(0).getCell(3).getStringCellValue()));
                    }
                    if (row.getCell(7) == null || StringUtils.isEmpty(row.getCell(7).toString())) {
                        //単元名空白の場合
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0063", i + 1 + "", sheet.getRow(0).getCell(7).getStringCellValue()));
                    }
                    //新規
                    if (row.getCell(0) == null || StringUtils.isEmpty(row.getCell(0).toString())) {
                        mstUnitEntityAdd = new MstUnitEntity();
                        //組織ID
                        if (row.getCell(1) != null) {
                            cell = row.getCell(1);
                            cell.setCellType(CellType.STRING);
                            mstUnitEntityAdd.setOrgId(row.getCell(1).getStringCellValue());
                        } else {
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0063", i + 1 + "", sheet.getRow(0).getCell(1).getStringCellValue()));
                        }
                        //学年区分
                        if (row.getCell(2) != null) {
                            cell = row.getCell(2);
                            cell.setCellType(CellType.STRING);
                            mstUnitEntityAdd.setSchyDiv(row.getCell(2).getStringCellValue());
                        }
                        //教科区分
                        mstUnitEntityAdd.setSubjtDiv(row.getCell(3).toString());
                        //単元管理コード
                        if (row.getCell(4) != null) {
                            cell = row.getCell(4);
                            cell.setCellType(CellType.STRING);
                            mstUnitEntityAdd.setUnitMstCd(row.getCell(4).getStringCellValue());
                        }
                        //章名
                        mstUnitEntityAdd.setChaptNm(row.getCell(5).toString());
                        //節名
                        mstUnitEntityAdd.setSectnNm(row.getCell(6).toString());
                        //単元名
                        mstUnitEntityAdd.setUnitNm(row.getCell(7).toString());
                        //作成日時
                        mstUnitEntityAdd.setCretDatime(DateUtils.getSysTimestamp());
                        //作成ユーザＩＤ
                        mstUnitEntityAdd.setCretUsrId(usrId);
                        //更新日時
                        mstUnitEntityAdd.setUpdDatime(DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ
                        mstUnitEntityAdd.setUpdUsrId(usrId);
                        //削除フラグ
                        mstUnitEntityAdd.setDelFlg(0);
                        try {
                            mstUnitDao.insert(mstUnitEntityAdd);
                        } catch (Exception e) {
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0079", i + 1 + ""));
                        }
                    } else {
                        if (StringUtils.equals(row.getCell(0).getCellType().toString(), "STRING")) {
                            mstUnitEntityAdd = mstUnitDao.selectById(Integer.parseInt(row.getCell(0).getStringCellValue()));
                        } else {
                            mstUnitEntityAdd = mstUnitDao.selectById((int) (row.getCell(0).getNumericCellValue()));
                        }
                        if (mstUnitEntityAdd == null) {
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0068", i + 1 + "", "ID（システム採番）"));
                        }
                        //エラーとする
                        if (type == 0) {
                            //id
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0065", i + 1 + "", "ID（システム採番）"));
                        }
                        //上書き
                        if (type == 1) {
                            //組織ID
                            mstUnitEntityAdd.setOrgId(row.getCell(1).getStringCellValue());
                            //学年区分
                            if (StringUtils.equals(row.getCell(2).getCellType().toString(), "STRING")) {
                                mstUnitEntityAdd.setSchyDiv(row.getCell(2).getStringCellValue());
                            } else {
                                row.getCell(2).setCellType(CellType.STRING);
                                mstUnitEntityAdd.setSchyDiv(row.getCell(2).getStringCellValue());
                            }
                            //教科区分
                            if (StringUtils.equals(row.getCell(3).getCellType().toString(), "STRING")) {
                                mstUnitEntityAdd.setSubjtDiv(row.getCell(3).getStringCellValue());
                            } else {
                                row.getCell(3).setCellType(CellType.STRING);
                                mstUnitEntityAdd.setSubjtDiv(row.getCell(3).getStringCellValue());
                            }
                            //単元管理コード
                            if (StringUtils.equals(row.getCell(4).getCellType().toString(), "STRING")) {
                                mstUnitEntityAdd.setUnitMstCd(row.getCell(4).getStringCellValue());
                            } else {
                                row.getCell(4).setCellType(CellType.STRING);
                                mstUnitEntityAdd.setUnitMstCd(row.getCell(4).getStringCellValue());
                            }
                            //章名
                            mstUnitEntityAdd.setChaptNm(row.getCell(5).toString());
                            //節名
                            mstUnitEntityAdd.setSectnNm(row.getCell(6).toString());
                            //単元名
                            mstUnitEntityAdd.setUnitNm(row.getCell(7).toString());
                            //作成日時
                            mstUnitEntityAdd.setCretDatime(DateUtils.getSysTimestamp());
                            //作成ユーザＩＤ
                            mstUnitEntityAdd.setCretUsrId(usrId);
                            //更新日時
                            mstUnitEntityAdd.setUpdDatime(DateUtils.getSysTimestamp());
                            //更新ユーザＩＤ
                            mstUnitEntityAdd.setUpdUsrId(usrId);
                            //削除フラグ
                            mstUnitEntityAdd.setDelFlg(0);
                            try {
                                mstUnitDao.updateById(mstUnitEntityAdd);
                            } catch (Exception e) {
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0079", i + 1 + ""));
                            }
                        }
                    }
                }
                //削除選択した場合、
                else {
                    //削除の場合
                    if (row.getCell(0) == null || StringUtils.isEmpty(row.getCell(0).toString())) {
                        //IDが存在しない場合
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0063", i + 1 + "", sheet.getRow(0).getCell(0).getStringCellValue()));
                    } else {
                        if (StringUtils.equals(row.getCell(0).getCellType().toString(), "STRING")) {
                            mstUnitEntityAdd = mstUnitDao.selectById(Integer.parseInt(row.getCell(0).getStringCellValue()));
                            f02001GetUnitInfoDtoList = textbDefTimeInfoDao.selectList(new QueryWrapper<TextbDefTimeInfoEntity>().and(w -> w.eq("unit_id", Integer.parseInt(row.getCell(0).getStringCellValue())).eq("del_flg", 0)));
                        } else {
                            mstUnitEntityAdd = mstUnitDao.selectById((int) (row.getCell(0).getNumericCellValue()));
                            f02001GetUnitInfoDtoList = textbDefTimeInfoDao.selectList(new QueryWrapper<TextbDefTimeInfoEntity>().and(w -> w.eq("unit_id", (int) row.getCell(0).getNumericCellValue()).eq("del_flg", 0)));
                        }
                        if (mstUnitEntityAdd == null) {
                            //システム採番ＩＤの単元マスタ存在チェック
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0068", i + 1 + "", sheet.getRow(0).getCell(0).getStringCellValue()));
                        }
                        if (f02001GetUnitInfoDtoList.size() > 0) {
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0061", (i + 1) + "行目単元情報"));
                        }
                        mstUnitEntityAdd.setUpdDatime(DateUtils.getSysTimestamp());
                        mstUnitEntityAdd.setUpdUsrId(usrId);
                        mstUnitEntityAdd.setDelFlg(1);
                        try {
                            mstUnitDao.updateById(mstUnitEntityAdd);
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
            }
        }
        try {
            if (wb != null) {
                wb.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * <p>下層組織リストを取得する</p>
     *
     * @param orgId 組織ID
     * @return
     */
    @Override
    public List getLowerOrg(String orgId) {
        return f02001Dao.selectLowerOrg(orgId);
    }

    /**
     * <p>上層組織リストを取得する</p>
     *
     * @param orgId 組織ID
     * @return
     */
    @Override
    public List getUpOrg(String orgId) {
        return f02001Dao.selectUpOrg(orgId);
    }
}
