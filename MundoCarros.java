import greenfoot.*;

public class MundoCarros extends World
{
    public MundoCarros()
    {    
        super(600, 700, 1);
        prepararMundo();
    }

    private void prepararMundo()
    {
        GreenfootImage fondo = new GreenfootImage(600, 700);
        fondo.setColor(Color.DARK_GRAY);
        fondo.fill();
        setBackground(fondo);

        CarroJugador jugador = new CarroJugador();
        addObject(jugador, 300, 580);
    }
}