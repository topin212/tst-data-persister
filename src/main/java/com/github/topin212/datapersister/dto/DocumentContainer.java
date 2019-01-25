package com.github.topin212.datapersister.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.topin212.datapersister.entity.Document;

import javax.persistence.Transient;

public class DocumentContainer {

    private Document[] data;

    public Document[] getData() {
        return data;
    }

    @Transient
    @JsonIgnore
    public boolean isValid(){
        boolean result = true;

        for (int i = 0; i < data.length && result == true; i++) {
            result &= data[i].isValid();
        }

        return result;
    }


    public void setData(Document[] data) {
        this.data = data;
    }
}
