package Controller;
import java.util.Scanner;

import org.bson.types.ObjectId;

import mongodbquery.ItensDoCarrinhoDAO;
import utils.Utils;

public class CarrinhoController {
    
    private static final Scanner entrada = new Scanner(System.in);
    
    public static void removeItemDoCarrinho(ObjectId id_carrinho, ItensDoCarrinhoDAO dao) 
        {

            System.out.println("------------------------------------");
            System.out.println();
            System.out.println("DIGITE O NOME DO PRODUTO(EXATAMENTE COMO MOSTRADO):");
            String nomeProduto = entrada.nextLine();
            System.out.println("------------------------------------");
            System.out.println();

            dao.removeItensDoCarrinho(id_carrinho, nomeProduto);

        }
        

}
