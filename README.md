# Fuse Community Example

Example on how to use Camel to consume messages from queues using transaction.

### how to run locally
    mvn clean spring-boot:run

### To deploy in Openshift
    mvn clean package -Popenshift fabric8:deploy