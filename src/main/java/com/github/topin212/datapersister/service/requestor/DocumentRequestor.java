package com.github.topin212.datapersister.service.requestor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.datapersister.entity.Document;
import com.github.topin212.datapersister.entity.DocumentContainer;
import com.github.topin212.datapersister.util.UrlUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class DocumentRequestor implements Requestor<Document> {

    private OkHttpClient httpClient;
    private ObjectMapper mapper;

    @Autowired
    public DocumentRequestor(OkHttpClient httpClient, ObjectMapper mapper) {
        this.httpClient = httpClient;
        this.mapper = mapper;
    }

    @Override
    public List<Document> get(String hash) throws IOException {
        URL url = UrlUtil.formUrl(hash);

        Request req = new Request.Builder()
                .url(url)
                .build();

        Response response = httpClient.newCall(req).execute();

        assert response.body() != null;
        InputStream responseBody = response.body().byteStream();
        DocumentContainer doc = mapper.treeToValue(mapper.readTree(responseBody), DocumentContainer.class);

        return Arrays.asList(doc.getData());
    }
}
