package com.example.demo.mapper;

import com.example.demo.builder.QueryBuilder;
import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Mapper with annotations
 */
@Mapper
public interface EmployeeRepository {
    @Select("select * from emp")
    @ResultMap("EmployeeMap")
    //@Results({ @Result(property = "firstName", column = "first_Name")})
    public List<Employee> findAll();

    /**
     * construct object graph with Join Query.
     */
    @Select("select e.*, " +
            " d.dep_name, d.leader as dep_leader, " +
            " t.id as task_id, t.job as task_job, t.emp_id as task_emp_id" +
            " from emp e " +
            " left outer join dep d on e.dep_id=d.id " +
            " left outer join task t on t.emp_id=e.id")
    @ResultMap("JoinEmployeeMap")
    public List<Employee> findAllWithJoin();

//    @Select("SELECT id, first_name, last_name, career, dep_id FROM emp WHERE id = #{id}")
//    @Results(id="simpleEmp", value = {
//            @Result(property = "id", column = "id", id = true),
//            @Result(property = "firstName", column = "first_name"),
//            @Result(property = "lastName", column = "last_name"),
//            @Result(property = "career", column = "career"),
//            @Result(property = "department", column = "dep_id", one = @One(fetchType = FetchType.LAZY, select = "selectDepartment"))
//    })
    public Employee selectEmployeeWithNested(long id);

//    @Select("SELECT id, dep_name, leader FROM dep WHERE id = #{depId}")
//    @Results({
//            @Result(property = "id", column = "id", id = true),
//            @Result(property = "departName", column = "dep_name"),
//            @Result(property = "leader", column = "leader")
//    })
    //public Department selectDepartment(@Param("depId") Long id);

    @Select("SELECT * FROM emp WHERE id = #{id}")
    @ResultMap("EmployeeMap")
    public Optional<Employee> findById(long id);
    @Select("SELECT * FROM emp WHERE id = #{id}")
    @ResultMap("EmployeeMap")
    public Optional<Employee> findById2(long id);

    /**
     * Map is return without entity.
     */
    @Select("SELECT * FROM emp WHERE id = #{id}")
    @ResultMap("EmployeeMap")
    public Map findAsMap(long id);

    @Select("SELECT * FROM emp WHERE first_name = #{firstName} limit 1")
    @ResultMap("EmployeeMap")
    public Optional<Employee> findByFirstName(String firstName);

    @Update("update emp set dep_id=#{depId} WHERE id = #{empId}")
    public int updateDep(@Param("empId") Long empId, @Param("depId") Long depId);

    @Insert("insert into emp (id, first_name, last_name, career) values(#{id}, #{firstName}, #{lastName}, #{career})")
    @SelectKey(statement="VALUES NEXT VALUE FOR SEQ_ID", keyProperty="id", before=true, resultType=Long.class)
    public int create(Employee employee);

    @SelectProvider(type = QueryBuilder.class, method = "selectOnly3")
    @ResultMap("EmployeeMap")
    public List<Employee> selectOnly3();

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
    @ResultMap("EmployeeMap")
    public List<Employee> findWithCondition(Long depId, String career);

    /**
     * generate "where" condition only when parameter is not null
     */
    @SelectProvider(type = QueryBuilder.class)  // if method missing, then choose Same Method Name
    @ResultMap("EmployeeMap")
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
    @ResultMap("EmployeeMap")
    public List<Employee> findByFirstNameLike(String firstName);


}
