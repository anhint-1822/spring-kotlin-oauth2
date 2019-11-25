First, run docker compose use: `docker-compose up`

Show all docker running : docker ps

I. Config master slave mysql

When docker-compose up complete, you can type docker ps show all docker running.
1. Login mysql master in docker : `docker exec -it "id container master" mysql -u root -p`
Input password : 123456

Type step by step command : 
`CREATE USER 'trung'@'%' IDENTIFIED BY '123456';`
`GRANT REPLICATION SLAVE ON *.* TO 'trung'@'%';`

Type : `show master` you can see all information in master;

2. Login mysql slave in docker: `docker exec -it "id container slave" mysql -u root -p`
Input password : 123456

Type step by step command : 
`change master to master_host='your ip',master_port=3308,master_user='trung',master_password='123456',master_log_file='information you can see in master container when type command show masters; Example : my.000003', master_log_pos=information you can see in master container when type command show masters; Example:194";
`

Complete: `change master to master_host='ipaddress',master_port=3308,master_user='trungtb',master_password='123456',master_log_file='my.000004',master_log_pos=194;`

Then type command : `start slave;`

Show status slave type command : `show slave status \G`

If mysql show command : `Waiting for master to send event` you has connect success connect to master.
Now you can use master slave project. Try create database in master, Mysql auto sync to slave. 

Make slave readonly using command : `FLUSH TABLES WITH READ LOCK;` and `SET GLOBAL read_only = ON`;

In project, Mysql master running port: 3308 and mysql slave running port: 3309 and redis running port:  6379 . If you change port , please specific it  
in docker-compose.yml

Use redis cli please use command : docker exec -it IdContainerRedis redis-cli

In redis-cli : If you want monitor everything process(input,output) redis use command : monitor

With Api reset password: Please input username and password of email in application.yml. Then input email of user missing password.
Api auto send to email use input. Please check token in email and confirm new password.


1. Run authorization server (port 8901) and resource server (port 8080)

2. Gen authorization code

Access browser: `http://localhost:8901/oauth/authorize?client_id=myapp&scope=read&response_type=code&redirect_uri=http://example.com`

Login with valid username and password (admin@example.com/1234 or member@example.com/1234) and then get code value from redirect URI parameters.

3. Generate access token

```bash
$ curl myapp:secret@localhost:8901/oauth/token -dgrant_type=authorization_code -dredirect_uri=http://example.com -dcode=fIe1rY
```

Use this token to access resource server APIs

4. Check access token

```bash
$ curl myapp:secret@localhost:8901/oauth/check_token -dtoken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibXlhcGkiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJyZWFkIl0sImlkIjo5LCJleHAiOjE1NzEzODM1MzYsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiODk4NjZjY2MtZTY2MC00ZWM2LTlhYTAtZTAzOGY4OTIxNjVjIiwiY2xpZW50X2lkIjoibXlhcHAifQ.q7gtCyoEJRfvs6fTGpnmgEJNM9EG1UCGLyF2qIn1hU0
```

5. Refresh token

```bash
$ curl myapp:secret@localhost:8901/oauth/token -dgrant_type=refresh_token -drefresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibXlhcGkiXSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJyZWFkIl0sImlkIjo5LCJleHAiOjE1NzEzODM1MzYsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiODk4NjZjY2MtZTY2MC00ZWM2LTlhYTAtZTAzOGY4OTIxNjVjIiwiY2xpZW50X2lkIjoibXlhcHAifQ.q7gtCyoEJRfvs6fTGpnmgEJNM9EG1UCGLyF2qIn1hU0
```
