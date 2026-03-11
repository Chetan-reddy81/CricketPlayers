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
  
  }
 
}
