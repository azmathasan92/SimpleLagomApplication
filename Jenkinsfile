pipeline{
    agent none
            environment {
                AKKA_HOSTNAME='0.0.0.0'
                AKKA_PORT='2557'
                AKKA_BIND_HOSTNAME='0.0.0.0'
                AKKA_BIND_PORT='2557'
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

   }
           post{
               always{
                   echo "Testing pipeline"
               }
           }
}
