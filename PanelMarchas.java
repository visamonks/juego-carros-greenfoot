import greenfoot.*;

public class PanelMarchas extends Actor
{
    private static final int ANCHO = 270;
    private static final int ALTO = 150;
    private CarroJugador jugador;

    public PanelMarchas(CarroJugador jugador)
    {
        this.jugador = jugador;
        actualizarPanel();
    }

    public void act()
    {
        actualizarPanel();
    }

    private void actualizarPanel()
    {
        GreenfootImage imagen = new GreenfootImage(ANCHO, ALTO);

        dibujarFondo(imagen);

        if (jugador.estaApagado()) {
            dibujarEstadoApagado(imagen);
        } else {
            dibujarEstadoNormal(imagen);
        }

        setImage(imagen);
    }

    private void dibujarFondo(GreenfootImage imagen)
    {
        imagen.setColor(new Color(15, 15, 15, 190));
        imagen.fillRect(0, 0, ANCHO, ALTO);

        imagen.setColor(new Color(180, 180, 180));
        imagen.drawRect(0, 0, ANCHO - 1, ALTO - 1);
    }

    private void dibujarEstadoApagado(GreenfootImage imagen)
    {
        imagen.setFont(new Font("Arial", true, false, 20));
        imagen.setColor(new Color(230, 60, 60));
        imagen.drawString("CARRO APAGADO", 15, 30);

        imagen.setFont(new Font("Arial", false, false, 17));
        imagen.setColor(Color.WHITE);
        imagen.drawString("Espera: " + jugador.getTiempoApagado() + " s", 15, 90);
        imagen.drawString("Desacelerando...", 15, 50);
        imagen.drawString("Puedes girar", 15, 113);
    }

    private void dibujarEstadoNormal(GreenfootImage imagen)
    {
        int marcha = jugador.getMarcha();
        int rpm = jugador.getRevoluciones();
        int velocidad = jugador.getVelocidadVisual();

        imagen.setFont(new Font("Arial", true, false, 20));

        if (jugador.estaMuerto())
        {
            imagen.setColor(new Color(255, 50, 50));
            imagen.drawString("GAME OVER", 15, 30);
            Greenfoot.stop();
        }
        else
        {
            imagen.setColor(new Color(80, 180, 255));
            imagen.drawString("Marcha: " + marcha, 15, 30);
        }

        imagen.setFont(new Font("Arial", false, false, 17));
        imagen.setColor(Color.WHITE);
        imagen.drawString("RPM: " + rpm, 15, 58);
        imagen.drawString("Velocidad: " + velocidad, 15, 83);

        dibujarMensajeCambio(imagen, marcha);

        imagen.drawString("Vidas: " + jugador.getPuntos(), 15, 105);

 
        World w = getWorld();

        if (w != null)
        {
            MundoCarros mundo = (MundoCarros) w;
            imagen.drawString("Puntos: " + mundo.getScore(), 100, 105);
        }
    }

    private void dibujarMensajeCambio(GreenfootImage imagen, int marcha)
    {
        if (jugador.necesitaSubirMarcha()) {
            imagen.setColor(new Color(255, 210, 60));
            imagen.drawString("Sube con E: " + jugador.getTiempoCambioAlta() + "s", 15, 130);
        } else if (jugador.necesitaBajarMarcha()) {
            imagen.setColor(new Color(255, 160, 60));
            imagen.drawString("Baja con Q: " + jugador.getTiempoCambioBaja() + "s", 15, 130);
        } else if (marcha == 5) {
            imagen.setColor(new Color(80, 230, 120));
            imagen.drawString("Marcha final", 15, 130);
        } else {
            imagen.setColor(new Color(120, 220, 120));
            imagen.drawString("Cambio estable", 15, 130);
        }
    }
}