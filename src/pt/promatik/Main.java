package pt.promatik;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import pt.promatik.utils.Parallel;
import pt.promatik.utils.Utils;

public class Main {

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		Utils.log("Utils test");

		// ----------
		// Example of a function returning a String
		Parallel.run(() -> {
			Thread.sleep(1000);
			return Math.random();
		}, (Double result) -> {
			Utils.log("Value: " + result);
		});

		// ----------
		// Example of a function returning a Boolean
		Parallel.run(() -> {
			Thread.sleep(2000);
			return true;
		}, (Boolean result) -> {
			Utils.log("Result as a Boolean: " + result.toString());
		});

		// ----------
		// HTTP Request example
		Parallel.run(() -> {
			try {
				URL url = new URL("http://google.pt/");
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");

				con.getResponseCode();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String input;
				StringBuffer content = new StringBuffer();

				while ((input = in.readLine()) != null)
					content.append(input);
				in.close();

				return content.toString();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}, (String result) -> {
			if (result != null)
				Utils.log("HTTP Request result: " + result.toString().substring(0, 32) + "...");
		});

		// ----------
		// Cancel a call
		Parallel threadToCancel = new Parallel(() -> {
			return "This will never run!";
		}, (result) -> {
			Utils.log(result);
		});

		threadToCancel.cancel();

		// ----------
		Utils.log("Main thread is running asynchronously ...");
	}
}
