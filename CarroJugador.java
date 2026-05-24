import greenfoot.*;

public class CarroJugador extends Actor
{
    private int velocidad = 5;

    public CarroJugador()
    {
        setImage("carro_jugador.png");
        getImage().scale(70, 110);
    }

    public void act()
    {
        moverCarro();
    }

    private void moverCarro()
    {
        if (Greenfoot.isKeyDown("left")) {
            setLocation(getX() - velocidad, getY());
        }

        if (Greenfoot.isKeyDown("right")) {
            setLocation(getX() + velocidad, getY());
        }

        if (Greenfoot.isKeyDown("up")) {
            setLocation(getX(), getY() - velocidad);
        }

        if (Greenfoot.isKeyDown("down")) {
            setLocation(getX(), getY() + velocidad);
        }

        mantenerDentroDelCamino();
    }

    private void mantenerDentroDelCamino()
    {
        if (getX() < 100) {
            setLocation(100, getY());
        }

        if (getX() > 500) {
            setLocation(500, getY());
        }

        if (getY() < 80) {
            setLocation(getX(), 80);
        }

        if (getY() > 620) {
            setLocation(getX(), 620);
        }
    }
}