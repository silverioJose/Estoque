package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import inventory.service.StockService;
import inventory.dao.*;
import inventory.model.*;

public class Main{
	
	public static void main(String[] args) throws Exception{
		DepartmentDAO departmentDAO = new DepartmentDAO();
		System.out.println("Inicio\n");
		
		System.out.println("Departamentos: \n");
		
		List<Department> list = departmentDAO.findAll();

		for (Department d : list) {
		    System.out.println(d.getId() + " - " + d.getName());
		}
		
	}
}