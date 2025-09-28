import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Clase encargada de generar archivos planos pseudoaleatorios
 * que servirán como entrada para el programa principal de procesamiento de
 * ventas.
 *
 * Esta clase genera tres tipos de archivos:
 * - Archivos de ventas por vendedor
 * - Archivo de información de productos
 * - Archivo de información de vendedores
 *
 */
public class GenerateInfoFiles {

    /** Generador de números aleatorios para crear datos pseudoaleatorios */
    private static final Random random = new Random();

    /** Lista de nombres reales para generar información coherente */
    private static final String[] NOMBRES = {
            "Juan", "María", "Carlos", "Ana", "Luis", "Carmen", "José", "Laura",
            "Miguel", "Sofia", "Antonio", "Isabel", "Francisco", "Patricia",
            "Manuel", "Rosa", "David", "Elena", "Rafael", "Mónica"
    };

    /** Lista de apellidos reales para generar información coherente */
    private static final String[] APELLIDOS = {
            "García", "González", "Rodríguez", "Fernández", "López", "Martínez",
            "Sánchez", "Pérez", "Gómez", "Martín", "Jiménez", "Ruiz", "Hernández",
            "Díaz", "Moreno", "Muñoz", "Álvarez", "Romero", "Gutiérrez", "Navarro"
    };

    /** Lista de nombres de productos para generar información coherente */
    private static final String[] PRODUCTOS = {
            "Laptop Dell Inspiron", "Mouse Inalámbrico Logitech", "Teclado Mecánico Corsair",
            "Monitor Samsung 24''", "Auriculares Sony WH-1000XM4", "Smartphone iPhone 14",
            "Tablet Samsung Galaxy", "Impresora HP LaserJet", "Disco Duro Externo 1TB",
            "Cámara Web Logitech HD", "Parlantes Bluetooth JBL", "Cargador Portátil Anker",
            "Cable HDMI 2.0", "Memoria USB 64GB", "Router WiFi TP-Link", "Hub USB-C",
            "Silla Ergonómica Oficina", "Lámpara LED Escritorio", "Soporte para Laptop",
            "Mousepad Gaming XL"
    };

    /** Tipos de documento disponibles */
    private static final String[] TIPOS_DOCUMENTO = { "Cedula de Ciudadania", "Cedula de Extranjeria", "Pasaporte" };

