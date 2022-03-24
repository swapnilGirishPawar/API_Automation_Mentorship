package functionalTesting;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public class ecom_product {
	
	public String url = "https://ecommerceservice.herokuapp.com";
	public String accessToken;
	public String productid;
	public String CreatedOrderId;
	public String CreatedProductId;
	
	@Test (priority = 0, enabled = true)
	public void signup()
	{
		RestAssured.baseURI = url;
		
		String requestBody = "{\r\n"
				+ "	\"email\": \"swapnilpawar322283@gmail.com\",\r\n"
				+ "	\"password\": \"swapnil@12321\"\r\n"
				+ "}\r\n"
				+ "";
		 
				given().header("content-Type","application/json")
				.body(requestBody)
				.when()
				.post("/user/signup")
				.then().assertThat().statusCode(201).and().contentType(ContentType.JSON);
	}

	@Test (priority = 1, enabled = true)
	public void login()
	{
		RestAssured.baseURI = url;
		
		String requestBody = "{\r\n"
				+ "	\"email\": \"swapnilpawar32223@gmail.com\",\r\n"
				+ "	\"password\": \"swapnil@12321\"\r\n"
				+ "}\r\n"
				+ "";
		
		Response response = 
				given().header("content-Type","application/json")
				.body(requestBody)
				.when()
				.post("/user/login")
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();
		
		String jsonData = response.asString();
		JsonPath responseBody = new JsonPath(jsonData);
		// Storing the access token.
		accessToken = responseBody.get("accessToken");
		System.out.println("Access Token = "+ accessToken);
	}
	
	@Test (priority = 2, enabled = true)
	public void getAllProducts()
	{
		RestAssured.baseURI = url;
		
		Response response = 
				given().header("content-Type","application/json")
				.header("Authorization","Bearer "+accessToken)
				
				.when().get("/products")
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();
				
		String jsonData = response.asString();
		JsonPath responseBody = new JsonPath(jsonData);
		System.out.println(responseBody.get("products[10]._id"));
// Note:- it is not printing all products in console, but showing everything in command prompt.
	}
	
	@Test (priority = 3, enabled = true)
	public void createProduct()
	{
		RestAssured.baseURI = url;
		
		String requestBody = "{\r\n"
				+ "	\"name\": \"Samsung LED\",\r\n"
				+ "	\"price\": 96500\r\n"
				+ "}\r\n"
				+ ""; 
		
		Response response = 
				given().header("content-Type","application/json")
				.header("Authorization","Bearer "+accessToken)
				.body(requestBody)
				
				.when().post("/products")
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();
				
		String jsonData = response.asString();
		JsonPath responseBody = new JsonPath(jsonData);
		System.out.println(responseBody.get("product._id"));
		productid = responseBody.get("product._id");
	}
	
	@Test (priority = 4, enabled = true)
	public void getProductById()
	{
		RestAssured.baseURI = url;
		
		Response response = 
				given().header("content-Type","application/json")
				.header("Authorization","Bearer "+accessToken)
				
				.when().get("/products/"+productid)
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();
				
		String jsonData = response.asString();
		JsonPath responseBody = new JsonPath(jsonData);
		System.out.println(responseBody.get());
	}

	@Test (priority = 5, enabled = true)
	public void updateById()
	{
		RestAssured.baseURI = url;
		
		String requestBody = "{\r\n"
				+ "	\"name\": \"OnePlus TV\",\r\n"
				+ "	\"price\": 98457\r\n"
				+ "}\r\n"
				+ "";
		
		Response response = 
				given().header("content-Type","application/json")
				.header("Authorization","Bearer "+accessToken)
				.body(requestBody)
				
				.when()
				.patch("/products/"+productid)
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();
		
		String jsonData = response.asString();
		JsonPath responseBody = new JsonPath(jsonData);
		System.out.println(responseBody.get());
	}
	
	@Test (priority = 6, enabled = true)
	public void deleteProductById()
	{
		RestAssured.baseURI = url;
		
		Response response = 
				given().header("content-Type","application/json")
				.header("Authorization","Bearer "+accessToken)
				
				.when().delete("/products/"+productid)
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();
				
		String jsonData = response.asString();
		JsonPath responseBody = new JsonPath(jsonData);
		System.out.println(responseBody.get());
	}

	@Test(priority = 7, enabled = true)
	public void get_all_Orders()
	{
		RestAssured.baseURI = url;
		
		Response response = 
				given().header("content-Type","application/json")
				.header("Authorization","Bearer "+accessToken)
				
				.when().get("/orders")
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();
				
		String jsonData = response.asString();
		JsonPath responseBody = new JsonPath(jsonData);
		
	}
	
	@Test(priority = 8, enabled = true)
	public void create_order()
	{
		RestAssured.baseURI = url;
		String requestBody = "{\r\n"
				+ "	\"product\": \"62283c6dd7cea500172f11d2\",\r\n"
				+ "	\"quantity\": 77\r\n"
				+ "}\r\n"
				+ "";
		
		Response response =
		given().header("content-Type","application/json")
		.header("Authorization","Bearer "+accessToken)
		.body(requestBody).
		when().post("/orders").
		then().assertThat().statusCode(201).and().contentType(ContentType.JSON).
		extract().response();
		
		String jsonData = response.asString();
		JsonPath responseBody = new JsonPath(jsonData);
		CreatedOrderId = responseBody.get("order._id");
		System.out.println("Created Order ID = "+CreatedOrderId);
		
		CreatedProductId = responseBody.get("order._id");
		System.out.println("Created Product ID = "+CreatedProductId);
		
	}
	
	@Test(priority = 9, enabled = true)
	public void get_order_by_id()
	{
		RestAssured.baseURI = url;
		
		Response response = 
				given().header("content-Type","application/json")
				.header("Authorization","Bearer "+accessToken)
				
				.when().get("/orders/"+CreatedOrderId)
				.then().assertThat().statusCode(201).and().contentType(ContentType.JSON)
				.extract().response();
				
		String jsonData = response.asString();
		JsonPath responseBody = new JsonPath(jsonData);
		System.out.println(responseBody.get("product.name"));
	}
	
	@Test(priority = 10, enabled = true)
	public void delete_order_by_id()
	{
		
RestAssured.baseURI = url;
		
		Response response = 
				given().header("content-Type","application/json")
				.header("Authorization","Bearer "+accessToken)
				
				.when().delete("/orders/"+CreatedOrderId)
				.then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();
		
				
		String jsonData = response.asString();
		JsonPath responseBody = new JsonPath(jsonData);
		System.out.println(responseBody.get("message"));
	}
}
