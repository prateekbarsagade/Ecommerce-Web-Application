package com.ecommerce.controller.admin;

public class AdminDescription {
	
//	The AdminProductController is responsible for product management by admins.
	
//    Admin can :- 
//    ->Create products
//    ->Update products
//    ->Delete products
//    ->View all products
//    ->Update stock
	
//	These are the APIs we will implement.
	
//	Operation	            API	                     Method
//	
//	Create product	    /admin/products	              POST
//	Update product	    /admin/products/{id}	       PUT
//	Delete product	    /admin/products/{id}	     DELETE
//	Get all products	/admin/products	                GET
//	Get product by id	/admin/products/{id}	        GET
//	Update stock	    /admin/products/{id}/stock	    PUT
	
	
	
	
	
//	The AdminOrderController is responsible for order management by admins.
	
//	    Admins can:
//
//		View all orders
//		View order details
//		Update order status
//		Cancel orders
	
//	These are the APIs we will implement
	
//	Operation	            Method	       API
	
//	Get all orders	          GET	    /admin/orders
//	Get order by id	          GET	    /admin/orders/{id}
//	Update order status	      PUT	    /admin/orders/{id}/status
//	Delete / cancel order	 DELETE	    /admin/orders/{id}
	
	
	
	
	
	//	The AdminCategoryController allows admins to manage the product catalog structure
	
//	Admins can:
//
//		Create categories
//		Update categories
//		Delete categories
//		View all categories
//		View category by id
	
//	These are the APIs we will implement
	
//	Operation	          Method	      API
	
//	Create category	       POST	      /admin/categories
//	Update category	        PUT       /admin/categories/{id}
//	Delete category	       DELETE	  /admin/categories/{id}
//	Get all categories	    GET	      /admin/categories
//	Get category by id	    GET   	  /admin/categories/{id}
	
	
	
	
	
	
	//The AdminUserController allowe admins manage the platform and handle fraud or misuse.
	
//	Admins can:
//
//		View all users
//		View user by id
//		Delete users
//		Block users (optional)
	
	
	//these are the apis we will implemets
	
//	Operation	        Method	  API
	
//	Get all users	      GET	/admin/users
//	Get user by id	      GET	/admin/users/{id}
//	Delete user	        DELETE	/admin/users/{id}
	
	

}
