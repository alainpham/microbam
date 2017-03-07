#/bin/sh
ps -aux | grep event-collector | grep -v grep | awk '{print $2}' | xargs kill -KILL
ps -aux | grep trade-service | grep -v grep | awk '{print $2}' | xargs kill -KILL
ps -aux | grep dashapp | grep -v grep | awk '{print $2}' | xargs kill -KILL

rm log.txt
services/event-collector/target/hawt-app/bin/run.sh>>log.txt  &
services/trade-service/target/hawt-app/bin/run.sh>>log.txt &
services/dashapp/target/hawt-app/bin/run.sh>>log.txt &
