package Modelo;
public class Producto {
    private int id_producto;
    private String nombre;
    private int stock_actual;
    private int stock_minimo;
    private double precio_unitario;
    private String unidad_medida;
    private int id_categoria;
    private String descripcion;
    public Producto() {}
    public Producto(int id_producto, String nombre, int stock_actual, int stock_minimo, double precio_unitario, String unidad_medida, int id_categoria, String descripcion) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.stock_actual = stock_actual;
        this.stock_minimo = stock_minimo;
        this.precio_unitario = precio_unitario;
        this.unidad_medida = unidad_medida;
        this.id_categoria = id_categoria;
        this.descripcion = descripcion;
    }
    public int getId_producto() { return id_producto; }
    public void setId_producto(int id_producto) { this.id_producto = id_producto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getStock_actual() { return stock_actual; }
    public void setStock_actual(int stock_actual) { this.stock_actual = stock_actual; }
    public int getStock_minimo() { return stock_minimo; }
    public void setStock_minimo(int stock_minimo) { this.stock_minimo = stock_minimo; }
    public double getPrecio_unitario() { return precio_unitario; }
    public void setPrecio_unitario(double precio_unitario) { this.precio_unitario = precio_unitario; }
    public String getUnidad_medida() { return unidad_medida; }
    public void setUnidad_medida(String unidad_medida) { this.unidad_medida = unidad_medida; }
    public int getId_categoria() { return id_categoria; }
    public void setId_categoria(int id_categoria) { this.id_categoria = id_categoria; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
