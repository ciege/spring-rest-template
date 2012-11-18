package org.dedeler.template.dao;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

import org.dedeler.template.model.AbstractModel;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Repository
public class GenericDao<T extends AbstractModel> {

	@Autowired
	private LocalValidatorFactoryBean localValidator;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(GenericDao.class);

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

		System.out
				.println("GenericDaoImpl#createCriteria: pageNumber: " + pageNumber + " maxResults: " + maxResults + " firstResult: " + firstResult);

		Criteria sessionCriteria = createCriteria(persistentClass);
		sessionCriteria.setFirstResult(firstResult);
		sessionCriteria.setMaxResults(maxResults);

		return sessionCriteria;
	}

	public boolean delete(T t) {
		try {
			t.setDeleted(true);
			t.setModificationDate(Calendar.getInstance());
			t.setDeletionDate(Calendar.getInstance());
			sessionFactory.getCurrentSession().update(t);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			logger.error("DataAccessException", e);
			return false;
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
			e.printStackTrace();
			return null;
		}
	}

	public boolean hardDelete(T t) {
		try {
			sessionFactory.getCurrentSession().delete(t);
			return true;
		}
		catch (DataAccessException e) {
			e.printStackTrace();
			logger.error("Unexpected error", e);
			return false;
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
			e.printStackTrace();
			return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Long save(T t) {
		try {
			checkValidations(t);
			t.setModificationDate(Calendar.getInstance());
			// getSingleSession().beginTransaction().begin();
			Long l = (Long) sessionFactory.getCurrentSession().save(t);
			// getSingleSession().beginTransaction().commit();
			return l;
		}catch(ValidationException e){
			e.printStackTrace(); 
			return null; //FIXME: maybe escalate? consider.
		}
		catch (DataIntegrityViolationException e) {
			e.printStackTrace(); //FIXME: log
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();  //FIXME: log
			return null;
		}
	}

	public boolean update(T t) { //FIXME: deleted objects should not be updated
		try {

			checkValidations(t);
			t.setModificationDate(Calendar.getInstance());
			t.setDeleted(false);
			// getHibernateTemplate().setAllowCreate(false);
			// getSingleSession().beginTransaction().begin();
			sessionFactory.getCurrentSession().update(t);
			// getSingleSession().beginTransaction().commit();
			return true;
		}
		catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

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
				// logger.error(cv.getLeafBean()); //TODO: nekibuki

			}
			throw new ValidationException("Validation failed: " + cv.getMessage());
		}
	}

}
