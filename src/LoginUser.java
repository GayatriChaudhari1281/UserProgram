import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginUser extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String uid,ps,qr;//nm,email,add,gen;
		uid=request.getParameter("email");
		ps=request.getParameter("pass");
		
		qr="select id from user1 where id=? and pass=?";
		try
		{
			HttpSession session=request.getSession();
			Connection con=null;
			PreparedStatement pst=null;
			ResultSet rs=null;
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlclass","root","root");
			pst=con.prepareStatement(qr);
			pst.setString(1, uid);
			pst.setString(2, ps);
			rs=pst.executeQuery();
			if(rs.next())
			{
				session.setAttribute("userid", uid);
				RequestDispatcher dis=getServletContext().getRequestDispatcher("/JSP/UserHome.jsp");
				dis.forward(request, response);
			}
			else
			{
				RequestDispatcher dis =getServletContext().getRequestDispatcher("/index.html");
				dis.include(request, response);
				out.println("<font color='red'>Password Failed..!</font>");
			}
			rs.close();
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
