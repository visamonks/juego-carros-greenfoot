import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Carronegro here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Carronegro extends Carro
{
    int velocidad = 5;
    public Carronegro()
    {
        setImage("carro_negro.png");
        getImage().scale(55, 85);
    }

    public void addedToWorld(World world)
    {
        int xAleatorio = Greenfoot.getRandomNumber(world.getWidth());
        setLocation(xAleatorio, 0);
    }

    public void act()
    {
        mover();
        detectarColision();
    }
    
    public void detectarColision()
    {
    CarroJugador jugador =
        (CarroJugador)getOneIntersectingObject(CarroJugador.class);

    // choque con jugador
    if (jugador != null)
    {
        jugador.quitarPuntos(10);

        int xAleatorio =
            Greenfoot.getRandomNumber(getWorld().getWidth());

        setLocation(xAleatorio, 0);
    }

    // choque con otros enemigos
    else if (isTouching(Carro.class))
    {
        int xAleatorio =
            Greenfoot.getRandomNumber(getWorld().getWidth());

        setLocation(xAleatorio, 0);
    }
    }

    public void mover()
    {
        int nuevaY = getY() + velocidad;

    // si llega abajo
    if (nuevaY >= getWorld().getHeight())
    {
        int xAleatorio = Greenfoot.getRandomNumber(getWorld().getWidth());

        // reaparece arriba
        setLocation(xAleatorio, 0);
    }
    else
    {
        // sigue bajando
        setLocation(getX(), nuevaY);
    }
    }
}
