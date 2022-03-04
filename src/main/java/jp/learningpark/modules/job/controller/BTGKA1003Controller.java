package jp.learningpark.modules.job.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.job.entity.BTGKA1003Dto;
import jp.learningpark.modules.job.service.BTGKA1003Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("BTGKA1003Controller")
public class BTGKA1003Controller {
    @Autowired
    MstCodDService mstCodDService;
    @Autowired
    BTGKA1003Service btgka1003Service;

    /**
     * @return R
     */
    public void downloadCSV() {
        String sysTime = DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
//        String sysTime = "2020-03-06";
        //定期的バッチ実行時間を取る
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().eq("cod_key", "LEADER_ROLE_LIST").eq("del_flg", 0));
        //2.1ユーザー基本マスタ、管理者基本マスタ、コードマスタから本日新規作成/変更されたの管理者データを抽出して、CSVで出力する
        List<BTGKA1003Dto> managerList = btgka1003Service.getUserList(sysTime, "1", 0);
        btgka1003Service.downloadCSV("0", managerList, "1");
        //2.3ユーザー基本マスタ、管理者基本マスタ、コードマスタから本日削除されたの管理者データを抽出して、CSVで出力する
        List<BTGKA1003Dto> managerDelList = btgka1003Service.getUserList(sysTime, "1", 1);
        btgka1003Service.downloadCSV("1", managerDelList, "1");
        //3.1ユーザー基本マスタ、メンター基本マスタ、コードマスタから本日新規作成/変更されたのメンターデータを抽出して、CSVで出力する
        List<BTGKA1003Dto> mentorList = btgka1003Service.getUserList(sysTime, "2", 0);
        btgka1003Service.downloadCSV("2", mentorList, "2");
        //3.3ユーザー基本マスタ、メンター基本マスタ、コードマスタから本日削除されたのメンターデータを抽出して、CSVで出力する
        List<BTGKA1003Dto> mentorDelList = btgka1003Service.getUserList(sysTime, "2", 1);
        btgka1003Service.downloadCSV("3", mentorDelList, "2");
        //4.1ユーザー基本マスタ、保護者基本マスタ、コードマスタから本日新規作成/更新されたの保護者データを抽出して、CSVで出力する
        List<BTGKA1003Dto> guardList = btgka1003Service.getUserList(sysTime, "3", 0);
        btgka1003Service.downloadCSV("4", guardList, "3");
        //4.3ユーザー基本マスタ、保護者基本マスタ、コードマスタから本日削除されたの保護者データを抽出して、CSVで出力する
        List<BTGKA1003Dto> guardDelList = btgka1003Service.getUserList(sysTime, "3", 1);
        btgka1003Service.downloadCSV("5", guardDelList, "3");
        //5.1ユーザー基本マスタ、生徒基本マスタ、コードマスタ、生徒ポイントから本日新規作成/更新されたの保護者データを抽出して、CSVで出力する
        List<BTGKA1003Dto> stuList = btgka1003Service.getUserList(sysTime, "4", 0);
        btgka1003Service.downloadCSV("6", stuList, "4");
        //5.3毎日の夜　11時でユーザー基本マスタ、生徒基本マスタ、コードマスタ、生徒ポイントから本日削除されたの保護者データを抽出して、CSVで出力する
        List<BTGKA1003Dto> stuDelList = btgka1003Service.getUserList(sysTime, "4", 1);
        btgka1003Service.downloadCSV("7", stuDelList, "4");
        //6.1ユーザー基本マスタ、組織マスタ、コードマスタ、管理者基本マスタから本日新規作成/更新されたの組織データを抽出して、CSVで出力する
        List<BTGKA1003Dto> orgList = btgka1003Service.getOrgList(sysTime, 0);
        btgka1003Service.downloadCSV("8", orgList, null);
        //6.3毎日の夜　11時でユーザー基本マスタ、組織マスタ、コードマスタ、管理者基本マスタから本日削除されたの組織データを抽出して、CSVで出力する
        List<BTGKA1003Dto> orgDelList = btgka1003Service.getOrgList(sysTime, 1);
        btgka1003Service.downloadCSV("9", orgDelList, null);
        //7.1ユーザー基本マスタ、コードマスタ、組織マスタ、管理者基本マスタ、メンター基本マスタ、保護者基本マスタ、生徒基本マスタから
        List<BTGKA1003Dto> orgAllList = btgka1003Service.getOrgAllList(sysTime, 0);
        btgka1003Service.downloadCSV("10", orgAllList, null);
        //7.3毎日の夜　11時でユーザー基本マスタ、コードマスタ、組織マスタ、管理者基本マスタ、メンター基本マスタ、保護者基本マスタ、生徒基本マスタから
        List<BTGKA1003Dto> orgAllDelList = btgka1003Service.getOrgAllList(sysTime, 1);
        btgka1003Service.downloadCSV("11", orgAllDelList, null);
        //8.1ユーザ初期パスワード管理から本日新規作成されたユーザパスワード出して、CSVで出力する
        List<BTGKA1003Dto> pwList = btgka1003Service.getPwList(sysTime, 0);
        btgka1003Service.downloadCSV("12", pwList, null);
        //8.3ユーザ初期パスワード管理から本日削除されたユーザパスワードデータを抽出して、CSVで出力する
        List<BTGKA1003Dto> pwDelList = btgka1003Service.getPwList(sysTime, 1);
        btgka1003Service.downloadCSV("13", pwDelList, null);
    }
}
