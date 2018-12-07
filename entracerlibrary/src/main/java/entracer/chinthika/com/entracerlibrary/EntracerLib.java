package entracer.chinthika.com.entracerlibrary;

import android.content.Context;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EntracerLib {
    private String token;
    OkHttpClient client;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public EntracerLib(String token) {
        this.token = token;
        client = new OkHttpClient();
    }

    public String getPerson(String id) {
        return "The response";
    }

    public String getAllPersons() {
        //RequestBody body = RequestBody.create(JSON, json);
        try {
            Request request = new Request.Builder()
                    .url("http://crm.orete.org/api/v1/people")
                    .addHeader("Authorization", new StringBuffer("Token token=").append(this.token).toString())
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
