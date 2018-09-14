package microfocus.com.controller;

import microfocus.com.commons.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogInController")
public class LogInController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// content type
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		String ip = null;
		String user = null;
		String password = null;
		String command = "zypper pt | grep novell | grep i+ | awk '{print $3}' > /var/pattern_installed.txt";
		int connectionCheck = 1;

		ip = request.getParameter("ip");
		user = request.getParameter("username");
		password = request.getParameter("pwd");

		HttpSession session = request.getSession();
		session.setAttribute("ip", ip);
		session.setAttribute("username", user);
		session.setAttribute("password", password);

		try {
			JSCHConnection obj = new JSCHConnection();
			
			connectionCheck = obj.SSH_Connection(ip, user, password, command);
			
			obj.fileFetch("/var/pattern_installed.txt","/WebContent/Pattern/");
		
			if(connectionCheck==0) {
				out.println("output    "+connectionCheck);
				RequestDispatcher reqDispatch1 = request.getRequestDispatcher("home.jsp");
				reqDispatch1.forward(request, response);
			}else {
				RequestDispatcher reqDispatch2 = request.getRequestDispatcher("index.jsp");
				reqDispatch2.forward(request, response);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
