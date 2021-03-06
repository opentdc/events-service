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

import javax.servlet.http.HttpServletRequest;

import org.opentdc.service.exception.DuplicateException;
import org.opentdc.service.exception.InternalServerErrorException;
import org.opentdc.service.exception.NotFoundException;
import org.opentdc.service.exception.ValidationException;

/*
 * interface ServiceProvider encapsulates implementations.
 * @author Bruno Kaiser
 */
public interface ServiceProvider {
	
	public abstract List<EventModel> list(
		String query,
		String queryType,
		int position,
		int size
	);

	public abstract EventModel create(
		EventModel event) 
	throws DuplicateException, ValidationException;

	public abstract EventModel read(
		String id) 
	throws NotFoundException;

	public abstract EventModel update(
		HttpServletRequest request,
		String id, 
		EventModel event) 
	throws NotFoundException, ValidationException;

	public abstract void delete(
		String id) 
	throws NotFoundException, InternalServerErrorException;
	
	public abstract String getMessage(
			String id)
	throws NotFoundException, InternalServerErrorException;
	
	public abstract void sendMessage(
			String id)
	throws NotFoundException, InternalServerErrorException;

	public abstract void sendAllMessages()
	throws InternalServerErrorException;
	
	public abstract void register(
			HttpServletRequest request,
			String id,
			String comment)
	throws NotFoundException, ValidationException;
	
	public abstract void deregister(
			HttpServletRequest request,
			String id,
			String comment)
	throws NotFoundException, ValidationException;
}
