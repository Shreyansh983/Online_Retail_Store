package com.retailstore.cart.service;

import com.retailstore.cart.VO.Product;
import com.retailstore.cart.entity.Cart;
import com.retailstore.cart.entity.LineItem;
import com.retailstore.cart.exception.CartNotFoundException;
import com.retailstore.cart.external.service.ProductService;
import com.retailstore.cart.repository.CartRepository;
import com.retailstore.cart.repository.LineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;

    @Autowired
    LineItemRepository lineItemRepository;

    public Cart addCart(Long customerId, Long productId) throws CartNotFoundException {
        Cart cart = (Cart)cartRepository.findByCustomerId(customerId).orElseThrow(()-> new CartNotFoundException("Cart with Customer Id '"+customerId+"' Not Found"));
        Product product;
        try {
            product = productService.getProducts(productId);
        }catch(RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        List<LineItem> list = cart.getLineItems();
        LineItem lineItem = createLineItem(product);
        if(checkIfProductAlreadyExistInCart(list, productId)) {
            list= increaseQuantity(list, productId);
            cart.setLineItems(list);
        }else {
            cart.getLineItems().add(lineItem);
            lineItem.setCart(cart);
        }
        return cartRepository.save(cart);
    }

    public boolean checkIfProductAlreadyExistInCart(List<LineItem> lineItems,Long productId) {

        for(int i=0;i<lineItems.size();i++) {
            LineItem lineItem = lineItems.get(i);
            if(lineItem.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    public List<LineItem> increaseQuantity(List<LineItem> lineItems,Long productId) {

        for(int i=0;i<lineItems.size();i++) {
            LineItem lineItem = lineItems.get(i);
            if(lineItem.getProductId().equals(productId)) {
                Long quant = lineItem.getQuantity();
                quant++;
                lineItem.setQuantity(quant);
                break;
            }
        }
        return lineItems;
    }

    public Cart emptyCart(Long customerId) throws CartNotFoundException {
        Cart cart = (Cart)cartRepository.findByCustomerId(customerId).orElseThrow(()-> new CartNotFoundException("Cart with Customer Id '"+customerId+"' Not Found"));
        try {
            for(LineItem lineItem : cart.getLineItems()) {
                cart.getLineItems().remove(lineItem);
                lineItemRepository.delete(lineItem);
            }
        }catch(RuntimeException e) {
            System.out.println("Empty Cart");
        }
        return cartRepository.save(cart);
    }

    public Cart searchCart(Long customerId) throws CartNotFoundException {
        return (Cart) cartRepository.findByCustomerId(customerId).orElseThrow(()-> new CartNotFoundException("Cart with Customer Id '"+customerId+"' Not Found"));
    }

    public Cart createCart(Long userId) {
        UUID ranUUID = UUID.randomUUID();
        Long randomCartId = ranUUID.getMostSignificantBits();
        Cart cart = new Cart();
        cart.setCartId(randomCartId);
        cart.setCustomerId(userId);
        return cartRepository.save(cart);
    }

    public void deleteCart(Long customerId) throws CartNotFoundException {
        Cart cart = (Cart) cartRepository.findByCustomerId(customerId).orElseThrow(()-> new CartNotFoundException("Cart with Customer Id '"+customerId+"' Not Found"));
        cartRepository.delete(cart);
    }

    public Cart removeProduct(Long customerId,Long productId) throws CartNotFoundException {
        Cart cart = (Cart)cartRepository.findByCustomerId(customerId).orElseThrow(()-> new CartNotFoundException("Cart with Customer Id '"+customerId+"' Not Found"));

        try {
            for(LineItem lineItem : cart.getLineItems()) {
                if(lineItem.getProductId().equals(productId)) {
                    cart.getLineItems().remove(lineItem);
                    lineItemRepository.delete(lineItem);
                    break;
                }
            }
        }catch(RuntimeException e) {
            System.out.println("Product Not Exists in the cart");
        }
        return cartRepository.save(cart);
    }

    public LineItem createLineItem(Product product) {
        LineItem lineItem = new LineItem();
        UUID ranUUID = UUID.randomUUID();
        Long randomLineItemid = ranUUID.getMostSignificantBits();
        lineItem.setItemId(randomLineItemid);
        lineItem.setPrice(product.getProductPrice());
        lineItem.setProductId(product.getProductId());
        Long quant = (long) 1;
        lineItem.setQuantity(quant);
        lineItem.setProductName(product.getProductName());
        return lineItem;
    }
}
