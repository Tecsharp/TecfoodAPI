package com.tecsharp.apis.recipeapp.controller;

import com.tecsharp.apis.recipeapp.dto.SlideDTO;
import com.tecsharp.apis.recipeapp.enums.SlideType;
import com.tecsharp.apis.recipeapp.model.Slide;
import com.tecsharp.apis.recipeapp.service.slide.SlideService;
import com.tecsharp.apis.recipeapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/slide")
public class SlideController {

    @Autowired
    private SlideService slideService;

    Logger log = LoggerFactory.getLogger(SlideController.class);
    public static final String CONTROLLER_NAME = "SlideController";


    @GetMapping("/index/header")
    public ResponseEntity<List<SlideDTO>> getIndexSlideHeader() {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "getIndexSlideHeader");
        var slides = slideService.getIndexSlides();
        log.info("Slides obtained: {}", slides);
        return ResponseEntity.ok().body(slides);
    }

    @PostMapping("/index/create")
    public ResponseEntity<Void> createSlide(
            @RequestParam Long id,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String url,
            @RequestParam("image") MultipartFile image,
            @RequestParam("type") SlideType type
            ) throws IOException {
        log.info(Constants.CONTROLLER_METHOD, CONTROLLER_NAME, "createSlide");

        var slide = new Slide();
        slide.setId(id);
        slide.setTitle(title);
        slide.setDescription(description);
        slide.setUrl(url);
        slide.setType(type);

        log.info("Slide created: {}", slide);
        slideService.createSlide(slide, image);
        return ResponseEntity.ok().build();
    }

}
