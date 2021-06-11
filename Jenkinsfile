pipeline {
    agent any

    environment {
        PATH = "/usr/lib/gradle/bin:$PATH"
        // PATH = "/opt/gradle/gradle-6.6.1/bin:$PATH"
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
                sh 'gradle clean build'
                echo 'Build success'
            }
        }
    }
}
