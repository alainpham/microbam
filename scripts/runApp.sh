#/bin/bash
ps -aux | grep event-collector | grep -v grep | awk '{print $2}' | xargs kill -KILL
ps -aux | grep trade-service | grep -v grep | awk '{print $2}' | xargs kill -KILL
ps -aux | grep dashapp | grep -v grep | awk '{print $2}' | xargs kill -KILL

basedir=`dirname $0`
cd $basedir
rm log.txt
cd ./../services/event-collector && java -jar target/event-collector*.jar>>target/log.txt  &
cd $basedir
cd ./../services/trade-service && java -jar target/trade-service*.jar>>target/log.txt  &
cd $basedir
cd ./../services/dashapp && java -jar target/dashapp*.jar>>target/log.txt  &
cd $basedir

echo "##### App Started"
echo "View app at this URL : http://localhost:8012 "
