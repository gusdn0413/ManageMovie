package model;

import lombok.Data;

@Data
public class MovieDTO {

    private int id;
    private String title;
    private String content;
    private String grade; // 1 일반 2 평론가 3 관리자

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof MovieDTO) {
            MovieDTO m = (MovieDTO) o;
            return id == m.id;
        }
        return false;
    }
}
