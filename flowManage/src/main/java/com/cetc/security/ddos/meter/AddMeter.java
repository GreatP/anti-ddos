package com.cetc.security.ddos.meter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;


public class AddMeter {
	String HostName;
	String UserName;
	String PassWord;
	
	String ControllerName;
	int FlowId;
	int kbps;
	
	public AddMeter(String ControllerName, int FlowId, int kbps){
		
	}
	
	public AddMeter(String HostName, String UserName,String PassWord,String ControllerName, int FlowId, int kbps){
		this.HostName = HostName;
		this.UserName = UserName;
		this.PassWord = PassWord;
		this.ControllerName = ControllerName;
		this.FlowId = FlowId;
		this.kbps = kbps;
	}
	
	public void SendMeterToSwitch(){
		String Cmd;
		
		try
		{
			/* Create a connection instance */

			Connection conn = new Connection(HostName);

			/* Now connect */

			conn.connect();

			/* Authenticate.
			 * If you get an IOException saying something like
			 * "Authentication method password not supported by the server at this stage."
			 * then please check the FAQ.
			 */

			boolean isAuthenticated = conn.authenticateWithPassword(UserName, PassWord);

			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");
			
			Cmd = "ovs-ofctl add-meter br0 meter=" + FlowId +",kbps,band=type=drop,rate=" + kbps;

			/* Create a session */

			Session sess = conn.openSession();

			sess.execCommand(Cmd);

			System.out.println("Here is some information about the remote host:");

			/* 
			 * This basic example does not handle stderr, which is sometimes dangerous
			 * (please read the FAQ).
			 */

			InputStream stdout = new StreamGobbler(sess.getStdout());

			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

			while (true)
			{
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}

			/* Show exit status, if available (otherwise "null") */

			System.out.println("ExitCode: " + sess.getExitStatus());

			/* Close this session */

			sess.close();

			/* Close the connection */

			conn.close();

		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
			System.exit(2);
		}
		
	}
	
	
	public static void main(String[] args)
	{
		String hostname = "192.168.153.148";
		String username = "root";
		String password = "abc123";

		try
		{
			/* Create a connection instance */

			Connection conn = new Connection(hostname);

			/* Now connect */

			conn.connect();

			/* Authenticate.
			 * If you get an IOException saying something like
			 * "Authentication method password not supported by the server at this stage."
			 * then please check the FAQ.
			 */

			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");

			/* Create a session */

			Session sess = conn.openSession();

			sess.execCommand("uname -a && date && uptime && who");

			System.out.println("Here is some information about the remote host:");

			/* 
			 * This basic example does not handle stderr, which is sometimes dangerous
			 * (please read the FAQ).
			 */

			InputStream stdout = new StreamGobbler(sess.getStdout());

			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

			while (true)
			{
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}

			/* Show exit status, if available (otherwise "null") */

			System.out.println("ExitCode: " + sess.getExitStatus());

			/* Close this session */

			sess.close();

			/* Close the connection */

			conn.close();

		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}

}
