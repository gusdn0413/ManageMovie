package model;

import lombok.Data;

@Data
public class RatePointDTO {

    private int id;
    private int movieId;
    private int userId;
    private int point;
    private String review;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof RatePointDTO) {
            RatePointDTO g = (RatePointDTO) o;
            return id == g.id;
        }
        return false;
    }
}
