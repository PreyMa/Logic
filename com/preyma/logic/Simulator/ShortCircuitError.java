package com.preyma.logic.Simulator;

class ShortCircuitError extends Exception {

    Pin m_pin;

    ShortCircuitError( Pin p ) {
        m_pin= p;
    }

    Pin getPin() {
        return m_pin;
    }
}
