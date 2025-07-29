package com.murat.studentService.repository;

import com.murat.studentService.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {
    public List<Student> studentList =new ArrayList<>();
    public Student addStudent(Student student){
        studentList.add(student);
        return  student;
    }
    public Student findById(Long id){
        return studentList.stream().filter(c -> c.id().equals(id)).findFirst().orElseThrow();
    }
    public List<Student>  findAll(){
        return  studentList;
    }

    public List<Student> findByLessonId(Long id) {
        return studentList.stream().filter(c -> c.lessonId().equals(id))
                .collect(Collectors.toList());
    }
}
