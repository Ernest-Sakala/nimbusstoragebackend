package zm.org.zra.nimbusstorage.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zm.org.zra.nimbusstorage.entity.FolderEntity;
import zm.org.zra.nimbusstorage.service.FolderService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/folders")
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping("/create")
    public ResponseEntity<FolderEntity> createFolder(@RequestParam String name, @RequestParam(required = false) Long parentId) {
        try {
            FolderEntity folder = folderService.createFolder(name, parentId);
            return ResponseEntity.ok(folder);
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<FolderEntity>> getAllFolders() {
        List<FolderEntity> folders = folderService.getAllFolders();
        return ResponseEntity.ok(folders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FolderEntity> getFolderById(@PathVariable Long id) {
        try {
            FolderEntity folder = folderService.getFolderById(id);
            return ResponseEntity.ok(folder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
