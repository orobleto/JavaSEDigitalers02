package com.educacionit.entidades;

import com.educacionit.excepciones.ExcepcionPatrones;
import com.educacionit.interfaces.Patrones;

/**
 * 
 * @author octav
 *
 */
public class Usuario {
	private String correo;
	private String clave;
	private Boolean activo;

	public Usuario() {

	}

	public Usuario(String correo, String clave, Boolean activo) throws ExcepcionPatrones {
		super();
		setCorreo(correo);
		setClave(clave);
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Usuario [" + correo + ", " + clave + ", " + activo + "]";
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) throws ExcepcionPatrones {
		if (!Patrones.esCorreo(correo)) {
			throw new ExcepcionPatrones(1);
		}
		this.correo = correo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) throws ExcepcionPatrones {
		if (!Patrones.esClave(clave)) {
			throw new ExcepcionPatrones(2);
		}
		this.clave = clave;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

}
