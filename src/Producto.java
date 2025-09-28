/**
 * Clase que representa un producto con su información básica y estadísticas de
 * ventas.
 * Almacena los datos del producto y lleva el control de las cantidades
 * vendidas.
 */
public class Producto {

    /** ID único del producto */
    private int id;

    /** Nombre descriptivo del producto */
    private String nombre;

    /** Precio por unidad del producto */
    private double precio;

    /** Cantidad total vendida del producto */
    private int cantidadVendida;

    /**
     * Constructor para crear un producto con información básica.
     * 
     * @param id     Identificador único del producto
     * @param nombre Nombre del producto
     * @param precio Precio por unidad
     */
    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadVendida = 0;
    }

    /**
     * Obtiene el ID del producto.
     * 
     * @return ID del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del producto.
     * 
     * @return Nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio unitario del producto.
     * 
     * @return Precio por unidad
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Obtiene la cantidad total vendida del producto.
     * 
     * @return Cantidad vendida
     */
    public int getCantidadVendida() {
        return cantidadVendida;
    }

    /**
     * Establece la cantidad vendida del producto.
     * 
     * @param cantidadVendida Nueva cantidad vendida
     */
    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    /**
     * Suma una cantidad a las ventas del producto.
     * 
     * @param cantidad Cantidad a sumar a las ventas
     */
    public void sumarVenta(int cantidad) {
        this.cantidadVendida += cantidad;
    }

    /**
     * Calcula el total de dinero generado por este producto.
     * 
     * @return Total de ventas (precio * cantidad vendida)
     */
    public double calcularTotalVentas() {
        return precio * cantidadVendida;
    }

    /**
     * Representación en cadena del producto para debugging.
     * 
     * @return Información del producto en formato legible
     */
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidadVendida=" + cantidadVendida +
                '}';
    }
}