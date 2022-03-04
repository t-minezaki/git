package jp.learningpark.modules.xapi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import gov.adlnet.xapi.client.StatementClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XApiClient extends StatementClient {

    public XApiClient(String uri, String user, String password) throws java.net.MalformedURLException {
        super(uri, user, password);
    }

    public XApiClient(URL uri, String user, String password) throws MalformedURLException {
        super(uri, user, password);
    }

    public XApiClient(String uri, String encodedUsernamePassword) throws MalformedURLException {
        super(uri, encodedUsernamePassword);
    }

    public XApiClient(URL uri, String encodedUsernamePassword) throws MalformedURLException {
        super(uri, encodedUsernamePassword);
    }
    
    public String postStatement(String json) throws java.io.IOException {
        Gson gson = this.getDecoder();
        String result = this.issuePost("/statements", json);
        JsonArray jsonResult = gson.fromJson(result, JsonArray.class);
        return jsonResult.get(0).getAsString();
    }
    
    
    public ArrayList<String> postStatements(List<String> jsons) throws java.io.IOException {
        Gson gson = this.getDecoder();
        String json = gson.toJson(jsons);
        String result = this.issuePost("/statements", json);
        JsonArray jsonResult = gson.fromJson(result, JsonArray.class);
        ArrayList<String> IDs = new ArrayList<>();
        for (int i = 0; i < jsonResult.size(); i++) {
            IDs.add(jsonResult.get(i).getAsString());
        }
        return IDs;
    }

}
