PS C:\Users\Levur\BTDD\KST-BTDD---Programov-n-zen-testy> docker compose logs app
tool-rental-app  | 
tool-rental-app  |   .   ____          _            __ _ _
tool-rental-app  |  /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
tool-rental-app  | ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
tool-rental-app  |  \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
tool-rental-app  |   '  |____| .__|_| |_|_| |_\__, | / / / /
tool-rental-app  |  =========|_|==============|___/=/_/_/_/
tool-rental-app  |  :: Spring Boot ::                (v3.2.0)
tool-rental-app  |
tool-rental-app  | 2026-03-11T13:56:27.604Z  INFO 1 --- [tool-rental] [           main] cz.kst.btdd.ToolRentalApplication        : Starting ToolRentalApplication v0.1.0-SNAPSHOT using Java 17.0.18 with PID 1 (/app/app.jar started by app in /app)
tool-rental-app  | 2026-03-11T13:56:27.618Z DEBUG 1 --- [tool-rental] [           main] cz.kst.btdd.ToolRentalApplication        : Running with Spring Boot v3.2.0, Spring v6.1.1   
tool-rental-app  | 2026-03-11T13:56:27.619Z  INFO 1 --- [tool-rental] [           main] cz.kst.btdd.ToolRentalApplication        : No active profile set, falling back to 1 default profile: "default"
tool-rental-app  | 2026-03-11T13:56:28.622Z  INFO 1 --- [tool-rental] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
tool-rental-app  | 2026-03-11T13:56:28.692Z  INFO 1 --- [tool-rental] [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 58 ms. Found 3 JPA repository interfaces.
tool-rental-app  | 2026-03-11T13:56:29.232Z  INFO 1 --- [tool-rental] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
tool-rental-app  | 2026-03-11T13:56:29.246Z  INFO 1 --- [tool-rental] [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
tool-rental-app  | 2026-03-11T13:56:29.246Z  INFO 1 --- [tool-rental] [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.16] 
tool-rental-app  | 2026-03-11T13:56:29.293Z  INFO 1 --- [tool-rental] [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
tool-rental-app  | 2026-03-11T13:56:29.295Z  INFO 1 --- [tool-rental] [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1536 ms
tool-rental-app  | 2026-03-11T13:56:29.328Z  INFO 1 --- [tool-rental] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
tool-rental-app  | 2026-03-11T13:56:29.466Z  INFO 1 --- [tool-rental] [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
tool-rental-app  | 2026-03-11T13:56:29.525Z  INFO 1 --- [tool-rental] [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.3.1.Final
tool-rental-app  | 2026-03-11T13:56:29.565Z  INFO 1 --- [tool-rental] [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
tool-rental-app  | 2026-03-11T13:56:29.816Z  INFO 1 --- [tool-rental] [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
tool-rental-app  | 2026-03-11T13:56:29.843Z  INFO 1 --- [tool-rental] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
tool-rental-app  | 2026-03-11T13:56:29.843Z  WARN 1 --- [tool-rental] [           main] o.h.e.j.e.i.JdbcEnvironmentInitiator     : HHH000342: Could not obtain connection to query metadata
tool-rental-app  |
tool-rental-app  | java.lang.RuntimeException: Driver org.h2.Driver claims to not accept jdbcUrl, jdbc:postgresql://db:5432/rental
tool-rental-app  |      at com.zaxxer.hikari.util.DriverDataSource.<init>(DriverDataSource.java:110) ~[HikariCP-5.0.1.jar!/:na]
tool-rental-app  |      at com.zaxxer.hikari.pool.PoolBase.initializeDataSource(PoolBase.java:326) ~[HikariCP-5.0.1.jar!/:na]
tool-rental-app  |      at com.zaxxer.hikari.pool.PoolBase.<init>(PoolBase.java:112) ~[HikariCP-5.0.1.jar!/:na]
tool-rental-app  |      at com.zaxxer.hikari.pool.HikariPool.<init>(HikariPool.java:93) ~[HikariCP-5.0.1.jar!/:na]
tool-rental-app  |      at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:112) ~[HikariCP-5.0.1.jar!/:na]
tool-rental-app  |      at org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl.getConnection(DatasourceConnectionProviderImpl.java:122) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess.obtainConnection(JdbcEnvironmentInitiator.java:424) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.resource.transaction.backend.jdbc.internal.JdbcIsolationDelegate.delegateWork(JdbcIsolationDelegate.java:61) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator.getJdbcEnvironmentUsingJdbcMetadata(JdbcEnvironmentInitiator.java:273) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator.initiateService(JdbcEnvironmentInitiator.java:105) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator.initiateService(JdbcEnvironmentInitiator.java:66) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.boot.registry.internal.StandardServiceRegistryImpl.initiateService(StandardServiceRegistryImpl.java:129) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.service.internal.AbstractServiceRegistryImpl.createService(AbstractServiceRegistryImpl.java:263) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.service.internal.AbstractServiceRegistryImpl.initializeService(AbstractServiceRegistryImpl.java:238) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.service.internal.AbstractServiceRegistryImpl.getService(AbstractServiceRegistryImpl.java:215) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.boot.model.relational.Database.<init>(Database.java:45) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.boot.internal.InFlightMetadataCollectorImpl.getDatabase(InFlightMetadataCollectorImpl.java:223) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.boot.internal.InFlightMetadataCollectorImpl.<init>(InFlightMetadataCollectorImpl.java:191) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final] 
tool-rental-app  |      at org.hibernate.boot.model.process.spi.MetadataBuildingProcess.complete(MetadataBuildingProcess.java:169) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]  
tool-rental-app  |      at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.metadata(EntityManagerFactoryBuilderImpl.java:1432) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1503) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:75) ~[spring-orm-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:376) ~[spring-orm-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:409) ~[spring-orm-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396) ~[spring-orm-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:352) ~[spring-orm-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1822) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1771) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:601) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:523) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:325) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:323) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:199) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1173) ~[spring-context-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:936) ~[spring-context-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:616) ~[spring-context-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:753) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:455) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at org.springframework.boot.SpringApplication.run(SpringApplication.java:323) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at org.springframework.boot.SpringApplication.run(SpringApplication.java:1342) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at org.springframework.boot.SpringApplication.run(SpringApplication.java:1331) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at cz.kst.btdd.ToolRentalApplication.main(ToolRentalApplication.java:10) ~[!/:0.1.0-SNAPSHOT]
tool-rental-app  |      at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
tool-rental-app  |      at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Unknown Source) ~[na:na]
tool-rental-app  |      at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source) ~[na:na]
tool-rental-app  |      at java.base/java.lang.reflect.Method.invoke(Unknown Source) ~[na:na]
tool-rental-app  |      at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:91) ~[app.jar:0.1.0-SNAPSHOT]
tool-rental-app  |      at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:53) ~[app.jar:0.1.0-SNAPSHOT]
tool-rental-app  |      at org.springframework.boot.loader.launch.JarLauncher.main(JarLauncher.java:58) ~[app.jar:0.1.0-SNAPSHOT]
tool-rental-app  |
tool-rental-app  | 2026-03-11T13:56:29.866Z  WARN 1 --- [tool-rental] [           main] org.hibernate.orm.deprecation            : HHH90000025: PostgreSQLDialect does not need to be specified explicitly using 'hibernate.dialect' (remove the property setting and it will be selected by default)
tool-rental-app  | 2026-03-11T13:56:30.660Z  INFO 1 --- [tool-rental] [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
tool-rental-app  | 2026-03-11T13:56:30.672Z  INFO 1 --- [tool-rental] [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
tool-rental-app  | 2026-03-11T13:56:30.676Z ERROR 1 --- [tool-rental] [           main] j.LocalContainerEntityManagerFactoryBean : Failed to initialize JPA EntityManagerFactory: [P
ersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is java.lang.RuntimeException: Driver org.h2.Driver claims to not accept jdbcUrl, jdbc:postgresql://db:5432/rental
tool-rental-app  | 2026-03-11T13:56:30.677Z  WARN 1 --- [tool-rental] [           main] ConfigServletWebServerApplicationContext : Exception encountered during context initializati
on - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/s
pringframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is java.lang.RuntimeException: Driver org.h2.Driver claims to not accept jdbcUrl, jdbc:postgresql://db:5432/rental
tool-rental-app  | 2026-03-11T13:56:30.679Z  INFO 1 --- [tool-rental] [           main] o.apache.catalina.core.StandardService   : Stopping service [Tomcat]
tool-rental-app  | 2026-03-11T13:56:30.690Z  INFO 1 --- [tool-rental] [           main] .s.b.a.l.ConditionEvaluationReportLogger :
tool-rental-app  |
tool-rental-app  | Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
tool-rental-app  | 2026-03-11T13:56:30.702Z ERROR 1 --- [tool-rental] [           main] o.s.boot.SpringApplication               : Application run failed
tool-rental-app  |
tool-rental-app  | org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework
/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is java.lang.RuntimeException: Driver org.h2.Driver claims to not accept jdbcUrl, jdbc:postgresql://db:5432/rental
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1775) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:601) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:523) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:325) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:323) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:199) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1173) ~[spring-context-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:936) ~[spring-context-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:616) ~[spring-context-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:753) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:455) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at org.springframework.boot.SpringApplication.run(SpringApplication.java:323) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at org.springframework.boot.SpringApplication.run(SpringApplication.java:1342) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at org.springframework.boot.SpringApplication.run(SpringApplication.java:1331) ~[spring-boot-3.2.0.jar!/:3.2.0]
tool-rental-app  |      at cz.kst.btdd.ToolRentalApplication.main(ToolRentalApplication.java:10) ~[!/:0.1.0-SNAPSHOT]
tool-rental-app  |      at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
tool-rental-app  |      at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Unknown Source) ~[na:na]
tool-rental-app  |      at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source) ~[na:na]
tool-rental-app  |      at java.base/java.lang.reflect.Method.invoke(Unknown Source) ~[na:na]
tool-rental-app  |      at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:91) ~[app.jar:0.1.0-SNAPSHOT]
tool-rental-app  |      at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:53) ~[app.jar:0.1.0-SNAPSHOT]
tool-rental-app  |      at org.springframework.boot.loader.launch.JarLauncher.main(JarLauncher.java:58) ~[app.jar:0.1.0-SNAPSHOT]
tool-rental-app  | Caused by: jakarta.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory; nested exception is java.lang.RuntimeException: Driver org.h2.Driver claims to not accept jdbcUrl, jdbc:postgresql://db:5432/rental
tool-rental-app  |      at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:421) ~[spring-orm-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:396) ~[spring-orm-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:352) ~[spring-orm-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1822) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1771) ~[spring-beans-6.1.1.jar!/:6.1.1]
tool-rental-app  |      ... 23 common frames omitted
tool-rental-app  | Caused by: java.lang.RuntimeException: Driver org.h2.Driver claims to not accept jdbcUrl, jdbc:postgresql://db:5432/rental
tool-rental-app  |      at com.zaxxer.hikari.util.DriverDataSource.<init>(DriverDataSource.java:110) ~[HikariCP-5.0.1.jar!/:na]
tool-rental-app  |      at com.zaxxer.hikari.pool.PoolBase.initializeDataSource(PoolBase.java:326) ~[HikariCP-5.0.1.jar!/:na]
tool-rental-app  |      at com.zaxxer.hikari.pool.PoolBase.<init>(PoolBase.java:112) ~[HikariCP-5.0.1.jar!/:na]
tool-rental-app  |      at com.zaxxer.hikari.pool.HikariPool.<init>(HikariPool.java:93) ~[HikariCP-5.0.1.jar!/:na]
tool-rental-app  |      at com.zaxxer.hikari.HikariDataSource.getConnection(HikariDataSource.java:112) ~[HikariCP-5.0.1.jar!/:na]
tool-rental-app  |      at org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl.getConnection(DatasourceConnectionProviderImpl.java:122) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess.obtainConnection(JdbcEnvironmentInitiator.java:424) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.resource.transaction.backend.jdbc.internal.DdlTransactionIsolatorNonJtaImpl.getIsolatedConnection(DdlTransactionIsolatorNonJtaImpl.java:46) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.getIsolatedConnection(GenerationTargetToDatabase.java:60) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.jdbcStatement(GenerationTargetToDatabase.java:112) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:79) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.tool.schema.internal.Helper.applySqlString(Helper.java:233) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.tool.schema.internal.Helper.applySqlStrings(Helper.java:217) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.tool.schema.internal.SchemaDropperImpl.applyConstraintDropping(SchemaDropperImpl.java:470) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final] 
tool-rental-app  |      at org.hibernate.tool.schema.internal.SchemaDropperImpl.dropConstraintsTablesSequences(SchemaDropperImpl.java:242) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.tool.schema.internal.SchemaDropperImpl.dropFromMetadata(SchemaDropperImpl.java:215) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]        
tool-rental-app  |      at org.hibernate.tool.schema.internal.SchemaDropperImpl.performDrop(SchemaDropperImpl.java:185) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:155) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.tool.schema.internal.SchemaDropperImpl.doDrop(SchemaDropperImpl.java:115) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:244) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.lambda$process$5(SchemaManagementToolCoordinator.java:145) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at java.base/java.util.HashMap.forEach(Unknown Source) ~[na:na]
tool-rental-app  |      at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:142) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.boot.internal.SessionFactoryObserverForSchemaExport.sessionFactoryCreated(SessionFactoryObserverForSchemaExport.java:37) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.internal.SessionFactoryObserverChain.sessionFactoryCreated(SessionFactoryObserverChain.java:35) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:295) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:450) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1507) ~[hibernate-core-6.3.1.Final.jar!/:6.3.1.Final]
tool-rental-app  |      at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:75) ~[spring-orm-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:376) ~[spring-orm-6.1.1.jar!/:6.1.1]
tool-rental-app  |      at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:409) ~[spring-orm-6.1.1.jar!/:6.1.1]
tool-rental-app  |      ... 27 common frames omitted
tool-rental-app  |
```

### Příčina a oprava

**Chyba:** `Driver org.h2.Driver claims to not accept jdbcUrl, jdbc:postgresql://db:5432/rental`

