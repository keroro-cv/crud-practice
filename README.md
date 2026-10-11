# Spring Boot CRUD Backend Practice

## 1. 프로젝트 소개

Spring Boot를 이용해 구현한 학생 관리 REST API 서버입니다.

학생 정보를 등록(Create), 조회(Read), 수정(Update), 삭제(Delete)하는 CRUD 기능을 구현했습니다.

데이터베이스 대신 Java의 `HashMap`을 사용하여 학생 정보를 메모리에 저장합니다.

## 2. 개발 환경

- Java 21
- Spring Boot 4.1.1
- Gradle
- IntelliJ IDEA
- Postman

## 3. 주요 기능 및 API

| HTTP Method | Endpoint | 기능 |
|---|---|---|
| GET | `/students` | 전체 학생 조회 |
| GET | `/students/{id}` | 특정 학생 조회 |
| POST | `/students` | 학생 한 명 등록 |
| POST | `/students/batch` | 여러 학생 일괄 등록 |
| PUT | `/students/{id}` | 학생 이름 및 학년 수정 |
| PUT | `/students/{id}/grade` | 학생 학년 수정 |
| DELETE | `/students/{id}` | 특정 학생 삭제 |
| DELETE | `/students` | 전체 학생 삭제 |

## 4. HTTP 상태 코드

다음과 같은 HTTP 응답 코드를 구현하고 Postman으로 테스트했습니다.

| 상태 코드 | 의미 | 사용 예시 |
|---|---|---|
| 200 OK | 요청 성공 | 학생 조회, 수정, 삭제 |
| 201 Created | 데이터 생성 성공 | 학생 등록 |
| 400 Bad Request | 잘못된 요청 | 필수 학생 정보 누락 |
| 404 Not Found | 데이터 없음 | 존재하지 않는 학생 조회 |
| 500 Internal Server Error | 서버 내부 오류 | 테스트용 서버 오류 |
| 503 Service Unavailable | 서비스 이용 불가 | 테스트용 서버 점검 상태 |

500과 503 상태 코드는 테스트 전용 API를 통해 재현했습니다.

## 5. JSON 응답 형식

정상 응답 예시:

```json
{
  "status": "success",
  "data": {
    "id": 1001,
    "name": "철수",
    "grade": 2
  }
}
```

오류 응답 예시:

```json
{
  "status": "error",
  "data": "해당 학생이 없습니다."
}
```

`ResponseEntity`를 사용해 HTTP 상태 코드를 지정하고, `GlobalExceptionHandler`를 통해 잘못된 JSON 요청과 예상하지 못한 예외를 처리했습니다.

## 6. 미들웨어 구현

`HandlerInterceptor`를 구현한 `LoggingInterceptor`를 사용하여 요청과 응답 정보를 기록했습니다.

`WebConfig`에서 인터셉터를 등록하여 학생 관리 API에 적용했습니다.

실행 로그 예시:

```text
[REQUEST] GET /students
[RESPONSE] GET /students - 200
```

## 7. 프로젝트 실행 방법

1. 프로젝트를 IntelliJ IDEA에서 엽니다.
2. Java 21 환경을 확인합니다.
3. `CrudPracticeApplication.java`를 실행합니다.
4. 서버가 정상적으로 실행되면 Postman에서 API를 테스트합니다.

기본 서버 주소:

`http://localhost:8080`

## 8. 테스트

Postman을 사용하여 CRUD API와 HTTP 응답 코드를 테스트했습니다.

- GET, POST, PUT, DELETE 각각 2개 API
- 200, 201, 400, 404, 500, 503 상태 코드
- 미들웨어 요청·응답 로그
- 공통 예외 처리

## 9. 참고 사항

학생 데이터는 `HashMap`에 저장되므로 서버를 종료하거나 재시작하면 초기 데이터로 돌아갑니다.

`/students/test/error`와 `/students/test/maintenance`는 5xx 응답 코드 확인을 위한 테스트 전용 API입니다.