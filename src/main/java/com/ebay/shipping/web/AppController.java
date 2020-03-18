package com.ebay.shipping.web;

import com.ebay.shipping.model.Item;
import com.ebay.shipping.service.EligibilityService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 *
 * eligible controller, all service related endpoints go here.
 * @author henrylian
 */
@Controller
@Path("/shipping")
@Api(value = "Eligible Service API")
public class AppController {

    final static Logger logger = LoggerFactory.getLogger(AppController.class);

    @Inject
    private EligibilityService eligibilityService;

    @POST
    @Path("eligible")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @ApiOperation(value = "check shipping eligibility", response =Item.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Item.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "bad request")})
    public Response isEligible(List<Item> items) {
        //validation
        Item invalid = items.stream().filter(item -> !item.isValid()).findAny().orElse(null);
        if(invalid != null) {
            logger.debug("malformed input");
            return Response.status(400).build();
        }

        items.stream().forEach(item -> item.setEligible(eligibilityService.isEligible(item)));
        return Response.status(200).entity(items).build();
    }
}
