package com.preyma.logic.Simulator;

/**
 * Component Input-Pin
 * Its electrical state is always 'Z'
 */
public class ComponentInput extends ComponentPin {
    private static final State m_internalState= new State( State.Undefined );

    private State m_value;

    ComponentInput( Component c, int i ) {
        super( c, i );
        m_value= new State( State.Undefined );
    }

    public State getState() {
        return m_internalState;
    }

    public State getValue() {
        return m_value;
    }


    public void output( byte s, int ticks ) {
        System.out.println("Cannot ouput to an Input-Pin!");
    }

    public void input( byte s ) {
        m_value.set( s );
    }

    @Override
    public void futureSet(byte s) {}
}
