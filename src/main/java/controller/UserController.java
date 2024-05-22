package controller;

import model.UserDTO;

import java.util.ArrayList;

public class UserController {

    private ArrayList<UserDTO> list;
    private int nextId;

    public UserController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public void insert(UserDTO userDTO) {
        userDTO.setId(nextId++);
        list.add(userDTO);
    }

    public UserDTO auth(String username, String password) {
        for (UserDTO u : list) {
            if (username.equalsIgnoreCase(u.getUsername())) {
                if (password.equals(u.getPassword())) {
                    return u;
                }
            }
        }
        return null;
    }

    public boolean validateUsername(String username) {
        for (UserDTO u : list) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }

    public void update(UserDTO userDTO) {
        list.set(list.indexOf(userDTO), userDTO);
    }

    public void delete(int id) {
        UserDTO temp = new UserDTO();
        temp.setId(id);

        list.remove(temp);
    }

    public ArrayList<UserDTO> selectAll() {
        return list;
    }

    public UserDTO selectOne(int id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        return list.get(list.indexOf(userDTO));
    }

    public boolean validateInput(int input) {
        if (input == 0) {
            return true;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(input);
        return list.contains(userDTO);
    }

    public ArrayList<UserDTO> selectOneGrade2() {
        ArrayList<UserDTO> result = new ArrayList<>();
        for (UserDTO userDTO : list) {
            if (userDTO.getGrade() == 2) {
                result.add(userDTO);
            }
        }
        return result;
    }

    public ArrayList<UserDTO> selectOneGrade13() {
        ArrayList<UserDTO> result = new ArrayList<>();
        for (UserDTO userDTO : list) {
            if (userDTO.getGrade() != 2) {
                result.add(userDTO);
            }
        }
        return result;
    }
}
