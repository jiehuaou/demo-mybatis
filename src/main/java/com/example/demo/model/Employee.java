package com.example.demo.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String career;
//    private Long depId;
    private Department department;

    private List<Task> tasks = new ArrayList<>();

    public Employee(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", career='" + career + '\'' +
                ", dep=" + showDepartmentId() +
                ", tasks=" + tasks +
                '}';
    }

    private String showDepartmentId() {
        return Optional.ofNullable(department)
                .map(e->e.toShortString())
                .orElseGet(()->"{}");
    }
}
