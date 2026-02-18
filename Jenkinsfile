pipeline {
  agent any

  stages {

    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build Frontend') {
      steps {
        dir('Angular') {
          sh '''
          npm install
          npm run build
          '''
        }
      }
    }

    stage('Integrate Frontend') {
      steps {
        sh '''
        rm -rf Spring/src/main/resources/static
        mkdir -p Spring/src/main/resources/static
        cp -r Angular/dist/cricket-project/* Spring/src/main/resources/static/
        '''
      }
    }

    stage('Build Backend') {
      steps {
        dir('Spring') {
          sh 'mvn clean package -DskipTests'
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        dir('Spring'){
        sh '''
        docker build -t cricket-backend:latest .
        '''
      }}
    }

    stage('Deploy Using Compose') {
      steps {
        sh '''
        docker compose down || true
        docker compose up -d
        '''
      }
    }

  }
}
