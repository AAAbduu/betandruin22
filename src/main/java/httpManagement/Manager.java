package httpManagement;

import okhttp3.*;

import java.io.IOException;

public class Manager {
    /**
     * This method is in charge of doing an http request with the given endpoint.
     * @param endpoint Part of the http request, concretely the end part of it.
     * @return String containing the response of the request made.
     */
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

    /**
     * This method is in charge of doing an http request with the given endpoint.
     * @param endpoint Part of the http request, concretely the end part of it.
     * @return String containing the response of the request made.
     */
    public String makeRequest(String endpoint){
        return request(endpoint);
    }


}
