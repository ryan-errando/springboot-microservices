# springboot-microservices
Authentication service and user management service created with spring boot framework by using jwt and spring boot security to do CRUD with REST

How to run the application ?

1. run it in your IDE (im using intellij IDEA)

2. open postman and fill the url : http://localhost:8080

3. if you want to add user into the database add "/api/adduser", and fill the requirement that included in **User** class
   
![image](https://github.com/user-attachments/assets/7a48dbea-302b-40b1-895b-ee523cbd1cdc)

4. if you want to login with your account you can add "/api/auth/login" and fill the username and password from your account, it will return the user information and the token that needed for authentication
   
![image](https://github.com/user-attachments/assets/edb37937-bba1-4a2f-8d77-53ae4f4b5bcf)

5. if you want to see the user detail you can add "/api/user/profile" and add the token you have from the login session into the header make sure to add **"Bearer "**
   
![image](https://github.com/user-attachments/assets/016ad958-0a96-439c-a213-c1431af3502c)

6. if you want to update your account profile you can add "/api/user/update" and add the token
    
![image](https://github.com/user-attachments/assets/0a5a1316-387c-4f60-ba09-cb2aa3026a6c)

