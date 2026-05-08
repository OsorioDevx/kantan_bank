# 🏦 KantanBank

> Console-based banking system built with vanilla Java, intentionally developed without external libraries as a learning project focused on Java fundamentals and practical Object-Oriented Programming

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Console](https://img.shields.io/badge/Interface-Console-blue?style=flat-square)
![OOP](https://img.shields.io/badge/Paradigma-POO-green?style=flat-square)
![No frameworks](https://img.shields.io/badge/Frameworks-Nenhum-lightgrey?style=flat-square)

---

## 📌 About the project

JavaBank is a functional mini banking system that runs entirely in the terminal. The project was created with an educational purpose: every class and method was designed to teach Java concepts in a practical and natural way, without artificially forcing language features into the codebase.
The current version uses no frameworks or databases, keeping the focus on core Java fundamentals and Object-Oriented Programming. As my knowledge evolves, future versions of the project will include more advanced features, database integration, and external libraries.

If you're learning Java and want to see concepts like Inheritance, Polymorphism, Interfaces, and Abstract Classes applied in a real and cohesive project, this is the perfect project to study and recreate to solidify your understanding.

---

## ✅ Features

- Create accounts (Checking or Savings)
- View all registered accounts
- Deposit and withdraw funds with validation rules
- Transfer money between accounts
- Check balances with account-specific details
- View transaction history and account statements
- Close accounts
- Simulate savings yields and checking account fees
- View overall bank statistics

---

## 🧠 Concepts covered

### Java Fundamentals
| Concept | Where it is used |
|---|---|
| Primitive types (`int`, `double`, `boolean`) | Attributes of the `Account` and `Client` classes |
| Reference Types (non primitive) (`String`, objects, arrays) | `transactionHistory: String[]`, `accountHolder: Client` |
| Input validation using Scanner | `Main` - All methods `read*()` |
| Conditional statements and the ternary operator | Validation, status, overdrafts alert |
| Switch-case | Main menu implemented in `Main` |
