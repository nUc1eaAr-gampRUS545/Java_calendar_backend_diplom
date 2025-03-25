package ru.minusd.security.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

@Component
public class FileManager {

    @Value("${yandex.token}")
    private String token;

    private final RestTemplate restTemplate;

    public FileManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void upload(byte[] resources,String fullFileName) {

        final String baseUrl = "https://cloud-api.yandex.net/v1/disk/resources/upload";

        RequestEntity<Void> requestEntity = RequestEntity.get(
                        UriComponentsBuilder.fromUriString(baseUrl)
                                .queryParam("path",fullFileName)
                                .build()
                                .toUri()
                ).header("Authorization","OAuth " + token)
                .build();

        ResponseEntity<Link> linkResponseEntity = restTemplate.exchange(requestEntity,Link.class);

        String link = Objects.requireNonNull(linkResponseEntity.getBody()).href();

        RequestEntity<byte[]> requestToUpload = RequestEntity.put(
                UriComponentsBuilder.fromUriString(link)
                        .build()
                        .toUri()
        ).body(resources);

        ResponseEntity<String> responseToUpload = restTemplate.exchange(
                requestToUpload,String.class
        );

        if (responseToUpload.getStatusCode().is2xxSuccessful()) {
            System.out.println("File is uploaded successfully");
        } else {
            System.out.println(responseToUpload.getBody() + " | " + responseToUpload.getStatusCode());
        }
    }

    public String download(String path) throws IOException {
        final String baseUrl = "https://cloud-api.yandex.net/v1/disk/resources/download";

        RequestEntity<Void> requestEntity = RequestEntity.get(
                        UriComponentsBuilder.fromUriString(baseUrl)
                                .queryParam("path",path)
                                .build().toUri()
                )
                .header("Authorization","OAuth " + token)
                .build();

        ResponseEntity<Link> response = restTemplate.exchange(requestEntity,Link.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody()==null) {
            throw new IOException("Error getting download link from Yandex Disk");
        }

        return response.getBody().href();

    }

    public void createDirectory(String path) {
        final String baseUrl = "https://cloud-api.yandex.net/v1/disk/resources";
        RequestEntity<Void> requestEntity = RequestEntity.put(
                        UriComponentsBuilder.fromUriString(baseUrl)
                                .queryParam("path",path)
                                .build().toUri()
                )
                .header("Authorization","OAuth " + token)
                .build();
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(requestEntity,String.class);
            HttpStatusCode statusCode = exchange.getStatusCode();
            if (statusCode.equals(HttpStatusCode.valueOf(201))) {
                System.out.println("Successfully created folder: " + path);
            }
        } catch (Exception e) {
            System.out.println("Folder already exists: " + e.getMessage());
        }
    }

    public void delete(String fileName) throws IOException {
      final String baseUrl = "https://cloud-api.yandex.net/v1/disk/resources";
        RequestEntity<Void> requestEntity = RequestEntity.get(
                        UriComponentsBuilder.fromUriString(baseUrl)
                                .queryParam("path",fileName)
                                .build().toUri()
                )
                .header("Authorization","OAuth " + token)
                .build();
        ResponseEntity<Link> response = restTemplate.exchange(requestEntity,Link.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody()==null) {
            throw new IOException("Error getting download link from Yandex Disk");
        }

    }

}

record Link(String href,String method,boolean templated) {
}
