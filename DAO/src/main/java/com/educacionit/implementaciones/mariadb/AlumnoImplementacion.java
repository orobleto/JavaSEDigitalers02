package com.educacionit.implementaciones.mariadb;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.educacionit.entidades.Alumno;
import com.educacionit.entidades.Documento;
import com.educacionit.interfaces.ConexionMariaDB;
import com.educacionit.interfaces.DAO;
import com.educacionit.interfaces.Fechas;

public class AlumnoImplementacion implements DAO<Alumno, Documento>, ConexionMariaDB {
	private PreparedStatement psInsertar;
	private PreparedStatement psModificar;
	private PreparedStatement psEliminar;
	private PreparedStatement psListar;
	private PreparedStatement psBuscar;

	private boolean insertar(Alumno alumno) {
		String query = "insert into alumnos(tipoDocumento, numeroDocumento, descripcion,fechaNacimiento, activo)"
				+ "values (?, ?, ?, ? ,?);";
		try {
			if (null == psInsertar) {
				psInsertar = getConexion().prepareStatement(query);
			}
			psInsertar.setString(1, alumno.getDocumento().getTipo());
			psInsertar.setString(2, alumno.getDocumento().getNumero());
			psInsertar.setString(3, alumno.getDescripcion());
			psInsertar.setDate(4, Date.valueOf(Fechas.geFechaSQLAString(alumno.getFechaNacimiento())));
			psInsertar.setBoolean(5, alumno.getActivo());
			return psInsertar.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean modificar(Alumno alumno) {
		String query = "update alumnos set descripcion = ?, fechaNacimiento = ?, activo = ?"
				+ " where numeroDocumento = ? and tipoDocumento = ?";
		try {
			if (null == psModificar) {
				psModificar = getConexion().prepareStatement(query);
			}

			psModificar.setString(1, alumno.getDescripcion());
			psModificar.setDate(2, Date.valueOf(Fechas.geFechaSQLAString(alumno.getFechaNacimiento())));
			psModificar.setBoolean(3, alumno.getActivo());
			psModificar.setString(4, alumno.getDocumento().getNumero());
			psModificar.setString(5, alumno.getDocumento().getTipo());
			return psModificar.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean eliminar(Alumno alumno) {
		String query = "delete from alumnos where numeroDocumento = ? and tipoDocumento = ?";
		try {
			if (psEliminar == null) {
				psEliminar = getConexion().prepareStatement(query);
			}
			psEliminar.setString(1, alumno.getDocumento().getNumero());
			psEliminar.setString(2, alumno.getDocumento().getTipo());
			return psEliminar.executeUpdate() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Alumno buscar(Documento documento) {
		String query = "select descripcion, fechaNacimiento, activo from alumnos where numeroDocumento = ? and tipoDocumento = ?";
		try {
			if (null == psBuscar) {
				psBuscar = getConexion().prepareStatement(query);
			}

			psBuscar.setString(1, documento.getNumero());
			psBuscar.setString(2, documento.getTipo());

			ResultSet resultado = psBuscar.executeQuery();
			if (resultado.next()) {
				Alumno alumno = new Alumno();
				alumno.setDocumento(documento);
				alumno.setDescripcion(resultado.getString("descripcion"));
				alumno.setFechaNacimiento(Fechas.getStringAFechaSQL(resultado.getString("fechaNacimiento")));
				alumno.setActivo(resultado.getBoolean("activo"));
				return alumno;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Alumno> listar() {
		List<Alumno> alumnos = new ArrayList<>();
		String query = "select tipoDocumento, numerodocumento, descripcion, fechaNacimiento, activo from alumnos";
		try {
			if (null == psListar) {
				psListar = getConexion().prepareStatement(query);
			}

			ResultSet resultado = psListar.executeQuery();

			while (resultado.next()) {
				Alumno alumno = new Alumno();
				alumno.setDocumento(
						new Documento(resultado.getString("tipoDocumento"), resultado.getString("numeroDocumento")));
				alumno.setDescripcion(resultado.getString("descripcion"));
				alumno.setFechaNacimiento(Fechas.getStringAFechaSQL(resultado.getString("fechaNacimiento")));
				alumno.setActivo(resultado.getBoolean("activo"));
				alumnos.add(alumno);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return alumnos;
	}

	@Override
	public boolean guardar(Alumno alumno) { // save
		if (buscar(alumno.getDocumento()) == null) {
			return insertar(alumno);
		}
		return modificar(alumno);

	}
}
