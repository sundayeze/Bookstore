Book Store  

Introduction  
A simple online bookstore application built with Java & Spring Boot, allowing users to search books, add them to a cart, simulate checkout via different payment methods, and view purchase history.  

Features  
- Book Inventory: Manage available books.  
- Search Functionality: Search books by title, author, genre, or year.  
- Shopping Cart: Add, view.
- Simulated Checkout: Supports Web, USSD, and Transfer payment options.  
- Purchase History: Users can view their purchases.  
- Unit & Integration Testing: – Ensures system reliability.  

Tech Stack  
-Java 17
-Spring Boot 
- Maven
- DB of choice (Currently running on H2, for dev) 
 

Setup Instructions  
-Clone the Repository: git clone https://github.com/sundayeze/Bookstore.git  
- Build and Run the Application



Access the Application 
- API Base URL: http://serverUrl:1000

API Endpoints  
Book Management   
- GET /books/search?title=bookTitle - Search books by title
- GET /books/search?author=author - Search books by author
- GET /books/search?genre=genre - Search books by genre 
- GET /books/search?year=year - Search books by year

Shopping Cart  
- POST /cart/add - Add a book to the cart  
- GET /cart/cartId -  View cart contents  

Checkout & Purchase History  
- POST /checkout - Checkout via Web, USSD, or Transfer  
- GET /purchase-history/{userId} - View user’s purchase history  


