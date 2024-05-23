package viewer;

import controller.RatePointController;
import controller.UserController;
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
    private UserController userController;
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
            if (!ratePointController.hasReview(logIn.getId())) {
                String message = "아직 평론을 작성하지 않으셨습니다 평론을 등록해주세요";
                String review = nextLine(scanner, message);
                RatePointDTO ratePointDTO = ratePointController.selectOneByUserId(logIn.getId(), movieId);
                ratePointDTO.setReview(review);
                ratePointController.update(ratePointDTO);
            }
        } else {
            String message = "평점을 등록해주세요 (1~10)";
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
            System.out.println("등록되었습니다");
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
                if (ratePointDTO.getReview() == null) {
                    System.out.println("평론을 등록하지 않았습니다.");
                } else {
                    System.out.println("나의 평론 : " + ratePointDTO.getReview());
                }
            }
        } else {
            while (true) {
                String message = "1. 평점 수정 2. 평점 삭제 3. 돌아가기";
                int userChoice = nextInt(scanner, message,1,3);
                if (userChoice == 1) {
                    update(movieId);
                    break;
                } else if (userChoice == 2) {
                    delete(movieId);
                    break;
                } else if (userChoice == 3) {
                    System.out.println("돌아갑니다");
                    break;
                }
            }
        }
    }

    private void printList(int movieId) {

        int count = 0;
        int total = 0;
        ArrayList<RatePointDTO> list = ratePointController.selectAllByMovieId(movieId);
        for (RatePointDTO ratePointDTO : list) {
            if (ratePointDTO != null) {
                System.out.println("평점 : " + ratePointDTO.getPoint());
                System.out.println("===========================");
                total = total + ratePointDTO.getPoint();
                count++;
            }
        }
        printAverage(count, total, "평점이 없습니다");
    }

    private void printExpertRateList(int movieId) {
        double total = 0;
        int count = 0;
        ArrayList<UserDTO> list = userController.selectOneGrade2();
        for (UserDTO userDTO : list) {
            RatePointDTO ratePointDTO = ratePointController.selectOneByUserId(userDTO.getId(), movieId);
            if (ratePointDTO != null) {
                System.out.println("평론가 평점 : " + ratePointDTO.getPoint());
                if (ratePointDTO.getReview() == null) {
                    System.out.println("평론 : 평론이 없습니다");
                    System.out.println("==============================");
                } else {
                    System.out.println("평론 : " + ratePointDTO.getReview());
                    System.out.println("==============================");
                }
                total = total + ratePointDTO.getPoint();
                count++;
            }
        }
        printAverage(count, total, "평론가 평점이 없습니다.");
    }

    private void printUserRateList(int movieId) {
        double total = 0;
        int count = 0;
        ArrayList<UserDTO> list = userController.selectOneGrade13();
        for (UserDTO userDTO : list) {
            RatePointDTO ratePointDTO = ratePointController.selectOneByUserId(userDTO.getId(), movieId);
            if (ratePointDTO != null) {
                System.out.println("일반 회원 평점 : " + ratePointDTO.getPoint());
                System.out.println("==============================");
                total = total + ratePointDTO.getPoint();
                count++;
            }
        }
        printAverage(count, total, "일반회원 평점이 없습니다.");
    }

    private static void printAverage(int count, double total, String answer) {
        if (count > 0) {
            double avg = total / count;
            System.out.println("평점 평균 : " + avg);
            System.out.println("평점 개수 : " + count);
        } else {
            System.out.println(answer);
        }
    }

    private void update(int movieId) {
        String message = "평점을 입력해주세요 (1~10)";
        int newPoint = nextInt(scanner, message, 1, 10);
        RatePointDTO ratePointDTO = ratePointController.selectOneByUserId(logIn.getId(), movieId);
        ratePointDTO.setPoint(newPoint);
        if (logIn.getGrade() == 2) {
            message = "평론을 입력해주세요";
            String newReview = nextLine(scanner, message);
            ratePointDTO.setReview(newReview);
        }
        ratePointController.update(ratePointDTO);
        System.out.println("수정이 완료되었습니다");
    }

    private void delete(int movieId) {
        if (ratePointController.selectOneByUserId(logIn.getId(), movieId) == null) {
            System.out.println("평점이 없습니다");
        } else {

            String message = "평점을 삭제하시겠습니까? Y/N";
            String answer = nextLine(scanner, message);
            if (answer.equalsIgnoreCase("Y")) {
                message = "비밀번호를 입력해주세요";
                String password = nextLine(scanner, message);
                if (password.equalsIgnoreCase(logIn.getPassword())) {
                    ratePointController.delete(movieId, logIn.getId());
                    System.out.println("삭제를 성공했습니다");
                } else {
                    System.out.println("비밀번호가 틀렸습니다");
                }
            } else {
                System.out.println("삭제를 취소합니다");
            }
        }
    }
}