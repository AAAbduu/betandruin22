package httpManagement;

import okhttp3.*;

import java.io.IOException;

public class Manager {

    private String request(String endpoint){
        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://api.football-data.org"+endpoint)
                .get()
                .addHeader("X-Auth-Token", System.getenv("TOKEN"))
                .build();

        try{
            Response response = client.newCall(request).execute();
            if(response.code()==200){
                result = response.body().string();
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return result;
    }


    public String makeRequest(String endpoint){
        return request(endpoint);
    }


}
