package com.example.uploadingfiles.mongo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@Document(collection = "files")
public class MongoFile {
    @Id
    private String id;

    private String title;

    private Binary file;

    public MongoFile(String title) {
        this.title = title;
    }

    public Map<String, String> toHashMap() {
        Map<String, String> map = new HashMap<>();
        map.put("id", this.id);
        map.put("title", this.title);
        return map;
    }
}
