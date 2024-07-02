package com.urlshortner.shortxurl.dao;

//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
import com.urlshortner.shortxurl.Model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepo extends MongoRepository<Url, Integer>{
    public Url findByShortUrl(String url);
}
