import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ForgetPassword extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String uid,dob,qr,psw="";
		
		uid=request.getParameter("email");
		dob=request.getParameter("dob");
		
		psw=uid.substring(0,3)+dob.substring(8,10);
		qr="update user1 set pass='"+psw+"' where id=? and dob=?";
		
		try
		{
			Connection con=null;
			PreparedStatement pst=null;
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlclass","root","root");
			pst=con.prepareStatement(qr);
			pst.setString(1, uid);
			pst.setString(2, dob);
			int n=pst.executeUpdate();
			if(n>0) {
				out.println("Password Updated Successfully..!!");
				out.println("<br><a href='../index.html'>Home</a>");
			}
			else
			{
				out.println("Sorry Try Again..!");
				out.println("<br><a href='../index.html'>Home</a>");
			}
			pst.close();
			con.close();
		}
		catch (Exception e)
		{
			out.println(e);
		}
		out.close();
	}
}
