node {
    stage('Clone repository') {
        checkout scm
    }

    stage("Build image") {
        sh "mvn spring-boot:build-image -D spring.profiles.active=prod"
    }

    stage("Docker login") {
        environment {
            DOCKER_HUB_LOGIN = credentials('docker-hub')
        }
        sh 'docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW'
    }

    stage("Image rename") {
        sh "docker tag betting_game_api:0.0.1-SNAPSHOT loljoa/betting_game_api:0.0.1-SNAPSHOT"
    }


    stage("Image push") {
        sh "docker push loljoa/betting_game_api:0.0.1-SNAPSHOT"
    }

    stage("Resource cleanup") {
        sh "docker image prune -a"
    }
}