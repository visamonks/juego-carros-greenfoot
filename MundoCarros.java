import greenfoot.*;

public class MundoCarros extends World
{
    private static final int ANCHO = 600;
    private static final int ALTO = 700;

    private static final int POS_X_JUGADOR = 300;
    private static final int POS_Y_JUGADOR = 560;

    private static final int POS_X_PANEL = 155;
    private static final int POS_Y_PANEL = 200;

    private static final int POS_X_LINEA = 300;

    private int contadorLineas = 0;

    private CarroJugador jugador;

    public MundoCarros()
    {    
        super(ANCHO, ALTO, 1);
        prepararMundo();
    }

    private void prepararMundo()
    {
        crearFondo();

        jugador = new CarroJugador();
        addObject(jugador, POS_X_JUGADOR, POS_Y_JUGADOR);

        PanelMarchas panelMarchas = new PanelMarchas(jugador);
        addObject(panelMarchas, POS_X_PANEL, POS_Y_PANEL);

        agregarLineasIniciales();
    }

    private void crearFondo()
    {
        GreenfootImage fondo = new GreenfootImage(ANCHO, ALTO);
        fondo.setColor(Color.DARK_GRAY);
        fondo.fill();
        setBackground(fondo);
    }

    private void agregarLineasIniciales()
    {
        addObject(new LineaCarretera(jugador), POS_X_LINEA, 100);
        addObject(new LineaCarretera(jugador), POS_X_LINEA, 300);
        addObject(new LineaCarretera(jugador), POS_X_LINEA, 500);
    }

    public void act()
    {
        generarLineasCarretera();
    }

    private void generarLineasCarretera()
    {
        int velocidad = jugador.getVelocidadVisual();

        if (velocidad <= 0) {
            return;
        }

        contadorLineas++;

        int frecuencia = calcularFrecuenciaLineas(velocidad);

        if (contadorLineas >= frecuencia) {
            addObject(new LineaCarretera(jugador), POS_X_LINEA, -60);
            contadorLineas = 0;
        }
    }

    private int calcularFrecuenciaLineas(int velocidad)
    {
        int frecuencia = 90 - velocidad * 5;

        if (frecuencia < 30) {
            frecuencia = 30;
        }

        return frecuencia;
    }
}