package zm.org.zra.nimbusstorage.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zm.org.zra.nimbusstorage.entity.FileMetadataEntity;
import zm.org.zra.nimbusstorage.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/files")
public class FileStorageController {

    private final FileStorageService storageService;

    public FileStorageController(FileStorageService storageService) {
        this.storageService = storageService;
    }


    @PostMapping("/upload/{folderName}")
    public FileMetadataEntity uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String folderName) throws IOException {
        return storageService.storeFile(file, folderName);
    }



    @GetMapping("/download/{folderName}/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String folderName, @PathVariable String filename) throws IOException {

        String decodedFilename = URLDecoder.decode(filename, StandardCharsets.UTF_8);
        Path path = storageService.getFilePath(folderName, decodedFilename);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found");
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }


    @GetMapping
    public ResponseEntity<List<FileMetadataEntity>> getFiles() {
        try {

            return ResponseEntity.ok(storageService.getFiles());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/{folderName}")
    public ResponseEntity<?> getFolderFiles(@PathVariable String folderName) {
        try {
            return ResponseEntity.ok(storageService.getFolderFiles(folderName));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<?> getFileDetails(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(storageService.getFileDetails(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
