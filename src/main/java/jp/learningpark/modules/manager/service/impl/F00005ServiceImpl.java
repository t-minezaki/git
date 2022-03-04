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
import jp.learningpark.modules.common.dao.MstGrpDao;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.dao.StuGrpDao;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.entity.StuGrpEntity;
import jp.learningpark.modules.manager.dao.F00005Dao;
import jp.learningpark.modules.manager.dto.F00005Dto;
import jp.learningpark.modules.manager.service.F00005Service;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>F00005_生徒グループ管理画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/25 : gong: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F00005ServiceImpl implements F00005Service {
    /**
     * グループマスタ Dao
     */
    @Autowired
    private MstGrpDao mstGrpDao;

    /**
     * 会員-グループマスタ dao
     */
    @Autowired
    private StuGrpDao stuGrpDao;

    /**
     * ユーザ基本マスタ Dao
     */
    @Autowired
    private MstUsrDao mstUsrDao;

    /**
     * F00005_生徒グループ管理画面
     */
    @Autowired
    private F00005Dao f00005Dao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * DBの生徒グループ管理、ユーザ基本マスタから⑧で選択した保存先に情報を出力ファイルに出力して保存する。
     *
     * @param orgId 組織ID
     * @return
     */
    @Override
    public List<F00005Dto> getList(String orgId) {
        return f00005Dao.selectList(orgId);
    }

    /**
     * グループ情報のインポート
     *
     * @param file ファイル
     * @param type エラーとする or 上書きする
     * @return
     */
    @Override
    public R importFile(MultipartFile file, Integer type) {
        //ブランドコード
        //String brandCd = ShiroUtils.getBrandcd();
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ユーザId
        String usrId = ShiroUtils.getUserId();
        //ファイル名を取得
        String fileName = file.getOriginalFilename();
        //
        Cell cell;
        //判断ファイルのタイプ
        if (!fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0076", "xlsx"));
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

        //ファイルのチェック  　20200521 NWT文 曜日区分を追加
        String[] title = new String[] {"グループID（システム採番）", "グループ名", "曜日区分"};
        if (sheet == null || !ExcelUtils.isApplicable(sheet.getRow(1), title)) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0078", "'グループ情報"));
        }

        String yobiDiv;
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        String divs = "";
        String divss = "[,1-7]*";
        //DBへグループIDの存在チェック行う
        MstGrpEntity mstGrpEntity;
        if (sheet.getLastRowNum() != 0) {
            //2行をから遍歴し、論理を処理する
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row r = sheet.getRow(i);
                if (ExcelUtils.isEmptyRow(r, title.length)) {
                    return R.ok();
                }
                if (r.getCell(1) == null || StringUtils.isEmpty(r.getCell(1).toString())) {
                    throw new RRException((i + 1) + "行目のグループ名は必須入力項目です。");
                }
                if (r.getCell(2) != null && !StringUtils.isEmpty(r.getCell(2).toString())) {
                    if (StringUtils.equals("NUMERIC",r.getCell(2).getCellType().toString())) {
                        r.getCell(2).setCellType(CellType.STRING);
                    }
                    yobiDiv = r.getCell(2).toString();
                    String div = "";
                    if (yobiDiv.matches(divss)) {
                        for (int j = 0; j < yobiDiv.length(); j++) {
                            if (pattern.matcher(yobiDiv.charAt(j) + "").matches() && Integer.parseInt(yobiDiv.charAt(j)+"") <= 7) {
                                if (j == yobiDiv.length() -1) {
                                    div += yobiDiv.charAt(j) + "";
                                } else {
                                    div += yobiDiv.charAt(j) + ",";
                                }
                            }
                        }
                    } else {
                        throw new RRException((i + 1) + "行目の曜日区分が間違っています。");
                    }
                    List<String> yobi = Arrays.asList(div.split(","));
                    List<String> listWithoutDuplicates = yobi.stream().distinct().collect(Collectors.toList());
                    Collections.sort(listWithoutDuplicates);
                    divs = StringUtils.join(listWithoutDuplicates.toArray(), ',');
                }
                if (type != 2) {
                    //新規
                    if (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString())) {
                        mstGrpEntity = new MstGrpEntity();
                        //組織ID
                        mstGrpEntity.setOrgId(orgId);
                        //曜日区分
                        mstGrpEntity.setDayweekDiv(divs);
                        //グループ名
                        mstGrpEntity.setGrpNm(r.getCell(1).toString());
                        //作成日時
                        mstGrpEntity.setCretDatime(DateUtils.getSysTimestamp());
                        //作成ユーザＩＤ
                        mstGrpEntity.setCretUsrId(usrId);
                        //更新日時
                        mstGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ
                        mstGrpEntity.setUpdUsrId(usrId);
                        //削除フラグ
                        mstGrpEntity.setDelFlg(0);
                        try {
                            mstGrpDao.insert(mstGrpEntity);
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
                        }
                    }
                    //修正
                    else {
                        if (StringUtils.equals(r.getCell(0).getCellType().toString(), "STRING")) {
                            mstGrpEntity = mstGrpDao.selectById(Integer.parseInt(r.getCell(0).getStringCellValue()));
                        } else {
                            mstGrpEntity = mstGrpDao.selectById((int)(r.getCell(0).getNumericCellValue()));
                        }

                        if (mstGrpEntity == null) {
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", "グループID（システム採番）"));
                        }
                        //上書き
                        if (type == 0) {
                            mstGrpEntity = new MstGrpEntity();
                            //グループid
                            if (StringUtils.equals(r.getCell(0).getCellType().toString(), "STRING")) {
                                mstGrpEntity.setGrpId(Integer.parseInt(r.getCell(0).getStringCellValue()));
                            } else {
                                mstGrpEntity.setGrpId((int)(r.getCell(0).getNumericCellValue()));
                            }

                            //グループ名
                            mstGrpEntity.setGrpNm(r.getCell(1).toString());
                            //曜日区分
                            mstGrpEntity.setDayweekDiv(divs);
                            //更新日時
                            mstGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                            //更新ユーザＩＤ
                            mstGrpEntity.setUpdUsrId(usrId);
                            try {
                                mstGrpDao.updateById(mstGrpEntity);
                            } catch (Exception e) {
                                logger.error(e.getMessage());
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
                            }
                        }
                        //エラーとする
                        if (type == 1) {
                            //id
                            if (mstGrpEntity != null) {
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0065", (i + 1) + "", "グループID（システム採番）"));
                            }
                        }
                    }
                }
                //削除選択した場合、
                else {
                    if (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString())) {
                        throw new RRException((i + 1) + "行目のグループID（システム採番）は必須入力項目です。");
                    }

                    if (StringUtils.equals(r.getCell(0).getCellType().toString(), "STRING")) {
                        mstGrpEntity = mstGrpDao.selectById(Integer.parseInt(r.getCell(0).getStringCellValue()));
                    } else {
                        mstGrpEntity = mstGrpDao.selectById((int)(r.getCell(0).getNumericCellValue()));
                    }

                    if (mstGrpEntity == null) {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", "グループID（システム採番）"));
                    }
                    //del_flg
                    mstGrpEntity.setDelFlg(1);
                    //更新日時
                    mstGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    mstGrpEntity.setUpdUsrId(usrId);
                    try {
                        mstGrpDao.updateById(mstGrpEntity);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
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
     * 生徒・グループ情報のインポート
     *
     * @param file ファイル
     * @param type エラーとする or 上書きする
     * @return
     */
    @Override
    public R importFileStuGrp(MultipartFile file, Integer type) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ユーザＩＤ
        String usrId = ShiroUtils.getUserId();
        //ファイル名を取得
        String fileName = file.getOriginalFilename();
        //判断ファイルのタイプ
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0064", "xlsx"));
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
        StuGrpEntity stuGrpEntity;
        MstGrpEntity mstGrpEntity;
        MstUsrEntity usrEntity;
        //アクセスアップロードファイルの最初のシート
        Sheet sheet = wb.getSheetAt(0);

        //ファイルのチェック
        String[] title = new String[] {"会員-グループID\n（システム採番）", "生徒会員ID", "グループID"};
        if (sheet == null || !ExcelUtils.isApplicable(sheet.getRow(1), title)) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0078", "'生徒・グループ情報"));
        }
        if (sheet.getLastRowNum() != 0) {
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row r = sheet.getRow(i);
                if (ExcelUtils.isEmptyRow(r, title.length)) {
                    return R.ok();
                }
                //新規・修正（エラーとする）または新規・修正（上書き）
                if (type != 2) {
                    if (r.getCell(1) == null || StringUtils.isEmpty(r.getCell(1).toString())) {
                        throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(1).toString()+"は必須入力項目です。");
                    }
                    if ( r.getCell(2) == null || StringUtils.isEmpty(r.getCell(2).toString())) {
                        throw new RRException((i + 1) + "行目の"+sheet.getRow(1).getCell(2).toString()+"は必須入力項目です。");
                    }

                    //（B列）生徒会員IDの存在チェック行う
                    usrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(
                            w->w.eq("after_usr_id", r.getCell(1).toString()).eq("org_id", orgId).eq("usr_sts", "1").eq("del_flg", 0)));
                    if (usrEntity == null) {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", "生徒会員ID"));
                    }

                    //グループID
                    if (StringUtils.equals(r.getCell(2).getCellType().toString(), "STRING")) {
                        mstGrpEntity = mstGrpDao.selectById(Integer.parseInt(r.getCell(2).getStringCellValue()));
                    } else {
                        mstGrpEntity = mstGrpDao.selectById((int)(r.getCell(2).getNumericCellValue()));
                    }
                    if (mstGrpEntity == null || !StringUtils.equals(mstGrpEntity.getOrgId(), orgId)) {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", "グループID"));
                    }

                    //生徒グループ関係登録済みチェック行う
                    String usrId1 = usrEntity.getUsrId();
                    if (StringUtils.equals(r.getCell(2).getCellType().toString(), "STRING")) {
                        int grpId = Integer.parseInt(r.getCell(2).getStringCellValue());
                        stuGrpEntity = stuGrpDao.selectOne(
                                new QueryWrapper<StuGrpEntity>().and(w->w.eq("stu_id", usrId1).eq("grp_id", grpId).eq("del_flg", 0)));
                    } else {
                        int grpId = (int)r.getCell(2).getNumericCellValue();
                        stuGrpEntity = stuGrpDao.selectOne(
                                new QueryWrapper<StuGrpEntity>().and(w->w.eq("stu_id", usrId1).eq("grp_id", grpId).eq("del_flg", 0)));
                    }
                    //新規
                    if (stuGrpEntity != null && (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString()))) {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0085", (i + 1) + ""));
                    }
                    //修正
                    if (stuGrpEntity != null && r.getCell(0) != null && !StringUtils.isEmpty(r.getCell(0).toString())) {
                        if (StringUtils.equals(r.getCell(0).getCellType().toString(), "STRING")) {
                            int id = Integer.parseInt(r.getCell(0).getStringCellValue());
                            if (id != stuGrpEntity.getId()) {
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0085", (i + 1) + ""));
                            }
                        } else {
                            int id = (int)r.getCell(0).getNumericCellValue();
                            if (id != stuGrpEntity.getId()) {
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0085", (i + 1) + ""));
                            }
                        }
                    }

                    //新規
                    if (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString())) {
                        stuGrpEntity = new StuGrpEntity();
                        //生徒ID
                        stuGrpEntity.setStuId(usrEntity.getUsrId());
                        //グループID
                        if (StringUtils.equals(r.getCell(2).getCellType().toString(), "STRING")) {
                            stuGrpEntity.setGrpId(Integer.parseInt(r.getCell(2).getStringCellValue()));
                        } else {
                            stuGrpEntity.setGrpId((int)r.getCell(2).getNumericCellValue());
                        }

                        //作成日時
                        stuGrpEntity.setCretDatime(DateUtils.getSysTimestamp());
                        //作成ユーザＩＤ
                        stuGrpEntity.setCretUsrId(usrId);
                        //更新日時
                        stuGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                        //更新ユーザＩＤ
                        stuGrpEntity.setUpdUsrId(usrId);
                        //削除フラグ
                        stuGrpEntity.setDelFlg(0);
                        try {
                            stuGrpDao.insert(stuGrpEntity);
                        } catch (Exception e) {
                            logger.error(e.getMessage());
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
                        }
                    }
                    //修正
                    else {
                        if (StringUtils.equals(r.getCell(0).getCellType().toString(), "STRING")) {
                            stuGrpEntity = stuGrpDao.selectById(Integer.parseInt(r.getCell(0).getStringCellValue()));
                        } else {
                            stuGrpEntity = stuGrpDao.selectById((int)(r.getCell(0).getNumericCellValue()));
                        }

                        if (stuGrpEntity == null) {
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", "会員-グループID（システム採番）"));
                        }
                        //上書き
                        if (type == 0) {
                            //生徒ID
                            stuGrpEntity.setStuId(usrEntity.getUsrId());
                            //グループID
                            if (StringUtils.equals(r.getCell(2).getCellType().toString(), "STRING")) {
                                stuGrpEntity.setGrpId(Integer.parseInt(r.getCell(2).getStringCellValue()));
                            } else {
                                stuGrpEntity.setGrpId((int)r.getCell(2).getNumericCellValue());
                            }

                            //更新日時
                            stuGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                            //更新ユーザＩＤ
                            stuGrpEntity.setUpdUsrId(usrId);
                            try {
                                stuGrpDao.updateById(stuGrpEntity);
                            } catch (Exception e) {
                                logger.error(e.getMessage());
                                throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
                            }
                        }
                        //エラーとする
                        if (type == 1) {
                            throw new RRException(MessageUtils.getMessage("MSGCOMN0065", (i + 1) + "", "会員-グループID（システム採番）"));
                        }
                    }
                }
                //削除選択した場合
                else {
                    if (r.getCell(0) == null || StringUtils.isEmpty(r.getCell(0).toString())) {
                        throw new RRException((i + 1) + "行目のグループID（システム採番）は必須入力項目です。");
                    }

                    if (StringUtils.equals(r.getCell(0).getCellType().toString(), "STRING")) {
                        stuGrpEntity = stuGrpDao.selectById(Integer.parseInt(r.getCell(0).getStringCellValue()));
                    } else {
                        stuGrpEntity = stuGrpDao.selectById((int)(r.getCell(0).getNumericCellValue()));
                    }

                    if (stuGrpEntity == null) {
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0068", (i + 1) + "", "会員-グループID（システム採番）"));
                    }
                    stuGrpEntity = new StuGrpEntity();
                    //ID

                    if (StringUtils.equals(r.getCell(0).getCellType().toString(), "STRING")) {
                        stuGrpEntity.setId(Integer.parseInt(r.getCell(0).getStringCellValue()));
                    } else {
                        stuGrpEntity.setId((int)r.getCell(0).getNumericCellValue());
                    }
                    //更新日時
                    stuGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                    //更新ユーザＩＤ
                    stuGrpEntity.setUpdUsrId(usrId);
                    //del_flg
                    stuGrpEntity.setDelFlg(1);
                    try {
                        stuGrpDao.updateById(stuGrpEntity);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        throw new RRException(MessageUtils.getMessage("MSGCOMN0079", (i + 1) + ""));
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
}
