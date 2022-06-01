# spring boot project for aws localstack

### Guides

docker setup : init topic, queue in loccalstack

* docker-compose.yml
* localstack-setup.sh or manual-init.sh

### API TEST CASE 1
* POST http://localhost:8080/sns/publish
* Content-Type: application/json

`{
"data": {
"orderId": "test-order-id-001",
"owner": "jinhyung.lee",
"eventType": "ORDER_CREATED"
}
}`

### API TEST CASE 2
* POST http://localhost:8080/sns/publish
* Content-Type: application/json

`{
"data": {
"orderId": "test-order-id-100",
"owner": "jinhyung.lee",
"eventType": "ORDER_CANCELLED"
}
}`
