package com.github.topin212.datapersister.service.requestor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.datapersister.entity.Document;
import com.github.topin212.datapersister.entity.validation.DocumentContainerValidator;
import okhttp3.OkHttpClient;
import okhttp3.mock.Behavior;
import okhttp3.mock.MockInterceptor;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static okhttp3.mock.MediaTypes.MEDIATYPE_JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

public class DocumentRequestorTest {

    private Requestor docRequestor;
    private String baseUrl = "https://lb-api-sandbox.prozorro.gov.ua/api/2.4";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() throws IOException {

        MockInterceptor mockInterceptor = setupMockInterceptor();

        OkHttpClient testHttpClient = new OkHttpClient.Builder()
                .addInterceptor(mockInterceptor)
                .build();

        docRequestor = new DocumentRequestor(
                testHttpClient,
                new ObjectMapper(),
                new DocumentContainerValidator());

        ReflectionTestUtils.setField(docRequestor, "baseUrl", baseUrl);
    }

    @Test
    public void objectObtainingShouldSucceed_WhenSuppliedWithValidJSON() throws IOException {
        List<Document> validHash = docRequestor.get("validHash");

        Document doc = validHash.get(0);

        assertThat(doc.getId(), not(isEmptyOrNullString()));
        assertThat(doc.getHash(), not(isEmptyOrNullString()));
        assertThat(doc.getDescription(), isEmptyOrNullString());
    }

    @Test
    public void objectObtainingShouldThrowIllegalArgumentException_WhenSuppliedWithInValidJSON() throws IOException {
        expectedException.expect(IllegalArgumentException.class);
        docRequestor.get("invalidHash");
    }

    private MockInterceptor setupMockInterceptor() throws FileNotFoundException {

        MockInterceptor mockInterceptor = new MockInterceptor();
        mockInterceptor.behavior(Behavior.UNORDERED);

        FileInputStream valid = new FileInputStream(new File("src/test/resources/test1.json"));
        FileInputStream invalid = new FileInputStream(new File("src/test/resources/test1_invalid.json"));

        mockInterceptor.addRule()
                .get()
                .url(baseUrl + "/contracts/validHash/documents")
                .respond(valid, MEDIATYPE_JSON);

        mockInterceptor.addRule()
                .get(baseUrl + "/contracts/invalidHash/documents")
                .respond(invalid, MEDIATYPE_JSON);

        return mockInterceptor;
    }
}