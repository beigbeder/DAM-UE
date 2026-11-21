package model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Producto {
    private int id;

    @SerializedName("title")
    private String nombre;

    @SerializedName("description")
    private String descripcion;

    @SerializedName("stock")
    private int cantidad;

    @SerializedName("price")
    private double precio;

    public void toData(){
        System.out.println("id = " + id);
        System.out.println("nombre = " + nombre);
        System.out.println("descripcion = " + descripcion);
        System.out.println("cantidad = " + cantidad);
        System.out.println("precio = " + precio);
    }
}
