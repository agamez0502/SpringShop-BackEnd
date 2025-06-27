# 🛍️ SpringShop E-Commerce API & Frontend

![spring-shop-logo png](https://github.com/user-attachments/assets/5446fb34-52c3-4980-bd3d-8113f2de555a)

Welcome to **SpringShop**, a full-stack e-commerce application built using **Java**, **Spring Boot**, **MySQL**, and a pre-configured front-end. This project demonstrates a secure and scalable backend API to power a full online store, complete with product management, shopping cart functionality, user authentication, and profile management.

---

## 📦 Features

### ✅ Product Management
- Filter by category, price, or color
- Full CRUD for admin users
- View featured or in-stock products

### ✅ Category Management
- Browse all product categories
- Admins can add, update, and delete categories

### ✅ Shopping Cart (Logged-In Users)
- Add items to cart
- Update quantities
- Clear cart
- Cart total calculated dynamically

### ✅ User Authentication
- JWT-based login and registration
- Role-based access for `user` and `admin`

### ✅ User Profile
- View current profile
- Update personal info: name, phone, address, etc.

---

## 🚀 Technologies Used

| Tech Stack    | Description                            |
|---------------|----------------------------------------|
| Java          | Backend logic                          |
| Spring Boot   | RESTful API & Security                 |
| MySQL         | Relational database                    |
| JDBC          | Database access                        |
| Postman       | API testing                            |
| JWT           | Secure authentication                  |
| Git / GitHub  | Version control + deployment           |

---

## 🧪 API Endpoints Overview

| Method | Endpoint               | Description                       | Access         |
|--------|------------------------|-----------------------------------|----------------|
| GET    | `/products`            | Search or list products           | Public         |
| GET    | `/products/{id}`       | View a single product             | Public         |
| POST   | `/products`            | Add new product                   | Admin only     |
| PUT    | `/products/{id}`       | Update product                    | Admin only     |
| DELETE | `/products/{id}`       | Delete product                    | Admin only     |
| GET    | `/categories`          | List all categories               | Public         |
| GET    | `/categories/{id}`     | Get category by ID                | Public         |
| POST   | `/categories`          | Add new category                  | Admin only     |
| PUT    | `/categories/{id}`     | Update category                   | Admin only     |
| DELETE | `/categories/{id}`     | Delete category                   | Admin only     |
| GET    | `/cart`                | View user’s cart                  | Logged-in user |
| POST   | `/cart/products/{id}`  | Add product to cart               | Logged-in user |
| PUT    | `/cart/products/{id}`  | Update cart item quantity         | Logged-in user |
| DELETE | `/cart`                | Clear the cart                    | Logged-in user |
| GET    | `/profile`             | View user profile                 | Logged-in user |
| PUT    | `/profile`             | Update user profile               | Logged-in user |

> 💡 **Phase 5 (Checkout)** was considered but not implemented in this version.

---

## 🔐 Authentication Notes

- Users login via `/login` with a username/password.
- The returned JWT must be included in all protected requests:

---

## 🖼️ Screenshots
- 🛍️ Product listing screen  
- 🛒 Cart with items  
- 👤 Profile update form  
- 🔐 Login screen  

---

## 🔮 Future Versions (Wishlist Features)

These are ideas for improving SpringShop in future versions — inspired by real e-commerce experiences like Amazon, Etsy, and more.

| Feature            | Description |
|--------------------|-------------|
| ⭐ **Wishlist / Favorites**  | Allow users to save favorite products for later |
| ⭐ **Product Reviews**       | Enable star ratings and comments per product |
| ⭐ **Admin Dashboard**       | Central UI to manage users, products, and orders |
| ⭐ **Discount Codes**        | Support promo codes that apply cart-wide discounts |

> These would require additional tables (e.g. `wishlist`, `reviews`, `discounts`) and frontend UI support.

---

## 🧠 Reflections

This capstone challenged me to apply everything I’ve learned about:
- DAO patterns
- Secure user flows
- Role-based access control
- Postman testing
- Real-world backend development

It’s been so rewarding to see everything come together 💖

---

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00758F?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Made with Love](https://img.shields.io/badge/Made%20with-%F0%9F%A4%A9%20corazón-ff69b4?style=for-the-badge)

---

### ✨ Created with care by  
#### **La Dev Creativa** – Alondra Gamez 👩🏽‍💻🌸  
> Building beautiful backends & coding with corazón 💕