**Příčina:** V docker-compose se nastavuje PostgreSQL URL, ale z `application.yml` se načítá H2 driver (`driver-class-name: org.h2.Driver`). Spring tedy používá H2 driver na PostgreSQL URL → selže.

**Oprava:** V `docker-compose.yml` u služby `app` byla přidána proměnná:
`SPRING_DATASOURCE_DRIVER_CLASS_NAME: org.postgresql.Driver`
Pak znovu: `docker compose down -v` a `docker compose up --build`.

---

## Další kroky (po opravě driveru)

1. **Ověř, že máš soubor `.env`** v kořeni projektu s heslem pro PostgreSQL, např.:
   ```env
   POSTGRES_PASSWORD=tvoje_heslo
   ```
   (Pokud je tam `.env.example`, zkopíruj ho na `.env` a doplň heslo.)

2. **Znovu spusť kontejnery** (po předchozí opravě):
   ```powershell
   docker compose down -v
   docker compose up --build
   ```
   Aplikace by měla naběhnout a na konci by měl být běžící Tomcat na portu 8080.

3. **Ověř běh aplikace:**
   - V prohlížeči nebo Postmanu: `http://localhost:8080` (popř. konkrétní endpointy z API).
   - Případně: `docker compose logs app` — na konci by neměly být chyby, mělo by být „Started ToolRentalApplication“.

