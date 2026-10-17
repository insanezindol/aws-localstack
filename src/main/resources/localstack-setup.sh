#!/bin/bash
# LocalStack ready hook (/etc/localstack/init/ready.d) - LocalStack이 준비된 뒤 실행됨

TOPIC_ARN=arn:aws:sns:ap-northeast-2:000000000000:test-order-topic
QUEUE_ARN_PREFIX=arn:aws:sqs:ap-northeast-2:000000000000

echo "creating topics : creates a SNS topic"
awslocal sns create-topic --name test-order-topic

echo "creating queues : creates a SQS queue"
awslocal sqs create-queue --queue-name test-order-transmission-queue --attributes ReceiveMessageWaitTimeSeconds=20
awslocal sqs create-queue --queue-name test-order-dispatch-queue --attributes ReceiveMessageWaitTimeSeconds=20

awslocal sqs create-queue --queue-name test-order-transmission-raw-queue --attributes ReceiveMessageWaitTimeSeconds=20
awslocal sqs create-queue --queue-name test-order-dispatch-raw-queue --attributes ReceiveMessageWaitTimeSeconds=20

echo "creating subscriptions : subscribes a queue to a topic"
awslocal sns subscribe --protocol sqs --topic-arn $TOPIC_ARN --notification-endpoint $QUEUE_ARN_PREFIX:test-order-transmission-queue
awslocal sns subscribe --protocol sqs --topic-arn $TOPIC_ARN --notification-endpoint $QUEUE_ARN_PREFIX:test-order-dispatch-queue

awslocal sns subscribe --protocol sqs --topic-arn $TOPIC_ARN --notification-endpoint $QUEUE_ARN_PREFIX:test-order-transmission-raw-queue --attributes RawMessageDelivery=true
awslocal sns subscribe --protocol sqs --topic-arn $TOPIC_ARN --notification-endpoint $QUEUE_ARN_PREFIX:test-order-dispatch-raw-queue --attributes RawMessageDelivery=true

echo "initialization finished successfully"
