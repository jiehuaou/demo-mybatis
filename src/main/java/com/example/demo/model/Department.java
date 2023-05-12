package com.example.demo.model;

import lombok.*;
import org.apache.ibatis.type.Alias;

@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("DepartmentType")
public class Department {
    private Long id;
    private String departName;
    private String leader;
}
