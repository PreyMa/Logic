package com.preyma.logic.Simulator;

/**
 * POD-Type holding a pin and whether it should be ignored during the simulation run
 */
class RingEntry {

    private boolean m_ign;
    private Pin m_pin;
    private byte m_state;

    RingEntry( Pin p, boolean i, byte s ) {
        m_ign= i;
        m_pin= p;
        m_state= s;
    }

    Pin getPin() { return m_pin; }
    boolean ignorePin() { return m_ign; }

    void setPin() {
        m_pin.futureSet( m_state );
    }
}
