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
stage('Frontend Integrate') {
  steps {
    sh '''
      rm -rf Spring/src/main/resources/static
      mkdir -p Spring/src/main/resources/static
      cp -r Angular/dist/cricket-project/* Spring/src/main/resources/static/
    '''
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
