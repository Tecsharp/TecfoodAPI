package com.tecsharp.apis.recipeapp.service.slide;
import com.tecsharp.apis.recipeapp.dto.SlideDTO;
import com.tecsharp.apis.recipeapp.model.Slide;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SlideService {

    void createSlide(Slide slide, MultipartFile image) throws IOException;

    List<SlideDTO> getIndexSlides();
}
