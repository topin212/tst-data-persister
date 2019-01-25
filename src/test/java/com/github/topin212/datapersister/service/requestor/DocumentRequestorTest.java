package com.github.topin212.datapersister.service.requestor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.topin212.datapersister.entity.Document;
import okhttp3.OkHttpClient;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

public class DocumentRequestorTest {

    private Requestor docRequestor;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        docRequestor = new DocumentRequestor(new OkHttpClient(), new ObjectMapper());
    }

    @Test
    public void parsingShouldSucceed_WhenSuppliedWithValidJSON() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class cl = docRequestor.getClass();
        Method parseStreamMethod = cl.getDeclaredMethod("parseStream", InputStream.class);
        parseStreamMethod.setAccessible(true);

        File jsonFile = new File("src/test/resources/test1.json");

        FileInputStream fis = new FileInputStream(jsonFile);

        List<Document> result = (List) parseStreamMethod.invoke(docRequestor, fis);

        assertThat(
                result,
                everyItem(isValid())
        );
    }

    @Test
    public void parsingShouldThrowIllegalArgumentException_WhenSuppliedWithInValidJSON() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        expectedException.expectCause(isA(IllegalArgumentException.class));

        Class cl = docRequestor.getClass();
        Method parseStreamMethod = cl.getDeclaredMethod("parseStream", InputStream.class);
        parseStreamMethod.setAccessible(true);

        File jsonFile = new File("src/test/resources/test1_invalid.json");

        FileInputStream fis = new FileInputStream(jsonFile);

        parseStreamMethod.invoke(docRequestor, fis);
    }

    private Matcher<Document> isValid() {
        return new BaseMatcher<>() {
            @Override
            public boolean matches(Object o) {
                Document doc = (Document) o;
                return doc.isValid();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("should be true");
            }
        };
    }
}