package fachada;

import basedatos.Medicamento;
import basedatos.Produccion;
import basedatos.Usuario;
import basedatos.Vaca;

public class FachadaWebService {
	
	//---------------------------------------Usuario------------------------------------------------//
	/**
	 * M�todo que devuelve la lista de usuarios como String
	 * 
	 * @return String Lista de usuarios
	 */
	public String listaUsuarios() {
		return new Usuario().listaUsuariosString();
	}
	
	/**
	 * M�todo que devuelve el usuario como String.
	 * 
	 * @param dni
	 *            DNI del usuario o Id del usuario
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * @return String Usuario como String
	 * */
	public String Usuario(String dni, String contrase�a) {
		return new Usuario().usuarioString(dni, contrase�a);
	}

	
	/**
	 * M�todo que si existe el usuario devuelve un true y si no un false
	 * 
	 * @param dni
	 *            DNI o id del usuario
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * @return boolean Existe o no el usuario
	 * */
	public boolean usuarioExistente(String dni, String contrase�a) {
		return new Usuario().usuarioExistente(dni, contrase�a);
	}

	/**
	 * Actualiza los par�metros del usuario cuyo id se pasa por par�metro.
	 * 
	 * @param dni
	 *            DNI o id del ususario
	 * @param nombre
	 *            Nombre del usuario
	 * @param apellido1
	 *            Primer apellido
	 * @param apellido2
	 *            Segundo apellido
	 * @param direccion
	 *            Direcci�n del usuario
	 * @param poblacion
	 *            Poblaci�n del usuario
	 * @param telefono
	 *            Tel�fono del usuario
	 * */
	public void actualizarUsuario(String dni, String nombre, String apellido1,
			String apellido2, String direccion, String poblacion, int telefono,
			String correo, String codigo_explotacion) {
		new Usuario().actualizarUsuario(dni, nombre, apellido1, apellido2,
				direccion, poblacion, telefono, correo, codigo_explotacion);
	}

	/**
	 * Cambia la contrase�a del usuario.
	 * 
	 * @param dni
	 *            DNI o Id del usuario
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * */
	public void actualizarContrase�a(String dni, String contrase�a) {
		new Usuario().actualizarContrase�a(dni, contrase�a);
	}

	/**
	 * Elimina el usuario.
	 * 
	 * @param id_usuario
	 *            Id del usuario
	 * */
	public void eliminarUsuario(String id_usuario) {
		new Usuario().eliminarUsuario(id_usuario);
	}

	/**
	 * M�todo que a�ade un nuevo usuario
	 * 
	 * @param usuario
	 *            Usuario como String
	 */
	public void a�adirUsuario(String usuario) {
		new Usuario().a�adirUsuario(usuario);
	}
	
	/**
	 * M�todo que envia un correo al usuario para recordarle cual es su usuario y contrase�a
	 * @param correo String correo del usuario
	 */
	public void recordarUsuario(String correo){
		new Usuario().recordarContrase�a(correo);
	}
	//--------------------------------------Vaca---------------------------------------------//
	
	/**
	 * M�todo que devuelve la lista de animales que tiene un usuario como String.
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @return String Lista de medicamentos como String
	 * */
	public String listaVacas(String id_usuario) {
		return new Vaca().listaVacasString(id_usuario);
	}
	
	/**
	 * M�todo que devuelve el animal como String.
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * @param id_usuario
	 *            DNI del usuario o Id del usuario
	 * @return String Vaca como String
	 * */
	public String Vaca(String id_vaca, String id_usuario) {
		return new Vaca().vacaString(id_vaca, id_usuario);
	}

	/**
	 * M�todo que a�ade un animal que se pasa por parametro como String.
	 * 
	 * @param vaca
	 *            Vaca que como String para deserializar
	 * */
	public void a�adirVaca(String vaca) {
		new Vaca().a�adirVaca(vaca);
	}

	/**
	 * M�todo que elimina un animal
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @param id_usuario
	 *            Id del usuario
	 * */
	public void eliminarVaca(String id_vaca, String id_usuario) {
		new Vaca().eliminarVaca(id_vaca, id_usuario);
	}
	
	/**
	 * M�todo que elimina la lista de vacas de un usuario
	 * 
	 * @param id_usuario
	 */
	public void eliminarVacas(String id_usuario){
		new Vaca().eliminarVacas(id_usuario);
	}
	
	//-----------------------------------Medicamento---------------------------------//
	/**
	 * M�todo que devuelve la lista de medicamentos como String.
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @return String Lista de medicamentos como String
	 * */
	public String listaMedicamentos(String id_vaca) {
		return new Medicamento().listaMedicamentoString(id_vaca);
	}

	/**
	 * M�todo que devuelve el medicamento que hay en la base de datos como
	 * String.
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * @param id_medicamento
	 *            Id del medicamento
	 * @return String Medicamento como String
	 * */
	public String Medicamento(String id_vaca, String id_medicamento) {
		return new Medicamento().medicamentoString(id_vaca, id_medicamento);
	}

	/**
	 * M�todo que a�ade un medicamento que se pasa por parametro como String.
	 * 
	 * @param medicamento
	 *            Medicamento como String
	 * */
	public void a�adirMedicamento(String medicamento) {
		new Medicamento().a�adirMedicamento(medicamento);
	}

	/**
	 * Elimina el medicamento.
	 * 
	 * @param id_medicamento
	 *            Id del medicamento
	 * @param id_vaca
	 *            Id de la vaca
	 * */
	public void eliminarMedicamento(String id_medicamento, String id_vaca) {
		new Medicamento().eliminarMedicamento(id_medicamento, id_vaca);
	}
	
	/**
	 * M�todo que elimina todos los medicamentos de un animal
	 * @param id_vaca
	 */
	public void eliminarMedicamentos(String id_vaca){
		new Medicamento().eliminarMedicamentos(id_vaca);
	}
	
	//-----------------------------------Producci�n-------------------------------------//
	
	/**
	 * Devuelve la lista de producci�n que hay en la base de datos cloud como
	 * String
	 * 
	 * @param id_usuario
	 * @return String Lista de produccion
	 */
	public String getProducciones(String id_usuario){
		return new Produccion().getProducciones(id_usuario);
	}
	
	/**
	 * Guarda en la base de datos cloud la lista de producci�n que se pasa por
	 * par�metro como String
	 * 
	 * @param listaProducciones
	 *            String Lista de producci�n
	 * @param id_usuario
	 *            String id usuario
	 */
	public void setProducciones(String listaProducciones,String id_usuario){
		new Produccion().setProducciones(listaProducciones,id_usuario);
	}

}
