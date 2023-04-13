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

public class ChangePassword extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String ps,cps,qr,id,psw="";
		
		ps=request.getParameter("cpsw");
		cps=request.getParameter("psw");
		
		HttpSession session=request.getSession();
		id=(String) session.getAttribute("userid");
		qr="select * from user1 where id=? and pass=?";
		
		try
		{
			Connection con=null;
			PreparedStatement pst=null;
			ResultSet rs=null;
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sqlclass","root","root");
			pst=con.prepareStatement(qr);
			pst.setString(1, id);
			pst.setString(2, ps);
			
			rs=pst.executeQuery();
			if(rs.next())
			{
				qr="update user1 set pass=? where id=?";
				pst=con.prepareStatement(qr);
				pst.setString(1, cps);
				pst.setString(2, id);
				int n=pst.executeUpdate();
				if(n>0)
				{
					out.println("Password Updated....!");
					out.println("<input type='button' value='Back' onclick='history.back()'>");
				}
				else
				{
					out.println("Password Mismatched");
					out.println("<input type='button' value='Back' onclick='history.back()'>");
				}
			}
			else
			{
				out.println("Password Mismatched");
				out.println("<input type='button' value='Back' onclick='history.back()'>");
			}
		}
		catch (Exception e)
		{
			out.println(e);
		}
		out.close();
	}

}
