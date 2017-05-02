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
   mvn spring-boot:run -Drun.profiles=prod
else
  echo "Running with default profile"
  mvn spring-boot:run
fi

