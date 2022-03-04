package jp.learningpark.modules.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub;
import jp.gakken.id2.V2GakkenTransactionIDPrivilegeSvcStub;
import jp.learningpark.framework.gakkenID.GakkenIdAPI;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.dao.F40006Dao;
import jp.learningpark.modules.com.dao.F40008Dao;
import jp.learningpark.modules.com.service.F40006Service;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * <p>F40006 学研コミュニケーションアプリ_ServiceImpl</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2018/12/28 : xiong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F40006ServiceImpl implements F40006Service {
    /**
     * F40006 学研コミュニケーションアプリ Dao
     */
    @Autowired
    private F40006Dao f40006Dao;

    /**
     * mstCodDDao
     */
    @Autowired
    private MstCodDDao mstCodDDao;

    /**
     * 学研API呼出用クラス
     */
    @Autowired
    private GakkenIdAPI gakkenIdAPI;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * F40008 パスワード変更画面 Dao
     */
    @Autowired
    private F40008Dao f40008Dao;

    // 2021/10/12　MANAMIRU1-776 cuikl　Edit　Start
    private final String EMAIL = "mail";
    private final String GIDPK = "gidPk";
    // 2021/10/12　MANAMIRU1-776 cuikl　Edit　End

    /**
     * ・生徒の場合のみ、保護者のメールアドレスより判定する。
     * @param afterUserId
     * @param email
     * @return
     */
    @Override
    public R confirmId(String afterUserId, String email, String brandCd) {
        // 2021/10/13　MANAMIRU1-776 cuikl　Edit Start
        HashMap<String,String> gidCheckMap = getGidEmail(afterUserId);
        R info = new R();
        if (gidCheckMap != null && StringUtils.isNotBlank(gidCheckMap.get(GIDPK))){
            // ユーザ基本マスタ情報取得
            List<MstUsrEntity> mstUsrEntityList = f40006Dao.selectRoleDivById(afterUserId, "1", brandCd,gidCheckMap.get(GIDPK));
            if (mstUsrEntityList.size() > 0 && "3".equals(mstUsrEntityList.get(0).getRoleDiv().trim())
                    && "1".equals(mstUsrEntityList.get(0).getGidFlg())){
                //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　Start
                if (StringUtils.equals(email,gidCheckMap.get(EMAIL))){
                    // 変更後ユーザＩＤ
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID_FOR_RESET_PASSWORD, afterUserId);
                    // GIDフラグ
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_GID_FLG_FOR_RESET_PASSWORD, "1");
                    // 学研IDプライマリキー
                    ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_GIDPK_FOR_RESET_PASSWORD, gidCheckMap.get(GIDPK));
                    return info.put("success",true);
        // 2021/10/25 manamiru1-776 cuikl edit start
                }
            }
        }
        MstCodDEntity mstCodDEntity = mstCodDDao.selectOne(new QueryWrapper<MstCodDEntity>().eq("cod_key", "URL_ADD_KEY").eq("del_flg", 0));
        String manaFlag = StringUtils.equals(mstCodDEntity.getCodValue(), brandCd) ? "1" : "0";
        // ユーザ基本マスタ情報取得
        List<MstUsrEntity> mstUsrEntityList = f40006Dao.selectRoleDivById(afterUserId, manaFlag, brandCd,null);
        if (mstUsrEntityList.size() > 0){
            String role = mstUsrEntityList.get(0).getRoleDiv().trim();
            int count = 0;
            if (StringUtils.equals(role, "4")) {
                // ロール ＝ 「生徒」の場合
                count = f40006Dao.selectCountStuById(afterUserId, email);
            } else if (StringUtils.equals(role, "3")) {
                // ロール ＝ 「保護者」の場合
                count = f40006Dao.selectCountGuaById(afterUserId, email);
            } else if (StringUtils.equals(role, "2")) {
                // ロール ＝ 「メンター」の場合
                count = f40006Dao.selectCountMenById(afterUserId, email);
            } else if (StringUtils.equals(role, "1")) {
                // ロール ＝ 「管理者」の場合
                count = f40006Dao.selectCountManById(afterUserId, email);
            }
            // 結果判定
            if (count > 0) {
                // 変更後ユーザＩＤ
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_AFTER_USR_ID_FOR_RESET_PASSWORD, afterUserId);
                // GIDフラグ
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_GID_FLG_FOR_RESET_PASSWORD, "0");
                // 学研IDプライマリキー
                ShiroUtils.setSessionAttribute(GakkenConstant.SESSION_GIDPK_FOR_RESET_PASSWORD, null);
                return info.put("success", true);
                //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　End
            } else {
                return info.put("success", false);
            }
            // 2021/10/25 manamiru1-776 cuikl edit end
        }
        return info;
        // 2021/10/13　MANAMIRU1-776 cuikl　Edit end
    }

    /* 2021/3/12 4-37 cuikailin modify start */
    /**
     * GIDPKとGIDのメールアドレス１を取得
     * @param gid gid
     * @return email
     */
    public HashMap<String,String> getGidEmail(String gid){
        HashMap<String,String> resultMap = new HashMap<>();
        V2GakkenIDPrivilegeSvcStub.GakkenID gakkenID = new V2GakkenIDPrivilegeSvcStub.GakkenID();
        V2GakkenIDPrivilegeSvcStub.GakkenIDselect gakkenIdSelect = new V2GakkenIDPrivilegeSvcStub.GakkenIDselect();
        gakkenID.setGid(gid);
        // GIDPK
        gakkenIdSelect.setGidpk(true);
        // GID
        gakkenIdSelect.setGid(true);
        // メールアドレス１
        gakkenIdSelect.setMailaddress_1(true);
        // GIDPKとGIDのメールアドレス１を取得
        try {
            // GID側のメールアドレスの存在チェック
            V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDs resultGakkenIDs =  gakkenIdAPI.getGakkenIDSearch(gakkenID,gakkenIdSelect);
            if (resultGakkenIDs != null && resultGakkenIDs.getRecordCounts() >0) {
                V2GakkenIDPrivilegeSvcStub.GakkenID[] gakkenIDs = resultGakkenIDs.getGakkenID();
                //2021/10/11　MANAMIRU1-810 huangxinliang　Edit　Start
                V2GakkenIDPrivilegeSvcStub.GakkenID gakkenIDResult = new V2GakkenIDPrivilegeSvcStub.GakkenID();
                for (V2GakkenIDPrivilegeSvcStub.GakkenID gakken : gakkenIDs) {
                    if (gakken != null && StringUtils.equals(gid, gakken.getGid())){
                        gakkenIDResult = gakken;
                    }
                }
                if ("0".equals(gakkenIDResult.getFamily_type())){
                    return null;
                }
                if (!StringUtils.isEmpty(gakkenIDResult.getMailaddress_1())){
                    // 2021/10/12　MANAMIRU1-776 cuikl　Edit　Start
                    resultMap.put(EMAIL,gakkenIDResult.getMailaddress_1());
                    resultMap.put(GIDPK,gakkenIDResult.getGidpk());
                    // 2021/10/12　MANAMIRU1-776 cuikl　Edit　End
                    return resultMap;
                }else {
                    // GIDメールアドレス１が未設定
                    String gidPk = gakkenIDResult.getGidpk();
                    // 2021/10/11　MANAMIRU1-810 huangxinliang　Edit　End
                    if (StringUtils.isNotBlank(gidPk)) {
                        V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID gakkenTransactionIDForSearch = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID();
                        gakkenTransactionIDForSearch.setGidpk(gidPk);
                        gakkenTransactionIDForSearch.setEvent_key_1(gakkenIdAPI.getGakkenMailKey());
                        gakkenTransactionIDForSearch.setSite_cd(gakkenIdAPI.getServiceCd2());
                        V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionIDselect gakkenTIDSelect = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionIDselect();
                        gakkenTIDSelect.setMailaddress_1(true);
                        gakkenTIDSelect.setEvent_key_1(true);
                        gakkenTIDSelect.setUpdate_ts(true);
                        // GTIDのメールアドレス１を取得
                        V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs resultGakkenTransactionIDs = gakkenIdAPI.getGakkenTransactionIDsearch(gakkenTransactionIDForSearch, gakkenTIDSelect);
                        if (resultGakkenTransactionIDs != null && resultGakkenTransactionIDs.getRecordCounts() > 0){
                            int indexForMailad = -1;
                            long lastTimeForMailad = -1L;
                            V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID[] gakkenTransactionID = resultGakkenTransactionIDs.getGakkenTransactionID();
                            for (int i = 0; i < gakkenTransactionID.length; i++) {
                                if (gakkenTransactionID[i] == null){
                                    continue;
                                }
                                //メールアドレス
                                if (StringUtils.equals(gakkenTransactionID[i].getEvent_key_1(), gakkenIdAPI.getGakkenMailKey())) {
                                    if (gakkenTransactionID[i].getUpdate_ts().getTime() > lastTimeForMailad) {
                                        lastTimeForMailad = gakkenTransactionID[i].getUpdate_ts().getTime();
                                        indexForMailad = i;
                                    }
                                }
                            }
                            if (indexForMailad >= 0) {
                                // 2021/10/12　MANAMIRU1-776 cuikl　Edit　Start
                                resultMap.put(EMAIL,gakkenTransactionID[indexForMailad].getMailaddress_1());
                                resultMap.put(GIDPK,gidPk);
                                // GTIDメールアドレス１
                                return resultMap;
                                // 2021/10/12　MANAMIRU1-776 cuikl　Edit　End
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    /* 2021/3/12 4-37 cuikailin modify end */
}
