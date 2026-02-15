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
    stage('Build Docker Image'){
      steps{
        dir('Spring'){
          sh 'docker build -t cricket-backend:v1 .'
        }
      }
    }
  stage('Deploy Container') {
    steps {
        sh '''
        docker stop cricket-container || true
        docker rm cricket-container || true
        docker run -d \
        --name cricket-container \
        --network cricket-network \
        -p 9090:8080 \
        -e DB_URL=jdbc:mysql://mysql-container:3306/cricketdb \
        -e DB_USERNAME=cricketuser \
        -e DB_PASSWORD=cricketpass \
        cricket-backend:v1
        '''
    }
}



  }
}
