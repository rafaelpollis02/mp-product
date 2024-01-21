package br.com.resource;

import br.com.domain.Product;
import br.com.exception.BusinessException;
import br.com.service.ProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("api/v1/product")
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @GET
    @Path("/{id}")
    public Product getProductByid(@PathParam("id") Long id) throws BusinessException {
        return productService.getProductById(id);
    }

    @POST
    @Transactional
    public Response saveProduct(Product product) {
      productService.saveProduct(product);
      return Response.status(Response.Status.CREATED).entity(product).build();
    }


}
