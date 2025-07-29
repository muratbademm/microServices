package com.murat.lessonService.model;

public record Student(Long id, String name, Long lessonId) {
    // getters işlemlerini constract'ır ve equals gibi işlemleri otomatik yapar
    //final gibidir (immutable) değiştirilemez bir yapıdadır.

}
