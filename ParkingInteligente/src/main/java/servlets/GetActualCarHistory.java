/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import db.CarHistory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import logic.Log;
import logic.Logic;

/**
 *
 * @author Usuario
 */
@WebServlet("/GetActualCarHistory")
public class GetActualCarHistory extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    public GetActualCarHistory() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Get Actual History from parking--");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try 
		{
			int idCiudad = Integer.parseInt(request.getParameter("idCiudad"));
			int idParking = Integer.parseInt(request.getParameter("idParking"));	
			Log.log.info("idCiudad= "+idCiudad);
			Log.log.info("idParking= "+idParking);
			
			ArrayList<CarHistory> values =Logic.getActualCarHistoryFromParking(idCiudad,idParking);
			String jsonHistorico = new Gson().toJson(values);
			Log.log.info("JSON Sensors Values=> {}", jsonHistorico);
			out.println(jsonHistorico);
		} catch (NumberFormatException nfe) 
		{
			out.println("-1");
			Log.log.error("Number Format Exception: {}", nfe);
		} catch (IndexOutOfBoundsException iobe) 
		{
			out.println("-1");
			Log.log.error("Index out of bounds Exception: {}", iobe);
		} catch (Exception e) 
		{
			out.println("-1");
			Log.log.error("Exception: {}", e);
		} finally 
		{
			out.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
