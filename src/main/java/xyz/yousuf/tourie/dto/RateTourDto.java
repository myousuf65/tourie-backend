package xyz.yousuf.tourie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RateTourDto {
    private String bookingId;
    private int rating;
    private String username;
}
