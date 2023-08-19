import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
import java.sql.*;

public class searchstuct extends HttpServlet
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DB_URL="jdbc:mysql://localhost/miniproj?autoReconnect=true&useSSL=false";
	static final String USER = "root";
	static final String PASS = "ssnce";
	public void doPost(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException
	{
        String nam = rq.getParameter("nam");
        String roll = rq.getParameter("roll");
		String bno = rq.getParameter("bno");
		String dep = rq.getParameter("dep");
        String sec = rq.getParameter("sec");
        String sem = rq.getParameter("sem");

		PrintWriter pw = rp.getWriter();

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			String sql = "select c.s_id, name, roll_no, c.cat_num, sub_1, sub_2, sub_3, sub_4, sub_5, sub_6 from cat as c join semm as s join batchh as b where b.id = s.id and s.s_id = c.s_id and s.sem ='"+ sem +"' and b.batch = '"+ bno +"' and b.dept = '"+ dep +"' and b.sec = '"+ sec +"' and c.roll_no = '"+ roll + "';";
            String sql1 = "select * from sub where s_id = ANY(" + "select se.s_id from semester as se join semm as s join batchh as b where b.id = s.id and s.s_id = se.s_id and s.sem = '" + sem + "' and batch = '" + bno +"' and dept = '" + dep + "' and sec = '"+ sec + "');";

            //System.out.println(sql);
            //System.out.println(sql1);

            
            ResultSet rs = stmt.executeQuery(sql1);

            String style = "<style>" +
                        "body"+"{"+
   "background: #136a8a;"+
"}"+
"table"+"{"+
"height:50%;"+
 "color: white;"+
 "text-align: center;" + 
 "background: #136a8a;"+
  "background: linear-gradient(to right, #267871, #136a8a);"+
  "margin: auto;"+
  "box-shadow:"+
    "0px 2px 10px rgba(0,0,0,0.2),"+
    "0px 10px 20px rgba(0,0,0,0.3),"+
    "0px 30px 60px 1px rgba(0,0,0,0.5);"+
  "border-radius: 8px;"+
  "padding: 100px;"+
"}"+
"table,td,th"+"{"+
  "border: 1px solid black;"+
  "border-collapse: collapse;"+
 "}" +"</style>";
            //System.out.println(style);
            String script = "<script src='file.js'>" +       
                            "</script>";

            String html = "<html>" + 
                            "<head>" +
                            "<title>Class SEM</title>" +
                            style +
                            script +
                            "</head>";
			
            String table = "<table>" + 
                        "<tr>" +
                            //"<th>" + "Name" + "</th>" +
                            //"<th>" + "Roll No" + "</th>" +
                            "<th>" + "Cat No" + "</th>" ;
                        
            while(rs.next()) {
                table += "<th onclick=stusubject('" + rs.getString(2) + "','" + bno + "','" + sem + "','" + sec + "','" + roll + "')>" + rs.getString(3) + "</th>" ;
            }

            table += "</tr>";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                table += "<tr>";
                //table += "<td>" + rs.getString(2) + "</td>";
                //table += "<td>" + rs.getString(3) + "</td>";
                table += "<td>" + rs.getString(4) + "</td>";
                table += "<td>" + rs.getString(5) + "</td>";
                table += "<td>" + rs.getString(6) + "</td>";
                table += "<td>" + rs.getString(7) + "</td>";
                table += "<td>" + rs.getString(8) + "</td>";
                table += "<td>" + rs.getString(9) + "</td>";
                table += "<td>" + rs.getString(10) + "</td>";
                table += "</tr>";
            }

             table += "</table>";

             rs.close();

             pw.println(html +
                "<body>" +
                "<h1>Name: "+ nam + "</h1>" +
                "<h1>Roll Number: "+ roll + "</h1>" +
                "<h1>Class: "+ dep + " " + sec + "</h1>" +
                "<h1>Cat marks of Semester "+ sem + "</h1>" +
                table +
                "</body>" +
                "</html>"
             );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}