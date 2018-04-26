# Java-Parallel-Util
Easy way to run Threads attaching a callback

## Usage examples

Run a Parallel thread with a callable returning a **Double** 

```java
Parallel.run(() -> {
	Thread.sleep(1000);
	return Math.random();
}, (Double result) -> {
	Utils.log("Value: " + result);
});
```

Example of an HTTP request, the return is a **String**

```java
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
```

Stop a Parallel thread

```java
Parallel threadToCancel = new Parallel(() -> {
...
threadToCancel.cancel();
```
