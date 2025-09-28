/**
 * Clase que representa un vendedor con su información personal y ventas.
 * Almacena los datos básicos del vendedor y permite calcular sus ventas
 * totales.
 */
public class Vendedor {

    /** Tipo de documento del vendedor */
    private String tipoDocumento;

    /** Número de documento del vendedor */
    private long numeroDocumento;

    /** Nombres del vendedor */
    private String nombres;

    /** Apellidos del vendedor */
    private String apellidos;

    /** Total de dinero recaudado por el vendedor */
    private double dineroRecaudado;

    /**
     * Constructor para crear un vendedor con información básica.
     * 
     * @param tipoDocumento   Tipo de documento de identificación
     * @param numeroDocumento Número de documento
     * @param nombres         Nombres del vendedor
     * @param apellidos       Apellidos del vendedor
     */
    public Vendedor(String tipoDocumento, long numeroDocumento, String nombres, String apellidos) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dineroRecaudado = 0.0;
    }

    /**
     * Obtiene el tipo de documento del vendedor.
     * 
     * @return Tipo de documento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Obtiene el número de documento del vendedor.
     * 
     * @return Número de documento
     */
    public long getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Obtiene los nombres del vendedor.
     * 
     * @return Nombres del vendedor
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Obtiene los apellidos del vendedor.
     * 
     * @return Apellidos del vendedor
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Obtiene el nombre completo del vendedor.
     * 
     * @return Nombre completo (nombres + apellidos)
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    /**
     * Obtiene el total de dinero recaudado por el vendedor.
     * 
     * @return Dinero recaudado
     */
    public double getDineroRecaudado() {
        return dineroRecaudado;
    }

    /**
     * Establece el total de dinero recaudado por el vendedor.
     * 
     * @param dineroRecaudado Cantidad de dinero recaudado
     */
    public void setDineroRecaudado(double dineroRecaudado) {
        this.dineroRecaudado = dineroRecaudado;
    }

    /**
     * Suma una cantidad al dinero recaudado por el vendedor.
     * 
     * @param cantidad Cantidad a sumar
     */
    public void sumarRecaudacion(double cantidad) {
        this.dineroRecaudado += cantidad;
    }

    /**
     * Representación en cadena del vendedor para debugging.
     * 
     * @return Información del vendedor en formato legible
     */
    @Override
    public String toString() {
        return "Vendedor{" +
                "tipoDocumento='" + tipoDocumento + '\'' +
                ", numeroDocumento=" + numeroDocumento +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dineroRecaudado=" + dineroRecaudado +
                '}';
    }
}