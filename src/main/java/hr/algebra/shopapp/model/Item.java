package hr.algebra.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private Double price;
    private String description;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private ItemType category;
    private String image;
}
