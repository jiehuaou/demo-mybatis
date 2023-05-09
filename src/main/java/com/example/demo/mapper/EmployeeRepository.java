package com.example.demo.mapper;

import com.example.demo.builder.QueryBuilder;
import com.example.demo.model.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmployeeRepository {
    @Select("select * from emp")
    @Results({ @Result(property = "firstName", column = "first_Name")})
    public List<Employee> findAll();

    @Select("SELECT * FROM emp WHERE id = #{id}")
    public Employee findById(long id);

    @Select("SELECT * FROM emp WHERE id = #{id}")
    public Optional<Employee> findById2(long id);

    @Select("SELECT * FROM emp WHERE first_name = #{firstName} limit 1")
    public Optional<Employee> findByFirstName(String firstName);

    @Update("update emp set dep_id=#{depId} WHERE id = #{empId}")
    public int updateDep(@Param("empId") Long empId, @Param("depId") Long depId);

    @Insert("insert into emp (first_name, last_name, career) values(#{firstName}, #{lastName}, #{career})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int create(Employee employee);

    @SelectProvider(type = QueryBuilder.class, method = "hello")
    public List<Employee> findSome();

    @Select("""
            <script>select * from emp
                <if test='depId != null or career != null'> WHERE </if>
                <if test='depId != null'> dep_id=#{depId} </if>
                <if test='depId != null and career != null'> AND </if>
                <if test='career != null'> career=#{career} </if>
            </script>
            """)
    public List<Employee> findWithCondition(Long depId, String career);
}