4. **Pokud vše běží:** můžeš sem do dokumentu vložit krátký úspěšný výstup z `docker compose logs app` (řádky po startu) pro budoucí referenci.

5. **Pokud se objeví další chyba:** vlož výstup sem do sekce „Další chyby“ a uveď příkaz, který jsi spouštěl.

---

## Další chyby

Vkládej další výstupy sem; u každého uveď krátce, co jsi spouštěl (příkaz).
Spustil jsem aplikace pres Inteliji a spadlo to vysledek: Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2026-03-11T15:25:03.605+01:00 ERROR 13808 --- [tool-rental] [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSourceScriptDatabaseInitializer' defined in class path resource [org/springframework/boot/autoconfigure/sql/init/DataSourceInitializationConfiguration.class]: Failed to execute SQL script statement #1 of file [C:\Users\Levur\BTDD\KST-BTDD---Programov-n-zen-testy\target\classes\data.sql]: INSERT INTO users (name, "role") VALUES ('Jan Novak', 'CUSTOMER')
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1775) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:601) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:523) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:325) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:323) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:199) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:312) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:199) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:973) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:946) ~[spring-context-6.1.1.jar:6.1.1]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:616) ~[spring-context-6.1.1.jar:6.1.1]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:753) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:455) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:323) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1342) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1331) ~[spring-boot-3.2.0.jar:3.2.0]
	at cz.kst.btdd.ToolRentalApplication.main(ToolRentalApplication.java:10) ~[classes/:na]
