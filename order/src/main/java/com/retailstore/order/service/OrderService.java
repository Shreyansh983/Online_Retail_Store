package com.retailstore.order.service;

import com.retailstore.order.entity.Order;
import com.retailstore.order.entity.OrderLineItem;
import com.retailstore.order.exception.OrderNotFoundException;
import com.retailstore.order.external.service.CartService;
import com.retailstore.order.repository.OrderLineItemRepository;
import com.retailstore.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CartService cartService;

    @Autowired
    OrderLineItemRepository orderLineItemRepository;

    public Order createOrder(Long customerId, List<OrderLineItem> lineItems) {

        Order order = new Order();
        UUID ranUUID = UUID.randomUUID();
        Long id = ranUUID.getMostSignificantBits();
        order.setOrderId(id);
        order.setCustomerId(customerId);

        order.setTotalPrice(getTotalPrice(lineItems));
        for(OrderLineItem line : lineItems) {
            line.setItemId(UUID.randomUUID().getMostSignificantBits());
            line.setOrder(order);
        }
        order.setLineItems(lineItems);

        cartService.emptyCart(customerId);
        return orderRepository.save(order);
    }

    public List<Order> getOrderByCustomer(Long customerId) throws OrderNotFoundException {
        return orderRepository.findByCustomerId(customerId).orElseThrow(()-> new OrderNotFoundException("No Orders found with Customer Id '"+customerId+"'"));
    }

    public void deleteOrder(Long orderId) throws OrderNotFoundException {
        Order o = orderRepository.findById(orderId).orElseThrow(()->  new OrderNotFoundException("Order with Order Id '"+orderId+"' Not Found"));
        orderRepository.delete(o);
    }

    public Order getOrderByOrderId(Long orderId) throws OrderNotFoundException {
        Order o = orderRepository.findById(orderId).orElseThrow(()->  new OrderNotFoundException("Order with Order Id '"+orderId+"' Not Found"));
        List<OrderLineItem>  listItems=orderLineItemRepository.findAll();
        List<OrderLineItem> orderItems = new ArrayList<>();
//		for(OrderLineItemEntity item : listItems) {
////			if(item.getOrder().getOrderId().equals(orderId)) {
////				orderItems.add(item);
////			}
//			System.out.println(item.getOrder().getOrderId());
//		}
////		o.setLineItems(orderItems);
        return o;
    }

    public void deleteOrderByCustomer(Long customerId) {
        List<Order> o = orderRepository.findByCustomerId(customerId).get();
        for(Order order : o) {
            orderRepository.delete(order);
        }
    }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public double getTotalPrice(List<OrderLineItem> lineItem) {
        double total = 0;
        for(OrderLineItem li : lineItem) {
            total =total + (li.getQuantity()*li.getPrice());
        }
        return total;
    }

}
