# plugin_time_record <a href="http://www.abilists.com" ><img src="https://github.com/minziappa/abilists_client/blob/master/src/main/webapp/static/apps/img/abilists/logo01.png" height="22" alt="Abilists"></a>

![markdown](https://github.com/abilists/plugin_time_record/blob/master/doc/img/record01.png)

Plugin_time_record는 어빌리스츠에 설치해서 무료로 쓸 수 있는 플러그인 입니다. 심플하게 출근관 퇴근시간을 관리합니다. 어빌리스츠를 먼저 설치해 주시기 바랍니다.

## 필요한 시스템 환경

* [Abilists](http://www.abilists.com/home/download)

---

## v0.1.0에 기능

- 출근 카드
- 퇴근 카드
- 결근에 자동 채워지기
- 자동 근무시간
- 근무관련 코멘트 쓰기

## See Also

- **[Abilists](https://github.com/abilists/abilists_client)**
- **[abilists_plugins](https://github.com/abilists/abilists_plugins)**
- **[abilists_docker](https://github.com/abilists/abilists_docker)**
- **[paging](https://github.com/abilists/paging)**

---

## How to install


### 어빌리스츠 설치하기

** Docker와 함께 설치 **

[*Docker*](http://www.abilists.com/home/docker)로 설치를 하시면 쉽게 설치가 가능합니다.

1, Download the image of Docker for Abilists
```
$ docker pull abilists/tomcat8.5:0.7.7
```
2, Start the tomcat on Docker
```
$ docker container run -d -p 80:8080 -v ~/.abilists:/root/.abilists abilists/tomcat8.5:0.7.7
```

** 근태관리 플러그인 설치 **

![markdown](https://github.com/abilists/plugin_time_record/blob/master/doc/img/admin01.png)



```
joonk@joonk-mint-linux /usr/local/tomcat $ ./bin/startup.sh 
Using CATALINA_BASE:   /usr/local/tomcat
Using CATALINA_HOME:   /usr/local/tomcat
Using CATALINA_TMPDIR: /usr/local/tomcat/temp
Using JRE_HOME:        /usr
Using CLASSPATH:       /usr/local/tomcat/bin/bootstrap.jar:/usr/local/tomcat/bin/tomcat-juli.jar
Tomcat started.
```

```
joonk@joonk-mint-linux /usr/local/tomcat $ ./bin/shutdown.sh 
Using CATALINA_BASE:   /usr/local/tomcat
Using CATALINA_HOME:   /usr/local/tomcat
Using CATALINA_TMPDIR: /usr/local/tomcat/temp
Using JRE_HOME:        /usr
Using CLASSPATH:       /usr/local/tomcat/bin/bootstrap.jar:/usr/local/tomcat/bin/tomcat-juli.jar
OpenJDK 64-Bit Server VM warning: ignoring option PermSize=64m; support was removed in 8.0
OpenJDK 64-Bit Server VM warning: ignoring option MaxPermSize=256m; support was removed in 8.0
```






* **Live Preview** : Edit Markdown while keeping an eye on the rendered HTML. Your edits will be applied immediately.
* **Scroll Sync** : Synchronous scrolling between Markdown and Preview. You don't need to scroll through each one separately.
* **Auto Indent** : The cursor will always be where you want it to be.
* **Syntax Highlight** : You can check broken Markdown syntax immediately.
