pipeline {
    agent any

//     environment {
//         PATH = "/usr/lib/gradle/bin:$PATH"
//     }

    tools {
        gradle 'Gradle 6.7.1'
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
                sh 'gradlew clean build -DskipTests=true'
            }
        }

        stage('Test') {
            steps {
                // sh 'gradle test'
                sh 'gradlew surefile:test'
                junit '**/build/test-results/test/*.xml'
            }
        }
    }
}
