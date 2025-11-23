kafka-run-class.sh kafka.tools.GetOffsetShell \
--bootstrap-server pkc-l6wr6.europe-west2.gcp.confluent.cloud:9092 \
--command-config kafka.properties \
--topic reference-fps-sort-codes | awk -F  ":" '{sum += $3} END {print sum}'