# oEmbed rest api

## Goal

- URL을 입력받고 [oEmbed](http://oembed.com/) 데이터를 수집하여 보여주는 서비스

## 주요 구현사항

- oEmbed Provider 목록을 외부 api에서 전부 받아와 사용함으로서 최대한 많은 Provider 호환성 제공
- restful한 예외처리 응답
- 로그구현
- 테스트 코드 작성
- EH캐시 라이브러리를 사용해 캐싱기능 구현

## 아쉬운점

- 인스타그램의경우 페이스북 정책에 따라 어플리케이션 검수가 완료되어야만 graph api접근이 가능하여 제공불가능 예외처리를 함

## 빌드 방법

- gradlew.bat build
- cd build/libs
- java -jar embedapi-0.0.1-SNAPSHOT.jar

## 도커와 도커허브를 사용해 ec2 서버에 배포

- 호스트:포트

http://3.38.36.238:8081/

- 헬스체크

[체크](http://3.38.36.238:8081/api/ping)


- 예시 링크

[유튜브 예시](http://3.38.36.238:8081/v1/oembed?url=https://www.youtube.com/watch?v=dBD54EZIrZo)

[트위터 예시](http://3.38.36.238:8081/v1/oembed?url=https://twitter.com/hellopolicy/status/867177144815804416)

[vimeo 예시](http://3.38.36.238:8081/v1/oembed?url=https://vimeo.com/20097015)
