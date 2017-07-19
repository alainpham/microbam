#/bin/bash

cd ./../services/event-collector && java -jar target/event-collector*.jar>./../../scripts/event-collector.log  &
cd ./../services/trade-service && java -jar target/trade-service*.jar>./../../scripts/trade-service.log  &
cd ./../services/dashapp && java -jar target/dashapp*.jar>./../../scripts/dashapp.log  &

echo "Booting up.."
while [ "$(curl -s 'http://localhost:8086/health')" == "" ]; do
  echo -n '.'
  sleep 1
done
echo ""
echo "event-collector is up"

while [ "$(curl -s 'http://localhost:8087/health')" == "" ]; do
  echo -n '.'
  sleep 1
done
echo "trade-service is up"

while [ "$(curl -s 'http://localhost:8088/health')" == "" ]; do
  echo -n '.'
  sleep 1
done
echo "dashapp is up"


echo "##### App Started"
echo "View app at this URL : http://localhost:8012"
echo "View logs : "
echo "tail -f event-collector.log"
echo "tail -f trade-service.log"
echo "tail -f dashapp.log"

