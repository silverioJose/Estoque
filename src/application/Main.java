package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import inventory.service.StockService;
import inventory.dao.*;
import inventory.model.*;

public class Main{
	
	public static void main(String[] args) throws Exception{
		DepartmentDAO departmentDAO = new DepartmentDAO();
		System.out.println("Inicio\n");
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Criação de departamento: \n");
		System.out.println("Digite o nome: \n");
		
		
		
		String departamento = input.nextLine();
		Department d = new Department();
		d.setName(departamento);
		departmentDAO.insert(d);
		departmentDAO.findAll();
		
		input.close();
	}
}