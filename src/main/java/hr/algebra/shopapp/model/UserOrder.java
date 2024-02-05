package hr.algebra.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userOrders")
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String paymentMethod;
    private Double totalPrice;
    private String username;
    private Boolean delivered;
    private LocalDateTime orderDate;
}
