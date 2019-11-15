node {
  agent any
     def mvnTool = tool 'Maven'
     stages {
        stage('Checkout') {
            git url: 'https://github.com/tbtrungit/spring-kotlin-crud.git', branch: 'master'
        }
 
        stage('Build') {
            sh "${mvnTool}/bin/mvn clean install" 
        }
        
        stage("Docker build") {
          sh "docker build -t spring-boot-kotlin ."
       }

       stage("Deploy to staging") {
          sh "docker run -d --rm -p 8765:8585 --name spring-boot-kotlin spring-boot-2"
       }

     }
}
