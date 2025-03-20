package xyz.yousuf.tourie.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "booking")
public class Booking {

    @SequenceGenerator(
            name = "booking_sequence",
            sequenceName = "booking_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "booking_sequence"
    )
    @Id
    private Long BookingId;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;
    private boolean payment;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ColumnDefault("false")
    private boolean isDeleted;


    @ColumnDefault("0")
    private int rating;

    @Override
    public String toString() {
        return "Booking{" +
                "BookingId=" + BookingId +
                ", tour=" + tour +
                ", payment=" + payment +
                ", user=" + user +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
