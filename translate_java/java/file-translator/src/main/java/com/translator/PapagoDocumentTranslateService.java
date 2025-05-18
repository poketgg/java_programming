package com.translator;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.json.JSONObject;

@Service
public class PapagoDocumentTranslateService {

    private final String clientId = "rnaw9p5f0t";         // 네이버 클라우드 API 키 ID
    private final String clientSecret = "TjqkJwZV0ru54vhMpxr0KsOSOkYQjwcEkRPsKZuH"; // 시크릿

    public String translateDocument(MultipartFile file, String sourceLang, String targetLang) {
        try {
            String apiURL = "https://papago.apigw.ntruss.com/doc-trans/v1";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("X-NCP-APIGW-API-KEY-ID", clientId);
            headers.set("X-NCP-APIGW-API-KEY", clientSecret);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("source", sourceLang);
            body.add("target", targetLang);

            // MultipartFile을 Resource로 감싸기
            Resource fileResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            body.add("file", fileResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(apiURL, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject json = new JSONObject(response.getBody());
                return json.getJSONObject("result").getString("translatedText");
            } else {
                return "API 요청 실패: " + response.getStatusCode();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "오류 발생: " + e.getMessage();
        }
    }
}
