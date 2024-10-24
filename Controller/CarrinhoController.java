package Controller;

import sql.ItensDoCarrinhoDAO;
import utils.Utils;

public class CarrinhoController {
    
    
    public static void removeItemDoCarrinho(int id_carrinho, ItensDoCarrinhoDAO dao) 
        {

            System.out.println("------------------------------------");
            System.out.println();
            System.out.println("Digite o ID do produto:");
            int id = Utils.Opcao();
            System.out.println("------------------------------------");
            System.out.println();

            dao.removeItensDoCarrinho(id_carrinho, id);

        }
        

}
