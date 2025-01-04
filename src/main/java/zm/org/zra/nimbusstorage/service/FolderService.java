package zm.org.zra.nimbusstorage.service;

import org.springframework.stereotype.Service;
import zm.org.zra.nimbusstorage.entity.FolderEntity;
import zm.org.zra.nimbusstorage.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FolderService {

    @Value("${storage.location}")
    private String baseStorageLocation;

    private final FolderRepository folderRepository;

    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public FolderEntity createFolder(String name, Long parentId) throws IOException {
        FolderEntity parentFolder = null;

        if (parentId != null) {
            parentFolder = folderRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Parent folder not found"));
        }

        if (folderRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Folder already exists: " + name);
        }


        Path folderPath = parentFolder == null ? Paths.get(baseStorageLocation, name).toAbsolutePath().normalize() : Paths.get(parentFolder.getPath(), name).toAbsolutePath().normalize();

        Files.createDirectories(folderPath);

        FolderEntity folder = new FolderEntity();
        folder.setName(name);
        folder.setPath(folderPath.toString());
        folder.setCreatedAt(LocalDateTime.now());
        folder.setParent(parentFolder);

        return folderRepository.save(folder);
    }

    public List<FolderEntity> getAllFolders() {
        return folderRepository.findAll();
    }

    public FolderEntity getFolderById(Long id) {
        return folderRepository.findById(id).orElseThrow(() -> new RuntimeException("Folder not found"));
    }
}
