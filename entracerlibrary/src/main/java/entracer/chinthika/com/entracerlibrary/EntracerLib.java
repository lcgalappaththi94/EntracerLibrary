package entracer.chinthika.com.entracerlibrary;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EntracerLib {
    private String token;
    OkHttpClient client;
    ConcurrentHashMap concurrentHashMap;


    public EntracerLib(String token) {
        this.token = token;
        client = new OkHttpClient();
        concurrentHashMap = new ConcurrentHashMap<String, String>();

    }

    public String getPerson(String id) {
        return "The response";
    }

    private String getTag() {
        return UUID.randomUUID().toString();
    }

    public String getAllPersons() {
        String tag = getTag();
        new RequestTask().execute(this.token, tag, "http://crm.orete.org/api/v1/people");
        while (!concurrentHashMap.containsKey(tag)) ;
        String result= (String) concurrentHashMap.get(tag);
        return result;
    }


    private class RequestTask extends AsyncTask<String, Void, String> {

        String tag;

        @Override
        protected String doInBackground(String... params) {
            String token = params[0];
            tag = params[1];
            String url = params[2];
            try {
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", new StringBuffer("Token token=").append(token).toString())
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            concurrentHashMap.put(tag, result);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
