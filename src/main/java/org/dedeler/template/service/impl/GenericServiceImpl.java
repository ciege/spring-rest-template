package org.dedeler.template.service.impl;

import javax.validation.ValidationException;

import org.dedeler.template.dao.GenericDao;
import org.dedeler.template.model.AbstractModel;
import org.dedeler.template.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class GenericServiceImpl<T extends AbstractModel> implements GenericService<T> {

	protected GenericDao<T> dao;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Instantiates a new generic service.
	 * 
	 * @param dao
	 *            the dao
	 */
	public GenericServiceImpl(GenericDao<T> dao) {
		this.dao = dao;
	}

	/**
	 * Delete generic.
	 * 
	 * @param t
	 *            the t
	 */
	@Override
	public void delete(T t) {
		dao.delete(t);
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
	@Override
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
	@Override
	public long save(T t) throws ValidationException {
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
	@Override
	public boolean update(T t) {
		return dao.update(t);
	}
}
