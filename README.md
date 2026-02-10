# Spring AI + PGVector RAG Application

A Retrieval Augmented Generation (RAG) application built with **Spring Boot**, **Spring AI**, and **PostgreSQL + PGVector**.

This project demonstrates how to store embeddings in Postgres and use them to retrieve relevant context for LLM-powered responses.

---

## 🚀 Features

- Spring Boot REST API  
- Spring AI integration  
- PGVector for vector storage & similarity search  
- Document ingestion & embedding  
- Context-aware AI responses  
- Docker support for database  

---

## 🧱 Architecture

User Query → Embedding → Vector Search (PGVector) → Context Retrieval → LLM → Response

---

## 🛠 Tech Stack

- Java 25+  
- Spring Boot  
- Spring AI  
- PostgreSQL  
- PGVector  
- Maven  
- Ollama / OpenAI (depending on configuration)

---

## ⚙️ How to Run the Application

### 1️⃣ Install Docker & Start PGVector database

## 👨‍💻 Author

**Ravikiran Jakkannavar**  
Software Engineer | Programmer | AI Enthusiast  


```bash
docker compose up -d
