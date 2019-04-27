pipeline{

    agent any
            environment {
                AKKA_HOSTNAME='0.0.0.0'
                AKKA_PORT='2556'
                AKKA_BIND_HOSTNAME='0.0.0.0'
                AKKA_BIND_PORT='2556'
                AKKA_STARTUP_TIMEOUT='30s'
                APPLICATION_SECRET='none'
                HTTP_PORT='9000'
                CASSANDRA_KEYSPACE='product'
                CAS_CONTACT_POINT_ONE='localhost'
                CAS_CONTACT_POINTS_PORT='9042'
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
              }
        }
        stage('genarate-artifact'){
            steps{
                sh '''
                    sbt docker:publishLocal
                '''
            }
        }
        stage('store-artifact'){
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

   }
           post{
               always{
                   echo "Complete CI pipeline"
               }
           }
}
