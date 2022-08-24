package com.myavro.consumingwithaavro;

import lombok.Data;

@Data
public class Student {
    private String name;
    private String course;
    private Long age;

    public Student() {

    }

    public Student(String name, String course, Long age) {
        this.name = name;
        this.course = course;
        this.age = age;
    }
}
