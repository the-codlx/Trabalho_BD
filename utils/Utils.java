package utils;

import java.util.Scanner;

public class Utils {
    
    
    public static int produtoId(Scanner scanner) {

        int id = -1;  
        boolean entradaValida = false;
        
        while (!entradaValida) 

        {

            try {

                System.out.println("Digite o id do produto:");
                id = Integer.parseInt(scanner.nextLine());
                
                if (id > 0) 
                {

                    entradaValida = true;
                    
                } 

                else 
                {
                    
                    System.out.println("O id deve ser um número positivo. Tente novamente.");
                
                }
            }
            
            catch (NumberFormatException e) {

                System.out.println("Entrada inválida. Por favor, digite um número.");

            }

        }
        
        return id;

    }

}
