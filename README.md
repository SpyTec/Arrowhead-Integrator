# Arrowhead-Integrator
This project is an integrator between Cumulocity and the Arrowhead Framework. Developed under the course D0020E at Luleå Technical University.

## Table of Contents
  1. [Getting Started](#getting-stated)
  2. [Prerequisites](#prerequisites)
      1. [curl](#curl)
      2. [Docker](#docker)
      3. [Docker-compose](#docker-compose)
      4. [Download the Arrowhead Framework docker](#download-the-arrowhead-framework-docker)
  3. [Installing](#installing)
      1. [Cumulocity password](#cumulocity-password)
  4. [Tests](#tests)
      1. [With curl command](#with-curl-command)
      2. [With soapUI](#with-soapui)
      3. [With the integrator](#with-the-integrator)
  5. [Useful links](#useful-links)
  6. [Common Problems](#common-problems)
      1. [Bind address already in use](#bind-address-already-in-use)
      2. [Remote failure: JVM option](#remote-failure-jvm-option)
  
  
## Getting Started
### Prerequisites

#### curl
curl is used to download the correct version of docker-compose. To install curl do:
```
  sudo apt-get install curl
```

#### Docker
As the Arrowhead Framework local cloud is running with docker we need to download docker:
```
sudo apt-get install docker.io
```
#### Docker-compose
The arrowhead framework docker needed version 1.17.x to run. To download this version of docker-compose:
```
sudo curl -L https://github.com/docker/compose/releases/download/1.17.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

#### Download the Arrowhead Framework docker
You can download the Arrowhead Framework docker [here](https://forge.soa4d.org/frs/download.php/file/273/ahf-docker-17.10.4.0.zip)

## Installing
To install and get the docker running follow these steps go to the directory where the Arrowhead Framework docker is downloaded and unzip:
```
cd ~/Downloads/ahf-docker-17.10.4.0/core/
sudo docker-compose up --build
```

### Cumulocity password
To make sure you got the right cumulocity domain, username and password you need to change data in integrator.properties (example can be found in integrator.properties.example):
```
cumulocity_domain=<domain>
cumulocity_username=<user>
cumulocity_password=<pass>
```

If a problem occurred please check the [Common Problem section](#common-problems).


## Tests

### With curl command
To test that the AHF docker is up and running you can test to send a curl command to the authorisation registry
```
curl -X POST \
    -H 'Content-Type: text/xml' \
    -H 'SOAPAction: ""' \
    --data '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                              xmlns:aut="http://arrowhead.eu/authorisation">
               <soapenv:Header/>
               <soapenv:Body>
                  <aut:addAuthorisationRule>
                     <name>light-control-2</name>
                     <type>_light-control-rest-http._tcp</type>
                     <distinguishedNameSuffix>CN=sensor-module-1.docker.ahf</distinguishedNameSuffix>
                  </aut:addAuthorisationRule>
               </soapenv:Body>
            </soapenv:Envelope>' \
    http://127.0.0.1:8080/authorisation/AuthorisationConfigurationService
```

If the docker is running correctly the server should respond with: 
```


<?xml version='1.0' encoding='UTF-8'?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">
<S:Body>
    <ns2:addAuthorisationRuleResponse xmlns:ns2="http://arrowhead.eu/authorisation"/>
</S:Body>
</S:Envelope>
```

### With soapUI
In the ahf docker there is a directory called soapUI. When located in the directory run:
```
sudo docker-compose up --build
```
There should be 0 fails returned.


### With the integrator
Open up the integrator in your IDE and follow these steps:
1. Create a new Service with the Service class.
2. Send that service to Publish.
3. Check managementtool to check if the service is registered to AHF.
4. Check Cumulocity that the service is registered.

## Useful links

To check which services and authorisation rules that is currently added to AHF you can go to:
[http://127.0.0.1:8080/managementtool/](http://127.0.0.1:8080/managementtool/)


## Common Problems

### Bind address already in use

Check which program that is using the same port
```
sudo netstat -peanut | grep -P ":53\s"
```
If it is dnsmasq that uses port 53 follow these steps: 
1. ```gksudo gedit /etc/NetworkManager/NetworkManager.conf```
2. Change "dns=dnsmasq" to "#dns=dnsmasq"
3. save and exit
4. ```sudo service network-manager restart```
5. Should work now, go to the ahf-docker directory and try ```sudo docker-compose up --build```

### Remote failure: JVM option

Check which containers that exists with:
```
sudo docker ps -a
```

For each container that exists run the following command: 
```
sudo docker rm <containerID>
```
**NOTE: This will remove the containers. Make sure this is what you really want.**
