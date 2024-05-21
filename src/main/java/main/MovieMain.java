package main;

import controller.*;
import model.RatePointDTO;
import model.UserDTO;
import viewer.*;

import java.util.Scanner;

public class MovieMain {
    public static void main(String[] args) {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("manager");
        userDTO.setPassword("123");
        userDTO.setNickname("관리자");
        userDTO.setGrade(3);

        Scanner scanner = new Scanner(System.in);

        UserController userController = new UserController();
        MovieController movieController = new MovieController();
        RatePointController ratePointController = new RatePointController();
        TheaterController theaterController = new TheaterController();
        ScreeningInfoController screeningInfoController = new ScreeningInfoController();

        UserViewer userViewer = new UserViewer();
        MovieViewer movieViewer = new MovieViewer();
        RatePointViewer ratePointViewer = new RatePointViewer();
        TheaterViewer theaterViewer = new TheaterViewer();
        ScreeningInfoViewer screeningInfoViewer = new ScreeningInfoViewer();

        userViewer.setScanner(scanner);
        userViewer.setUserController(userController);
        userViewer.setMovieViewer(movieViewer);

        movieViewer.setScanner(scanner);
        movieViewer.setMovieController(movieController);
        movieViewer.setRatePointViewer(ratePointViewer);
        movieViewer.setTheaterViewer(theaterViewer);

        ratePointViewer.setScanner(scanner);
        ratePointViewer.setRatePointController(ratePointController);
        ratePointViewer.setUserController(userController);

        theaterViewer.setScanner(scanner);
        theaterViewer.setTheaterController(theaterController);
        theaterViewer.setScreeningInfoViewer(screeningInfoViewer);

        screeningInfoViewer.setScanner(scanner);
        screeningInfoViewer.setScreeningInfoController(screeningInfoController);
        screeningInfoViewer.setMovieController(movieController);

        userController.insert(userDTO);

        userViewer.showIndex();
    }

}
