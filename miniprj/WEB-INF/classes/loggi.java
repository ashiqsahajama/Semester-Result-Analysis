import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
import java.sql.*;

public class loggi extends HttpServlet
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DB_URL="jdbc:mysql://localhost/miniproj?autoReconnect=true&useSSL=false";
	static final String USER = "root";
	static final String PASS = "ssnce";
	public void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException
	{
		String uname = rq.getParameter("name");
		String password = rq.getParameter("pass");

		PrintWriter pw = rp.getWriter();

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String sql;
			sql="SELECT user,pass FROM login";
			ResultSet rs = stmt.executeQuery(sql);
			int flag_uname = 0,flag_pass = 0;
			while(rs.next())
			{
				String first = rs.getString(1);
				String last = rs.getString(2);
				if (uname.equals(first))
				{
					flag_uname = 1;
					if(password.equals(last))
						flag_pass = 1;
				}
			}
			if(flag_pass==1 && flag_uname==1)
				//path of the registration from
				rp.sendRedirect("catsem.html");
			/*{
				ServletContext sc = getServletContext();
				sc.getRequestDispatcher("event_reg.html").forward(rq, rp);
			}*/
			else
				pw.println("<html><body><b>Invalid credentials</b></body></html>");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}