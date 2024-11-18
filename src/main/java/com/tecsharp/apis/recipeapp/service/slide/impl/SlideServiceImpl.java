package com.tecsharp.apis.recipeapp.service.slide.impl;

import com.tecsharp.apis.recipeapp.dto.SlideDTO;
import com.tecsharp.apis.recipeapp.enums.SlideType;
import com.tecsharp.apis.recipeapp.model.Slide;
import com.tecsharp.apis.recipeapp.repository.slide.SlideRepository;
import com.tecsharp.apis.recipeapp.service.slide.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private SlideRepository slideRepository;

    @Override
    public void createSlide(Slide slide, MultipartFile image) {
        try {
            String b64Image = Base64.getEncoder().encodeToString(image.getBytes());
            slide.setB64image(b64Image);
            slideRepository.save(slide);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el slide: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SlideDTO> getIndexSlides() {
        List<SlideDTO> slideList = new ArrayList<>();
        try {
            var slides = slideRepository.findByType(SlideType.INDEX);
            for (Slide slide : slides) {
                SlideDTO slideDTO = new SlideDTO();
                slideDTO.setId(slide.getId());
                slideDTO.setTitle(slide.getTitle());
                slideDTO.setDescription(slide.getDescription());
                slideDTO.setUrl(slide.getUrl());
                slideDTO.setB64image(slide.getB64image());
                slideList.add(slideDTO);
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al obtener los slides: " + e.getMessage(), e);
        }
        return slideList;
    }


}
