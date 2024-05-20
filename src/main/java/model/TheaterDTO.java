package model;

import lombok.Data;

@Data
public class TheaterDTO {
    private int id;
    private String name;
    private String location;
    private String telephoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof TheaterDTO) {
            TheaterDTO t = (TheaterDTO) o;
            return id == t.id;
        }
        return false;
    }
}
