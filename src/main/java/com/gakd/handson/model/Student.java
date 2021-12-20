package com.gakd.handson.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Student {

    private Integer studentId;
    private String studentName;
}
