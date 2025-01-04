package zm.org.zra.nimbusstorage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "FILE_METADATA")
public class FileMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;

    private String formatedSize;
    private LocalDateTime uploadedAt;

    private String checksum;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "folder_id")
    private FolderEntity folder;

    public FileMetadataEntity() {
    }

    public FileMetadataEntity(Long id, String fileName, String filePath, String fileType, Long fileSize, String formatedSize, LocalDateTime uploadedAt, String checksum, FolderEntity folder) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.formatedSize = formatedSize;
        this.uploadedAt = uploadedAt;
        this.checksum = checksum;
        this.folder = folder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public FolderEntity getFolder() {
        return folder;
    }

    public void setFolder(FolderEntity folder) {
        this.folder = folder;
    }

    public String getFormatedSize() {
        return formatedSize;
    }

    public void setFormatedSize(String formatedSize) {
        this.formatedSize = formatedSize;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    @Override
    public String toString() {
        return "FileMetadataEntity{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileSize=" + fileSize +
                ", formatedSize='" + formatedSize + '\'' +
                ", uploadedAt=" + uploadedAt +
                ", checksum='" + checksum + '\'' +
                ", folder=" + folder +
                '}';
    }
}
