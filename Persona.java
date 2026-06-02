import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Persona here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Persona extends Carro
{
    /**
     * Act - do whatever the Persona wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int velocidad = 3;
    private static final int CARRIL_IZQUIERDA = 120;
    private static final int CARRIL_CENTRO_IZQUIERDA = 210;
    private static final int CARRIL_CENTRO = 300;
    private static final int CARRIL_CENTRO_DERECHA = 390;
    private static final int CARRIL_DERECHA = 480;

    public Persona()
    {
        setImage("persona.png");
        getImage().scale(40, 55);
    }
    
    public void act()
    {
        mover();
        detectarColision();
    }
    
    public void mover()
    {
        int nuevaY = getY() + velocidad;
    
        if (nuevaY >= getWorld().getHeight())
        {
            setLocation(obtenerCarrilAleatorio(), 0);
        }
        else
        {
            setLocation(getX(), nuevaY);
        }
    }
    
    private int obtenerCarrilAleatorio()
    {
        int carril = Greenfoot.getRandomNumber(5);

        if (carril == 0) {
            return CARRIL_IZQUIERDA;
        } else if (carril == 1) {
            return CARRIL_CENTRO_IZQUIERDA;
        } else if (carril == 2) {
            return CARRIL_CENTRO;
        } else if (carril == 3) {
            return CARRIL_CENTRO_DERECHA;
        } else {
            return CARRIL_DERECHA;
        }
    }

    public void detectarColision()
    {
        CarroJugador jugador = 
            (CarroJugador)getOneIntersectingObject(CarroJugador.class);

        // Choque con el jugador
        if (jugador != null)
        {
            jugador.quitarPuntos(10);
            setLocation(obtenerCarrilAleatorio(), 0);
        }

        // Choque con otros carros enemigos
        else if (isTouching(Carro.class))
        {
            setLocation(obtenerCarrilAleatorio(), 0);
        }
    }
}
