pipeline {
    environment {
        registry = "skarnaukhov/sk_test_repository"
        registryCredential = 'sk_dockerID'
    }

    agent {
        docker {
            image 'maven:3-alpine'
            args '--network ci --mount type=volume,source=ci-maven-home,target=/root/.m2'
        }
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}