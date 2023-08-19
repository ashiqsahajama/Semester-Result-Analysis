import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
import java.sql.*;
import java.util.*;

public class studentsemana extends HttpServlet
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
            sql="select "+subincat+" from semester where s_id='"+s_id+"' and Roll_No='"+ map.get("roll") +"';";
            //System.out.println(sql);
            String mark=null;
            rs=stmt.executeQuery(sql);
            if (rs.next()){
                mark=rs.getString(1);
                //System.out.println("sub is :"+subject);
            }
            sql="select name from semester where roll_no='"+map.get("roll")+"'limit 1;";
            String name=null;
            rs=stmt.executeQuery(sql);
            if (rs.next()){
                name=rs.getString(1);
            }
            
            sql="select "+subincat+" from semester where s_id='"+s_id+"';";
            //System.out.println(sql);
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
            int Rank=1;
            String grade=null;
            String resu=null;

            while(rs.next()){
                //out.println(rs.getString(1));
                if (Integer.parseInt(rs.getString(1))>50){
                    pass=pass+1;
                    if (Integer.parseInt(rs.getString(1))==Integer.parseInt(mark)){
                        resu="Pass";
                    }
                }
                if (Integer.parseInt(rs.getString(1))<=50){
                    fail=fail+1;
                    F=F+1;
                    if (Integer.parseInt(rs.getString(1))==Integer.parseInt(mark)){
                        grade="F";
                        resu="Fail";
                    }
                }
                if (Integer.parseInt(rs.getString(1))>Integer.parseInt(mark)){
                    Rank=Rank+1;
                }
                sum=sum+Integer.parseInt(rs.getString(1));
                totalno=totalno+1;
                clsavg=sum/totalno;
                if (Integer.parseInt(rs.getString(1))>=90){
                    O=O+1;
                    if (Integer.parseInt(rs.getString(1))==Integer.parseInt(mark)){
                        grade="O";
                    }
                } 
                if (Integer.parseInt(rs.getString(1))<90){
                    if (Integer.parseInt(rs.getString(1))>=80){
                        A_plus=A_plus+1;
                        if (Integer.parseInt(rs.getString(1))==Integer.parseInt(mark)){
                        grade="A+";
                    }
                    }
                } 
                if ((Integer.parseInt(rs.getString(1))<80)){
                    if (Integer.parseInt(rs.getString(1))>=70){
                        A=A+1;
                        if (Integer.parseInt(rs.getString(1))==Integer.parseInt(mark)){
                        grade="A";
                    }
                    }
                }
                if (Integer.parseInt(rs.getString(1))<70){
                    if (Integer.parseInt(rs.getString(1))>=60){
                        B=B+1;
                        if (Integer.parseInt(rs.getString(1))==Integer.parseInt(mark)){
                        grade="B";
                    }
                    } 
                }
                if (Integer.parseInt(rs.getString(1))<60){
                    if (Integer.parseInt(rs.getString(1))>50){
                        C=C+1;
                        if (Integer.parseInt(rs.getString(1))==Integer.parseInt(mark)){
                        grade="C";
                    }
                    } 
                }  
            } 
            String style = "<style>" +
            "body"+"{"+
            "background: #136a8a;"+
            "}"+
            "h1"+"{"+
            "text-align:center;"+
            "}"+
            "table"+"{"+
            "height:30%;"+
            "width:50%;"+
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
            out.println("<h1>Analysing the Student's Results</h1>");
            out.println("<h2>Name: "+name+"</h2>");
            out.println("<h2>Roll: "+map.get("roll")+"</h2>");
            out.println("<h2>Subject: "+subject+"</h2>");
            out.println("<table>"+
                        "<tr>"+
                        "<th>Marks obtained</th>"+
                        "<td>"+mark+"</td>"+
                        "</tr>"+
                        "<tr>"+
                        "<tr>"+
                        "<th>Result</th>"+
                        "<td>"+resu+"</td>"+
                        "</tr>"+
                        "<tr>"+
                        "<th>Grade</th>"+
                        "<td>"+grade+"</td>"+
                        "</tr>"+
                        "<tr>"+
                        "<th>Class Average Mark</th>"+
                        "<td>"+clsavg+"</td>"+
                        "</tr>"+
                        "<tr>"+
                        "<th>Class Rank</th>"+
                        "<td>"+Rank+"</td>"+
                        "</tr>"+
                        "</table>"
                        );
            out.println("</body>"+"</html>");
            rs.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
	}
}