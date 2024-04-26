## User management System Java GRPC

- GRPC based server
- Language used is JAVA
- JWT support for login 
- MySQL DB support


## Build the Application
```bash
./gradlew clean build
```


## Run the Application

- This will start your application on port 8181 
- You can use localhost:8181 to access the application

```bash
./gradlew run
```


## Database Schema

Create database

```sql
create database user_management_system;
```
Create Users Table

```sql
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  CONSTRAINT `unique_email` UNIQUE (`email`)
); 
```


## Website to Generate the Public Private Key
- https://acte.ltd/utils/openssl
- Algorithm : RSA
- Key Size : 2048
- PKCS8