Caused by: org.springframework.jdbc.datasource.init.ScriptStatementFailedException: Failed to execute SQL script statement #1 of file [C:\Users\Levur\BTDD\KST-BTDD---Programov-n-zen-testy\target\classes\data.sql]: INSERT INTO users (name, "role") VALUES ('Jan Novak', 'CUSTOMER')
	at org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript(ScriptUtils.java:282) ~[spring-jdbc-6.1.1.jar:6.1.1]
	at org.springframework.jdbc.datasource.init.ResourceDatabasePopulator.populate(ResourceDatabasePopulator.java:254) ~[spring-jdbc-6.1.1.jar:6.1.1]
	at org.springframework.jdbc.datasource.init.DatabasePopulatorUtils.execute(DatabasePopulatorUtils.java:54) ~[spring-jdbc-6.1.1.jar:6.1.1]
	at org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer.runScripts(DataSourceScriptDatabaseInitializer.java:87) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer.runScripts(AbstractScriptDatabaseInitializer.java:146) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer.applyScripts(AbstractScriptDatabaseInitializer.java:108) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer.applyDataScripts(AbstractScriptDatabaseInitializer.java:102) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer.initializeDatabase(AbstractScriptDatabaseInitializer.java:77) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer.afterPropertiesSet(AbstractScriptDatabaseInitializer.java:66) ~[spring-boot-3.2.0.jar:3.2.0]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1822) ~[spring-beans-6.1.1.jar:6.1.1]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1771) ~[spring-beans-6.1.1.jar:6.1.1]
	... 18 common frames omitted
Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException: Column "role" not found; SQL statement:
INSERT INTO users (name, "role") VALUES ('Jan Novak', 'CUSTOMER') [42122-224]
	at org.h2.message.DbException.getJdbcSQLException(DbException.java:514) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.message.DbException.getJdbcSQLException(DbException.java:489) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.message.DbException.get(DbException.java:223) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.message.DbException.get(DbException.java:199) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.table.Table.getColumn(Table.java:759) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.command.Parser.parseColumn(Parser.java:1190) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.command.Parser.parseColumnList(Parser.java:1175) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.command.Parser.parseInsert(Parser.java:1549) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.command.Parser.parsePrepared(Parser.java:719) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.command.Parser.parse(Parser.java:592) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.command.Parser.parse(Parser.java:564) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.command.Parser.prepareCommand(Parser.java:483) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.engine.SessionLocal.prepareLocal(SessionLocal.java:639) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.engine.SessionLocal.prepareCommand(SessionLocal.java:559) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.jdbc.JdbcConnection.prepareCommand(JdbcConnection.java:1166) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.jdbc.JdbcStatement.executeInternal(JdbcStatement.java:245) ~[h2-2.2.224.jar:2.2.224]
	at org.h2.jdbc.JdbcStatement.execute(JdbcStatement.java:231) ~[h2-2.2.224.jar:2.2.224]
	at com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94) ~[HikariCP-5.0.1.jar:na]
	at com.zaxxer.hikari.pool.HikariProxyStatement.execute(HikariProxyStatement.java) ~[HikariCP-5.0.1.jar:na]
	at org.springframework.jdbc.datasource.init.ScriptUtils.executeSqlScript(ScriptUtils.java:261) ~[spring-jdbc-6.1.1.jar:6.1.1]
	... 28 common frames omitted


Process finished with exit code 1

### Příčina a oprava (Column "role" not found)

**Co se spouštělo:** Aplikace z IntelliJ (default profil → H2 databáze).

**Chyba:** `Failed to execute SQL script statement #1 of file [data.sql]: INSERT INTO users (name, "role") VALUES ...`  
**Příčina:** V H2 se neuvozovaná jména sloupců ukládají velkými písmeny (ROLE). Skript v `data.sql` používal `"role"` (uvozovky = přesná velikost), takže H2 hledal sloupec `role` a našel jen `ROLE` → „Column \"role\" not found“.

**Oprava:**  
- V entitě `EntityUser` byl sloupec mapován na ne-rezervované jméno: `@Column(name = "user_role", ...)`.  
- V `data.sql` se místo `"role"` používá `user_role`.  
Stejný skript pak funguje pro H2 (IntelliJ) i PostgreSQL (Docker).

---

