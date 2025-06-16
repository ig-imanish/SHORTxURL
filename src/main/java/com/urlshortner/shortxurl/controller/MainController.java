package com.urlshortner.shortxurl.controller;

import java.util.UUID;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.urlshortner.shortxurl.Model.Url;
import com.urlshortner.shortxurl.helpers.Helper;
import com.urlshortner.shortxurl.service.UrlService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

 //   private final int limit = 50;

    @Value("${your.domain.name}")
    String domain;

    @Autowired
    UrlService urlService;

    @Autowired
    Helper helper;

    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String showIndexPage(HttpSession session, Model model) {
        return "index";
    }

    @PostMapping("/shortxurl")
    public String shortUrlRedirect(@RequestParam String originalUrl,
            @RequestParam String customName,
            RedirectAttributes redirectAttributes, HttpSession session, Model model) {
     originalUrl = originalUrl.trim();
    customName = customName.trim();
    
    customName = customName.replace(" ", "-");
    
        if (originalUrl == null || originalUrl.isEmpty() || customName == null || customName.isEmpty()) {
            redirectAttributes.addFlashAttribute("urlNotValid", "URL and custom name cannot be empty");
            return "redirect:/index";
        }

        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(originalUrl);

        if (!matcher.matches()) {
            redirectAttributes.addFlashAttribute("urlNotValid", "Provided URL is not valid");
            return "redirect:/index";
        }

        if (originalUrl.length() > 2048 || customName.length() > 50) {
            redirectAttributes.addFlashAttribute("urlNotValid", "URL or custom name is too long");
            return "redirect:/index";
        }

        UUID uuid = UUID.randomUUID();
        
        int uniqueID = (int) (uuid.getLeastSignificantBits() & 0xFFFFFFFFL);
        
        
        Url url = new Url();
        url.setId(uniqueID);
        url.setCustomName(customName);
        url.setOriginalUrl(originalUrl);
        String shorterUrl = helper.generateRandomString(customName);
        url.setShortUrl(shorterUrl);
        url.setCreatedAt(LocalDateTime.now());

        Url savedUrl = urlService.saveUrl(url);
        redirectAttributes.addFlashAttribute("domain", domain);
        redirectAttributes.addFlashAttribute("shortenedUrl", savedUrl.getShortUrl());
        return "redirect:/index";
    }

 @GetMapping("/{shortUrl}")
    public ResponseEntity<String> redirectToOrgUrl(@PathVariable String shortUrl) {
        Url originalUrl = urlService.getUrlByUrl(shortUrl);
        if (originalUrl != null && originalUrl.getOriginalUrl() != null) {
            URI uri = URI.create(originalUrl.getOriginalUrl());
            String redirectingMessage = "<html><body><h1>Redirecting to " + uri.toString() + "</h1></body></html>";
            return ResponseEntity.status(HttpStatus.FOUND).location(uri).body(redirectingMessage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("<html><body><h1>URL not found</h1></body></html>");
        }
    }

}
