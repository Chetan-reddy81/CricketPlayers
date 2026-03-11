pipeline{
 agent any
  stages{
    stage('checkout'){
      steps{
        checkout scm
      }
    }
   stage('Build'){
    steps{
     dir('Spring'){
     sh 'mvn clean package -DskipTests'
     }
    }
   }
  
  }
}
