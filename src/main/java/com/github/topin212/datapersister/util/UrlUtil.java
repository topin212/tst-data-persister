package com.github.topin212.datapersister.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtil {
    public static URL formUrl(String hash) throws MalformedURLException {
        return new URL(
                "https://lb-api-sandbox.prozorro.gov.ua/api/2.4/contracts/"
                        + hash
                        + "/documents"
        );
    }
}
