package org.dedeler.template.dao;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

import org.dedeler.template.exception.ApiDaoLayerException;
import org.dedeler.template.model.AbstractModel;
import org.dedeler.template.service.LoggingService;
import org.dedeler.template.service.LoggingService.LogType;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * All DAOs shall extend this class to have generic CRUD operations implementations.
 * Any DAO extending this class not have to implement basic CRUD operations.
 * 
 * @author destan
 * 
 * @param <T>
 */
@Repository
public class GenericDao<T extends AbstractModel> {

	@Autowired
	private LocalValidatorFactoryBean localValidator;

	@Autowired
	private SessionFactory sessionFactory;

	protected final Logger logger = LoggingService.getLogger(LogType.DAO);

	public Criteria createCriteria(Class<? extends AbstractModel> persistentClass) {
		Criteria sessionCriteria = sessionFactory.getCurrentSession().createCriteria(persistentClass);
		sessionCriteria.add(Restrictions.eq("deleted", false));

		return sessionCriteria;
	}

	public Criteria createCriteria(Class<? extends AbstractModel> persistentClass, int pageNumber, int maxResults) {
		if (maxResults <= 0) {
			maxResults = 10;
		}

		int firstResult = pageNumber * maxResults;

		logger.debug("GenericDaoImpl#createCriteria: pageNumber: " + pageNumber + " maxResults: " + maxResults + " firstResult: " + firstResult);

		Criteria sessionCriteria = createCriteria(persistentClass);
		sessionCriteria.setFirstResult(firstResult);
		sessionCriteria.setMaxResults(maxResults);

		return sessionCriteria;
	}

	/* CRUD operations */
	public long save(T t) {
		try {
			checkValidations(t);
			t.setModificationDate(Calendar.getInstance());
			Long l = (Long) sessionFactory.getCurrentSession().save(t);
			return l;
		}
		catch (DataIntegrityViolationException e) {
			logger.error("DataIntegrityViolationException", e);
			throw new ApiDaoLayerException(e);
		}
		catch (Exception e) {
			logger.error("Exception", e);
			throw new ApiDaoLayerException(e);
		}
	}

	public boolean merge(T t) {
		try {
			checkValidations(t);
			t.setModificationDate(Calendar.getInstance());
			t.setDeleted(false);
			sessionFactory.getCurrentSession().merge(t);
			return true;
		}
		catch (DataIntegrityViolationException e) {
			logger.error("DataIntegrityViolationException", e);
			throw new ApiDaoLayerException(e);
		}
		catch (Exception e) {
			logger.error("Exception", e);
			throw new ApiDaoLayerException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<T> findAll(Class<T> persistentClass) {
		return createCriteria(persistentClass).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	public T findById(Class<T> persistentClass, final long id) {
		try {
			return (T) createCriteria(persistentClass).add(Restrictions.eq("id", id)).uniqueResult();
		}
		catch (Exception e) {
			logger.error("Exception", e);
			throw new ApiDaoLayerException(e);
		}
	}

	public boolean update(T t) {
		try {
			checkValidations(t);
			t.setModificationDate(Calendar.getInstance());
			sessionFactory.getCurrentSession().update(t);
			return true;
		}
		catch (DataIntegrityViolationException e) {
			logger.error("DataIntegrityViolationException", e);
			throw new ApiDaoLayerException(e);
		}
		catch (Exception e) {
			logger.error("DataIntegrityViolationException", e);
			throw new ApiDaoLayerException(e);
		}
	}

	public boolean delete(T t) {
		try {
			t.setDeleted(true);
			t.setModificationDate(Calendar.getInstance());
			t.setDeletionDate(Calendar.getInstance());
			sessionFactory.getCurrentSession().save(t);
			return true;
		}
		catch (DataAccessException e) {
			logger.error("DataAccessException", e);
			throw new ApiDaoLayerException(e);
		}
	}

	public boolean hardDelete(T t) {
		try {
			sessionFactory.getCurrentSession().delete(t);
			return true;
		}
		catch (DataAccessException e) {
			logger.error("DataAccessException", e);
			throw new ApiDaoLayerException(e);
		}
	}

	/* End of CRUD operations */

	private void checkValidations(T t) throws ValidationException {
		Set<ConstraintViolation<T>> constraintViolations = localValidator.validate(t);
		if (constraintViolations.size() > 0) {
			Iterator<ConstraintViolation<T>> it = constraintViolations.iterator();
			ConstraintViolation<T> cv = null;
			while (it.hasNext()) {
				cv = it.next();

				logger.error(cv.getMessage());
				logger.error(cv.toString());
				logger.error(cv.getInvalidValue().toString());
				logger.error(cv.getMessageTemplate());
			}
			throw new ApiDaoLayerException(new ValidationException("Validation failed: " + cv.getMessage()));
		}
	}

}
