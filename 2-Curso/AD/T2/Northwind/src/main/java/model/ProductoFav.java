package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductoFav {
    private int id;
    private int id_producto;

    public void toData(){
        System.out.println("id = " + id);
        System.out.println("id_producto = " + id_producto);
    }
}
