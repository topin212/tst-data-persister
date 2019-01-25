package com.github.topin212.datapersister.service.persister;

import com.github.topin212.datapersister.entity.Document;
import com.github.topin212.datapersister.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentPersister implements Persister {

    private DocumentRepository documentRepository;

    @Autowired
    public DocumentPersister(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void saveAll(List<Document> documentList) {
        documentRepository.saveAll(documentList);
    }
}
