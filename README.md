# Description
Spring boot integration 을 이용하여 다수의 RSS URL에서 주기적으로 feed 내용을 받아오는 샘플 코드입니다.

기본적인 Spring integration Feed 의 경우 하나의 RSS URL을 허용합니다~

하지만 크롤링을 목적으로 하는 경우 하나의 RSS URL만 사용할 수 없으므로 

여러개의 URL을 사용할 수 있도록 설정해 놓았습니다. 

현재는 Spring Boot 의 기본설정에 맞게 Annotaion 으로 설정되어 있으며,

XML 방식도 추가 할 계획입니다.
(Schedule 방식으로 인하여 Unit test code 는 생략하였습니다.)

