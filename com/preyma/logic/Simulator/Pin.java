package com.preyma.logic.Simulator;

public interface Pin {

    State getState();
    State getValue();

    void setNet( Net n );
    Net getNet();

    Component getComponent();

    void input( State s ) throws ShortCircuitError;
    void output( State s, int ticks );

    void input( byte s ) throws ShortCircuitError;
    void output( byte s, int ticks );

    void futureSet( byte s );
}
