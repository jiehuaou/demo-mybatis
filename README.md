# demo-mybatis

* use H2 database
* use Mybatis

### App config with H2 database and MyBatis & Mapper
```shell
spring.h2.console.enabled=true
spring.h2.console.path=/h2

# DB config
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=123

# mybatis config
mybatis.mapper-locations=classpath:mapper/**/*-mapper.xml
mybatis.config-location=classpath:/mybatis-config.xml

# mapper loging
logging.level.com.example.demo.mapper=TRACE
```

### mybatis-config.xml
```xml
<configuration>
    <settings>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
    <typeAliases>
        <typeAlias type="com.example.demo.model.Department" alias="DepartmentType" />
    </typeAliases>
</configuration>
```

### Mapper XML and Mapper Interface
```xml
<mapper namespace="com.example.demo.mapper.DepartmentRepository">
    <!-- result mapping  -->
    <resultMap type="DepartmentType" id="DepartmentMap">
        <id property="id" column="id"/>
        <result property="departName" column="dep_name"/>
        <result property="leader" column="leader"/>
    </resultMap>

    <select id="findById" resultType="DepartmentType" resultMap="DepartmentMap">
        select * from dep where id = #{id}
    </select>
</mapper>
```

```java
@Mapper
public interface DepartmentRepository {
    Optional<Department> findById(Long id);
}
```

### Mapper with Annotations
```java
@Mapper
public interface EmployeeRepository {
    @Select("select * from emp")
    @Results({@Result(property = "firstName", column = "first_Name")})
    public List<Employee> findAll();

    @Select("SELECT * FROM emp WHERE id = #{id}")
    public Employee findById(long id);
}
```

### Dynamic SQL with XML
```xml
<!-- dynamic condition : find by Name Like '%xxx%' and leader EQ 'xxx' -->
    <select id="findByNameLikeAndLeader" resultType="DepartmentType" resultMap="DepartmentMap">
        <bind name="depName" value="'%' + depName.toLowerCase().trim() + '%'"/>
        select * from dep
        <where>
            <if test="depName!=null">dep_name like #{depName}</if>
            <if test="leader!=null">and leader = #{leader}</if>
        </where>
    </select>
```

java corresponding Method
```java
@Mapper
public interface DepartmentRepository {
    
    List<Department> findByNameLikeAndLeader(@Param("depName") String nameLike, String leader);
}
```

### Dynamic SQL with Annotations
Sql provider
```java
public class QueryBuilder implements ProviderMethodResolver {
    /**
     * generate "where" condition only when parameter is not null
     */
    public String selectWithCondition(final Long depId, final String career) {
        String sql = new SQL(){{
            SELECT("*");
            FROM("EMP");
            if(depId!=null) {
                WHERE("dep_id=#{depId}");
            }
            if(career!=null) {
                WHERE("career=#{career}");
            }
        }}.toString();
        log.info("sql ==> {}", sql);
        return sql;
    }

}
```
Mapper Interface using Sql provider
```java
@Mapper
public interface EmployeeRepository {
    /**
     * generate "where" condition only when parameter is not null
     */
    @SelectProvider(type = QueryBuilder.class)  // if method missing, then choose Same Method Name
    public List<Employee> selectWithCondition(@Param("depId") Long depId, @Param("career") String career);
}
```

### Using Spring-Managed Transaction

```java
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public void testSuccessTransaction(Long id1, Long id2) {
        departmentRepository.create(Department.builder().id(id1).departName("a001").build());
        departmentRepository.create(Department.builder().id(id2).departName("a002").build());
    }
}
```

### Programmatic Transaction

