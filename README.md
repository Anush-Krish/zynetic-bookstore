# zynetic-bookstore

## Enhancements

-add redis caching to improve rating service(to avoid aggregation query in every fetch)
-add refresh token support(use keycloak instead in for security)
-setup roles for book creation,delete,update like for admin and super admin only
-setup different environments like dev,qa,prod

## setup instructions:
## For local setup:
Install postgresSql
create database zynetic;
CREATE SCHEMA bookstore;



## Endpoints:

# user signup and login 
POST /auth/signup
{
"name": "Anush Krishnaaa",
"email": "anush122@gmail.com",
"password": "password1234"
}

POST /auth/login 
{
"email": "anush122@gmail.com",
"password": "password1234"
}

# books endpoint
POST /books
GET /books/{bookId}
GET /books
GET /books/books
PUT /books/{bookId}
DELETE /books/{bookId}


