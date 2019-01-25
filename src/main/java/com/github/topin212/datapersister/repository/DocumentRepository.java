package com.github.topin212.datapersister.repository;

import com.github.topin212.datapersister.entity.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DocumentRepository extends CrudRepository<Document, String> {
    List<Document> findAll();
}
