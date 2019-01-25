package com.github.topin212.datapersister.cli.command;

import com.github.topin212.datapersister.entity.Document;
import com.github.topin212.datapersister.service.persister.DocumentPersister;
import com.github.topin212.datapersister.service.requestor.DocumentRequestor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadPersistCommand implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LoadPersistCommand.class);

    private DocumentPersister documentPersister;
    private DocumentRequestor documentRequestor;

    @Autowired
    public LoadPersistCommand(DocumentRequestor requestor, DocumentPersister persister) {
        this.documentRequestor = requestor;
        this.documentPersister = persister;
    }

    @Override
    public void run(String... args) throws Exception {

        if (args.length == 0) {
            logger.error("Please, provide a hash argument.");
            return;
        }

        String hash = args[0];

        List<Document> documents = documentRequestor.get(hash);

        logger.info("Got {} objects.", documents.size());

        logger.info("Persisting...");
        documentPersister.saveAll(documents);
        logger.info("Persistance done.");
    }
}
