package com.tmax.commerce.itemmodule.entity.file;

import com.tmax.commerce.itemmodule.entity.base.DateTimeEntity;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class File extends DateTimeEntity implements Comparable<File> {
    private String fileName;
    private String fileUrl;
    private int sequence;

    private File(String fileName, String fileUrl, int sequence) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.sequence = sequence;
    }

    public static File create(String fileName, String fileUrl, int sequence) {
        return new File(fileName, fileUrl, sequence);
    }

    public int compareTo(File other) {
        return Integer.compare(this.sequence, other.getSequence());
    }

}