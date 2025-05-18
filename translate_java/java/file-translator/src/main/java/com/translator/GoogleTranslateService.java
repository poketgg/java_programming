package com.translator;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GoogleTranslateService {

    private final String API_KEY = "AIzaSyAp0D4Mfr9n7ft8-lRKcw0ZkdWReyRe8m0"; // 발급받은 키 입력

    public String translateText(String text, String sourceLang, String targetLang) {
        try {
            String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);
            String url = "https://translation.googleapis.com/language/translate/v2"
                       + "?q=" + encodedText
                       + "&source=" + sourceLang
                       + "&target=" + targetLang
                       + "&format=text"
                       + "&key=" + API_KEY;

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject json = new JSONObject(response.getBody());
                return json.getJSONObject("data")
                           .getJSONArray("translations")
                           .getJSONObject(0)
                           .getString("translatedText");
            } else {
                return "번역 실패: " + response.getStatusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "오류 발생: " + e.getMessage();
        }
    }
}
