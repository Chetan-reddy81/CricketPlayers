pipeline{
 agent any
  stages{
    stage('checkout'){
      steps{
        checkout scm
      }
    }
   stage('Backend'){
    steps{
     dir('Spring'){
     sh 'mvn clean package -DskipTests'
     }
    }
   }
   stage('Front End'){
    steps{
     dir('Angular'){
      sh 'npm install'
      sh 'npm run build'
     }
    }
   }
   stage('Docker images'){
    steps{
     dir('Spring'){
      sh 'docker build -t cricket-backend ./backend'
     }
     dir('Angular'){
      sh 'docker build -t cricket-frontend ./frontend'
     }
    }
   }

   stage('Containers'){
    steps{
     dir('Spring'){
      sh 'docker run -d -p 9090:8080 cricket-backend'
     }
     dir('Angular'){
      sh 'docker run -d -p 80:80 cricket-frontend'
     }
    }
   }
  
  }
  post {
  success {
   archiveArtifacts artifacts: 'Spring/target/*.jar, Angular/dist/**'
  }
  }
 
}
