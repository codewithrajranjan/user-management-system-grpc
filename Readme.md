


## Database Schema

Create database

```sql
create database user_management_system;
```
Create Users Table

```sql
CREATE TABLE `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `password` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
); 
```
Create Index on the email_address and password

```sql
CREATE INDEX idx_email_password ON users (email,password);
```