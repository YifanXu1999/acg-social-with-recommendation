[TOC]

# Headnews Java



## Service Layer

### User Service

Property

- application.name: user-service
- server.servlet.context-path: /user

Security

- Permit access to "/actuator/**", "/authenticate/**
- Authenticated by JWT tokens

Controller

-  /authenticate

  - post /signin: authenticate user and return back JWT token

  - post /signup: register user and return back JWT token

    




## Gateway Layer

### User Gateway



## Commons

- Config

  - Swagger

- Exception

  - ExceptionControllerAdvice

- Enum

  - AppHttpCodeEnum

- Result

  - ResponseResult

  

## Utils

- JwtUtilService: provides JwtToken validation, generation and claim extraction



# Consul



# Databse



