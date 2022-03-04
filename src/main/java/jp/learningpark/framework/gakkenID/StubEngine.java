package jp.learningpark.framework.gakkenID;

import jp.gakken.id2.V2GakkenIDPrivilegeSvcStub;
import jp.gakken.id2.V2GakkenIDSvcStub;
import jp.gakken.id2.V2GakkenTransactionIDPrivilegeSvcStub;
import jp.gakken.id2.V2GakkenTransactionIDSvcStub;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.axiom.om.OMXMLParserWrapper;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.client.Stub;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.apache.rampart.RampartMessageData;
import org.springframework.core.io.ClassPathResource;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

/**
 * 学研APIスタブ生成用クラス
 * 
 * @author wangqiang
 * @history 2020/06/03 V2
 */
public class StubEngine {

	// 学研IDスタブ（特権）
	private static V2GakkenIDPrivilegeSvcStub v2GakkenIDPrivilegeSvcStub;
	// 学研IDスタブ（一般）
	private static V2GakkenIDSvcStub v2GakkenIDSvcStub;
	// 学研TIDスタブ（特権）
	private static V2GakkenTransactionIDPrivilegeSvcStub v2GakkenTransactionIDPrivilegeSvcStub;
	// 学研IDスタブ（一般）
	private static V2GakkenTransactionIDSvcStub v2GakkenTransactionIDSvcStub;
	
	
	/**
	 * 学研IDスタブ（特権）を取得する
	 * 
	 * @param requestUrl 学研API接続URL
	 * @param user SOAP認証用UsernameToken
	 * @param password SOAP認証用Password
	 * @throws Exception 異常
	 */
	public static synchronized V2GakkenIDPrivilegeSvcStub getGakkenIDPrivilegeSvcStub(String requestUrl, String user, String password) throws Exception {

		if (v2GakkenIDPrivilegeSvcStub == null) {
			v2GakkenIDPrivilegeSvcStub = new V2GakkenIDPrivilegeSvcStub(requestUrl + "V2GakkenIDPrivilegeSvc");
			setClient(v2GakkenIDPrivilegeSvcStub, user, password);
		}

		return v2GakkenIDPrivilegeSvcStub;
	}

	/**
	 * 学研IDスタブ（一般）を取得する
	 * 
	 * @param requestUrl 学研API接続URL
	 * @param user SOAP認証用UsernameToken
	 * @param password SOAP認証用Password
	 * @throws Exception 異常
	 */
	public static synchronized V2GakkenIDSvcStub getGakkenIDSvcStub(String requestUrl, String user, String password) throws Exception {

		if (v2GakkenIDSvcStub == null) {
			v2GakkenIDSvcStub = new V2GakkenIDSvcStub(requestUrl + "V2GakkenIDSvc");
			setClient(v2GakkenIDSvcStub, user, password);
		}

		return v2GakkenIDSvcStub;
	}

	/**
	 * 学研TIDスタブ（特権）を取得する
	 * 
	 * @param requestUrl 学研API接続URL
	 * @param user SOAP認証用UsernameToken
	 * @param password SOAP認証用Password
	 * @throws Exception 異常
	 */
	public static synchronized V2GakkenTransactionIDPrivilegeSvcStub getGakkenTransactionIDPrivilegeSvcStub(String requestUrl, String user, String password) throws Exception {

		if (v2GakkenTransactionIDPrivilegeSvcStub == null) {
			v2GakkenTransactionIDPrivilegeSvcStub = new V2GakkenTransactionIDPrivilegeSvcStub(
					requestUrl + "V2GakkenTransactionIDPrivilegeSvc");
			setClient(v2GakkenTransactionIDPrivilegeSvcStub, user, password);
		}

		return v2GakkenTransactionIDPrivilegeSvcStub;
	}

	/**
	 * 学研TIDスタブ（一般）を取得する
	 * 
	 * @param requestUrl 学研API接続URL
	 * @param user SOAP認証用UsernameToken
	 * @param password SOAP認証用Password
	 * @throws Exception 異常
	 */
	public static synchronized V2GakkenTransactionIDSvcStub getGakkenTransactionIDSvcStub(String requestUrl, String user, String password) throws Exception {

		if (v2GakkenTransactionIDSvcStub == null) {
			v2GakkenTransactionIDSvcStub = new V2GakkenTransactionIDSvcStub(requestUrl + "V2GakkenTransactionIDSvc");
			setClient(v2GakkenTransactionIDSvcStub, user, password);
		}

		return v2GakkenTransactionIDSvcStub;
	}
	
	/**
	 * APIクライアント設定
	 * 
	 * @param stub スタブ
	 * @param user SOAP認証用UsernameToken
	 * @param password SOAP認証用Password
	 * @throws Exception 異常
	 */
	private static void setClient(Stub stub, String user, String password) throws Exception {

		ServiceClient serviceClient = stub._getServiceClient();

		Options options = serviceClient.getOptions();
		options.setUserName(user);
		options.setPassword(password);
		options.setProperty(RampartMessageData.KEY_RAMPART_POLICY, loadPolicy("policy.xml"));// 実際にファイルを配置したパスを記述
		serviceClient.setOptions(options);
		serviceClient.engageModule("rampart");
		serviceClient.engageModule("addressing");

		stub._setServiceClient(serviceClient);
	}

	/**
	 * Policyファイルの読込
	 * 
	 * @param name ファイル名
	 * @return Policy
	 * @throws Exception 異常
	 */
	private static Policy loadPolicy(String name) throws Exception {

		XMLInputFactory factory = XMLInputFactory.newFactory();
		ClassPathResource classPathResource = new ClassPathResource(name);
		XMLStreamReader reader = factory.createXMLStreamReader(classPathResource.getInputStream());
		
		OMXMLParserWrapper builder = OMXMLBuilderFactory.createStAXOMBuilder(reader);
		return PolicyEngine.getPolicy(builder.getDocumentElement());
	}
}
