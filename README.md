# ☕ Coffee Shop Backend API

커피 쇼핑몰 백엔드 서버입니다.  
상품 조회, 장바구니, 주문 기능을 제공하며 관리자용 API도 포함되어 있습니다.

---

## 🚀 배포 정보

| 항목 | 내용 |
|------|------|
| 배포 플랫폼 | Railway |
| 서버 주소 | `https://be-production-9ee1.up.railway.app` |
| Swagger UI | `https://be-production-9ee1.up.railway.app/swagger-ui/index.html` |

---

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 21 |
| Framework | Spring Boot 3.5 |
| ORM | Spring Data JPA |
| Database | MySQL |
| API 문서 | Swagger (springdoc-openapi) |
| 빌드 도구 | Gradle |
| 배포 | Railway (Docker) |

---

## 📁 프로젝트 구조

```
src/main/java/com/team02/be/
├── config/          # 설정 (CORS, Swagger)
├── controller/      # API 엔드포인트
├── service/         # 비즈니스 로직
├── repository/      # DB 접근
├── entity/          # JPA 엔티티
├── dto/             # 요청/응답 DTO
└── exception/       # 예외 처리
```

---

## 📌 API 목록

### 🛍 상품 (Product)

| Method | URL | 설명 |
|--------|-----|------|
| `GET` | `/api/products` | 상품 목록 조회 (필터링 가능) |
| `GET` | `/api/products/{id}` | 상품 단건 조회 |

**필터 파라미터**

| 파라미터 | 타입 | 설명 | 예시 |
|----------|------|------|------|
| `isDecaf` | Boolean | 디카페인 여부 | `true` |
| `roastingLevel` | String | 로스팅 단계 | `LIGHT` / `MEDIUM` / `DARK` |
| `acidity` | Boolean | 산미 여부 | `false` |

---

### 🛒 장바구니 (Cart)

쿠키 기반 비회원 장바구니입니다. 첫 상품 담기 시 `cart_token` 쿠키가 자동 발급됩니다 (유효기간 3일).

| Method | URL | 설명 |
|--------|-----|------|
| `POST` | `/api/cart/items` | 장바구니에 상품 추가 |
| `GET` | `/api/cart` | 장바구니 전체 조회 |
| `PATCH` | `/cart/items/{cartItemId}` | 장바구니 상품 수량 수정 |
| `DELETE` | `/cart/items/{cartItemId}` | 장바구니 상품 삭제 |

---

### 📦 주문 (Order)

| Method | URL | 설명 |
|--------|-----|------|
| `POST` | `/api/orders` | 주문 생성 |
| `GET` | `/api/orders/{id}` | 주문 상세 조회 |
| `GET` | `/api/orders?email={email}` | 이메일로 주문 내역 조회 |

**주문 상태 (OrderStatus)**

| 값 | 설명 |
|----|------|
| `PENDING` | 주문 대기 |
| `PROCESSING` | 처리중 |
| `SHIPPING` | 배송중 |
| `DELIVERED` | 배송 완료 |
| `CANCELLED` | 취소 |

---

### 🔧 관리자 (Admin)

| Method | URL | 설명 |
|--------|-----|------|
| `POST` | `/api/admin/products` | 상품 등록 |
| `PATCH` | `/api/admin/products/{productId}` | 상품 수정 |
| `DELETE` | `/api/admin/products/{id}` | 상품 삭제 |
| `GET` | `/api/admin/orders` | 주문 목록 조회 (페이징 + 필터) |

**주문 필터 파라미터**

| 파라미터 | 설명 | 예시 |
|----------|------|------|
| `order_status` | 주문 상태 필터 | `PENDING` |
| `product_name` | 상품명 검색 (부분 일치) | `콜롬비아` |
| `page` | 페이지 번호 (0부터 시작) | `0` |
| `size` | 페이지 크기 | `10` |

---

## ⚙️ 로컬 개발 환경 설정

### 1. `application.yaml` 생성

`src/main/resources/application.yaml` 파일을 직접 생성합니다.  
(보안상 gitignore 처리되어 있어 저장소에 포함되지 않습니다.)

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/team02_db?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: root
    password: 본인_비밀번호
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    open-in-view: false
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
    show-sql: true
```

### 2. 실행

```bash
./gradlew bootRun
```

### 3. Swagger 확인

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🌿 브랜치 전략

```
feature/* → dev → main
```

| 브랜치 | 설명 |
|--------|------|
| `main` | 운영 배포 브랜치 |
| `dev` | 통합 개발 브랜치 |
| `feat/*` | 기능 개발 브랜치 |