    /**
     * Método principal que ejecuta la generación de todos los archivos de prueba.
     * Genera archivos con cantidades predeterminadas para facilitar las pruebas.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando generación de archivos de prueba...");

            // Generar archivo de productos (20 productos)
            createProductsFile(20);
            System.out.println("✓ Archivo de productos generado exitosamente");

            // Generar información de vendedores (nombres, apellidos y documentos)
            String[] nombresVendedores = new String[10];
            String[] apellidosVendedores = new String[10];
            long[] documentosVendedores = new long[10];

            for (int i = 0; i < 10; i++) {
                nombresVendedores[i] = NOMBRES[random.nextInt(NOMBRES.length)];
                apellidosVendedores[i] = APELLIDOS[random.nextInt(APELLIDOS.length)] + " " +
                        APELLIDOS[random.nextInt(APELLIDOS.length)];
                documentosVendedores[i] = 1000000000L + random.nextInt(100000000);
            }

            // Generar archivo de información de vendedores (10 vendedores)
            createSalesManInfoFile(10, nombresVendedores, apellidosVendedores, documentosVendedores);
            System.out.println("✓ Archivo de información de vendedores generado exitosamente");

            // Generar archivos de ventas para cada vendedor usando la misma información
            for (int i = 0; i < 10; i++) {
                String nombreVendedor = nombresVendedores[i];
                long documentoVendedor = documentosVendedores[i];
                int ventasAleatorias = 5 + random.nextInt(16); // Entre 5 y 20 ventas

                createSalesMenFile(ventasAleatorias, nombreVendedor, documentoVendedor);
            }
            System.out.println("✓ Archivos de ventas de vendedores generados exitosamente");

            System.out.println("\n¡Generación de archivos completada exitosamente!");
            System.out.println("Archivos generados:");
            System.out.println("- productos.txt (información de productos)");
            System.out.println("- vendedores.txt (información de vendedores)");
            System.out.println("- Archivos individuales de ventas por vendedor");

        } catch (Exception e) {
            System.err.println("Error durante la generación de archivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Crea un archivo de ventas pseudoaleatorio para un vendedor específico.
     * El archivo contiene información del vendedor y sus ventas de productos.
     * 
     * Formato del archivo:
     * - Primera línea: TipoDocumento;NúmeroDocumento
     * - Líneas siguientes: IDProducto;CantidadVendida;
     * 
     * @param randomSalesCount Cantidad de ventas a generar para el vendedor
     * @param name             Nombre del vendedor (usado para el nombre del
     *                         archivo)
     * @param id               Número de documento del vendedor
     */
    public static void createSalesMenFile(int randomSalesCount, String name, long id) {
        String nombreArchivo = "ventas_" + name.toLowerCase().replace(" ", "_") + "_" + id + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            // Escribir información del vendedor (primera línea)
            String tipoDocumento = TIPOS_DOCUMENTO[random.nextInt(TIPOS_DOCUMENTO.length)];
            writer.write(tipoDocumento + ";" + id);
            writer.newLine();

            // Generar ventas aleatorias
            for (int i = 0; i < randomSalesCount; i++) {
                int idProducto = 1 + random.nextInt(20); // IDs de productos del 1 al 20
                int cantidadVendida = 1 + random.nextInt(10); // Cantidad entre 1 y 10

                writer.write(idProducto + ";" + cantidadVendida + ";");
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al crear archivo de ventas para " + name + ": " + e.getMessage());
        }
    }

    /**
     * Crea un archivo con información pseudoaleatoria de productos.
     * 
     * Formato del archivo:
     * IDProducto;NombreProducto;PrecioPorUnidad
     * 
     * @param productsCount Número de productos a generar
     */
    public static void createProductsFile(int productsCount) {
        String nombreArchivo = "productos.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {

            for (int i = 1; i <= productsCount; i++) {
                String nombreProducto = PRODUCTOS[i % PRODUCTOS.length];
                // Generar precio entre 50,000 y 2,000,000 pesos colombianos
                double precio = 50000 + (random.nextDouble() * 1950000);
                // Redondear a dos decimales
                precio = Math.round(precio * 100.0) / 100.0;

                writer.write(i + ";" + nombreProducto + ";" + precio);
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al crear archivo de productos: " + e.getMessage());
        }
    }

    /**
     * Crea un archivo con información pseudoaleatoria de vendedores.
     * La información incluye tipo de documento, número, nombres y apellidos.
     * 
     * Formato del archivo:
     * TipoDocumento;NúmeroDocumento;Nombres;Apellidos
     * 
     * @param salesmanCount        Número de vendedores a generar
     * @param nombresVendedores    Array con los nombres de los vendedores
     * @param apellidosVendedores  Array con los apellidos de los vendedores
     * @param documentosVendedores Array con los números de documento a usar
     */
    public static void createSalesManInfoFile(int salesmanCount, String[] nombresVendedores,
            String[] apellidosVendedores, long[] documentosVendedores) {
        String nombreArchivo = "vendedores.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {

            for (int i = 0; i < salesmanCount; i++) {
                String tipoDocumento = TIPOS_DOCUMENTO[random.nextInt(TIPOS_DOCUMENTO.length)];
                long numeroDocumento = documentosVendedores[i];
                String nombres = nombresVendedores[i];
                String apellidos = apellidosVendedores[i];

                writer.write(tipoDocumento + ";" + numeroDocumento + ";" + nombres + ";" + apellidos);
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Error al crear archivo de vendedores: " + e.getMessage());
        }
    }
}