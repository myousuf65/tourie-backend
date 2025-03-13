package xyz.yousuf.tourie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeleteTourDto {
    private Long tour;
    private String username;

    @Override
    public String toString() {
        return "DeleteTourDto{" +
                "tour='" + tour + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
