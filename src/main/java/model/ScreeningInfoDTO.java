package model;

import lombok.Data;

@Data
public class ScreeningInfoDTO {
    private int id;
    private int movieId;
    private int theaterId;
    private String screenTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ScreeningInfoDTO) {
            ScreeningInfoDTO s = (ScreeningInfoDTO) o;
            return id == s.id;
        }
        return false;
    }
}
