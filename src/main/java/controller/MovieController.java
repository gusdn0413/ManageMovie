package controller;

import model.MovieDTO;


import java.util.ArrayList;

public class MovieController {

    private ArrayList<MovieDTO> list;
    private int nextId;

    public MovieController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public void insert(MovieDTO movieDTO) {
        movieDTO.setId(nextId++);
        list.add(movieDTO);
    }

    public void update(MovieDTO movieDTO) {
        list.set(list.indexOf(movieDTO), movieDTO);
    }

    public void delete(int id) {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(id);

        list.remove(movieDTO);
    }

    public ArrayList<MovieDTO> selectAll() {
        return list;
    }

    public MovieDTO selectOne(int id) {
        for (MovieDTO movieDTO : list) {
            if (movieDTO.getId() == id) {
                return movieDTO;
            }
        }
        return null;
    }

    public boolean validateInput(int input) {
        if (input == 0) {
            return true;
        }

        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(input);
        return list.contains(movieDTO);
    }

    public boolean validateTitle(String title) {
        for (MovieDTO m : list) {
            if (m.getTitle().equalsIgnoreCase(title)) {
                return false;
            }
        }
        return true;
    }
}
