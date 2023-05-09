# demo-mybatis

* use H2 database

### H2 database config
```shell
spring.h2.console.enabled=true
spring.h2.console.path=/h2

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=123

# mapping first_name => firstName
mybatis.configuration.map-underscore-to-camel-case=true
```

