package com.shu.onlineEducation.utils.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class PythonRunner {
	
	public static String run(String pyPaths, String[] parameters) {
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
		StringBuffer sb = new StringBuffer();
		try {
			if (in != null) {
				while ((line = in.readLine()) != null) {
					sb.append(line).append("\n");
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
		return sb.toString();
	}
	
	@Async
	public void train(String param) {
		PythonRunner.run("D:\\Projects\\Github\\Online-Education-Backend\\src\\main\\resources\\static\\python\\train.py", new String[]{param});
	}
	
	public static void main(String[] args) {
	}
}
