## 도서관리 프로젝트(CRUD)
### (타임 리프 -> REST API with React)


###(Thymeleaf CRUD Without RestAPI) <br>

https://github.com/user-attachments/assets/14d9a74c-d745-44cf-8114-180eaef273cd


<hr>

### 기능
- insertBook
- selectBookDetail
- updateBook
- deleteBook
- selectFileInfo

<hr>

### 컨트롤러
- 책 정보 쓰기 화면 요청을 처리 
- 책 정보 리스트
- 책 정보 저장 요청을 처리 
- 상세 조회 요청을 처리
- 수정 요청을 처리
- 삭제 요청을 처리
- 이미지 다운로드를 처리

<hr>

### DB
#### BookImage(책사진관련 테이블)
![image](https://github.com/user-attachments/assets/e8746c8d-2287-420e-ab6b-5bc388733766)


#### Books(책 정보 테이블)
![image](https://github.com/user-attachments/assets/bc1f18d6-3b28-40c5-9d5d-a47d940c18d5)

### ERD
![image](https://github.com/user-attachments/assets/3a1461c2-1d5d-4903-a8cb-ecf7bcf01565)


<hr>

### 트러블 슈팅
#### Create 시, 풀판일이 들어가지 않는 오류
-> 해결. 타임리프에서 input 필드의 id와 name이 publishedDt로 되어있어야 하는데, 나는 published_Date 이렇게 되어있었음.

#### 파일 업로드하는 기능을 만들고 실행, 오류가 발생
-> 해결 book.BookDto의 path가 wrong path이라는 오류 <br>
-> book-sql.xml (마이바티스의 sql 쿼리) 의 resultType이 잘못된 경로였다는 점. <br>
-> 또한, bookId가 저장된 이후에, BookImages에 접근하여 사진을 저장해야한다는 사실. (즉, 순서가 중요하다.) <br>
-> bookDto에 값이 저장되지 않았는데, bookFileDto를 처리할 수는 없다. <br>


#### isbn 중복 처리
-> 미해결. isbn이 unique라서, 중복될 수 없음에 중복 처리를 해줘야함.

<hr>

### 추후 개선사항 & 공부해야 할 것들
- 예외 처리 페이지
- Aop와 인터셉터 사용시점과 트랜잭션 요청의 위치 확인하기
- 로깅
- 마이바티스를 제대로 이해하고, book-sql.xml 파일 리팩토링
- 데이터의 흐름
