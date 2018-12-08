package entracer.chinthika.com.entracerlibrary;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EntracerLib {
    private String token;
    OkHttpClient client;
    Map<String, String> concurrentHashMap;
    ExecutorService executor;


    public EntracerLib(String token) {
        this.token = token;
        client = new OkHttpClient();
        concurrentHashMap = new ConcurrentHashMap<String, String>();
        executor = Executors.newFixedThreadPool(10);
    }

    private String getTag() {
        return UUID.randomUUID().toString();
    }

    public Person getPersonById(String id) {
        final String tag = getTag();
        final String url = new StringBuffer("http://crm.orete.org/api/v1/people/").append(id).toString();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", new StringBuffer("Token token=").append(token).toString())
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    concurrentHashMap.put(tag, response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                    concurrentHashMap.put(tag, e.toString());
                }
            }
        });

        while (!concurrentHashMap.containsKey(tag)) ;
        String result = concurrentHashMap.get(tag);
        Gson gson = new Gson();
        return gson.fromJson(result,Person.class);
    }

    public String getAllPersons() {
        final String tag = getTag();
        final String url = "http://crm.orete.org/api/v1/people";
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", new StringBuffer("Token token=").append(token).toString())
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    concurrentHashMap.put(tag, response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                    concurrentHashMap.put(tag, e.toString());
                }
            }
        });

        while (!concurrentHashMap.containsKey(tag)) ;
        String result = concurrentHashMap.get(tag);
        Gson gson = new Gson();
        gson.fromJson(result,Person.class);
        return result;
    }

}
