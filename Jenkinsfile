pipeline {
    agent any

    tools {
        jdk 'JAVA17'
        maven 'maven3'
    }

    environment {
        SCANNER_HOME= tool 'sonar-scanner'
        DOCKER_COMPOSE_FILE = "${env.WORKSPACE}/gtps/docker-compose.yml"

    }

    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', changelog: false, poll: false, url: 'https://github.com/jashobantapatra/gtps.git'
            }
        }
        stage('Compile') {
            steps {
                bat "mvn clean compile"
            }
        }
        stage('Sonarqube Analysis') {
            steps {
                bat ''' %SCANNER_HOME%/bin/sonar-scanner -Dsonar.sources=. -Dsonar.host.url=http://localhost:9000 -Dsonar.login=squ_7cd8d98ce4185b1a1bc2a7fe6f3bf1c55cbc4afe -Dsonar.projectName=gtps \
                -Dsonar.java.binaries=. \
                -Dsonar.projectKey=gtps '''
            }
        }
        stage('OWASP SCAN') {
            steps {
                dependencyCheck additionalArguments: ' --scan ./', odcInstallation: 'DP'
                dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }
        }
        stage('Build Application') {
            steps {
                bat "mvn clean install"
            }
        }
        stage('Build & Push Docker Image') {
            steps {
                script{
                     docker.withRegistry('https://index.docker.io/v1/', '2f864ba3-ecbf-4f17-a282-6f507da35854') {
                        bat "docker compose down"
                        bat "docker compose up -d --build"
                    }
                }
            }
        }
    }
}
