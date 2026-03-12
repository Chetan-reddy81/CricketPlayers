pipeline{
 agent any
 environment{
  AWS_REGION='us-east-1'
  AWS_ACCOUNT_ID='707600960718'
  ECR_FRONTEND='707600960718.dkr.ecr.us-east-1.amazonaws.com/cricket-frontend'
  ECR_BACKEND='707600960718.dkr.ecr.us-east-1.amazonaws.com/cricket-backend'
 }
  stages{
    stage('checkout'){
      steps{
        checkout scm
      }
    }
   stage('Build Backend'){
    steps{
     dir('Spring'){
     sh 'mvn clean package -DskipTests'
     }
    }
   }
   stage('Build Front End'){
    steps{
     dir('Angular'){
      sh 'npm install'
      sh 'npm run build'
     }
    }
   }
   
 
 stage('Login to ECR') {
           
            steps {
                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-credentials'
                    
                ]]) {
                    sh '''
                        aws ecr get-login-password --region $AWS_REGION | \
                        docker login --username AWS \
                        --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
                    '''
                }
            }
        }
  stage('Docker images'){
    steps{
     dir('Spring'){
      sh 'docker build -t cricket-backend .'
      sh 'docker tag cricket-backend:latest $ECR_BACKEND:latest'
     }
     dir('Angular'){
      sh 'docker build -t cricket-frontend .'
      sh 'docker tag cricket-frontend:latest $ECR_FRONTEND:latest'
     }
    }
   }
   stage('Push to ECR') {
           
            steps {
                sh '''
                    docker push $ECR_BACKEND:latest
                    docker push $ECR_FRONTEND:latest
                '''
            }
        }
  stage('Deploy Containers') {
            
            steps {
                sh '''
                    docker pull $ECR_BACKEND:latest
                    docker pull $ECR_FRONTEND:latest

                    docker rm -f cricket-backend || true
                    docker rm -f cricket-frontend || true

                    docker run -d -p 9090:8080 \
                    --name cricket-backend $ECR_BACKEND:latest

                    docker run -d -p 80:80 \
                    --name cricket-frontend $ECR_FRONTEND:latest
                '''
            }
        }

    }
  /* stage('Deploy Containers'){
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
}*/
  
  }
  post {
  success {
   archiveArtifacts artifacts: 'Spring/target/*.jar, Angular/dist/**'
  }
  }
 
}
