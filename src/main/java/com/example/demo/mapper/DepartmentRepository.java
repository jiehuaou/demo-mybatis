package com.example.demo.mapper;

import com.example.demo.model.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentRepository {
    List<Department> findAll();

    List<Department> findByNameLike(String depName);

    List<Department> findByNameLikeAndLeader(@Param("depName") String nameLike,  String leader);

    List<Department> findByNameIn(String ...names);
}
