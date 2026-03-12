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
      sh 'docker build -t cricket-backend .'
     }
     dir('Angular'){
      sh 'docker build -t cricket-frontend .'
     }
    }
   }

  /* stage('Containers'){
    steps{
     dir('Spring'){
      sh 'docker rm -f cricket-backend'
      sh 'docker run -d -p 9090:8080 --name cricket-backend cricket-backend'
     }
     dir('Angular'){
      sh 'docker rm -f cricket-frontend'
      sh 'docker run -d -p 80:80 --name cricket-frontend cricket-frontend'
     }
    }
   }*/
   stage('Deploy Containers'){
 steps{
    dir('Spring'){
  sh '''
  ssh -o StrictHostKeyChecking=no ubuntu@10.0.1.80 "
  docker rm -f cricket-backend || true
  docker run -d -p 9090:8080 --name cricket-backend cricket-backend
  "
  '''
    }
  dir('Angular'){
  sh '''
  ssh -o StrictHostKeyChecking=no ubuntu@10.0.1.80 "
  docker rm -f cricket-frontend || true
  docker run -d -p 80:80 --name cricket-frontend cricket-frontend
  "
  '''
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
