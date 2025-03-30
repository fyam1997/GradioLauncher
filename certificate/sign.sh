# for Windows, run this file with git bash

if [ ! -f "passphrase.txt" ]; then
  read -r -p "passphrase: " passphrase
  echo "$passphrase" > "passphrase.txt"
fi

if [ ! -f "publish_token.txt" ]; then
  read -r -p "publish token: " publish_token
  echo "$publish_token" > "publish_token.txt"
fi

openssl genpkey\
  -aes-256-cbc\
  -pass file:passphrase.txt\
  -algorithm RSA\
  -out private_encrypted.pem\
  -pkeyopt rsa_keygen_bits:4096

openssl rsa\
  -in private_encrypted.pem\
  -passin file:passphrase.txt\
  -out private.pem

openssl req\
  -key private.pem\
  -new\
  -x509\
  -days 365\
  -out chain.crt
