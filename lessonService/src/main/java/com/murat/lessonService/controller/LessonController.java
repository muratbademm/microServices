package com.murat.lessonService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.murat.lessonService.client.StudentClient;
import com.murat.lessonService.model.Lesson;
import com.murat.lessonService.producer.LessonProducer;
import com.murat.lessonService.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
public class
LessonController {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private StudentClient studentClient;
    @Autowired
    LessonProducer lessonProducer;

    @PostMapping
    public Lesson add(@RequestBody Lesson lesson){
         return lessonRepository.addLesson(lesson);
    }

    @GetMapping("/{id}")
    public   Lesson findById(@PathVariable Long id ){
        return lessonRepository.findById(id);
    }
    @GetMapping
    public List<Lesson> findByAll(){
        return lessonRepository.findAll();
    }
    @GetMapping("/withStudents")
    public List<Lesson> findAllWithStudents(){
        List<Lesson> lessonList = lessonRepository.findAll();
        lessonList.forEach(c-> c.setStudentList(studentClient.findByLessonId(c.getId())));
        return  lessonList;
    }

    //lessonService derslerle ilgili işleri yapıyor. Ama içinde Student verisi yok.
    //Öğrencilerin bilgilerini almak için studentService'e HTTP çağrısı yapması gerekiyor.
    //StudentClient interface’i ile yapıyoruz.
    // Özet akış diagramı lesson ile student arası bağlantı şeması
    //flowchart
    //    Eureka[Service Registry (Eureka)]
    //    Lesson[Lesson Service]
    //    Student[Student Service]
    //    User[Client (Tarayıcı, Postman vb.)]
    //
    //    User -->|HTTP İstekleri| Lesson
    //    Lesson -->|Öğrenci Bilgisi İsteği| StudentClient Proxy
    //    StudentClient Proxy -->|HTTP İsteği| Student
    //    Student -->|JSON Veri| StudentClient Proxy
    //    StudentClient Proxy --> Lesson
    //Kullanıcı lessonService'e istek yapar.
    //lessonService öğrenci verisi için StudentClient proxy’sine çağrı yapar.
    //Proxy arka planda studentService'e HTTP GET çağrısı yapar.
    //studentService veri döner, proxy JSON’u Java nesnesine çevirir.

    //lessonService kullanıcıya birleşik veriyi döner.

    @PostMapping("/notification")
    public ResponseEntity<Lesson> postLessonEvent(@RequestBody Lesson lesson) throws JsonProcessingException {
        //kafka producer
        lessonProducer.sendLessonTopics(lesson);
        return  ResponseEntity.status(HttpStatus.CREATED).body(lesson);
    }


}



