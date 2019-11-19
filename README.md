First, run docker compose use: `docker-compose up`

In project, Mysql running port: 3307 and redis running port:  6379 . If you change port , please specific it  
in docker-compose.yml

Use redis cli please use command : docker exec -it IdContainerRedis redis-cli

In redis-cli : If you want monitor everything process(input,output) redis use command : monitor

With Api reset password: Please input username and password of email in application.yml. Then input email of user missing password.
Api auto send to email use input. Please check token in email and confirm new password.

 Create 2 Mysql databases: `authorization-server` and `resource-server`

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
