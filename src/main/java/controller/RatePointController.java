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

    public ArrayList<RatePointDTO> selectAllWithReview(int movieId) {
        ArrayList<RatePointDTO> result = new ArrayList<>();
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO.getMovieId() == movieId &&
                    ratePointDTO.getReview() != null &&
                    !ratePointDTO.getReview().isEmpty()) {
                result.add(ratePointDTO);
            }
        }
        return result;
    }

    public ArrayList<RatePointDTO> selectAllWithoutReview(int movieId) {
        ArrayList<RatePointDTO> result = new ArrayList<>();
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO.getMovieId() == movieId &&
                    (ratePointDTO.getReview() == null ||
                            ratePointDTO.getReview().isEmpty())) {
                result.add(ratePointDTO);
            }
        }
        return result;
    }

    public boolean hasAlreadyRate(int userId, int movieId) {
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO.getUserId() == userId && ratePointDTO.getMovieId() == movieId) {
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

    public double pointExpertAverage(int movieId) {
        int sum = 0;
        int count = 0; // 특정 영화에 대한 평점 개수를 세는 변수 추가
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO.getMovieId() == movieId &&
                    ratePointDTO.getReview() != null &&
                    !ratePointDTO.getReview().isEmpty()) {
                sum += ratePointDTO.getPoint();
                count++;
            }
        }
        return count == 0 ? 0 : (double) sum / count;

    }

    public double pointUserAverage(int movieId) {
        int sum = 0;
        int count = 0;
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO.getMovieId() == movieId &&
                    (ratePointDTO.getReview() == null ||
                            ratePointDTO.getReview().isEmpty())) {
                sum += ratePointDTO.getPoint();
                count++;
            }
        }
        return count == 0 ? 0 : (double) sum / count;
    }
}
