package com.urlshortner.shortxurl.Model;

import org.springframework.stereotype.Component;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;

//@Entity
@Document(collection = "urls")
@Component
public class Url {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String shortUrl;
    private String originalUrl;
    private String customName;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getShortUrl() {
        return shortUrl;
    }
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
    public String getOriginalUrl() {
        return originalUrl;
    }
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
    
    public String getCustomName() {
        return customName;
    }
    public void setCustomName(String customName) {
        this.customName = customName;
    }
    public Url() {
    }
    @Override
    public String toString() {
        return "Url [id=" + id + ", shortUrl=" + shortUrl + ", originalUrl=" + originalUrl + ", customName="
                + customName + "]";
    }
    
}
