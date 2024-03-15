package com.urlshortner.shortxurl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urlshortner.shortxurl.Model.Url;
import com.urlshortner.shortxurl.dao.UrlRepo;

@Service
public class UrlService {
    
    @Autowired
    UrlRepo urlRepo;

    public Url saveUrl(Url url){
        Url savedUrl = null;
        if(url != null){
            savedUrl = urlRepo.save(url);
        }
        return savedUrl;
    }

    public Url getUrlByUrl(String url){
        Url outputUrl = urlRepo.findByShortUrl(url);
        return outputUrl;
    }
}
