package com.github.topin212.datapersister.service.persister;

import com.github.topin212.datapersister.entity.Document;

import java.util.List;

public interface Persister {

    void saveAll(List<Document> documentList);
}
