import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase principal que procesa los archivos generados y crea los reportes
 * requeridos.
 * 
 * Funcionalidades implementadas:
 * - Lectura de archivos de productos, vendedores y ventas
 * - Procesamiento y cálculo de totales
 * - Generación de reportes CSV ordenados
 */
public class Main {

    /** Mapa para almacenar productos indexados por ID para búsqueda rápida */
    private static Map<Integer, Producto> productosMap;

    /** Mapa para almacenar vendedores indexados por número de documento */
    private static Map<Long, Vendedor> vendedoresMap;

    /**
     * Método principal que ejecuta todo el procesamiento de datos y generación de
     * reportes.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        try {
            System.out.println("=== SISTEMA DE PROCESAMIENTO DE VENTAS ===");
            System.out.println("Iniciando procesamiento de archivos...\n");

            // Inicializar estructuras de datos
            productosMap = new HashMap<>();
            vendedoresMap = new HashMap<>();

            // Paso 1: Cargar información de productos
            System.out.println("1. Cargando información de productos...");
            cargarProductos("productos.txt");
            System.out.println("✓ Productos cargados: " + productosMap.size());

            // Paso 2: Cargar información de vendedores
            System.out.println("\n2. Cargando información de vendedores...");
            cargarVendedores("vendedores.txt");
            System.out.println("✓ Vendedores cargados: " + vendedoresMap.size());

            // Paso 3: Procesar archivos de ventas
            System.out.println("\n3. Procesando archivos de ventas...");
            procesarArchivosVentas();
            System.out.println("✓ Archivos de ventas procesados exitosamente");

            // Paso 4: Generar reportes
            System.out.println("\n4. Generando reportes...");
            generarReportes();
            System.out.println("✓ Reportes generados exitosamente");

            // Mostrar estadísticas
            ReportGenerator.mostrarEstadisticas(
                    new ArrayList<>(vendedoresMap.values()),
                    new ArrayList<>(productosMap.values()));

            System.out.println("\n=== PROCESAMIENTO COMPLETADO EXITOSAMENTE ===");
            System.out.println("Archivos generados:");
            System.out.println("- reporte_vendedores.csv (vendedores ordenados por recaudación)");
            System.out.println("- reporte_productos.csv (productos ordenados por cantidad vendida)");

        } catch (Exception e) {
            System.err.println("Error durante el procesamiento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga la información de productos desde el archivo productos.txt.
     * Formato: IDProducto;NombreProducto;PrecioPorUnidad
     * 
     * @param nombreArchivo Nombre del archivo de productos
     * @throws IOException Si ocurre un error al leer el archivo
     */
    private static void cargarProductos(String nombreArchivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int lineaNumero = 0;

            while ((linea = reader.readLine()) != null) {
                lineaNumero++;
                linea = linea.trim();

                if (linea.isEmpty()) {
                    continue; // Saltar líneas vacías
                }

                try {
                    String[] datos = linea.split(";");
                    if (datos.length >= 3) {
                        int id = Integer.parseInt(datos[0].trim());
                        String nombre = datos[1].trim();
                        double precio = Double.parseDouble(datos[2].trim());

                        Producto producto = new Producto(id, nombre, precio);
                        productosMap.put(id, producto);
                    } else {
                        System.out.println("Advertencia: Línea " + lineaNumero + " en " + nombreArchivo
                                + " tiene formato incorrecto");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Advertencia: Error de formato numérico en línea " + lineaNumero + " de " + nombreArchivo);
                }
            }
        }
    }

    /**
     * Carga la información de vendedores desde el archivo vendedores.txt.
     * Formato: TipoDocumento;NúmeroDocumento;Nombres;Apellidos
     * 
     * @param nombreArchivo Nombre del archivo de vendedores
     * @throws IOException Si ocurre un error al leer el archivo
     */
    private static void cargarVendedores(String nombreArchivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            int lineaNumero = 0;

            while ((linea = reader.readLine()) != null) {
                lineaNumero++;
                linea = linea.trim();

                if (linea.isEmpty()) {
                    continue; // Saltar líneas vacías
                }

                try {
                    String[] datos = linea.split(";");
                    if (datos.length >= 4) {
                        String tipoDocumento = datos[0].trim();
                        long numeroDocumento = Long.parseLong(datos[1].trim());
                        String nombres = datos[2].trim();
                        String apellidos = datos[3].trim();

                        Vendedor vendedor = new Vendedor(tipoDocumento, numeroDocumento, nombres, apellidos);
                        vendedoresMap.put(numeroDocumento, vendedor);
                    } else {
                        System.out.println("Advertencia: Línea " + lineaNumero + " en " + nombreArchivo
                                + " tiene formato incorrecto");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Advertencia: Error de formato numérico en línea " + lineaNumero + " de " + nombreArchivo);
                }
            }
        }
    }

