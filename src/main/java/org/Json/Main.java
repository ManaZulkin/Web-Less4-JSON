package org.Json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        String request = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        String result = performRequest(request);

        Gson json  = new GsonBuilder().create();

        Corency[] corencies = json.fromJson(result, Corency[].class);

        for (Corency corency: corencies
             ) {
            System.out.println(corency.toString());
        }
    }

    public static String performRequest(String request) throws IOException {
        URL url = new URL(request);
        StringBuilder sb = new StringBuilder();

        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            char[] buf = new char[1000000];
            int i = 0;
            do{
                if ((i = br.read(buf)) > 0){
                    sb.append(new String(buf, 0, i));
                }
            }while (i > 0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
