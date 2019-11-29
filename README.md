First, run docker compose use: `docker-compose up`.

When `docker-compose up` complete, you can type `docker ps` show all Docker containers running.

In project:

- MySQL master running port: 3308 
- MySQL slave running port: 3309
- Redis running port:  6379

---

### 1. MySQL replication

#### 1.1. MySQL master

1. Login MySQL root account with password `123456`:
 
```bash
$ docker exec -it <mysql-master container ID/name> mysql -u root -p
``` 

2. Create master account e.g. trung/123456:

```bash
mysql> GRANT REPLICATION SLAVE ON *.* TO "trung"@"%" IDENTIFIED BY "123456";
mysql> FLUSH PRIVILEGES;
```

3. Show master status:

```bash
mysql> SHOW MASTER STATUS \G
```

#### 1.2. MySQL slave

1. Login MySQL root account with password `123456`:
 
```bash
$ docker exec -it <mysql-slave container ID/name> mysql -u root -p
``` 

2. Stop slave:

```bash
mysql> STOP SLAVE;
```

3. Config connection to master:

```bash
mysql> CHANGE MASTER TO MASTER_HOST='192.168.48.1',MASTER_PORT=3307,MASTER_USER='trung',MASTER_PASSWORD='123456',MASTER_LOG_FILE='my.000003',MASTER_LOG_POS=629;
```

with:

- `MASTER_HOST`: Host IP address
- `MASTER_USER` and `MASTER_PASSWORD`: username and password of master user.
- `MASTER_LOG_FILE` and `MASTER_LOG_POS`: copied `File` and `Position` values from `SHOW MASTER STATUS` command result.

4. Start slave:

```bash
mysql> START SLAVE;
```

5. Show slave status:

```bash
mysql> SHOW SLAVE STATUS \G
```

If log contains below lines, replication is successful:

```
Slave_IO_Running: Yes 
Slave_SQL_Running: Yes
```

6. Make read-only for all users: 

```bash
mysql> SET GLOBAL super_read_only = ON;
```

Now you can use master slave project. Try create database in master, Mysql auto synchronize to slave. 

---

### 2. Redis

Use Redis CLI: `docker exec -it IdContainerRedis redis-cli`

In Redis CLI, if you want monitor everything process(input,output) Redis use command: `monitor`

---

### 3. OAuth2 authentication code flow

1. Run authorization server (port 8901) and resource server (port 8080)

2. Gen authorization code

Access browser: `http://localhost:8901/oauth/authorize?client_id=myapp&scope=read&response_type=code&redirect_uri=http://example.com`

Login with valid username and password (admin@example.com/1234 or member@example.com/1234) and then get code value from redirect URI parameters.

3. Generate access token

```bash
$ curl myapp:secret@localhost:8901/oauth/token -dgrant_type=authorization_code -dredirect_uri=http://example.com -dcode=fIe1rY
```

Use this token to access resource server APIs.

4. Check access token

```bash
$ curl myapp:secret@localhost:8901/oauth/check_token -dtoken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibXlhcGkiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJyZWFkIl0sImlkIjo5LCJleHAiOjE1NzEzODM1MzYsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiODk4NjZjY2MtZTY2MC00ZWM2LTlhYTAtZTAzOGY4OTIxNjVjIiwiY2xpZW50X2lkIjoibXlhcHAifQ.q7gtCyoEJRfvs6fTGpnmgEJNM9EG1UCGLyF2qIn1hU0
```

5. Refresh token

```bash
$ curl myapp:secret@localhost:8901/oauth/token -dgrant_type=refresh_token -drefresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibXlhcGkiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJyZWFkIl0sImlkIjo5LCJleHAiOjE1NzEzODM1MzYsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiODk4NjZjY2MtZTY2MC00ZWM2LTlhYTAtZTAzOGY4OTIxNjVjIiwiY2xpZW50X2lkIjoibXlhcHAifQ.q7gtCyoEJRfvs6fTGpnmgEJNM9EG1UCGLyF2qIn1hU0
```

---

### 4. API reset password

Please input username and password of email in `application.yml`. Then input email of user missing password.
Api auto send to email use input. Please check token in email and confirm new password.

---

### 5. OTP authentication

#### Step 1: Authenticate

POST: /api/otp/authenticate

```json
{
  "email": "yourEmail"
}
```

If return 200 OK, please check mail to get OTP code. OTP code lives in 60 seconds.

#### Step 2: Validate OTP

POST: /api/otp/validate

```json
{
  "email": "yourEmail",
  "otp": "yourOTP"
}
```
