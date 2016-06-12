package Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase que gestiona una conexion con una base de datos.
 *
 * @author Kevin
 */
public class ConexionMySQL {

    private final String PATH = "ficheros/conexionmysql.properties";    //Ruta del fichero de configuracion

    private final String CONTROLLER;    //Driver de la base de datos
    private final String NOMBRE_BD;     //Nombre de la base de datos
    private final String ENLACE_BD;     //Direccion de la base de datos
    private final String USUARIO;       //Usuario de la base de datos
    private final String PASSWORD;      //Contraseña del usuario

    private final Connection connection;    //Conexion con la base de datos
    private final Statement statement;      //Objeto de sentencias

    /**
     * Crea una conexion con una base de datos cuyas propiedades estan definidas
     * en el archivo pasado como parametro.
     *
     * @throws FileNotFoundException si el archivo no existe.
     * @throws IOException si hay un error de lectura del archivo.
     * @throws ClassNotFoundException si no puede importar.
     * @throws SQLException si hay un error al conectar conla base de datos.
     */
    public ConexionMySQL() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        Properties propiedades = new Properties();
        try (FileInputStream in = new FileInputStream(PATH)) {
            propiedades.load(in);
        }

        CONTROLLER = propiedades.getProperty("controller");
        if (CONTROLLER != null) {
            Class.forName(CONTROLLER);
        }

        NOMBRE_BD = propiedades.getProperty("nombre");
        ENLACE_BD = propiedades.getProperty("direccion");
        USUARIO = propiedades.getProperty("usuario");
        PASSWORD = propiedades.getProperty("contrasenya");

        connection = DriverManager.getConnection(ENLACE_BD + NOMBRE_BD, USUARIO, PASSWORD);
        statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    }

    /**
     * Ejecuta la consulta MySQL pasada como parametro.
     *
     * @param consulta la sentencia de consulta MySQL a ejecutar.
     * @return el resultado de la consulta.
     * @throws SQLException si hay algun problema con la consulta.
     */
    public ResultSet ejecutarConsulta(String consulta) throws SQLException {
        return statement.executeQuery(consulta);
    }

    /**
     * Ejecuta una sentencia de actualizacion MySQL.
     *
     * @param sentencia la sentencia a ejecutar.
     * @throws SQLException si hay algun fallo en la ejecucion.
     */
    public void ejecutaSentencia(String sentencia) throws SQLException {
        statement.executeUpdate(sentencia);
    }

    /**
     * Cierra la conexion con la base de datos.
     *
     * @throws SQLException si hay algun fallo al cerrar la base de datos.
     */
    public void close() throws SQLException {
        statement.close();
        connection.close();
    }

}
