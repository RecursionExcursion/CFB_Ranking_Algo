package com.foofinc.cfbra.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/*
Singleton
 */

enum APICaller {
    INSTANCE;

    String getJSONFromAPICall(String urlString, String bearer) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Authorization", "Bearer " + bearer);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");
        conn.connect();

        int respCode = conn.getResponseCode();

        if (respCode != 200) {
            throw new RuntimeException("Http Response Code- " + respCode);
        } else {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            Scanner scanner = new Scanner(br);
            while (scanner.hasNext()) sb.append(scanner.nextLine());
            return sb.toString();
        }
    }
}
