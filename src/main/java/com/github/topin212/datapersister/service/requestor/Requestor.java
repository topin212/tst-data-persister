package com.github.topin212.datapersister.service.requestor;

import java.io.IOException;
import java.util.List;

public interface Requestor<T> {
    List<T> get(String id) throws IOException;
}
