import greenfoot.*;

public class CarroJugador extends Carro
{
    private static final int MARCHA_MINIMA = 1;
    private static final int MARCHA_MAXIMA = 5;

    private static final int RPM_INICIAL = 1000;
    private static final int RPM_MINIMA = 700;
    private static final int RPM_MAXIMA = 3200;

    private static final int RPM_SUBIR_MARCHA = 2100;
    private static final int RPM_BAJAR_MARCHA = 900;

    private static final int RPM_CAMBIO_SUBIDA_MIN = 1500;
    private static final int RPM_CAMBIO_SUBIDA_MAX = 3200;
    private static final int RPM_CAMBIO_BAJADA_MAX = 1700;

    private static final int RPM_DESPUES_SUBIR = 1300;
    private static final int RPM_DESPUES_BAJAR = 2100;

    private static final int LIMITE_TIEMPO_ALTA = 140;
    private static final int LIMITE_TIEMPO_BAJA = 140;
    private static final int TIEMPO_APAGADO_TOTAL = 180;

    private static final int VELOCIDAD_LATERAL = 5;
    private static final int VELOCIDAD_MINIMA_VISUAL = 3;
    private static final int VELOCIDAD_FRENANDO_MINIMA = 2;

    private static final int X_MINIMO = 100;
    private static final int X_MAXIMO = 500;
    private static final int Y_MINIMO = 430;
    private static final int Y_MAXIMO = 620;

    private static final int Y_CENTRO_ACELERANDO = 470;
    private static final int Y_REPOSO = 580;

    private int marcha = MARCHA_MINIMA;
    private int revoluciones = RPM_INICIAL;

    private int velocidadVisual = VELOCIDAD_MINIMA_VISUAL;

    private boolean apagado = false;
    private int tiempoApagado = 0;

    private int tiempoZonaAlta = 0;
    private int tiempoZonaBaja = 0;

    private int contadorDesaceleracion = 0;
    private int puntos = 100;
    private boolean teclaESuelta = true;
    private boolean teclaQSuelta = true;

    public CarroJugador()
    {
        setImage("carro_jugador.png");
        getImage().scale(115, 110);
    }

    public void act()
    {
        if (apagado) {
            procesoCarroApagado();
            return;
        }

        controlarMarchas();
        actualizarRevoluciones();
        actualizarVelocidadVisual();
        moverCarro();
        revisarRevoluciones();
    }

    private void controlarMarchas()
    {
        if (teclaPresionadaUnaVez("e", teclaESuelta)) {
            subirMarcha();
            teclaESuelta = false;
        }

        if (!Greenfoot.isKeyDown("e")) {
            teclaESuelta = true;
        }

        if (teclaPresionadaUnaVez("q", teclaQSuelta)) {
            bajarMarcha();
            teclaQSuelta = false;
        }

        if (!Greenfoot.isKeyDown("q")) {
            teclaQSuelta = true;
        }
    }

    private boolean teclaPresionadaUnaVez(String tecla, boolean teclaSuelta)
    {
        return Greenfoot.isKeyDown(tecla) && teclaSuelta;
    }

    private void subirMarcha()
    {
        if (marcha >= MARCHA_MAXIMA) {
            return;
        }

        if (revoluciones >= RPM_CAMBIO_SUBIDA_MIN && revoluciones <= RPM_CAMBIO_SUBIDA_MAX) {
            marcha++;
            revoluciones = RPM_DESPUES_SUBIR;
            reiniciarZonasDeCambio();
        } else {
            apagarCarro();
        }
    }

    private void bajarMarcha()
    {
        if (marcha <= MARCHA_MINIMA) {
            return;
        }

        if (revoluciones <= RPM_CAMBIO_BAJADA_MAX) {
            marcha--;
            revoluciones = RPM_DESPUES_BAJAR;
            reiniciarZonasDeCambio();
        } else {
            apagarCarro();
        }
    }

    private void actualizarRevoluciones()
    {
        if (Greenfoot.isKeyDown("up")) {
            revoluciones += 12;
        } else if (Greenfoot.isKeyDown("down")) {
            revoluciones -= 35;
        } else {
            revoluciones -= 18;
        }

        revoluciones = limitar(revoluciones, RPM_MINIMA, RPM_MAXIMA);
    }

    private void actualizarVelocidadVisual()
    {
        if (Greenfoot.isKeyDown("up")) {
            velocidadVisual = obtenerVelocidadPorMarcha();
        } else if (Greenfoot.isKeyDown("down")) {
            bajarVelocidadHasta(VELOCIDAD_FRENANDO_MINIMA);
        } else {
            bajarVelocidadHasta(VELOCIDAD_MINIMA_VISUAL);
        }
    }

    private int obtenerVelocidadPorMarcha()
    {
        if (marcha == 1) {
            return 3;
        } else if (marcha == 2) {
            return 5;
        } else if (marcha == 3) {
            return 7;
        } else if (marcha == 4) {
            return 9;
        } else {
            return 11;
        }
    }

    private void bajarVelocidadHasta(int minimo)
    {
        if (velocidadVisual > minimo) {
            velocidadVisual--;
        } else {
            velocidadVisual = minimo;
        }
    }

    private void moverCarro()
    {
        boolean girando = moverLateral();

        moverVertical();

        if (!girando) {
            setRotation(0);
        }

        mantenerDentroDelCamino();
    }

