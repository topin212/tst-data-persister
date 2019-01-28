package com.github.topin212.datapersister.dto;

import com.github.topin212.datapersister.entity.Document;

public class DocumentContainer {

    private Document[] data;

    public Document[] getData() {
        return data;
    }

    public void setData(Document[] data) {
        this.data = data;
    }
}
