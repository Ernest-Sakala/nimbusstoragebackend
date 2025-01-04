package zm.org.zra.nimbusstorage.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zm.org.zra.nimbusstorage.entity.FileMetadataEntity;
import zm.org.zra.nimbusstorage.entity.FolderEntity;
import zm.org.zra.nimbusstorage.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Value;
import zm.org.zra.nimbusstorage.repository.FolderRepository;
import zm.org.zra.nimbusstorage.util.ChecksumUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileStorageService {


    @Value("${storage.location}")
    private String baseStorageLocation;

    private final FileMetadataRepository repository;

    private final FolderRepository folderRepository;

    private final ChecksumUtil checksumUtil;

    public FileStorageService(FileMetadataRepository repository, FolderRepository folderRepository, ChecksumUtil checksumUtil) {
        this.repository = repository;
        this.folderRepository = folderRepository;
        this.checksumUtil = checksumUtil;
    }


    public FileMetadataEntity storeFile(MultipartFile file, String folderName) throws IOException {

        Optional<FolderEntity> optionalFolderEntity = folderRepository.findByName(folderName);

        if (optionalFolderEntity.isPresent()) {
            Path folderPath = Paths.get(baseStorageLocation, folderName).toAbsolutePath().normalize();
            Files.createDirectories(folderPath);

            String filename = file.getOriginalFilename();
            Path targetLocation = folderPath.resolve(filename);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String checksum = checksumUtil.calculateChecksum(file.getInputStream());

            FileMetadataEntity metadata = new FileMetadataEntity();

            metadata.setFolder(optionalFolderEntity.get());
            metadata.setFileName(filename);
            metadata.setChecksum(checksum);
            metadata.setFilePath(targetLocation.toString());
            metadata.setFileType(file.getContentType());
            metadata.setFileSize(file.getSize());
            metadata.setUploadedAt(LocalDateTime.now());
            metadata.setFormatedSize(formatFileSize(file.getSize()));

            return repository.save(metadata);

        }else {
            throw new RuntimeException("Folder does not exist");
        }

    }


    private String formatFileSize(long size) {
        String hrSize = null;

        double b = size;
        double k = size/1024.0;
        double m = ((size/1024.0)/1024.0);
        double g = (((size/1024.0)/1024.0)/1024.0);
        double t = ((((size/1024.0)/1024.0)/1024.0)/1024.0);

        DecimalFormat dec = new DecimalFormat("0.00");

        if ( t>1 ) {
            hrSize = dec.format(t).concat(" TB");
        } else if ( g>1 ) {
            hrSize = dec.format(g).concat(" GB");
        } else if ( m>1 ) {
            hrSize = dec.format(m).concat(" MB");
        } else if ( k>1 ) {
            hrSize = dec.format(k).concat(" KB");
        } else {
            hrSize = dec.format(b).concat(" Bytes");
        }

        return hrSize;
    }

    public Path getFilePath(String folderName, String filename) {
        return Paths.get(baseStorageLocation, folderName).resolve(filename).normalize();
    }

    public List<FileMetadataEntity> getFiles(){
        return repository.findAll();
    }

    public List<FileMetadataEntity> getFolderFiles(String folderName){

        Optional<FolderEntity> folderEntity = folderRepository.findByName(folderName);

        if(folderEntity.isPresent()) {
            return repository.findAllByFolder(folderEntity.get());

        }else {
            throw new RuntimeException("Folder does not exist");
        }
    }


    public FileMetadataEntity getFileDetails(Long id){
        return repository.findById(id).orElse(null);
    }



    public void createFolder(String folderName) throws IOException {
        Path folderPath = Paths.get(baseStorageLocation, folderName).toAbsolutePath().normalize();
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        } else {
            throw new RuntimeException("Folder already exists: " + folderName);
        }
    }
}
