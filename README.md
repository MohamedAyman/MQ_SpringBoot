# MQ_SpringBoot

run IBM MQ queue messaging 

first run this docker to initialize the MQ

docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --publish 1414:1414 --publish 9443:9443 --detach ibmcom/mq
