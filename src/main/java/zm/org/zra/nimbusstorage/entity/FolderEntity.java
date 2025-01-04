package zm.org.zra.nimbusstorage.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FOLDER_METADATA")
public class FolderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String path;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private FolderEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FolderEntity> children = new ArrayList<>();

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileMetadataEntity> files = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public FolderEntity getParent() {
        return parent;
    }

    public void setParent(FolderEntity parent) {
        this.parent = parent;
    }

    public List<FolderEntity> getChildren() {
        return children;
    }

    public void setChildren(List<FolderEntity> children) {
        this.children = children;
    }

    public List<FileMetadataEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileMetadataEntity> files) {
        this.files = files;
    }
}
