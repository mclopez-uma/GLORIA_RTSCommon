package eu.gloria.rt.tools.runtime;

import java.io.InputStream;
import java.io.OutputStream;

public class Executor {

	public ExecResult execute(String script, String[] params) throws Exception {

		return execute(script, params, null);

	}

	public ExecResult execute(String script, String[] params, OutputStream out)
			throws Exception {

		StringBuilder sb = new StringBuilder();
		sb.append(script);
		sb.append(" ");

		if (params != null) {
			for (int x = 0; x < params.length; x++) {
				sb.append(params[x]);
				sb.append(" ");
			}
		}

		String cmd = sb.toString();

		System.out.println("Executor. Executing:" + cmd);

		Process proc;
		int exCode = 0;
		StringBuffer ret = new StringBuffer();
		StringBuffer err = new StringBuffer();

		InputStream is = null;
		InputStream ies = null;
		try {

			proc = Runtime.getRuntime().exec(cmd);
			is = proc.getInputStream();
			ies = proc.getErrorStream();

			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];

			// Read the standard output
			System.out.println("Executor. output......");
			while (true) {
				int read = is.read(buffer, 0, bufferSize);
				if (read == -1) {
					break;
				}

				if (out != null) {
					out.write(buffer, 0, read);
				} else {
					ret.append(new String(buffer, 0, read));
				}
			}

			// Read the error output
			System.out.println("Executor. err....");
			while (true) {
				int read = ies.read(buffer, 0, bufferSize);
				if (read == -1) {
					break;
				}
				err.append(new String(buffer, 0, read));
			}

			System.out.println("Executor. waiting....");
			exCode = proc.waitFor();
			System.out.println("Executor. code=" + exCode);

		} catch (Exception ex) {
			String[] names2 = { "cmd", "exception" };
			String[] values2 = { cmd, ex.getMessage() };
			throw new Exception("Error executing CMD. " + getLog(names2, values2));
		} finally {
			if (is != null)
				try{
					is.close();
				}catch(Exception e){					
				};
			if (ies != null)
				try{
					ies.close();
				}catch(Exception e){					
				};
		}

		ExecResult result = new ExecResult();
		result.setCode(exCode);
		result.setOutput(ret.toString());
		result.setErr(err.toString());

		return result;

	}

	/**
	 * Returns a String like this: [name1=value1, name2=value2....]
	 * 
	 * @param names
	 *            List of names.
	 * @param values
	 *            List of values
	 * @return String.
	 */
	private static String getLog(String[] names, String[] values) {
		if (names != null && values != null && names.length == values.length) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (int x = 0; x < values.length; x++) {
				if (x > 0) {
					sb.append(", ");
				}
				sb.append(names[x]).append("=").append(values[x]);
			}
			sb.append("] ");
			return sb.toString();

		} else {

			return "";
		}

	}

}
