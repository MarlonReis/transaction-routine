## Rotina de transações

Cada portador de cartão (cliente) possui uma conta com seus dados. A cada operação realizada pelo cliente uma transação é criada e associada à sua respectiva conta. Cada transação possui um tipo (compra à vista, compra parcelada, saque ou pagamento), um valor e uma data de criação. Transações de tipo compra e saque são registradas com valor negativo, enquanto transações de pagamento são registradas com valor positivo.

---
## Requisitos do ambiente
Para executar esse projeto no ambiente local é necessário.

- OpenJDK 14
- Docker
- Docker-compose
- Maven 3.6.3
---
## Construção do projeto
Para que esse projeto possa ser executado no ambiente será necessário executar os comando descrito a baixo:
**Construção da imagem docker da aplicação**

    mvn spring-boot:build-image

**Rodando a aplicação usando docker**

     docker-compose --env-file ./docker.env up -d
---
## Testes
Para esse projeto os testes de unidade, integração e e2e são executados juntos, com o mesmo comando.

    mvn clean test

### Testes de mutação
Para os testes de mutação está sendo utilizado o plugin do **Pitest**.  Essa estrategia de teste serve para nos auxiliar a aumentar a qualidade dos nossos testes.
```
mvn test-compile org.pitest:pitest-maven:mutationCoverage
``` 
O resultado do teste fica na pasta
```
target/pit-reports/*/index.html

