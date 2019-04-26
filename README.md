# Simple-Scala-CRUD-Application-in-Lagom-Using-Embedded-Cassandra

#Simple Lagom Scala CRUD

In this lagom application, we used embedded Cassandra to store the data and persistence entity to store the events.


We created only one microservice in this project which is ProductService which contains two methods:

1.addProduct(product:Product)
2.getProduct(id:String)

addProduct(product: Product) method is used to add the product to the embedded Cassandra and getProduct(id: String) method is used to retrieve the particular product from the id.

#Steps to run the Project:

1. Clone the project:
	
$git clone https://github.com/azmathasan92/Simple-Scala-CRUD-Application-in-Lagom-Using-Embedded-Cassandra.git


2.  Go to the Project folder:

$cd Simple-Scala-CRUD-Application-in-Lagom-Using-Embedded-Cassandra


3. Run the Lagom Project:

$sbt runAll

It will take some time to run the servers ...

Once the server is started you can add the product in the Cassandra database.

#Adding a product into the application:


#Getting the product with their product id:

//get the product with their id, you must specify the id in this route on :id place

https://localhost:9000/products/get/product/:id


Now you have created a successful insert and retrieve application on lagom framework using embedded Cassandra 

Thank you
