package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import db.Measurement;

public class WeatherForecast
{
	private static String APIkey = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

	
	private ArrayList<Measurement> getForecastCity(String city, String countryCode)
	{
		ArrayList<Measurement> weatherValues = new ArrayList<>();
		try
		{
			String urlStr = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + countryCode + "&appid=" + APIkey;
			URL url = new URL(urlStr);
			InputStream is = url.openStream();
			weatherValues = obtainPrediction(is);
		}catch(Exception e)
		{
			Log.log.error("Error searching weather by city: {}", e);
		}
		return weatherValues;
	}
	
	static ArrayList<Measurement> getForecastGps(String lat, String lon)
	{
		ArrayList<Measurement> weatherValues = new ArrayList<>();
		try
		{
			String urlStr = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&appid=" + APIkey;
			Log.log.info("Seraching forecast from: {}", urlStr);
			URL url = new URL(urlStr);
			InputStream is = url.openStream();
			weatherValues = obtainPrediction(is);
		}catch(Exception e)
		{
			Log.log.error("Error searching weather by GPS: {}", e);
		}
		return weatherValues;
	}

	
	public static ArrayList<Measurement> obtainPrediction(InputStream is)
	{
		ArrayList<Measurement> weather = new ArrayList<>();
		try
		{
			//EN: Obtain data from API in a StringBuffer
			//ES:Obtener información del API en el StringBuffer
			//InputStream is = getURLCity("Guadalajara","ES").openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			StringBuffer json = new StringBuffer();
			String inputLine;
			while ((inputLine = br.readLine()) != null)
			{
				json.append(inputLine);
			}

			Gson gson = new Gson();
			JsonElement element = gson.fromJson(json.toString(), JsonElement.class);
			com.google.gson.JsonObject o = element.getAsJsonObject();	
			
			int cont = 0;

			// ES: recorremos las horas que hay hasta las 12 de la maï¿½ana del dia siguiente desde este momento.
			// EN: we go through the hours until 12 in the morning of tomorrow from this moment.
			for (int i = 0; i < (36 - Calendar.getInstance().get(Calendar.HOUR_OF_DAY)); i++)
			{
				if (i % 3 == 0)
				{
					Date currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((o.get("list").getAsJsonArray().get(cont).getAsJsonObject().get("dt_txt")).toString().split("\"")[1]);
					
					double temp = Double.parseDouble((o.get("list").getAsJsonArray().get(cont).getAsJsonObject().get("main").getAsJsonObject().get("temp").toString())) - 272.15f;
					weather.add(new Measurement(1,currentDate, temp));
					
					double humidity = Double.parseDouble((o.get("list").getAsJsonArray().get(cont).getAsJsonObject().get("main").getAsJsonObject().get("humidity").toString()));
					weather.add(new Measurement(2,currentDate, humidity));
					
					double wind = Double.parseDouble((o.get("list").getAsJsonArray().get(cont).getAsJsonObject().get("wind").getAsJsonObject().get("speed").toString()));
					weather.add(new Measurement(6,currentDate, wind));
					
					cont++;
				}
			}

			is.close(); // Close connection, as it is not used anymore

		} catch (IOException e)
		{
			Log.log.error("WeatherObtainer Error: {}", e.toString());
		} catch (Exception e)
		{
			Log.log.error("WeatherObtainer Error: {}", e.toString());
		}
		return weather;
	}
}