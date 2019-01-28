package com.github.topin212.datapersister.service.requestor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.datapersister.dto.DocumentContainer;
import com.github.topin212.datapersister.entity.Document;
import com.github.topin212.datapersister.entity.validation.DocumentContainerValidator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Service
public class DocumentRequestor implements Requestor<Document> {

    private OkHttpClient httpClient;
    private ObjectMapper mapper;
    private DocumentContainerValidator validator;

    @Value("${baseUrl}")
    private String baseUrl;

    @Autowired
    public DocumentRequestor(OkHttpClient httpClient, ObjectMapper mapper, DocumentContainerValidator validator) {
        this.httpClient = httpClient;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public List<Document> get(String id) throws IOException {
        URL url = formUrl(id);

        Request req = new Request.Builder()
                .url(url)
                .build();

        Response response = httpClient.newCall(req).execute();
        InputStream is = response.body().byteStream();

        DocumentContainer doc = mapper.readValue(is, DocumentContainer.class);

        BindException bindException = new BindException(doc, "Document");
        validator.validate(doc, bindException);

        return Arrays.asList(doc.getData());
    }

    private URL formUrl(String hash) throws MalformedURLException {
        return new URL(
                baseUrl
                        + "/contracts"
                        + "/" + hash
                        + "/documents"
        );
    }
}
