package com.example.demo.controller;

import com.example.demo.service.HackerDetector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.Response;
import java.io.*;
import java.text.ParseException;

@Controller
public class HotelBedsTestController {

    private HackerDetector hackerDetector;

    public HotelBedsTestController(HackerDetector hackerDetector) {
        this.hackerDetector = hackerDetector;
    }

    @RequestMapping(path = "/logFile")
    public ResponseEntity<String> logFile() throws IOException, ParseException {
        String result;
        result = hackerDetector.checkFile();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "/timeCalculation")
    public ResponseEntity<Integer> timeCalculation() throws ParseException {
        return new ResponseEntity<>(hackerDetector.timeCalculator(), HttpStatus.OK);
    }
}
