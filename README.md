# AWS LocalStack SNS/SQS 통합 프로젝트

LocalStack을 사용하여 로컬 개발 및 테스트를 위한 AWS SNS(Simple Notification Service)와 SQS(Simple Queue Service) 통합을 보여주는 Spring Boot 애플리케이션입니다.

## 개요

이 프로젝트는 다음의 완전한 예제를 제공합니다:

-   AWS SNS 토픽에 메시지 발행
-   SQS 큐에서 메시지 소비
-   주문 처리를 통한 이벤트 기반 아키텍처
-   LocalStack을 사용한 로컬 AWS 서비스 시뮬레이션
-   쉬운 설정을 위한 Docker 컨테이너화

## 아키텍처

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Spring Boot   │───▶│   SNS Topic     │───▶│   SQS Queues    │
│   Application   │    │                 │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                               │                       │
                               │                       ├─ transmission-queue
                               │                       ├─ dispatch-queue
                               │                       ├─ transmission-raw-queue
                               └───────────────────────└─ dispatch-raw-queue
```

## 기능

-   **SNS 발행**: SNS 토픽에 이벤트를 발행하는 REST API 엔드포인트
-   **SQS 소비**: SQS 큐에서 자동으로 메시지 소비
-   **이벤트 타입**: ORDER_CREATED 및 ORDER_CANCELLED 이벤트 지원
-   **메시지 처리**: JSON 래핑 및 원시 메시지 전달 모두 지원
-   **LocalStack 통합**: 완전한 로컬 AWS 환경 시뮬레이션

## 필수 조건

-   Java 11 이상
-   Docker 및 Docker Compose
-   Gradle (또는 포함된 Gradle Wrapper 사용)

## 빠른 시작

### 1. LocalStack 시작

```bash
docker-compose up -d
```

이 명령은 다음을 수행합니다:

-   포트 4566에서 LocalStack 컨테이너 시작
-   SNS 토픽과 SQS 큐 자동 생성
-   토픽과 큐 간의 구독 설정

### 2. 애플리케이션 실행

```bash
./gradlew bootRun
```

애플리케이션이 `http://localhost:8080`에서 시작됩니다.

### 3. API 테스트

`test-case.http`에 포함된 HTTP 테스트 케이스를 사용하거나 수동으로 요청을 보내세요:

## API 문서

### 이벤트 발행

**엔드포인트**: `POST /sns/publish`  
**Content-Type**: `application/json`

**요청 본문**:

```json
{
    "data": {
        "orderId": "test-order-id-001",
        "owner": "jinhyung.lee",
        "eventType": "ORDER_CREATED"
    }
}
```

**응답**: `200 OK`와 함께 `"OK"` 본문

### 지원되는 이벤트 타입

-   `ORDER_CREATED`: 새 주문이 생성될 때 트리거
-   `ORDER_CANCELLED`: 주문이 취소될 때 트리거

## 프로젝트 구조

```
├── src/main/java/com/example/awslocalstack/
│   ├── AwsLocalstackApplication.java      # 메인 Spring Boot 애플리케이션
│   ├── config/
│   │   ├── AwsSnsConfig.java             # SNS 설정
│   │   └── AwsSqsConfig.java             # SQS 설정
│   ├── controller/
│   │   └── SnsController.java            # REST API 컨트롤러
│   ├── message/
│   │   ├── SimpleMessageConsumer.java    # SQS 메시지 소비자
│   │   └── SimpleMessageProducer.java    # 메시지 생산자
│   ├── model/
│   │   ├── Event.java                    # 이벤트 모델
│   │   ├── EventData.java               # 이벤트 데이터 모델
│   │   └── EventType.java               # 이벤트 타입 열거형
│   └── service/
│       └── SnsService.java              # SNS 서비스 계층
├── src/main/resources/
│   ├── application.yml                   # 애플리케이션 설정
│   ├── docker-compose.yml              # Docker 서비스 설정
│   ├── localstack-setup.sh             # LocalStack 초기화
│   ├── manual-init.sh                   # 수동 설정 스크립트
│   └── test-case.http                   # HTTP 테스트 케이스
└── build.gradle                         # Gradle 빌드 설정
```

## 설정

### AWS 설정 (`application.yml`)

```yaml
aws:
    endpoint: http://localhost:4566
    accessKey: foo
    secretKey: bar
    region: ap-northeast-2
```

### LocalStack 서비스

LocalStack에서 다음 AWS 서비스가 설정됩니다:

-   **SNS 토픽**: `test-order-topic`
-   **SQS 큐**:
    -   `test-order-transmission-queue`
    -   `test-order-dispatch-queue`
    -   `test-order-transmission-raw-queue`
    -   `test-order-dispatch-raw-queue`

## 개발

### 프로젝트 빌드

```bash
./gradlew build
```

### 테스트 실행

```bash
./gradlew test
```

### 수동 LocalStack 설정

LocalStack을 수동으로 설정하려면:

```bash
./src/main/resources/manual-init.sh
```

## 테스트 예제

### 테스트 케이스 1: 주문 생성 이벤트

```bash
curl -X POST http://localhost:8080/sns/publish \
  -H "Content-Type: application/json" \
  -d '{
    "data": {
      "orderId": "test-order-id-001",
      "owner": "jinhyung.lee",
      "eventType": "ORDER_CREATED"
    }
  }'
```

### 테스트 케이스 2: 주문 취소 이벤트

```bash
curl -X POST http://localhost:8080/sns/publish \
  -H "Content-Type: application/json" \
  -d '{
    "data": {
      "orderId": "test-order-id-100",
      "owner": "jinhyung.lee",
      "eventType": "ORDER_CANCELLED"
    }
  }'
```

## 의존성

-   **Spring Boot 2.7.0**: 핵심 프레임워크
-   **Spring Cloud AWS Messaging 2.4.1**: AWS 통합
-   **Amazon SQS Java Messaging Library**: SQS 지원
-   **Lombok**: 코드 생성
-   **LocalStack**: 로컬 AWS 서비스 시뮬레이션

## 모니터링

다음에 접근하여 LocalStack 서비스와 큐를 모니터링할 수 있습니다:

-   LocalStack 대시보드: `http://localhost:4566`
-   LocalStack과 함께 AWS CLI를 사용하여 큐 내용 확인

## 문제 해결

### 일반적인 문제

1. **포트 충돌**: 포트 4566과 8080이 사용 가능한지 확인
2. **Docker 문제**: Docker가 실행 중이고 접근 가능한지 확인
3. **LocalStack 초기화**: 설정이 실패할 경우 컨테이너 로그 확인

### 참고 명령어

```bash
# LocalStack 상태 확인
docker-compose ps

# LocalStack 로그 보기
docker-compose logs localstack

# SNS 토픽 목록
awslocal sns list-topics --endpoint-url=http://localhost:4566

# SQS 큐 목록
awslocal sqs list-queues --endpoint-url=http://localhost:4566
```
