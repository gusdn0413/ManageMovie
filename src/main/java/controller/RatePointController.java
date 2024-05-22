package controller;

import model.RatePointDTO;

import java.util.ArrayList;
import java.util.Iterator;

public class RatePointController {

    private ArrayList<RatePointDTO> list;
    private int nextId;

    public RatePointController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public void insert(RatePointDTO ratePointDTO) {
        ratePointDTO.setId(nextId++);
        list.add(ratePointDTO);
    }

    public void update(RatePointDTO ratePointDTO) {
        list.set(list.indexOf(ratePointDTO), ratePointDTO);
    }

    public ArrayList<RatePointDTO> selectAllByMovieId(int movieId) {
        ArrayList<RatePointDTO> result = new ArrayList<>();
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO.getMovieId() == movieId) {
                result.add(ratePointDTO);
            }
        }
        return result;
    }

    public RatePointDTO selectOneByMovieId(int movieId, int userId) {
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO.getMovieId() == movieId && ratePointDTO.getUserId() == userId) {
                return ratePointDTO;
            }
        }
        return null;
    }

    public boolean hasAlreadyRate(int userId, int movieId) {
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO.getUserId() == userId && ratePointDTO.getMovieId() == movieId) {
                return true;
            }
        }
        return false;
    }

    public boolean hasReview(int userId) {
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO.getUserId() == userId && ratePointDTO.getReview() != null) {
                return true;
            }
        }
        return false;
    }

    public RatePointDTO selectOneByUserId(int userId, int movieId) {
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO.getUserId() == userId && ratePointDTO.getMovieId() == movieId) {
                return ratePointDTO;
            }
        }
        return null;
    }

    public void delete(int movieId,int userId) {
        Iterator<RatePointDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            RatePointDTO ratePointDTO = iterator.next();
            if (ratePointDTO.getMovieId() == movieId && ratePointDTO.getUserId() == userId) {
                iterator.remove();
            }
        }
    }
}
