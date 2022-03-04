package jp.learningpark.modules.xapi;

import gov.adlnet.xapi.model.Verb;

import java.util.HashMap;

public final class Verbs {
    private static Verb createVerb(String description){
        Verb v = new Verb();
        HashMap<String, String> descriptions = new HashMap<String, String>();
        descriptions.put("en-US", description);
        v.setId(XApiUtils.getBasicURI() + "/verb/" + description);
        v.setDisplay(descriptions);
        return v;
    }
    //  解答した
    public static Verb answered(){
        return createVerb("answered");
    }
    // 質問した
    public static Verb asked(){
        return createVerb("asked");
    }
    public static Verb attempted(){
        return createVerb("attempted");
    }
    //  出席した
    public static Verb attended(){
        return createVerb("attended");
    }
    public static Verb commented(){
        return createVerb("commented");
    }
    public static Verb completed(){
        return createVerb("completed");
    }
    public static Verb exited(){
        return createVerb("exited");
    }
    public static Verb experienced(){
        return createVerb("experienced");
    }
    public static Verb failed(){
        return createVerb("failed");
    }
    public static Verb imported(){
        return createVerb("imported");
    }
    public static Verb initialized(){
        return createVerb("initialized");
    }
    public static Verb interacted(){
        return createVerb("interacted");
    }
    public static Verb launched(){
        return createVerb("launched");
    }
    public static Verb mastered(){
        return createVerb("mastered");
    }
    public static Verb passed(){
        return createVerb("passed");
    }
    // 志望した
    public static Verb preferred(){
        return createVerb("preferred");
    }
    public static Verb progressed(){
        return createVerb("progressed");
    }
    // 申し込みした
    public static Verb registered(){
        return createVerb("registered");
    }
    public static Verb responded(){
        return createVerb("responded");
    }
    public static Verb resumed(){
        return createVerb("resumed");
    }
    //  得点した
    public static Verb scored(){
        return createVerb("scored");
    }
    public static Verb shared(){
        return createVerb("shared");
    }
    public static Verb suspended(){
        return createVerb("suspended");
    }
    public static Verb voided(){
        return createVerb("voided");
    }
    public static Verb loggedIn(){
        return createVerb("logged-in");
    }
    public static Verb loggedOut(){
        return createVerb("logged-out");
    }

    //    追加した,面談を予約した
    public static Verb scheduled() {
        return createVerb("scheduled");
    }

    // 削除した
    public static Verb canceled() {
        return createVerb("canceled");
    }

    // 始めた started
    public static Verb started() {
        return createVerb("started");
    }

    // 終わった
    public static Verb terminated() {
        return createVerb("terminated");
    }

    // 面談した
    public static Verb interviewed() {
        return createVerb("interviewed");
    }
    //  話した
    public static Verb talked() {
        return createVerb("talked");
    }
    //  やめた
    public static Verb left() {
        return createVerb("left");
    }
    //  配信した
    public static Verb delivered() {
        return createVerb("delivered");
    }
    
    //  欠席した    not attended TODO
    //  志望をやめた  quit preferred TODO
}
