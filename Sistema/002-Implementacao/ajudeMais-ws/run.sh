mvn clean install
cd RESTful-api

if [ $1 = "test" ]; then
   echo "Running with test profile"
   mvn spring-boot:run -Drun.profiles=test

elif [ $1 = "dev" ]; then
   echo "Running with development profile"
   mvn spring-boot:run -Drun.profiles=dev

elif [ $1 = "prod" ]; then
   echo "Running with production profile"
   heroku login	
   heroku deploy:jar target/ajudeMais-ws-0.5-SNAPSHOT.jar Procfile --app ajudemais-ws

else
  echo "Running with default profile"
  mvn spring-boot:run
fi

