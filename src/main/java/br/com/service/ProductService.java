package br.com.service;

import br.com.domain.Product;
import br.com.domain.ProductStock;
import br.com.exception.BusinessException;
import br.com.integration.SaveProductStock;
import br.com.repository.ProductRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Inject
    @RestClient
    SaveProductStock saveProductStock;

    public Product saveProduct(Product product) {

        productRepository.persist(product);

        try {

            ProductStock productStock = new ProductStock(product.getId(), 0);
            Response response = saveProductStock.saveProductStock(productStock);

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                System.out.println("Produto (" + product.getId() + ") " + product.getName() + " salvo no banco de dados e no estoque!");
            } else {
                System.out.println("Erro ao chamar o serviço de estoque. Resposta do servidor: " + response.getStatusInfo().getReasonPhrase());
            }
        } catch (Exception e) {
            System.out.println("Erro ao chamar o serviço de estoque: " + e.getMessage());

        }
        return product;
    }

    @RolesAllowed("manager")
    public List<Product> getAllProduct() {
        return productRepository.listAll();
    }

    public Product getProductById(@PathParam("id") Long id) throws BusinessException {

        Product product = productRepository.findById(id);

        if (product == null) {
            throw new BusinessException("Não existe um produto com o ID " + id);
        }
        System.out.println("Produto (" + product.getId() + ") " + product.getName() + " foi consultado !");
        return product;
    }
}

