# ⚡ Electrician Management System (EMS)

A full-stack **Electrician Management System** built with **Spring Boot (Backend)** and **Angular (Frontend)** to manage electricians, workers, attendance, salary, billing, and subscriptions.


## 🚀 Project Overview

This system is designed for contractors/electricians to manage:

* 👷 Workers
* 📍 Work Sites
* 📄 Quotations
* 🧾 Final Billing
* 💰 Owner Payments
* 📅 Attendance
* 💵 Salary & Withdrawals
* 📊 Admin Dashboard & Subscription Plans


## 🏗️ Tech Stack

### 🔹 Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Spring Security (Basic Auth + BCrypt)
* MySQL

### 🔹 Frontend

* Angular (Standalone Components)
* TypeScript
* HTML / CSS (Modern UI)


## 👥 User Roles & Access

| Role            | Access                               |
| --------------- | ------------------------------------ |
| **Admin**       | Full system access                   |
| **Electrician** | Manage workers, work sites, billing  |
| **Worker**      | View attendance, salary, withdrawals |


## 🔐 Security Flow

* BCrypt password encryption
* Spring Security with role-based access
* API protection:

  * `/admin/**` → ADMIN only
  * `/electrician/**` → ADMIN + ELECTRICIAN
  * `/worker/**` → ADMIN + ELECTRICIAN + WORKER


## 📌 Modules


### 🛠️ Admin Panel

* Admin Login
* Add Electrician
* View Electricians
* Subscribe Electrician to Plan
* Manage Subscription Plans
* Dashboard:

  * Total Electricians
  * Active Electricians
  * Plan Distribution (3/6/12 months)
  * Total Revenue


### ⚡ Electrician Panel

* Dashboard
* Worker Management
* Work Site Management
* Quotation Upload
* Final Bill Creation
* Owner Payment Tracking
* Attendance Management
* Salary Calculation
* Withdrawal Management


### 👷 Worker Panel

* Login
* Dashboard
* Attendance History
* Salary Summary
* Withdrawal History


## 🔄 System Flow

```text
Admin creates Electrician
        ↓
Electrician subscribes to plan
        ↓
Electrician creates Work Site
        ↓
Upload Quotation
        ↓
Owner gives advance payment
        ↓
Final Bill created after work completion
        ↓
System calculates:
   Total Paid
   Remaining Amount
        ↓
Worker attendance → Salary → Withdrawal
```


## 💰 Billing Flow

* Owner can pay **before final bill (Advance)**
* Final Bill created after work completion
* System auto-calculates:

```text
Remaining = Final Bill - Total Owner Payments
```

* Payment Status:

  * ✅ PAID
  * 🟡 PARTIAL
  * 🔴 PENDING

## 📂 Project Structure

```
EMS/
├── backend/ (Spring Boot)
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   └── security/
│
├── frontend/ (Angular)
│   ├── admin/
│   ├── electrician/
│   ├── worker/
│   ├── core/services/
│   └── routes/
```


## ⚙️ Setup Instructions

### 🔹 Backend

1. Clone repo
2. Configure MySQL in `application.properties`
3. Run Spring Boot app

```bash
mvn spring-boot:run
```

---

### 🔹 Frontend

```bash
npm install
ng serve
```

Open:

```text
http://localhost:4200
```


## 🔥 Features Highlights

* ✔ Role-based security
* ✔ Advance payment support
* ✔ Dynamic billing calculation
* ✔ Modern Angular UI
* ✔ File upload (Quotation & Bills)
* ✔ Dashboard analytics
* ✔ Modular architecture

## 🚀 Future Improvements

* JWT Authentication
* Real-time notifications
* Mobile responsive improvements
* Charts (Revenue & Analytics)
* Multi-admin support
* Owner Panel


## 👨‍💻 Author

Aditya Talaviya

---

## ⭐ If you like this project

Give it a ⭐ on GitHub!
