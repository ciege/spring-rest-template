package org.dedeler.template.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * All entities shall extend this class. This class provides
 * common fields for all database entities.
 * 
 * @author destan
 * 
 */
@MappedSuperclass
public abstract class AbstractModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long oid;

	@NotNull
	private boolean deleted;

	private Calendar creationDate;
	private Calendar modificationDate;
	private Calendar deletionDate;

	public AbstractModel() {
		this.deleted = false;
		this.creationDate = Calendar.getInstance();
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Calendar getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Calendar modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Calendar getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(Calendar deletionDate) {
		this.deletionDate = deletionDate;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractModel [oid=");
		builder.append(oid);
		builder.append(", deleted=");
		builder.append(deleted);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", modificationDate=");
		builder.append(modificationDate);
		builder.append(", deletionDate=");
		builder.append(deletionDate);
		builder.append("]");
		return builder.toString();
	}

}
