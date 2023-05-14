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

    public Employee(Long id, String firstName, String lastName, String career) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.career = career;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", career='" + career + '\'' +
                '}';
    }

    public String toLongString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", career='" + career + '\'' +
                ", dep=" + showDepartmentId() +
                ", tasks=" + getTasks() +
                '}';
    }

    private String showDepartmentId() {
        return Optional.ofNullable(getDepartment())
                .map(e->e.toShortString())
                .orElseGet(()->"{}");
    }
}
