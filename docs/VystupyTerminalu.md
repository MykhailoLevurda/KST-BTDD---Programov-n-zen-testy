PS C:\Users\Levur\BTDD\KST-BTDD---Programov-n-zen-testy> kubectl get pods
NAME                            READY   STATUS    RESTARTS   AGE
tool-rental-6dc75bbcd7-gs8w9    1/1     Running   0          2m24s
tool-rental-db-8bf4ff65-z2dhw   1/1     Running   0          19m
PS C:\Users\Levur\BTDD\KST-BTDD---Programov-n-zen-testy> kubectl logs -l app=tool-rental
2026-03-13T19:49:25.238Z  WARN 1 --- [tool-rental] [           main] org.hibernate.orm.deprecation            : HHH90000025: PostgreSQLDialect does not need to be specified explicitly using 'hibernate.dialect' (remove the property setting and it will be selected by default)
2026-03-13T19:49:36.742Z  INFO 1 --- [tool-rental] [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2026-03-13T19:49:37.247Z  INFO 1 --- [tool-rental] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2026-03-13T19:49:40.250Z  INFO 1 --- [tool-rental] [           main] o.s.d.j.r.query.QueryEnhancerFactory     : Hibernate is in classpath; If applicable, HQL parser will be used.  
2026-03-13T19:49:46.538Z  WARN 1 --- [tool-rental] [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2026-03-13T19:49:50.953Z  INFO 1 --- [tool-rental] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
2026-03-13T19:49:51.141Z  INFO 1 --- [tool-rental] [           main] cz.kst.btdd.ToolRentalApplication        : Started ToolRentalApplication in 62.199 seconds (process running for 67.876)
2026-03-13T19:49:55.049Z  INFO 1 --- [tool-rental] [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2026-03-13T19:49:55.050Z  INFO 1 --- [tool-rental] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2026-03-13T19:49:55.129Z  INFO 1 --- [tool-rental] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 78 ms
PS C:\Users\Levur\BTDD\KST-BTDD---Programov-n-zen-testy>
