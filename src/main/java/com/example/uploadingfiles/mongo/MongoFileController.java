package com.example.uploadingfiles.mongo;

import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Controller
public class MongoFileController {

    private final MongoFileService mongoFileService;

    @Autowired
    public MongoFileController(MongoFileService mongoFileService) {
        this.mongoFileService = mongoFileService;
    }

    // get file list
    @GetMapping("/mongo/file")
    public List<Map<String, String>> getFiles() {

        return mongoFileService.getMongoFiles();

    }

    // get a file
    @GetMapping("/mongo/file/{id}")
    public Map<String, String> getFile(@PathVariable String id) {

        return mongoFileService.getMongoFile(id).toHashMap();

    }

    // upload - save file in mongodb
    @PostMapping("/mongo/file")
    public String addPhoto(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        String id = mongoFileService.addMongoFile(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded and mongo id is " + id + "!");
        return "redirect:/";

    }

    // download - download from mongodb
    @GetMapping("/mongo/file/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable String id) {

        MongoFile mongoFile = mongoFileService.getMongoFile(id);
        byte[] bytes = mongoFile.getFile().getData();
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(bytes.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(mongoFile.getTitle().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"")
                .body(resource);

    }

    @DeleteMapping("/mongo/file/delete/{id}")
    public void delete(@PathVariable String id) {
        mongoFileService.deleteMongoFile(id);
    }

    @ExceptionHandler(MongoException.class)
    public ResponseEntity<?> handleStorageFileNotFound(MongoFileException exc) {
        return ResponseEntity.notFound().build();
    }
}
