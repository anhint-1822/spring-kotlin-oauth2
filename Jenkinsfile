pipeline {
     agent any
     stages { 
stage("Package") {
     steps {
          sh "./mvn clean install"
     }
}
stage("Docker build") {
     steps {
      
          sh "docker build -t spring-boot-maven ."
     }
}
stage("Deploy to staging") {
     steps {
 
          sh "docker run -d --rm -p 8765:8080 --name calculator_1 spring-boot-maven"
     }
}
}
}
