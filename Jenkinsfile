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
        sh 'docker build -t cricket-backend:v1-Spring  .'
      }
      }
    }

    stage('Deploy Containers') {
      steps {
        dir('Spring'){
        sh '''
        docker network create cricket-network || true

        docker stop mysql-container || true
        docker rm mysql-container || true

        docker stop cricket-container || true
        docker rm cricket-container || true

        docker run -d \
          --name mysql-container \
          --network cricket-network \
          -e MYSQL_ROOT_PASSWORD=rootpass \
          -e MYSQL_DATABASE=cricketdb \
          -e MYSQL_USER=cricketuser \
          -e MYSQL_PASSWORD=cricketpass \
          mysql:8.0

        sleep 20

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
}
