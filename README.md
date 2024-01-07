# Library Management System

The Library Management System is a Java-based application with a graphical user interface (GUI) designed to facilitate the management of a library's book inventory. The system allows users to perform various operations such as adding, removing, viewing, borrowing, and returning books.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## Overview

The Library Management System is built using Java and Swing for the graphical user interface. It provides a simple and intuitive way for librarians or users to manage the library's book collection efficiently.

## Features

### 1. Add Book

Users can add new books to the library by providing the title and author details. The system automatically marks the book as available and not borrowed.

### 2. Remove Book

Librarians can remove existing books from the library. However, a book cannot be removed if it is currently borrowed. The system ensures that books are not deleted while on loan.

### 3. View Books

The application displays a list of available books, including information about their availability and borrowing status. If a book is borrowed, the borrower's name and contact details are also shown.

### 4. Borrow Book

Users can borrow a book by selecting from the available books. They need to provide their name and contact details, and the system updates the book's status accordingly.

### 5. Return Book

Borrowed books can be returned by selecting the book from the borrowed list. The system verifies the borrower's name and contact details before updating the book's status.

## Getting Started

### Prerequisites

Before running the application, ensure you have the following installed:

- Java Development Kit (JDK)
- Java Swing library

### Installation

1. Clone the repository:

   ```bash
   https://github.com/MdNihal005/JAVA/tree/main
