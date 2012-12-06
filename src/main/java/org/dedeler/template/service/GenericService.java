package org.dedeler.template.service;

import javax.validation.ValidationException;

import org.dedeler.template.dao.GenericDao;
import org.dedeler.template.model.AbstractModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericService<T extends AbstractModel> {

	protected GenericDao<T> dao;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Instantiates a new generic service.
	 * 
	 * @param dao
	 *            the dao
	 */
	public GenericService(GenericDao<T> dao) {
		this.dao = dao;
	}

	
	/**
	 * Generic delete a persistent object.
	 * @param t The instance to be deleted
	 * @return true for success, false for failure
	 */
	public boolean delete(T t) {
		return dao.delete(t);
	}
	
	/**
	 * Generic delete a persistent object.
	 * @param oid The oid of the object to be deleted
	 * @param persistentClass The class for the object
	 * @return  true for success, false for failure
	 */
	public boolean delete(Long oid, Class<T> persistentClass) {
		return dao.delete(oid, persistentClass);
	}

	/**
	 * Find by id generic.
	 * 
	 * @param klaz
	 *            the klaz
	 * @param id
	 *            the id
	 * @return the t
	 */
	public T findById(Class<T> klaz, long id) {
		return dao.findById(klaz, id);
	}

	/**
	 * Save generic.
	 * 
	 * @param t
	 *            the t
	 * @return the long
	 * @throws ValidationException
	 *             the validation exception
	 */
	public Long save(T t) throws ValidationException {
		return dao.save(t);
	}

	/**
	 * Update generic.
	 * 
	 * @param t
	 *            the t
	 * @return true, if successful
	 * @throws DataCreationException
	 */
	public boolean update(T t) {
		return dao.update(t);
	}
}
