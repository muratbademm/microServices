package com.murat.lessonService.client;

import com.murat.lessonService.model.Student;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface StudentClient {
    @GetExchange("student/lesson/{lessonId}")
    public List<Student> findByLessonId(@PathVariable("lessonId") Long lessonId );
    //StudentClient sadece metodun imzasını içerir ama arka planda Spring otomatik olarak bu interface için bir proxy (vekil) oluşturur.
    //Bu proxy, studentService'in ilgili endpointine (/student/lesson/{lessonId}) HTTP çağrısı yapar.
    //LessonService içinde studentClient.findByLessonId(id) dediğinde, bu proxy gerçek HTTP isteği atar, cevap JSON olarak gelir ve List<Student> nesnesine dönüştürülür.
}
