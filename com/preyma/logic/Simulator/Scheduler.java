package com.preyma.logic.Simulator;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Simulator Class which calculates scheduled changes on the nets until
 * they have stabilized to a state
 */
public class Scheduler {

    private final TimingRing m_ring;
    private final HashSet<Component> m_updateRequests;

    private final static int _max_simulation_iterations= 100;

    public Scheduler() {
        m_ring= new TimingRing();
        m_updateRequests= new HashSet<Component>();
    }


    void schedule( Pin p, int ticks,  byte s, boolean ig ) {
        m_ring.insert( p, ticks, s, ig );
    }

    void schedule( Pin p, int ticks, byte s ) {
        m_ring.insert( p, ticks, s );
    }


    void requestUpdate( Component c ) {
        m_updateRequests.add( c );
    }

    public void stabilize() throws OscillationError {
        int i= 0;
        while( this.onTick() ) {
            i++;
            if( i == _max_simulation_iterations ) {
                throw new OscillationError();
            }
        }
    }


    private boolean onTick() {
        if( !m_ring.next() ) {
            return false;
        }

        ArrayList<RingEntry> entries= m_ring.get();
        for( int i= 0; i!= entries.size(); i++ ) {
            // Update pin to its new value
            entries.get( i ).setPin();

            Pin pin= entries.get( i ).getPin();
            boolean ignorePin= entries.get( i ).ignorePin();


            pin.getNet().update( ignorePin ? pin : null );
        }

        Iterator<Component> it = m_updateRequests.iterator();
        while( it.hasNext() ) {
            it.next().update();
        }

        m_updateRequests.clear();
        return true;
    }

}
