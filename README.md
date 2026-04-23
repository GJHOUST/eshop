# E-shop REST API

Backendová aplikace postavená na Spring Boot (Java 21), která poskytuje REST API pro správu produktů.

Projekt slouží jako ukázka backendové architektury včetně JWT autentizace a napojení na databázi.

⚠️ Poznámka: Pokud chcete využívat uživatelské (user) endpointy, je nutné si aplikaci naklonovat z GitHubu (nebo stáhnout) a spustit lokálně. Zároveň je potřeba ručně nastavit PostgreSQL databázi a spravovat uživatelské role přímo v databázi.

Dostupné role:

ADMIN,
USER.

## Deployment

API je nasazené a dostupné zde: **https://eshop-production-474b.up.railway.app**

## Technologie

- **Spring Boot 3.4.2**
- **Java 21**
- **JWT (JSON Web Tokens)** pro autentizaci
- **Spring Security**
- **PostgreSQL** databáze
- **Maven** pro build management

## Autentizace

API používá JWT (JSON Web Token) pro autentizaci. Všechny chráněné endpointy vyžadují platný JWT token v headeru `Authorization`.

### Jak získat token:
1. Registrujte se na `/api/auth/register`
2. Přihlaste se na `/api/auth/login` a obdržíte JWT token
3. Přidejte token do headeru při dalších requestech

## Testování v Postmanu

API se testuje pomocí **Postmanu** zasíláním HTTP requestů.

### Import Postman Collection

V Postmanu vytvořte nový request s následujícím nastavením:

## API ENDPOINTS

### Authentication (`/api/auth`)

#### Register
```
POST /api/auth/register
```
**Body:**
```json
{
  "email": "user@example.com",
  "password": "heslo123",
  "confirmPassword": "heslo123",
  "userName": "jmeno",
  "phone": "+420123456789",
  "dateOfBirth": "2001-03-03"
  "address": "Ulice 123, Praha"
}
```

#### Login
```
POST /api/auth/login
```
**Body:**
```json
{
  "email": "user@example.com",
  "password": "heslo123"
}
```
**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userName": "jmeno",
  "role": "USER"
}
```
### Products (`/api/products`)

#### Create Product (ADMIN)
```
POST /api/products/create
```
**Body:**
```json
{
  "name": "Laptop",
  "price": 25000
}
```

#### Get All Products
```
GET /api/products?page=0&size=10
```

#### Get Product by ID
```
GET /api/products/{id}
```

#### Update Product (ADMIN)
```
PUT /api/products/{id}
```
**Body:**
```json
{
  "name": "Laptop Updated",
  "price": 26000
}
```

#### Delete Product (ADMIN)
```
DELETE /api/products/{id}
```

### Cart (`/api/cart`)

#### Create Cart
```
POST /api/cart
```
**Body:**
```json
{
  "userId": "12345678-1234-1234-1234-123456789012"
}
```

#### Get Cart by ID
```
GET /api/cart/{id}
```

#### Get Cart by User ID
```
GET /api/cart/by-user/{userId}
```

#### Add Item to Cart
```
POST /api/cart/{cartId}/items
```
**Body:**
```json
{
  "productId": "12345678-1234-1234-1234-123456789012",
  "quantity": 2
}
```

#### Update Cart Item
```
PUT /api/cart/{cartId}/items/{itemId}
```
**Body:**
```json
{
  "quantity": 3
}
```

#### Remove Item from Cart
```
DELETE /api/cart/{cartId}/items/{itemId}
```

#### Delete Cart
```
DELETE /api/cart/{id}
