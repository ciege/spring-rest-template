package org.dedeler.template.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
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
	
	/**
	 * Updates certain fields of an entity. 
	 * When this method is called, the target entity (the entity with the same oid as sourceEntity) is retrieved from the persistence context, 
	 * and all the values of the fields given in fieldNames are copied over to the target entity. 
	 * The target entity is then persisted with its new state.
	 * This method expects entities to have conventional getter/setter names. (both getXXX and isXXX is supported.)
	 * @param sourceObject The entity containing the new values
	 * @param fieldNames The names of the fields to be copied
	 * @param klazz The class of the entity
	 * @return true on success
	 */
	public boolean updateFields(T sourceObject, String[] fieldNames, Class<T> klazz){
		T targetObject = dao.findById(klazz, sourceObject.getOid());
		copyFields(sourceObject, targetObject, fieldNames, klazz);
		return dao.update(targetObject);
	}
	
	//TODO: maybe put these in an Utility class
	//FIXME: this method begs for an unit test IMHO
	void copyFields(T sourceObject, T targetObject, String[] fields, Class<T> klazz){
		Method[] methods = klazz.getMethods();
		for (String fieldName: fields) {
			for (Method method : methods) {
				if(method.getName().equals(getSetterNameForField(fieldName))){
					Method getterMethod;
					Object newValue;
					try {
						getterMethod = getGetterMethod(klazz, fieldName);
						newValue = getterMethod.invoke(sourceObject);
						method.invoke(targetObject, newValue);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e); //TODO: may not be ideal, consider
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e);
					} catch (NoSuchMethodException e) {
						throw new RuntimeException(e);
					}
				}
			}			
		}
	}
	


	private Method getGetterMethod(Class<T> klazz, String fieldName) throws NoSuchMethodException {
		Method getterMethod = null;
		String getterNameForField = getGetterNameForField(fieldName);
		try {
			getterMethod = klazz.getMethod(getterNameForField);
		} catch (NoSuchMethodException e) {
			String getterNameForBooleanField = getGetterNameForBooleanField(fieldName);
			try {
				getterMethod = klazz.getMethod(getterNameForBooleanField);
			} catch (NoSuchMethodException e1) {
				logger.debug("No getter method found for field:'"+fieldName+"' in type:'"+klazz.getCanonicalName()+"'");
				throw e1;
			}
			logger.debug("No method "+getterNameForField+" found for field:'"+fieldName+"' in type:'"+klazz.getCanonicalName()+"'. Trying "+getterNameForBooleanField);
		}
		return getterMethod;
	}
	
	private String getSetterNameForField(String fieldName){
		return "set" + StringUtils.capitalize(fieldName);
	}
	private String getGetterNameForField(String fieldName){
	    return "get" + StringUtils.capitalize(fieldName);
	}
	private String getGetterNameForBooleanField(String fieldName){
	    return "is" + StringUtils.capitalize(fieldName);
	}
}
