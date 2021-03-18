package com.example.fileAttachment;

import com.example.fileAttachment.mongo.MongoFileService;
import com.example.fileAttachment.storage.FileUploadController;
import com.example.fileAttachment.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.stream.Collectors;

@Controller
public class CommonController {
    private final StorageService storageService;
    private final MongoFileService mongoFileService;

    @Autowired
    public CommonController(StorageService storageService, MongoFileService mongoFileService) {
        this.storageService = storageService;
        this.mongoFileService = mongoFileService;
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("localFiles", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
        model.addAttribute("mongoFiles", mongoFileService.getMongoFiles());
        return "uploadForm";
    }
}
