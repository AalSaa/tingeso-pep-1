pipeline {
    agent any

    tools {
        gradle 'gradle'
    }

    stages {

        stage('Checkout repository') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/AalSaa/tingeso-pep-1']])
            }
        }

        stage('Build backend') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'cd backend && docker build -t aalsaa/presta-banco-backend:lastest .'
                    } else {
                        bat 'cd backend && docker build -t aalsaa/presta-banco-backend:lastest .'
                    }
                }
                
                withCredentials([string(credentialsId: 'AalSaa_pw', variable: 'dhpsw')]) {
                    script {
                        if (isUnix()) {
                            sh 'docker login -u aalsaa -p $dhpsw'
                        } else {
                            bat 'docker login -u aalsaa -p %dhpsw%'
                        }
                    }
                }

                script {
                    if (isUnix()) {
                        sh 'docker push aalsaa/presta-banco-backend:lastest'
                    } else {
                        bat 'docker push aalsaa/presta-banco-backend:lastest'
                    }
                }
            }
        }

        stage('Test backend') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'cd Backend && ./gradlew test'
                    } else {
                        bat 'cd Backend && gradlew.bat test'
                    }
                }
            }
        }

        stage('Build frontend') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'cd frontend && docker build -t aalsaa/presta-banco-frontend:lastest .'
                    } else {
                        bat 'cd frontend && docker build -t aalsaa/presta-banco-frontend:lastest .'
                    }
                }

                withCredentials([string(credentialsId: 'AalSaa_pw', variable: 'dhpsw')]) {
                    script {
                        if (isUnix()) {
                            sh 'docker login -u aalsaa -p $dhpsw'
                        } else {
                            bat 'docker login -u aalsaa -p %dhpsw%'
                        }
                    }
                }

                script {
                    if (isUnix()) {
                        sh 'docker push aalsaa/presta-banco-frontend:lastest'
                    } else {
                        bat 'docker push aalsaa/presta-banco-frontend:lastest'
                    }
                }
            }
        }
    }
}