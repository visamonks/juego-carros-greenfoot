import greenfoot.*;

public class LuzCarretera extends Actor
{
    private static final int ANCHO = 45;
    private static final int ALTO = 110;
    private static final int LIMITE_INFERIOR = 760;

    private CarroJugador jugador;
    private boolean ladoIzquierdo;

    public LuzCarretera(CarroJugador jugador, boolean ladoIzquierdo)
    {
        this.jugador = jugador;
        this.ladoIzquierdo = ladoIzquierdo;
        crearImagen();
    }

    private void crearImagen()
    {
        GreenfootImage imagen = new GreenfootImage(ANCHO, ALTO);

        imagen.setColor(Color.GRAY);

        if (ladoIzquierdo) {
            // Poste
            imagen.fillRect(28, 20, 6, 90);

            // Brazo de la lámpara
            imagen.fillRect(12, 20, 22, 5);

            // Luz
            imagen.setColor(Color.YELLOW);
            imagen.fillOval(5, 13, 18, 18);
        } else {
            // Poste
            imagen.fillRect(12, 20, 6, 90);

            // Brazo de la lámpara
            imagen.fillRect(12, 20, 22, 5);

            // Luz
            imagen.setColor(Color.YELLOW);
            imagen.fillOval(25, 13, 18, 18);
        }

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