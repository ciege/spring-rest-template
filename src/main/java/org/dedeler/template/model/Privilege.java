package org.dedeler.template.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represent a privilege which grants permission to reach a resource/service which needs authorization. <br>
 * <br>
 * Note that privileges are own by no entities but {@link Role}
 * 
 * @author yasa
 * 
 */
@Entity
@Table(name = "Privilege")
public class Privilege extends AbstractModel {

	private static final long serialVersionUID = 1L;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Privilege [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
