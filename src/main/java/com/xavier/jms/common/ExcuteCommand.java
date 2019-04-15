package com.xavier.jms.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Excute cmd/doc command.
 *
 * @author NewGr8Player
 */
public class ExcuteCommand {

	/**
	 * Excute cmd/doc
	 *
	 * @param cmd
	 * @return
	 */
	public static List<String> excute(String cmd) {
		Runtime rt = Runtime.getRuntime();
		List<String> lineList = new LinkedList<>();
		try {
			Process process = rt.exec(cmd);
			process.waitFor();
			InputStream stderr = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null) {
				lineList.add(line);
			}
			br.close();
			isr.close();
			stderr.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return lineList;
	}
}
