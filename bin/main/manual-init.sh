aws --endpoint-url=http://localhost:4566 sns create-topic --name test-order-topic

aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name test-order-transmission-queue
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name test-order-dispatch-queue

aws --endpoint-url=http://localhost:4566 sns subscribe --topic-arn arn:aws:sns:ap-northeast-2:000000000000:test-order-topic --protocol sqs --notification-endpoint arn:aws:sqs:ap-northeast-2:000000000000:test-order-transmission-queue
aws --endpoint-url=http://localhost:4566 sns subscribe --topic-arn arn:aws:sns:ap-northeast-2:000000000000:test-order-topic --protocol sqs --notification-endpoint arn:aws:sqs:ap-northeast-2:000000000000:test-order-dispatch-queue
