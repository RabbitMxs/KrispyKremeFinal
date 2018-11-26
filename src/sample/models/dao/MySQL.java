package sample.models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private static Connection conn;
    private static final String driver="com.mysql.jdbc.Driver";
    private static final String user ="root";
    private static final String pasword="mendoza";
    private static final String url="jdbc:mysql://localhost:3306/script";

    public MySQL() {
        conn = null;
        try {
            Class.forName(driver);
            conn=DriverManager.getConnection(url, user, pasword);
            if(conn!=null){
                System.out.println("Conexion establecida...");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error de conexion..."+e);
        }
    }

    //retorna la conexion
    public static Connection getConnection(){
        return conn;
    }

    public void desconectar(){
        conn=null;
        if(conn==null){
            System.out.println("Conexion terminada...");
        }
    }



}