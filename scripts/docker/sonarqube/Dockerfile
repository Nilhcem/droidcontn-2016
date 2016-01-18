FROM java:8
MAINTAINER Nilhcem

RUN DEBIAN_FRONTEND=noninteractive apt update
RUN DEBIAN_FRONTEND=noninteractive apt install -y wget unzip
RUN wget -q https://sonarsource.bintray.com/Distribution/sonarqube/sonarqube-5.3.zip
RUN unzip -qq sonarqube-5.3.zip -d /opt/
RUN rm sonarqube-5.3.zip

EXPOSE 9000
EXPOSE 9092

CMD ["/opt/sonarqube-5.3/bin/linux-x86-64/sonar.sh", "console"]
