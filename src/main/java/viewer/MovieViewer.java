package viewer;

import controller.MovieController;
import lombok.Setter;
import model.MovieDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

import static util.ScannerUtil.*;

public class MovieViewer {

    @Setter
    private Scanner scanner;
    @Setter
    private MovieController movieController;
    @Setter
    private UserDTO logIn;
    @Setter
    private RatePointViewer ratePointViewer;
    @Setter
    private TheaterViewer theaterViewer;


    public void showIndex() {
        ratePointViewer.setLogIn(logIn);
        theaterViewer.setLogIn(logIn);
        while (true) {
            if (logIn.getGrade() == 3) {
                String message = "1. 영화 등록 2. 영화 목록 보기 3. 극장 4. 메인 화면";
                int userChoice = nextInt(scanner, message, 1, 5);
                if (userChoice == 1) {
                    register();
                } else if (userChoice == 2) {
                    printList();
                } else if (userChoice == 3) {
                    theaterViewer.showIndex();
                } else if (userChoice == 4) {
                    System.out.println("메인화면으로 돌아갑니다.");
                    break;
                }
            } else {
                String message = "1. 영화 목록 보기 2. 극장 3. 메인 화면";
                int userChoice = nextInt(scanner, message, 1, 4);
                if (userChoice == 1) {
                    printList();
                } else if (userChoice == 2) {
                    theaterViewer.showIndex();
                } else if (userChoice == 3) {
                    System.out.println("메인화면으로 돌아갑니다.");
                    break;
                }
            }
        }
    }

    private void register() {
        String message = "영화 제목을 입력해주세요";
        String title = nextLine(scanner, message);
        if (movieController.validateTitle(title)) {
            message = "영화 내용을 입력해주세요";
            String content = nextLine(scanner, message);

            message = "영화 등급을 입력해주세요";
            String grade = nextLine(scanner, message);

            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setTitle(title);
            movieDTO.setContent(content);
            movieDTO.setGrade(grade);

            movieController.insert(movieDTO);
        }
    }

    private void printList() {
        ArrayList<MovieDTO> list = movieController.selectAll();
        for (MovieDTO movieDTO : list) {
            System.out.println("=================================");
            System.out.println("영화 id : " + movieDTO.getId());
            System.out.println("영화 제목 : " + movieDTO.getTitle());
            System.out.println("영화 등급 : " + movieDTO.getGrade());
            System.out.println("=================================");
        }
        if (list.isEmpty()) {
            System.out.println("등록된 영화가 없습니다.");
        } else {
            String message = "상세보기할 영화의 번호나 뒤로가시려면 0을 눌러주세요";
            int userChoice = nextInt(scanner, message);
            while (!movieController.validateInput(userChoice)) {
                System.out.println("잘못 입력하셨습니다.");
                userChoice = nextInt(scanner, message);
            }
            if (userChoice != 0) {
                printOne(userChoice);
            }
        }
    }

    private void printOne(int movieId) {
        MovieDTO movieDTO = movieController.selectOne(movieId);
        System.out.println("=============================");
        System.out.println("영화 id : " + movieDTO.getId());
        System.out.println("영화 제목 : " + movieDTO.getTitle());
        System.out.println("영화 내용 : " + movieDTO.getContent());
        System.out.println("영화 등급 : " + movieDTO.getGrade()); // 나중 수정

        if (logIn.getGrade() == 3) {
            String message = "1. 영화 수정 2. 영화 삭제 3. 평점 4. 돌아가기";
            int userChoice = nextInt(scanner, message, 1, 4);
            if (userChoice == 1) {
                update(movieId);
            } else if (userChoice == 2) {
                delete(movieId);
            } else if (userChoice == 3) {
                // 평점
                ratePointViewer.showIndex(movieDTO.getId());
            } else if (userChoice == 4) {
                System.out.println("돌아갑니다");
            }
        } else {
            String message = "평점을 보시겠습니까? Y/N";
            String answer = nextLine(scanner, message);
            if (answer.equalsIgnoreCase("Y")) {
                //평점
                ratePointViewer.showIndex(movieDTO.getId());
            } else {
                System.out.println("돌아갑니다.");
            }
        }

    }

    private void update(int movieId) {
        String message = "비밀번호를 입력해주세요";
        String password = nextLine(scanner, message);
        if (password.equals(logIn.getPassword())) {
            message = "새로운 제목을 등록해주세요";
            String newTitle = nextLine(scanner, message);
            message = "새로운 내용을 입력해주세요";
            String newContent = nextLine(scanner, message);
            message = "새로운 등급을 입력해주세요";
            String newGrade = nextLine(scanner, message);

            MovieDTO movieDTO = movieController.selectOne(movieId);
            movieDTO.setTitle(newTitle);
            movieDTO.setContent(newContent);
            movieDTO.setGrade(newGrade);
            movieController.update(movieDTO);
        } else {
            System.out.println("비밀번호가 틀렸습니다");
        }
    }

    private void delete(int movieId) {
        String message = "비밀번호를 입력해주세요";
        String password = nextLine(scanner, message);
        if (password.equals(logIn.getPassword())) {
            message = "정말 삭제하시겠습니까? Y/N";
            String answer = nextLine(scanner, message);
            if (answer.equalsIgnoreCase("Y")) {
                movieController.delete(movieId);
                System.out.println("삭제되었습니다.");
            } else {
                System.out.println("삭제를 취소합니다");
            }
        } else {
            System.out.println("비밀번호가 틀렸습니다");

        }
    }
}