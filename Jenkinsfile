pipeline {
    environment {
        registry = "skarnaukhov/sk_test_repository"
        registryCredential = 'sk_dockerID'
        dockerImage = ''
    }
    agent any

    tools {
        maven 'mvn-3.5.2'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code inspection & quality gate') {
            steps {
                echo "-=- ToDo -=-"
            }
        }

        stage('Build & Push Image') {
            steps{
                script {
                    docker.withRegistry( '', registryCredential )    {
                        app = docker.build(registry)
                        app.push("${env.BUILD_NUMBER}")
                        app.push("latest")
                    }
                }
            }
        }

        stage('Integration & performance tests') {
            steps {
                echo "-=- ToDo -=-"
            }
        }

        stage('Deploying into k8s'){
             steps{
                  script {
                    withKubeConfig([credentialsId: 'kube-config']) {
                        sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl"'
                        sh 'chmod u+x ./kubectl'
                        sh './kubectl apply -f deploy/deployment.yml'
                        sh './kubectl apply -f deploy/service.yml'
                    }
                  }
             }
        }
    }
}