package com.github.topin212.datapersister.service.requestor;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class DocumentRequestorTest {

    private Requestor docRequestor;

    @Before
    public void setup(){
        docRequestor = new DocumentRequestor(new OkHttpClient(), new ObjectMapper());
    }

    @Test
    public void getShouldReturnCorrectValue_WhenSuppliedWithValidURl() throws IOException {
        List all = docRequestor.get("23567e24f52746ef92c470be6059d193");

        assertThat(all.size(), equalTo(3));
    }
}