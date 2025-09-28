import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Clase encargada de generar los reportes CSV requeridos por el proyecto.
 * Crea archivos de reporte para vendedores y productos con el formato
 * especificado.
 */
public class ReportGenerator {

    /**
     * Genera un reporte de vendedores ordenado por dinero recaudado (mayor a
     * menor).
     * El archivo contiene: NombresVendedor;ApellidosVendedor;DineroRecaudado
     * 
     * @param vendedores    Lista de vendedores a incluir en el reporte
     * @param nombreArchivo Nombre del archivo a generar
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    public static void generarReporteVendedores(List<Vendedor> vendedores, String nombreArchivo) throws IOException {
        // Ordenar vendedores por dinero recaudado (descendente)
        Collections.sort(vendedores, new Comparator<Vendedor>() {
            @Override
            public int compare(Vendedor v1, Vendedor v2) {
                return Double.compare(v2.getDineroRecaudado(), v1.getDineroRecaudado());
            }
        });

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Vendedor vendedor : vendedores) {
                writer.write(vendedor.getNombres() + ";" +
                        vendedor.getApellidos() + ";" +
                        String.format("%.2f", vendedor.getDineroRecaudado()));
                writer.newLine();
            }
        }
    }

    /**
     * Genera un reporte de productos ordenado por cantidad vendida (mayor a menor).
     * El archivo contiene: NombreProducto;PrecioUnitario;CantidadVendida
     * 
     * @param productos     Lista de productos a incluir en el reporte
     * @param nombreArchivo Nombre del archivo a generar
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    public static void generarReporteProductos(List<Producto> productos, String nombreArchivo) throws IOException {
        // Ordenar productos por cantidad vendida (descendente)
        Collections.sort(productos, new Comparator<Producto>() {
            @Override
            public int compare(Producto p1, Producto p2) {
                return Integer.compare(p2.getCantidadVendida(), p1.getCantidadVendida());
            }
        });

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Producto producto : productos) {
                writer.write(producto.getNombre() + ";" +
                        String.format("%.2f", producto.getPrecio()) + ";" +
                        producto.getCantidadVendida());
                writer.newLine();
            }
        }
    }

    /**
     * Muestra estadísticas básicas en consola para verificación.
     * 
     * @param vendedores Lista de vendedores
     * @param productos  Lista de productos
     */
    public static void mostrarEstadisticas(List<Vendedor> vendedores, List<Producto> productos) {
        System.out.println("\n=== ESTADÍSTICAS GENERALES ===");
        System.out.println("Total de vendedores procesados: " + vendedores.size());
        System.out.println("Total de productos disponibles: " + productos.size());

        // Calcular totales
        double totalRecaudado = 0;
        int totalProductosVendidos = 0;

        for (Vendedor v : vendedores) {
            totalRecaudado += v.getDineroRecaudado();
        }

        for (Producto p : productos) {
            totalProductosVendidos += p.getCantidadVendida();
        }

        System.out.println("Total recaudado por todos los vendedores: $" + String.format("%.2f", totalRecaudado));
        System.out.println("Total de productos vendidos: " + totalProductosVendidos);

        // Mostrar mejor vendedor
        if (!vendedores.isEmpty()) {
            Vendedor mejorVendedor = Collections.max(vendedores, Comparator.comparing(Vendedor::getDineroRecaudado));
            System.out.println("Mejor vendedor: " + mejorVendedor.getNombreCompleto() +
                    " ($" + String.format("%.2f", mejorVendedor.getDineroRecaudado()) + ")");
        }

        // Mostrar producto más vendido
        if (!productos.isEmpty()) {
            Producto productoMasVendido = Collections.max(productos,
                    Comparator.comparing(Producto::getCantidadVendida));
            System.out.println("Producto más vendido: " + productoMasVendido.getNombre() +
                    " (" + productoMasVendido.getCantidadVendida() + " unidades)");
        }
    }
}