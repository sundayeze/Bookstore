package com.interswitch.bookstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "cart_id", unique = true)
//    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "purchase_history_id")
    @JsonIgnore
    private PurchaseHistory purchaseHistory;

    private boolean paymentSuccessful;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }

    public void setPaymentSuccessful(boolean paymentSuccessful) {
        this.paymentSuccessful = paymentSuccessful;
    }

    public PurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(PurchaseHistory purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }
}

