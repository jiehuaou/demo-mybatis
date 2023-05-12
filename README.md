# demo-mybatis

* use H2 database

### App config with H2 database and MyBatis & Mapper
```shell
spring.h2.console.enabled=true
spring.h2.console.path=/h2

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=123

mybatis.mapper-locations=classpath:mapper/**/*-mapper.xml
mybatis.config-location=classpath:/mybatis-config.xml
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
    <resultMap type="DepartmentType" id="DepartmentResult">
        <id property="id" column="id"/>
        <result property="departName" column="dep_name"/>
        <result property="leader" column="leader"/>
    </resultMap>

    <select id="findById" resultType="DepartmentType" resultMap="DepartmentResult">
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
    <select id="findByNameLikeAndLeader" resultType="DepartmentType" resultMap="DepartmentResult">
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