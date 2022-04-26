# Gym-Helper
It is my first project to learn how create application using Spring Boot.
I've deployed the project on Heroku.
It's available at https://gym-helper-dp.herokuapp.com/api

I have used:

Java, Spring Boot, PostgreSql, Liquibase
## Table of contents
* [Description](#Description)
* [Endpoints](#Endpoints)
## Description
This project allows you to easily save the results from the gym trainings.
You can:
* register and login as user 
* save your planned or done trainings with exercises
* see history of trainings
* see list of example type of exercises


Every user's data is stored in database, where are tables: users, roles, users_roles, exercises, trainings.
I used JWT for authorization user.


## Endpoints

### Default api URL
`https://gym-helper-dp.herokuapp.com/api`
### Auth

| Method | Url | Description | Example Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/auth/register| Sign up | [JSON](#register) |
| POST   | /api/auth/login | Log in | [JSON](#login) |

### Users

| Method | Url | Description | Example Valid Request Body |
|--------| --- | ----------- | ------------------------- |
| GET    | /api/users | get all users by admin | |
| GET    | /api/users/me/info | Get logged in user info | |
| GET    | /api/users/{username}/info | Get info about user by username | |
| GET    | /api/users/{username}/trainings | Get trainings created by user | |
| GET    | /api/users/check/username | Check if username is available to register |[JSON](#checkusername) |
| GET    | /api/users/check/email | Check if email is available to register |[JSON](#checkemail) |
| POST   | /api/users | Add user (Only for admin) | [JSON](#usercreate) |
| PUT  | /api/users/{username} | Update user (By admin) | [JSON](#userupdatebyadmin) |
| PUT    | /api/users/ | Update user (By logged in user) | [JSON](#userupdate) |
| PATCH  | /api/users/{username}/give/admin | Give admin role to user (only for admins) | |
| PATCH  | /api/users/{username}/take/admin | Take admin role from user (only for admins) | |
| PATCH  | /api/users/change/password | Update user password | [JSON](#userpasswordchange) |
| PATCH  | /api/users/change/email | Update user email | [JSON](#useremailchange) |
| DELETE | /api/users/ | Delete user (For logged in user  | |
| DELETE | /api/users/{username} | Delete user (For admin) | |

### Trainings

| Method | Url | Description | Example Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/trainings | Get all trainings belong to log in user | |
| GET    | /api/trainings/{id} | Get training belongs to log in user by training id | |
| GET    | /api/trainings/filter | Get filtered trainings | |
| GET    | /api/trainings/export/csv | Export filtered trainings to csv | |
| POST   | /api/trainings | Create new training | [JSON](#trainingcreate) |
| PATCH    | /api/trainings/{id}/skipped | Set training status to SKIPPED  | |
| PATCH    | /api/trainings/{id}/done | Set training status to DONE | |
| PUT    | /api/trainings/{id} | Update training  | [JSON](#trainingupdate) |
| DELETE | /api/trainings/{id} | Delete training  | |

### Exercises

| Method | Url | Description | Example Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/exercises/all-type | Get all types of exercises | |
| GET    | /api/trainings/{trainingId}/exercises | Get all exercises which belongs to training with id = trainingId | |
| GET    | /api/trainings/{trainingId}/exercises/{exerciseId} | Get exercise by id if it belongs to training with id = trainingId | |
| POST   | /api/trainings/{trainingId}/exercises | Create new exercise for training with id = trainingId | [JSON](#exercisecreate) |
| POST   | /api/trainings/{trainingId}/exercises/random | Create new random exercise for training with id = trainingId | [JSON](#exercisecreate) |
| PUT    | /api/trainings/{trainingId}/exercises/{exerciseId} | Update exercise by id if it belongs to training with id = trainingId  | [JSON](#exerciseupdate) |
| DELETE | /api/trainings/{trainingId}/exercises/{exerciseId} | Delete exercise by id if it belongs to training with id = trainingId  | |


##### <a id="register">Register -> /api/auth/register</a>
```json
{
  "username":"dawid00",
  "email":"dawid@gmail.com",
  "password":"Testowe10!",
  "height": 184,
  "weight": 85
}
```
##### <a id="login">Log in : /api/auth/login</a>
```json
{
	"username": "dawid00",
	"password": "Testowe10!"
}
```
##### Response Body
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYXdpZCIsImlhdCI6MTY0NTcwOTU1OCwiZXhwIjoxNjQ1NzQ1NTU4fQ.mQrM9PBrZsHAqvOLsp2EpBrgfS19RGroI7spr5A6uwCLGWFtdgIKdWn3IJ7OfwCOmK-Zbx1byVCjHOKHagP94w",
  "id": 1,
  "username": "dawid00",
  "email": "dawid@gmail.com",
  "roles": [
    "ROLE_USER","ROLE_ADMIN"
  ],
  "type": "Bearer"
}
```

##### <a id="refreshtoken">Refresh token : /api/auth/refresh-token</a>
```json
{
  "refreshToken": "01ade098-ea3e-4594-802d-4d3b5a921707"
}
```
##### Response Body
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYXdpZCIsImlhdCI6MTY0NTcyMTc2NiwiZXhwIjoxNjQ1NzU3NzY2fQ.tyutfztKrSFJPsl8zvArS4E5YThPyqRfIJNtbWBFgiBGjyRVz8ItwifKGpTaBlDyZk_6v5oTlN__CGb9VF2zPg",
  "refreshToken": "01ade098-ea3e-4594-802d-4d3b5a921707",
  "tokenType": "Bearer"
}
```




##### <a id="checkusername">Check Username -> /api/auth/check/username</a>
```json
{
	"username": "robert02"
}
```

##### <a id="checkemail">Check Email : /api/auth/check/email</a>
```json
{
	"email": "example10@wp.pl"
}
```

##### <a id="createuser">Create user : /api/users</a>
```json
{
  "email": "robert@gmail.com",
  "username": "rebo18",
  "password": "Password17!",
  "info": {
    "firstname": "Robert",
    "lastname": "Rogal",
    "height": 176,
    "weight": 75
  }
}
```
##### <a id="userupdatebyadmin">Update user with username by admin : /api/users/{username}</a> 
```json
{
  "email": "robert123@gmail.com",
  "username": "rebo18",
  "password": "Password17!",
  "info": {
    "firstname": "Robert",
    "lastname": "Rogal",
    "height": 176,
    "weight": 85
  }
}
```
##### <a id="userupdate">Update user : /api/users</a>
```json
{
  "email": "robert123@gmail.com",
  "username": "rebo18",
  "password": "Password17!",
  "info": {
    "firstname": "Robert",
    "lastname": "Rogal",
    "height": 176,
    "weight": 80
  }
}
```
##### <a id="changepassword">Change password : /api/auth/change/password</a>
```json
{
	"password": "Footballer29!"
}
```

##### <a id="changeemail">Change Email : /api/auth/change/email</a>
```json
{
	"email": "katar2022@gmail.com"
}
```
##### <a id="trainingcreate">Create new training : /api/trainings</a>
```json
{
  "description": "Motivation does not exist",
  "date": "2022-02-12T16:09:49.398Z",
  "status": "DONE",
  "exercises": [
    {
      "type": "OHP",
      "weight": 50,
      "reps": 8,
      "series": 3
    }
  ]
}
```
##### <a id="trainingupdate">Update training : /api/trainings/{id}</a>
```json
{
  "description":"123",
  "status":"DONE"
}
```

##### <a id="exercisecreate ">Create new exercise : /api/trainings/{trainingId}/exercises</a>
```json
{
  "reps":12,
  "series":3,
  "weight":50,
  "type":"PULL_UP"
}
```
##### <a id="exerciseupdate">Update exercise :	/api/trainings/{trainingId}/exercises/{id}</a>
```json
{
  "reps":10
}
```