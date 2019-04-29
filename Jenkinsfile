pipeline{
    agent { label 'master' }
environment {
        env_ver= credentials('2e3ac819-1bf5-4756-8d2b-281b2f0ec373')

    }
    stages{
        stage('environment'){
            steps {
                   sh '''
                   . ./env_ver
                   '''
            }
        }

        stage('code-compile'){
            steps {
                   sh '''
                     sbt clean compile
                   '''
            }
        }
        stage('unit-test'){
              steps {
                   sh '''
                     sbt coverage test coverageReport
                   '''
                   step([$class: 'ScoveragePublisher', reportDir: 'product-impl/target/scala-2.12/scoverage-report', reportFile: 'scoverage.xml'])
              }
        }
        stage('genarate-artifact'){
            when {
                 expression { BRANCH_NAME ==~ /(master|develop)/ }
            }
            steps{
                sh '''
                    sbt docker:publishLocal
                '''
            }
        }
        stage('store-artifact'){
            when {
                 expression { BRANCH_NAME ==~ /(master|develop)/ }
            }
            steps{
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'d32aee8b-31fc-4eed-aeca-b01945bcb4f6', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                     sh '''
                        docker tag product-impl:0.1.0-SNAPSHOT azmathasan92/simple-lagom-application:1.0
                        docker login --username $USERNAME --password $PASSWORD
                        docker push azmathasan92/simple-lagom-application:1.0
                    '''
                }
            }
        }
        stage('run-artifact'){
            when {
                branch 'master'
            }
            steps{
                sh '''
                    docker rm -f simple-lagom-container
                    docker run -d --env-file /home/knoldus/env2.list --name simple-lagom-container -p 9000:9000 azmathasan92/simple-lagom-application:1.0
                '''
            }
        }
   }
           post{
               always{
                   echo "unit test done"
               }
           }
}
