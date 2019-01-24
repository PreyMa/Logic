package com.preyma.logic.Simulator;

class ComponentOutput extends ComponentPin {

    private final State m_state;

    ComponentOutput( Component c, int i ) {
        super( c, i );
        m_state= new State( State.Undefined );
    }


    public State getState() {
        return m_state;
    }

    public State getValue() {
        return m_state;
    }

    public void output( byte s, int ticks ) {

        if( getNet() != null ) {
            getNet().notify( this, ticks, s );
        } else {
            m_state.set( s );
        }
    }

    @Override
    public void futureSet(byte s) {
        m_state.set( s );
    }

    /**
     * Try to output to net
     * @param s - State to output
     * @throws ShortCircuitError - If the net and the pin are actively driven and have a different state (ErrorStat is
     * ignored)
     */
    public void input( byte s ) throws ShortCircuitError {
        if( m_state.isDriving() ) {
            if( !m_state.is( s ) ) {
                if( s == State.Error ) {
                    throw new ShortCircuitError( this );
                }
            }
        }
    }
}
