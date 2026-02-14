pipeline{
  agent any
  stages{
    stage('Checkout'){
      steps{
        checkout scm
      }
    }
    stage('Build front end'){
      steps{
        dir('Angular'){
      sh '''
      npm install
      npm run build
      '''
        }
      }
    }
    stage('frontend integrate'){
      steps{
        sh 'cp -r Angular/dist/*/*  Spring/src/main/resources/static/'
      }
    }
    stage('Build back end'){
      steps{
        dir('Spring'){
        sh 'mvn clean package -DskipTests'
        }
      }
    }
    stage('Archive Artifact') {
    steps {
        archiveArtifacts artifacts: 'Spring/target/*.jar', fingerprint: true
    }
}

  }
}
