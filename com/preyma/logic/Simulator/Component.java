package com.preyma.logic.Simulator;

import java.util.ArrayList;

/**
 * Generic Component-Class with a fised number of inputs and outputs
 * The abstract update method is implemented by the actual gates
 */
public abstract class Component {

    ArrayList< ComponentPin > m_pins;
    int m_inputCount;

    public Component( int inputs, int outputs ) {
        m_inputCount= inputs;

        m_pins= new ArrayList<ComponentPin>( inputs+ outputs );
        for( int i= 0; i!= inputs; i++ ) {
            m_pins.add( new ComponentInput( this, i ) );
        }
        for( int i= 0; i!= outputs; i++ ) {
            m_pins.add( new ComponentOutput( this, i ) );
        }

    }

    public ComponentPin getInput( int index ) {
        return m_pins.get( index );
    }

    public ComponentPin getOutput( int index ) {
        return m_pins.get( m_inputCount+ index );
    }

    protected ComponentPin getOutput() {
        return m_pins.get( m_inputCount );
    }

    public abstract void update();

}
