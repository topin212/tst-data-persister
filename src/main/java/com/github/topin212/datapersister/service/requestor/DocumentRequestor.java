package com.github.topin212.datapersister.service.requestor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.datapersister.entities.Document;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class DocumentRequestor implements Requestor<Document> {

    private OkHttpClient httpClient;
    private ObjectMapper mapper;

    @Autowired
    public DocumentRequestor(OkHttpClient httpClient,ObjectMapper mapper) {
        this.httpClient = httpClient;
        this.mapper = mapper;
    }

    @Override
    public List<Document> get(String url) throws IOException {
        Request req = new Request.Builder()
                .url(url)
                .build();

        Response response = httpClient.newCall(req).execute();

        assert response.body() != null;
        String responseBody = response.body().string();

        JsonNode node = mapper.readTree(responseBody);

        String purified = node.get("data").toString();
        Document[] documents = mapper.readValue(purified, Document[].class);

        return Arrays.asList(documents);
    }
}
