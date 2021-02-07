package com.shu.onlineEducation.utils.runner;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

@Slf4j
@Data
public class PythonRunner {
	
	public static void run(String pyPaths, String[] parameters) {
		String[] runTimeArgs = new String[parameters.length + 2];
		runTimeArgs[0] = "python";
		runTimeArgs[1] = pyPaths;
		System.arraycopy(parameters, 0, runTimeArgs, 2, parameters.length);
		Process pr = null;
		try {
			pr = Runtime.getRuntime().exec(runTimeArgs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader in = null;
		if (pr != null) {
			try {
				in = new BufferedReader(new InputStreamReader(pr.getInputStream(), "GBK"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String line;
		try {
			if (in != null) {
				while ((line = in.readLine()) != null) {
					log.info(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			if (in != null) {
				in.close();
			}
			if (pr != null) {
				pr.waitFor();
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PythonRunner.run("src/main/resources/static/python/py2.py",new String[]{String.valueOf(1), String.valueOf(2)});
	}
}
