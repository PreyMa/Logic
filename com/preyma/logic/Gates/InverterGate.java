package com.preyma.logic.Gates;

import com.preyma.logic.Simulator.State;
import com.preyma.logic.Simulator.Component;

public class InverterGate extends Component {

    public InverterGate() {
        // 1 Input & 1 Output
        super( 1, 1 );
    }

    public void update() {
        State a=   getInput( 0 ).getValue();

        if( a.is( State.Error ) ||a.is( State.Undefined ) ) {
            getOutput().output( State.Error, 1 );

        } else if( a.isLow() ) {
            getOutput().output( State.Low, 1 );

        } else {
            getOutput().output( State.High, 1 );
        }
    }

}
