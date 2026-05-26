package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import model.ProductClass;
import service.ProductService;

public class Main{
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		ProductService service = new ProductService();
		
		char continua;
		
		service.insertProduct(new ProductClass(0, "Arroz", "food", 2, "kg", 5, LocalDate.of(2026, 12, 31)));
		service.insertProduct(new ProductClass(0, "Detergente", "cleaning", 1, "unit", 3, LocalDate.of(2027, 6, 15)));
		service.insertProduct(new ProductClass(0, "Leite", "drink", 8, "liter", 2, LocalDate.of(2026, 6, 10)));
		
		
		do {
			System.out.println("Escolha a ação: ");
			System.out.println("\n1 - Criar produto");
			System.out.println("2 - Ajustar quantidade");
			System.out.println("3 - Verificar estoque");
			System.out.println("4 - Listar estoque\n");
			
			int option = input.nextInt();
			switch (option) {
			
				case 1: {
					int id = 0;
					System.out.println("Digite o nome do produto: ");
					String name = input.next();
					
					System.out.println("Digite a categoria: ");
					String category = input.next();
					
					System.out.println("Digite a quantidade: ");
					int amount = input.nextInt();
					
					System.out.println("Digite o tipo de unidade: ");
					String unit = input.next();
					
					System.out.println("Digite a quantidade minima: ");
					int min_amount = input.nextInt();
					
					System.out.println("Digite a validade (dd/MM/yy): ");
					String expirationString = input.next();
					LocalDate expiration = LocalDate.parse(expirationString, DateTimeFormatter.ofPattern("dd/MM/yy"));
					
					
					ProductClass p = new ProductClass(id, name, category, amount, unit, min_amount, expiration);
					service.insertProduct(p);
					System.out.println("\nCriação com sucesso");
					break;
				}
				case 2: {
					service.listProducts();
					
					System.out.println("\nDigite o numero do produto a ser alterado: ");
					int id = input.nextInt();
					System.out.println("\nDigite a nova quantidade: ");
					int amount = input.nextInt();
					service.updateAmount(id, amount);
					break;
				}
				case 3: 
					service.verifyAmount();
					break;
				case 4: 
					service.listProducts();
					break;
				
				default: 
					System.out.println("\nInvalid");
			}
			System.out.println("\nDeseja realizar outra ação? (S/N)");
			continua = input.next().charAt(0);
			
		} while (continua != 'n' && continua != 'N');
		
		
		//fim
	}
}