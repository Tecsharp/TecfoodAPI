package com.tecsharp.apis.recipeapp.repository.slide;

import com.tecsharp.apis.recipeapp.model.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {

}
