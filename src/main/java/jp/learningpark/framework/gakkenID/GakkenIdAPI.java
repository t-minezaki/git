package jp.learningpark.framework.gakkenID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub;
import jp.gakken.id2.V2GakkenIDSvcStub;
import jp.gakken.id2.V2GakkenTransactionIDPrivilegeSvcStub;
import jp.gakken.id2.V2GakkenTransactionIDSvcStub;
import jp.learningpark.framework.gakkenID.dao.GakkenApiDao;
import jp.learningpark.framework.gakkenID.dto.GakkenId;
import jp.learningpark.framework.gakkenID.dto.GakkenTransactionID;
import jp.learningpark.framework.gakkenID.utils.DateUtils;
import jp.learningpark.framework.gakkenID.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstAddrDao;
import jp.learningpark.modules.common.entity.MstAddrEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 学研API呼出用クラス
 *
 * @author wangqiang
 */
@ConfigurationProperties(prefix = "gakken-api")
@Component
public class GakkenIdAPI {

	// 学研API接続URL
	private String requestUrl;
	// SOAP認証用UsernameToken
	private String user;
	// SOAP認証用Password
	private String password;
	// 特権アクセストークン
	private String priToken;
	// サービスサイトコード
	private String serviceCd;
	// サービスショップコード
	private String shopCd;
	
	// GakkenTID履歴キー
	private String gakkenTidHistKey;
	// メールアドレスキー
	private String gakkenMailKey;
	// 指導者コードキー
	private String teacherCodeKey;
	// サービスサイトコード2
	private String serviceCd2;
	
	// 本仮APIフラグ（true:本API,false:仮API）
	private boolean realApi;
	
	// 仮API相関
    @Autowired
    private GakkenApiDao gakkenApiDao;
    @Autowired
    private MstAddrDao addrDao;
//    @Autowired
//    private AutoGrowthService autoGrowthService;

	private static final Logger log = LoggerFactory.getLogger(GakkenIdAPI.class);


//-----------GakkenID-Start--------
    
	/**
	 * ログイン（GakkenID(特権)）
	 *
	 * @param gakkenID       学研ID
	 * @param pwd            パスワード
	 * @return 結果（APIResultGakkenIDshort->
	 * 					success:ログイン結果
	 * 					GakkenIDshort:学研IDトークン構造体）
	 * @throws Exception
	 */
	// 2021/09/22 manamiru1-772 cuikl del
	public V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDshort login(String gakkenID, String pwd) throws Exception {

		// 本API
		if (realApi) {
			
			V2GakkenIDPrivilegeSvcStub stub = StubEngine.getGakkenIDPrivilegeSvcStub(requestUrl, user, password);
	
			// パラメータの設定
			V2GakkenIDPrivilegeSvcStub.Login request = new V2GakkenIDPrivilegeSvcStub.Login();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// 特権アクセストークン
			request.setAccessToken(priToken);
			// 学研ID
			request.setId(gakkenID);
			// パスワード
			request.setPw(pwd);
	
			// 学研API_ログイン＋GakkenIDデータをアクセス
			V2GakkenIDPrivilegeSvcStub.LoginResponse response = stub.login(request);
	
			V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDshort result = response.get_return();
	
			return result;
		} else {
			// 仮API
	        GakkenId condition = new GakkenId();
	        condition.setGid(gakkenID);
	        condition.setPass(pwd);

	        List<GakkenId> gakkenIds = gakkenApiDao.selectGakkenId(condition);

	        V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDshort result = new V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDshort();
	        
	        if (gakkenIds != null && !gakkenIds.isEmpty()) {
	        	// ログイン結果
	        	result.setSuccess(true);
	        	
	        	// 学研ID構造体
	        	GakkenId gidResult = gakkenIds.get(0);
	        	
	        	// 返却用学研IDトークン構造体
	        	V2GakkenIDPrivilegeSvcStub.GakkenIDshort returnGakkenIDshort = new V2GakkenIDPrivilegeSvcStub.GakkenIDshort();
	        	// 学研IDプライマリキー
	        	returnGakkenIDshort.setGidpk(gidResult.getGidpk());
	        	// 学研ID
	        	returnGakkenIDshort.setGid(gidResult.getGid());
	        	// アクセストークン
	        	returnGakkenIDshort.setSso_accesstoken(StringUtils.getStringRandom(32));
	        	// アクセストークン有効期限
	        	Calendar accEnd = Calendar.getInstance();
	        	accEnd.add(11, 2);
	        	returnGakkenIDshort.setSso_accesstoken_end(accEnd);
	        	result.setGakkenIDshort(returnGakkenIDshort);
	        } else {
	        	// ログイン結果
	        	result.setSuccess(false);
	        }
	        
			return result;
		}
	}

