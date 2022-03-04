/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.com.dao.NoticeApiDao;
import jp.learningpark.modules.com.service.NoticeApiService;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.GuardReadingStsDao;
import jp.learningpark.modules.common.dao.MstNoticeDao;
import jp.learningpark.modules.common.dao.MstNoticeDeliverDao;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30112Dao;
import jp.learningpark.modules.manager.dao.F09020Dao;
import jp.learningpark.modules.manager.dto.F09020Dto;
import jp.learningpark.modules.manager.dto.SendMessageDto;
import jp.learningpark.modules.manager.service.F09020Service;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;

/**
 * <p>F09020_一斉お知らせ配信(新規作成)（スマホ）</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/03/03 : zpa: 新規<br />
 * @version 1.0
 */
@Service
public class F09020ServiceImpl implements F09020Service {
    @Autowired
    F09020Dao f09020Dao;
    @Autowired
    MstNoticeDao mstNoticeDao;
    @Autowired
    MstStuService mstStuService;
    @Autowired
    MstNoticeDeliverDao mstNoticeDeliverDao;
    @Autowired
    GuardReadingStsDao guardReadingStsDao;
    @Autowired
    MstNoticeDeliverService mstNoticeDeliverService;
    @Autowired
    GuardReadingStsService guardReadingStsService;
    @Autowired
    MstNoticeService mstNoticeService;
    @Autowired
    private MstDeviceTokenService mstDeviceTokenService;
    @Autowired
    NoticeApiService noticeApiService;
    @Autowired
    CommonService commonService;

    //add at 2021/08/17 for V9.02 by NWT LiGX START
    /**
     * デバイストーケン noticeApiDao
     */
    @Autowired
    NoticeApiDao noticeApiDao;
    //add at 2021/08/17 for V9.02 by NWT LiGX END

    Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * F30112Dao
     */
    @Autowired
    private F30112Dao f30112Dao;

    @Override
    public List<F09020Dto> init1() {
        return f09020Dao.init1();
    }

    @Override
    public F09020Dto init2(Integer noticeId) {
        return f09020Dao.init2(noticeId);
    }

