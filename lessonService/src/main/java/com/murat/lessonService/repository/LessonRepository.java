package com.murat.lessonService.repository;

import com.murat.lessonService.model.Lesson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class LessonRepository {
    public List<Lesson> lessonList =new ArrayList<>();
    public Lesson addLesson(Lesson lesson){
        lessonList.add(lesson);
        return  lesson;
    }
    public Lesson findById(Long id){
       return lessonList.stream().filter(c->c.getId().equals(id)).findFirst().orElseThrow();
    }
    public List<Lesson>  findAll(){
        return  lessonList;
    }
}
