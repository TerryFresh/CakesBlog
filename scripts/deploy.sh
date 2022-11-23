#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_ed25519 \
  out/LexBakerShop.jar \
  root@62.113.119.21:/home/root/

echo 'Restart server...'

ssh -i ~/.ssh/id_ed25519 root@62.113.119.21:/home/root/ << EOF

pgrep java |  | xargs kill -9
nohup java -jar LexBakerShop.jar > log.txt &

EOF

echo 'Bye'
