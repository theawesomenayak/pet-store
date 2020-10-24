aws --endpoint-url "http://localhost:4566" dynamodb create-table \
    --table-name pet-store \
    --attribute-definitions AttributeName=id,AttributeType=S \
    --key-schema AttributeName=id,KeyType=HASH \
    --billing-mode PAY_PER_REQUEST
