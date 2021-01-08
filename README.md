# Fuse Community Example

Example on how to use Camel to consume messages from queues using transaction.

### how to run locally
    mvn clean spring-boot:run

### To deploy in Openshift
    mvn clean package -Popenshift fabric8:deploy

### Manually Deploy 
    oc new-build java --name=fuse-community --binary=true
    oc new-app fuse-community

    oc start-build fuse-community --from-file=*.jar
