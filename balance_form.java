/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author lenovo1
 */
public class balance_form extends HttpServlet 

{
    Connection cn=null;
    Statement st=null;
    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        HttpSession session=req.getSession();
        String accountno=req.getParameter("accountno");
        String usernm=req.getParameter("usernm");
        String password=req.getParameter("password");
        
        String balance="";
        String event= req.getParameter("submit");
        
        
        out.println(accountno);
        out.println(usernm);
        out.println(password);
        out.println(event);
        
        Database db = new Database();
        String result = db.Connectdb();
        out.println(result)
                ;
        
        if(event.equals("SUBMIT"))
        {
            if(accountno.equals("")||usernm.equals("")||password.equals(""))
            {
                resp.setContentType("text/html");
                out.println("<script type='text/javascript'>");
                out.println("alert('Something is empty')");
                out.println("location='balance_form.jsp'");
                out.println("</script>");
            }
            else
            {
                try
                    {
                    if(usernm.equals(session.getAttribute("username")) && password.equals(session.getAttribute("password")))
                    { 
                        
                                 Class.forName("com.mysql.jdbc.Driver");
                                 cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/online_banking_system","root","root");
                                 st=cn.createStatement();
                                 
                                   
                                 
                                 String sql2 = "select * from new_acc where user_id ='"+session.getAttribute("user_id")+"' && account_number = '"+accountno.toString()+"' ";
                                 ResultSet rs2 = st.executeQuery(sql2);
                                 if(rs2.next())
                                    {
                                        
                                        balance = rs2.getString("amount");  
                                        session.setAttribute("balance", balance);
                                        resp.sendRedirect("balance_form.jsp");

                                     }
                    }
                    else
                    {
                                     resp.setContentType("text/html");
                                    out.println("   <script type=\"text/javascript\">   ");
                                    out.println("   alert('Account Not Found');                ");
                                    out.println("   location='balance_form.jsp';            ");
                                    out.println("   </script>                           "); 
                    }
                                
                               
                }
                catch(Exception ex)
                {
                                resp.setContentType("text/html");

                                out.println("   <script type=\"text/javascript\"> ");
                                out.println("   alert('"+ex.toString()+"'); ");
                                out.println("   location='balance_form.jsp'; ");
                                out.println("   </script>  ");
                }
            }
        }
        
        
         if(event.equals("CANCEL"))
        {
               resp.sendRedirect("user_homepage.jsp");
        
        } 
                    
        }
}
