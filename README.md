# Server_Crawling
서버 크롤링 작업장


## 제노 요기요 크롤링 API  
### 현위치 주변 메뉴 조회(카테고리: 치킨)  
GET localhost:8080/yogiyo/restaurant?lat=37.54775437002297&lon=127.06083008344&category=치킨

### 현위치 주변 메뉴 조회(카테고리: all)  
GET localhost:8080/yogiyo/restaurant?lat=37.54775437002297&lon=127.06083008344

### 가게의 모든 메뉴 조회  
GET localhost:8080/yogiyo/313580/menu
--------------------------------------
## JDK 11.0.15 버전 다운 링크
- https://1drv.ms/u/s!AlxRVL_MeFUfgqYToHrGux4RMg36jQ?e=sBxFwe

## 요기요 API
### 요기요 매장정보 조회 API
GET https://www.yogiyo.co.kr/api/v1/restaurants-geo/?category=치킨&items=20&lat=37.54775437002297&lng=127.06083008344&page=0  
X-ApiSecret: fe5183cc3dea12bd0ce299cf110a75a2  
x-Apikey: iphoneap  
> headers:  
> - X-ApiSecret: 요기요 API 키값
> - X-Apikey: 키 종류
> 
> parameters:  
> - category: 메뉴 카테고리  
> - items: 불러올 매장의 개수
> - lat: 위도
> - lng: 경도
> - order: 정렬 기준(ex: review_count)
> - page: 조회 페이지 번호

### 요기요 매장의 메뉴 조회  
GET https://www.yogiyo.co.kr/api/v1/restaurants/533374/menu/  
X-ApiSecret: fe5183cc3dea12bd0ce299cf110a75a2  
x-Apikey: iphoneap  
> headers:  
> - X-ApiSecret: 요기요 API 키값
> - X-Apikey: 키 종류  
> - {restaurant} : 레스토랑 ID

