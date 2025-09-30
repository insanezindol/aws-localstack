echo "suspending initialization for another 5 seconds"
sleep 5

echo "creating topics : creates a SNS topic"
awslocal sns create-topic --name test-order-topic

echo "creating queues : creates a SQS queue"
awslocal sqs create-queue --queue-name test-order-transmission-queue --attributes ReceiveMessageWaitTimeSeconds=20
awslocal sqs create-queue --queue-name test-order-dispatch-queue --attributes ReceiveMessageWaitTimeSeconds=20

awslocal sqs create-queue --queue-name test-order-transmission-raw-queue --attributes ReceiveMessageWaitTimeSeconds=20
awslocal sqs create-queue --queue-name test-order-dispatch-raw-queue --attributes ReceiveMessageWaitTimeSeconds=20

echo "creating subscriptions : subscribes a queue to a topic"
awslocal sns subscribe --protocol sqs --topic-arn arn:aws:sns:ap-northeast-2:000000000000:test-order-topic --notification-endpoint http://localhost:4576/queue/test-order-transmission-queue
awslocal sns subscribe --protocol sqs --topic-arn arn:aws:sns:ap-northeast-2:000000000000:test-order-topic --notification-endpoint http://localhost:4576/queue/test-order-dispatch-queue

awslocal sns subscribe --protocol sqs --topic-arn arn:aws:sns:ap-northeast-2:000000000000:test-order-topic --notification-endpoint http://localhost:4576/queue/test-order-transmission-raw-queue --attributes RawMessageDelivery=true
awslocal sns subscribe --protocol sqs --topic-arn arn:aws:sns:ap-northeast-2:000000000000:test-order-topic --notification-endpoint http://localhost:4576/queue/test-order-dispatch-raw-queue --attributes RawMessageDelivery=true

echo "initialization finished successfully"
