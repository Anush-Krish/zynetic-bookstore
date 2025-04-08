# zynetic-bookstore

## Enhancements

- add redis caching to improve rating service(to avoid aggregation query in every fetch)
- add refresh token support(use keycloak instead in for security)
- setup roles for book creation,delete,update like for admin and super admin only
- setup different environments like dev,qa,prod

## setup instructions for local:
- Install postgresSql
- create database zynetic;
- CREATE SCHEMA bookstore;



## Endpoints:

# user signup and login 
- POST /auth/signup
{
"name": "Anush Krishnaaa",
"email": "anush122@gmail.com",
"password": "password1234"
}

- POST /auth/login 
{
"email": "anush122@gmail.com",
"password": "password1234"
}

# books endpoint
- POST /books :create book
- GET /books/{bookId} :fetchBookById
- GET /book?page=0&size=20 :fetch all books paginated
- GET /books/books :fetch books by filter author,category,title,minRating,page,size
- PUT /books/{bookId} :update book
- DELETE /books/{bookId} :delete book by id


