kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --delete --topic acs.request.transactions.topic
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --delete --topic mq.request.transactions.intermediate.topic
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --delete --topic mq.response.transactions.topic
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --delete --topic mq.request.transactions.topic
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --delete --topic acs.response.transactions.intermediate.topic
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --delete --topic acs.response.transactions.topic
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --delete --topic mq_request_response_transactions-request-state-store-changelog
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --delete --topic mq_request_response_transactions-sequence-state-store-changelog

kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --create --topic acs.request.transactions.topic --partitions 2
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --create --topic mq.request.transactions.intermediate.topic --partitions 2
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --create --topic mq.response.transactions.topic --partitions 2
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --create --topic mq.request.transactions.topic --partitions 2
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --create --topic acs.response.transactions.intermediate.topic --partitions 2
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --create --topic acs.response.transactions.topic --partitions 2
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --create --config cleanup.policy=compact --topic mq_request_response_transactions-request-state-store-changelog --partitions 2
kafka-topics --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --create --config cleanup.policy=compact --topic mq_request_response_transactions-sequence-state-store-changelog --partitions 2

kafka-console-consumer --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --topic acs.request.transactions.topic --from-beginning
kafka-console-consumer --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --topic mq.request.transactions.intermediate.topic --from-beginning
kafka-console-consumer --bootstrap-server kafka-0.kafka.confluent.svc.cluster.local:9092 --topic mq.request.transactions.topic --from-beginning