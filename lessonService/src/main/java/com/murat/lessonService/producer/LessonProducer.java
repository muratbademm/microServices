package com.murat.lessonService.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.murat.lessonService.model.Lesson;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component

public class LessonProducer {
    @Autowired
    KafkaTemplate<Long,String> kafkaTemplate;
    @Autowired
    ObjectMapper objectMapper;
    public  void  sendLessonTopics(Lesson lesson) throws JsonProcessingException {
        Long id = lesson.getId();
        String value = objectMapper.writeValueAsString(lesson);
        CompletableFuture<SendResult<Long, String>> sendResultCompletableFuture = kafkaTemplate.sendDefault(id, value);
        sendResultCompletableFuture.thenApply(result -> {
            handleSuccess(id, value ,result);
            return  result;
        });
        sendResultCompletableFuture.exceptionally(throwable -> {
            handleFaiure(throwable);
            return null;
        });
    }
    private  void handleSuccess(Long id,String value,SendResult<Long,String> result){
        System.out.println("Mesaj gönderildi. key: {}"+id + "Value: "+value+ "partition: {} "+result.getRecordMetadata().partition());
    }
    private  void handleFaiure(Throwable throwable){
        System.out.println("Mesaj gönderilirken hata alındı "+throwable.getMessage());
        try {
            throw  throwable;
        }catch (Throwable throwable1){
            System.err.println("error in onFailure"+throwable1.getMessage());
        }
    }

}
