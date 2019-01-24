package com.preyma.logic.Simulator;

/**
 * Top level class of a components pin
 */
public abstract class ComponentPin implements Pin {

    private Net m_net;
    private final Component m_comp;
    private final int m_id;

    ComponentPin( Component c, int i ) {
        m_net= null;
        m_comp= c;
        m_id= i;
    }

    public Component getComponent() {
        return m_comp;
    }


    public Net getNet() {
        return m_net;
    }

    public void setNet( Net n ) {
        m_net= n;
    }

    public void input( State s ) throws ShortCircuitError {  this.input( s.toByte() );        }
    public void output( State s )                         { this.output( s.toByte(), 1 );     }
    public void output( State s, int ticks )              { this.output( s.toByte(), ticks ); }
}
