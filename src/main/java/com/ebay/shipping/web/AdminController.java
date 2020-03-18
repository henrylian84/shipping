package com.ebay.shipping.web;

import com.ebay.shipping.exception.ConflictResultException;
import com.ebay.shipping.exception.EmptyResultException;
import com.ebay.shipping.model.Category;
import com.ebay.shipping.model.Item;
import com.ebay.shipping.model.NumericRule;
import com.ebay.shipping.model.Seller;
import com.ebay.shipping.service.CategoryService;
import com.ebay.shipping.service.EnrollmentService;
import com.ebay.shipping.service.RuleEngineService;
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
 * This controller is meant for admin related endpoints, such as below:
 *
 * - seller enrollment:  get, create and delete
 * - pre-approved category: get, create and delete
 * - rules related operations
 *
 */
@Controller
@Path("/admin")
@Api(value = "Eligible Service Admin API")
public class AdminController {

    final static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Inject
    private EnrollmentService enrollmentService;
    @Inject
    private CategoryService categoryService;
    @Inject
    private RuleEngineService ruleEngineService;

    @PUT
    @Path("/enrollment/{name}")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success", response = Seller.class),
            @ApiResponse(code = 409, message = "conflict")})
    public Response enrollSeller(@PathParam("name") String name){
        Seller seller = null;
        try {
            seller = enrollmentService.enrollSeller(name);
        } catch (ConflictResultException e) {
            logger.error("record exists", e);
            return Response.status(409).build();
        }
        return Response.status(201).entity(seller).build();
    }

    @GET
    @Path("/enrollment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Seller.class, responseContainer = "List")})
    @Produces(APPLICATION_JSON)
    public Response getAllEnrolledSellers(){
        List<Seller> sellers = enrollmentService.getAllEnrolledSellers();
        return Response.status(200).entity(sellers).build();
    }

    @DELETE
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 404, message = "resource not found")})
    @Path("/enrollment/{id}")
    public Response deleteEnrolledSellerById(@PathParam("id") Long id){
        try {
            enrollmentService.removeEnrolledSellerById(id);
        } catch (Exception e) {
            return Response.status(404).build();
        }
        return Response.status(204).build();
    }

    @GET
    @Path("/category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Category.class, responseContainer = "List")})
    @Produces(APPLICATION_JSON)
    public Response getAllCategories(){
        List<Category> cats = categoryService.getAllCategories();
        return Response.status(200).entity(cats).build();
    }

    @PUT
    @Path("/category/{categoryId}")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success"),
            @ApiResponse(code = 409, message = "conflict")})
    public Response addCategory(@PathParam("categoryId") Integer categoryId){
        try {
            categoryService.addCategory(categoryId);
        } catch (ConflictResultException e) {
            logger.error("record exists", e);
            return Response.status(409).build();
        }
        return Response.status(201).build();
    }

    @DELETE
    @Path("/category/{categoryId}")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success")})
    public Response deleteCategory(@PathParam("categoryId") Integer categoryId){
        categoryService.removeCategory(categoryId);
        return Response.status(204).build();
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Path("/rules/valueCompare")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success" ,response = NumericRule.class),
            @ApiResponse(code = 409, message = "conflict")})
    public Response createNumericRule(NumericRule numericRule){
        if(!numericRule.isValid()) return Response.status(400).build();
        NumericRule rule = null;
        try {
            rule = ruleEngineService.addNumericRule(numericRule);
        } catch (ConflictResultException e) {
            logger.error("record exists", e);
            return Response.status(409).build();
        }
        return Response.status(201).entity(rule).build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/rules/valueCompare")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = NumericRule.class, responseContainer = "List")})
    public Response getAllNumericRules(){
        List<NumericRule> result = ruleEngineService.getAllNumericRules();
        return Response.status(200).entity(result).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Path("/rules/valueCompare")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 400, message = "bad request"),
            @ApiResponse(code = 404, message = "resource not found")})
    public Response updateNumericRule(NumericRule numericRule){
        if(numericRule.getId() == 0 || !numericRule.isValid()) return Response.status(400).build();
        try {
            ruleEngineService.updateNumericRule(numericRule);
        } catch (EmptyResultException e) {
            logger.error("result not found");
            return Response.status(404).build();
        }
        return Response.status(204).build();
    }

    @DELETE
    @Consumes(APPLICATION_JSON)
    @Path("/rules/valueCompare/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 404, message = "resource not found")})
    public Response deleteNumericRuleById(@PathParam("id") Long id){
        try {
            ruleEngineService.deleteNumericRuleById(id);
        } catch (Exception e) {
            logger.error("result not found");
            return Response.status(404).build();
        }
        return Response.status(204).build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/rules")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class, responseContainer = "List")})
    public Response getAllRules(){
        List<String> rules = ruleEngineService.getAllRules();
        return Response.status(200).entity(rules).build();
    }

}
