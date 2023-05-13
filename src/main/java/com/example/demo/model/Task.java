package com.example.demo.model;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    private Long id;
    private String job;
    private Long empId;

    public Task(Long id) {
        this.id = id;
    }
}
