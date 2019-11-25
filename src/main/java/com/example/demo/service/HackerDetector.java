package com.example.demo.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;

public interface HackerDetector {

	String checkFile() throws IOException, ParseException;

	String parseLine(String line);

	Integer timeCalculator() throws ParseException;
}