    @Override
    @Transactional
    public R go(F09020Dto dto, MultipartFile file) {
        Integer id = 0;
        try {
            dto.setNoticeTitle(URLDecoder.decode(dto.getNoticeTitle(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        MstNoticeEntity mstNoticeEntity = new MstNoticeEntity();
        List<F09020Dto> f09020DtoList = null;
        if (dto.getNoticeId() != null) {
            f09020DtoList = f09020Dao.getDeliverStuList(dto.getNoticeId());
        }

        if (dto.getNoticeId() == null && (dto.getStuList() == null || dto.getStuList().size() == 0)) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0074", "配信先対象"));
        }
        if (dto.getNoticeId() != null) {
            mstNoticeEntity = mstNoticeService.getById(dto.getNoticeId());
            if (!StringUtils.equals(DateUtils.format(mstNoticeEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS), dto.getUpdStr())) {
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
            mstNoticeEntity = mstNoticeDao.selectById(dto.getNoticeId());
            mstNoticeEntity.setNoticeTitle(dto.getNoticeTitle());
            mstNoticeEntity.setNoticeCont(dto.getNoticeCont());
            mstNoticeEntity.setNoticeTypeDiv(dto.getNoticeTypeDiv());
            mstNoticeEntity.setNoticeLevelDiv(dto.getNoticeLevelDiv());
            //2020/11/17 LiYuHuan modifly start
            mstNoticeEntity.setPubPlanStartDt(DateUtils.toTimestamp(DateUtils.parse(dto.getPubPlanStartDt(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            mstNoticeEntity.setPubPlanEndDt(DateUtils.toTimestamp(DateUtils.parse(dto.getPubPlanEndDt(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            //2020/11/17 LiYuHuan modifly end
            if (file != null) {
                StringBuilder filePath = new StringBuilder();
                try {
                    filePath.append(getPath(file));
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
                mstNoticeEntity.setAttachFilePath(filePath.toString());
            } else {
                mstNoticeEntity.setAttachFilePath("");
            }
            mstNoticeEntity.setUpdDatime(DateUtils.getSysTimestamp());
            mstNoticeEntity.setUpdUsrId(ShiroUtils.getUserId());
            try {
                mstNoticeDao.updateById(mstNoticeEntity);
                mstNoticeDeliverDao.delete(
                        new QueryWrapper<MstNoticeDeliverEntity>().eq("org_id", mstNoticeEntity.getOrgId()).eq("notice_id", mstNoticeEntity.getId()));
                guardReadingStsDao.delete(
                        new QueryWrapper<GuardReadingStsEntity>().eq("org_id", mstNoticeEntity.getOrgId()).eq("notice_id", mstNoticeEntity.getId()));
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        } else {
            mstNoticeEntity.setOrgId(ShiroUtils.getUserEntity().getOrgId());
            mstNoticeEntity.setNoticeTitle(dto.getNoticeTitle());
            mstNoticeEntity.setNoticeCont(dto.getNoticeCont());
            mstNoticeEntity.setNoticeTypeDiv(dto.getNoticeTypeDiv());
            mstNoticeEntity.setNoticeLevelDiv(dto.getNoticeLevelDiv());
            mstNoticeEntity.setPubPlanStartDt(DateUtils.toTimestamp(DateUtils.parse(dto.getPubPlanStartDt(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            mstNoticeEntity.setPubPlanEndDt(DateUtils.toTimestamp(DateUtils.parse(dto.getPubPlanEndDt(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM)));
            mstNoticeEntity.setAllDeliverFlg("");
            if (file != null) {
                StringBuilder filePath = new StringBuilder();
                try {
                    filePath.append(getPath(file));
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
                mstNoticeEntity.setAttachFilePath(filePath.toString());
            } else {
                mstNoticeEntity.setAttachFilePath("");
            }
            mstNoticeEntity.setTitleImgPath("");
            mstNoticeEntity.setCretDatime(DateUtils.getSysTimestamp());
            mstNoticeEntity.setCretUsrId(ShiroUtils.getUserId());
            mstNoticeEntity.setUpdDatime(DateUtils.getSysTimestamp());
            mstNoticeEntity.setUpdUsrId(ShiroUtils.getUserId());
            mstNoticeEntity.setDelFlg(0);
            try {
                mstNoticeDao.insert(mstNoticeEntity);
                id = mstNoticeEntity.getId();
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        List<F09020Dto> f09020Dtos = new ArrayList<>();
        if (dto.getNoticeId() != null && dto.getStuList() == null) {
            List<String> stuIdList = new ArrayList<>();
            Set<String> orgIdList = new HashSet<>();
            if (f09020DtoList != null) {
                for (F09020Dto f09020Dto : f09020DtoList) {
                    stuIdList.add(f09020Dto.getStuId());
                    orgIdList.add(f09020Dto.getOrgId());
                }
            }
            f09020Dtos = f09020Dao.getList(stuIdList, new ArrayList<>(orgIdList));
        } else {
            f09020Dtos = f09020Dao.getList(dto.getStuIdList(), dto.getOrgIdList());
        }
        List<String> deviceIdList = new ArrayList<>();
        List<MstNoticeDeliverEntity> mstNoticeDeliverEntityArrayList = new ArrayList<>();
        List<GuardReadingStsEntity> guardReadingStsEntityList = new ArrayList<>();
        for (int i = 0; i < f09020Dtos.size(); i++) {
            MstNoticeDeliverEntity mstNoticeDeliverEntity = new MstNoticeDeliverEntity();
            mstNoticeDeliverEntity.setOrgId(f09020Dtos.get(i).getOrgId());
            if (dto.getNoticeId() != null) {
                mstNoticeDeliverEntity.setNoticeId(dto.getNoticeId());
            } else {
                mstNoticeDeliverEntity.setNoticeId(id);
            }
            mstNoticeDeliverEntity.setMgrFlg(dto.getMgrFlg());
            mstNoticeDeliverEntity.setGuardId(f09020Dtos.get(i).getGuardId());
            mstNoticeDeliverEntity.setStuId(f09020Dtos.get(i).getStuId());
            mstNoticeDeliverEntity.setCretDatime(DateUtils.getSysTimestamp());
            mstNoticeDeliverEntity.setCretUsrId(ShiroUtils.getUserId());
            mstNoticeDeliverEntity.setUpdDatime(DateUtils.getSysTimestamp());
            mstNoticeDeliverEntity.setUpdUsrId(ShiroUtils.getUserId());
            mstNoticeDeliverEntity.setDelFlg(0);
            mstNoticeDeliverEntityArrayList.add(mstNoticeDeliverEntity);

            GuardReadingStsEntity guardReadingStsEntity = new GuardReadingStsEntity();
            guardReadingStsEntity.setOrgId(f09020Dtos.get(i).getOrgId());
            if (dto.getNoticeId() != null) {
                guardReadingStsEntity.setNoticeId(dto.getNoticeId());
            } else {
                guardReadingStsEntity.setNoticeId(id);
            }
            guardReadingStsEntity.setGuardId(f09020Dtos.get(i).getGuardId());
            guardReadingStsEntity.setStuId(f09020Dtos.get(i).getStuId());
            guardReadingStsEntity.setReadingStsDiv("0");
            guardReadingStsEntity.setCretDatime(DateUtils.getSysTimestamp());
            guardReadingStsEntity.setCretUsrId(ShiroUtils.getUserId());
            guardReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
            guardReadingStsEntity.setUpdUsrId(ShiroUtils.getUserId());
            guardReadingStsEntity.setDelFlg(0);
            guardReadingStsEntityList.add(guardReadingStsEntity);
            // 通知プッシュ： 受信先デバイスIDの集合、必須項目
            //get deviceId by guard id(after id)
            //delete  at 2021/08/19 for V9.02 by NWT LiGX START
//            MstDeviceTokenEntity mstDeviceTokenEntity = mstDeviceTokenService.getOne(
//                    new QueryWrapper<MstDeviceTokenEntity>().eq("usr_id", f09020Dtos.get(i).getGuardId()).eq("del_flg", 0));
            //delete  at 2021/08/19 for V9.02 by NWT LiGX END
            //add at 2021/08/19 for V9.02 by NWT LiGX START
            List<String> usrIds = Arrays.asList(f09020Dtos.get(i).getGuardId().split(","));
            Map<String, Object> deviceUserId = new HashMap<>();
            deviceUserId.put("userIdList",usrIds);
            List<MstDeviceTokenEntity> mstDeviceTokenEntityList = noticeApiDao.selectDeviceInfo(deviceUserId);
            //add at 2021/08/19 for V9.02 by NWT LiGX END
            //modify at 2021/08/19 for V9.02 by NWT LiGX START
            if (CollectionUtils.isNotEmpty(mstDeviceTokenEntityList)) {
                for (MstDeviceTokenEntity mstDeviceTokenEntity : mstDeviceTokenEntityList) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", mstDeviceTokenEntity.getDeviceId());
                    Integer unReadCount = commonService.pushUnreadCount(f09020Dtos.get(i).getGuardId());
                    map.put("unreadcount", unReadCount);
                    map.put("stuId", f09020Dtos.get(i).getStuId());
                    MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", f09020Dtos.get(i).getStuId()).eq("del_flg", 0));
                    String stuName = mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm();
                    map.put("stuname", stuName);
                    //add deviceId to deviceIdList by forサイクル
                    deviceIdList.add(JSON.toJSONString(map));
                }
            }
            //modify at 2021/08/19 for V9.02 by NWT LiGX END
        }
        try {
            mstNoticeDeliverService.saveBatch(mstNoticeDeliverEntityArrayList);
            guardReadingStsService.saveBatch(guardReadingStsEntityList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        if (CollectionUtils.isNotEmpty(deviceIdList)){
            //通知プッシュ;マナミル側の各関連機能のところでメッセージを送信する時、該当共通部品を利用する。
            String message = noticeApiService.getMessageDetail("0");
            //通知サーバー側の「メッセージ送信API」を呼んで、通知サーバー側へのメッセージ送信処理を行う。
            SendMessageDto sendMessageDto = new SendMessageDto();
            sendMessageDto.setComment("ここはcomment");
            sendMessageDto.setImgUrl(mstNoticeEntity.getAttachFilePath() == null? "" : mstNoticeEntity.getAttachFilePath());
            sendMessageDto.setMessage(message);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("msgId",mstNoticeEntity.getId());
            params.put("msgType", Constant.sendMsgTypeNotice);
            sendMessageDto.setParams(JSON.toJSONString(params));
            sendMessageDto.setPriority(1);
            sendMessageDto.setSendTime(Long.toString(mstNoticeEntity.getPubPlanStartDt().getTime()));
            sendMessageDto.setTitle(mstNoticeEntity.getNoticeTitle());
            sendMessageDto.setToken(commonService.getToken());
            sendMessageDto.setDeviceList(deviceIdList);
            //通知プッシュの起止時間と処理時間をログで記録する
            Timestamp sTime = DateUtils.getSysTimestamp();
            logger.info("Startプッシュ通知：<" + sTime.getTime() + ">");
            noticeApiService.sendMessage(JSON.toJSONString(sendMessageDto));
            Timestamp eTime = DateUtils.getSysTimestamp();
            Long cTime = eTime.getTime() - sTime.getTime();
            logger.info("Endプッシュ通知：<" + eTime.getTime() + ">");
            logger.info("プッシュ通知処理時間：<" + cTime + ">");
        }
        return R.ok();
    }

    /**
     * ファイルパス
     *
     * @param file 　ファイル
     * @return fileName
     * @throws IOException
     */
    public String getPath(MultipartFile file) throws IOException {
        // ファイル名を生成
        String fileName = Objects.requireNonNull(file.getOriginalFilename()).substring(0, file.getOriginalFilename().lastIndexOf(".")) + DateUtils.format(
                DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS) + file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf("."));
        //ローカルパス
        String realPath = ("channel/" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_SLASH)).replace(
                "/", File.separator) + File.separator + fileName;
        // 生成サーバパス（データベースパス）
        String savePath = GakkenConstant.FILE_PATH_PREFIX + realPath;
        // ファイルを生成
        File destFile = FileUtils.getStorageFile(MessageUtils.getMessage("path.file"), realPath);
        if (!destFile.exists()) {
            destFile.getParentFile().mkdirs();
            destFile.createNewFile();
        }
        InputStream in = file.getInputStream();
        FileOutputStream out = new FileOutputStream(destFile);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
        // データベースパス

        return savePath;
    }
}
