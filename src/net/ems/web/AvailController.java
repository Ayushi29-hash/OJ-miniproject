package net.ems.web;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.ems.dao.Ambulance_AvailDao;
import net.ems.dao.Ambulane_RegisterDao;
import net.ems.model.Avail;
import net.ems.model.Register;




@WebServlet("/avail")

public class AvailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Ambulance_AvailDao userDao;
    private Ambulane_RegisterDao driverDao;
	public void init() {
		userDao = new Ambulance_AvailDao();
		driverDao= new Ambulane_RegisterDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		avail(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("avail/avail.jsp");
		
	}

	private void avail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String Name = request.getParameter("Name");
		String phoneno = request.getParameter("phoneno");
		String pickup = request.getParameter("pickup");
		String drop = request.getParameter("drop");
		String vehicle_type = request.getParameter("vehicle_type");
		

		Avail book = new Avail();
		book.setName(Name);
		book.setPhoneno(phoneno);
		book.setPickup(pickup);
		book.setDrop(drop);
		book.setVehicle_type(vehicle_type);
		Register driver = new Register();
		

		try {
			int result = userDao.registerAvail(book);
			
			if(result == 1) {
				driver=driverDao.registerEmployee(vehicle_type);
				if (driver.getFirstName() == null)
				{
					request.setAttribute("NOTIFICATION","NotFound");
				}
				else {
					Random rand = new Random();
					double rand_int1 = rand.nextDouble()*20; 
					 DecimalFormat df = new DecimalFormat("#");
				        df.setRoundingMode(RoundingMode.CEILING);
					double Km=rand_int1;
					double fare=0;
					int a=1,basic=0;
					String str1 = "AC",str2="Non AC",str3="ICU";
					
				
				//int result = userDao.
				request.setAttribute("NOTIFICATION", "We will provide your ambulance details shortly!");
				//Register driver = new Register();
				//System.out.println("hello" +driver.getFirstName());
				request.setAttribute("NOTIFICATION",driver.getFirstName());
				request.setAttribute("PHONE",driver.getPhoneno());
				request.setAttribute("VNUM",driver.getVehicleno());
				request.setAttribute("VTYPE",driver.getVehicletype());
				
				if(driver.getVehicletype().equals(str1))
				{
					basic=250;
					if (Km <= 5)
						fare = (Km * 50)+basic;
					else if (Km > 5 && Km <= 10)
					{
						fare = 5 * 50;
						fare = fare + ((Km - 5) * 70)+basic;
					} else
					{
						fare = (Km - 10) * 80;
						fare = fare + 5 * 50;
						fare = fare + (5 * 70)+basic;
					}
					request.setAttribute("FARE",df.format(fare));
				}
				if(driver.getVehicletype().equals(str2))
				{
					basic=500;
					if (Km <= 5)
						fare = (Km * 50) + basic;
					else if (Km > 5 && Km <= 10) {
						fare = 5 * 50;
						fare = fare + ((Km - 5) * 70) + basic;
					} else {
						fare = (Km - 10) * 80;
						fare = fare + 5 * 50;
						fare = fare + (5 * 70) + basic;
					}
					request.setAttribute("FARE",df.format(fare));
				}
				if(driver.getVehicletype().equals(str3))
				{
					basic = 750;
					if (Km <= 5)
						fare = (Km * 50) + basic;
					else if (Km > 5 && Km <= 10) {
						fare = 5 * 50;
						fare = fare + ((Km - 5) * 70) + basic;
					} else {
						fare = (Km - 10) * 80;
						fare = fare + 5 * 50;
						fare = fare + (5 * 70) + basic;
					}
					request.setAttribute("FARE",df.format(fare));
				}
			}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("avail/avail.jsp");
		dispatcher.forward(request, response);
	}
	
}