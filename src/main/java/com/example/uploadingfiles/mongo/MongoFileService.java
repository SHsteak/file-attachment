package com.example.uploadingfiles.mongo;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MongoFileService {
    @Autowired
    private MongoFileRepository mongoFileRepo;

    // upload
    public String addMongoFile(MultipartFile file) throws IOException {
        MongoFile mongoFile = new MongoFile(new String(file.getOriginalFilename().getBytes(StandardCharsets.UTF_8)));
        mongoFile.setFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        return mongoFileRepo.insert(mongoFile).getId();
    }

    // get
    public MongoFile getMongoFile(String id) {
        return mongoFileRepo.findById(id).get();
    }


    // get all
    public List<Map<String, String>> getMongoFiles() {
        return mongoFileRepo.findAll().stream().map(MongoFile::toHashMap).collect(Collectors.toList());
    }

    // delete
    public void deleteMongoFile(String id) {
        mongoFileRepo.deleteById(id);
    }
}