use either transactionManager or TransactionTemplate
```java
@Component
public class Runner129TransactionManually {

    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    DepartmentRepository departmentRepository;

    // use transactionManager
    void testSuccessTransactionManually() {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            departmentRepository.create(Department.builder().id(1501L).departName("a005").build());
            departmentRepository.create(Department.builder().id(1502L).departName("a005").build());
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.info("TransactionException ==> {}", e);
        }
    }

    // use TransactionTemplate
    void testSuccessTransactionTemplateManually() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            try {
                departmentRepository.create(Department.builder().id(1801L).departName("a005").build());
                departmentRepository.create(Department.builder().id(1802L).departName("a005").build());
                //abort();
            } catch (Exception e) {
                status.setRollbackOnly();
                log.info("TransactionException ==> {}", e.toString());
            }
            return null;
        });
    }
}
```

### Join Query & ResultMap

Join Query
```sql
select e.id, e.first_name, e.last_name, e.career, e.dep_id, 
     d.dep_name, d.leader, 
     t.id as task_id, t.job as task_job, t.emp_id as task_emp_id
     from emp e 
     left outer join dep d on e.dep_id=d.id 
     left outer join task t on t.emp_id=e.id
```

Result Map
```xml
<mapper namespace="com.example.demo.mapper.EmployeeRepository">
    <resultMap type="EmployeeType" id="JoinEmployeeMap" extends="EmployeeMap">
        <!--   ref department Object     -->
        <association property="department" resultMap="DepartmentMap"/>
        <!--   ref collection of Task Object     -->
        <collection property="tasks" resultMap="TaskMap" ofType="TaskType" />
    </resultMap>
    <resultMap id="EmployeeMap" type="EmployeeType">
        <id     column="id" property="id"/>
        <result column="first_name" property="firstName" />
        <result column="last_name" property="lastName" />
        <result column="career" property="career" />
    </resultMap>
    <resultMap id="DepartmentMap" type="DepartmentType">
        <id     property="id" column="dep_id"/>
        <result property="departName" column="dep_name"/>
        <result property="leader" column="dep_leader"/>
    </resultMap>
    <resultMap id="TaskMap" type="TaskType">
        <id property="id" column="task_id"/>
        <result property="job" column="task_job"/>
        <result property="empId" column="task_emp_id"/>
    </resultMap>
</mapper>
```

Entity Class
```java
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String career;
    private Department department;
    private List<Task> tasks = new ArrayList<>();
}
```

Mapper Interface, via Annotations, reference the ResultMap **JoinEmployeeResultMap** defined in XML file. 
```java
@Mapper
public interface EmployeeRepository {
    @Select("select ... " +
            " from emp e " +
            " left outer join dep d on e.dep_id=d.id " +
            " left outer join task t on t.emp_id=e.id")
    @ResultMap("JoinEmployeeMap")
    public List<Employee> findAllWithJoin();
}
```

### lazy loading 
mybatis-config.xml
```xml
<settings>
    <setting name="aggressiveLazyLoading" value="false" />
    <setting name="lazyLoadTriggerMethods" value=""/> <!-- default: "equals,clone,hashCode,toString" -->
</settings>
```

Mapper XML, specify **fetchType="lazy"** in association or collection.
```xml
<mapper>
    <resultMap id="NestedEmployeeMap" type="EmployeeType" extends="EmployeeMap">
        <association property="department" column="dep_id" select="selectDepartment" fetchType="lazy"/>
        <collection property="tasks" column="id" select="selectTask" fetchType="lazy"/>
    </resultMap>

    <select id="selectEmployeeWithNested" resultType="EmployeeType" resultMap="NestedEmployeeMap">
        SELECT id, first_name, last_name, career, dep_id FROM emp WHERE id = #{id}
    </select>

    <select id="selectDepartment" resultType="DepartmentType" resultMap="DepartmentMap">
        select id as dep_id, dep_name, leader as dep_leader from dep where id = #{id}
    </select>
    <select id="selectTask" resultType="TaskType" resultMap="TaskMap">
        select id as task_id, job as task_job, emp_id as task_emp_id from task where emp_id = #{id}
    </select>

</mapper>
```

