package com.example.demo.model;

import lombok.*;

@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
    private Long id;
    private String departName;
    private String leader;
}
