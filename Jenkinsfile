pipeline {
    agent any

    environment {
        PATH = "/usr/lib/gradle/bin:$PATH"
    }

    stages {

        stage('Git Checkout') {
            steps {
                checkout scm
                echo 'checkout success'
            }
        }

        stage('Build') {
            steps {
                sh 'gradle clean build --exclude-task test'
                echo 'Build success'
            }
        }

        stage('Test') {
            steps {
                sh 'gradle test'
                echo 'test success'
            }
        }
    }
}
