# Spring Framework Examples
An educational project with Spring Framework examples. Used for lectures at courses.

## Related links
- [Spring Framework Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/)
- [Spring Core Technologies](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html)
- [Spring Guides](https://spring.io/guides)
- [Spring Quickstart Guide](https://spring.io/quickstart)
- [Properties with Spring and Spring Boot](https://www.baeldung.com/properties-with-spring)
- [An Intro to the Spring DispatcherServlet](https://www.baeldung.com/spring-dispatcherservlet)
- [Design Pattern - Front Controller Pattern](https://www.tutorialspoint.com/design_pattern/front_controller_pattern.htm)
- [Introduction to Using Thymeleaf in Spring](https://www.baeldung.com/thymeleaf-in-spring-mvc)
- [Servlet Filter and Handler Interceptor](https://medium.com/techno101/servlet-filter-and-handler-interceptor-spring-boot-implementation-b58d397d9dbd)
- [Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
- [Spring 5, Embedded Tomcat 8, and Gradle: a Quick Tutorial](https://auth0.com/blog/spring-5-embedded-tomcat-8-gradle-tutorial/)
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)

## Spring Security 6 Migration links
- [Migrating to 6.0](https://docs.spring.io/spring-security/reference/migration/index.html)
- [Spring Security without the WebSecurityConfigurerAdapter](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)

## Important information
- The module [example-17-authorization](example-17-authorization) has an issue https://github.com/vrudas/spring-framework-examples/issues/101 that was caused because of update to Spring Security 6

## Example 21 - JWT Instructions

Please note that IntelliJ IDEA [HTTP Client](https://blog.jetbrains.com/idea/2020/09/at-your-request-use-the-http-client-in-intellij-idea-for-spring-boot-restful-web-services/) was used to perform requests in code snippets

Please follow the steps to perform a demo of how to get a JWT token for an existing user:

- Perform login action
```HTTP request
POST http://localhost:8080/login?username=user&password=user
Accept: application/json
```

- Extract the generated Bearer token from a response header `Authorization: Bearer <token>`
```
HTTP/1.1 200
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjc4ODM2OTY1fQ.Afagk8no-r2kUiDOdtjWMT06gYPHkrhCoOSoK5_X6k8BC8Lr6k5rB-9gyoE72-lkd0rx1sEPET-3Uf7KP-7BrQ
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 0
Date: Tue, 14 Mar 2023 23:35:05 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>
```

- Use the generated Bearer token to perform the call to an endpoint by providing the `Authorization: Bearer <token>` header
```HTTP request
GET http://localhost:8080/users/me
Accept: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNjc4ODM1NjE5fQ.cRZ1ob4XZfG5RnU0jl2kdPihc9Ln-BlEOe7hbuwZJWp-UuQSGukI_57pWrBcdaCWPN-8luCF08YWU74tUErOFg
```