    /**
     * Procesa todos los archivos de ventas en el directorio actual.
     * Busca archivos que comiencen con "ventas_" y tengan extensión .txt
     */
    private static void procesarArchivosVentas() {
        File directorio = new File(".");
        File[] archivos = directorio.listFiles();

        if (archivos == null) {
            System.out.println("No se pudo acceder al directorio actual");
            return;
        }

        int archivosVentasProcesados = 0;

        for (File archivo : archivos) {
            if (archivo.getName().startsWith("ventas_") && archivo.getName().endsWith(".txt")) {
                try {
                    procesarArchivoVenta(archivo.getName());
                    archivosVentasProcesados++;
                } catch (IOException e) {
                    System.out.println("Error procesando " + archivo.getName() + ": " + e.getMessage());
                }
            }
        }

        System.out.println("Archivos de ventas procesados: " + archivosVentasProcesados);
    }

    /**
     * Procesa un archivo individual de ventas.
     * Formato:
     * - Primera línea: TipoDocumento;NúmeroDocumento
     * - Líneas siguientes: IDProducto;CantidadVendida;
     * 
     * @param nombreArchivo Nombre del archivo de ventas a procesar
     * @throws IOException Si ocurre un error al leer el archivo
     */
    private static void procesarArchivoVenta(String nombreArchivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea = reader.readLine();

            if (linea == null) {
                System.out.println("Advertencia: Archivo " + nombreArchivo + " está vacío");
                return;
            }

            // Procesar primera línea (información del vendedor)
            String[] infoVendedor = linea.trim().split(";");
            if (infoVendedor.length < 2) {
                System.out.println("Advertencia: Formato incorrecto en primera línea de " + nombreArchivo);
                return;
            }

            long numeroDocumento;
            try {
                numeroDocumento = Long.parseLong(infoVendedor[1].trim());
            } catch (NumberFormatException e) {
                System.out.println("Advertencia: Número de documento inválido en " + nombreArchivo);
                return;
            }

            Vendedor vendedor = vendedoresMap.get(numeroDocumento);
            if (vendedor == null) {
                System.out.println("Advertencia: Vendedor con documento " + numeroDocumento + " no encontrado");
                return;
            }

            // Procesar líneas de ventas
            int lineaNumero = 1;
            while ((linea = reader.readLine()) != null) {
                lineaNumero++;
                linea = linea.trim();

                if (linea.isEmpty()) {
                    continue;
                }

                try {
                    String[] datosVenta = linea.split(";");
                    if (datosVenta.length >= 2) {
                        int idProducto = Integer.parseInt(datosVenta[0].trim());
                        int cantidad = Integer.parseInt(datosVenta[1].trim());

                        // Buscar producto y actualizar estadísticas
                        Producto producto = productosMap.get(idProducto);
                        if (producto != null) {
                            producto.sumarVenta(cantidad);
                            double montoVenta = producto.getPrecio() * cantidad;
                            vendedor.sumarRecaudacion(montoVenta);
                        } else {
                            System.out.println(
                                    "Advertencia: Producto ID " + idProducto + " no encontrado en " + nombreArchivo);
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out
                            .println("Advertencia: Error de formato en línea " + lineaNumero + " de " + nombreArchivo);
                }
            }
        }
    }

    /**
     * Genera los reportes CSV requeridos por el proyecto.
     * 
     * @throws IOException Si ocurre un error al escribir los archivos
     */
    private static void generarReportes() throws IOException {
        // Convertir mapas a listas para los reportes
        List<Vendedor> vendedores = new ArrayList<>(vendedoresMap.values());
        List<Producto> productos = new ArrayList<>(productosMap.values());

        // Generar reporte de vendedores
        ReportGenerator.generarReporteVendedores(vendedores, "reporte_vendedores.csv");

        // Generar reporte de productos
        ReportGenerator.generarReporteProductos(productos, "reporte_productos.csv");
    }
}