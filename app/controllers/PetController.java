package controllers;

import java.util.List;
import play.mvc.*;

import views.html.*;
import models.*;
import services.*;
import play.libs.*;
/**
 * This controller contains actions to handle rest api calls
 */


public class PetController extends Controller {

    public Result fetch() {
        try{
    		String values[] = request().queryString().get("id");
    		String id = null;
    		if(values != null && values.length > 0) {
    			id = values[0];
    		}
    		if(id == null) {
    			List<Pet> results = MongoDbPetService.getInstance().fetchAll();	
    			return ok(Json.toJson(results));
    		}else {
    			Pet p = new Pet();
    			p.setId(id);
    			Pet result = MongoDbPetService.getInstance().fetch(p);	
    			return ok(Json.toJson(result));
    		}
    	} catch(Exception e) {
    		return internalServerError();
    	}
    }

    public Result add() {
    	try{
    		Pet pet = Json.fromJson(request().body().asJson(), Pet.class);
        	MongoDbPetService.getInstance().add(pet);
        	return ok();
    	} catch(Exception e) {
    		return internalServerError(e.getMessage());
    	}
    }

    public Result update() {
    	try{
    		Pet pet = Json.fromJson(request().body().asJson(), Pet.class);
        	MongoDbPetService.getInstance().update(pet);
        	return ok();
    	} catch(Exception e) {
    		return internalServerError();
    	}
    }

    public Result delete() {
    	try{
    		String values[] = request().queryString().get("id");
    		String id = null;
    		if(values != null && values.length > 0) {
    			id = values[0];
    		}
    		if(id == null) {
    			return internalServerError("Id is missing");
    		}else {
    			Pet p = new Pet();
    			p.setId(id);
    			MongoDbPetService.getInstance().delete(p);	
    			return ok();
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    		return internalServerError();
    	}
    }

}
