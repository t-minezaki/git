package jp.learningpark.modules.xapi;

import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.ActivityDefinition;

import java.util.HashMap;

public final class Activitys {
    private static Activity createActivity(String description){
        Activity activity = new Activity();
        activity.setId(XApiUtils.getBasicURI() + "/activity/" + description);
        ActivityDefinition ad = new ActivityDefinition();
        HashMap<String, String> descriptions = new HashMap<String, String>();
        descriptions.put("en-US", description);
        ad.setDescription(descriptions);
        activity.setDefinition(ad);
        return activity;
    }

    public static Activity system(){
        return createActivity("system");
    }
    
    public static Activity link(){
        return createActivity("link");
    }

    // 授業
    public static Activity lesson() {
        return createActivity("lesson");
    }

    // 試験
    public static Activity examination() {
        return createActivity("examination");
    }

    // 学習計画
    public static Activity schedule() {
        return createActivity("schedule");
    }

    // ドリル
    public static Activity drill() {
        return createActivity("drill");
    }

    // テスト
    public static Activity test() {
        return createActivity("test");
    }

    // 問題
    public static Activity question() {
        return createActivity("question");
    }
    
    // 学校
    public static Activity school() {
        return createActivity("school");
    }
    
    // 塾
    public static Activity academy() {
        return createActivity("academy");
    }

    // コース
    public static Activity course() {
        return createActivity("course");
    }

//    public static Activity assessment(){
//        return createActivity("assessment");
//    }  
//
//    public static Activity course(){
//        return createActivity("course");
//    }
//    
//    public static Activity file(){
//        return createActivity("file");
//    }
//    
//    public static Activity interaction(){
//        return createActivity("interaction");
//    }
//    
//    public static Activity lesson(){
//        return createActivity("lesson");
//    }
//    

//    
//    public static Activity media(){
//        return createActivity("media");
//    }
//    
//    public static Activity module(){
//        return createActivity("module");
//    }
//    
//    public static Activity question(){
//        return createActivity("question");
//    }
}
