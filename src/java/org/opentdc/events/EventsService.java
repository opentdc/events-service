/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Arbalo AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.opentdc.events;

import java.util.List;
import java.util.logging.Logger;

// import io.swagger.annotations.*;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.opentdc.service.GenericService;
import org.opentdc.service.exception.DuplicateException;
import org.opentdc.service.exception.InternalServerErrorException;
import org.opentdc.service.exception.NotFoundException;
import org.opentdc.service.exception.ValidationException;

/*
 * @author Bruno Kaiser
 */
@Path("/api/event")
// @Api(value = "/api/event", description = "Operations about events")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventsService extends GenericService<ServiceProvider> {
	
	private static ServiceProvider sp = null;

	private static final Logger logger = Logger.getLogger(EventsService.class.getName());

	/**
	 * Invoked for each service invocation (Constructor)
	 * @throws ReflectiveOperationException
	 */
	public EventsService(
		@Context ServletContext context
	) throws ReflectiveOperationException{
		logger.info("> EventsService()");
		if (sp == null) {
			sp = this.getServiceProvider(EventsService.class, context);
		}
		logger.info("EventsService() initialized");
	}

	/******************************** events *****************************************/
	@GET
	@Path("/")
//	@ApiOperation(value = "Return a list of all events", response = List<EventsModel>.class)
	public List<EventModel> list(
		@DefaultValue(DEFAULT_QUERY) @QueryParam("query") String query,
		@DefaultValue(DEFAULT_QUERY_TYPE) @QueryParam("queryType") String queryType,
		@DefaultValue(DEFAULT_POSITION) @QueryParam("position") int position,
		@DefaultValue(DEFAULT_SIZE) @QueryParam("size") int size
	) {
		return sp.list(query, queryType, position, size);
	}

	@POST
	@Path("/")
	//	@ApiOperation(value = "Create a new event", response = EventsModel.class)
	//	@ApiResponses(value = 
	//			{ @ApiResponse(code = 409, message = "An object with the same id exists already (CONFLICT)") },
	//			{ @ApiResponse(code = 400, message = "Invalid ID supplied or mandatory field missing (BAD_REQUEST)" })
	public EventModel create(
		EventModel event) 
	throws DuplicateException, ValidationException {
		return sp.create(event);
	}

	@GET
	@Path("/{id}")
	//	@ApiOperation(value = "Find a event by id", response = EventsModel.class)
	//	@ApiResponses(value = { 
	//			@ApiResponse(code = 405, message = "An object with the given id was not found (NOT_FOUND)" })
	public EventModel read(
		@PathParam("id") String id
	) throws NotFoundException {
		return sp.read(id);
	}

	@PUT
	@Path("/{id}")
	//	@ApiOperation(value = "Update the event with id with new values", response = EventsModel.class)
	//	@ApiResponses(value =  
	//			{ @ApiResponse(code = 405, message = "An object with the given id was not found (NOT_FOUND)" },
	//			{ @ApiResponse(code = 400, message = "Invalid new values given or trying to change immutable fields (BAD_REQUEST)" })
	public EventModel update(
		@Context HttpServletRequest request,
		@PathParam("id") String id,
		EventModel event
	) throws NotFoundException, ValidationException {
		return sp.update(request, id, event);
	}

	@DELETE
	@Path("/{id}")
	//	@ApiOperation(value = "Delete the event with the given id" )
	//	@ApiResponses(value =  
	//			{ @ApiResponse(code = 405, message = "An object with the given id was not found (NOT_FOUND)" },
	//			{ @ApiResponse(code = 500, message = "Data inconsistency found (INTERNAL_SERVER_ERROR)" })
	public void delete(
		@PathParam("id") String id) 
	throws NotFoundException, InternalServerErrorException {
		sp.delete(id);
	}
	
	@GET
	@Path("/{id}/message")
	public String getMessage(
			@PathParam("id") String id)
		throws NotFoundException, InternalServerErrorException {
		return sp.getMessage(id);
	}
	
	// TODO: should be POST, but it is easier to test
	@GET
	@Path("/{id}/send")
	public void sendMessage(
			@PathParam("id") String id)
		throws NotFoundException, InternalServerErrorException {
		sp.sendMessage(id);
	}
	
	// TODO: should be POST, but it is easier to test
	@GET
	@Path("/{id}/sendall")
	public void sendAllMessages()
		throws InternalServerErrorException {
		sp.sendAllMessages();
	}
	
	@PUT
	@Path("/{id}/register")
	public void register(
			@Context HttpServletRequest request,
			@PathParam("id") String id,
			String comment)
		throws NotFoundException, ValidationException {
		sp.register(request, id, comment);
	}
	
	@PUT
	@Path("/{id}/deregister")
	public void deregister(
			@Context HttpServletRequest request,
			@PathParam("id") String id,
			String comment)
		throws NotFoundException, ValidationException {
		sp.deregister(request, id, comment);
	}
}
