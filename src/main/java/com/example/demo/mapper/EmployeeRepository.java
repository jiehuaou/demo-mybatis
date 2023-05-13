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
    @ResultMap("EmployeeResultMap")
    //@Results({ @Result(property = "firstName", column = "first_Name")})
    public List<Employee> findAll();

    @Select("select e.*, " +
            " d.dep_name, d.leader, " +
            " t.id as task_id, t.job as task_job, t.emp_id as task_emp_id" +
            " from emp e " +
            " left outer join dep d on e.dep_id=d.id " +
            " left outer join task t on t.emp_id=e.id")
    @ResultMap("EmployeeResultMap")
    public List<Employee> findAllJoin();

    @Select("SELECT * FROM emp WHERE id = #{id}")
    @ResultMap("EmployeeResultMap")
    public Employee findById(long id);

    @Select("SELECT * FROM emp WHERE id = #{id}")
    @ResultMap("EmployeeResultMap")
    public Optional<Employee> findById2(long id);

    @Select("SELECT * FROM emp WHERE first_name = #{firstName} limit 1")
    @ResultMap("EmployeeResultMap")
    public Optional<Employee> findByFirstName(String firstName);

    @Update("update emp set dep_id=#{depId} WHERE id = #{empId}")
    public int updateDep(@Param("empId") Long empId, @Param("depId") Long depId);

    @Insert("insert into emp (id, first_name, last_name, career) values(#{id}, #{firstName}, #{lastName}, #{career})")
    @SelectKey(statement="VALUES NEXT VALUE FOR SEQ_ID", keyProperty="id", before=true, resultType=Long.class)
    public int create(Employee employee);

    @SelectProvider(type = QueryBuilder.class, method = "selectOnly3")
    @ResultMap("EmployeeResultMap")
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
    @ResultMap("EmployeeResultMap")
    public List<Employee> findWithCondition(Long depId, String career);

    /**
     * generate "where" condition only when parameter is not null
     */
    @SelectProvider(type = QueryBuilder.class)  // if method missing, then choose Same Method Name
    @ResultMap("EmployeeResultMap")
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
    @ResultMap("EmployeeResultMap")
    public List<Employee> findByFirstNameLike(String firstName);
}
