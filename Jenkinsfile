pipeline {
   agent any
   stages{
      stage('Checkout'){
        steps{
          echo 'Recupiration du code depuis Github'
          checkout scm
       }
     }
     stage('Backend - Build & test'){
        steps{
          dir('backend'){
         echo 'Compilatio,n et test du backnd Spring boot'
         sh 'mvn clean verify'
         
        }
        }
     }
     stage('Frontend -Install Dependecies'){
        steps{
         dir('frontend'){
           echo 'Installation des dependances Angular'
           sh 'npm ci'
         }
        }
       }
       stage('Frontend - Build'){
         steps{
          dir ('frontend'){
            echo 'Compilation  du frontend Angular')
            sh ' npm run build'
          }
         }
        }
     }
     post {
       success {
        echo 'CI/CD SUCCES : toutes les etpaes sont validess !'
        }
        failure {
         echo 'CI/CD FAILURE : Une erreur est survenue !'
         }
        always {
          echo 'Fin du pipeline'
          }
         }
 
}
