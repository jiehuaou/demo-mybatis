package com.example.demo.mapper;

import com.example.demo.builder.QueryBuilder;
import com.example.demo.model.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * Mapper with annotations
 */
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

    @Insert("insert into emp (id, first_name, last_name, career) values(#{id}, #{firstName}, #{lastName}, #{career})")
    @SelectKey(statement="VALUES NEXT VALUE FOR SEQ_ID", keyProperty="id", before=true, resultType=Long.class)
    public int create(Employee employee);

    @SelectProvider(type = QueryBuilder.class, method = "selectOnly3")
    public List<Employee> findSome();

    /**
     * generate "where" condition only when parameter is not null
     */
    @Select("""
            <script>select * from emp 
                <where>
                    <if test='depId != null'> dep_id=#{depId} </if>
                    <if test='career != null'> AND career=#{career} </if>
                </where>
            </script>
            """)
    public List<Employee> findWithCondition(Long depId, String career);

    /**
     * generate "where" condition only when parameter is not null
     */
    @SelectProvider(type = QueryBuilder.class)  // if method missing, then choose Same Method Name
    public List<Employee> selectWithCondition(@Param("depId") Long depId, @Param("career") String career);

    /**
     * use bind parameter
     */
    @Select("""
            <script>
                <bind name="firstName" value="'%' + _parameter + '%'" />
                SELECT * FROM emp 
                    WHERE first_name like #{firstName}
            </script>""")
    public List<Employee> findByFirstNameLike(String firstName);
}
