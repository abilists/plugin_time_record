# plugin_time_record <a href="http://www.abilists.com" ><img src="https://github.com/minziappa/abilists_client/blob/master/src/main/webapp/static/apps/img/abilists/logo01.png" height="22" alt="Abilists"></a>

![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/abilists/plugin_time_record)

![markdown](https://github.com/abilists/plugin_time_record/blob/master/doc/img/record01.png)

Plugin_time_record는 어빌리스츠에 설치해서 무료로 쓸 수 있는 플러그인 입니다. 
 심플하게 출근 퇴근시간을 관리합니다. 어빌리스츠를 먼저 설치해 주시기 바랍니다.

## 필요한 시스템 환경

* [Abilists](http://www.abilists.com/home/download)

---

## New in v0.1.0

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

### 플랫폼 [어빌리스츠](http://www.abilists.com/home) 설치하기

**[*Docker*](http://www.abilists.com/home/docker)와 함께 설치**

1, Download the image of Docker for Abilists
```
$ docker pull abilists/tomcat8.5:0.7.7
```
2, Start the tomcat on Docker
```
$ docker container run -d -p 80:8080 -v ~/.abilists:/root/.abilists abilists/tomcat8.5:0.7.7
```

### 근태관리 플러그인 설치

1. `파트너 아이디` 등록하기
<img src="https://github.com/abilists/plugin_time_record/blob/master/doc/img/admin02.png" width="100%" title="Registering a partner Id" alt="Register a partner Id" style="border: 1px solid #eeeeec;"></img>

2. 근태관리 `플러그인`이 표시
![markdown](https://github.com/abilists/plugin_time_record/blob/master/doc/img/admin03.png)

3. `인스톨` 버튼 누름후
![markdown](https://github.com/abilists/plugin_time_record/blob/master/doc/img/admin05.png)

4. 톰캣을 재시작

**Docker**로 설치했을 경우, `톰캣 재시작`
```
$ docker ps -a
$ docker stop <CONTAINER ID>
$ docker start <CONTAINER ID>
```

아래는 예를 들은 경우 입니다.(@에러가 났을 때에는 한번더 재시작 해주시기 바랍니다.)
```
$ docker ps -a
CONTAINER ID        IMAGE                      COMMAND             CREATED             STATUS              PORTS                  NAMES
1f297cc69e9a        abilists/tomcat8.5:0.7.7   "catalina.sh run"   7 minutes ago       Up 7 minutes        0.0.0.0:80->8080/tcp   nice_goldberg
$ docker stop 1f297cc69e9a
1f297cc69e9a
$ docker start 1f297cc69e9a
1f297cc69e9a
```

**ROOT.war**로 설치했을 경우, `톰캣 재시작`
```
$ /usr/local/tomcat/bin/shutdown.sh 
$ /usr/local/tomcat/bin/startup.sh 
```

** 근태관리 주요기능 **
* **출근버튼** : 출근 시간을 기록합니다.
* **퇴근버튼** : 퇴근 시간을 기록합니다.
* **근무시간** : 근무시간을 표시합니다.
* **코멘트** : 휴가 및 결근에 대한 이유를 언급합니다.

## How to develop
**어빌리스츠에 필요한 유틸리티 설치하기**

아래의 유틸리티를 Clone을 해서 Local에 설치할 필요가 있습니다.

* [io.utility:security:0.0.1](https://github.com/abilists/security_utility)
* [io.utility:letter:0.0.7](https://github.com/abilists/letter_utility)
* [io.utility:api:0.0.4](https://github.com/abilists/api_utility)

Local 시스템에서 설치합니다.
```
$ gradle install
```

**Local시스템에서 실행하기**

Gradle과 함께 Jetty를 통해서 다음의 URL로 접속을 할 수 있습니다.
```
$ gradle jettyRun
```
Product용 Jar파일 생성하기
```
$ gradle -b ./probuild.gradle buildJar
```
**Local브라우저에서 확인하기**
* http://localhost:9005/plugins/timerecord/index

## License
This software is licensed under the MIT © Abilists.
