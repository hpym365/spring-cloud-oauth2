# spring-cloud-demo
eureka port 1111

app1 port 2222
app2 port 2223

feign port 3333

zuul port 5555

auth-ser port 5656

sso port 7777

访问:http://localhost:5555/app/test?a=2&accessToken=123
使用用户 admin admin 登录  会看到页面
使用test test登录会提示无访问权限

访问先走 security的十几个过滤器  通过delegatingFilterProxy先走
然后再走zuul的自己的过滤器  
