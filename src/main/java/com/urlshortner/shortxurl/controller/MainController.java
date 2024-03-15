package com.urlshortner.shortxurl.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.urlshortner.shortxurl.Model.Url;
import com.urlshortner.shortxurl.helpers.Helper;
import com.urlshortner.shortxurl.service.UrlService;

@Controller
public class MainController {

    @Value("${your.domain.name}")
    String domain;
    @Autowired
    UrlService urlService;

    @Autowired
    Helper helper;

    @Autowired
    Url url;

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/index")
    public String showIndexPage() {
        return "index";
    }

    @PostMapping("/shortxurl")
    public String shortUrlRedirect(@RequestParam("originalUrl") String originalUrl,
            @RequestParam("customName") String customName,
            RedirectAttributes redirectAttributes) {
        // Check if the original URL and custom name are not null and not empty
        if (originalUrl == null || originalUrl.isEmpty() || customName == null || customName.isEmpty()) {
            redirectAttributes.addFlashAttribute("urlNotValid", "URL and custom name cannot be empty");
            return "redirect:/index";
        }

        // Check if the original URL matches the valid URL pattern
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(originalUrl);

        if (!matcher.matches()) {
            // If the URL does not match the pattern, handle the invalid URL case
            redirectAttributes.addFlashAttribute("urlNotValid", "Provided URL is not valid");
            return "redirect:/index";
        }

        // Check if the original URL or custom name exceeds certain lengths
        if (originalUrl.length() > 2048 || customName.length() > 50) {
            redirectAttributes.addFlashAttribute("urlNotValid", "URL or custom name is too long");
            return "redirect:/index";
        }

        // Proceed with URL shortening process
        url.setCustomName(customName);
        url.setOriginalUrl(originalUrl);
        String shorterUrl = helper.generateRandomString(url.getCustomName());
        url.setShortUrl(shorterUrl);

        System.out.println(shorterUrl);

        Url savedUrl = urlService.saveUrl(url);
        redirectAttributes.addFlashAttribute("domain", domain);
        redirectAttributes.addFlashAttribute("shortenedUrl", savedUrl.getShortUrl());

        return "redirect:/index";
    }

    @GetMapping("/{shortUrl}")
    public String redirectToOrgUrl(@PathVariable("shortUrl") String shortUrl) {
        Url originalUrl = urlService.getUrlByUrl(shortUrl);
        if (originalUrl != null) {
            return "redirect:" + originalUrl.getOriginalUrl();
        } else {
            return "index";
        }
    }
}
