pipeline {
    agent any

    environment {
        PATH = "/usr/lib/gradle/bin:$PATH"
    }

    stages {

        stage('Git Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // sh 'gradle clean build --exclude-task test'
                sh './gradlew clean build --exclude-task test'
            }
        }

        stage('Test') {
            steps {
                // sh 'gradle test'
                sh './gradlew test'
                junit '**/build/test-results/test/*.xml'
            }
        }
    }
}
