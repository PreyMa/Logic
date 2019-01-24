package com.preyma.logic.Simulator;


import java.util.ArrayList;

/**
 * Container class holding a Ring-Buffer of Arrays storing the edges/changes on the net
 * The slots hold the changes that will happen in a relative time of ticks in the future
 * Each simulation step the internal pointer is moved a slot forward, so that the next set of
 * egdes can be simulated
 * As the maximum number of delayed ticks is known/defined the strcuture can be Ring-Buffer, that
 * wraps around if a tick-distance is too long
 */
class TimingRing {

    private final static int _max_tick_delay= 64;

    private ArrayList<ArrayList<RingEntry>> m_arr;
    private int m_pos;

    TimingRing() {

        m_arr = new ArrayList< ArrayList<RingEntry> >( _max_tick_delay );
        for( int i= 0; i!= _max_tick_delay; i++ ){
            m_arr.add( new ArrayList<RingEntry>( 10 ) );
        }
        m_pos= 0;

    }

    void insert( Pin p, int ticks, byte s ) {
        this.insert( p, ticks, s, true );
    }

    /**
     * Insert new edge that will occur in a specified number of ticks
     * @param p     - Pin to change
     * @param ticks - Ticks until change
     * @param i     - Ignore Pin during simulation
     */
    void insert( Pin p, int ticks, byte s, boolean i ) {
        if( ticks > _max_tick_delay ) {
            System.out.println("Error: Max allowed ticks are "+ Integer.toString( _max_tick_delay ) );
            return;
        }

        m_arr.get((m_pos+ ticks) % _max_tick_delay).add( new RingEntry( p, i, s ) );
    }

    /**
     * Get current set of changes
     * @return - Changes
     */
    ArrayList<RingEntry> get() {
        return m_arr.get( m_pos );
    }

    /**
     * Find next set of changes
     * @return - Next set of changes or null if the buffer is empty
     */
    boolean next() {
        m_arr.get( m_pos ).clear();

        for( int i= 0; i!= _max_tick_delay; i++ ) {
            m_pos= (m_pos+ 1) % _max_tick_delay;

            if( !this.get().isEmpty() ) {
                return true;
            }
        }

        return false;
    }

}
