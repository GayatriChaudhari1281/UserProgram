import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String uid,ps,nm,dob,qr;
		uid=request.getParameter("email");
		ps=request.getParameter("psw");
		nm=request.getParameter("nm");
		dob=request.getParameter("dob");
		qr="insert into user1 values(?,?,?,?)";
		try
		{
			//JDBC Coding Here
			Connection con=null;
			PreparedStatement pst=null;
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlclass","root","root");
			pst=con.prepareStatement(qr);
			pst.setString(1, uid);
			pst.setString(2, ps);
			pst.setString(3, nm);
			pst.setString(4, dob);
			int n=pst.executeUpdate();
			if(n>0)
			{
				out.println("User Data Added..!!");
				out.println("<br><a href='index.html'>Home</a>");
			}
			else
			{
				out.println("Sorry try Again...!");
				out.println("<br><a href='index.html'>Home</a>");
			}
			pst.close();
			con.close();
		}
		catch (Exception e)
		{
			out.println(e);
		}
		out.close();
	}//dopost()
}//class
