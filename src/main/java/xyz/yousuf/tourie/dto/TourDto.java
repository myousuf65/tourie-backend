package xyz.yousuf.tourie.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestController;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TourDto {

    private String name;

    private String description;

    private float price;

    private String photoUrl;

    private String username;

    @Override
    public String toString() {
        return "TourDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", photoUrl='" + photoUrl + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
