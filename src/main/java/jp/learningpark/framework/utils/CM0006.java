package jp.learningpark.framework.utils;

import jp.learningpark.modules.common.dao.MstLoginDao;
import jp.learningpark.modules.common.entity.MstLoginEntity;

import java.sql.Timestamp;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)wyh
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/01/18 ： NWT)wyh ： 新規作成
 * @date 2021/01/18
 */
public class CM0006 {
    /**
     * mstLoginDao
     */
    static MstLoginDao mstLoginDao;

    /**
     * mst_loginデータを挿入します
     * @param loginResultFlg    ログイン結果フラグ
     * @param loginErrKbn       ログインエラー区分
     * @param loginId           ログインID
     * @param loginUrl          ログインURL
     * @param loginTypeKbn      ログインタイプ区分
     * @param orgId             組織ID
     */
    public static void insertMstLoginData(String loginResultFlg, String loginErrKbn, String loginId, String loginUrl, String loginTypeKbn, String orgId){
        if (mstLoginDao == null) {
            mstLoginDao = SpringContextUtils.getBean(MstLoginDao.class);
        }
        Timestamp sysTimestamp = DateUtils.getSysTimestamp();
        //エラーメッセージをログイン管理に登録する。
        MstLoginEntity mstLoginEntity = new MstLoginEntity();
        //ログインタイプ区分
        mstLoginEntity.setLoginTypeKbn(loginTypeKbn);
        //ログインID
        mstLoginEntity.setLoginId(loginId);
        //組織ID
        mstLoginEntity.setOrgId(orgId);
        //ログイン日時
        mstLoginEntity.setLoginTime(DateUtils.getSysTimestamp());
        //ログイン結果フラグ
        mstLoginEntity.setLoginResultFlg(loginResultFlg);
        //ログインエラー区分
        mstLoginEntity.setLoginErrKbn(loginErrKbn);
        //備考
        mstLoginEntity.setCommentTxt(loginUrl);
        //作成日時
        mstLoginEntity.setCretDatime(sysTimestamp);
        //作成ユーザＩＤ
        mstLoginEntity.setCretUsrId(loginId);
        //更新日時
        mstLoginEntity.setUpdDatime(sysTimestamp);
        //更新ユーザＩＤ
        mstLoginEntity.setUpdUsrId(loginId);
        //削除フラグ
        mstLoginEntity.setDelFlg(0);
        try {
            mstLoginDao.insert(mstLoginEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static R loginCheck(CM0003.LoginCheckResultDto loginCheckResultDto, String loginId,String loginTypeKbn,String loginUrl){
        if (StringUtils.equals("1", loginCheckResultDto.getLoginCheckFlag())){
//            insertMstLoginData("0",null,loginId,loginUrl,loginTypeKbn);
        }
        //GID認証フラグ＝「0：失敗」　且つ　ログイン認証フラグ＝「0：NG」の場合、下記処理行う。
        if (StringUtils.equals("0", loginCheckResultDto.getGidCheckFlag())
                &&StringUtils.equals("0",loginCheckResultDto.getLoginCheckFlag())){
            //マナミルIDフラグ：「1：OK」　且つ　マナミルPWフラグ：「0：NG」の場合、、ログイン
            if (StringUtils.equals("1",loginCheckResultDto.getUserExistsFlag())
                    &&StringUtils.equals("0",loginCheckResultDto.getPasswordCheckFlag())){
                insertMstLoginData("1","4",loginId,loginUrl,loginTypeKbn, null);
            }else if (StringUtils.equals("0",loginCheckResultDto.getUserExistsFlag())){
                insertMstLoginData("1","3",loginId,loginUrl,loginTypeKbn, null);
            }
            return R.error(MessageUtils.getMessage("MSGCOMN0001"));
        }
        //GID認証フラグ＝「1：成功」　且つ　ログイン認証フラグ＝「0：NG」の場合、ログイン失敗。
        else if (StringUtils.equals("1", loginCheckResultDto.getGidCheckFlag())&&StringUtils.equals("0",loginCheckResultDto.getLoginCheckFlag())){
            insertMstLoginData("1","5",loginId,loginUrl,loginTypeKbn, null);
            return R.error(MessageUtils.getMessage("MSGCOMN0001"));
        }
        return R.ok();
    }
}
