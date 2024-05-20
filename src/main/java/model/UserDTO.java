package model;

import lombok.Data;

@Data
public class UserDTO {

    private int id;
    private String username;
    private String password;
    private String nickname;
    private int grade; // 1 일반 2 전문 3 관리

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof UserDTO) {
            UserDTO u = (UserDTO) o;
            return id == u.id;
        }
        return false;
    }
}
