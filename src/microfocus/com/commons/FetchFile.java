package microfocus.com.commons;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FetchFile {

	public  void fileFetch(String sourceDir, String destDir) {
		JSch jsch = new JSch();
		Session session = null;
		try {
			java.util.Properties config = new java.util.Properties(); 
	    	//config.put("StrictHostKeyChecking", "no");
	    	
	    	/*session=jsch.getSession(user, host, 22);
	    	session.setPassword(password);
	    	session.setConfig(config);
	    	session.connect();*/
	    	System.out.println("Connected");
	    	
			// copy remote log file to localhost.
			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			channelSftp.get(sourceDir, destDir);
			channelSftp.exit();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/*public static void main(String[] args) {
		
		FetchFile obj = new FetchFile();
		obj.fileFetch("164.99.117.110","root", "/var/pattern_installed.txt", "/WebContent/Pattern/","novell");

	}
*/
}