    private boolean moverLateral()
    {
        if (Greenfoot.isKeyDown("left")) {
            setLocation(getX() - VELOCIDAD_LATERAL, getY());
            setRotation(-12);
            return true;
        }

        if (Greenfoot.isKeyDown("right")) {
            setLocation(getX() + VELOCIDAD_LATERAL, getY());
            setRotation(12);
            return true;
        }

        return false;
    }

    private void moverVertical()
    {
        if (Greenfoot.isKeyDown("up")) {
            if (getY() > Y_CENTRO_ACELERANDO) {
                setLocation(getX(), getY() - 2);
            }
        } else {
            if (getY() < Y_REPOSO) {
                setLocation(getX(), getY() + 1);
            }
        }

        if (Greenfoot.isKeyDown("down")) {
            if (getY() < Y_MAXIMO) {
                setLocation(getX(), getY() + 2);
            }
        }
    }

    private void revisarRevoluciones()
    {
        revisarRevolucionesAltas();
        revisarRevolucionesBajas();
    }

    private void revisarRevolucionesAltas()
    {
        if (marcha == MARCHA_MAXIMA) {
            tiempoZonaAlta = 0;
            return;
        }

        if (revoluciones >= RPM_SUBIR_MARCHA) {
            tiempoZonaAlta++;

            if (tiempoZonaAlta >= LIMITE_TIEMPO_ALTA) {
                apagarCarro();
            }
        } else {
            tiempoZonaAlta = 0;
        }
    }

    private void revisarRevolucionesBajas()
    {
        if (marcha == MARCHA_MINIMA) {
            tiempoZonaBaja = 0;
            return;
        }

        if (revoluciones <= RPM_BAJAR_MARCHA) {
            tiempoZonaBaja++;

            if (tiempoZonaBaja >= LIMITE_TIEMPO_BAJA) {
                apagarCarro();
            }
        } else {
            tiempoZonaBaja = 0;
        }
    }

    private void apagarCarro()
    {
        apagado = true;
        tiempoApagado = TIEMPO_APAGADO_TOTAL;
        revoluciones = 0;
        contadorDesaceleracion = 0;
        reiniciarZonasDeCambio();
    }

    private void procesoCarroApagado()
    {
        tiempoApagado--;
        contadorDesaceleracion++;

        moverLateralApagado();
        desacelerarApagado();
        regresarAPosicionDeReposo();

        if (tiempoApagado <= 0) {
            encenderCarro();
        }

        mantenerDentroDelCamino();
    }

    private void moverLateralApagado()
    {
        boolean girando = false;

        if (Greenfoot.isKeyDown("left")) {
            setLocation(getX() - VELOCIDAD_LATERAL, getY());
            setRotation(-8);
            girando = true;
        }

        if (Greenfoot.isKeyDown("right")) {
            setLocation(getX() + VELOCIDAD_LATERAL, getY());
            setRotation(8);
            girando = true;
        }

        if (!girando) {
            setRotation(0);
        }
    }

    private void desacelerarApagado()
    {
        if (contadorDesaceleracion >= 12) {
            if (velocidadVisual > 0) {
                velocidadVisual--;
            }

            contadorDesaceleracion = 0;
        }
    }

    private void regresarAPosicionDeReposo()
    {
        if (getY() < Y_REPOSO) {
            setLocation(getX(), getY() + 1);
        }
    }

    private void encenderCarro()
    {
        apagado = false;
        marcha = MARCHA_MINIMA;
        revoluciones = RPM_INICIAL;
        velocidadVisual = VELOCIDAD_MINIMA_VISUAL;
        contadorDesaceleracion = 0;
        reiniciarZonasDeCambio();
    }

    private void reiniciarZonasDeCambio()
    {
        tiempoZonaAlta = 0;
        tiempoZonaBaja = 0;
    }

    private void mantenerDentroDelCamino()
    {
        int x = limitar(getX(), X_MINIMO, X_MAXIMO);
        int y = limitar(getY(), Y_MINIMO, Y_MAXIMO);

        setLocation(x, y);
    }

    private int limitar(int valor, int minimo, int maximo)
    {
        if (valor < minimo) {
            return minimo;
        }

        if (valor > maximo) {
            return maximo;
        }

        return valor;
    }

    public int getMarcha()
    {
        return marcha;
    }

    public int getRevoluciones()
    {
        return revoluciones;
    }

    public int getVelocidadVisual()
    {
        return velocidadVisual;
    }

    public boolean estaApagado()
    {
        return apagado;
    }

    public int getTiempoApagado()
    {
        return tiempoApagado / 60;
    }

    public boolean necesitaSubirMarcha()
    {
        return revoluciones >= RPM_SUBIR_MARCHA && marcha < MARCHA_MAXIMA && !apagado;
    }

    public boolean necesitaBajarMarcha()
    {
        return revoluciones <= RPM_BAJAR_MARCHA && marcha > MARCHA_MINIMA && !apagado;
    }

    public int getTiempoCambioAlta()
    {
        return calcularTiempoRestante(LIMITE_TIEMPO_ALTA, tiempoZonaAlta);
    }

    public int getTiempoCambioBaja()
    {
        return calcularTiempoRestante(LIMITE_TIEMPO_BAJA, tiempoZonaBaja);
    }

    private int calcularTiempoRestante(int limite, int tiempoActual)
    {
        int restante = limite - tiempoActual;

        if (restante < 0) {
            restante = 0;
        }

        return restante / 60;
    }
    public void quitarPuntos(int cantidad)
    {
        puntos -= cantidad;
    
        if (puntos < 0)
        {
            puntos = 0;
        }
    }
    public int getPuntos()
    {
        return puntos;
    }
    public boolean estaMuerto()
    {
        return puntos <= 0;
    }
}