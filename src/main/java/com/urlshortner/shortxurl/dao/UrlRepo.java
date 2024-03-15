package com.urlshortner.shortxurl.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.urlshortner.shortxurl.Model.Url;

@Repository
public interface UrlRepo extends CrudRepository<Url, Integer>{
    public Url findByShortUrl(String url);
}
