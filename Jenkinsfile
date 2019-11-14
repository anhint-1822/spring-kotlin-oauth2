node {
  agent any
     stages {
        stage('Checkout') {
            git url: 'https://github.com/piomin/sample-spring-microservices.git', credentialsId: 'github-piomin', branch: 'master'
        }
 
        stage('Build') {
	    def mvnTool = tool 'Maven_3_3_9'
            sh "${mvnTool}/bin/mvn clean install" 
 
            def pom = readMavenPom file:'pom.xml'
            print pom.version
            env.version = pom.version
        }
        
        stage("Docker build") {
         steps {
      
          sh "docker build -t spring-boot-1 ."
         }
       }

       stage("Deploy to staging") {
       steps {
 
          sh "docker run -d --rm -p 8765:8080 --name spring-boot-kotlin spring-boot-1"
       }
       }

     }
}
