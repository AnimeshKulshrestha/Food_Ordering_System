CREATE DATABASE FastFoodCenter;
USE FastFoodCEnter;
CREATE TABLE Food_Prd (ID INT NOT NULL  PRIMARY KEY,Name VARCHAR(30),Price FLOAT NOT NULL,Cuisine VARCHAR(30),Image LONGBLOB);
SELECT * FROM Food_Prd;
drop table Food_Prd;
CREATE TABLE transactions(OrderID INT NOT NULL PRIMARY KEY,CustomerID INT ,total_price FLOAT,tip FLOAT,orderDate DATETIME,tax FLOAT,FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID));
CREATE TABLE Customers(CustomerID INT NOT NULL PRIMARY KEY,Username VARCHAR(50),Password VARCHAR(50));
INSERT INTO Customers(CustomerID,Username,Password) VALUES (0,null,null);
DELETE FROM transactions;
SELECT * FROM transactions;
SELECT * FROM Customers;
DELETE FROM Customers
WHERE CustomerID > 0;
DELETE FROM transactions;
WHERE CustomerID IN (4,5,7,8,9);
SELECT * FROM Table4;
DROP TABLE Table1;

