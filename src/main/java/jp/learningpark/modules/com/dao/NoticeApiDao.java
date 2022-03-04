package jp.learningpark.modules.com.dao;

import jp.learningpark.modules.common.entity.MstDeviceTokenEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 通知アプリ連携API
 *
 * @author NWT 楊
 */
@Mapper
public interface NoticeApiDao {
    /**
     * 送信JSON１の各パラメータをもとに、下記条件でユーザ基本マスタから対象データを取得する
     *
     * @param loginId 送信JSON１．loginId
     * @param password マナミルパスワード
     * @param brandCd 本塾に属するブランドコード
     * @return
     */
    MstUsrEntity getUserEntity(@Param("loginId") String loginId, @Param("password") String password, @Param("brandCd") String brandCd);

    /**
     * <p>デバイスを取得する。</p>
     * <p>
     * add at 2021/08/16 for V9.02 by NWT wen
     *
     * @param map
     * @return
     */
    List<MstDeviceTokenEntity> selectDeviceInfo(Map<String, Object> map);

    // add at 2021/9/29 for V9.02 by NWT HuangXL START
    /**
     * 最大端末数を超過するデバイスを無効化するため
     * add at 2021/9/29 for V9.02 by NWT HuangXL
     * @param usrId
     * @return
     */
    int updateDelFlgByUsrId(@Param("usrId")String usrId);
    // add at 2021/9/29 for V9.02 by NWT HuangXL END
}
