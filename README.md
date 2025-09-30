# AWS LocalStack SNS/SQS Integration Project

A Spring Boot application demonstrating AWS SNS (Simple Notification Service) and SQS (Simple Queue Service) integration using LocalStack for local development and testing.

## Overview

This project provides a complete example of:

-   Publishing messages to AWS SNS topics
-   Consuming messages from SQS queues
-   Event-driven architecture with order processing
-   Local AWS services simulation using LocalStack
-   Docker containerization for easy setup

## Architecture

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

## Features

-   **SNS Publishing**: REST API endpoints for publishing events to SNS topics
-   **SQS Consumption**: Automatic message consumption from SQS queues
-   **Event Types**: Support for ORDER_CREATED and ORDER_CANCELLED events
-   **Message Processing**: Both JSON wrapped and raw message delivery
-   **LocalStack Integration**: Complete local AWS environment simulation

## Prerequisites

-   Java 11 or higher
-   Docker and Docker Compose
-   Gradle (or use included Gradle Wrapper)

## Quick Start

### 1. Start LocalStack

```bash
docker-compose up -d
```

This will:

-   Start LocalStack container on port 4566
-   Automatically create SNS topics and SQS queues
-   Set up subscriptions between topics and queues

### 2. Run the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

### 3. Test the API

Use the included HTTP test cases in `test-case.http` or send requests manually:

## API Documentation

### Publish Event

**Endpoint**: `POST /sns/publish`  
**Content-Type**: `application/json`

**Request Body**:

```json
{
    "data": {
        "orderId": "test-order-id-001",
        "owner": "jinhyung.lee",
        "eventType": "ORDER_CREATED"
    }
}
```

**Response**: `200 OK` with body `"OK"`

### Supported Event Types

-   `ORDER_CREATED`: Triggers when a new order is created
-   `ORDER_CANCELLED`: Triggers when an order is cancelled

## Project Structure

```
├── src/main/java/com/example/awslocalstack/
│   ├── AwsLocalstackApplication.java      # Main Spring Boot application
│   ├── config/
│   │   ├── AwsSnsConfig.java             # SNS configuration
│   │   └── AwsSqsConfig.java             # SQS configuration
│   ├── controller/
│   │   └── SnsController.java            # REST API controller
│   ├── message/
│   │   ├── SimpleMessageConsumer.java    # SQS message consumer
│   │   └── SimpleMessageProducer.java    # Message producer
│   ├── model/
│   │   ├── Event.java                    # Event model
│   │   ├── EventData.java               # Event data model
│   │   └── EventType.java               # Event type enum
│   └── service/
│       └── SnsService.java              # SNS service layer
├── src/main/resources/
│   ├── application.yml                   # Application configuration
│   ├── docker-compose.yml              # Docker services setup
│   ├── localstack-setup.sh             # LocalStack initialization
│   ├── manual-init.sh                   # Manual setup script
│   └── test-case.http                   # HTTP test cases
└── build.gradle                         # Gradle build configuration
```

## Configuration

### AWS Configuration (`application.yml`)

```yaml
aws:
    endpoint: http://localhost:4566
    accessKey: foo
    secretKey: bar
    region: ap-northeast-2
```

### LocalStack Services

The following AWS services are configured in LocalStack:

-   **SNS Topic**: `test-order-topic`
-   **SQS Queues**:
    -   `test-order-transmission-queue`
    -   `test-order-dispatch-queue`
    -   `test-order-transmission-raw-queue`
    -   `test-order-dispatch-raw-queue`

## Development

### Building the Project

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

### Manual LocalStack Setup

If you prefer to set up LocalStack manually:

```bash
./src/main/resources/manual-init.sh
```

## Testing Examples

### Test Case 1: Order Created Event

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

### Test Case 2: Order Cancelled Event

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

## Dependencies

-   **Spring Boot 2.7.0**: Core framework
-   **Spring Cloud AWS Messaging 2.4.1**: AWS integration
-   **Amazon SQS Java Messaging Library**: SQS support
-   **Lombok**: Code generation
-   **LocalStack**: Local AWS services simulation

## Monitoring

You can monitor LocalStack services and queues by accessing:

-   LocalStack Dashboard: `http://localhost:4566`
-   Check queue contents using AWS CLI with LocalStack

## Troubleshooting

### Common Issues

1. **Port Conflicts**: Ensure ports 4566 and 8080 are available
2. **Docker Issues**: Verify Docker is running and accessible
3. **LocalStack Initialization**: Check container logs if setup fails

### Useful Commands

```bash
# Check LocalStack status
docker-compose ps

# View LocalStack logs
docker-compose logs localstack

# List SNS topics
awslocal sns list-topics --endpoint-url=http://localhost:4566

# List SQS queues
awslocal sqs list-queues --endpoint-url=http://localhost:4566
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is provided as an example for educational purposes.
