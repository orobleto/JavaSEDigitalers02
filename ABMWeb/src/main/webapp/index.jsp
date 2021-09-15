<%@page import="com.educacionit.enumerados.Mensajes"%>
<%@page import="com.educacionit.interfaces.Fechas"%>
<%@page import="com.educacionit.entidades.Alumno"%>
<%@page
	import="com.educacionit.implementaciones.mariadb.AlumnoImplementacion"%>
<%@page import="com.educacionit.entidades.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/button.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/table.css">
<link rel="stylesheet" href="css/alert.css">
<title>Inicio</title>
</head>

<%
Usuario usuario = (Usuario) session.getAttribute("usuario");
if (null == usuario) {
	response.sendRedirect("login.jsp");
}

Mensajes mensaje = (Mensajes) request.getAttribute("Mensaje");
%>
<body>
	<%
	if (null != mensaje) {
	%>
	<div id="alert">
		<a class="alert <%=mensaje.getClaseCSS()%>" href="#alert"><%=mensaje.getMensaje()%></a>
	</div>
	<%
	}
	%>


	<header>
		<h1>Digitalers - Educacion IT</h1>
		<img alt="" src="images/logo-it.svg" width="200" height="50">

	</header>

	<nav>
		<a href="alumno.jsp">Agregar Alumno</a> <a
			href="ValidarLogin?cerrarSesion=true">Cerrar Sesion</a>
	</nav>


	<section id="alumnos">
		<h3>Lista de Alumnos</h3>
		<table>
			<tr>
				<th>Documento</th>
				<th>Descripcion</th>
				<th>Fecha de Nacimiento</th>
				<th>Activo</th>
				<th>Accion</th>
			</tr>
			<%
			AlumnoImplementacion alumnoImplementacion = new AlumnoImplementacion();

			for (Alumno alumno : alumnoImplementacion.listar()) {
			%>
			<tr>
				<td><%=alumno.getDocumento().getTipo() + " - " + alumno.getDocumento().getNumero()%></td>
				<td><%=alumno.getDescripcion()%></td>
				<td><%=Fechas.getFechaAString(alumno.getFechaNacimiento())%></td>
				<td><%=alumno.getActivo() ? "Si" : "No"%></td>
				<td>
					<button class="warning"
						onclick="window.location.href='alumno.jsp?tipoDocumento=<%=alumno.getDocumento().getTipo()%>&numeroDocumento=<%=alumno.getDocumento().getNumero()%>&editar=true'">Editar</button>
					<button class="danger"
						onclick="confirmacionBorrado('<%=alumno.getDocumento()%>')?window.location.href='alumnoABM?tipoDocumento=<%=alumno.getDocumento().getTipo()%>&numeroDocumento=<%=alumno.getDocumento().getNumero()%>':window.location.href='#'">Eliminar</button>

					<%-- 						"window.location.href='alumnoABM?tipoDocumento=<%=alumno.getDocumento().getTipo()%>&numeroDocumento=<%=alumno.getDocumento().getNumero()%>'" --%>
				</td>
			</tr>

			<%
			}
			%>
		</table>

	</section>


	<footer> Creado por Octavio robleto </footer>
	<script>
		function confirmacionBorrado(mensaje) {
			const borrarRegistro = window.confirm('Desea eliminar ' + mensaje);
			console.log(borrarRegistro);
			return borrarRegistro;
		}
	</script>


</body>
</html>