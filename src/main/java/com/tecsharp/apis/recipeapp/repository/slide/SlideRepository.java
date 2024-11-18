package com.tecsharp.apis.recipeapp.repository.slide;

import com.tecsharp.apis.recipeapp.enums.SlideType;
import com.tecsharp.apis.recipeapp.model.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {

    List<Slide> findByType(SlideType type);

}
