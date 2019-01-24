package com.preyma.logic.Gates;

import com.preyma.logic.Simulator.Scheduler;
import com.preyma.logic.Simulator.State;
import com.preyma.logic.Simulator.Component;
import com.preyma.logic.Simulator.OscillationError;

public class ConstantGate extends Component {

    private Scheduler m_scheduler;

    public ConstantGate( Scheduler sch, byte s ) {
        super( 0, 1 );

        m_scheduler= sch;
        getOutput(0).output( s, 1 );
    }

    public void set( boolean b ) throws OscillationError {
        getOutput().output( b ? State.High : State.Low, 1 );
        m_scheduler.stabilize();
    }

    public void set( byte s ) throws OscillationError {
        getOutput().output( s, 1 );
        m_scheduler.stabilize();
    }

    public void update() {}

}