	/**
	 * ログイン＋GakkenIDデータ（GakkenID(特権)）
	 *
	 * @param gakkenID       学研ID
	 * @param pwd            パスワード
	 * @param gakkenIdSelect 学研ID構造体（結果限定）
	 * @return 結果（APIResultGakkenIDshort->
	 * 					success:ログイン結果
	 * 					GakkenIDshort:学研IDトークン構造体
	 * 					GakkenID:学研ID構造体）
	 * @throws Exception
	 */
	// 2021/09/22 manamiru1-772 cuikl del
	public V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDshort loginWithGakkenID(String gakkenID, String pwd,
			V2GakkenIDPrivilegeSvcStub.GakkenIDselect gakkenIdSelect) throws Exception {

		// 本API
		if (realApi) {
			
			V2GakkenIDPrivilegeSvcStub stub = StubEngine.getGakkenIDPrivilegeSvcStub(requestUrl, user, password);
	
			// パラメータの設定
			V2GakkenIDPrivilegeSvcStub.LoginWithGakkenID request = new V2GakkenIDPrivilegeSvcStub.LoginWithGakkenID();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// 特権アクセストークン
			request.setAccessToken(priToken);
			// 学研ID
			request.setId(gakkenID);
			// パスワード
			request.setPw(pwd);
			if (gakkenIdSelect != null) {
				// 学研ID構造体（結果限定）
				request.setGakkenIDselect(gakkenIdSelect);
			}
	
			// 学研API_ログイン＋GakkenIDデータをアクセス
			V2GakkenIDPrivilegeSvcStub.LoginWithGakkenIDResponse response = stub.loginWithGakkenID(request);
	
			V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDshort result = response.get_return();
	
			return result;
		} else {
			// 仮API
	        GakkenId condition = new GakkenId();
	        condition.setGid(gakkenID);
	        condition.setPass(pwd);

	        List<GakkenId> gakkenIds = gakkenApiDao.selectGakkenId(condition);

	        V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDshort result = new V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDshort();
	        
	        if (gakkenIds != null && !gakkenIds.isEmpty()) {
	        	// ログイン結果
	        	result.setSuccess(true);
	        	
	        	// 学研ID構造体
	        	GakkenId gidResult = gakkenIds.get(0);
	        	// 学研ID構造体返却用
	        	V2GakkenIDPrivilegeSvcStub.GakkenID returnGakkenID = new V2GakkenIDPrivilegeSvcStub.GakkenID();
	        	// 学研IDプライマリキー
	        	if (gakkenIdSelect == null || gakkenIdSelect.getGidpk()) {
	        		returnGakkenID.setGidpk(gidResult.getGidpk());
	        	}
	        	// 学研ID
	        	if (gakkenIdSelect == null || gakkenIdSelect.getGid()) {
	        		returnGakkenID.setGid(gidResult.getGid());
	        	}
	        	// メールアドレス1
	        	if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_1()) {
	        		returnGakkenID.setMailaddress_1(gidResult.getMailaddress1());
	        	}
	        	// メールアドレス2
	        	if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_2()) {
	        		returnGakkenID.setMailaddress_2(gidResult.getMailaddress2());
	        	}
	        	// メールアドレス3
	        	if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_3()) {
	        		returnGakkenID.setMailaddress_3(gidResult.getMailaddress3());
	        	}
	        	// 携帯メールアドレス
	        	if (gakkenIdSelect == null || gakkenIdSelect.getMobile()) {
	        		returnGakkenID.setMobile(gidResult.getMobile());
	        	}
	        	// 姓
	        	if (gakkenIdSelect == null || gakkenIdSelect.getLastname()) {
	        		returnGakkenID.setLastname(gidResult.getLastname());
	        	}
	        	// 名
	        	if (gakkenIdSelect == null || gakkenIdSelect.getFirstname()) {
	        		returnGakkenID.setFirstname(gidResult.getFirstname());
	        	}
	        	// セイ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getKana_lastname()) {
	        		returnGakkenID.setKana_lastname(gidResult.getKanaLastname());
	        	}
	        	// メイ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getKana_firstname()) {
	        		returnGakkenID.setKana_firstname(gidResult.getKanaFirstname());
	        	}
	        	// 生年月日
	        	if (gakkenIdSelect == null || gakkenIdSelect.getBirth()) {
	        		returnGakkenID.setBirth(gidResult.getBirth());
	        	}
	        	// ニックネーム
	        	if (gakkenIdSelect == null || gakkenIdSelect.getNickname()) {
	        		returnGakkenID.setNickname(gidResult.getNickname());
	        	}
	        	// 性別
	        	if (gakkenIdSelect == null || gakkenIdSelect.getSex()) {
	        		if (StringUtils.isNotEmpty(gidResult.getSex())) {
	        			returnGakkenID.setSex(Short.parseShort(gidResult.getSex()));
	        		}
	        	}
	        	// 国
	        	if (gakkenIdSelect == null || gakkenIdSelect.getCountry()) {
	        		returnGakkenID.setCountry(gidResult.getCountry());
	        	}
	        	// 郵便番号
	        	if (gakkenIdSelect == null || gakkenIdSelect.getZip()) {
	        		returnGakkenID.setZip(gidResult.getZip());
	        	}
	        	// 電話番号
	        	if (gakkenIdSelect == null || gakkenIdSelect.getTel()) {
	        		returnGakkenID.setTel(gidResult.getTel());
	        	}
				// 電話番号2
				if (gakkenIdSelect == null || gakkenIdSelect.getTel_2()) {
					returnGakkenID.setTel_2(gidResult.getTel2());
				}
	        	// 都道府県コード
	        	if (gakkenIdSelect == null || gakkenIdSelect.getPref_cd()) {
	        		returnGakkenID.setPref_cd(gidResult.getPrefCd());
	        	}
	        	// 住所1（都道府県）
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_1()) {
	        		returnGakkenID.setAddress_1(gidResult.getAddress1());
	        	}
	        	// 住所2（市区町村）
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_2()) {
	        		returnGakkenID.setAddress_2(gidResult.getAddress2());
	        	}
	        	// 住所3（番地）
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_3()) {
	        		returnGakkenID.setAddress_3(gidResult.getAddress3());
	        	}
	        	// 住所4（号室）
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_4()) {
	        		returnGakkenID.setAddress_4(gidResult.getAddress4());
	        	}
	        	// 住所5
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_5()) {
	        		returnGakkenID.setAddress_5(gidResult.getAddress5());
	        	}
	        	// 住所6
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_6()) {
	        		returnGakkenID.setAddress_6(gidResult.getAddress6());
	        	}
	        	// 家族コード
	        	if (gakkenIdSelect == null || gakkenIdSelect.getFamily_cd()) {
	        		returnGakkenID.setFamily_cd(gidResult.getFamilyCd());
	        	}
	        	// 家族タイプ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getFamily_type()) {
	        		if (gidResult.getFamilyType() != null) {
	        			returnGakkenID.setFamily_type(gidResult.getFamilyType());
	        		}
	        	}
	        	// DM配信フラグ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getDirectmail_flg()) {
	        		if (StringUtils.isNotEmpty(gidResult.getDirectmailFlg())) {
	        			returnGakkenID.setDirectmail_flg(Short.parseShort(gidResult.getDirectmailFlg()));
	        		}
	        	}
	        	// 決済不可フラグ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getPayment_not_flg()) {
	        		if (StringUtils.isNotEmpty(gidResult.getPaymentNotFlg())) {
	        			returnGakkenID.setPayment_not_flg(Short.parseShort(gidResult.getPaymentNotFlg()));
	        		}
	        	}
	        	// 退会フラグ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getWithdrawl_flg()) {
	        		if (StringUtils.isNotEmpty(gidResult.getWithdrawlFlg())) {
	        			returnGakkenID.setWithdrawl_flg(Short.parseShort(gidResult.getWithdrawlFlg()));
	        		}
	        	}
	        	// 同意フラグ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAgree_flg()) {
	        		if (StringUtils.isNotEmpty(gidResult.getAgreeFlg())) {
	        			returnGakkenID.setAgree_flg(Short.parseShort(gidResult.getAgreeFlg()));
	        		}
	        	}
				// データ利用許諾
				if (gakkenIdSelect == null || gakkenIdSelect.getData_utill_agree_flg()) {
					if (StringUtils.isNotEmpty(gidResult.getDataUtillAgreeFlg())) {
						returnGakkenID.setData_utill_agree_flg(Short.parseShort(gidResult.getDataUtillAgreeFlg()));
					}
				}
	        	// 登録日
	        	if (gakkenIdSelect == null || gakkenIdSelect.getRegist_ts()) {
	        		returnGakkenID.setRegist_ts(gidResult.getRegistT());
	        	}
	        	// 更新日
	        	if (gakkenIdSelect == null || gakkenIdSelect.getUpdate_ts()) {
	        		returnGakkenID.setUpdate_ts(gidResult.getUpdateTs());
	        	}
	        	// パスワード最終更新日
	        	if (gakkenIdSelect == null || gakkenIdSelect.getPass_update_ts()) {
	        		returnGakkenID.setPass_update_ts(gidResult.getPassUpdateTs());
	        	}
	        	// 退会日
	        	if (gakkenIdSelect == null || gakkenIdSelect.getWithdrawl_ts()) {
	        		returnGakkenID.setWithdrawl_ts(gidResult.getWithdrawlTs());
	        	}
				// 予備項目_1
				if (gakkenIdSelect == null || gakkenIdSelect.getExt_1()) {
					returnGakkenID.setExt_1(gidResult.getExt1());
				}
				// 予備項目_2
				if (gakkenIdSelect == null || gakkenIdSelect.getExt_2()) {
					returnGakkenID.setExt_2(gidResult.getExt2());
				}
				// 予備項目_3
				if (gakkenIdSelect == null || gakkenIdSelect.getExt_3()) {
					returnGakkenID.setExt_3(gidResult.getExt3());
				}

	        	result.setGakkenID(returnGakkenID);
	        	
	        	// 返却用学研IDトークン構造体
	        	V2GakkenIDPrivilegeSvcStub.GakkenIDshort returnGakkenIDshort = new V2GakkenIDPrivilegeSvcStub.GakkenIDshort();
	        	// 学研IDプライマリキー
	        	returnGakkenIDshort.setGidpk(gidResult.getGidpk());
	        	// 学研ID
	        	returnGakkenIDshort.setGid(gidResult.getGid());
	        	// アクセストークン
	        	returnGakkenIDshort.setSso_accesstoken(StringUtils.getStringRandom(32));
	        	// アクセストークン有効期限
	        	Calendar accEnd = Calendar.getInstance();
	        	accEnd.add(11, 2);
	        	returnGakkenIDshort.setSso_accesstoken_end(accEnd);
	        	result.setGakkenIDshort(returnGakkenIDshort);
	        } else {
	        	// ログイン結果
	        	result.setSuccess(false);
	        	
	        }
	        
			return result;
		}
	}

	/**
	 * 学研ID重複確認（GakkenID(特権)）
	 *
	 * @param gakkenID 学研ID
	 * @return 結果 true:重複,false：重複しない
	 * @throws Exception
	 */
	public boolean checkGID(String gakkenID) throws Exception {

		// 本API
		if (realApi) {
			V2GakkenIDPrivilegeSvcStub stub = StubEngine.getGakkenIDPrivilegeSvcStub(requestUrl, user, password);
	
			// パラメータの設定
			V2GakkenIDPrivilegeSvcStub.CheckGID request = new V2GakkenIDPrivilegeSvcStub.CheckGID();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// 特権アクセストークン
			request.setAccessToken(priToken);
			// 学研ID
			request.setGid(gakkenID);
	
			// 学研API_学研ID重複確認をアクセス
			V2GakkenIDPrivilegeSvcStub.CheckGIDResponse response = stub.checkGID(request);
	
			V2GakkenIDPrivilegeSvcStub.APIResultExist result = response.get_return();
			if (!result.getSuccess()) {
				throw new Exception("学研API呼出[GakkenId#checkGID]はエラーが発生しました。\r\n" + result.getErrorcode() + ":"
						+ result.getErrorMessage());
			}
	
			return result.getExist();
		} else {
			// 仮API
			GakkenId condition = new GakkenId();
	        condition.setGid(gakkenID);

	        List<GakkenId> gakkenIds = gakkenApiDao.selectGakkenId(condition);
	        
	        if (gakkenIds != null && !gakkenIds.isEmpty()) {
	        	return true;
	        } else {
	        	return false;
	        }
		}
	}

	/**
	 * 個人情報登録（バルク）（GakkenID(特権)）
	 *
	 * @param gakkenIDs      学研ID構造体配列
	 * @return 結果（APIResultGakkenIDRegists->
	 * 					result:登録結果
	 * 					GakkenID:学研ID構造体配列）
	 * @throws Exception
	 */
	public V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDRegists registGakkenIDBulk(
			V2GakkenIDPrivilegeSvcStub.GakkenID[] gakkenIDs) throws Exception {

		// 本API
		if (realApi) {
			V2GakkenIDPrivilegeSvcStub stub = StubEngine.getGakkenIDPrivilegeSvcStub(requestUrl, user, password);

			// パラメータの設定
			V2GakkenIDPrivilegeSvcStub.RegistGakkenIDBulk request = new V2GakkenIDPrivilegeSvcStub.RegistGakkenIDBulk();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// 特権アクセストークン
			request.setAccessToken(priToken);
			// 学研ID構造体配列
			request.setGakkenID(gakkenIDs);

			// 学研API_個人情報登録（バルク）をアクセス
			V2GakkenIDPrivilegeSvcStub.RegistGakkenIDBulkResponse response = stub.registGakkenIDBulk(request);

			V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDRegists result = response.get_return();
			if (!result.getSuccess() || !result.getResult()) {
				throw new Exception("学研API呼出[GakkenId#registGakkenIDBulk]はエラーが発生しました。\r\n" + result.getErrorcode() + ":"
						+ result.getErrorMessage());
			}

			return result;
		} else {
			V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDRegists result = new V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDRegists();
			// 仮API
			if (gakkenIDs == null || gakkenIDs.length == 0) {
				throw new Exception("学研API呼出[GakkenId#registGakkenIDBulk]はエラーが発生しました。");
			}
			// 返却用構造体配列
			V2GakkenIDPrivilegeSvcStub.GakkenID[] returnGIDs = new V2GakkenIDPrivilegeSvcStub.GakkenID[gakkenIDs.length];
			
			for (int i = 0; i < gakkenIDs.length; i++) {
				V2GakkenIDPrivilegeSvcStub.GakkenID gakkenID = gakkenIDs[i];
				
				// 返却用構造体
				V2GakkenIDPrivilegeSvcStub.GakkenID returnGID = new V2GakkenIDPrivilegeSvcStub.GakkenID();
				// 登録用構造体
				GakkenId gakkenIdInfo = new GakkenId();
				// 学研ID
				if (StringUtils.isEmpty(gakkenID.getGid())) {
//					String gid = autoGrowthService.getNumassLoginId();
//					returnGID.setGid(gid);
				} else {
					returnGID.setGid(gakkenID.getGid());
				}
				gakkenIdInfo.setGid(returnGID.getGid());

				// 学研IDプライマリキー
				gakkenIdInfo.setGidpk(returnGID.getGid() + "pk");
				returnGID.setGidpk(returnGID.getGid() + "pk");
				
				// パスワード
				if (StringUtils.isEmpty(gakkenID.getPass())) {
//					gakkenIdInfo.setPass(autoGrowthService.getNumassPassword());
				} else {
					gakkenIdInfo.setPass(gakkenID.getPass());
				}
				
				// メールアドレス1
				gakkenIdInfo.setMailaddress1(gakkenID.getMailaddress_1());
				returnGID.setMailaddress_1(gakkenID.getMailaddress_1());
				
				// メールアドレス2
				gakkenIdInfo.setMailaddress2(gakkenID.getMailaddress_2());
				returnGID.setMailaddress_2(gakkenID.getMailaddress_2());
				
				// メールアドレス3
				gakkenIdInfo.setMailaddress3(gakkenID.getMailaddress_3());
				returnGID.setMailaddress_3(gakkenID.getMailaddress_3());
				
				// 携帯メールアドレス
				gakkenIdInfo.setMobile(gakkenID.getMobile());
				returnGID.setMobile(returnGID.getMobile());
				
				// 姓
				gakkenIdInfo.setLastname(gakkenID.getLastname());
				returnGID.setLastname(returnGID.getLastname());
				
				// 名
				gakkenIdInfo.setFirstname(gakkenID.getFirstname());
				returnGID.setFirstname(gakkenID.getFirstname());
				
				// セイ
				gakkenIdInfo.setKanaLastname(gakkenID.getKana_lastname());
				returnGID.setKana_lastname(gakkenID.getKana_lastname());
				
				// メイ
				gakkenIdInfo.setKanaFirstname(gakkenID.getKana_firstname());
				returnGID.setKana_firstname(gakkenID.getKana_firstname());
				
				// 生年月日
				gakkenIdInfo.setBirth(gakkenID.getBirth());
				returnGID.setBirth(gakkenID.getBirth());
				
				// ニックネーム
				gakkenIdInfo.setNickname(gakkenID.getNickname());
				returnGID.setNickname(gakkenID.getNickname());
				
		        // 性別
				gakkenIdInfo.setSex(Short.toString(gakkenID.getSex()));
				returnGID.setSex(gakkenID.getSex());
				
		        // 国
				gakkenIdInfo.setCountry(gakkenID.getCountry());
				returnGID.setCountry(gakkenID.getCountry());
				
		        // 郵便番号
				gakkenIdInfo.setZip(gakkenID.getZip());
				returnGID.setZip(gakkenID.getZip());

		        // 電話番号
				gakkenIdInfo.setTel(gakkenID.getTel());
				returnGID.setTel(gakkenID.getTel());

				// 電話番号2
				gakkenIdInfo.setTel2(gakkenID.getTel_2());
				returnGID.setTel_2(gakkenID.getTel_2());

		        // 都道府県コード
				gakkenIdInfo.setPrefCd(gakkenID.getPref_cd());
				returnGID.setPref_cd(gakkenID.getPref_cd());
				
				// 住所1（都道府県）
				if (!StringUtils.isEmpty(gakkenID.getPref_cd())) {
					MstAddrEntity addr = addrDao.selectOne(new QueryWrapper<MstAddrEntity>().select("distinct prefct_nm")
							.eq("prefct_cd", gakkenID.getPref_cd()).eq("del_flg", GakkenConstant.DEL_FLG.NO.getValue()));
					if (addr != null) {
						gakkenIdInfo.setAddress1(addr.getPrefctNm());
						returnGID.setAddress_1(addr.getPrefctNm());
					}
				}
				
				// 住所2（市区町村）
				gakkenIdInfo.setAddress2(gakkenID.getAddress_2());
				returnGID.setAddress_2(gakkenID.getAddress_2());

		        // 住所3（番地）
				gakkenIdInfo.setAddress3(gakkenID.getAddress_3());
				returnGID.setAddress_3(gakkenID.getAddress_3());
				
		        // 住所4（号室）
				gakkenIdInfo.setAddress4(gakkenID.getAddress_4());
				returnGID.setAddress_4(gakkenID.getAddress_4());
				
		        // 住所5
				gakkenIdInfo.setAddress5(gakkenID.getAddress_5());
				returnGID.setAddress_5(gakkenID.getAddress_5());
				
		        // 住所6
				gakkenIdInfo.setAddress6(gakkenID.getAddress_6());
				returnGID.setAddress_6(gakkenID.getAddress_6());
				
				// 家族コード
		        if (StringUtils.isEmpty(gakkenID.getFamily_cd())) {
		        	String familycd = StringUtils.getStringRandom(8) + DateUtils.dateTimeNow();
		            gakkenIdInfo.setFamilyCd(familycd);
		            returnGID.setFamily_cd(familycd);
		        } else {
		            gakkenIdInfo.setFamilyCd(gakkenID.getFamily_cd());
		            returnGID.setFamily_cd(gakkenID.getFamily_cd());
		        }
		        
		        // 家族タイプ
		        if (StringUtils.isEmpty(gakkenID.getFamily_type())) {
		            gakkenIdInfo.setFamilyType(GakkenConstant.API_FAMILY_TYPE.NOT_REPRESENT.getValue());
		            returnGID.setFamily_type(GakkenConstant.API_FAMILY_TYPE.NOT_REPRESENT.getValue());
		        } else {
		        	gakkenIdInfo.setFamilyType(gakkenID.getFamily_type());
		            returnGID.setFamily_type(gakkenID.getFamily_type());
		        }
		        
		        // DM配信フラグ
	        	gakkenIdInfo.setDirectmailFlg("1");
	            returnGID.setDirectmail_flg((short) 1);

		        // 決済不可フラ グ
		        gakkenIdInfo.setPaymentNotFlg("0");
		        returnGID.setPayment_not_flg((short) 0);
		        
		        // 退会フラグ
		        gakkenIdInfo.setWithdrawlFlg("0");
		        returnGID.setWithdrawl_flg((short) 0);
		        
		        // 同意フラグ
		        gakkenIdInfo.setAgreeFlg("1");
		        returnGID.setAgree_flg((short) 1);

				// データ利用許諾
				gakkenIdInfo.setDataUtillAgreeFlg("1");
				returnGID.setData_utill_agree_flg((short) 1);
		        
		        Date now = DateUtils.getNowDate();
		        // 登録日
		        gakkenIdInfo.setRegistT(now);
		        returnGID.setRegist_ts(now);
		        
		        // 更新日
		        gakkenIdInfo.setUpdateTs(now);
		        returnGID.setUpdate_ts(now);
		        
		        // パスワード最 終更新日
		        gakkenIdInfo.setPassUpdateTs(now);
		        returnGID.setPass_update_ts(now);
		        
		        // 退会日->null
		        // 誕生日開始 ->null
		        // 誕生日終了 ->null

				// 予備項目_1
				gakkenIdInfo.setExt1(gakkenID.getExt_1());
				returnGID.setExt_1(gakkenID.getExt_1());

				// 予備項目_2
				gakkenIdInfo.setExt2(gakkenID.getExt_2());
				returnGID.setExt_2(gakkenID.getExt_2());

				// 予備項目_3
				gakkenIdInfo.setExt3(gakkenID.getExt_3());
				returnGID.setExt_3(gakkenID.getExt_3());
		        
		        gakkenApiDao.insertGakkenId(gakkenIdInfo);
		        returnGIDs[i] = returnGID;
			}
			
			result.setSuccess(true);
			// 登録結果
			result.setResult(true);
			// 学研ID構造体配列
			result.setGakkenID(returnGIDs);
			
			return result;
		}
	}
	
	/**
						 * 個人情報取得（バルク）（GakkenID(特権)）
						 *
						 * @param gidpks  学研IDプライマリキー配列
						 * @param gakkenIdSelect 学研ID構造体（結果限定）
						 * @return 結果（APIResultGakkenIDs->
						 * 					recordCounts:件数
						 * 					GakkenID:学研ID構造体配列）
						 * @throws Exception
						 */
						public V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDs getGakkenIDBulk(String[] gidpks,
								V2GakkenIDPrivilegeSvcStub.GakkenIDselect gakkenIdSelect) throws Exception {

							// 本API
							if (realApi) {
								V2GakkenIDPrivilegeSvcStub stub = StubEngine.getGakkenIDPrivilegeSvcStub(requestUrl, user, password);

								// パラメータの設定
								V2GakkenIDPrivilegeSvcStub.GetGakkenIDBulk request = new V2GakkenIDPrivilegeSvcStub.GetGakkenIDBulk();
								// サービスサイトコード
								request.setServiceCode(serviceCd);
								// サービスショップコード
								request.setShopCode(shopCd);
								// 特権アクセストークン
								request.setAccessToken(priToken);
								// 学研IDプライマリキー配列
								request.setGidpk(gidpks);

								if (gakkenIdSelect != null) {
									// 学研ID構造体（結果限定）
									request.setGakkenIDselect(gakkenIdSelect);
								}

								// 学研API_ 個人情報取得（バルク）をアクセス
								V2GakkenIDPrivilegeSvcStub.GetGakkenIDBulkResponse response = stub.getGakkenIDBulk(request);

								V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDs result = response.get_return();

								if (!result.getSuccess()) {
									throw new Exception("学研API呼出[GakkenId#getGakkenIDBulk]はエラーが発生しました。\r\n" + result.getErrorcode() + ":"
											+ result.getErrorMessage());
								}

								return result;
							} else {
								// 仮API
								if (gidpks == null || gidpks.length == 0) {
									throw new Exception("学研API呼出[GakkenId#getGakkenIDBulk]はエラーが発生しました。");
								}
								V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDs result = new V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDs();
								List<V2GakkenIDPrivilegeSvcStub.GakkenID> returnGakkenIDs = new ArrayList<V2GakkenIDPrivilegeSvcStub.GakkenID>();

								for (String gidpk : gidpks) {

									GakkenId condition = new GakkenId();
									condition.setGidpk(gidpk);

									List<GakkenId> gakkenIds = gakkenApiDao.selectGakkenId(condition);
									if (gakkenIds != null && !gakkenIds.isEmpty()) {
										// 学研ID構造体
										GakkenId gidResult = gakkenIds.get(0);
										// 学研ID構造体返却用
										V2GakkenIDPrivilegeSvcStub.GakkenID returnGakkenID = new V2GakkenIDPrivilegeSvcStub.GakkenID();
										// 学研IDプライマリキー
										if (gakkenIdSelect == null || gakkenIdSelect.getGidpk()) {
											returnGakkenID.setGidpk(gidResult.getGidpk());
										}
										// 学研ID
										if (gakkenIdSelect == null || gakkenIdSelect.getGid()) {
											returnGakkenID.setGid(gidResult.getGid());
										}
										// メールアドレス1
										if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_1()) {
											returnGakkenID.setMailaddress_1(gidResult.getMailaddress1());
										}
										// メールアドレス2
										if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_2()) {
											returnGakkenID.setMailaddress_2(gidResult.getMailaddress2());
										}
										// メールアドレス3
										if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_3()) {
											returnGakkenID.setMailaddress_3(gidResult.getMailaddress3());
										}
										// 携帯メールアドレス
										if (gakkenIdSelect == null || gakkenIdSelect.getMobile()) {
											returnGakkenID.setMobile(gidResult.getMobile());
										}
										// 姓
										if (gakkenIdSelect == null || gakkenIdSelect.getLastname()) {
											returnGakkenID.setLastname(gidResult.getLastname());
										}
										// 名
										if (gakkenIdSelect == null || gakkenIdSelect.getFirstname()) {
											returnGakkenID.setFirstname(gidResult.getFirstname());
										}
										// セイ
										if (gakkenIdSelect == null || gakkenIdSelect.getKana_lastname()) {
											returnGakkenID.setKana_lastname(gidResult.getKanaLastname());
										}
										// メイ
										if (gakkenIdSelect == null || gakkenIdSelect.getKana_firstname()) {
											returnGakkenID.setKana_firstname(gidResult.getKanaFirstname());
										}
										// 生年月日
										if (gakkenIdSelect == null || gakkenIdSelect.getBirth()) {
											returnGakkenID.setBirth(gidResult.getBirth());
										}
										// ニックネーム
										if (gakkenIdSelect == null || gakkenIdSelect.getNickname()) {
											returnGakkenID.setNickname(gidResult.getNickname());
										}
										// 性別
										if (gakkenIdSelect == null || gakkenIdSelect.getSex()) {
											if (StringUtils.isNotEmpty(gidResult.getSex())) {
												returnGakkenID.setSex(Short.parseShort(gidResult.getSex()));
											}
										}
										// 国
										if (gakkenIdSelect == null || gakkenIdSelect.getCountry()) {
											returnGakkenID.setCountry(gidResult.getCountry());
										}
										// 郵便番号
										if (gakkenIdSelect == null || gakkenIdSelect.getZip()) {
											returnGakkenID.setZip(gidResult.getZip());
										}
										// 電話番号
										if (gakkenIdSelect == null || gakkenIdSelect.getTel()) {
											returnGakkenID.setTel(gidResult.getTel());
										}
										// 電話番号2
										if (gakkenIdSelect == null || gakkenIdSelect.getTel_2()) {
											returnGakkenID.setTel_2(gidResult.getTel2());
										}
										// 都道府県コード
										if (gakkenIdSelect == null || gakkenIdSelect.getPref_cd()) {
											returnGakkenID.setPref_cd(gidResult.getPrefCd());
										}
										// 住所1（都道府県）
										if (gakkenIdSelect == null || gakkenIdSelect.getAddress_1()) {
											returnGakkenID.setAddress_1(gidResult.getAddress1());
										}
										// 住所2（市区町村）
										if (gakkenIdSelect == null || gakkenIdSelect.getAddress_2()) {
											returnGakkenID.setAddress_2(gidResult.getAddress2());
										}
										// 住所3（番地）
										if (gakkenIdSelect == null || gakkenIdSelect.getAddress_3()) {
											returnGakkenID.setAddress_3(gidResult.getAddress3());
										}
										// 住所4（号室）
										if (gakkenIdSelect == null || gakkenIdSelect.getAddress_4()) {
											returnGakkenID.setAddress_4(gidResult.getAddress4());
										}
										// 住所5
										if (gakkenIdSelect == null || gakkenIdSelect.getAddress_5()) {
											returnGakkenID.setAddress_5(gidResult.getAddress5());
										}
										// 住所6
										if (gakkenIdSelect == null || gakkenIdSelect.getAddress_6()) {
											returnGakkenID.setAddress_6(gidResult.getAddress6());
		        	}
		        	// 家族コード
		        	if (gakkenIdSelect == null || gakkenIdSelect.getFamily_cd()) {
		        		returnGakkenID.setFamily_cd(gidResult.getFamilyCd());
		        	}
		        	// 家族タイプ
		        	if (gakkenIdSelect == null || gakkenIdSelect.getFamily_type()) {
		        		if (gidResult.getFamilyType() != null) {
		        			returnGakkenID.setFamily_type(gidResult.getFamilyType());
		        		}
		        	}
		        	// DM配信フラグ
		        	if (gakkenIdSelect == null || gakkenIdSelect.getDirectmail_flg()) {
		        		if (StringUtils.isNotEmpty(gidResult.getDirectmailFlg())) {
		        			returnGakkenID.setDirectmail_flg(Short.parseShort(gidResult.getDirectmailFlg()));
		        		}
		        	}
		        	// 決済不可フラグ
		        	if (gakkenIdSelect == null || gakkenIdSelect.getPayment_not_flg()) {
		        		if (StringUtils.isNotEmpty(gidResult.getPaymentNotFlg())) {
		        			returnGakkenID.setPayment_not_flg(Short.parseShort(gidResult.getPaymentNotFlg()));
		        		}
		        	}
		        	// 退会フラグ
		        	if (gakkenIdSelect == null || gakkenIdSelect.getWithdrawl_flg()) {
		        		if (StringUtils.isNotEmpty(gidResult.getWithdrawlFlg())) {
		        			returnGakkenID.setWithdrawl_flg(Short.parseShort(gidResult.getWithdrawlFlg()));
		        		}
		        	}
		        	// 同意フラグ
		        	if (gakkenIdSelect == null || gakkenIdSelect.getAgree_flg()) {
		        		if (StringUtils.isNotEmpty(gidResult.getAgreeFlg())) {
		        			returnGakkenID.setAgree_flg(Short.parseShort(gidResult.getAgreeFlg()));
		        		}
		        	}
					// データ利用許諾
					if (gakkenIdSelect == null || gakkenIdSelect.getData_utill_agree_flg()) {
						if (StringUtils.isNotEmpty(gidResult.getDataUtillAgreeFlg())) {
							returnGakkenID.setData_utill_agree_flg(Short.parseShort(gidResult.getDataUtillAgreeFlg()));
						}
					}
		        	// 登録日
		        	if (gakkenIdSelect == null || gakkenIdSelect.getRegist_ts()) {
		        		returnGakkenID.setRegist_ts(gidResult.getRegistT());
		        	}
		        	// 更新日
		        	if (gakkenIdSelect == null || gakkenIdSelect.getUpdate_ts()) {
		        		returnGakkenID.setUpdate_ts(gidResult.getUpdateTs());
		        	}
		        	// パスワード最終更新日
		        	if (gakkenIdSelect == null || gakkenIdSelect.getPass_update_ts()) {
		        		returnGakkenID.setPass_update_ts(gidResult.getPassUpdateTs());
		        	}
		        	// 退会日
		        	if (gakkenIdSelect == null || gakkenIdSelect.getWithdrawl_ts()) {
		        		returnGakkenID.setWithdrawl_ts(gidResult.getWithdrawlTs());
		        	}
					// 予備項目_1
					if (gakkenIdSelect == null || gakkenIdSelect.getExt_1()) {
						returnGakkenID.setExt_1(gidResult.getExt1());
					}
					// 予備項目_2
					if (gakkenIdSelect == null || gakkenIdSelect.getExt_2()) {
						returnGakkenID.setExt_2(gidResult.getExt2());
					}
					// 予備項目_3
					if (gakkenIdSelect == null || gakkenIdSelect.getExt_3()) {
						returnGakkenID.setExt_3(gidResult.getExt3());
					}
		        	
		        	returnGakkenIDs.add(returnGakkenID);
	            }
	            
			}
			result.setSuccess(true);
			result.setRecordCounts(returnGakkenIDs.size());
			result.setGakkenID(returnGakkenIDs.toArray(new V2GakkenIDPrivilegeSvcStub.GakkenID[returnGakkenIDs.size()]));
			
			return result;
		}
	}

	/**
	 * 個人情報更新（バルク）（GakkenID(特権)）
	 * 
	 * @param gakkenIDs 学研ID構造体配列
	 * @param gakkenIdSelect 学研ID構造体（登録指定）
	 * @return 結果APIResultGakkenIDRegists->
	 * 					result:登録結果
	 * 					GakkenID:学研ID構造体配列）
	 * @throws Exception
	 */
	public V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDRegists updateGakkenIDBulk(
			V2GakkenIDPrivilegeSvcStub.GakkenID[] gakkenIDs, V2GakkenIDPrivilegeSvcStub.GakkenIDselect gakkenIdSelect)
			throws Exception {

		// 本API
		if (realApi) {
			V2GakkenIDPrivilegeSvcStub stub = StubEngine.getGakkenIDPrivilegeSvcStub(requestUrl, user, password);

			// パラメータの設定
			V2GakkenIDPrivilegeSvcStub.UpdateGakkenIDBulk request = new V2GakkenIDPrivilegeSvcStub.UpdateGakkenIDBulk();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// 特権アクセストークン
			request.setAccessToken(priToken);
			// GakkenID配列
			request.setGakkenID(gakkenIDs);

			if (gakkenIdSelect != null) {
				// 学研ID構造体（登録指定）
				request.setGakkenIDselect(gakkenIdSelect);
			}

			V2GakkenIDPrivilegeSvcStub.UpdateGakkenIDBulkResponse response = stub.updateGakkenIDBulk(request);

			V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDRegists result = response.get_return();

			if (!result.getSuccess() || !result.getResult()) {
				for(V2GakkenIDPrivilegeSvcStub.GakkenID item : gakkenIDs){
					/* 2021/09/16 manamiru1-772 cuikl edit start */
					log.error(item.getGidpk());
					/* 2021/09/16 manamiru1-772 cuikl edit end */
				}
				throw new Exception("学研API呼出[GakkenId#updateGakkenIDBulk]はエラーが発生しました。\r\n" + result.getErrorcode() + ":"
						+ result.getErrorMessage());
			}

			return result;
		} else {
			V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDRegists result = new V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDRegists();
			// 仮API
			if (gakkenIDs == null || gakkenIDs.length == 0) {
				throw new Exception("学研API呼出[GakkenId#updateGakkenIDBulk]はエラーが発生しました。");
			}
			// 返却用構造体配列
			V2GakkenIDPrivilegeSvcStub.GakkenID[] returnGIDs = new V2GakkenIDPrivilegeSvcStub.GakkenID[gakkenIDs.length];
			
			for (int i = 0; i < gakkenIDs.length; i++) {
				V2GakkenIDPrivilegeSvcStub.GakkenID gakkenID = gakkenIDs[i];
				
				// 更新用構造体
				GakkenId gakkenIdInfo = new GakkenId();
				// 学研IDプライマリキー
				gakkenIdInfo.setGidpk(gakkenID.getGidpk());

				if (gakkenIdSelect == null || gakkenIdSelect.getGid()) {
					// 学研ID
					gakkenIdInfo.setGid(gakkenID.getGid());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getPass()) {
					// パスワード
					gakkenIdInfo.setPass(gakkenID.getPass());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_1()) {
					// メールアドレス1
					gakkenIdInfo.setMailaddress1(gakkenID.getMailaddress_1());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_2()) {
					// メールアドレス2
					gakkenIdInfo.setMailaddress2(gakkenID.getMailaddress_2());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_3()) {
					// メールアドレス3
					gakkenIdInfo.setMailaddress3(gakkenID.getMailaddress_3());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getMobile()) {
					// 携帯メールアドレス
					gakkenIdInfo.setMobile(gakkenID.getMobile());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getLastname()) {
					// 姓
					gakkenIdInfo.setLastname(gakkenID.getLastname());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getFirstname()) {
					// 名
					gakkenIdInfo.setFirstname(gakkenID.getFirstname());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getKana_lastname()) {
					// セイ
					gakkenIdInfo.setKanaLastname(gakkenID.getKana_lastname());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getKana_firstname()) {
					// メイ
					gakkenIdInfo.setKanaFirstname(gakkenID.getKana_firstname());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getKana_firstname()) {
					// 生年月日
					gakkenIdInfo.setBirth(gakkenID.getBirth());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getNickname()) {
					// ニックネーム
					gakkenIdInfo.setNickname(gakkenID.getNickname());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getSex()) {
					// 性別
					gakkenIdInfo.setSex(Short.toString(gakkenID.getSex()));
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getCountry()) {
					// 国
					gakkenIdInfo.setCountry(gakkenID.getCountry());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getZip()) {
					// 郵便番号
					gakkenIdInfo.setZip(gakkenID.getZip());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getTel()) {
					// 電話番号
					gakkenIdInfo.setTel(gakkenID.getTel());
				}

				if (gakkenIdSelect == null || gakkenIdSelect.getTel_2()) {
					// 電話番号2
					gakkenIdInfo.setTel2(gakkenID.getTel_2());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getPref_cd()) {
					// 都道府県コード
					gakkenIdInfo.setPrefCd(gakkenID.getPref_cd());
					
					MstAddrEntity addr = addrDao.selectOne(new QueryWrapper<MstAddrEntity>().select("distinct prefct_nm")
							.eq("prefct_cd", gakkenID.getPref_cd()).eq("del_flg", GakkenConstant.DEL_FLG.NO.getValue()));
					// 住所1（都道府県）
					if (addr != null) {
						gakkenIdInfo.setAddress1(addr.getPrefctNm());
					}
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getAddress_2()) {
					// 住所2（市区町村）
					gakkenIdInfo.setAddress2(gakkenID.getAddress_2());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getAddress_3()) {
					// 住所3（番地）
					gakkenIdInfo.setAddress3(gakkenID.getAddress_3());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getAddress_4()) {
					// 住所4（号室）
					gakkenIdInfo.setAddress4(gakkenID.getAddress_4());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getAddress_5()) {
					// 住所5
					gakkenIdInfo.setAddress5(gakkenID.getAddress_5());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getAddress_6()) {
					// 住所6
					gakkenIdInfo.setAddress6(gakkenID.getAddress_6());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getFamily_type()) {
					// 家族タイプ
					gakkenIdInfo.setFamilyType(gakkenID.getFamily_type());
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getDirectmail_flg()) {
					// DM配信フラグ
					gakkenIdInfo.setDirectmailFlg(Short.toString(gakkenID.getDirectmail_flg()));
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getPayment_not_flg()) {
					// 決済不可フラグ
					gakkenIdInfo.setPaymentNotFlg(Short.toString(gakkenID.getDirectmail_flg()));
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getWithdrawl_flg()) {
					// 退会フラグ
					gakkenIdInfo.setWithdrawlFlg(Short.toString(gakkenID.getWithdrawl_flg()));
				}
				
				if (gakkenIdSelect == null || gakkenIdSelect.getAgree_flg()) {
					// 同意フラグ
					gakkenIdInfo.setAgreeFlg(Short.toString(gakkenID.getAgree_flg()));
				}

				if (gakkenIdSelect == null || gakkenIdSelect.getData_utill_agree_flg()) {
					// データ利用許諾
					gakkenIdInfo.setDataUtillAgreeFlg(Short.toString(gakkenID.getData_utill_agree_flg()));
				}
				
				Date now = DateUtils.getNowDate();
				// 更新日
				gakkenIdInfo.setUpdateTs(now);
				
		        // 退会日->更新しない
		        // 誕生日開始 ->更新しない
		        // 誕生日終了 ->更新しない

				if (gakkenIdSelect == null || gakkenIdSelect.getExt_1()) {
					// 予備項目_1
					gakkenIdInfo.setExt1(gakkenID.getExt_1());
				}

				if (gakkenIdSelect == null || gakkenIdSelect.getExt_2()) {
					// 予備項目_2
					gakkenIdInfo.setExt2(gakkenID.getExt_2());
				}

				if (gakkenIdSelect == null || gakkenIdSelect.getExt_3()) {
					// 予備項目_3
					gakkenIdInfo.setExt3(gakkenID.getExt_3());
				}

				gakkenApiDao.updateGakkenId(gakkenIdInfo);
				
				// 返却
				GakkenId condition = new GakkenId();
                condition.setGidpk(gakkenIdInfo.getGidpk());
                GakkenId gidResult = gakkenApiDao.selectGakkenId(condition).get(0);
                
				// 返却用構造体
				V2GakkenIDPrivilegeSvcStub.GakkenID returnGakkenID = new V2GakkenIDPrivilegeSvcStub.GakkenID();
				
				// 学研IDプライマリキー
        		returnGakkenID.setGidpk(gidResult.getGidpk());
	        	// 学研ID
        		returnGakkenID.setGid(gidResult.getGid());
	        	// メールアドレス1
        		returnGakkenID.setMailaddress_1(gidResult.getMailaddress1());
	        	// メールアドレス2
        		returnGakkenID.setMailaddress_2(gidResult.getMailaddress2());
	        	// メールアドレス3
        		returnGakkenID.setMailaddress_3(gidResult.getMailaddress3());
	        	// 携帯メールアドレス
        		returnGakkenID.setMobile(gidResult.getMobile());
	        	// 姓
        		returnGakkenID.setLastname(gidResult.getLastname());
	        	// 名
        		returnGakkenID.setFirstname(gidResult.getFirstname());
	        	// セイ
        		returnGakkenID.setKana_lastname(gidResult.getKanaLastname());
	        	// メイ
        		returnGakkenID.setKana_firstname(gidResult.getKanaFirstname());
	        	// 生年月日
        		returnGakkenID.setBirth(gidResult.getBirth());
	        	// ニックネーム
        		returnGakkenID.setNickname(gidResult.getNickname());
	        	// 性別
        		if (StringUtils.isNotEmpty(gidResult.getSex())) {
        			returnGakkenID.setSex(Short.parseShort(gidResult.getSex()));
        		}
	        	// 国
        		returnGakkenID.setCountry(gidResult.getCountry());
	        	// 郵便番号
        		returnGakkenID.setZip(gidResult.getZip());
	        	// 電話番号
        		returnGakkenID.setTel(gidResult.getTel());
				// 電話番号2
				returnGakkenID.setTel_2(gidResult.getTel2());
	        	// 都道府県コード
        		returnGakkenID.setPref_cd(gidResult.getPrefCd());
	        	// 住所1（都道府県）
        		returnGakkenID.setAddress_1(gidResult.getAddress1());
	        	// 住所2（市区町村）
        		returnGakkenID.setAddress_2(gidResult.getAddress2());
	        	// 住所3（番地）
        		returnGakkenID.setAddress_3(gidResult.getAddress3());
	        	// 住所4（号室）
        		returnGakkenID.setAddress_4(gidResult.getAddress4());
	        	// 住所5
        		returnGakkenID.setAddress_5(gidResult.getAddress5());
	        	// 住所6
        		returnGakkenID.setAddress_6(gidResult.getAddress6());
	        	// 家族コード
        		returnGakkenID.setFamily_cd(gidResult.getFamilyCd());
	        	// 家族タイプ
        		if (gidResult.getFamilyType() != null) {
        			returnGakkenID.setFamily_type(gidResult.getFamilyType());
        		}
	        	// DM配信フラグ
        		if (StringUtils.isNotEmpty(gidResult.getDirectmailFlg())) {
        			returnGakkenID.setDirectmail_flg(Short.parseShort(gidResult.getDirectmailFlg()));
        		}
	        	// 決済不可フラグ
        		if (StringUtils.isNotEmpty(gidResult.getPaymentNotFlg())) {
        			returnGakkenID.setPayment_not_flg(Short.parseShort(gidResult.getPaymentNotFlg()));
        		}
	        	// 退会フラグ
        		if (StringUtils.isNotEmpty(gidResult.getWithdrawlFlg())) {
        			returnGakkenID.setWithdrawl_flg(Short.parseShort(gidResult.getWithdrawlFlg()));
        		}
	        	// 同意フラグ
        		if (StringUtils.isNotEmpty(gidResult.getAgreeFlg())) {
        			returnGakkenID.setAgree_flg(Short.parseShort(gidResult.getAgreeFlg()));
        		}
				// データ利用許諾
				if (StringUtils.isNotEmpty(gidResult.getDataUtillAgreeFlg())) {
					returnGakkenID.setData_utill_agree_flg(Short.parseShort(gidResult.getDataUtillAgreeFlg()));
				}
	        	// 登録日
        		returnGakkenID.setRegist_ts(gidResult.getRegistT());
	        	// 更新日
        		returnGakkenID.setUpdate_ts(gidResult.getUpdateTs());
	        	// パスワード最終更新日
        		returnGakkenID.setPass_update_ts(gidResult.getPassUpdateTs());
	        	// 退会日
        		returnGakkenID.setWithdrawl_ts(gidResult.getWithdrawlTs());

				// 予備項目_1
				returnGakkenID.setExt_1(gidResult.getExt1());
				// 予備項目_2
				returnGakkenID.setExt_2(gidResult.getExt2());
				// 予備項目_1
				returnGakkenID.setExt_3(gidResult.getExt3());
        		
        		returnGIDs[i] = returnGakkenID;
			}

			result.setSuccess(true);
			// 登録結果
			result.setResult(true);
			// 学研ID構造体配列
			result.setGakkenID(returnGIDs);
						
			return result;
		}
	}
	
	/**
	 * 個人情報検索（GakkenID(特権)）
	 * 
	 * @param gakkenID 学研ID構造体
	 * @param gakkenIdSelect 学研ID構造体（結果限定）
	 * @return 結果APIResultGakkenIDs->
	 * 					recordCounts:件数
	 * 					GakkenID:学研ID構造体配列）
	 * @throws Exception
	 */
	public V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDs getGakkenIDSearch(V2GakkenIDPrivilegeSvcStub.GakkenID gakkenID,
			V2GakkenIDPrivilegeSvcStub.GakkenIDselect gakkenIdSelect) throws Exception {

		// 本API
		if (realApi) {
			V2GakkenIDPrivilegeSvcStub stub = StubEngine.getGakkenIDPrivilegeSvcStub(requestUrl, user, password);

			// パラメータの設定
			V2GakkenIDPrivilegeSvcStub.GetGakkenIDSearch request = new V2GakkenIDPrivilegeSvcStub.GetGakkenIDSearch();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// 特権アクセストークン
			request.setAccessToken(priToken);
			// 学研ID構造体
			request.setGakkenID(gakkenID);
			if (gakkenIdSelect != null) {
				// 学研ID構造体（結果限定）
				request.setGakkenIDselect(gakkenIdSelect);
			}

			V2GakkenIDPrivilegeSvcStub.GetGakkenIDSearchResponse response = stub.getGakkenIDSearch(request);

			V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDs result = response.get_return();

			if (!result.getSuccess()) {
				throw new Exception("学研API呼出[GakkenId#getGakkenIDSearch]はエラーが発生しました。\r\n" + result.getErrorcode() + ":"
						+ result.getErrorMessage());
			}

			return result;
		} else {
			// 仮API
			// 検索条件
			GakkenId condition = new GakkenId();
			// 学研IDプライマリキー
			condition.setGidpk(gakkenID.getGidpk());
			// 学研ID
			condition.setGid(gakkenID.getGid());
			// メールアドレス1
			condition.setMailaddress1(gakkenID.getMailaddress_1());
			// 携帯メールアドレス
			condition.setMobile(gakkenID.getMobile());
			// 姓
			condition.setLastname(gakkenID.getLastname());
			// 名
			condition.setFirstname(gakkenID.getFirstname());
			// セイ
			condition.setKanaLastname(gakkenID.getKana_lastname());
			// メイ
			condition.setKanaFirstname(gakkenID.getKana_firstname());
			// 生年月日
			condition.setBirth(gakkenID.getBirth());
			// ニックネーム
			condition.setNickname(gakkenID.getNickname());
			// 性別
			condition.setSex(Short.toString(gakkenID.getSex()));
			// 国
			condition.setCountry(gakkenID.getCountry());
			// 郵便番号
			condition.setZip(gakkenID.getZip());
			// 電話番号
			condition.setTel(gakkenID.getTel());
			// 電話番号
			condition.setTel2(gakkenID.getTel_2());
			// 都道府県コード
			condition.setPrefCd(gakkenID.getPref_cd());
			// 住所1（都道府県）
			condition.setAddress1(gakkenID.getAddress_1());
			// 住所2（市区町村）
			condition.setAddress2(gakkenID.getAddress_2());
			// 住所3（番地）
			condition.setAddress3(gakkenID.getAddress_3());
			// 住所4（号室）
			condition.setAddress4(gakkenID.getAddress_4());
			// 住所5
			condition.setAddress5(gakkenID.getAddress_5());
			// 住所6
			condition.setAddress6(gakkenID.getAddress_6());
			// 家族コード
			condition.setFamilyCd(gakkenID.getFamily_cd());
			// 家族タイプ
			condition.setFamilyType(gakkenID.getFamily_type());
			// DM配信フラグ
			condition.setDirectmailFlg(Short.toString(gakkenID.getDirectmail_flg()));
			// 決済不可フラグ
			condition.setPaymentNotFlg(Short.toString(gakkenID.getPayment_not_flg()));
			// 退会フラグ
			condition.setWithdrawlFlg(Short.toString(gakkenID.getWithdrawl_flg()));
			// 同意フラグ
			condition.setAgreeFlg(Short.toString(gakkenID.getAgree_flg()));
			// データ利用許諾
			condition.setDataUtillAgreeFlg(Short.toString(gakkenID.getData_utill_agree_flg()));
			// 退会日
			condition.setWithdrawlTs(gakkenID.getWithdrawl_ts());
			// 誕生日開始
			condition.setBirthFrom(gakkenID.getBirth_from());
			// 誕生日終了
			condition.setBirthEnd(gakkenID.getBirth_end());

			List<GakkenId> gakkenIds = gakkenApiDao.selectGakkenId(condition);
			
			// 返却用構造体リスト
			List<V2GakkenIDPrivilegeSvcStub.GakkenID> returnGakkenIDs = new ArrayList<V2GakkenIDPrivilegeSvcStub.GakkenID>();
			
			for (GakkenId gidResult : gakkenIds) {
				V2GakkenIDPrivilegeSvcStub.GakkenID returnGakkenID = new V2GakkenIDPrivilegeSvcStub.GakkenID();
				
				// 学研IDプライマリキー
	        	if (gakkenIdSelect == null || gakkenIdSelect.getGidpk()) {
	        		returnGakkenID.setGidpk(gidResult.getGidpk());
	        	}
	        	// 学研ID
	        	if (gakkenIdSelect == null || gakkenIdSelect.getGid()) {
	        		returnGakkenID.setGid(gidResult.getGid());
	        	}
	        	// メールアドレス1
	        	if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_1()) {
	        		returnGakkenID.setMailaddress_1(gidResult.getMailaddress1());
	        	}
	        	// メールアドレス2
	        	if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_2()) {
	        		returnGakkenID.setMailaddress_2(gidResult.getMailaddress2());
	        	}
	        	// メールアドレス3
	        	if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_3()) {
	        		returnGakkenID.setMailaddress_3(gidResult.getMailaddress3());
	        	}
	        	// 携帯メールアドレス
	        	if (gakkenIdSelect == null || gakkenIdSelect.getMobile()) {
	        		returnGakkenID.setMobile(gidResult.getMobile());
	        	}
	        	// 姓
	        	if (gakkenIdSelect == null || gakkenIdSelect.getLastname()) {
	        		returnGakkenID.setLastname(gidResult.getLastname());
	        	}
	        	// 名
	        	if (gakkenIdSelect == null || gakkenIdSelect.getFirstname()) {
	        		returnGakkenID.setFirstname(gidResult.getFirstname());
	        	}
	        	// セイ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getKana_lastname()) {
	        		returnGakkenID.setKana_lastname(gidResult.getKanaLastname());
	        	}
	        	// メイ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getKana_firstname()) {
	        		returnGakkenID.setKana_firstname(gidResult.getKanaFirstname());
	        	}
	        	// 生年月日
	        	if (gakkenIdSelect == null || gakkenIdSelect.getBirth()) {
	        		returnGakkenID.setBirth(gidResult.getBirth());
	        	}
	        	// ニックネーム
	        	if (gakkenIdSelect == null || gakkenIdSelect.getNickname()) {
	        		returnGakkenID.setNickname(gidResult.getNickname());
	        	}
	        	// 性別
	        	if (gakkenIdSelect == null || gakkenIdSelect.getSex()) {
	        		if (StringUtils.isNotEmpty(gidResult.getSex())) {
	        			returnGakkenID.setSex(Short.parseShort(gidResult.getSex()));
	        		}
	        	}
	        	// 国
	        	if (gakkenIdSelect == null || gakkenIdSelect.getCountry()) {
	        		returnGakkenID.setCountry(gidResult.getCountry());
	        	}
	        	// 郵便番号
	        	if (gakkenIdSelect == null || gakkenIdSelect.getZip()) {
	        		returnGakkenID.setZip(gidResult.getZip());
	        	}
	        	// 電話番号
	        	if (gakkenIdSelect == null || gakkenIdSelect.getTel()) {
	        		returnGakkenID.setTel(gidResult.getTel());
	        	}
				// 電話番号2
				if (gakkenIdSelect == null || gakkenIdSelect.getTel_2()) {
					returnGakkenID.setTel_2(gidResult.getTel2());
				}
	        	// 都道府県コード
	        	if (gakkenIdSelect == null || gakkenIdSelect.getPref_cd()) {
	        		returnGakkenID.setPref_cd(gidResult.getPrefCd());
	        	}
	        	// 住所1（都道府県）
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_1()) {
	        		returnGakkenID.setAddress_1(gidResult.getAddress1());
	        	}
	        	// 住所2（市区町村）
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_2()) {
	        		returnGakkenID.setAddress_2(gidResult.getAddress2());
	        	}
	        	// 住所3（番地）
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_3()) {
	        		returnGakkenID.setAddress_3(gidResult.getAddress3());
	        	}
	        	// 住所4（号室）
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_4()) {
	        		returnGakkenID.setAddress_4(gidResult.getAddress4());
	        	}
	        	// 住所5
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_5()) {
	        		returnGakkenID.setAddress_5(gidResult.getAddress5());
	        	}
	        	// 住所6
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_6()) {
	        		returnGakkenID.setAddress_6(gidResult.getAddress6());
	        	}
	        	// 家族コード
	        	if (gakkenIdSelect == null || gakkenIdSelect.getFamily_cd()) {
	        		returnGakkenID.setFamily_cd(gidResult.getFamilyCd());
	        	}
	        	// 家族タイプ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getFamily_type()) {
	        		if (gidResult.getFamilyType() != null) {
	        			returnGakkenID.setFamily_type(gidResult.getFamilyType());
	        		}
	        	}
	        	// DM配信フラグ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getDirectmail_flg()) {
	        		if (StringUtils.isNotEmpty(gidResult.getDirectmailFlg())) {
	        			returnGakkenID.setDirectmail_flg(Short.parseShort(gidResult.getDirectmailFlg()));
	        		}
	        	}
	        	// 決済不可フラグ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getPayment_not_flg()) {
	        		if (StringUtils.isNotEmpty(gidResult.getPaymentNotFlg())) {
	        			returnGakkenID.setPayment_not_flg(Short.parseShort(gidResult.getPaymentNotFlg()));
	        		}
	        	}
	        	// 退会フラグ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getWithdrawl_flg()) {
	        		if (StringUtils.isNotEmpty(gidResult.getWithdrawlFlg())) {
	        			returnGakkenID.setWithdrawl_flg(Short.parseShort(gidResult.getWithdrawlFlg()));
	        		}
	        	}
	        	// 同意フラグ
	        	if (gakkenIdSelect == null || gakkenIdSelect.getAgree_flg()) {
	        		if (StringUtils.isNotEmpty(gidResult.getAgreeFlg())) {
	        			returnGakkenID.setAgree_flg(Short.parseShort(gidResult.getAgreeFlg()));
	        		}
	        	}
				// データ利用許諾
				if (gakkenIdSelect == null || gakkenIdSelect.getData_utill_agree_flg()) {
					if (StringUtils.isNotEmpty(gidResult.getDataUtillAgreeFlg())) {
						returnGakkenID.setData_utill_agree_flg(Short.parseShort(gidResult.getDataUtillAgreeFlg()));
					}
				}
	        	// 登録日
	        	if (gakkenIdSelect == null || gakkenIdSelect.getRegist_ts()) {
	        		returnGakkenID.setRegist_ts(gidResult.getRegistT());
	        	}
	        	// 更新日
	        	if (gakkenIdSelect == null || gakkenIdSelect.getUpdate_ts()) {
	        		returnGakkenID.setUpdate_ts(gidResult.getUpdateTs());
	        	}
	        	// パスワード最終更新日
	        	if (gakkenIdSelect == null || gakkenIdSelect.getPass_update_ts()) {
	        		returnGakkenID.setPass_update_ts(gidResult.getPassUpdateTs());
	        	}
	        	// 退会日
	        	if (gakkenIdSelect == null || gakkenIdSelect.getWithdrawl_ts()) {
	        		returnGakkenID.setWithdrawl_ts(gidResult.getWithdrawlTs());
	        	}
				// 予備項目_1
				if (gakkenIdSelect == null || gakkenIdSelect.getExt_1()) {
					returnGakkenID.setExt_1(gidResult.getExt1());
				}
				// 予備項目_2
				if (gakkenIdSelect == null || gakkenIdSelect.getExt_2()) {
					returnGakkenID.setExt_2(gidResult.getExt2());
				}
				// 予備項目_3
				if (gakkenIdSelect == null || gakkenIdSelect.getExt_3()) {
					returnGakkenID.setExt_3(gidResult.getExt3());
				}

	        	
	        	returnGakkenIDs.add(returnGakkenID);
				
			}
			
			V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDs result = new V2GakkenIDPrivilegeSvcStub.APIResultGakkenIDs();
			result.setSuccess(true);
			result.setRecordCounts(returnGakkenIDs.size());
			result.setGakkenID(returnGakkenIDs.toArray(new V2GakkenIDPrivilegeSvcStub.GakkenID[returnGakkenIDs.size()]));
			
			return result;
		}

	}
	
	/**
	 * メールアドレス重複確認（GakkenID(特権)）
	 * 
	 * @param mailaddress メールアドレス
	 * @return　結果 true:重複,false：重複しない
	 * @throws Exception
	 */
	public boolean checkMailaddress(String mailaddress) throws Exception {

		// 本API
		if (realApi) {
			V2GakkenIDPrivilegeSvcStub stub = StubEngine.getGakkenIDPrivilegeSvcStub(requestUrl, user, password);

			// パラメータの設定
			V2GakkenIDPrivilegeSvcStub.CheckMailaddress request = new V2GakkenIDPrivilegeSvcStub.CheckMailaddress();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// 特権アクセストークン
			request.setAccessToken(priToken);
			// メールアドレス
			request.setMailaddress(mailaddress);

			V2GakkenIDPrivilegeSvcStub.CheckMailaddressResponse response = stub.checkMailaddress(request);

			V2GakkenIDPrivilegeSvcStub.APIResultExist result = response.get_return();

			if (!result.getSuccess()) {
				throw new Exception("学研API呼出[GakkenId#checkMailaddress]はエラーが発生しました。\r\n" + result.getErrorcode() + ":"
						+ result.getErrorMessage());
			}

			return result.getExist();
		} else {
			// 仮API
	        GakkenId condition = new GakkenId();
	        condition.setMailaddress1(mailaddress);

	        List<GakkenId> gakkenIds = gakkenApiDao.selectGakkenId(condition);
	        
	        if (gakkenIds != null && !gakkenIds.isEmpty()) {
	            // 重複確認結果（重複）
	            return true;
	        } else {
	            // 重複確認結果（重複しない）
	        	return false;
	        }
		}
	}

	/**
	 * アクセストークン更新（GakkenID(一般)）
	 *
	 * @param accessToken アクセストーケン
	 * @return 結果（APIResultGakkenIDshort：学研IDトークン構造体）
	 * @throws Exception
	 */
	public V2GakkenIDSvcStub.APIResultGakkenIDshort updateAccesstoken(String accessToken) throws Exception {

		// 本API
		if (realApi) {
			V2GakkenIDSvcStub stub = StubEngine.getGakkenIDSvcStub(requestUrl, user, password);

			// パラメータの設定
			V2GakkenIDSvcStub.UpdateAccesstoken request = new V2GakkenIDSvcStub.UpdateAccesstoken();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// アクセストークン
			request.setAccessToken(accessToken);
			

			// 学研API_アクセストークン更新をアクセス
			V2GakkenIDSvcStub.UpdateAccesstokenResponse response = stub.updateAccesstoken(request);

			V2GakkenIDSvcStub.APIResultGakkenIDshort result = response.get_return();
			if (!result.getSuccess()) {
				throw new Exception("学研API呼出[GakkenId#updateAccesstoken]はエラーが発生しました。\r\n" + result.getErrorcode() + ":"
						+ result.getErrorMessage());
			}

			return result;
		} else {
			V2GakkenIDSvcStub.APIResultGakkenIDshort result = new V2GakkenIDSvcStub.APIResultGakkenIDshort();
        	// 返却用学研IDトークン構造体
			V2GakkenIDSvcStub.GakkenIDshort returnGakkenIDshort = new V2GakkenIDSvcStub.GakkenIDshort();
        	// アクセストークン
			returnGakkenIDshort.setSso_accesstoken(StringUtils.getStringRandom(32));
        	// アクセストークン有効期限
        	Calendar accEnd = Calendar.getInstance();
        	accEnd.add(11, 2);
        	returnGakkenIDshort.setSso_accesstoken_end(accEnd);
        	
        	result.setGakkenIDshort(returnGakkenIDshort);

			return result;
		}
	}
	
	/**
	 * 個人情報更新（GakkenID(一般)）
	 *
	 * @param accessToken アクセストーケン
	 * @param gakkenID 学研ID構造体
	 * @param gakkenIdSelect 学研ID構造体（登録指定）
	 * @return 結果（APIResultGakkenIDRegist->
	 * 					result:登録結果
	 * 					GakkenID:学研ID構造体）
	 * @throws Exception
	 */
	public V2GakkenIDSvcStub.APIResultGakkenIDRegist updateGakkenID(String accessToken, V2GakkenIDSvcStub.GakkenID gakkenID,
																	V2GakkenIDSvcStub.GakkenIDselect gakkenIdSelect) throws Exception {

		// 本API
		if (realApi) {
			V2GakkenIDSvcStub stub = StubEngine.getGakkenIDSvcStub(requestUrl, user, password);

			// パラメータの設定
			V2GakkenIDSvcStub.UpdateGakkenID request = new V2GakkenIDSvcStub.UpdateGakkenID();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// アクセストークン
			request.setAccessToken(accessToken);
			// 学研ID構造体
			request.setGakkenID(gakkenID);

			if (gakkenIdSelect != null) {
				// 学研ID構造体（登録指定）
				request.setGakkenIDselect(gakkenIdSelect);
			}

			// 学研API_個人情報更新をアクセス
			V2GakkenIDSvcStub.UpdateGakkenIDResponse response = stub.updateGakkenID(request);

			V2GakkenIDSvcStub.APIResultGakkenIDRegist result = response.get_return();
			if (!result.getSuccess() || !result.getResult()) {
				/* 2021/09/16 manamiru1-772 cuikl edit start */
				log.error(gakkenID.getGidpk());
				/* 2021/09/16 manamiru1-772 cuikl edit end */
				throw new Exception("学研API呼出[GakkenId#updateGakkenID]はエラーが発生しました。\r\n" + result.getErrorcode() + ":"
						+ result.getErrorMessage());
			}

			return result;
		} else {
			// 更新用構造体
			GakkenId updateInfo = new GakkenId();
			
			// 学研IDプライマリキー
			updateInfo.setGidpk(gakkenID.getGidpk());
			
			if (gakkenIdSelect == null || gakkenIdSelect.getGid()) {
				// 学研ID
				updateInfo.setGid(gakkenID.getGid());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getPass()) {
				// パスワード
				updateInfo.setPass(gakkenID.getPass());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_1()) {
				// メールアドレス1
				updateInfo.setMailaddress1(gakkenID.getMailaddress_1());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_2()) {
				// メールアドレス2
				updateInfo.setMailaddress2(gakkenID.getMailaddress_2());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_3()) {
				// メールアドレス3
				updateInfo.setMailaddress3(gakkenID.getMailaddress_3());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getMobile()) {
				// 携帯メールアドレス
				updateInfo.setMobile(gakkenID.getMobile());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getLastname()) {
				// 姓
				updateInfo.setLastname(gakkenID.getLastname());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getFirstname()) {
				// 名
				updateInfo.setFirstname(gakkenID.getFirstname());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getKana_lastname()) {
				// セイ
				updateInfo.setKanaLastname(gakkenID.getKana_lastname());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getKana_firstname()) {
				// メイ
				updateInfo.setKanaFirstname(gakkenID.getKana_firstname());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getBirth()) {
				// 生年月日
				updateInfo.setBirth(gakkenID.getBirth());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getNickname()) {
				// ニックネーム
				updateInfo.setNickname(gakkenID.getNickname());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getSex()) {
				// 性別
				updateInfo.setSex(Short.toString(gakkenID.getSex()));
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getCountry()) {
				// 国
				updateInfo.setCountry(gakkenID.getCountry());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getZip()) {
				// 郵便番号
				updateInfo.setZip(gakkenID.getZip());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getTel()) {
				// 電話番号
				updateInfo.setTel(gakkenID.getTel());
			}

			if (gakkenIdSelect == null || gakkenIdSelect.getTel_2()) {
				// 電話番号2
				updateInfo.setTel2(gakkenID.getTel_2());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getPref_cd()) {
				// 都道府県コード
				updateInfo.setPrefCd(gakkenID.getPref_cd());
				
				// 住所1（都道府県）
	            MstAddrEntity addr = addrDao.selectOne(new QueryWrapper<MstAddrEntity>().select("distinct prefct_nm")
	                    .eq("prefct_cd", gakkenID.getPref_cd()).eq("del_flg", GakkenConstant.DEL_FLG.NO.getValue()));
	            if (addr != null) {
	            	updateInfo.setAddress1(addr.getPrefctNm());
	            }
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getAddress_2()) {
				// 住所2（市区町村）
				updateInfo.setAddress2(gakkenID.getAddress_2());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getAddress_3()) {
				// 住所3（番地）
				updateInfo.setAddress3(gakkenID.getAddress_3());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getAddress_4()) {
				// 住所4（号室）
				updateInfo.setAddress4(gakkenID.getAddress_4());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getAddress_5()) {
				// 住所5
				updateInfo.setAddress5(gakkenID.getAddress_5());
			}

			if (gakkenIdSelect == null || gakkenIdSelect.getAddress_6()) {
				// 住所6
				updateInfo.setAddress6(gakkenID.getAddress_6());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getFamily_type()) {
				// 家族タイプ
				updateInfo.setFamilyType(gakkenID.getFamily_type());
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getDirectmail_flg()) {
				// DM配信フラグ
				updateInfo.setDirectmailFlg(Short.toString(gakkenID.getDirectmail_flg()));
			}
			
			if (gakkenIdSelect == null || gakkenIdSelect.getAgree_flg()) {
				// 同意フラグ
				updateInfo.setAgreeFlg(Short.toString(gakkenID.getAgree_flg()));
			}

			if (gakkenIdSelect == null || gakkenIdSelect.getData_utill_agree_flg()) {
				// データ利用許諾
				updateInfo.setDataUtillAgreeFlg(Short.toString(gakkenID.getData_utill_agree_flg()));
			}

			if (gakkenIdSelect == null || gakkenIdSelect.getExt_1()) {
				// 予備項目_1
				updateInfo.setExt1(gakkenID.getExt_1());
			}
			if (gakkenIdSelect == null || gakkenIdSelect.getExt_2()) {
				// 予備項目_2
				updateInfo.setExt2(gakkenID.getExt_2());
			}
			if (gakkenIdSelect == null || gakkenIdSelect.getExt_3()) {
				// 予備項目_3
				updateInfo.setExt3(gakkenID.getExt_3());
			}
			
			Date now = DateUtils.getNowDate();
			// 更新日
			updateInfo.setUpdateTs(now);
			
			gakkenApiDao.updateGakkenId(updateInfo);
			
			
			// 返却
			GakkenId condition = new GakkenId();
            condition.setGidpk(updateInfo.getGidpk());
            GakkenId gidResult = gakkenApiDao.selectGakkenId(condition).get(0);
            
			// 返却用構造体
			V2GakkenIDSvcStub.GakkenID returnGakkenID = new V2GakkenIDSvcStub.GakkenID();
			
			// 学研IDプライマリキー
    		returnGakkenID.setGidpk(gidResult.getGidpk());
        	// 学研ID
    		returnGakkenID.setGid(gidResult.getGid());
        	// メールアドレス1
    		returnGakkenID.setMailaddress_1(gidResult.getMailaddress1());
        	// メールアドレス2
    		returnGakkenID.setMailaddress_2(gidResult.getMailaddress2());
        	// メールアドレス3
    		returnGakkenID.setMailaddress_3(gidResult.getMailaddress3());
        	// 携帯メールアドレス
    		returnGakkenID.setMobile(gidResult.getMobile());
        	// 姓
    		returnGakkenID.setLastname(gidResult.getLastname());
        	// 名
    		returnGakkenID.setFirstname(gidResult.getFirstname());
        	// セイ
    		returnGakkenID.setKana_lastname(gidResult.getKanaLastname());
        	// メイ
    		returnGakkenID.setKana_firstname(gidResult.getKanaFirstname());
        	// 生年月日
    		returnGakkenID.setBirth(gidResult.getBirth());
        	// ニックネーム
    		returnGakkenID.setNickname(gidResult.getNickname());
        	// 性別
    		if (StringUtils.isNotEmpty(gidResult.getSex())) {
    			returnGakkenID.setSex(Short.parseShort(gidResult.getSex()));
    		}
        	// 国
    		returnGakkenID.setCountry(gidResult.getCountry());
        	// 郵便番号
    		returnGakkenID.setZip(gidResult.getZip());
        	// 電話番号
    		returnGakkenID.setTel(gidResult.getTel());
			// 電話番号
			returnGakkenID.setTel_2(gidResult.getTel2());
        	// 都道府県コード
    		returnGakkenID.setPref_cd(gidResult.getPrefCd());
        	// 住所1（都道府県）
    		returnGakkenID.setAddress_1(gidResult.getAddress1());
        	// 住所2（市区町村）
    		returnGakkenID.setAddress_2(gidResult.getAddress2());
        	// 住所3（番地）
    		returnGakkenID.setAddress_3(gidResult.getAddress3());
        	// 住所4（号室）
    		returnGakkenID.setAddress_4(gidResult.getAddress4());
        	// 住所5
    		returnGakkenID.setAddress_5(gidResult.getAddress5());
        	// 住所6
    		returnGakkenID.setAddress_6(gidResult.getAddress6());
        	// 家族コード
    		returnGakkenID.setFamily_cd(gidResult.getFamilyCd());
        	// 家族タイプ
    		if (gidResult.getFamilyType() != null) {
    			returnGakkenID.setFamily_type(gidResult.getFamilyType());
    		}
        	// DM配信フラグ
    		if (StringUtils.isNotEmpty(gidResult.getDirectmailFlg())) {
    			returnGakkenID.setDirectmail_flg(Short.parseShort(gidResult.getDirectmailFlg()));
    		}
        	// 決済不可フラグ
    		if (StringUtils.isNotEmpty(gidResult.getPaymentNotFlg())) {
    			returnGakkenID.setPayment_not_flg(Short.parseShort(gidResult.getPaymentNotFlg()));
    		}
        	// 退会フラグ
    		if (StringUtils.isNotEmpty(gidResult.getWithdrawlFlg())) {
    			returnGakkenID.setWithdrawl_flg(Short.parseShort(gidResult.getWithdrawlFlg()));
    		}
        	// 同意フラグ
    		if (StringUtils.isNotEmpty(gidResult.getAgreeFlg())) {
    			returnGakkenID.setAgree_flg(Short.parseShort(gidResult.getAgreeFlg()));
    		}
			// 同意フラグ
			if (StringUtils.isNotEmpty(gidResult.getDataUtillAgreeFlg())) {
				returnGakkenID.setData_utill_agree_flg(Short.parseShort(gidResult.getDataUtillAgreeFlg()));
			}
        	// 登録日
    		returnGakkenID.setRegist_ts(gidResult.getRegistT());
        	// 更新日
    		returnGakkenID.setUpdate_ts(gidResult.getUpdateTs());
        	// パスワード最終更新日
    		returnGakkenID.setPass_update_ts(gidResult.getPassUpdateTs());
        	// 退会日
    		returnGakkenID.setWithdrawl_ts(gidResult.getWithdrawlTs());
			// 予備項目_1
			returnGakkenID.setExt_1(gidResult.getExt1());
			// 予備項目_2
			returnGakkenID.setExt_2(gidResult.getExt2());
			// 予備項目_3
			returnGakkenID.setExt_3(gidResult.getExt3());

    		// 返却結果
			V2GakkenIDSvcStub.APIResultGakkenIDRegist result = new V2GakkenIDSvcStub.APIResultGakkenIDRegist();
    		result.setSuccess(true);
    		result.setResult(true);
    		result.setGakkenID(returnGakkenID);

			return result;
		}
		
	}
	
	/**
	 * 個人情報取得（GakkenID(一般)）
	 * 
	 * @param accessToken アクセストーケン
	 * @param gidpk 学研IDプライマリキー
	 * @param gakkenIdSelect 学研ID構造体（結果限定）
	 * @return 結果（APIResultGakkenID->
	 * 					GakkenID:学研ID構造体）
	 * @throws Exception
	 */
	public V2GakkenIDSvcStub.APIResultGakkenID getGakkenID(String accessToken, String gidpk,
														   V2GakkenIDSvcStub.GakkenIDselect gakkenIdSelect) throws Exception {

		// 本API
		if (realApi) {
			V2GakkenIDSvcStub stub = StubEngine.getGakkenIDSvcStub(requestUrl, user, password);

			// パラメータの設定
			V2GakkenIDSvcStub.GetGakkenID request = new V2GakkenIDSvcStub.GetGakkenID();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// アクセストーケン
			request.setAccessToken(accessToken);
			// GIDPK
			request.setGidpk(gidpk);

			if (gakkenIdSelect != null) {
				// 学研ID構造体（結果限定）
				request.setGakkenIDselect(gakkenIdSelect);
			}

			V2GakkenIDSvcStub.GetGakkenIDResponse response = stub.getGakkenID(request);

			V2GakkenIDSvcStub.APIResultGakkenID result = response.get_return();
			if (!result.getSuccess()) {
				throw new Exception("学研API呼出[GakkenId#getGakkenID]はエラーが発生しました。\r\n" + result.getErrorcode() + ":"
						+ result.getErrorMessage());
			}

			return result;
		} else {
			V2GakkenIDSvcStub.APIResultGakkenID result = new V2GakkenIDSvcStub.APIResultGakkenID();

			GakkenId condition = new GakkenId();
	        condition.setGidpk(gidpk);

	        List<GakkenId> gakkenIds = gakkenApiDao.selectGakkenId(condition);
	        
	        if (gakkenIds == null || gakkenIds.isEmpty()) {
	        	result.setSuccess(true);
	        	return result;
	        }
	        
	        GakkenId gidResult = gakkenIds.get(0);

			V2GakkenIDSvcStub.GakkenID returnGakkenID = new V2GakkenIDSvcStub.GakkenID();
			
			// 学研IDプライマリキー
        	if (gakkenIdSelect == null || gakkenIdSelect.getGidpk()) {
        		returnGakkenID.setGidpk(gidResult.getGidpk());
        	}
        	// 学研ID
        	if (gakkenIdSelect == null || gakkenIdSelect.getGid()) {
        		returnGakkenID.setGid(gidResult.getGid());
        	}
        	// メールアドレス1
        	if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_1()) {
        		returnGakkenID.setMailaddress_1(gidResult.getMailaddress1());
        	}
        	// メールアドレス2
        	if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_2()) {
        		returnGakkenID.setMailaddress_2(gidResult.getMailaddress2());
        	}
        	// メールアドレス3
        	if (gakkenIdSelect == null || gakkenIdSelect.getMailaddress_3()) {
        		returnGakkenID.setMailaddress_3(gidResult.getMailaddress3());
        	}
        	// 携帯メールアドレス
        	if (gakkenIdSelect == null || gakkenIdSelect.getMobile()) {
        		returnGakkenID.setMobile(gidResult.getMobile());
        	}
        	// 姓
        	if (gakkenIdSelect == null || gakkenIdSelect.getLastname()) {
        		returnGakkenID.setLastname(gidResult.getLastname());
        	}
        	// 名
        	if (gakkenIdSelect == null || gakkenIdSelect.getFirstname()) {
        		returnGakkenID.setFirstname(gidResult.getFirstname());
        	}
        	// セイ
        	if (gakkenIdSelect == null || gakkenIdSelect.getKana_lastname()) {
        		returnGakkenID.setKana_lastname(gidResult.getKanaLastname());
        	}
        	// メイ
        	if (gakkenIdSelect == null || gakkenIdSelect.getKana_firstname()) {
        		returnGakkenID.setKana_firstname(gidResult.getKanaFirstname());
        	}
        	// 生年月日
        	if (gakkenIdSelect == null || gakkenIdSelect.getBirth()) {
        		returnGakkenID.setBirth(gidResult.getBirth());
        	}
        	// ニックネーム
        	if (gakkenIdSelect == null || gakkenIdSelect.getNickname()) {
        		returnGakkenID.setNickname(gidResult.getNickname());
        	}
        	// 性別
        	if (gakkenIdSelect == null || gakkenIdSelect.getSex()) {
        		if (StringUtils.isNotEmpty(gidResult.getSex())) {
        			returnGakkenID.setSex(Short.parseShort(gidResult.getSex()));
        		}
        	}
        	// 国
        	if (gakkenIdSelect == null || gakkenIdSelect.getCountry()) {
        		returnGakkenID.setCountry(gidResult.getCountry());
        	}
        	// 郵便番号
        	if (gakkenIdSelect == null || gakkenIdSelect.getZip()) {
        		returnGakkenID.setZip(gidResult.getZip());
        	}
        	// 電話番号
        	if (gakkenIdSelect == null || gakkenIdSelect.getTel()) {
        		returnGakkenID.setTel(gidResult.getTel());
        	}
			// 電話番号2
			if (gakkenIdSelect == null || gakkenIdSelect.getTel_2()) {
				returnGakkenID.setTel_2(gidResult.getTel2());
			}
        	// 都道府県コード
        	if (gakkenIdSelect == null || gakkenIdSelect.getPref_cd()) {
        		returnGakkenID.setPref_cd(gidResult.getPrefCd());
        	}
        	// 住所1（都道府県）
        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_1()) {
        		returnGakkenID.setAddress_1(gidResult.getAddress1());
        	}
        	// 住所2（市区町村）
        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_2()) {
        		returnGakkenID.setAddress_2(gidResult.getAddress2());
        	}
        	// 住所3（番地）
        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_3()) {
        		returnGakkenID.setAddress_3(gidResult.getAddress3());
        	}
        	// 住所4（号室）
        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_4()) {
        		returnGakkenID.setAddress_4(gidResult.getAddress4());
        	}
        	// 住所5
        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_5()) {
        		returnGakkenID.setAddress_5(gidResult.getAddress5());
        	}
        	// 住所6
        	if (gakkenIdSelect == null || gakkenIdSelect.getAddress_6()) {
        		returnGakkenID.setAddress_6(gidResult.getAddress6());
        	}
        	// 家族コード
        	if (gakkenIdSelect == null || gakkenIdSelect.getFamily_cd()) {
        		returnGakkenID.setFamily_cd(gidResult.getFamilyCd());
        	}
        	// 家族タイプ
        	if (gakkenIdSelect == null || gakkenIdSelect.getFamily_type()) {
        		if (gidResult.getFamilyType() != null) {
        			returnGakkenID.setFamily_type(gidResult.getFamilyType());
        		}
        	}
        	// DM配信フラグ
        	if (gakkenIdSelect == null || gakkenIdSelect.getDirectmail_flg()) {
        		if (StringUtils.isNotEmpty(gidResult.getDirectmailFlg())) {
        			returnGakkenID.setDirectmail_flg(Short.parseShort(gidResult.getDirectmailFlg()));
        		}
        	}
        	// 決済不可フラグ
        	if (gakkenIdSelect == null || gakkenIdSelect.getPayment_not_flg()) {
        		if (StringUtils.isNotEmpty(gidResult.getPaymentNotFlg())) {
        			returnGakkenID.setPayment_not_flg(Short.parseShort(gidResult.getPaymentNotFlg()));
        		}
        	}
        	// 退会フラグ
        	if (gakkenIdSelect == null || gakkenIdSelect.getWithdrawl_flg()) {
        		if (StringUtils.isNotEmpty(gidResult.getWithdrawlFlg())) {
        			returnGakkenID.setWithdrawl_flg(Short.parseShort(gidResult.getWithdrawlFlg()));
        		}
        	}
        	// 同意フラグ
        	if (gakkenIdSelect == null || gakkenIdSelect.getAgree_flg()) {
        		if (StringUtils.isNotEmpty(gidResult.getAgreeFlg())) {
        			returnGakkenID.setAgree_flg(Short.parseShort(gidResult.getAgreeFlg()));
        		}
        	}
			// データ利用許諾
			if (gakkenIdSelect == null || gakkenIdSelect.getData_utill_agree_flg()) {
				if (StringUtils.isNotEmpty(gidResult.getDataUtillAgreeFlg())) {
					returnGakkenID.setData_utill_agree_flg(Short.parseShort(gidResult.getDataUtillAgreeFlg()));
				}
			}
        	// 登録日
        	if (gakkenIdSelect == null || gakkenIdSelect.getRegist_ts()) {
        		returnGakkenID.setRegist_ts(gidResult.getRegistT());
        	}
        	// 更新日
        	if (gakkenIdSelect == null || gakkenIdSelect.getUpdate_ts()) {
        		returnGakkenID.setUpdate_ts(gidResult.getUpdateTs());
        	}
        	// パスワード最終更新日
        	if (gakkenIdSelect == null || gakkenIdSelect.getPass_update_ts()) {
        		returnGakkenID.setPass_update_ts(gidResult.getPassUpdateTs());
        	}
        	// 退会日
        	if (gakkenIdSelect == null || gakkenIdSelect.getWithdrawl_ts()) {
        		returnGakkenID.setWithdrawl_ts(gidResult.getWithdrawlTs());
        	}
			// 予備項目_1
			if (gakkenIdSelect == null || gakkenIdSelect.getExt_1()) {
				returnGakkenID.setExt_1(gidResult.getExt1());
			}
			// 予備項目_2
			if (gakkenIdSelect == null || gakkenIdSelect.getExt_2()) {
				returnGakkenID.setExt_2(gidResult.getExt2());
			}
			// 予備項目_3
			if (gakkenIdSelect == null || gakkenIdSelect.getExt_3()) {
				returnGakkenID.setExt_3(gidResult.getExt3());
			}
        	
        	result.setSuccess(true);
        	result.setGakkenID(returnGakkenID);
        	
			return result;
		}

	}
	
