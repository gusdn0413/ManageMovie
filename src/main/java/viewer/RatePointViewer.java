package viewer;

import controller.RatePointController;
import lombok.Setter;
import model.RatePointDTO;
import model.UserDTO;

import java.util.ArrayList;
import java.util.Scanner;

import static util.ScannerUtil.*;

public class RatePointViewer {

    @Setter
    private Scanner scanner;
    @Setter
    private RatePointController ratePointController;
    @Setter
    private UserDTO logIn;

    public void showIndex(int movieId) {
        String message = "1. 평점 등록 2. 평점 조회 3. 돌아가기";
        while (true) {
            int userChoice = nextInt(scanner, message, 1, 3);
            if (userChoice == 1) {
                register(movieId);
            } else if (userChoice == 2) {
                showRate(movieId);
            } else if (userChoice == 3) {
                System.out.println("돌아갑니다");
                break;
            }
        }
    }

    private void register(int movieId) {
        if (ratePointController.hasAlreadyRate(logIn.getId(), movieId)) {
            System.out.println("이미 평점을 등록하셨습니다");
        } else {
            String message = "평점을 등록해주세요";
            int ratePoint = nextInt(scanner, message, 1, 10);
            RatePointDTO ratePointDTO = new RatePointDTO();
            ratePointDTO.setPoint(ratePoint);
            ratePointDTO.setMovieId(movieId);
            ratePointDTO.setUserId(logIn.getId());
            if (logIn.getGrade() == 2) {
                message = "평론을 등록해주세요";
                String review = nextLine(scanner, message);
                ratePointDTO.setReview(review);
            }
            ratePointController.insert(ratePointDTO);
        }
    }

    private void showRate(int movieId) {
        String message = "1. 내 평점 보기 2. 전체 평점 보기 3. 평론가 평점 보기 4. 일반 회원 평점 보기 5. 돌아가기";
        while (true) {
            int userChoice = nextInt(scanner, message, 1, 5);
            if (userChoice == 1) {
                printOne(movieId);
            } else if (userChoice == 2) {
                printList(movieId);
            } else if (userChoice == 3) {
                printExpertRateList(movieId);
            } else if (userChoice == 4) {
                printUserRateList(movieId);
            } else if (userChoice == 5) {
                System.out.println("돌아갑니다");
                break;
            }
        }
    }

    private void printOne(int movieId) {
        RatePointDTO ratePointDTO = ratePointController.selectOneByMovieId(movieId, logIn.getId());
        if (ratePointDTO == null) {
            System.out.println("등록한 평점이 없습니다");
        } else if (ratePointDTO.getUserId() == logIn.getId()) {
            System.out.println("나의 평점 : " + ratePointDTO.getPoint());
            if (logIn.getGrade() == 2) {
                System.out.println("나의 평론 : " + ratePointDTO.getReview());
            }
        }
    }

    private void printList(int movieId) {

        ArrayList<RatePointDTO> list = ratePointController.selectAllByMovieId(movieId);
        for (RatePointDTO ratePointDTO : list) {

            System.out.println("평점 : " + ratePointDTO.getPoint());
            System.out.println("===========================");
        }
        System.out.println("평점 평균 : " + ratePointController.pointAverage(movieId));
        if (list.isEmpty()) {
            System.out.println("등록된 평점이 없습니다");
        }
    }

    private void printExpertRateList(int movieId) {
        ArrayList<RatePointDTO> list = ratePointController.selectAllWithReview(movieId);
        for (RatePointDTO ratePointDTO : list) {
            System.out.println("평론가 평점 : " + ratePointDTO.getPoint());
            System.out.println("평론 : " + ratePointDTO.getReview());
            System.out.println("===========================");
        }
        System.out.println("평점 평균 : " + ratePointController.pointExpertAverage(movieId));
        if (list.isEmpty()) {
            System.out.println("평론가 평점이 없습니다");
        }
    }

    private void printUserRateList(int movieId) {
        ArrayList<RatePointDTO> list = ratePointController.selectAllWithoutReview(movieId);
        for (RatePointDTO ratePointDTO : list) {
            System.out.println("일반 회원 평점 : " + ratePointDTO.getPoint());
            System.out.println("===========================");
        }
        System.out.println("평점 평균 : " + ratePointController.pointUserAverage(movieId));
        if (list.isEmpty()) {
            System.out.println("일반 회원 평점이 없습니다");
        }
    }
}
