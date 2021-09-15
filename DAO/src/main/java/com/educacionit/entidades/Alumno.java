package com.educacionit.entidades;

import java.util.Date;
import java.util.Objects;

import com.educacionit.interfaces.Fechas;

public class Alumno {
	private Documento documento;
	private String descripcion;
	private Date fechaNacimiento;
	private Boolean activo;

	public Alumno() {

	}

	public Alumno(Documento documento, String descripcion, Date fechaNacimiento, Boolean activo) {
		super();
		this.documento = documento;
		this.descripcion = descripcion;
		this.fechaNacimiento = fechaNacimiento;
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "Alumno [" + documento + ", " + descripcion + ", " + Fechas.getFechaAString(fechaNacimiento) + ", " + activo + "]";
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(documento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		return Objects.equals(documento, other.documento);
	}

}