//-----------GakkenID-End-----------
//-----------GakkenTID-Start--------

	/**
	 * 個人情報登録（GakkenTID(一般)）
	 *
	 * @param accessToken アクセストーケン
	 * @param gakkenTID   学研TID構造体
	 * @return 結果（APIResultGakkenTransactionIDRegist->
	 * 					result:登録結果
	 * 					GakkenTransactionID:学研TID構造体）
	 * @throws Exception
	 */
	public V2GakkenTransactionIDSvcStub.APIResultGakkenTransactionIDRegist registGakkenTransactionID(String accessToken,
																									 V2GakkenTransactionIDSvcStub.GakkenTransactionID gakkenTID) throws Exception {

		// 本API
		if (realApi) {
			V2GakkenTransactionIDSvcStub stub = StubEngine.getGakkenTransactionIDSvcStub(requestUrl, user, password);

			// パラメータの設定
			V2GakkenTransactionIDSvcStub.RegistGakkenTransactionID request = new V2GakkenTransactionIDSvcStub.RegistGakkenTransactionID();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// アクセストークン
			request.setAccessToken(accessToken);
			// 学研TID構造体
			request.setGakkenTransactionID(gakkenTID);

			// 学研API_ログイン＋GakkenIDデータをアクセス
			V2GakkenTransactionIDSvcStub.RegistGakkenTransactionIDResponse response = stub
					.registGakkenTransactionID(request);

			V2GakkenTransactionIDSvcStub.APIResultGakkenTransactionIDRegist result = response.get_return();
			if (!result.getSuccess() || !result.getResult()) {
				throw new Exception("学研API呼出[GakkenTId#registGakkenTransactionID]はエラーが発生しました。\r\n"
						+ result.getErrorcode() + ":" + result.getErrorMessage());
			}

			return result;
		} else {
			// 仮API
			// 登録用構造体
			GakkenTransactionID gakkenTransactionID = new GakkenTransactionID();

	        String current = StringUtils.getStringRandom(6) + DateUtils.dateTimeNow();
	        // 学研TIDプライマリキー
	        gakkenTransactionID.setGtidpk(current + "T");
	        // 学研IDプライマリキー
	        gakkenTransactionID.setGidpk(gakkenTID.getGidpk());
	        // 学研PIDプライマリキー
	        gakkenTransactionID.setGpidpk(current + "P");
	        // メールアドレス1
	        gakkenTransactionID.setMailaddress1(gakkenTID.getMailaddress_1());
	        // メールアドレス2
	        gakkenTransactionID.setMailaddress2(gakkenTID.getMailaddress_2());
	        // メールアドレス3
	        gakkenTransactionID.setMailaddress3(gakkenTID.getMailaddress_3());
	        // 携帯メールアドレス
	        gakkenTransactionID.setMobile(gakkenTID.getMobile());
	        // 姓
	        gakkenTransactionID.setLastname(gakkenTID.getLastname());
	        // 名
	        gakkenTransactionID.setFirstname(gakkenTID.getFirstname());
	        // セイ
	        gakkenTransactionID.setKanaLastname(gakkenTID.getKana_lastname());
	        // メイ
	        gakkenTransactionID.setKanaFirstname(gakkenTID.getKana_firstname());
	        // 生年月日
	        gakkenTransactionID.setBirth(gakkenTID.getBirth());
	        // ニックネーム
	        gakkenTransactionID.setNickname(gakkenTID.getNickname());
	        // 性別
	        gakkenTransactionID.setSex(Short.toString(gakkenTID.getSex()));
	        // 国
	        gakkenTransactionID.setCountry(gakkenTID.getCountry());
	        // 郵便番号
	        gakkenTransactionID.setZip(gakkenTID.getZip());
	        // 電話番号
	        gakkenTransactionID.setTel(gakkenTID.getTel());
			// 電話番号2
			gakkenTransactionID.setTel2(gakkenTID.getTel_2());
	        // 都道府県コード
	        gakkenTransactionID.setPrefCd(gakkenTID.getPref_cd());
	        // 住所1（都道府県）
	        if (StringUtils.isNotEmpty(gakkenTID.getPref_cd())) {
	        	 MstAddrEntity addr = addrDao.selectOne(new QueryWrapper<MstAddrEntity>().select("distinct prefct_nm")
	                     .eq("prefct_cd", gakkenTID.getPref_cd()).eq("del_flg", GakkenConstant.DEL_FLG.NO.getValue()));
	             if (addr != null) {
	                 gakkenTransactionID.setAddress1(addr.getPrefctNm());
	             }
	        }
	        // 住所2（市区町村）
	        gakkenTransactionID.setAddress2(gakkenTID.getAddress_2());
	        // 住所3（番地）
	        gakkenTransactionID.setAddress3(gakkenTID.getAddress_3());
	        // 住所4（号室）
	        gakkenTransactionID.setAddress4(gakkenTID.getAddress_4());
	        // 住所5
	        gakkenTransactionID.setAddress5(gakkenTID.getAddress_5());
	        // 住所6
	        gakkenTransactionID.setAddress6(gakkenTID.getAddress_6());
			// 予備項目_1
			gakkenTransactionID.setExt1(gakkenTID.getExt_1());
			// 予備項目_2
			gakkenTransactionID.setExt2(gakkenTID.getExt_2());
			// 予備項目_3
			gakkenTransactionID.setExt3(gakkenTID.getExt_3());

	        // サービスサイトコード
	        gakkenTransactionID.setSiteCd(gakkenTID.getSite_cd());
	        // ショップコード
	        gakkenTransactionID.setShopCd(gakkenTID.getShop_cd());
	        // イベントキー１
	        gakkenTransactionID.setEventKey1(gakkenTID.getEvent_key_1());
	        // イベントキー２
	        gakkenTransactionID.setEventKey2(gakkenTID.getEvent_key_2());
	        // イベントキー３
	        gakkenTransactionID.setEventKey3(gakkenTID.getEvent_key_3());
	        // パートナーコード
	        gakkenTransactionID.setPartnerCd(gakkenTID.getPartner_cd());
	        // 状態
	        gakkenTransactionID.setStatus("1");
	        
	        Date now = DateUtils.getNowDate();
	        // 登録日
	        gakkenTransactionID.setRegistT(now);
	        // 更新日
	        gakkenTransactionID.setUpdateTs(now);
	        // 登録開始
	        gakkenTransactionID.setRegistTsFrom(now);
	        // 登録終了
	        gakkenTransactionID.setRegistTsEnd(now);
	        
	        gakkenApiDao.insertGakkenTransaction(gakkenTransactionID);
	        
	        // 返却
	        GakkenTransactionID condition = new GakkenTransactionID();
	        condition.setGtidpk(gakkenTransactionID.getGtidpk());

	        GakkenTransactionID resultGtid = gakkenApiDao.selectGakkenTID(condition).get(0);

			V2GakkenTransactionIDSvcStub.GakkenTransactionID returnGtid = new V2GakkenTransactionIDSvcStub.GakkenTransactionID();
	        // 学研TIDプライマリキー
	        returnGtid.setGtidpk(resultGtid.getGtidpk());
	        // 学研IDプライマリキー
	        returnGtid.setGidpk(resultGtid.getGidpk());
	        // 学研PIDプライマリキー
	        returnGtid.setGpidpk(resultGtid.getGpidpk());
	        // メールアドレス1
	        returnGtid.setMailaddress_1(resultGtid.getMailaddress1());
	        // メールアドレス2
	        returnGtid.setMailaddress_2(resultGtid.getMailaddress2());
	        // メールアドレス3
	        returnGtid.setMailaddress_3(resultGtid.getMailaddress3());
	        // 携帯メールアドレス
	        returnGtid.setMobile(resultGtid.getMobile());
	        // 姓
	        returnGtid.setLastname(resultGtid.getLastname());
	        // 名
	        returnGtid.setFirstname(resultGtid.getFirstname());
	        // セイ
	        returnGtid.setKana_lastname(resultGtid.getKanaLastname());
	        // メイ
	        returnGtid.setKana_firstname(resultGtid.getKanaFirstname());
	        // 生年月日
	        returnGtid.setBirth(resultGtid.getBirth());
	        // ニックネーム
	        returnGtid.setNickname(resultGtid.getNickname());
	        // 性別
	        if (StringUtils.isNotEmpty(resultGtid.getSex())) {
	        	returnGtid.setSex(Short.parseShort(resultGtid.getSex()));
	        }
	        // 国
	        returnGtid.setCountry(resultGtid.getCountry());
	        // 郵便番号
	        returnGtid.setZip(resultGtid.getZip());
	        // 電話番号
	        returnGtid.setTel(resultGtid.getTel());
			// 電話番号2
			returnGtid.setTel_2(resultGtid.getTel2());
	        // 都道府県コード
	        returnGtid.setPref_cd(resultGtid.getPrefCd());
	        // 住所1（都道府県）
	        returnGtid.setAddress_1(resultGtid.getAddress1());
	        // 住所2（市区町村）
	        returnGtid.setAddress_2(resultGtid.getAddress2());
	        // 住所3（番地）
	        returnGtid.setAddress_3(resultGtid.getAddress3());
	        // 住所4（号室）
	        returnGtid.setAddress_4(resultGtid.getAddress4());
	        // 住所5
	        returnGtid.setAddress_5(resultGtid.getAddress5());
	        // 住所6
	        returnGtid.setAddress_6(resultGtid.getAddress6());
			// 予備項目_1
			returnGtid.setExt_1(resultGtid.getExt1());
			// 予備項目_2
			returnGtid.setExt_2(resultGtid.getExt2());
			// 予備項目_3
			returnGtid.setExt_3(resultGtid.getExt3());
	        // サービスサイトコード
	        returnGtid.setSite_cd(resultGtid.getSiteCd());
	        // ショップコード
	        returnGtid.setShop_cd(resultGtid.getShopCd());
	        // イベントキー１
	        returnGtid.setEvent_key_1(resultGtid.getEventKey1());
	        // イベントキー２
	        returnGtid.setEvent_key_2(resultGtid.getEventKey2());
	        // イベントキー３
	        returnGtid.setEvent_key_3(resultGtid.getEventKey3());
	        // パートナーコード
	        returnGtid.setPartner_cd(resultGtid.getPartnerCd());
	        // 状態
	        if (StringUtils.isNotEmpty(resultGtid.getStatus())) {
	        	returnGtid.setStatus(Short.parseShort(resultGtid.getStatus()));
	        }
	        // 登録日
	        returnGtid.setRegist_ts(resultGtid.getRegistT());
	        // 更新日
	        returnGtid.setUpdate_ts(resultGtid.getUpdateTs());
	        // 登録開始
	        returnGtid.setRegist_ts_from(resultGtid.getRegistTsFrom());
	        // 登録終了
	        returnGtid.setRegist_ts_end(resultGtid.getRegistTsEnd());

			V2GakkenTransactionIDSvcStub.APIResultGakkenTransactionIDRegist result = new V2GakkenTransactionIDSvcStub.APIResultGakkenTransactionIDRegist();
	        result.setSuccess(true);
	        result.setResult(true);
	        result.setGakkenTransactionID(returnGtid);
	        
			return result;
		}
	}

	/**
	 * 個人情報取得（GakkenTID(一般)）
	 *
	 * @param accessToken     アクセストーケン
	 * @param gidpk           学研IDプライマリキー
	 * @param gakkenTIDSelect 学研TID構造体（結果限定）
	 * @return 結果（APIResultGakkenTransactionIDs->
	 * 					recordCounts:件数
	 * 					GakkenTransactionID:学研TID構造体配列）
	 * @throws Exception
	 */
	public V2GakkenTransactionIDSvcStub.APIResultGakkenTransactionIDs getGakkenTransactionID(String accessToken,
			String gidpk, V2GakkenTransactionIDSvcStub.GakkenTransactionIDselect gakkenTIDSelect) throws Exception {

		// 本API
		if (realApi) {
			V2GakkenTransactionIDSvcStub stub = StubEngine.getGakkenTransactionIDSvcStub(requestUrl, user, password);

			// パラメータの設定
			V2GakkenTransactionIDSvcStub.GetGakkenTransactionID request = new V2GakkenTransactionIDSvcStub.GetGakkenTransactionID();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// アクセストークン
			request.setAccessToken(accessToken);
			// 学研IDプライマリキー
			request.setGidpk(gidpk);
			if (gakkenTIDSelect != null) {
				// 学研TID構造体（結果限定）
				request.setGakkenTransactionIDselect(gakkenTIDSelect);
			}

			// 学研API_ログイン＋GakkenIDデータをアクセス
			V2GakkenTransactionIDSvcStub.GetGakkenTransactionIDResponse response = stub.getGakkenTransactionID(request);

			V2GakkenTransactionIDSvcStub.APIResultGakkenTransactionIDs result = response.get_return();
			if (!result.getSuccess()) {
				throw new Exception("学研API呼出[GakkenTId#getGakkenTransactionID]はエラーが発生しました。\r\n" + result.getErrorcode()
						+ ":" + result.getErrorMessage());
			}

			return result;
		} else {
			// 仮API
			V2GakkenTransactionIDSvcStub.APIResultGakkenTransactionIDs result = new V2GakkenTransactionIDSvcStub.APIResultGakkenTransactionIDs();

	        GakkenTransactionID condition = new GakkenTransactionID();
	        condition.setGidpk(gidpk);

	        List<GakkenTransactionID> list = gakkenApiDao.selectGakkenTID(condition);
	        
	        if (list == null || list.isEmpty()) {
	        	result.setSuccess(true);
	        	result.setRecordCounts(0);
	        	
	        	return result;
	        }
	        
	        // 返却用GTID構造体
	        List<V2GakkenTransactionIDSvcStub.GakkenTransactionID> returnGakkenTIDs = new ArrayList<V2GakkenTransactionIDSvcStub.GakkenTransactionID>();
	        
	        for (GakkenTransactionID resultGtid : list) {
				V2GakkenTransactionIDSvcStub.GakkenTransactionID returnGtid = new V2GakkenTransactionIDSvcStub.GakkenTransactionID();

	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getGtidpk()) {
	        		// 学研TIDプライマリキー
	        		returnGtid.setGtidpk(resultGtid.getGtidpk());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getGidpk()) {
			        // 学研IDプライマリキー
			        returnGtid.setGidpk(resultGtid.getGidpk());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getGpidpk()) {
	        		// 学研PIDプライマリキー
	        		returnGtid.setGpidpk(resultGtid.getGpidpk());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_1()) {
	        		// メールアドレス1
	        		returnGtid.setMailaddress_1(resultGtid.getMailaddress1());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_2()) {
	        		// メールアドレス2
	        		returnGtid.setMailaddress_2(resultGtid.getMailaddress2());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_3()) {
	        		// メールアドレス3
	        		returnGtid.setMailaddress_3(resultGtid.getMailaddress3());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMobile()) {
	        		// 携帯メールアドレス
	        		returnGtid.setMobile(resultGtid.getMobile());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getLastname()) {
	        		// 姓
	        		returnGtid.setLastname(resultGtid.getLastname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getFirstname()) {
	        		// 名
	        		returnGtid.setFirstname(resultGtid.getFirstname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getKana_lastname()) {
	        		// セイ
	        		returnGtid.setKana_lastname(resultGtid.getKanaLastname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getKana_firstname()) {
	        		// メイ
	        		returnGtid.setKana_firstname(resultGtid.getKanaFirstname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getBirth()) {
	        		// 生年月日
	        		returnGtid.setBirth(resultGtid.getBirth());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getNickname()) {
	        		// ニックネーム
	        		returnGtid.setNickname(resultGtid.getNickname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getSex()) {
	        		// 性別
	        		if (StringUtils.isNotEmpty(resultGtid.getSex())) {
	        			returnGtid.setSex(Short.parseShort(resultGtid.getSex()));
	        		}
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getCountry()) {
	        		// 国
	        		returnGtid.setCountry(resultGtid.getCountry());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getZip()) {
	        		// 郵便番号
	        		returnGtid.setZip(resultGtid.getZip());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getTel()) {
	        		// 電話番号
	        		returnGtid.setTel(resultGtid.getTel());
	        	}

	        	//電話番号2
				if(gakkenTIDSelect == null || gakkenTIDSelect.getTel_2()){
					returnGtid.setTel_2(resultGtid.getTel2());
				}

				// 予備項目_1
				if(gakkenTIDSelect == null || gakkenTIDSelect.getExt_1()){
					returnGtid.setExt_1(resultGtid.getExt1());
				}
				// 予備項目_2
				if(gakkenTIDSelect == null || gakkenTIDSelect.getExt_2()){
					returnGtid.setExt_2(resultGtid.getExt2());
				}
				// 予備項目_3
				if(gakkenTIDSelect == null || gakkenTIDSelect.getExt_3()){
					returnGtid.setExt_3(resultGtid.getExt3());
				}

	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getPref_cd()) {
	        		// 都道府県コード
	        		returnGtid.setPref_cd(resultGtid.getPrefCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_1()) {
	        		// 住所1（都道府県）
	        		returnGtid.setAddress_1(resultGtid.getAddress1());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_2()) {
	        		// 住所2（市区町村）
	        		returnGtid.setAddress_2(resultGtid.getAddress2());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_3()) {
	        		// 住所3（番地）
	        		returnGtid.setAddress_3(resultGtid.getAddress3());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_4()) {
	        		// 住所4（号室）
	        		returnGtid.setAddress_4(resultGtid.getAddress4());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_5()) {
	        		// 住所5
	        		returnGtid.setAddress_5(resultGtid.getAddress5());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_6()) {
	        		// 住所6
	        		returnGtid.setAddress_6(resultGtid.getAddress6());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getSite_cd()) {
	        		// サービスサイトコード
	        		returnGtid.setSite_cd(resultGtid.getSiteCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getShop_cd()) {
	        		// ショップコード
	        		returnGtid.setShop_cd(resultGtid.getShopCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_1()) {
	        		// イベントキー１
	        		returnGtid.setEvent_key_1(resultGtid.getEventKey1());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_2()) {
	        		// イベントキー２
	        		returnGtid.setEvent_key_2(resultGtid.getEventKey2());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_3()) {
	        		// イベントキー３
	        		returnGtid.setEvent_key_3(resultGtid.getEventKey3());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getPartner_cd()) {
	        		// パートナーコード
	        		returnGtid.setPartner_cd(resultGtid.getPartnerCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getStatus()) {
	        		// 状態
	        		if (StringUtils.isNotEmpty(resultGtid.getStatus())) {
	        			returnGtid.setStatus(Short.parseShort(resultGtid.getStatus()));
	        		}
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getRegist_ts()) {
	        		// 登録日
	        		returnGtid.setRegist_ts(resultGtid.getRegistT());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getUpdate_ts()) {
	        		// 更新日
	        		returnGtid.setUpdate_ts(resultGtid.getUpdateTs());
	        	}
        		// 登録開始
        		returnGtid.setRegist_ts_from(resultGtid.getRegistTsFrom());
		        // 登録終了
		        returnGtid.setRegist_ts_end(resultGtid.getRegistTsEnd());
		        
		        returnGakkenTIDs.add(returnGtid);
	        }
	        
        	result.setSuccess(true);
        	result.setRecordCounts(returnGakkenTIDs.size());
        	result.setGakkenTransactionID(returnGakkenTIDs.toArray(new V2GakkenTransactionIDSvcStub.GakkenTransactionID[returnGakkenTIDs.size()]));

			return result;
		}
	}

	/**
	 * 個人情報取得（GakkenTID(特権)）
	 *
	 * @param gidpk           学研IDプライマリキー
	 * @param gpidpk          学研PIDプライマリキー
	 * @param gtidpk          学研TIDプライマリキー
	 * @param gakkenTIDSelect 学研TID構造体（結果限定）
	 * @return 結果（APIResultGakkenTransactionIDs->
	 * 					recordCounts:件数
	 * 					GakkenTransactionID:学研TID構造体配列）
	 * @throws Exception
	 */
	public V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs getGakkenTransactionID(String gidpk, String gpidpk, String gtidpk, V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionIDselect gakkenTIDSelect)
			throws Exception {

		// 本API
		if (realApi) {
			V2GakkenTransactionIDPrivilegeSvcStub stub = StubEngine.getGakkenTransactionIDPrivilegeSvcStub(requestUrl,
					user, password);

			// パラメータの設定
			V2GakkenTransactionIDPrivilegeSvcStub.GetGakkenTransactionID request = new V2GakkenTransactionIDPrivilegeSvcStub.GetGakkenTransactionID();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// 特権アクセストークン
			request.setAccessToken(priToken);
			if (StringUtils.isNotEmpty(gidpk)) {
				// 学研IDプライマリキー
				request.setGidpk(gidpk);
			}
			if (StringUtils.isNotEmpty(gpidpk)) {
				// 学研PIDプライマリキー
				request.setGpidpk(gpidpk);
			}
			if (StringUtils.isNotEmpty(gtidpk)) {
				// 学研TIDプライマリキー
				request.setGtidpk(gtidpk);
			}
			if (gakkenTIDSelect != null) {
				// 学研TID構造体（結果限定）
				request.setGakkenTransactionIDselect(gakkenTIDSelect);
			}

			// 学研API_個人情報取得(GTID特権)をアクセス
			V2GakkenTransactionIDPrivilegeSvcStub.GetGakkenTransactionIDResponse response = stub
					.getGakkenTransactionID(request);

			V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs result = response.get_return();
			if (!result.getSuccess()) {
				throw new Exception("学研API呼出[GakkenTId#getGakkenTransactionID(priv)]はエラーが発生しました。\r\n"
						+ result.getErrorcode() + ":" + result.getErrorMessage());
			}

			return result;
		} else {
			// 仮API
	        GakkenTransactionID condition = new GakkenTransactionID();
	        condition.setGidpk(gidpk);
	        condition.setGpidpk(gpidpk);
	        condition.setGtidpk(gtidpk);

	        List<GakkenTransactionID> list = gakkenApiDao.selectGakkenTID(condition);

			V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs result = new V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs();

	        if (list == null || list.isEmpty()) {
	        	result.setSuccess(true);
	        	result.setRecordCounts(0);
	        	
	        	return result;
	        }
	        
	        // 返却用GTID構造体
	        List<V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID> returnGakkenTIDs = new ArrayList<V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID>();
	        
	        for (GakkenTransactionID resultGtid : list) {
				V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID returnGtid = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID();
	        	
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getGtidpk()) {
	        		// 学研TIDプライマリキー
	        		returnGtid.setGtidpk(resultGtid.getGtidpk());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getGidpk()) {
			        // 学研IDプライマリキー
			        returnGtid.setGidpk(resultGtid.getGidpk());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getGpidpk()) {
	        		// 学研PIDプライマリキー
	        		returnGtid.setGpidpk(resultGtid.getGpidpk());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_1()) {
	        		// メールアドレス1
	        		returnGtid.setMailaddress_1(resultGtid.getMailaddress1());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_2()) {
	        		// メールアドレス2
	        		returnGtid.setMailaddress_2(resultGtid.getMailaddress2());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_3()) {
	        		// メールアドレス3
	        		returnGtid.setMailaddress_3(resultGtid.getMailaddress3());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMobile()) {
	        		// 携帯メールアドレス
	        		returnGtid.setMobile(resultGtid.getMobile());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getLastname()) {
	        		// 姓
	        		returnGtid.setLastname(resultGtid.getLastname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getFirstname()) {
	        		// 名
	        		returnGtid.setFirstname(resultGtid.getFirstname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getKana_lastname()) {
	        		// セイ
	        		returnGtid.setKana_lastname(resultGtid.getKanaLastname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getKana_firstname()) {
	        		// メイ
	        		returnGtid.setKana_firstname(resultGtid.getKanaFirstname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getBirth()) {
	        		// 生年月日
	        		returnGtid.setBirth(resultGtid.getBirth());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getNickname()) {
	        		// ニックネーム
	        		returnGtid.setNickname(resultGtid.getNickname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getSex()) {
	        		// 性別
	        		if (StringUtils.isNotEmpty(resultGtid.getSex())) {
	        			returnGtid.setSex(Short.parseShort(resultGtid.getSex()));
	        		}
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getCountry()) {
	        		// 国
	        		returnGtid.setCountry(resultGtid.getCountry());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getZip()) {
	        		// 郵便番号
	        		returnGtid.setZip(resultGtid.getZip());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getTel()) {
	        		// 電話番号
	        		returnGtid.setTel(resultGtid.getTel());
	        	}

				// 電話番号
				if (gakkenTIDSelect == null || gakkenTIDSelect.getTel_2()) {
					// 電話番号
					returnGtid.setTel_2(resultGtid.getTel2());
				}
				// 予備項目_1
				if (gakkenTIDSelect == null || gakkenTIDSelect.getExt_1()) {
					// 電話番号
					returnGtid.setExt_1(resultGtid.getExt1());
				}
				// 予備項目_2
				if (gakkenTIDSelect == null || gakkenTIDSelect.getExt_2()) {
					// 電話番号
					returnGtid.setExt_2(resultGtid.getExt2());
				}
				// 予備項目_3
				if (gakkenTIDSelect == null || gakkenTIDSelect.getExt_3()) {
					// 電話番号
					returnGtid.setExt_3(resultGtid.getExt3());
				}

	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getPref_cd()) {
	        		// 都道府県コード
	        		returnGtid.setPref_cd(resultGtid.getPrefCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_1()) {
	        		// 住所1（都道府県）
	        		returnGtid.setAddress_1(resultGtid.getAddress1());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_2()) {
	        		// 住所2（市区町村）
	        		returnGtid.setAddress_2(resultGtid.getAddress2());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_3()) {
	        		// 住所3（番地）
	        		returnGtid.setAddress_3(resultGtid.getAddress3());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_4()) {
	        		// 住所4（号室）
	        		returnGtid.setAddress_4(resultGtid.getAddress4());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_5()) {
	        		// 住所5
	        		returnGtid.setAddress_5(resultGtid.getAddress5());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_6()) {
	        		// 住所6
	        		returnGtid.setAddress_6(resultGtid.getAddress6());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getSite_cd()) {
	        		// サービスサイトコード
	        		returnGtid.setSite_cd(resultGtid.getSiteCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getShop_cd()) {
	        		// ショップコード
	        		returnGtid.setShop_cd(resultGtid.getShopCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_1()) {
	        		// イベントキー１
	        		returnGtid.setEvent_key_1(resultGtid.getEventKey1());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_2()) {
	        		// イベントキー２
	        		returnGtid.setEvent_key_2(resultGtid.getEventKey2());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_3()) {
	        		// イベントキー３
	        		returnGtid.setEvent_key_3(resultGtid.getEventKey3());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getPartner_cd()) {
	        		// パートナーコード
	        		returnGtid.setPartner_cd(resultGtid.getPartnerCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getStatus()) {
	        		// 状態
	        		if (StringUtils.isNotEmpty(resultGtid.getStatus())) {
	        			returnGtid.setStatus(Short.parseShort(resultGtid.getStatus()));
	        		}
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getRegist_ts()) {
	        		// 登録日
	        		returnGtid.setRegist_ts(resultGtid.getRegistT());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getUpdate_ts()) {
	        		// 更新日
	        		returnGtid.setUpdate_ts(resultGtid.getUpdateTs());
	        	}
        		// 登録開始
        		returnGtid.setRegist_ts_from(resultGtid.getRegistTsFrom());
		        // 登録終了
		        returnGtid.setRegist_ts_end(resultGtid.getRegistTsEnd());
		        
		        returnGakkenTIDs.add(returnGtid);
	        }
	        
        	result.setSuccess(true);
        	result.setRecordCounts(returnGakkenTIDs.size());
        	result.setGakkenTransactionID(returnGakkenTIDs.toArray(new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID[returnGakkenTIDs.size()]));

			return result;
			
		}
	}
	
	/**
	 * 個人情報取得（バルク）（GakkenTID(特権)）
	 * 
	 * @param gidpks           学研IDプライマリキー配列
	 * @param gpidpks          学研PIDプライマリキー配列
	 * @param gtidpks          学研TIDプライマリキー配列
	 * @param gakkenTIDSelect  学研TID構造体（結果限定）
	 * @return 結果（APIResultGakkenTransactionIDs->
	 * 					recordCounts:件数
	 * 					GakkenTransactionID:学研TID構造体配列）
	 * @throws Exception
	 */
	public V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs getGakkenTransactionIDBulk(String[] gidpks,
			String[] gpidpks, String[] gtidpks,
																										  V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionIDselect gakkenTIDSelect) throws Exception {

		// 本API
		if (realApi) {
			V2GakkenTransactionIDPrivilegeSvcStub stub = StubEngine.getGakkenTransactionIDPrivilegeSvcStub(requestUrl,
					user, password);
			// パラメータの設定
			V2GakkenTransactionIDPrivilegeSvcStub.GetGakkenTransactionIDBulk request = new V2GakkenTransactionIDPrivilegeSvcStub.GetGakkenTransactionIDBulk();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// 特権アクセストークン
			request.setAccessToken(priToken);
			if (gidpks != null) {
				// 学研IDプライマリキー
				request.setGidpk(gidpks);
			}
			if (gpidpks != null) {
				// 学研PIDプライマリキー
				request.setGpidpk(gpidpks);
			}
			if (gtidpks != null) {
				// 学研TIDプライマリキー
				request.setGtidpk(gtidpks);
			}
			if (gakkenTIDSelect != null) {
				// 学研TID構造体（結果限定）
				request.setGakkenTransactionIDselect(gakkenTIDSelect);
			}

			// 学研API_個人情報取得（バルク）(GTID特権)をアクセス
			V2GakkenTransactionIDPrivilegeSvcStub.GetGakkenTransactionIDBulkResponse response = stub
					.getGakkenTransactionIDBulk(request);

			V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs result = response.get_return();
			if (!result.getSuccess()) {
				throw new Exception("学研API呼出[GakkenTId#getGakkenTransactionIDBulk]はエラーが発生しました。\r\n"
						+ result.getErrorcode() + ":" + result.getErrorMessage());
			}

			return result;
		} else {
			// 仮API
	        List<GakkenTransactionID> list = gakkenApiDao.selectGakkenTIDOfArray(gidpks, gpidpks, gtidpks);

			V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs result = new V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs();

	        if (list == null || list.isEmpty()) {
	        	result.setSuccess(true);
	        	result.setRecordCounts(0);
	        	
	        	return result;
	        }
	        
	        // 返却用GTID構造体
	        List<V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID> returnGakkenTIDs = new ArrayList<V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID>();
	        
	        for (GakkenTransactionID resultGtid : list) {
				V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID returnGtid = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID();
	        	
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getGtidpk()) {
	        		// 学研TIDプライマリキー
	        		returnGtid.setGtidpk(resultGtid.getGtidpk());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getGidpk()) {
			        // 学研IDプライマリキー
			        returnGtid.setGidpk(resultGtid.getGidpk());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getGpidpk()) {
	        		// 学研PIDプライマリキー
	        		returnGtid.setGpidpk(resultGtid.getGpidpk());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_1()) {
	        		// メールアドレス1
	        		returnGtid.setMailaddress_1(resultGtid.getMailaddress1());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_2()) {
	        		// メールアドレス2
	        		returnGtid.setMailaddress_2(resultGtid.getMailaddress2());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_3()) {
	        		// メールアドレス3
	        		returnGtid.setMailaddress_3(resultGtid.getMailaddress3());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getMobile()) {
	        		// 携帯メールアドレス
	        		returnGtid.setMobile(resultGtid.getMobile());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getLastname()) {
	        		// 姓
	        		returnGtid.setLastname(resultGtid.getLastname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getFirstname()) {
	        		// 名
	        		returnGtid.setFirstname(resultGtid.getFirstname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getKana_lastname()) {
	        		// セイ
	        		returnGtid.setKana_lastname(resultGtid.getKanaLastname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getKana_firstname()) {
	        		// メイ
	        		returnGtid.setKana_firstname(resultGtid.getKanaFirstname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getBirth()) {
	        		// 生年月日
	        		returnGtid.setBirth(resultGtid.getBirth());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getNickname()) {
	        		// ニックネーム
	        		returnGtid.setNickname(resultGtid.getNickname());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getSex()) {
	        		// 性別
	        		if (StringUtils.isNotEmpty(resultGtid.getSex())) {
	        			returnGtid.setSex(Short.parseShort(resultGtid.getSex()));
	        		}
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getCountry()) {
	        		// 国
	        		returnGtid.setCountry(resultGtid.getCountry());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getZip()) {
	        		// 郵便番号
	        		returnGtid.setZip(resultGtid.getZip());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getTel()) {
	        		// 電話番号
	        		returnGtid.setTel(resultGtid.getTel());
	        	}

				// 電話番号2
				if (gakkenTIDSelect == null || gakkenTIDSelect.getTel_2()) {
					// 電話番号2
					returnGtid.setTel_2(resultGtid.getTel2());
				}
				// 予備項目_1
				if (gakkenTIDSelect == null || gakkenTIDSelect.getExt_1()) {
					// 予備項目_1
					returnGtid.setExt_1(resultGtid.getExt1());
				}
				// 予備項目_2
				if (gakkenTIDSelect == null || gakkenTIDSelect.getExt_2()) {
					// 予備項目_2
					returnGtid.setExt_2(resultGtid.getExt2());
				}
				// 予備項目_3
				if (gakkenTIDSelect == null || gakkenTIDSelect.getExt_3()) {
					// 予備項目_3
					returnGtid.setExt_3(resultGtid.getExt3());
				}

	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getPref_cd()) {
	        		// 都道府県コード
	        		returnGtid.setPref_cd(resultGtid.getPrefCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_1()) {
	        		// 住所1（都道府県）
	        		returnGtid.setAddress_1(resultGtid.getAddress1());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_2()) {
	        		// 住所2（市区町村）
	        		returnGtid.setAddress_2(resultGtid.getAddress2());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_3()) {
	        		// 住所3（番地）
	        		returnGtid.setAddress_3(resultGtid.getAddress3());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_4()) {
	        		// 住所4（号室）
	        		returnGtid.setAddress_4(resultGtid.getAddress4());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_5()) {
	        		// 住所5
	        		returnGtid.setAddress_5(resultGtid.getAddress5());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_6()) {
	        		// 住所6
	        		returnGtid.setAddress_6(resultGtid.getAddress6());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getSite_cd()) {
	        		// サービスサイトコード
	        		returnGtid.setSite_cd(resultGtid.getSiteCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getShop_cd()) {
	        		// ショップコード
	        		returnGtid.setShop_cd(resultGtid.getShopCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_1()) {
	        		// イベントキー１
	        		returnGtid.setEvent_key_1(resultGtid.getEventKey1());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_2()) {
	        		// イベントキー２
	        		returnGtid.setEvent_key_2(resultGtid.getEventKey2());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_3()) {
	        		// イベントキー３
	        		returnGtid.setEvent_key_3(resultGtid.getEventKey3());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getPartner_cd()) {
	        		// パートナーコード
	        		returnGtid.setPartner_cd(resultGtid.getPartnerCd());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getStatus()) {
	        		// 状態
	        		if (StringUtils.isNotEmpty(resultGtid.getStatus())) {
	        			returnGtid.setStatus(Short.parseShort(resultGtid.getStatus()));
	        		}
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getRegist_ts()) {
	        		// 登録日
	        		returnGtid.setRegist_ts(resultGtid.getRegistT());
	        	}
	        	if (gakkenTIDSelect == null || gakkenTIDSelect.getUpdate_ts()) {
	        		// 更新日
	        		returnGtid.setUpdate_ts(resultGtid.getUpdateTs());
	        	}
        		// 登録開始
        		returnGtid.setRegist_ts_from(resultGtid.getRegistTsFrom());
		        // 登録終了
		        returnGtid.setRegist_ts_end(resultGtid.getRegistTsEnd());
		        
		        returnGakkenTIDs.add(returnGtid);
	        }
	        
        	result.setSuccess(true);
        	result.setRecordCounts(returnGakkenTIDs.size());
        	result.setGakkenTransactionID(returnGakkenTIDs.toArray(new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID[returnGakkenTIDs.size()]));

			return result;
		}
	}
	/**
	 * 個人情報取得（GakkenTID(特権)）
	 *
	 * @param gakkenTransactionID   学研TID構造体
	 * @param gakkenTIDSelect 		学研TID構造体（結果限定）
	 * @return 結果（APIResultGakkenTransactionIDs->
	 * 					recordCounts:件数
	 * 					GakkenTransactionID:学研TID構造体配列）
	 * @throws Exception
	 */
	public V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs getGakkenTransactionIDsearch(V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID gakkenTransactionID, V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionIDselect gakkenTIDSelect)
			throws Exception {

		// 本API
		if (realApi) {
			V2GakkenTransactionIDPrivilegeSvcStub stub = StubEngine.getGakkenTransactionIDPrivilegeSvcStub(requestUrl,
					user, password);

			// パラメータの設定
			V2GakkenTransactionIDPrivilegeSvcStub.GetGakkenTransactionIDsearch request = new V2GakkenTransactionIDPrivilegeSvcStub.GetGakkenTransactionIDsearch();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// 特権アクセストークン
			request.setAccessToken(priToken);
			// 学研TID構造体
			request.setGakkenTransactionID(gakkenTransactionID);
			if (gakkenTIDSelect != null) {
				// 学研TID構造体（結果限定）
				request.setGakkenTransactionIDselect(gakkenTIDSelect);
			}

			// 学研API_個人情報取得(GTID特権)をアクセス
			V2GakkenTransactionIDPrivilegeSvcStub.GetGakkenTransactionIDsearchResponse response = stub.getGakkenTransactionIDsearch(request);

			V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs result = response.get_return();

			if (!result.getSuccess()) {
				throw new Exception("学研API呼出[GakkenTId#getGakkenTransactionIDsearch(priv)]はエラーが発生しました。\r\n"
						+ result.getErrorcode() + ":" + result.getErrorMessage());
			}

			return result;
		} else {
			// 仮API
			GakkenTransactionID condition = new GakkenTransactionID();
			condition.setGidpk(gakkenTransactionID.getGidpk());
			condition.setGpidpk(gakkenTransactionID.getGpidpk());
			condition.setGtidpk(gakkenTransactionID.getGtidpk());
			condition.setMailaddress1(gakkenTransactionID.getMailaddress_1());
			condition.setMailaddress2(gakkenTransactionID.getMailaddress_2());
			condition.setMailaddress3(gakkenTransactionID.getMailaddress_3());
			condition.setMobile(gakkenTransactionID.getMobile());
			condition.setLastname(gakkenTransactionID.getLastname());
			condition.setFirstname(gakkenTransactionID.getFirstname());
			condition.setKanaLastname(gakkenTransactionID.getKana_lastname());
			condition.setKanaFirstname(gakkenTransactionID.getKana_firstname());
			condition.setBirth(gakkenTransactionID.getBirth());
			condition.setNickname(gakkenTransactionID.getNickname());
			condition.setSex(Integer.toString(gakkenTransactionID.getSex()));
			condition.setCountry(gakkenTransactionID.getCountry());
			condition.setZip(gakkenTransactionID.getZip());
			condition.setTel(gakkenTransactionID.getTel());
			condition.setTel2(gakkenTransactionID.getTel_2());
			condition.setPrefCd(gakkenTransactionID.getPref_cd());
			condition.setAddress1(gakkenTransactionID.getAddress_1());
			condition.setAddress2(gakkenTransactionID.getAddress_2());
			condition.setAddress3(gakkenTransactionID.getAddress_3());
			condition.setAddress4(gakkenTransactionID.getAddress_4());
			condition.setAddress5(gakkenTransactionID.getAddress_5());
			condition.setAddress6(gakkenTransactionID.getAddress_6());
			condition.setExt1(gakkenTransactionID.getExt_1());
			condition.setExt2(gakkenTransactionID.getExt_2());
			condition.setExt3(gakkenTransactionID.getExt_3());
			condition.setSiteCd(gakkenTransactionID.getSite_cd());
			condition.setShopCd(gakkenTransactionID.getShop_cd());
			condition.setEventKey1(gakkenTransactionID.getEvent_key_1());
			condition.setEventKey2(gakkenTransactionID.getEvent_key_2());
			condition.setEventKey3(gakkenTransactionID.getEvent_key_3());
			condition.setPartnerCd(gakkenTransactionID.getPartner_cd());
			condition.setStatus(Integer.toString(gakkenTransactionID.getStatus()));
			condition.setRegistT(gakkenTransactionID.getRegist_ts());
			condition.setUpdateTs(gakkenTransactionID.getUpdate_ts());
			condition.setRegistTsFrom(gakkenTransactionID.getRegist_ts_from());
			condition.setRegistTsEnd(gakkenTransactionID.getRegist_ts_end());

			List<GakkenTransactionID> list = gakkenApiDao.selectGakkenTID(condition);

			V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs result = new V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDs();

			if (list == null || list.isEmpty()) {
				result.setSuccess(true);
				result.setRecordCounts(0);

				return result;
			}

			// 返却用GTID構造体
			List<V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID> returnGakkenTIDs = new ArrayList<V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID>();

			for (GakkenTransactionID resultGtid : list) {
				V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID returnGtid = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID();

				if (gakkenTIDSelect == null || gakkenTIDSelect.getGtidpk()) {
					// 学研TIDプライマリキー
					returnGtid.setGtidpk(resultGtid.getGtidpk());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getGidpk()) {
					// 学研IDプライマリキー
					returnGtid.setGidpk(resultGtid.getGidpk());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getGpidpk()) {
					// 学研PIDプライマリキー
					returnGtid.setGpidpk(resultGtid.getGpidpk());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_1()) {
					// メールアドレス1
					returnGtid.setMailaddress_1(resultGtid.getMailaddress1());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_2()) {
					// メールアドレス2
					returnGtid.setMailaddress_2(resultGtid.getMailaddress2());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getMailaddress_3()) {
					// メールアドレス3
					returnGtid.setMailaddress_3(resultGtid.getMailaddress3());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getMobile()) {
					// 携帯メールアドレス
					returnGtid.setMobile(resultGtid.getMobile());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getLastname()) {
					// 姓
					returnGtid.setLastname(resultGtid.getLastname());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getFirstname()) {
					// 名
					returnGtid.setFirstname(resultGtid.getFirstname());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getKana_lastname()) {
					// セイ
					returnGtid.setKana_lastname(resultGtid.getKanaLastname());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getKana_firstname()) {
					// メイ
					returnGtid.setKana_firstname(resultGtid.getKanaFirstname());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getBirth()) {
					// 生年月日
					returnGtid.setBirth(resultGtid.getBirth());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getNickname()) {
					// ニックネーム
					returnGtid.setNickname(resultGtid.getNickname());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getSex()) {
					// 性別
					if (StringUtils.isNotEmpty(resultGtid.getSex())) {
						returnGtid.setSex(Short.parseShort(resultGtid.getSex()));
					}
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getCountry()) {
					// 国
					returnGtid.setCountry(resultGtid.getCountry());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getZip()) {
					// 郵便番号
					returnGtid.setZip(resultGtid.getZip());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getTel()) {
					// 電話番号
					returnGtid.setTel(resultGtid.getTel());
				}

				// 電話番号
				if (gakkenTIDSelect == null || gakkenTIDSelect.getTel_2()) {
					// 電話番号
					returnGtid.setTel_2(resultGtid.getTel2());
				}
				// 予備項目_1
				if (gakkenTIDSelect == null || gakkenTIDSelect.getExt_1()) {
					// 電話番号
					returnGtid.setExt_1(resultGtid.getExt1());
				}
				// 予備項目_2
				if (gakkenTIDSelect == null || gakkenTIDSelect.getExt_2()) {
					// 電話番号
					returnGtid.setExt_2(resultGtid.getExt2());
				}
				// 予備項目_3
				if (gakkenTIDSelect == null || gakkenTIDSelect.getExt_3()) {
					// 電話番号
					returnGtid.setExt_3(resultGtid.getExt3());
				}

				if (gakkenTIDSelect == null || gakkenTIDSelect.getPref_cd()) {
					// 都道府県コード
					returnGtid.setPref_cd(resultGtid.getPrefCd());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_1()) {
					// 住所1（都道府県）
					returnGtid.setAddress_1(resultGtid.getAddress1());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_2()) {
					// 住所2（市区町村）
					returnGtid.setAddress_2(resultGtid.getAddress2());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_3()) {
					// 住所3（番地）
					returnGtid.setAddress_3(resultGtid.getAddress3());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_4()) {
					// 住所4（号室）
					returnGtid.setAddress_4(resultGtid.getAddress4());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_5()) {
					// 住所5
					returnGtid.setAddress_5(resultGtid.getAddress5());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getAddress_6()) {
					// 住所6
					returnGtid.setAddress_6(resultGtid.getAddress6());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getSite_cd()) {
					// サービスサイトコード
					returnGtid.setSite_cd(resultGtid.getSiteCd());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getShop_cd()) {
					// ショップコード
					returnGtid.setShop_cd(resultGtid.getShopCd());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_1()) {
					// イベントキー１
					returnGtid.setEvent_key_1(resultGtid.getEventKey1());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_2()) {
					// イベントキー２
					returnGtid.setEvent_key_2(resultGtid.getEventKey2());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getEvent_key_3()) {
					// イベントキー３
					returnGtid.setEvent_key_3(resultGtid.getEventKey3());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getPartner_cd()) {
					// パートナーコード
					returnGtid.setPartner_cd(resultGtid.getPartnerCd());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getStatus()) {
					// 状態
					if (StringUtils.isNotEmpty(resultGtid.getStatus())) {
						returnGtid.setStatus(Short.parseShort(resultGtid.getStatus()));
					}
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getRegist_ts()) {
					// 登録日
					returnGtid.setRegist_ts(resultGtid.getRegistT());
				}
				if (gakkenTIDSelect == null || gakkenTIDSelect.getUpdate_ts()) {
					// 更新日
					returnGtid.setUpdate_ts(resultGtid.getUpdateTs());
				}
				// 登録開始
				returnGtid.setRegist_ts_from(resultGtid.getRegistTsFrom());
				// 登録終了
				returnGtid.setRegist_ts_end(resultGtid.getRegistTsEnd());

				returnGakkenTIDs.add(returnGtid);
			}

			result.setSuccess(true);
			result.setRecordCounts(returnGakkenTIDs.size());
			result.setGakkenTransactionID(returnGakkenTIDs.toArray(new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID[returnGakkenTIDs.size()]));

			return result;

		}
	}

	/**
	 * トランザクション登録（バルク）（GakkenTID(特権)）
	 *
	 * @param gakkenTIDs   学研TID構造体配列
	 * @return 結果（APIResultGakkenTransactionIDRegist->
	 * 					result:登録結果
	 * 					GakkenTransactionID[]:学研TID構造体配列）
	 * @throws Exception
	 */
	public V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDRegists registGakkenTransactionIDBulk(
			V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID[] gakkenTIDs) throws Exception {

		// 本API
		if (realApi) {
			V2GakkenTransactionIDPrivilegeSvcStub stub = StubEngine.getGakkenTransactionIDPrivilegeSvcStub(requestUrl,
					user, password);

			// パラメータの設定
			V2GakkenTransactionIDPrivilegeSvcStub.RegistGakkenTransactionIDBulk request = new V2GakkenTransactionIDPrivilegeSvcStub.RegistGakkenTransactionIDBulk();
			// サービスサイトコード
			request.setServiceCode(serviceCd);
			// サービスショップコード
			request.setShopCode(shopCd);
			// 特権アクセストークン
			request.setAccessToken(priToken);
			// 学研ID構造体配列
			request.setGakkenTransactionID(gakkenTIDs);

			// 学研API_ログイン＋GakkenIDデータをアクセス
			V2GakkenTransactionIDPrivilegeSvcStub.RegistGakkenTransactionIDBulkResponse response = stub
					.registGakkenTransactionIDBulk(request);

			V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDRegists result = response.get_return();
			if (!result.getSuccess() || !result.getResult()) {
				throw new Exception("学研API呼出[GakkenTId#registGakkenTransactionIDBulk]はエラーが発生しました。\r\n"
						+ result.getErrorcode() + ":" + result.getErrorMessage());
			}

			return result;
		} else {
			// 仮API
			V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDRegists result = new V2GakkenTransactionIDPrivilegeSvcStub.APIResultGakkenTransactionIDRegists();
			
			if (gakkenTIDs == null || gakkenTIDs.length == 0) {
				result.setSuccess(true);
				result.setResult(true);
				return result;
			}
			
	        // 返却用GTID構造体
	        List<V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID> returnGakkenTIDs = new ArrayList<V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID>();

			for (V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID gakkenTID : gakkenTIDs) {
				
				// 登録用構造体
				GakkenTransactionID gakkenTransactionID = new GakkenTransactionID();

		        String current = StringUtils.getStringRandom(6) + DateUtils.dateTimeNow();
		        // 学研TIDプライマリキー
		        gakkenTransactionID.setGtidpk(current + "T");
		        // 学研IDプライマリキー
		        gakkenTransactionID.setGidpk(gakkenTID.getGidpk());
		        // 学研PIDプライマリキー
		        gakkenTransactionID.setGpidpk(current + "P");
		        // メールアドレス1
		        gakkenTransactionID.setMailaddress1(gakkenTID.getMailaddress_1());
		        // メールアドレス2
		        gakkenTransactionID.setMailaddress2(gakkenTID.getMailaddress_2());
		        // メールアドレス3
		        gakkenTransactionID.setMailaddress3(gakkenTID.getMailaddress_3());
		        // 携帯メールアドレス
		        gakkenTransactionID.setMobile(gakkenTID.getMobile());
		        // 姓
		        gakkenTransactionID.setLastname(gakkenTID.getLastname());
		        // 名
		        gakkenTransactionID.setFirstname(gakkenTID.getFirstname());
		        // セイ
		        gakkenTransactionID.setKanaLastname(gakkenTID.getKana_lastname());
		        // メイ
		        gakkenTransactionID.setKanaFirstname(gakkenTID.getKana_firstname());
		        // 生年月日
		        gakkenTransactionID.setBirth(gakkenTID.getBirth());
		        // ニックネーム
		        gakkenTransactionID.setNickname(gakkenTID.getNickname());
		        // 性別
		        gakkenTransactionID.setSex(Short.toString(gakkenTID.getSex()));
		        // 国
		        gakkenTransactionID.setCountry(gakkenTID.getCountry());
		        // 郵便番号
		        gakkenTransactionID.setZip(gakkenTID.getZip());
		        // 電話番号
		        gakkenTransactionID.setTel(gakkenTID.getTel());
				// 電話番号2
				gakkenTransactionID.setTel2(gakkenTID.getTel_2());
		        // 都道府県コード
		        gakkenTransactionID.setPrefCd(gakkenTID.getPref_cd());
		        // 住所1（都道府県）
		        if (StringUtils.isNotEmpty(gakkenTID.getPref_cd())) {
		        	 MstAddrEntity addr = addrDao.selectOne(new QueryWrapper<MstAddrEntity>().select("distinct prefct_nm")
		                     .eq("prefct_cd", gakkenTID.getPref_cd()).eq("del_flg", GakkenConstant.DEL_FLG.NO.getValue()));
		             if (addr != null) {
		                 gakkenTransactionID.setAddress1(addr.getPrefctNm());
		             }
		        }
		        // 住所2（市区町村）
		        gakkenTransactionID.setAddress2(gakkenTID.getAddress_2());
		        // 住所3（番地）
		        gakkenTransactionID.setAddress3(gakkenTID.getAddress_3());
		        // 住所4（号室）
		        gakkenTransactionID.setAddress4(gakkenTID.getAddress_4());
		        // 住所5
		        gakkenTransactionID.setAddress5(gakkenTID.getAddress_5());
		        // 住所6
		        gakkenTransactionID.setAddress6(gakkenTID.getAddress_6());
				// 予備項目_1
				gakkenTransactionID.setExt1(gakkenTID.getExt_1());
				// 予備項目_2
				gakkenTransactionID.setExt2(gakkenTID.getExt_2());
				// 予備項目_1
				gakkenTransactionID.setExt3(gakkenTID.getExt_3());
		        // サービスサイトコード
		        gakkenTransactionID.setSiteCd(gakkenTID.getSite_cd());
		        // ショップコード
		        gakkenTransactionID.setShopCd(gakkenTID.getShop_cd());
		        // イベントキー１
		        gakkenTransactionID.setEventKey1(gakkenTID.getEvent_key_1());
		        // イベントキー２
		        gakkenTransactionID.setEventKey2(gakkenTID.getEvent_key_2());
		        // イベントキー３
		        gakkenTransactionID.setEventKey3(gakkenTID.getEvent_key_3());
		        // パートナーコード
		        gakkenTransactionID.setPartnerCd(gakkenTID.getPartner_cd());
		        // 状態
		        gakkenTransactionID.setStatus("1");
		        
		        Date now = DateUtils.getNowDate();
		        // 登録日
		        gakkenTransactionID.setRegistT(now);
		        // 更新日
		        gakkenTransactionID.setUpdateTs(now);
		        // 登録開始
		        gakkenTransactionID.setRegistTsFrom(now);
		        // 登録終了
		        gakkenTransactionID.setRegistTsEnd(now);
		        
		        gakkenApiDao.insertGakkenTransaction(gakkenTransactionID);
		        
		        // 返却
		        GakkenTransactionID condition = new GakkenTransactionID();
		        condition.setGtidpk(gakkenTransactionID.getGtidpk());

		        GakkenTransactionID resultGtid = gakkenApiDao.selectGakkenTID(condition).get(0);

				V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID returnGtid = new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID();
		        // 学研TIDプライマリキー
		        returnGtid.setGtidpk(resultGtid.getGtidpk());
		        // 学研IDプライマリキー
		        returnGtid.setGidpk(resultGtid.getGidpk());
		        // 学研PIDプライマリキー
		        returnGtid.setGpidpk(resultGtid.getGpidpk());
		        // メールアドレス1
		        returnGtid.setMailaddress_1(resultGtid.getMailaddress1());
		        // メールアドレス2
		        returnGtid.setMailaddress_2(resultGtid.getMailaddress2());
		        // メールアドレス3
		        returnGtid.setMailaddress_3(resultGtid.getMailaddress3());
		        // 携帯メールアドレス
		        returnGtid.setMobile(resultGtid.getMobile());
		        // 姓
		        returnGtid.setLastname(resultGtid.getLastname());
		        // 名
		        returnGtid.setFirstname(resultGtid.getFirstname());
		        // セイ
		        returnGtid.setKana_lastname(resultGtid.getKanaLastname());
		        // メイ
		        returnGtid.setKana_firstname(resultGtid.getKanaFirstname());
		        // 生年月日
		        returnGtid.setBirth(resultGtid.getBirth());
		        // ニックネーム
		        returnGtid.setNickname(resultGtid.getNickname());
		        // 性別
		        if (StringUtils.isNotEmpty(resultGtid.getSex())) {
		        	returnGtid.setSex(Short.parseShort(resultGtid.getSex()));
		        }
		        // 国
		        returnGtid.setCountry(resultGtid.getCountry());
		        // 郵便番号
		        returnGtid.setZip(resultGtid.getZip());
		        // 電話番号
		        returnGtid.setTel(resultGtid.getTel());
				// 電話番号2
				returnGtid.setTel_2(resultGtid.getTel2());
		        // 都道府県コード
		        returnGtid.setPref_cd(resultGtid.getPrefCd());
		        // 住所1（都道府県）
		        returnGtid.setAddress_1(resultGtid.getAddress1());
		        // 住所2（市区町村）
		        returnGtid.setAddress_2(resultGtid.getAddress2());
		        // 住所3（番地）
		        returnGtid.setAddress_3(resultGtid.getAddress3());
		        // 住所4（号室）
		        returnGtid.setAddress_4(resultGtid.getAddress4());
		        // 住所5
		        returnGtid.setAddress_5(resultGtid.getAddress5());
		        // 住所6
		        returnGtid.setAddress_6(resultGtid.getAddress6());
				// 予備項目_1
				returnGtid.setExt_1(resultGtid.getExt1());
				// 予備項目_2
				returnGtid.setExt_2(resultGtid.getExt2());
				// 予備項目_3
				returnGtid.setExt_3(resultGtid.getExt3());
		        // サービスサイトコード
		        returnGtid.setSite_cd(resultGtid.getSiteCd());
		        // ショップコード
		        returnGtid.setShop_cd(resultGtid.getShopCd());
		        // イベントキー１
		        returnGtid.setEvent_key_1(resultGtid.getEventKey1());
		        // イベントキー２
		        returnGtid.setEvent_key_2(resultGtid.getEventKey2());
		        // イベントキー３
		        returnGtid.setEvent_key_3(resultGtid.getEventKey3());
		        // パートナーコード
		        returnGtid.setPartner_cd(resultGtid.getPartnerCd());
		        // 状態
		        if (StringUtils.isNotEmpty(resultGtid.getStatus())) {
		        	returnGtid.setStatus(Short.parseShort(resultGtid.getStatus()));
		        }
		        // 登録日
		        returnGtid.setRegist_ts(resultGtid.getRegistT());
		        // 更新日
		        returnGtid.setUpdate_ts(resultGtid.getUpdateTs());
		        // 登録開始
		        returnGtid.setRegist_ts_from(resultGtid.getRegistTsFrom());
		        // 登録終了
		        returnGtid.setRegist_ts_end(resultGtid.getRegistTsEnd());

		        returnGakkenTIDs.add(returnGtid);
			}
			
			result.setSuccess(true);
			result.setResult(true);
			result.setGakkenTransactionID(returnGakkenTIDs.toArray(new V2GakkenTransactionIDPrivilegeSvcStub.GakkenTransactionID[returnGakkenTIDs.size()]));

			return result;
		}
	}

//-----------GakkenTID-End-----------

	/**
	 * 学研API接続URLを取得する
	 * 
	 * @return　学研API接続URL
	 */
	public String getRequestUrl() {
		return requestUrl;
	}

	/**
	 * 学研API接続URLを設定する
	 * 
	 * @param requestUrl　学研API接続URL
	 */
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	/**
	 * SOAP認証用UsernameTokenを取得する
	 * 
	 * @return SOAP認証用UsernameToken
	 */
	public String getUser() {
		return user;
	}

	/**
	 * SOAP認証用UsernameTokenを設定する
	 * 
	 * @param user SOAP認証用UsernameToken
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * SOAP認証用Passwordを取得する
	 * 
	 * @return SOAP認証用Password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * SOAP認証用Passwordを設定する
	 * 
	 * @param password SOAP認証用Password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 特権アクセストークンを取得する
	 * 
	 * @return 特権アクセストークン
	 */
	public String getPriToken() {
		return priToken;
	}

	/**
	 * 特権アクセストークンを設定する
	 * 
	 * @param priToken 特権アクセストークン
	 */
	public void setPriToken(String priToken) {
		this.priToken = priToken;
	}

	/**
	 * サービスサイトコードを取得する
	 * 
	 * @return サービスサイトコード
	 */
	public String getServiceCd() {
		return serviceCd;
	}

	/**
	 * サービスサイトコードを設定する
	 * 
	 * @param serviceCd サービスサイトコード
	 */
	public void setServiceCd(String serviceCd) {
		this.serviceCd = serviceCd;
	}

	/**
	 * サービスショップコードを取得する
	 * 
	 * @return サービスショップコード
	 */
	public String getShopCd() {
		return shopCd;
	}

	/**
	 * サービスショップコードを設定する
	 * 
	 * @param shopCd サービスショップコード
	 */
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	/**
	 * GakkenTID履歴キーを取得する
	 * 
	 * @return GakkenTID履歴キー
	 */
	public String getGakkenTidHistKey() {
		return gakkenTidHistKey;
	}

	/**
	 * GakkenTID履歴キーを設定する
	 * 
	 * @param gakkenTidHistKey GakkenTID履歴キー
	 */
	public void setGakkenTidHistKey(String gakkenTidHistKey) {
		this.gakkenTidHistKey = gakkenTidHistKey;
	}

	/**
	 * メールアドレスキーを取得する
	 * 
	 * @return メールアドレスキー
	 */
	public String getGakkenMailKey() {
		return gakkenMailKey;
	}

	/**
	 * メールアドレスキーを設定する
	 * 
	 * @param gakkenMailKey メールアドレスキー
	 */
	public void setGakkenMailKey(String gakkenMailKey) {
		this.gakkenMailKey = gakkenMailKey;
	}

	/**
	 * 指導者コードキーを取得する
	 * 
	 * @return 指導者コードキー
	 */
	public String getTeacherCodeKey() {
		return teacherCodeKey;
	}

	/**
	 * 指導者コードキーを設定する
	 * 
	 * @param teacherCodeKey 指導者コードキー
	 */
	public void setTeacherCodeKey(String teacherCodeKey) {
		this.teacherCodeKey = teacherCodeKey;
	}

	/**
	 * 本仮APIフラグを取得する
	 * 
	 * @return 本仮APIフラグ
	 */
	public boolean isRealApi() {
		return realApi;
	}

	/**
	 * 本仮APIフラグを設定する
	 * 
	 * @param realApi 本仮APIフラグ
	 */
	public void setRealApi(boolean realApi) {
		this.realApi = realApi;
	}

	/**
	 * サービスサイトコード2を取得する
	 *
	 * @return サービスサイトコード2
	 */
	public String getServiceCd2() {
		return serviceCd2;
	}

	/**
	 * サービスサイトコード2を設定する
	 *
	 * @param serviceCd2 サービスサイトコード2
	 */
	public void setServiceCd2(String serviceCd2) {
		this.serviceCd2 = serviceCd2;
	}

}
