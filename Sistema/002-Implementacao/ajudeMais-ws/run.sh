if [ $1 = "run" ]; then
	cd RESTful-api	
	mvn spring-boot:run
	break

fi

mvn clean install

if [ $1 = "test" ]; then
   mvn spring-boot:run -Drun.profiles=test

elif [ $1 = "dev" ]; then
   mvn spring-boot:run -Drun.profiles=dev

elif [ $1 = "prod" ]; then

   heroku login	
   heroku deploy:jar target/ajudeMais-ws-0.5-SNAPSHOT.jar Procfile --app ajudemais-ws

else
  mvn spring-boot:run
fi

