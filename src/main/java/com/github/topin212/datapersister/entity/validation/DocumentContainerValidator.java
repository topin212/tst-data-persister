package com.github.topin212.datapersister.entity.validation;

import com.github.topin212.datapersister.dto.DocumentContainer;
import com.github.topin212.datapersister.entity.Document;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class DocumentContainerValidator implements Validator {

    private DocumentValidator documentValidator;

    public DocumentContainerValidator() {
        this.documentValidator = new DocumentValidator();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return DocumentContainer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DocumentContainer docCon = (DocumentContainer) o;

        if(docCon.getData().length == 0){
            throw new IllegalArgumentException("data cannot be empty");
        }

        Document[] data = docCon.getData();
        for (int i = 0; i < data.length; i++) {
            Document datum = data[i];
            errors.pushNestedPath("data[" + i + "]");
            ValidationUtils.invokeValidator(documentValidator, datum, errors);
            errors.popNestedPath();
        }
    }
}

class DocumentValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Document.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Document doc = (Document) o;

        String id = doc.getId();
        String hash = doc.getHash();


        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("hash is null");
        }

        if (hash == null || hash.isEmpty()) {
            throw new IllegalArgumentException("hash is null");
        }
    }
}
