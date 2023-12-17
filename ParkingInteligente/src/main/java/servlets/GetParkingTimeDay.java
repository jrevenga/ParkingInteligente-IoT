package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import db.CarHistory;
import java.util.Map;
import logic.Log;
import logic.Logic;

/**
 * SERVLET THAR SEARCH ALL THE STATIONS STORED IN THE DATABASE
 */
@WebServlet("/GetParkingTimeDay")
public class GetParkingTimeDay extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public GetParkingTimeDay() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Get Month History from parking--");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try 
		{
			int idCiudad = Integer.parseInt(request.getParameter("idCiudad"));
			int idParking = Integer.parseInt(request.getParameter("idParking"));	
			Log.log.info("idCiudad= "+idCiudad);
			Log.log.info("idParking= "+idParking);
			
			ArrayList<Map<Long, CarHistory>> values =Logic.getParkingTimeDayFromParking(idCiudad,idParking);
			String jsonEstacionamiento = new Gson().toJson(values);
			Log.log.info("JSON Sensors Values=> {}", jsonEstacionamiento);
			out.println(jsonEstacionamiento);
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