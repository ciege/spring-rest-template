package org.dedeler.template.service;

import javax.validation.ValidationException;

import org.dedeler.template.model.AbstractModel;

public interface GenericService<T extends AbstractModel> {

	/**
	 * Delete generic.
	 * 
	 * @param t
	 *            the t
	 */
	public void delete(T t);

	/**
	 * Find by id generic.
	 * 
	 * @param klaz
	 *            the klaz
	 * @param id
	 *            the id
	 * @return the t
	 */
	public T findById(Class<T> klaz, long id);

	/**
	 * Save generic.
	 * 
	 * @param t
	 *            the t
	 * @return the long
	 * @throws ValidationException
	 *             the validation exception
	 */
	public long save(T t) throws ValidationException;

	/**
	 * Update generic.
	 * 
	 * @param t
	 *            the t
	 * @return true, if successful
	 * @throws DataCreationException
	 */
	public boolean update(T t);
}
