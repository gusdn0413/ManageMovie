package controller;

import model.TheaterDTO;

import java.util.ArrayList;

public class TheaterController {

    private ArrayList<TheaterDTO> list;
    private int nextId;

    public TheaterController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public void insert(TheaterDTO theaterDTO) {
        theaterDTO.setId(nextId++);
        list.add(theaterDTO);
    }

    public ArrayList<TheaterDTO> selectAll() {
        return list;
    }

    public TheaterDTO selectOne(int id) {
        for (TheaterDTO theaterDTO : list) {
            if (theaterDTO.getId() == id) {
                return theaterDTO;
            }
        }
        return null;
    }

    public boolean validateInput(int input) {
        if (input == 0) {
            return true;
        }

        TheaterDTO theaterDTO = new TheaterDTO();
        theaterDTO.setId(input);
        return list.contains(theaterDTO);

    }

    public void update(TheaterDTO theaterDTO) {
        list.set(list.indexOf(theaterDTO), theaterDTO);
    }

    public void delete(int id) {
        TheaterDTO theaterDTO = new TheaterDTO();
        theaterDTO.setId(id);

        list.remove(theaterDTO);
    }
}
