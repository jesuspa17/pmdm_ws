package com.dam.salesianostriana.calccalorias;

import java.nio.file.Files;
import java.nio.file.Paths;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Created by Jesus Pallares on 21/01/2016.
 */
public class GreenDao {
    public static void main(String[] args) {
        Schema schema = new Schema(1000, "com.dam.salesianostriana.calccalorias");
        crearTablas(schema);
        try {

            if (!Files.isDirectory(Paths.get("./src-gen")))
                Files.createDirectory(Paths.get("./src-gen"));

            new DaoGenerator().generateAll(schema, "./src-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void crearTablas(Schema schema) {

        //Tabla Usuario.
        Entity usuario = schema.addEntity("Usuario");
        usuario.addIdProperty();
        usuario.addStringProperty("nombre");
        usuario.addDoubleProperty("peso");
        usuario.addStringProperty("usuario");
        usuario.addStringProperty("password");

        Entity ruta = schema.addEntity("Ruta");
        ruta.addIdProperty();
        ruta.addDoubleProperty("distancia");
        ruta.addDoubleProperty("calorias");
        ruta.addDateProperty("fecha");
        ruta.addStringProperty("duracion");


        //relación usuario-ruta
        Property id_usuario = ruta.addLongProperty("id_usuario").getProperty();
        ruta.addToOne(usuario, id_usuario);

        ToMany sitioToValoraciones  = usuario.addToMany(ruta, id_usuario);
        sitioToValoraciones.setName("Ruta_usuario");
    }
}
