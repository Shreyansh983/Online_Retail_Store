package com.retailstore.order.controller;


import com.retailstore.order.entity.Order;
import com.retailstore.order.entity.OrderLineItem;
import com.retailstore.order.exception.OrderNotFoundException;
import com.retailstore.order.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api5")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/{customerId}")
	public ResponseEntity<?> createOrder(@PathVariable Long customerId,@RequestBody List<OrderLineItem> lineItems) {
		logger.info("Create Order Api Called : Received Customer Id and Line Items");
		Order order = orderService.createOrder(customerId, lineItems);
		logger.info("Create Order Api Called : Order Created");

		return new ResponseEntity<>(order,HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllOrder(){
		logger.info("Get All Order Api Called");
		List<Order> orders = orderService.getAllOrder();
		logger.info("Get All Order Api : Found All Products");
		return new ResponseEntity<>(orders,HttpStatus.OK);
	}
	
	@GetMapping("/customerOrder/{customerId}")
	public ResponseEntity<?> getOrderByCustomer(@PathVariable Long customerId) throws OrderNotFoundException {
		logger.info("Get Order Api Called : Received Customer Id");
		List<Order> order = orderService.getOrderByCustomer(customerId);
		logger.info("Get Order Api Called : Order Found");
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@GetMapping("/orderId/{orderId}")
	public ResponseEntity<?> getOrderByOrderId(@PathVariable Long orderId) throws OrderNotFoundException {
		logger.info("Get Order Api Called : Received Customer Id");
		Order order = orderService.getOrderByOrderId(orderId);
		logger.info("Get Order Api Called : Order Found");
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) throws OrderNotFoundException {
		logger.info("Delete Order Api Called : Received Order Id");
		orderService.deleteOrder(orderId);
		logger.info("Delete Order Api Called : Deleted Order");
		return new ResponseEntity<>("Order Deleted Successfully",HttpStatus.OK);
	}
	
	@DeleteMapping("deleteByCustomer/{customerId}")
	public ResponseEntity<?> deleteOrderByCustomer(@PathVariable Long customerId){
		logger.info("Delete Order Api Called : Received Customer Id");
		orderService.deleteOrderByCustomer(customerId);
		logger.info("Delete Order Api Called : Deleted Order");
		return new ResponseEntity<>("Order Deleted Successfully",HttpStatus.OK);
	}
	
	
}
