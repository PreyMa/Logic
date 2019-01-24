package com.preyma.logic.Simulator;

import java.util.ArrayList;

/**
 * Net-Class, holding all pins connected to it
 */
public class Net extends State {

    private final Scheduler m_scheduler;

    private final ArrayList<Pin> m_pins;
    //private final ArrayList<Connection> m_con;


    public Net( Scheduler s ) {
        m_pins= new ArrayList<Pin>();
        m_scheduler= s;
    }

    /**
     * Connects a pin to the net, by building a double linked tree-like structure
     * @param p - Pin to connect
     */
    public void connect( Pin p ) {
        p.setNet( this );
        m_pins.add( p );
    }

    /**
     * Notifies the net, that a pin will change state in a specified number of ticks
     * @param p     - Pin that will change to the defined state
     * @param ticks - Number of ticks, when change will occur
     * @param s     - State the pin will change to
     */
    void notify( Pin p, int ticks, byte s ) {
        m_scheduler.schedule( p, ticks, s );
    }

    /**
     * Calculates the state of the net by considering all pins
     */
    private void calcStateFromPins() {
        this.set( State.Undefined );

        for( int i= 0; i!= m_pins.size(); i++ ) {
            this.multiply( m_pins.get( i ).getState() );

            if( this.is( State.Error ) ) {
                System.out.println( "Short circuit on pin" );
            }

        }

        this.mkBinary();
    }

    /**
     * Distributes a state change to all connected pins, and request an update
     * of their component
     * @param p - Pin which creates signal-edge and therefore doesn't have to be updated
     */
    private void distributeState( Pin p ) {

        for( int i= 0; i!= m_pins.size(); i++ ) {
            Pin pin= m_pins.get( i );

            if( pin != p ) {
                if( pin.getComponent() != null ) {
                    m_scheduler.requestUpdate( pin.getComponent() );
                }

                try{
                    pin.input( this );
                } catch ( ShortCircuitError e ) {

                    // If one Pin creates short-circuit error -> distribute error to all pins
                    this.set( State.Error );
                    this.distributeState( p );
                }
            }
        }
    }


    void update( Pin p ) {
        byte oldState= this.toByte();

        if( (p == null) || (p.getState().isPulling() ) ) {
            // A pulling pin requires the inspection of the whole net
            // to check if there are driving pins
            this.calcStateFromPins();

        } else {

            // A driving pin tries to override the net
            this.set( p.getState() );
        }

        // Prevent infinite recursion
        if( !this.is( oldState ) ) {
            this.distributeState(p);
        }
    }

    void merge( Net other ) {
        // @todo: Connections

        for( int i= 0; i!= other.m_pins.size(); i++ ) {
            this.connect( other.m_pins.get( i ) );
        }
    }


}
