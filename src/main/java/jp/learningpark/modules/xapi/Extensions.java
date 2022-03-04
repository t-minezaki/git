package jp.learningpark.modules.xapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import jp.learningpark.framework.utils.StringUtils;

import java.util.HashMap;

public class Extensions extends HashMap<String, JsonElement> {

    private static final long serialVersionUID = 1L;
    
    private String BASE_EXTENSIONS_URI = XApiUtils.getBasicURI() + "/activity/extension/"; 

    public Extensions put(String key, Object value) {
        super.put(BASE_EXTENSIONS_URI + key, new JsonPrimitive(StringUtils.defaultString(value)));
        return this;
    }
    public Extensions put(String key, JsonArray array) {
        super.put(BASE_EXTENSIONS_URI + key, array);
        return this;
    }
}
