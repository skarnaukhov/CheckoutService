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

        stage('Building image') {
            steps{
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Push Image') {
            steps{
                script {
                    docker.withRegistry( '', registryCredential )    {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Integration & performance tests') {
            steps {
                echo "-=- ToDo -=-"
            }
        }
    }
}