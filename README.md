# spring-boot-stater-multiple-datasource
spring boot 多数据源启动类

在项目开发中经常会遇见同一个项目中使用多个数据源的场景，在使用时我们需要对数据源进行切换，某些指定的方法访问特定的数据源。
spring-boot-stater-multiple-datasource 是可以让用户在spring boot项目中非常简单的实现多个数据源的操作，具体用法如下：

> 现在没有在共有仓库中上传，需要从github下载源码进行编译，然后导入到项目中，后期如果能够上传到maven仓库中，则可以直接添加依赖

1、下载spring-boot-stater-multiple-datasource 源码进行安装 
``` mvn install ```

2、在项目pom文件中添加依赖
```xml
<dependency>
    <groupId>com.zl</groupId>
    <artifactId>spring-boot-stater-multiple-data-source</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

3、在spring boot 配置文件中配置多个数据源，spring boot默认数据源配置为默认数据源，下面是两个数据源db1、db2(可以随意命名，后面切换数据源时会用到)，spring.datasource.multi.poolClassName属性可以指定数据库连接池全限定名，不填默认为c3p0数据库连接池
```properties
spring.datasource.url=jdbc:mysql:///test?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=admin
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

#spring.datasource.multi.poolClassName=

spring.datasource.multi.db1.url=jdbc:mysql:///test1?serverTimezone=UTC
spring.datasource.multi.db1.username=root
spring.datasource.multi.db1.password=admin
spring.datasource.multi.db1.driver-class-name=com.mysql.jdbc.Driver

spring.datasource.multi.db2.url=jdbc:mysql:///test2?serverTimezone=UTC
spring.datasource.multi.db2.username=root
spring.datasource.multi.db2.password=admin
spring.datasource.multi.db2.driver-class-name=com.mysql.jdbc.Driver

```

4、在需要指定数据源的方法上添加@SelectDatabase，用来指定数据源，比如想用上面定一的db1数据源，则添加SelectDatabase("db1")注解即可，支持在mybatis的接口上添加
```java
@Repository
public class DaoTest {

    @Autowired
    private JdbcTemplate template;

    @SelectDatabase("db1")
    public void test() {
        template.query("select * from t_user", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                System.out.println(resultSet.getInt(1) + "," + resultSet.getString(2));
            }
        });
    }
}

```

5、如果在某些地方不能使用注解的方式指定数据源，也可以通过代码的方式进行数据源切换，此操作线程安全
```java
DatabaseContextHolder.setDatabaseType("db1");
```

如有问题可以发我邮箱 925294372@qq.com 或者加我qq：925294372 共同讨论，共同学习