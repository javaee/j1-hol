package com.example.jakartaeesecurity;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.security.DeclareRoles;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author David R. Heffelfinger <dheffelfinger@ensode.com>
 */
@DeclareRoles({"foo", "bar"})
@ServletSecurity(
        @HttpConstraint(rolesAllowed = "foo"))
@BasicAuthenticationMechanismDefinition(realmName = "HOL-basic")
//@EmbeddedIdentityStoreDefinition({
//    @Credentials(callerName = "david", password = "david", groups = {"foo"})
//    ,
//    @Credentials(callerName = "ed", password = "ed", groups = {"bar",})
//    ,
//    @Credentials(callerName = "michael", password = "michael", groups = {"foo"})}
//)
@WebServlet(name = "SecuredServlet", urlPatterns = {"/test"})
public class SecuredServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html><html><body>");
            out.println("<div style=\"font-size:150%;font-weight:100;font-family: sans-serif;");
            out.println("text-align: center;color: DimGray;margin-top: 40vh;line-height: 150%\">");
            out.println("Jakarta EE 8 HoL<br/>");
            out.println(request.getAuthType());
            out.println("</div></body></html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
