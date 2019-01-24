package com.preyma.logic.Simulator;

/**
 * Basic electric state of a net or pin
 */
public class State {
    public static final byte Undefined =  0;
    public static final byte Error =      1;
    public static final byte Low =        2;
    public static final byte High =       3;
    public static final byte PullUp =     4;
    public static final byte PullDown =   5;

    private byte m_state;

    State() {
        m_state= Undefined;
    }

    State( State s ) {
        m_state= s.m_state;
    }

    State( boolean s ) {
        m_state= s ? High : Low;
    }

    State( byte s ) {
        m_state= s;
    }


    void set( byte s ) {
        m_state= s;
    }

    void set( State s ) {
        m_state= s.m_state;
    }


    byte toByte() {
        return m_state;
    }

    public String toString() {
        switch( m_state ) {
            case Undefined:
                return "Undefined";

            case Error:
                return "Error";

            case Low:
                return "Low";

            case High:
                return "High";

            case PullUp:
                return "Pull-Up";

            case PullDown:
                return "Pull-Down";

            default:
                return "<Unknwon-State>";
        }
    }

    public boolean is( State s ) {
        return m_state == s.m_state;
    }

    public boolean is( byte s ) {
        return m_state == s;
    }

    void mkBinary() {
        if( m_state == PullUp ) {
            m_state= High;
        }
        if( m_state == PullDown ) {
            m_state= Low;
        }
    }

    public boolean isHigh() {
        return (m_state == High) || (m_state ==  PullUp);
    }

    public boolean isLow() {
        return (m_state == Low) || (m_state ==  PullDown);
    }

    public boolean isDriving() {
        return (m_state == High) || (m_state == Low) || (m_state == Error);
    }

    public boolean isPulling() {
        return (m_state == PullUp) || (m_state == PullDown) || (m_state == Undefined);
    }

    /**
     * Multiplies the own state with a specified one to create a resulting one
     * @param pin - Pin to multiply with
     */
    void multiply( State pin ) {
        // Pin is Z -> Net is unchanged
        if( pin.is( Undefined ) ) {
            return;
        }

        // Pin is driving -> Net is changed or shorted to STATE_ERR
        if( pin.isDriving() ) {
            if( this.isDriving() ) {
                if( !pin.is( this ) ) {
                    m_state= Error;
                    return;
                }
            }

            m_state= pin.m_state;

            // Pin is pulling -> Net is pulled or unchanged
        } else {

            // Net is Z -> Can be pulled
            if( m_state == Undefined ) {
                m_state= pin.m_state;

                // Net is already pulled -> Check for short circuit
            } else {
                if( !pin.is( this ) ) {
                    m_state= Error;
                }
            }
        }
    }
}
