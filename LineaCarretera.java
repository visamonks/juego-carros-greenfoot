import greenfoot.*;

public class LineaCarretera extends Actor
{
    private static final int ANCHO_LINEA = 20;
    private static final int ALTO_LINEA = 90;
    private static final int LIMITE_INFERIOR = 760;

    private CarroJugador jugador;

    public LineaCarretera(CarroJugador jugador)
    {
        this.jugador = jugador;
        crearImagen();
    }

    private void crearImagen()
    {
        GreenfootImage imagen = new GreenfootImage(ANCHO_LINEA, ALTO_LINEA);
        imagen.setColor(Color.WHITE);
        imagen.fillRect(0, 0, ANCHO_LINEA, ALTO_LINEA);
        setImage(imagen);
    }

    public void act()
    {
        bajar();
        eliminarSiSaleDelMundo();
    }

    private void bajar()
    {
        setLocation(getX(), getY() + jugador.getVelocidadVisual());
    }

    private void eliminarSiSaleDelMundo()
    {
        if (getY() > LIMITE_INFERIOR) {
            getWorld().removeObject(this);
        }
    }
}