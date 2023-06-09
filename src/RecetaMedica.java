class RecetaMedica {
    private String medicina;
    private int cantidad;
    private String nombreCliente;
    private String cedula;

    public RecetaMedica(String medicina, int cantidad, String nombreCliente, String cedula) {
        this.medicina = medicina;
        this.cantidad = cantidad;
        this.nombreCliente = nombreCliente;
        this.cedula = cedula;
    }

    public String getMedicina() {
        return medicina;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getCedula() {
        return cedula;
    }
}

