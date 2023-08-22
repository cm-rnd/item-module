package com.tmax.commerce.itemmodule.entity.file;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class FileDetail {
    private String fileName;
    private String fileUrl;
    private int sequence;

    public File toEmbeddableFile() {
        return File.create(fileName, fileUrl, sequence);
    }
}
