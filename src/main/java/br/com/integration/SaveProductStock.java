package br.com.integration;

import br.com.domain.ProductStock;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8093/api/v1/stock")
public interface SaveProductStock {

    @POST
    Response saveProductStock(ProductStock payloadToSend);

}
