package com.interswitch.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_history_id")
    private Long id;


//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long purchase_history_id;

    @OneToMany(mappedBy = "purchaseHistory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Checkout> checkouts = new ArrayList<>();


//    @OneToMany(mappedBy = "purchaseHistory", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @JsonManagedReference
//    private List<Checkout> checkouts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private AppUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Checkout> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(List<Checkout> checkouts) {
        this.checkouts = checkouts;
    }


    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
