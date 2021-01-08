# Fuse Community Example

Example on how to use Camel to consume messages from queues using transaction.

### how to run locally
    mvn clean spring-boot:run

### To deploy in Openshift
    mvn clean package -Popenshift fabric8:deploy

### Deploy Manually  

#### Create the build
    oc new-build java:8 --name=fuse-community --binary=true
 
#### Build the image
    oc start-build fuse-community --from-file=fuse-community-1.0.0.jar

#### Create the deployment
    oc new-app fuse-community
