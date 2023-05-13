package com.example.demo.model;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
    private Long id;
    private String departName;
    private String leader;

    public Department(Long id) {
        this.id = id;
    }

    public String toShortString() {
        return "Dep{" +
                Optional.ofNullable(id).map(e->"id=" + e).orElseGet(()->"id=None") +
                Optional.ofNullable(departName).map(e->", departName=" + e).orElse("") +
                Optional.ofNullable(leader).map(e->", leader=" + e).orElse("")+
                "}";
    }
}
