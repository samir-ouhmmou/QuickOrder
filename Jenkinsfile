pipeline {

    agent any

    tools {
        nodejs 'NodeJS-22'
    }


    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Check Environment') {
            steps {
                sh '''
                    java -version
                    mvn -version
                    node -v
                    npm -v
                '''
            }
        }

        stage('Backend - Build') {
            steps {
                dir('backend') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Frontend - Install Dependencies') {
            steps {
                dir('frontend') {
                    sh 'npm ci'
                }
            }
        }

        stage('Frontend - Build') {
            steps {
                dir('frontend') {
                    sh 'npm run build'
                }
            }
        }
    }

    post {
        success {
            echo 'CI SUCCESS : Backend et Frontend compiles !'
        }

        failure {
            echo 'CI FAILURE : Une erreur est survenue !'
        }
    }
}
