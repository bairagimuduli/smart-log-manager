package microfocus.com.commons;

//import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class JSCHConnection {

	JSch jsch = new JSch();
	Session session = null;
	java.util.Properties config = new java.util.Properties();

	public int SSH_Connection(String host, String user, String password, String command1) {

		int exitValue = 1;
		try {
			config.put("StrictHostKeyChecking", "no");
			session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.setConfig(config);
			session.connect();
			System.out.println("Connected");

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command1);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			// InputStream in = channel.getInputStream();
			channel.connect();
			// byte[] tmp = new byte[1024];
			while (true) {
				/*
				 * while (in.available() > 0) { int i = in.read(tmp, 0, 1024); if (i < 0) break;
				 * System.out.print(new String(tmp, 0, i)); }
				 */
				if (channel.isClosed()) {
					exitValue = channel.getExitStatus();
					System.out.println("exit-status: " + exitValue);
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();

			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exitValue;

	}

	public void fileFetch(String sourceDir, String destDir) {
		
		System.out.println(sourceDir+"                "+destDir);
		System.out.println("in file fetch");
		try {
		
	    	System.out.println("Connected");
	    	
			// copy remote log file to localhost.
			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			System.out.println("Connected1111111");
			channelSftp.connect();
			System.out.println("Connected22222222");
			System.out.println("hi0");
			try {
			channelSftp.get(sourceDir, destDir);
			}
			catch(SftpException e)
			{
				e.printStackTrace();
				System.out.println(e);
			}
			System.out.println("hi1");
			System.out.println("hi2");
			System.out.println("Connected3333333");
			channelSftp.exit();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/*public static void main(String[] args) {

		JSCHConnection obj = new JSCHConnection();
		
		int exitval= obj.SSH_Connection("164.99.117.110", "root", "novell", "zypper pt | grep novell | grep i+ | awk '{print $3}' > /var/pattern_installed.txt");
		System.out.println(exitval+"--------------");
		obj.fileFetch("/var/pattern_installed.txt","/WebContent/Pattern/");

	}*/

}
