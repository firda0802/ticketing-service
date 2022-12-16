package com.binar.tix.service;

import com.binar.tix.payload.Messages;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Riko
 */
@Service
public class FCMService {

    private final org.apache.logging.log4j.Logger log = LogManager.getLogger(this.getClass());
    private final ObjectMapper mapper = new ObjectMapper();
    private static final JSONObject JS = new JSONObject();
    private static final JSONObject NOTIF = new JSONObject();
    private static final JSONObject MESSAGE = new JSONObject();
    private static final JSONObject DATA = new JSONObject();

    private static final String PROJECT_ID = "binar-academy-8e376";
    private static final String BASE_URL = "https://fcm.googleapis.com";
    private static final String FCM_SEND_ENDPOINT = "/v1/projects/" + PROJECT_ID + "/messages:send";

    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = {MESSAGING_SCOPE};

    private static String getAccessTokenn() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("binar-academy-8e376-firebase-adminsdk-4libl-dca08d0826.json");
        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(fileInputStream);
        credentials = credentials.createScoped(SCOPES);
        AccessToken accessToken = credentials.refreshAccessToken();
        return accessToken.getTokenValue();
    }

    public Messages sendNotif(String message, String deviceId) throws IOException {
        MESSAGE.put("title", "Binar Academy");
        MESSAGE.put("body", message);
        DATA.put("tag", "MY_TAG");
        DATA.put("payload", "");
        NOTIF.put("token", deviceId);
        NOTIF.put("notification", MESSAGE);
        NOTIF.put("data", DATA);
        JS.put("message", NOTIF);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(BASE_URL + FCM_SEND_ENDPOINT);
        StringEntity body = new StringEntity(JS.toString(), "UTF-8");
        postRequest.setHeader(HTTP.CONTENT_TYPE, "application/json");
        postRequest.setHeader("Authorization", "Bearer " + getAccessTokenn());
        postRequest.setEntity(body);
        try {
            Messages resp = new Messages();
            HttpResponse response = httpClient.execute(postRequest);
            int responseCode = response.getStatusLine().getStatusCode();
            resp.setResponseCode(responseCode);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            String output;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            JsonNode respFcm = mapper.readTree(sb.toString());
            resp.setData(respFcm);
            return resp;
        } catch (IOException ex) {
            log.error(ex.getMessage());
            return null;
        }

    }
}