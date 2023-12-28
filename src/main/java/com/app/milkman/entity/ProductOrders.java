package com.app.milkman.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * $table.getTableComment()
 */
@Data
@Entity
@Table(name = "milkman.product_orders")
public class ProductOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "product_order_id", nullable = false)
    private String productOrderId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderid", referencedColumnName = "orderid")
    private Orders orders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "productid", referencedColumnName = "productid")
    private Products products;

    @Column(name = "productname")
    private String productName;

    @Column(name = "productprice", nullable = false)
    private BigDecimal productPrice;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "createdtime")
    private LocalDateTime createdTime;

    @Column(name = "updatedby")
    private String updatedBy;

    @Column(name = "updatedtime")
    private LocalDateTime updatedTime;

    @Column(name = "status")
    private String status;

}
