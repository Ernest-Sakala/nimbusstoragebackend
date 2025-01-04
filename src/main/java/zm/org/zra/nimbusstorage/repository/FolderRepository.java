package zm.org.zra.nimbusstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zm.org.zra.nimbusstorage.entity.FolderEntity;

import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, Long> {
    Optional<FolderEntity> findByName(String name);
}
