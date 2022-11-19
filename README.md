## Rotina de transações

Cada portador de cartão (cliente) possui uma conta com seus dados. A cada operação realizada pelo cliente uma transação
é criada e associada à sua respectiva conta. Cada transação possui um tipo (compra à vista, compra parcelada, saque ou
pagamento), um valor e uma data de criação. Transações de tipo compra e saque são registradas com valor negativo,
enquanto transações de pagamento são registradas com valor positivo.

---

## Requisitos do ambiente

Para executar esse projeto no ambiente local é necessário.

- OpenJDK 14
- Docker
- Docker-compose
- Maven 3.6.3



---

#### RECURSOS

[IMAGEM NO DOCKER HUB)](https://hub.docker.com/r/marlonreis/challenge-transaction)

[GITHUB PIPELINE](https://github.com/MarlonReis/transaction-routine/actions/workflows/maven.yml)

---

## Construção do projeto

Para que esse projeto possa ser executado no ambiente será necessário executar os comando descrito a baixo:
**Construção da imagem docker da aplicação**

```bash
mvn spring-boot:build-image -DDockerHubUsername=docker-hub-username
```

**Rodando a aplicação usando docker**

```bash
docker-compose --env-file ./docker.env up -d
```

---

## Testes

Para esse projeto os testes de unidade, integração e e2e são executados juntos, com o mesmo comando.

```bash
mvn clean test
```

### Testes de mutação

Para os testes de mutação está sendo utilizado o plugin do **Pitest**. Essa estrategia de teste serve para nos auxiliar
a aumentar a qualidade dos nossos testes.

```bash
mvn test-compile org.pitest:pitest-maven:mutationCoverage
```

O resultado do teste fica na pasta

```
target/pit-reports/*/index.html
```

----

### OPERAÇÕES

##### CRIAÇÃO DE CONTA

Para criação de uma conta é necessário um CPF valido, que pode ser [gerado](https://www.4devs.com.br/gerador_de_cpf)
online.

```bash
curl --location --request POST 'http://localhost:8080/v1/accounts' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName":"{NOME DO CLIENTE}",
    "documentCpf":"{DOCUMENTO CPF COM OU SEM FORMATAÇÃO}",
    "creditLimit": {VALOR POSITIVO}
}'
```

**Respostas**

Status code 200

```json
{
  "accountId": "2725fad8-8bbb-412a-a7bd-c38a777c9e83",
  "agency": "0031",
  "accountNumber": "2029838614",
  "codeBank": "758",
  "customerName": "Fulano de Tals"
}
```

Status code 422

```json
{
  "code": "DOCUMENT_IS_BEING_USED_BY_ANOTHER_ACCOUNT",
  "message": "This document is being used by another customer and another account!",
  "date": "2022-11-15T15:09:05.225377"
}
```

**TABELA DE RESPOSTAS**

| DESCRIÇÃO                                 | CONSTANTE                                 | CÓDIGO |
| :---------------------------------------- | ----------------------------------------- | ------ |
| Sucesso                                   |                                           | 200    |
| Documento sendo utilizado por outra conta | DOCUMENT_IS_BEING_USED_BY_ANOTHER_ACCOUNT | 422    |
| Documento invalido                        | DOCUMENT_IS_INVALID                       | 422    |
| Atributo obrigatório não enviado          | INVALID_REQUEST                           | 400    |
| Erro interno do servidor                  | INTERNAL_ERROR_SERVER                     | 500    |
| Limite de credito não pode ser negativo   | CREDIT_LIMIT_CANNOT_BE_NEGATIVE           | 422    |


#### BUSCA DADOS DA CONTA POR ID

```bash
curl --location --request GET 'http://localhost:8080/v1/accounts/{ID DA CONTA}'
```

**Respostas**

Status code 200

```json
{
    "accountId": "c82fbed2-ffda-4b8f-b146-ecc67585fdb1",
    "documentNumber": "88212301044"
}
```

Status code 404

```json
{
    "code": "REGISTER_NOT_FOUND",
    "message": "Account register cannot be found!",
    "date": "2022-11-15T15:12:30.391686"
}
```

**TABELA DE RESPOSTAS**

| DESCRIÇÃO                                        | CONSTANTE                 | CÓDIGO |
| :----------------------------------------------- | ------------------------- | ------ |
| Sucesso                                          |                           | 200    |
| Id no formato que não caracteriza um UUID valido | IDENTITY_VALUE_IS_INVALID | 422    |
| Registro não encontrado                          | REGISTER_NOT_FOUND        | 404    |
| Erro interno do servidor                         | INTERNAL_ERROR_SERVER     | 500    |


#### SALVA TRANSAÇÃO

```bash
curl --location --request POST 'http://localhost:8080/v1/transactions' \
--header 'Content-Type: application/json' \
--data-raw '{
    "accountId": "a1ba8066-8355-44b4-87ac-a0c2e04749da",
    "operationTypeId": 3,
    "amount": 1.0
}'
```

**Respostas**

Status code 200

```
Não retorna dados
```

Status code 422

```json
{
  "code": "PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA",
  "message": "Purchase transaction value receive negative value!",
  "date": "2022-11-15T15:16:53.228050"
}
```

**TABELA DE RESPOSTAS**

| DESCRIÇÃO                                                    | CONSTANTE                                  | CÓDIGO |
| :----------------------------------------------------------- | ------------------------------------------ | ------ |
| Sucesso                                                      |                                            | 200    |
| Id no formato que não caracteriza um UUID valido             | IDENTITY_VALUE_IS_INVALID                  | 422    |
| Registro não encontrado                                      | REGISTER_NOT_FOUND                         | 404    |
| Erro interno do servidor                                     | INTERNAL_ERROR_SERVER                      | 500    |
| Recebe valor invalido para operação de compra. Valores negativo e zero são considerado valores inválidos. | PURCHASE_TRANSACTION_RECEIVED_INVALID_DATA | 422    |
| Recebe valor invalido para operação de saque                 | WITHDRAW_MONEY_RECEIVED_INVALID_VALUE      | 422    |
| Recebe valor invalido para operação de pagamento             | PAYMENT_RECEIVED_INVALID_VALUE             | 422    |
| Operação descronhecida                                       | UNKNOWN_TRANSACTION                        | 422    |
| Quando a conta não possui saldo nem credito o suficiente     | ACCOUNT_LIMIT_IS_INSUFFICIENT              | 422    |

#### SAÚDE DA APLICAÇÃO

Esse recurso é utilizado pelo serviço de balanceamento de carga para monitorar a saúde da aplicação.

```bash
curl --location --request GET 'http://localhost:8080/actuator/health/custom'
```

**Resposta**

```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP"
    },
    "diskSpace": {
      "status": "UP"
    },
    "ping": {
      "status": "UP"
    }
  }
}
```



