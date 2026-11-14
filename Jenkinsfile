pipeline {

    agent any
    
    tools {
        maven 'Maven-3.10.0'
    }
    
    stages {

        stage('Checkout') {
            steps {
                echo 'Recuperation du code depuis GitHub'
                checkout scm
            }
        }

        stage('Backend - Build') {
            steps {
                dir('backend') {
                    echo 'Compilation du backend Spring Boot'
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Frontend - Install Dependencies') {
            steps {
                dir('frontend') {
                    echo 'Installation des dependances Angular'
                    sh 'npm ci'
                }
            }
        }

        stage('Frontend - Build') {
            steps {
                dir('frontend') {
                    echo 'Compilation du frontend Angular'
                    sh 'npm run build'
                }
            }
        }
    }

    post {
        success {
            echo 'CI SUCCESS : Backend et Frontend compiles avec succes !'
        }

        failure {
            echo 'CI FAILURE : Une erreur est survenue !'
        }

        always {
            echo 'Fin du pipeline Jenkins'
        }
    }
}
