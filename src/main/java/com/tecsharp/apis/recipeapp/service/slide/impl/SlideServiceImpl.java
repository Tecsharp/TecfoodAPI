package com.tecsharp.apis.recipeapp.service.slide.impl;

import com.tecsharp.apis.recipeapp.dto.SlideDTO;
import com.tecsharp.apis.recipeapp.model.Slide;
import com.tecsharp.apis.recipeapp.repository.slide.SlideRepository;
import com.tecsharp.apis.recipeapp.service.slide.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideRepository slideRepository;

    @Override
    public void createSlide(Slide slide, MultipartFile image) throws IOException {

        String b64Image = Base64.getEncoder().encodeToString(image.getBytes());
        slide.setB64image(b64Image);

        slideRepository.save(slide);
    }
}
