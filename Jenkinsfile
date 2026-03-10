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
     sh 'mvn clean package'
    }
   }
   stage('TEST'){
    steps{
     sh 'mvn test'
    }
   }
  }
}
