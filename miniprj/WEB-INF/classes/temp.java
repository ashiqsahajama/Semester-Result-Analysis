import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
import java.sql.*;
import java.util.*;

public class temp extends HttpServlet
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	static final String DB_URL="jdbc:mysql://localhost/miniproj?autoReconnect=true&useSSL=false";
	static final String USER = "root";
	static final String PASS = "ssnce";
	public void doGet(HttpServletRequest rq, HttpServletResponse rp) throws ServletException, IOException
	{
		Cookie[] cookies = rq.getCookies();
        PrintWriter out = rp.getWriter();
        //iterate cookies array to get individual cookie objects.

        Map<String,String> map = new HashMap<>();
        //int i=0;

        for(Cookie cookie : cookies){

            System.out.println("Cookie Name: " + cookie.getName());
            System.out.println("Cookie Value: " + cookie.getValue());
            map.put(cookie.getName(), cookie.getValue());
            //cookieDetails[i] = cookie.getValue();
            //i++;
        }
        //for(i=0; i<5; i++) {
          //  out.println(cookieDetails[i]);
        //}

        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			
            String sql = "select id from batchh where batch = '" + map.get("batch") + "' and sec='" + map.get("sec") +"';";

            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            String id = null;
            if (rs.next()){
                id=rs.getString(1);
                System.out.println("id= "+id);
            }
            //rs.close();
            sql="select s_id from semm where id = '"+id+"' and sem ='"+ map.get("sem") +"';";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            String s_id = null;
            if (rs.next()){
                s_id = rs.getString(1);
                System.out.println("sid: "+s_id);
            }
            sql="select subject_name from sub where s_id = '"+s_id+"' and subject_code='"+map.get("subcode")+"';";
            String subject=null;
            rs = stmt.executeQuery(sql);
            if (rs.next()){
                subject=rs.getString(1);
                System.out.println("sub is :"+subject);
            }
            sql="select subject_code from sub where s_id = '"+s_id+"';";
            rs = stmt.executeQuery(sql);
            int i=0;
            while (rs.next()){
                i++;
                if(rs.getString(1).equals(map.get("subcode"))){
                    break;
                }
            }
            String subincat="sub_"+i;
            sql="select "+subincat+" from cat where s_id='"+s_id+"' and cat_num='"+map.get("cat")+"';";
            System.out.println(sql);
            rs=stmt.executeQuery(sql);
            int sum=0;
            int pass=0;
            int fail=0;
            int totalno=0;
            int clsavg=0;
            int O=0;
            int A=0;
            int A_plus=0;
            int B=0;
            int C=0;
            int F=0;

            while(rs.next()){
                //out.println(rs.getString(1));
                if (Integer.parseInt(rs.getString(1))>25){
                    pass=pass+1;
                }
                if (Integer.parseInt(rs.getString(1))<=25){
                    fail=fail+1;
                    F=F+1;
                }
                sum=sum+Integer.parseInt(rs.getString(1));
                totalno=totalno+1;
                clsavg=sum/totalno;
                if (Integer.parseInt(rs.getString(1))>=45){
                    O=O+1;
                } 
                if (Integer.parseInt(rs.getString(1))<45){
                    if (Integer.parseInt(rs.getString(1))>=40){
                        A_plus=A_plus+1;
                    }
                } 
                if ((Integer.parseInt(rs.getString(1))<40)){
                    if (Integer.parseInt(rs.getString(1))>=35){
                        A=A+1;
                    }
                }
                if (Integer.parseInt(rs.getString(1))<35){
                    if (Integer.parseInt(rs.getString(1))>=30){
                        B=B+1;
                    } 
                }
                if (Integer.parseInt(rs.getString(1))<30){
                    if (Integer.parseInt(rs.getString(1))>25){
                        C=C+1;
                    } 
                }  
            } 
            String style = "<style>" +
            "body"+"{"+
            "background: #136a8a;"+
            "}"+
            "h2"+"{"+
            "text-align:center;"+
            "}"+
            "table"+"{"+
            "height:30%;"+
            "width:100%;"+
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
            out.println("<html>" + "<head>" + "<title> analysis </title>"+style+"</head>"+"<body>");
            out.println("<h2>Analysing the Subject's Results</h2>");
            out.println("<h1>Subject: "+subject+"</h1>");
            out.println("<h1>Class: CSE "+map.get("sec")+"</h1>");
            out.println("<h1>CAT "+map.get("cat")+"</h1>");
            out.println("<h2>Class Average: "+clsavg+"</h2>");
            out.println("<table>"+"<tr>"+"<th> Total Pass </th>"+"<th> Total Fail </th>"+"</tr>");
            out.println("<tr>"+"<td>"+pass+"</td>"+"<td>"+fail+"</td>"+"</tr>"+"</table>");
            out.println("<br><br><br><br>");
            out.println("<table>"+"<tr>"+"<th> O </th>"+"<th> A+ </th>"+"<th> A </th>"+"<th> B </th>"+"<th> C </th>"+"<th> F </th>"+"</tr>");
            out.println("<tr>"+"<td>"+O+"</td>"+"<td>"+A_plus+"</td>"+"<td>"+A+"</td>"+"<td>"+B+"</td>"+"<td>"+C+"</td>"+"<td>"+F+"</td>"+"</tr>"+"</table>");
            out.println("</body>"+"</html>");
            rs.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
	}
}