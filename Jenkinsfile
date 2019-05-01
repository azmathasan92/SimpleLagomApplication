pipeline{
    agent { label 'master' }
        environment {
            AKKA_HOSTNAME = credentials('2e3ac819-1bf5-4756-8d2b-281b2f0ec373')
            AKKA_PORT1 = credentials('c1d7d272-5da4-4ed2-ac5c-97fd61d86d19')
            AKKA_PORT2 = credentials('7e65bb7c-c173-4ce3-8f08-a9cc7bf625c2	')
            AKKA_PORT3 = credentials('3ebdae09-82f5-44cd-9c37-e8dad8a3c13a')
            AKKA_BIND_HOSTNAME = credentials('37945514-bc0f-4405-ab2b-3aca5eaea802')
            AKKA_BIND_PORT1 = credentials('22f8ac67-4388-42db-abc7-8e0976810dc4')
            AKKA_BIND_PORT2 = credentials('cb094ed1-467d-41b3-9e88-d5294b0a9373')
            AKKA_BIND_PORT3 = credentials('	ddb5179e-6ef7-43f6-a32a-f4ab791066b2')
            AKKA_STARTUP_TIMEOUT = credentials('60ac6db2-ad0e-4e34-905e-3605e308c015')
            APPLICATION_SECRET = credentials('0bcba992-ba11-4c22-a4be-b86f0f465915')
            HTTP_PORT = credentials('f05168d3-86b3-44ef-a607-aa9ff437fdac')
            CASSANDRA_KEYSPACE = credentials('cc68600b-c967-48a7-9c4d-cb39beb18f51')
            CAS_CONTACT_POINT_ONE = credentials('43e619e9-f493-41d0-84f7-89d3c77d90b7')
            CAS_CONTACT_POINTS_PORT = credentials('f1e6427b-d88f-4a47-9cec-3db413001971')
        }

    stages{

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
}
