package Controller;
import Model.*;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.*;

public class DemoServ extends HttpServlet{

public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
{
res.setContentType("text/html");
PrintWriter pw=res.getWriter();
String name=req.getParameter("name");
pw.println("Welcome "+name);

demo d = new demo();
try {
ArrayList<String> list = d.loginUser();

for(String s: list)
    pw.println(s);
}
catch(Exception e) {
    System.out.println(e);
}


}

}
