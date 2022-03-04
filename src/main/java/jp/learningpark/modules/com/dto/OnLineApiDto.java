package jp.learningpark.modules.com.dto;

/**
 * <p>
 * マナミルSAMLMAPPING Dto
 * </p>
 *
 * @author NWT)lyx
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2021/03/16 ： NWT)lyx ： 新規作成
 * @date 2021/03/16 10:27
 */
public class OnLineApiDto {

    public DataBean attributes;

    public MailingAddress mailingAddress;

    public static class DataBean {

            public String type;
            public String action;

    }

    public static class MailingAddress {

        public String postalCode;
        public String contry;
        public String state;
        public String city;
        public String street;

    }

    public String FirstName;
    public String LastName;
    public String GakkoMei__c;
    public String GakunenKubun__r;
    public String GakuseiKubun__r;
    public String HogoshaMail__c;
    public String HogoshaMeiKana__c;
    public String HogoshaMei__c;
    public String HogoshaSeiKana__c;
    public String HogoshaSei__c;
    public String HogoshaTel__c;
    public String HogoshaZokugara__c;
    public String KinkyuRenrakusaki__c;
    public String MeiKana__c;
    public String SeiKana__c;
    public String Seibetsu__c;
    public String Seinengappi__c;
    public String SeitoBango2__c;
    public String TaijukuDate__c;
    public String KyoshitsuCourceMaster__c;
    public String KyoshitsuCourceMaster__r;
    public String KazokuCode__c;
    public String Phone;
    public String id;
}
