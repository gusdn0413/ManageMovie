package controller;

import model.RatePointDTO;

import java.util.ArrayList;

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

    public double pointAverage(int movieId) {
        int sum = 0;
        int count = 0;
        for (RatePointDTO r : list) {
            if (r.getMovieId() == movieId) {
                sum += r.getPoint();
                count++;
            }
        }
        return count == 0 ? 0 : (double) sum / count;
    }

    public RatePointDTO selectOneByUserId(int userId, int movieId) {
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO.getUserId() == userId && ratePointDTO.getMovieId() == movieId) {
                return ratePointDTO;
            }
        }
        return null;
    }
}
