package xyz.yousuf.tourie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import xyz.yousuf.tourie.dto.TourDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name="tour")
public class Tour {


    @SequenceGenerator(
            name = "tour_sequence",
            sequenceName = "tour_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tour_sequence"
    )

    @Id
    private Long TourId;

    private String name;

    private String description;

    private float price;

    private String photoUrl;

    private boolean isDeleted;


    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel user;

}
