spring-rest-template
====================
 
Spring Rest Template is a template for building JSON based RESTful services(to a degree). It contains Hibernate, Spring MVC, Spring Security, Jackson JSON parser integrated... Just ready to go. Maven is used for building and dependency management.

Hopefully no more "1h development 46543573h configuration" problems for early phases in projects.

Setup
=====

**Create DB**

    $  sudo -u postgres createuser -D -A -P dede
    [sudo] password for destan: 
    Enter password for new role: 
    Enter it again: 
    Shall the new role be allowed to create more new roles? (y/n) n
    $ sudo -u postgres createdb -O dede dededb

**Give JVM parameter for logs**

    -Dlog4j.logFolder="/home/destan/Desktop/logs"

Useful stuff
=====

**Login credentials**

    Default username/password pair is admin/admin

**URLs to check out**

    (assuming server is at localhost:8080)
    http://localhost:8080/spring-template/showcase/page
    http://localhost:8080/spring-template/showcase/
    http://localhost:8080/spring-template/ (login)
    http://localhost:8080/spring-template/showcase/exception
    
