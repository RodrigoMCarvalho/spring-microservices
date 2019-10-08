package com.academy.course.service;

import com.academy.course.model.Course;
import com.academy.course.repository.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CourseService {

    @Autowired
    private CourseRepository repository;

    public Iterable<Course> list(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
