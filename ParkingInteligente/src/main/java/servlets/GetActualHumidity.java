package servlets;

import com.google.gson.Gson;
import db.Measurement;
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
 * Servlet implementation class GetActualHumidity
 */
@WebServlet("/GetActualHumidity")
public class GetActualHumidity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetActualHumidity() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Get Actual Humidity from parking--");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try 
		{
			int idCiudad = Integer.parseInt(request.getParameter("idCiudad"));
			int idParking = Integer.parseInt(request.getParameter("idParking"));
			int idTipo = Integer.parseInt(request.getParameter("idTipo"));	
			Log.log.info("idCiudad= "+idCiudad);
			Log.log.info("idParking= "+idParking);
			Log.log.info("idTipo= "+idTipo);
			
			ArrayList<Measurement> values =Logic.getActualHumidityFromParking(idCiudad,idParking,idTipo);
			String jsonGases = new Gson().toJson(values);
			Log.log.info("JSON Sensors Values=> {}", jsonGases);
			out.println(jsonGases);
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
