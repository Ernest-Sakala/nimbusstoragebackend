package zm.org.zra.nimbusstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zm.org.zra.nimbusstorage.entity.FileMetadataEntity;
import zm.org.zra.nimbusstorage.entity.FolderEntity;

import java.util.List;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadataEntity, Long> {
    List<FileMetadataEntity> findAllByFolder(FolderEntity folderEntity);
}
