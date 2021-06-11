pipeline {
    agent any

    environment {
        PATH = "/usr/lib/gradle/bin:$PATH"
        SLACK_CHANNEL = '#빌드'
    }

    stages {

        stage('Git Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'gradle clean build --exclude-task test'
                // sh 'gradlew clean build -DskipTests=true'
            }
        }

        stage('Test') {
            steps {
                sh 'gradle test'
                // sh 'gradlew surefile:test'
                junit '**/build/test-results/test/*.xml'
            }
        }
    }

     post {
        success {
             slackSend (channel: SLACK_CHANNEL, color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
             }

        failure {
             slackSend (channel: SLACK_CHANNEL, color: '#F01717', message: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
             }
      }
}